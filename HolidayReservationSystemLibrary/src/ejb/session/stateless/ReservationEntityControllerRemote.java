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
import util.exception.ReservationLineItemNotFoundException;
import util.exception.ReservationNotFoundException;

/**
 *
 * @author twp10
 */
@Remote
public interface ReservationEntityControllerRemote {
 
    public ReservationEntity retrieveReservationById(Long reservationId) throws ReservationNotFoundException;

    public List<RoomEntity> retrieveRoomsByReservationId(Long reservationId) throws ReservationNotFoundException;

    public List<ReservationLineItemEntity> retrieveFirstException() throws ReservationLineItemNotFoundException;

    public List<ReservationLineItemEntity> retrieveItemsByReservationId(Long reservationId) throws ReservationNotFoundException;

    public List<ReservationLineItemEntity> retrieveSecondException() throws ReservationLineItemNotFoundException;
}
