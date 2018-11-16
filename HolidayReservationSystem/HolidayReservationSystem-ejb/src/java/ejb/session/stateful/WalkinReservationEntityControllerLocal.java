/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import entity.ReservationEntity;
import entity.ReservationLineItemEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Asus
 */
public interface WalkinReservationEntityControllerLocal {
    
    public List<ReservationEntity> retrieveReservationByStartAndEndDate(LocalDate bookingStartDate, LocalDate bookingEndDate);

    public ReservationEntity checkOut(Long employeeId, LocalDate startDate, LocalDate endDate, String guestFirstName, String guestLastName, String guestIdentificationNumber, String guestContactNumber, String guestEmail) throws Exception;
}
