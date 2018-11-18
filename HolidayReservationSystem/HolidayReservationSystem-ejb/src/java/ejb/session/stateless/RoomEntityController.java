/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ReservationEntity;
import entity.ReservationLineItemEntity;
import entity.RoomEntity;
import entity.RoomTypeEntity;
import entity.WalkinReservationEntity;
import java.time.LocalDate;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.RoomStatus;
import util.exception.CreateNewRoomException;
import util.exception.ReservationNotFoundException;
import util.exception.RoomNotFoundException;
import util.exception.UpdateInventoryException;

/**
 *
 * @author twp10
 */
@Stateless
@Local(RoomEntityControllerLocal.class)
@Remote(RoomEntityControllerRemote.class)

public class RoomEntityController implements RoomEntityControllerRemote, RoomEntityControllerLocal {

    @EJB
    private RoomTypeEntityControllerLocal roomTypeEntityController;

    @EJB
    private ReservationEntityControllerLocal reservationEntityControllerLocal;

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    @EJB
    private InventoryControllerLocal inventoryControllerLocal;

    @Resource
    private EJBContext eJBContext;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public RoomEntity createNewRoom(RoomEntity newRoom, Long id) throws CreateNewRoomException {

        try {
            RoomTypeEntity roomType = roomTypeEntityController.retrieveRoomTypeById(id);

            em.persist(newRoom);
            roomType.getRoom().add(newRoom);
            newRoom.setRoomType(roomType);
            em.flush();
            // Need to update inventory from the day of update to the last available inventory object
            inventoryControllerLocal.updateAllInventory();

            return newRoom;
        } catch (Exception ex) {

            eJBContext.setRollbackOnly();
            throw new CreateNewRoomException("Error creating new room: " + ex.getMessage());
        }
    }

    // Retrieved from retrieveRoomByRoomNumber
    @Override
    public void updateRoom(RoomEntity room, String roomNumber) {

        RoomEntity roomToUpdate;
        try {
            roomToUpdate = retrieveRoomByRoomNumber(roomNumber);
            if (room.getRoomNumber() != null) {
                roomToUpdate.setRoomNumber(roomNumber);
            }
            if (room.getGuest() != null) {
                roomToUpdate.setGuest(room.getGuest());
            }
            if (room.getRoomStatus() != null) {
                roomToUpdate.setRoomStatus(room.getRoomStatus());
            }
            em.flush();
            inventoryControllerLocal.updateAllInventory();
        } catch (RoomNotFoundException ex) {
            System.out.println("Room does not exist!");
        } catch (UpdateInventoryException ex) {
            System.out.println("Error updating Inventory!");
        }

    }

    @Override
    public void disableRoom(Long id) {

        RoomEntity room = em.find(RoomEntity.class, id);
        room.setIsDisabled(Boolean.TRUE);
        em.flush();
        try {
            inventoryControllerLocal.updateAllInventory();
        } catch (UpdateInventoryException ex) {
            System.out.println("Error updating Inventory!");
        }
    }

