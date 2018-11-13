/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Inventory;
import entity.ReservationEntity;
import entity.ReservationLineItemEntity;
import entity.RoomEntity;
import entity.RoomNumber;
import entity.RoomTypeEntity;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.RoomStatus;
import util.exception.RoomNotFoundException;

/**
 *
 * @author twp10
 */
@Stateless
@Local(RoomEntityControllerLocal.class)
@Remote(RoomEntityControllerRemote.class)

public class RoomEntityController implements RoomEntityControllerRemote, RoomEntityControllerLocal {

    @EJB
    private ReservationEntityControllerLocal reservationEntityControllerLocal;

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    @EJB
    private InventoryControllerLocal inventoryControllerLocal;
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public RoomEntity createNewRoom(RoomEntity newRoom, RoomTypeEntity roomType) {

        em.persist(newRoom);
        roomType.getRoom().add(newRoom);
        newRoom.setRoomType(roomType);
        
        // Need to update inventory from the day of update to the last available inventory object
        inventoryControllerLocal.updateAllInventory();
        em.flush();
        return newRoom;
    }

    // Retrieved from retrieveRoomByRoomNumber
    public void updateRoom(RoomEntity room) {

        RoomEntity roomToUpdate;
        try {
            roomToUpdate = retrieveRoomByRoomNumber(room.getRoomNumber());
            roomToUpdate.setGuest(room.getGuest());
            roomToUpdate.setRoomStatus(room.getRoomStatus());
            roomToUpdate.setRoomType(room.getRoomType());
            roomToUpdate.setIsDisabled(room.getIsDisabled());
            inventoryControllerLocal.updateAllInventory();
        } catch (RoomNotFoundException ex) {
            System.out.println("Room does not exist!");
        }

    }

    // need to modify this 
    // modify to what? (wp)
    @Override
    public void disableRoom(RoomEntity room) {

        room.setIsDisabled(Boolean.TRUE);
        inventoryControllerLocal.updateAllInventory();
    }

    // If VACANT, delete the room
    @Override
    public void deleteRoom(RoomEntity room) {

        RoomTypeEntity roomType = room.getRoomType();

        roomType.getRoom().remove(room);
        room.setRoomType(null);

        em.remove(room);
        
        // If room is already disabled, the room is already not in the inventory
        // Only update inventory if the room is not disabled
        if (room.getIsDisabled().equals(Boolean.FALSE)) {
            inventoryControllerLocal.updateAllInventory();
        }

        em.flush();
    }

    @Override
    public RoomEntity retrieveRoomByRoomNumber(String roomNumber) throws RoomNotFoundException {

        Query query = em.createQuery("SELECT r FROM RoomEntity r WHERE r.roomNumber=:inRoomNumber");
        query.setParameter("inRoomNumber", roomNumber);

        try {

            return (RoomEntity) query.getSingleResult();

        } catch (NoResultException | NonUniqueResultException ex) {

            throw new RoomNotFoundException("Room " + roomNumber + "does not exist!");
        }
    }

    @Override
    public List<RoomEntity> viewAllRoom() {

        Query query = em.createQuery("SELECT r FROM RoomEntity r");

        List<RoomEntity> rooms = (List<RoomEntity>) query.getResultList();

        return rooms;
    }
    
    // Reserve room is still needed because guest can walkin and reserve for future date
    // Once reserve room, we need to allocate room for the guest if the start date of reservation is equal to current date
    @Override
    public void walkInAllocateRoom(Long reservationId) {

        // Retrieve the current reservation
        ReservationEntity reservation = reservationEntityControllerLocal.retrieveReservationById(reservationId);
        List<ReservationLineItemEntity> reservationLineItems = reservation.getReservationLineItemEntities();
        Integer countOfRoomAvailableThroughout;
        Boolean availableThroughout;
        
        // Loop through all the reservationLineItem of currentReservation
        for(ReservationLineItemEntity reservationLineItem : reservationLineItems) {
            
            RoomTypeEntity roomType = reservationLineItem.getRoomType();
            
            List<RoomEntity> rooms = roomType.getRoom();
                
                countOfRoomAvailableThroughout = 0;
                        
                // Loop through all the rooms of the specified roomType & check whether can fullfil the booking requirement
                // Need to check whether the next reservation start date is the same as current reservation end time --> if yes, change the status to allocate
                for(RoomEntity room : rooms) {
                    
                    availableThroughout = Boolean.TRUE;
                    
                    for(LocalDate date = reservation.getStartDate(); !date.isAfter(reservation.getEndDate()) ; date.plusDays(1)) {
                        
                        if ( !room.getRoomStatus().equals(RoomStatus.VACANT) ) {
                            // If the current reservation end date is the same as new reservation start date, the room is considered available
                            if ( room.getRoomStatus().equals(RoomStatus.OCCUPIED) && room.getCurrentReservation().getEndDate().equals(reservation.getStartDate())) {
                            
                            } else {
                                availableThroughout = Boolean.FALSE;
                                break;
                            }
                        }
                    }
                    if ( availableThroughout.equals(Boolean.TRUE) ) {
                        
                        countOfRoomAvailableThroughout++;
                        room.setRoomStatus(RoomStatus.ALLOCATED); // should I change here??
                        // If the room if not occupied
                        if ( room.getCurrentReservation() != null ) {
                            room.setNextReservation(reservation);
                        } else {
                            room.setCurrentReservation(reservation);
                        }
                        
                        reservation.getRooms().add(room);
                        
                        // If all room in reservation has been successfully allocated, break
                        if ( countOfRoomAvailableThroughout.equals(reservationLineItem.getNumOfRoomBooked()) ) {
                            break;
                        }
                    }  
                }                
                if (countOfRoomAvailableThroughout < reservationLineItem.getNumOfRoomBooked()) {
                    //throw unable to allocate all reserved room exception
                }            
        }
    }
}
