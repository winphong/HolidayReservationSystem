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
import entity.RoomTypeEntity;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.RoomStatus;
import util.exception.ReservationNotFoundException;
import util.exception.RoomTypeNotFoundException;
import util.exception.UpdateInventoryException;

/**
 *
 * @author twp10
 */
@Stateless
public class EjbTimerSessionBean implements EjbTimerSessionBeanRemote, EjbTimerSessionBeanLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    @EJB
    private RoomTypeEntityControllerLocal roomTypeEntityControllerLocal;
    @EJB
    private ReservationEntityControllerLocal reservationEntityControllerLocal;
    @EJB
    private InventoryControllerLocal inventoryControllerLocal;

    public EjbTimerSessionBean() {
    }

    @Schedule(hour = "2", info = "AllocatRoomEverydayAt2AM")
    @Override
    public void allocateRoom() throws ReservationNotFoundException, RoomTypeNotFoundException, Exception {

        LocalDate currentDate = LocalDate.now();
        // Get list of reservations for current date
        List<ReservationEntity> reservations = reservationEntityControllerLocal.retrieveReservationByDateOrderByDescEndDate(currentDate);
        Boolean availableThroughout;
        
        // Loop through all reservations for current date
        for (ReservationEntity reservation : reservations) {
            
            // If reservation's all room has already been allocated, break            
            if (reservation.getIsAllocated()) {
                continue;
            }

            List<ReservationLineItemEntity> reservationLineItems = reservation.getReservationLineItemEntities();

            // Loop through all line item for current reservation
            for(ReservationLineItemEntity reservationLineItem : reservationLineItems) {
                
                // If the someone is already staying in the room, what for you allocate
                if ( reservation.getIsCheckedIn().equals(Boolean.TRUE) ) {
                    break;
                }
                
                RoomTypeEntity roomType = reservationLineItem.getRoomType();                

                List<RoomEntity> rooms = roomType.getRoom();

                // Loop through all the rooms of the specified roomType & check whether can fullfil the booking requirement
                // Need to check whether the next reservation start date is the same as current reservation end time --> if yes, change the status to allocate
                for(RoomEntity room : rooms) {
                    
                    // If the room is already allocated, skip to the next room
                    if ( room.getRoomStatus().equals(RoomStatus.ALLOCATED) ) {
                        continue;
                    }
  
                    availableThroughout = Boolean.TRUE;
                    
                    for(LocalDate date = reservation.getStartDate().toLocalDate(); !date.isAfter(reservation.getEndDate().toLocalDate()) ; date = date.plusDays(1)) {                       
                        
                        if ( room.getRoomStatus().equals(RoomStatus.MAINTENANCE) 
                                || room.getRoomStatus().equals(RoomStatus.OCCUPIED) && !room.getCurrentReservation().getEndDate().equals(reservation.getStartDate()) || room.getIsDisabled().equals(Boolean.TRUE)) {
                            
                            availableThroughout = Boolean.FALSE;
                            break;

                        }
                    }
                    if (availableThroughout.equals(Boolean.TRUE)) {

                        room.setRoomStatus(RoomStatus.ALLOCATED);
                        
                        if( reservationLineItem.getIsUpgraded() ) {
                            reservationLineItem.setNumOfSuccesfulUpgrade(reservationLineItem.getNumOfSuccesfulUpgrade() + 1);
                        } else {
                            reservationLineItem.setNumOfSuccesfulNormalAllocation(reservationLineItem.getNumOfSuccesfulNormalAllocation() + 1);
                        }
                        // If the room if not occupied
                        if (room.getCurrentReservation() != null) {
                            room.setNextReservation(reservation);
                        } else {
                            room.setCurrentReservation(reservation);
                        }

                        reservation.getRooms().add(room);
//                        
//                        if (reservation.getIsNotAllocated() == true && reservation.getIsUpgraded() == false) {
//                            reservation.setIsUpgraded(true);
//                        }
                        // If all room in reservation has been successfully allocated, break
                        if ( (reservationLineItem.getNumOfSuccesfulNormalAllocation() + reservationLineItem.getNumOfSuccesfulUpgrade()) == reservationLineItem.getNumOfRoomBooked() ) {
                            reservationLineItem.setIsAllocated(Boolean.TRUE);
                            reservation.setIsAllocated(Boolean.TRUE);
                            break;
                        }
                    }
                }
                
                if (((Integer)reservation.getRooms().size()) < reservationLineItem.getNumOfRoomBooked()) {
                    
                    reservationLineItem.setIsUpgraded(Boolean.TRUE);
                
                    if ( !reservationLineItem.getRoomType().getTier().equals(roomTypeEntityControllerLocal.retrieveHighestRoomTier()) ) {

                        reservationLineItem.setRoomType(roomTypeEntityControllerLocal.retrieveRoomTypeByTier(reservationLineItem.getRoomType().getTier() + 1));
                        allocateRoom(reservation.getReservationId());

                    } else if ( reservationLineItem.getRoomType().getTier().equals(roomTypeEntityControllerLocal.retrieveHighestRoomTier()) ) {

                        reservationLineItem.setNumOfFailureUpgrade(reservationLineItem.getNumOfRoomBooked() - reservationLineItem.getNumOfSuccesfulUpgrade() - reservationLineItem.getNumOfSuccesfulNormalAllocation());
                        throw new Exception("Not all room of the reservation line item has been allocated");
                    }
                }
            }        
        }
    }
    
                            //***********This is for upgrading allocation of room*****************//
    @Override
    public void allocateRoom(Long reservationId) throws ReservationNotFoundException, RoomTypeNotFoundException, Exception {
        
        ReservationEntity reservation = null;
        
        try {
            reservation = reservationEntityControllerLocal.retrieveReservationById(reservationId);
        } catch (ReservationNotFoundException ex) {
            throw new ReservationNotFoundException("Not found");
        }
        
        List<ReservationLineItemEntity> reservationLineItems = reservation.getReservationLineItemEntities();
        // Loop through all line item for current reservation
        for (ReservationLineItemEntity reservationLineItem : reservationLineItems) {
            
            if ( reservationLineItem.getIsAllocated() ) {
                continue;
            }
            
            RoomTypeEntity roomType = reservationLineItem.getRoomType();
            List<RoomEntity> rooms = roomType.getRoom();
            Boolean availableThroughout;
            
            // Loop through all the rooms of the specified roomType & check whether can fullfil the booking requirement
            // Need to check whether the next reservation start date is the same as current reservation end time --> if yes, change the status to allocate
            for (RoomEntity room : rooms) {
                
                if ( room.getRoomStatus().equals(RoomStatus.ALLOCATED) ) {
                    continue;
                }
                
                availableThroughout = Boolean.TRUE;
                
                for(LocalDate date = reservation.getStartDate().toLocalDate(); !date.isAfter(reservation.getEndDate().toLocalDate()) ; date = date.plusDays(1)) {
                    
                    if ( room.getRoomStatus().equals(RoomStatus.MAINTENANCE)
                            || room.getRoomStatus().equals(RoomStatus.OCCUPIED) && !room.getCurrentReservation().getEndDate().equals(reservation.getStartDate()) || room.getIsDisabled().equals(Boolean.TRUE)) {                       
                        
                        availableThroughout = Boolean.FALSE;
                        break;
                        
                    }
                }
                
                if (availableThroughout.equals(Boolean.TRUE)) {
                    
                    room.setRoomStatus(RoomStatus.ALLOCATED);
                    
                    if( reservationLineItem.getIsUpgraded() ) {
                        reservationLineItem.setNumOfSuccesfulUpgrade(reservationLineItem.getNumOfSuccesfulUpgrade() + 1);
                    } else {
                        reservationLineItem.setNumOfSuccesfulNormalAllocation(reservationLineItem.getNumOfSuccesfulNormalAllocation() + 1);
                    }
            // If the room is not occupied
                    if (room.getCurrentReservation() != null) {
                        room.setNextReservation(reservation);
                    } else {
                        room.setCurrentReservation(reservation);
                    }

                    reservation.getRooms().add(room);
            
            // If all room in reservation has been successfully allocated, break
                    if (  (reservationLineItem.getNumOfSuccesfulNormalAllocation() + reservationLineItem.getNumOfSuccesfulUpgrade()) == reservationLineItem.getNumOfRoomBooked() ) {
                        reservationLineItem.setIsAllocated(Boolean.TRUE);
                        reservation.setIsAllocated(Boolean.TRUE);
                        break;
                    }
                }
            }
            
            if ( ((Integer)reservation.getRooms().size()) < reservationLineItem.getNumOfRoomBooked()) {
                
                reservationLineItem.setIsUpgraded(Boolean.TRUE);
                
                if ( !reservationLineItem.getRoomType().getTier().equals(roomTypeEntityControllerLocal.retrieveHighestRoomTier()) ) {
                    
                    reservationLineItem.setRoomType(roomTypeEntityControllerLocal.retrieveRoomTypeByTier(reservationLineItem.getRoomType().getTier() + 1));
                    allocateRoom(reservation.getReservationId());
                    
                } else if ( reservationLineItem.getRoomType().getTier().equals(roomTypeEntityControllerLocal.retrieveHighestRoomTier()) ) {
                    
                    reservationLineItem.setNumOfFailureUpgrade(reservationLineItem.getNumOfRoomBooked() - reservationLineItem.getNumOfSuccesfulUpgrade() - reservationLineItem.getNumOfSuccesfulNormalAllocation());
                    throw new Exception("Not all room of the reservation line item has been allocated");
                }
            }
        }
    }

    @Schedule(hour = "14", info = "makeAllRoomReadyAt2PM")
    @Override
    public void finishUpHousekeeping() {

        List<RoomTypeEntity> roomTypes = roomTypeEntityControllerLocal.retrieveAllRoomType();

        for (RoomTypeEntity roomType : roomTypes) {

            List<RoomEntity> rooms = roomType.getRoom();

            for (RoomEntity room : rooms) {

                if (room.getIsDisabled().equals(Boolean.FALSE) && ( room.getRoomStatus().equals(RoomStatus.VACANT) || room.getRoomStatus().equals(RoomStatus.ALLOCATED)) ) {
                    room.setIsReady(Boolean.TRUE);
                }
            }
        }
    }

    @Schedule(hour = "0", info = "createNewInventoryEverydayAt12AM")
    public void createNewInventory() {

        Inventory inventory = new Inventory(Date.valueOf(LocalDate.now().plusYears(1).plusDays(1)));
        em.persist(inventory);
        em.flush();
        
        try {
            inventoryControllerLocal.updateAllInventory();
        } catch (UpdateInventoryException ex) {
            System.out.println("Error updating Inventory!");
        }
        
    }
}
