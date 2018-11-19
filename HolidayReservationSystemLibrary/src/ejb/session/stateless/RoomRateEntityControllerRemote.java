/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomRateEntity;
import entity.RoomTypeEntity;
import java.util.List;
import util.exception.CreateNewRoomRateException;
import util.exception.RoomRateNotFoundException;

/**
 *
 * @author twp10
 */
public interface RoomRateEntityControllerRemote {

    public RoomRateEntity retrieveRoomRateByName(String name, String roomTypeName) throws RoomRateNotFoundException;

    public void disableRoomRate(Long id);

    public void deleteRoomRate(Long id);

    public List<RoomRateEntity> viewAllRoomRate();

    public void updateRoomRate(RoomRateEntity roomRate, Long id);

    public RoomRateEntity createNewRoomRate(RoomRateEntity newRoomRate, Long id) throws CreateNewRoomRateException;
    
}
