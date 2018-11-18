/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomRateEntity;
import util.exception.RoomRateNotFoundException;

/**
 *
 * @author twp10
 */
public interface RoomRateEntityControllerLocal {
    
     public RoomRateEntity retrieveRoomRateByName(String name) throws RoomRateNotFoundException;

    public RoomRateEntity retrieveRoomRateById(Long id) throws RoomRateNotFoundException;
}
