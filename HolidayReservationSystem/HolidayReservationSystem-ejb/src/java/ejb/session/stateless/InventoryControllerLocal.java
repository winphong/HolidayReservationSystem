/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Inventory;
import entity.RoomEntity;
import java.time.LocalDate;
import util.exception.UpdateInventoryException;

/**
 *
 * @author twp10
 */
public interface InventoryControllerLocal {

    public void updateAllInventory() throws UpdateInventoryException;
    
    public Inventory getInventoryByDate(LocalDate date);

    public Boolean roomExist(RoomEntity room, LocalDate date, Integer roomTypeIndex);

    public Inventory retrieveInventoryById(Long inventoryId);
    
}
