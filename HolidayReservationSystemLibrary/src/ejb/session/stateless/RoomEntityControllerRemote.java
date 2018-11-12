/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomEntity;
import entity.RoomNumber;
import entity.RoomTypeEntity;
import java.util.List;
import util.exception.RoomNotFoundException;

/**
 *
 * @author twp10
 */
public interface RoomEntityControllerRemote {

    public RoomEntity createNewRoom(RoomEntity newRoom, RoomTypeEntity roomType);

    public void updateRoom(RoomEntity room);

    public void disableRoom(RoomEntity room);

    public void deleteRoom(RoomEntity room);

    public RoomEntity retrieveRoomByRoomNumber(String roomNumber) throws RoomNotFoundException;

    public List<RoomEntity> viewAllRoom();

    public void walkInAllocateRoom(Long reservationId);
    
}
