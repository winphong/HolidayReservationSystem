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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.RoomStatus;
import util.exception.ReservationLineItemNotFoundException;
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

    @Schedule(hour = "2", info = "scheduleEveryday2AM")
    @Override
    public void allocateRoom() {

        LocalDate currentDate = LocalDate.now();
        // Get list of reservations for current date
        List<ReservationEntity> reservations = reservationEntityControllerLocal.retrieveReservationByDateOrderByDescEndDate(currentDate);
        Boolean availableThroughout;
        //Integer countOfRoomAvailableThroughout;

        // Loop through all reservations for current date
        for (ReservationEntity reservation : reservations) {
            
            // If reservation's all room has already been allocated, break
//            List<RoomEntity> listOfRooms = reservation.getRooms();
//            Boolean breakTheLoop = Boolean.FALSE;
            
            if (reservation.getIsNotAllocated().equals(Boolean.FALSE)) {
                continue;
            }
            
//            for(RoomEntity room : listOfRooms) {
//                if ( room.getRoomStatus().equals(RoomStatus.ALLOCATED) ) {
//                    breakTheLoop = Boolean.TRUE;
//                }
//            }
//            
//            if (breakTheLoop) {
//                break;
//            }

            List<ReservationLineItemEntity> reservationLineItems = reservation.getReservationLineItemEntities();

            // Loop through all line item for current reservation
            for(ReservationLineItemEntity reservationLineItem : reservationLineItems) {
                
                // If the someone is already staying in the room, what for you allocate
                if ( reservation.getIsCheckedIn().equals(Boolean.TRUE) ) {
                    break;
                }
                
                RoomTypeEntity roomType = reservationLineItem.getRoomType();                

                List<RoomEntity> rooms = roomType.getRoom();
               //countOfRoomAvailableThroughout = 0;

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
                                || room.getRoomStatus().equals(RoomStatus.OCCUPIED) && !room.getCurrentReservation().getEndDate().equals(reservation.getStartDate())) {
                            
                            availableThroughout = Boolean.FALSE;
                            break;

                        }
                    }
                    if (availableThroughout.equals(Boolean.TRUE)) {

                        //countOfRoomAvailableThroughout++;
                        room.setRoomStatus(RoomStatus.ALLOCATED);
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
                        if ( ((Integer)reservation.getRooms().size()).equals(reservationLineItem.getNumOfRoomBooked()) ) {
                            reservationLineItem.setIsAllocated(Boolean.TRUE);
                            reservation.setIsNotAllocated(Boolean.FALSE);
                            break;
                        }
                    }
                }
                
                if (((Integer)reservation.getRooms().size()) < reservationLineItem.getNumOfRoomBooked()) {
                    
                    try {
                        //reservation.setIsNotAllocated(Boolean.TRUE); -- by default is already TRUE
                        upgradeRoomType(reservationLineItem);
                        allocateRoom(reservation.getReservationId());
                        
                    } catch (ReservationLineItemNotFoundException ex) {
                        System.out.println("Reservation Line Item with id " + reservationLineItem.getReservationLineItemId() + " not found!");
                    } catch (RoomTypeNotFoundException ex) {
                        System.out.println("Error: Room type not found " + ex.getMessage());
                    } catch (ReservationNotFoundException ex) {
                        System.out.println("Error: Reservation not found " + ex.getMessage());
                    }
                }
            }
        }
    }

    @Schedule(hour = "14", info = "scheduleEveryday2PM")
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

    @Schedule(hour = "0", info = "scheduleEveryday12AM")
    public void createNewInventory() {

        Inventory inventory = new Inventory(Date.valueOf(LocalDate.now().plusDays(7)));
        try {
            inventoryControllerLocal.updateAllInventory();
        } catch (UpdateInventoryException ex) {
            System.out.println("Error updating Inventory!");
        }
        em.persist(inventory);
        em.flush();
    }

    public void upgradeRoomType(ReservationLineItemEntity reservationLineItem) throws ReservationLineItemNotFoundException, RoomTypeNotFoundException {
        
        try {
            
            Integer tier = reservationLineItem.getRoomType().getTier();
            
            RoomTypeEntity nextHigherTierRoom = roomTypeEntityControllerLocal.retrieveRoomTypeByTier(tier + 1);
            
            reservationLineItem.setRoomType(nextHigherTierRoom);
            
        } catch (RoomTypeNotFoundException ex) {
            throw new RoomTypeNotFoundException("Error retrieving item: " + ex.getMessage());
        }
    }

                            //***********This is for upgrading allocation of room*****************//
    @Override
    public void allocateRoom(Long reservationId) throws ReservationNotFoundException {
        
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
            //Integer countOfRoomAvailableThroughout = 0;
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
                            || room.getRoomStatus().equals(RoomStatus.OCCUPIED) && !room.getCurrentReservation().getEndDate().equals(reservation.getStartDate())) {                       
                        
                        availableThroughout = Boolean.FALSE;
                        break;
                        
                    }
                }
                
                if (availableThroughout.equals(Boolean.TRUE)) {
                    
                    //countOfRoomAvailableThroughout++;
                    room.setRoomStatus(RoomStatus.ALLOCATED);
                   
// If the room if not occupied
                    if (room.getCurrentReservation() != null) {
                        room.setNextReservation(reservation);
                    } else {
                        room.setCurrentReservation(reservation);
                    }

                    reservation.getRooms().add(room);
            
// If all room in reservation has been successfully allocated, break
                    if ( ((Integer)reservation.getRooms().size()).equals(reservationLineItem.getNumOfRoomBooked())) {
                        reservationLineItem.setIsAllocated(Boolean.TRUE);
                        reservation.setIsNotAllocated(Boolean.FALSE);
                        break;
                    }
                }
            }
            
            if ( ((Integer)reservation.getRooms().size()) < reservationLineItem.getNumOfRoomBooked()) {
                try {
                    
                    upgradeRoomType(reservationLineItem);
                    allocateRoom(reservation.getReservationId());

                } catch (ReservationLineItemNotFoundException ex) {
                    System.out.println("Reservation Line Item with id " + reservationLineItem.getReservationLineItemId() + " not found!");
                } catch (RoomTypeNotFoundException ex) {
                    System.out.println("Error: Room type not found " + ex.getMessage());
                }
            }
        }
    }
}
