/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Inventory;
import entity.ReservationLineItemEntity;
import entity.RoomEntity;
import entity.RoomTypeEntity;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import util.exception.UpdateInventoryException;

/**
 *
 * @author twp10
 */
public interface InventoryControllerLocal {

    public void updateAllInventory() throws UpdateInventoryException;
    
    public Inventory getInventoryByDate(Date date);

    public Boolean roomExist(RoomEntity room, LocalDate date, Integer roomTypeIndex);

    public Inventory retrieveInventoryById(Long inventoryId);

    public List<ReservationLineItemEntity> getLineItemsForCurrentReservation();

    public void setLineItemsForCurrentReservation(List<ReservationLineItemEntity> lineItemsForCurrentReservation);
    
    public List<RoomTypeEntity> searchAvailableRoom(String startDate, String endDate, Integer numOfRoomRequired);
}
