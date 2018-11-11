/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import entity.ReservationEntity;
import java.time.LocalDate;
import util.exception.RoomTypeNotFoundException;



/**
 *
 * @author Asus
 */

public interface WalkinReservationEntityControllerRemote {
    
    public ReservationEntity checkOut(Long employeeId, LocalDate startDate, LocalDate endDate, String guestFirstName, String guestLastName, String guestIdentificationNumber, String guestContactNumber, String guestEmail) throws Exception;

    public void reserveRoom(String roomTypeName, LocalDate startDate, LocalDate endDate, Integer numOfRoomRequired) throws RoomTypeNotFoundException;
}
