/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomRateEntity;
import entity.RoomTypeEntity;
import java.util.List;
import util.exception.RoomRateNotFoundException;

/**
 *
 * @author twp10
 */
public interface RoomRateEntityControllerRemote {

    public RoomRateEntity createNewRoomRate(RoomRateEntity newRoomRate, RoomTypeEntity roomType);

    public RoomRateEntity retrieveRoomRateByName(String name) throws RoomRateNotFoundException;

    public void updateRoomRate(RoomRateEntity roomRate);

    public void disableRoomRate(RoomRateEntity roomRate);

    public void deleteRoomRate(RoomRateEntity roomRate);

    public List<RoomRateEntity> viewAllRoomRate();
    
}
