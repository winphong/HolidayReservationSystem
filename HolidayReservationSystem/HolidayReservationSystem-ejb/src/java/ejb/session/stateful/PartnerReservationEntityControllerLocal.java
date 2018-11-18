/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import entity.PartnerReservationEntity;
import entity.ReservationEntity;
import java.time.LocalDate;
import util.exception.ReservationNotFoundException;
import util.exception.RoomTypeNotFoundException;

/**
 *
 * @author Asus
 */

public interface PartnerReservationEntityControllerLocal {
    public PartnerReservationEntity retrieveReservationById(Long id) throws ReservationNotFoundException;

    public ReservationEntity checkOut(Long partnerId, String customerFirstName, String customerLastName, String customerIdentificationNumber, String customerContactNumber, String customerEmail, String startDate, String endDate) throws Exception;

    public Boolean reserveRoom(String roomTypeName, String startDate, String endDate, Integer numOfRoomRequired) throws RoomTypeNotFoundException, Exception;
}
