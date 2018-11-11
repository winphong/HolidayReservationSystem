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
import java.time.LocalDate;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import util.enumeration.RoomStatus;

/**
 *
 * @author twp10
 */
@Stateless
public class EjbTimerSessionBean implements EjbTimerSessionBeanRemote, EjbTimerSessionBeanLocal {

    @EJB
    private ReservationEntityControllerLocal reservationEntityControllerLocal;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Schedule(hour = "2", info = "scheduleEveryDay2AM")
    public void allocateRoom() {
        
        LocalDate currentDate = LocalDate.now();
        
        // Reservation for current date
        List<ReservationEntity> reservations = reservationEntityControllerLocal.retrieveReservationByDateOrderByDescEndDate(currentDate);
        Boolean availableThroughout;
        Integer countOfRoomAvailableThroughout;
        
        // Loop through all reservations for current date
        for(ReservationEntity reservation : reservations) {
            
            List<ReservationLineItemEntity> reservationLineItems = reservation.getReservationLineItemEntities();
            
            // Loop through all line item for current reservation
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
                        
                        room.setRoomStatus(RoomStatus.ALLOCATED);
                        
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
}
