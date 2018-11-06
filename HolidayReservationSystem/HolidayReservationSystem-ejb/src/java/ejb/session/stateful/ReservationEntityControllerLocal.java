/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import entity.ReservationEntity;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Asus
 */
public interface ReservationEntityControllerLocal {
    public List<ReservationEntity> retrieveReservationByStartAndEndDate(LocalDate bookingStartDate, LocalDate bookingEndDate);
}
