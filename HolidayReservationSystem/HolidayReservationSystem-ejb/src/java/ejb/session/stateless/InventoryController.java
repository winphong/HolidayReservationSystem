/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Inventory;
import entity.RoomTypeEntity;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author twp10
 */
@Stateless
@Local (InventoryControllerLocal.class)
@Remote (InventoryControllerRemote.class)

public class InventoryController implements InventoryControllerRemote, InventoryControllerLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;
    
    @EJB
    private RoomTypeEntityControllerLocal roomTypeEntityControllerLocal;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public void updateInventory() {
        
        LocalDate currentDate = LocalDate.now();
        
        Query query = em.createQuery("SELECT i FROM Inventory i WHERE date >= currentDate");
        
        List<Inventory> inventories = query.getResultList();
        
        for(Inventory inventory : inventories) {
            
            inventory.updateInventory();
        }
    }
    
    public Inventory getInventoryByDate(LocalDate thisDate) {
        
        Query query = em.createQuery("SELECT i FROM Inventory i WHERE date = thisDate");
        
        try {
            
            return (Inventory) query.getSingleResult();
        
        } catch (NoResultException | NonUniqueResultException ex) {
            
            System.out.println("Inventory for " + thisDate + " does not exist!");
        }
        
        return null;
    }
    
    // Check from startDate to endDate whether there is any available room
    // Check each room and each date and make sure there is atleast one that is 
    public List<RoomTypeEntity> searchAvailableRoom(LocalDate startDate, LocalDate endDate, Integer numOfRoomRequired) {
        
        
        
        List<RoomTypeEntity> availableRoomTypes = null;
        
        List<Boolean> roomIsAvailable = null;
        
        // Get the inventories specified by the start and end date
        List<Inventory> inventories = null;
        
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            
            inventories.add(getInventoryByDate(date));
        }
        
        // Inirialize roomIsAvailable to true
        Integer numOfRoomType = inventories.get(0).getRoomTypes().size();
        
        for(int i = 0; i < numOfRoomType; i++) {
            
            roomIsAvailable.add(Boolean.TRUE);
        }
        
        // For each day specified by the start and end date
        for(Inventory inventory : inventories) {
            
            List<Integer> numOfRoomForEachRoomType = inventory.getNumOfRoomForEachRoomType();
            
            int index = 0;
            
            for(Integer numOfRoomForARoomType : numOfRoomForEachRoomType ) {
                
                if (numOfRoomForARoomType < numOfRoomRequired) {
                    
                    roomIsAvailable.set(index, Boolean.FALSE);
                }
                
                index++;
            }
        }

        
        for(int index = 0; index < numOfRoomType; index++) {
            
            if ( roomIsAvailable.get(index).equals(Boolean.TRUE) ) {
                availableRoomTypes.add(inventories.get(0).getRoomTypes().get(index));
            }
        }
        
        return availableRoomTypes;
    }
}
