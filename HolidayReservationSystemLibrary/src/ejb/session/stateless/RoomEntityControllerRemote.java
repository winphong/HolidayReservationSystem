/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomEntity;
import java.util.List;
import util.exception.CreateNewRoomException;
import util.exception.ReservationNotFoundException;
import util.exception.RoomNotFoundException;

/**
 *
 * @author twp10
 */
public interface RoomEntityControllerRemote {

    public RoomEntity createNewRoom(RoomEntity newRoom, Long id) throws CreateNewRoomException;

    public void updateRoom(RoomEntity room, String roomNumber);

    public void disableRoom(Long id);

    public void deleteRoom(Long roomId, Long roomTypeId);

    public RoomEntity retrieveRoomByRoomNumber(String roomNumber) throws RoomNotFoundException;

    public List<RoomEntity> viewAllRoom();

    public void walkInAllocateRoom(Long reservationId) throws ReservationNotFoundException;

    public Boolean checkIn(Long reservationId) throws ReservationNotFoundException;

    public Boolean checkOut(String roomNumber) throws RoomNotFoundException,ReservationNotFoundException;
    
}
