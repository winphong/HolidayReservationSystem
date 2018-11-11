/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import entity.GuestEntity;
import entity.OnlineReservationEntity;
import entity.ReservationEntity;
import java.time.LocalDate;
import java.util.List;
import util.exception.ReservationNotFoundException;
import util.exception.RoomTypeNotFoundException;

/**
 *
 * @author Asus
 */
public interface OnlineReservationEntityControllerRemote {
    
    public OnlineReservationEntity retrieveReservationById(Long id) throws ReservationNotFoundException;

    public List<OnlineReservationEntity> viewAllReservationsOfGuest(GuestEntity guest);

    public void reserveRoom(String roomTypeName, LocalDate startDate, LocalDate endDate, Integer numOfRoomRequired) throws RoomTypeNotFoundException, Exception;

    public ReservationEntity checkOut(Long guestId, LocalDate startDate, LocalDate endDate) throws Exception;
    
}
