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
        Integer countOfRoomAvailableThroughout;

        // Loop through all reservations for current date
        for (ReservationEntity reservation : reservations) {

            List<ReservationLineItemEntity> reservationLineItems = reservation.getReservationLineItemEntities();

            // Loop through all line item for current reservation
            for (ReservationLineItemEntity reservationLineItem : reservationLineItems) {

                RoomTypeEntity roomType = reservationLineItem.getRoomType();
                List<RoomEntity> rooms = roomType.getRoom();
                countOfRoomAvailableThroughout = 0;

                // Loop through all the rooms of the specified roomType & check whether can fullfil the booking requirement
                // Need to check whether the next reservation start date is the same as current reservation end time --> if yes, change the status to allocate
                for (RoomEntity room : rooms) {
                    availableThroughout = Boolean.TRUE;
                    for (LocalDate date = reservation.getStartDate().toLocalDate(); !date.isAfter(reservation.getEndDate().toLocalDate()); date = date.plusDays(1)) {
                        if (!room.getRoomStatus().equals(RoomStatus.VACANT)) {
                            // If the current reservation end date is the same as new reservation start date, the room is considered available
                            if (room.getRoomStatus().equals(RoomStatus.OCCUPIED) && room.getCurrentReservation().getEndDate().equals(reservation.getStartDate())) {
                                // continue;
                            } else {
                                availableThroughout = Boolean.FALSE;
                                break;
                            }
                        }
                    }
                    if (availableThroughout.equals(Boolean.TRUE)) {

                        countOfRoomAvailableThroughout++;
//                        room.setRoomStatus(RoomStatus.ALLOCATED);
                        // If the room if not occupied
                        if (room.getCurrentReservation() != null) {
                            room.setNextReservation(reservation);
                        } else {
                            room.setCurrentReservation(reservation);
                        }

                        reservation.getRooms().add(room);
                        if (reservation.getIsNotAllocated() == true && reservation.getIsUpgraded() == false) {
                            reservation.setIsUpgraded(true);
                        }
                        // If all room in reservation has been successfully allocated, break
                        if (countOfRoomAvailableThroughout.equals(reservationLineItem.getNumOfRoomBooked())) {
                            break;
                        }
                    }
                }
                if (countOfRoomAvailableThroughout < reservationLineItem.getNumOfRoomBooked()) {
                    try {
                        reservation.setIsNotAllocated(true);
                        Boolean upgraded = false;
                        upgraded = upgradeRoomType(reservationLineItem.getReservationLineItemId());

                        if (upgraded) {
                            reallocateRoom(reservation.getReservationId());
                        }
                        else{
                            break;
                        }
                    } catch (ReservationLineItemNotFoundException ex) {
                        System.out.println("Reservation Line Item with id " + reservationLineItem.getReservationLineItemId() + " not found!");
                    } catch (RoomTypeNotFoundException ex) {
                        System.out.println("Error: Room type not found " + ex.getMessage());
                    }
                }
            }
        }
    }

    @Schedule(hour = "14", info = "scheduleEveryday2PM")
    public void finishUpHousekeeping() {

        List<RoomTypeEntity> roomTypes = roomTypeEntityControllerLocal.retrieveAllRoomType();

        for (RoomTypeEntity roomType : roomTypes) {

            List<RoomEntity> rooms = roomType.getRoom();

            for (RoomEntity room : rooms) {

                if (room.getIsDisabled().equals(Boolean.FALSE) && room.getRoomStatus().equals(RoomStatus.VACANT)) {
                    room.setIsReady(Boolean.TRUE);
                }
            }
        }
    }

    @Schedule(hour = "0", info = "scheduleEveryday12AM")
    public void createNewInventory() {

        Inventory inventory = new Inventory(Date.valueOf(LocalDate.now().plusDays(5)));
        try {
            inventoryControllerLocal.updateAllInventory();
        } catch (UpdateInventoryException ex) {
            System.out.println("Error updating Inventory!");
        }
        em.persist(inventory);
        em.flush();
    }

    public Boolean upgradeRoomType(Long id) throws ReservationLineItemNotFoundException, RoomTypeNotFoundException {
        try {
            ReservationLineItemEntity item = reservationEntityControllerLocal.retrieveItemById(id);
            int tier = item.getRoomType().getTier();
            if (roomTypeEntityControllerLocal.retrieveRoomTypeByTier(tier + 1) != null) {
                return true;
            } else {
                return false;
            }
        } catch (ReservationLineItemNotFoundException ex) {
            throw new ReservationLineItemNotFoundException("Error retrieving item: " + ex.getMessage());
        } catch (RoomTypeNotFoundException ex) {
            throw new RoomTypeNotFoundException("Error retrieving item: " + ex.getMessage());
        }
    }

    public void reallocateRoom(Long reservationId) {
        try {
            ReservationEntity reservation = reservationEntityControllerLocal.retrieveReservationById(reservationId);
            List<ReservationLineItemEntity> reservationLineItems = reservation.getReservationLineItemEntities();

            // Loop through all line item for current reservation
            for (ReservationLineItemEntity reservationLineItem : reservationLineItems) {

                RoomTypeEntity roomType = reservationLineItem.getRoomType();
                List<RoomEntity> rooms = roomType.getRoom();
                Integer countOfRoomAvailableThroughout = 0;
                Boolean availableThroughout;

                // Loop through all the rooms of the specified roomType & check whether can fullfil the booking requirement
                // Need to check whether the next reservation start date is the same as current reservation end time --> if yes, change the status to allocate
                for (RoomEntity room : rooms) {
                    availableThroughout = Boolean.TRUE;
                    for (LocalDate date = reservation.getStartDate().toLocalDate(); !date.isAfter(reservation.getEndDate().toLocalDate()); date = date.plusDays(1)) {
                        if (!room.getRoomStatus().equals(RoomStatus.VACANT)) {
                            // If the current reservation end date is the same as new reservation start date, the room is considered available
                            if (room.getRoomStatus().equals(RoomStatus.OCCUPIED) && room.getCurrentReservation().getEndDate().equals(reservation.getStartDate())) {
                                // continue;
                            } else {
                                availableThroughout = Boolean.FALSE;
                                break;
                            }
                        }
                    }
                    if (availableThroughout.equals(Boolean.TRUE)) {

                        countOfRoomAvailableThroughout++;
//                        room.setRoomStatus(RoomStatus.ALLOCATED);
// If the room if not occupied
                        if (room.getCurrentReservation() != null) {
                            room.setNextReservation(reservation);
                        } else {
                            room.setCurrentReservation(reservation);
                        }

                        reservation.getRooms().add(room);
                        if (reservation.getIsNotAllocated() == true && reservation.getIsUpgraded() == false) {
                            reservation.setIsUpgraded(true);
                        }
// If all room in reservation has been successfully allocated, break
                        if (countOfRoomAvailableThroughout.equals(reservationLineItem.getNumOfRoomBooked())) {
                            break;
                        }
                    }
                }
                if (countOfRoomAvailableThroughout < reservationLineItem.getNumOfRoomBooked()) {
                    try {
                        reservation.setIsNotAllocated(true);
                        Boolean upgraded = false;
                        upgraded = upgradeRoomType(reservationLineItem.getReservationLineItemId());

                        if (upgraded) {
                            reallocateRoom(reservation.getReservationId());
                        }
                    } catch (ReservationLineItemNotFoundException ex) {
                        System.out.println("Reservation Line Item with id " + reservationLineItem.getReservationLineItemId() + " not found!");
                    } catch (RoomTypeNotFoundException ex) {
                        System.out.println("Error: Room type not found " + ex.getMessage());
                    }
                }
            }
        } catch (ReservationNotFoundException ex) {
            System.out.println("Reservation id " + reservationId + " not found!");
        }
    }
}
