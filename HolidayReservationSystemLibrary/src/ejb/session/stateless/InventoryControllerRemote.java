/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomTypeEntity;
import java.time.LocalDate;
import java.util.List;
import util.exception.UpdateInventoryException;

/**
 *
 * @author twp10
 */
public interface InventoryControllerRemote {

    public List<RoomTypeEntity> searchAvailableRoom(LocalDate startDate, LocalDate endDate, Integer numOfRoomRequired);

    public void updateAllInventory() throws UpdateInventoryException;
    
}
