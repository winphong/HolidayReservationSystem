/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ReservationEntity;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author twp10
 */
@Local
public interface ReservationEntityControllerLocal {

    public ReservationEntity createReservation(String identity, Long Id, ReservationEntity newReservationEntity) throws Exception;

    public List<ReservationEntity> retrieveReservationByDateOrderByDescEndDate(LocalDate date);

    public ReservationEntity retrieveReservationById(Long reservationId);
}