    // If VACANT, delete the room
    @Override
    public void deleteRoom(Long roomId, Long roomTypeId) {

        RoomEntity room = em.find(RoomEntity.class, roomId);
        RoomTypeEntity roomType = em.find(RoomTypeEntity.class, roomTypeId);
        roomType.getRoom().remove(room);
        em.remove(room);

        // If room is already disabled, the room is already not in the inventory
        // Only update inventory if the room is not disabled
        if (room.getIsDisabled().equals(Boolean.FALSE)) {
            try {
                inventoryControllerLocal.updateAllInventory();
            } catch (UpdateInventoryException ex) {
                System.out.println("Error updating Inventory!");
            }
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

    public RoomEntity retrieveRoomById(Long id) throws RoomNotFoundException {

        Query query = em.createQuery("SELECT r FROM RoomEntity r WHERE r.roomId =:inId");
        query.setParameter("inId", id);

        try {

            return (RoomEntity) query.getSingleResult();

        } catch (NoResultException | NonUniqueResultException ex) {

            throw new RoomNotFoundException("Room " + id + "does not exist!");
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
/*    @Override
    public void walkInAllocateRoom(Long reservationId) throws ReservationNotFoundException {

        // Retrieve the current reservation
        ReservationEntity reservation;
        try {
            reservation = reservationEntityControllerLocal.retrieveReservationById(reservationId);
            List<ReservationLineItemEntity> reservationLineItems = reservation.getReservationLineItemEntities();
            Integer countOfRoomAvailableThroughout;
            Boolean availableThroughout;

            // Loop through all the reservationLineItem of currentReservation
            for (ReservationLineItemEntity reservationLineItem : reservationLineItems) {

                RoomTypeEntity roomType = reservationLineItem.getRoomType();

                List<RoomEntity> rooms = roomType.getRoom();

                countOfRoomAvailableThroughout = 0;

                // Loop through all the rooms of the specified roomType & check whether can fullfil the booking requirement
                // Need to check whether the next reservation start date is the same as current reservation end time --> if yes, change the status to allocate
                for (RoomEntity room : rooms) {

                    availableThroughout = Boolean.TRUE;

                    for (LocalDate date = reservation.getStartDate().toLocalDate(); !date.isAfter(reservation.getEndDate().toLocalDate()); date = date.plusDays(1)) {

                        if (room.getRoomStatus().equals(RoomStatus.MAINTENANCE)) {
                            availableThroughout = Boolean.FALSE;
                        }
//                    if (!room.getRoomStatus().equals(RoomStatus.VACANT)) {
//                        // If the current reservation end date is the same as new reservation start date, the room is considered available
//                        if (room.getRoomStatus().equals(RoomStatus.OCCUPIED) && room.getCurrentReservation().getEndDate().equals(reservation.getStartDate())) {
//
//                        } else {
//                            availableThroughout = Boolean.FALSE;
//                            break;
//                        }
//                    }
                    }
                    if (availableThroughout.equals(Boolean.TRUE)) {

                        countOfRoomAvailableThroughout++;
//                    room.setRoomStatus(RoomStatus.ALLOCATED); // should I change here??
                        // If the room if not occupied
                        if (room.getCurrentReservation() != null) {
                            room.setNextReservation(reservation);
                        } else {
                            room.setCurrentReservation(reservation);
                        }

                        reservation.getRooms().add(room);

                        // If all room in reservation has been successfully allocated, break
                        if (countOfRoomAvailableThroughout.equals(reservationLineItem.getNumOfRoomBooked())) {
                            break;
                        }
                    }
                }
                if (countOfRoomAvailableThroughout < reservationLineItem.getNumOfRoomBooked()) {
                    //throw unable to allocate all reserved room exception
                }
            }
        } catch (ReservationNotFoundException ex) {
            throw new ReservationNotFoundException("Reservation with id " + reservationId + " not found!");
        }

    }
*/
    @Override
    public Boolean checkIn(Long reservationId) throws ReservationNotFoundException {

        ReservationEntity reservation;
        try {
            reservation = reservationEntityControllerLocal.retrieveReservationById(reservationId);
            List<RoomEntity> rooms = reservation.getRooms();

            Boolean allRoomsReadyForCheckIn = Boolean.TRUE;

            for (RoomEntity room : rooms) {
                // If any of the rooms reserved is not ready for check in
                if (room.getIsReady().equals(Boolean.FALSE)) {
                    allRoomsReadyForCheckIn = Boolean.FALSE;
                    break;
                }
            }
// Should we split out and show which room is available and which room is not??       
            if (allRoomsReadyForCheckIn.equals(Boolean.TRUE)) {
                for (RoomEntity room : rooms) {
                    if (room.getIsReady().equals(Boolean.TRUE)) {
                        room.setGuest(((WalkinReservationEntity) reservation).getGuestFirstName() + ((WalkinReservationEntity) reservation).getGuestLastName());
                        room.setRoomStatus(RoomStatus.OCCUPIED);
                    }
                }
                reservationEntityControllerLocal.retrieveReservationById(reservationId).setIsCheckedIn(Boolean.TRUE);
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } catch (ReservationNotFoundException ex) {
            throw new ReservationNotFoundException("Reservation with id " + reservationId + " not found!");
        }

    }

    @Override
    public Boolean checkOut(String roomNumber) throws RoomNotFoundException, ReservationNotFoundException {

        RoomEntity room = retrieveRoomByRoomNumber(roomNumber);

        if (room.getRoomStatus().equals(RoomStatus.OCCUPIED)) {
            try {
                ReservationEntity reservation = reservationEntityControllerLocal.retrieveReservationById(room.getCurrentReservation().getReservationId());
                reservationEntityControllerLocal.retrieveReservationById(room.getCurrentReservation().getReservationId()).setIsCheckedIn(Boolean.FALSE);

                if (room.getNextReservation() != null) {
                    room.setCurrentReservation(room.getNextReservation());
                    room.setNextReservation(null);
                    room.setRoomStatus(RoomStatus.ALLOCATED);
                } else {
                    room.setCurrentReservation(null);
                    room.setRoomStatus(RoomStatus.VACANT);
                }
                //  room.getCurrentReservation().getRooms().remove(room);

                room.setIsReady(Boolean.FALSE);
                room.setGuest(null);
                return Boolean.TRUE;
            } catch (ReservationNotFoundException ex) {
                return Boolean.FALSE;
            }

        } else {
            return Boolean.FALSE;
        }
    }
}
