/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomTypeEntity;
import java.util.List;
import util.exception.RoomTypeNotFoundException;

/**
 *
 * @author twp10
 */

public interface RoomTypeEntityControllerLocal {

    public List<RoomTypeEntity> retrieveAllRoomType();
    
    public RoomTypeEntity retrieveRoomTypeByName(String name)throws RoomTypeNotFoundException;
    
    public RoomTypeEntity retrieveRoomTypeById(Long id)throws RoomTypeNotFoundException;

    public RoomTypeEntity retrieveRoomTypeByTier(int tier) throws RoomTypeNotFoundException;
}
