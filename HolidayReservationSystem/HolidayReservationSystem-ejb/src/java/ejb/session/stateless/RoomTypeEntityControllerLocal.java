/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomTypeEntity;
import java.util.List;

/**
 *
 * @author twp10
 */

public interface RoomTypeEntityControllerLocal {

    public List<RoomTypeEntity> retrieveAllRoomType();
    
}
