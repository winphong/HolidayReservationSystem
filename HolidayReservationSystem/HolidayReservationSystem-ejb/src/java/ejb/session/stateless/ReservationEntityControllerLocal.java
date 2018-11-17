/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ReservationEntity;
import entity.ReservationLineItemEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.Local;
import util.exception.ReservationLineItemNotFoundException;
import util.exception.ReservationNotFoundException;

/**
 *
 * @author twp10
 */
@Local
public interface ReservationEntityControllerLocal {

    public ReservationEntity createReservation(String identity, Long Id, List<ReservationLineItemEntity> reservationLineItem, BigDecimal totalAmount, ReservationEntity newReservationEntity) throws Exception;

    public List<ReservationEntity> retrieveReservationByDateOrderByDescEndDate(LocalDate date);

    public ReservationEntity retrieveReservationById(Long reservationId) throws ReservationNotFoundException;

    public ReservationLineItemEntity retrieveItemById(Long id) throws ReservationLineItemNotFoundException;
}
