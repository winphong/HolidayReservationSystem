/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.session.stateful.ReservationEntityControllerLocal;
import entity.Inventory;
import entity.RoomEntity;
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
    private ReservationEntityControllerLocal reservationEntityControllerLocal;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    
    //need to modify this 
    @Override
    public void updateInventory() {
        
        LocalDate currentDate = LocalDate.now();
        
        Query query = em.createQuery("SELECT i FROM Inventory i WHERE i.date >= :currentDate");
        query.setParameter("currentDate", currentDate);
        
        List<Inventory> inventories = query.getResultList();
        
        for(Inventory inventory : inventories) {
            inventory.updateInventory();
        }
    }
    
    @Override
    public Inventory getInventoryByDate(LocalDate inputDate) {
        
        Query query = em.createQuery("SELECT i FROM Inventory i WHERE i.date = :inputDate");
        query.setParameter("inputDate", inputDate);
        
        try {
            
            return (Inventory) query.getSingleResult();
        
        } catch (NoResultException | NonUniqueResultException ex) {
            
            System.out.println("Inventory for " + inputDate + " does not exist!");
        }
        
        return null;
    }
    
    // Check from startDate to endDate whether there is any available room
    // Check each room and each date and make sure there is atleast one that is 
    public List<RoomTypeEntity> searchAvailableRoom(LocalDate startDate, LocalDate endDate, Integer numOfRoomRequired) {
        
        List<RoomTypeEntity> availableRoomType = null;
        
        Inventory inventory = getInventoryByDate(startDate);
        
         // Get using getAvailableRoom will return room that is not disabled
        // If use getRoomTypes.get(index).getRoom() will return all room, including those that has been disabled
        List<List<RoomEntity>> listOfRoomsForDifferentRoomTypes = inventory.getAvailableRoom();
        
        Integer roomTypeIndex = 0;
        Boolean availableThroughout;
        Integer countOfRoomAvailableThroughout;
        Integer numOfReservationThatOverlapWithBooking = reservationEntityControllerLocal.retrieveReservationByStartAndEndDate(startDate, endDate).size();

        // Loop through each room type
        for(List<RoomEntity> listOfRooms : listOfRoomsForDifferentRoomTypes) {
            
            countOfRoomAvailableThroughout = 0;
            
            // Loop through each room of a particular room type
            for(RoomEntity room : listOfRooms) {
                
                availableThroughout = Boolean.TRUE;
                
                // Loop through the booking date
                for(LocalDate date = startDate.plusDays(1); !date.isAfter(endDate) ; date.plusDays(1)) {
                    
                    if (roomExist(room, date, roomTypeIndex)) {
                        continue;
                    } else {
                        availableThroughout = Boolean.FALSE;
                        break;
                    }
                }
                
                // As long there is enough room that is available throughout to fulfill the numOfRoomRequired
                // Need to check with reservation also, if reservation 
                if ( availableThroughout.equals(Boolean.TRUE) ) {
                    
                    countOfRoomAvailableThroughout++; 
                    
                    if ( (countOfRoomAvailableThroughout - numOfReservationThatOverlapWithBooking) >= numOfRoomRequired ) {
                        availableRoomType.add(room.getRoomType());
                        break;
                    }
                }
            }
            roomTypeIndex++;
        }
        return availableRoomType;
    }
    
    // Helper method
    // roomTypeIndex - indicate which roomType we are searching from
    public Boolean roomExist(RoomEntity room, LocalDate date, Integer roomTypeIndex) {
        
        Inventory inventory = getInventoryByDate(date);
        
        List<List<RoomEntity>> listOfRoomEntity = inventory.getAvailableRoom();
        
        if ( listOfRoomEntity.get(roomTypeIndex).indexOf(room) != -1 ) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        } 
    }
}
