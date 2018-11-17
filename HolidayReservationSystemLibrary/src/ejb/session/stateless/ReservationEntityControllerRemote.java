/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ReservationEntity;
import entity.ReservationLineItemEntity;
import entity.RoomEntity;
import java.util.List;
import javax.ejb.Remote;
import util.exception.CheckInException;
import util.exception.ReservationNotFoundException;

/**
 *
 * @author twp10
 */
@Remote
public interface ReservationEntityControllerRemote {
 
    public ReservationEntity retrieveReservationById(Long reservationId) throws ReservationNotFoundException;

    public List<RoomEntity> retrieveRoomsByReservation(Long reservationId) throws ReservationNotFoundException;

    public Boolean checkIn(Long reservationId, String guest) throws CheckInException, ReservationNotFoundException;

    public List<ReservationEntity> retrieveFirstException() throws ReservationNotFoundException;

    public List<ReservationLineItemEntity> retrieveItemsByReservation(Long id) throws ReservationNotFoundException;

    public List<ReservationEntity> retrieveSecondException() throws ReservationNotFoundException;
}
