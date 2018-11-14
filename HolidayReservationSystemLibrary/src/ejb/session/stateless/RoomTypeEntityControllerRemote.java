/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomTypeEntity;
import java.util.List;
import util.exception.RoomTypeNotFoundException;
import util.exception.UpdateInventoryException;

/**
 *
 * @author twp10
 */
public interface RoomTypeEntityControllerRemote {

    public RoomTypeEntity createNewRoomType(RoomTypeEntity newRoomType) throws UpdateInventoryException;

    public RoomTypeEntity retrieveRoomTypeByName(String name)throws RoomTypeNotFoundException;

    public void updateRoomType(RoomTypeEntity roomType);

    public void disableRoomType(RoomTypeEntity roomType);

    public void deleteRoomType(RoomTypeEntity roomType);

    public List<RoomTypeEntity> viewAllRoomType();
    
    public List<RoomTypeEntity> retrieveAllRoomType();

    public RoomTypeEntity retrieveRoomTypeById(Long id)throws RoomTypeNotFoundException;

    public void updateTier(int tier);
    
}
