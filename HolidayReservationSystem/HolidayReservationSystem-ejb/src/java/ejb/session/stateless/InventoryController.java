/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

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
import ejb.session.stateful.WalkinReservationEntityControllerLocal;
import entity.ReservationEntity;
import entity.ReservationLineItemEntity;
import java.util.ArrayList;
import util.enumeration.RoomStatus;

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
    private WalkinReservationEntityControllerLocal reservationEntityControllerLocal;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    
    // Everytime a room / roomType is created/ updated / deleted, inventory must be updated from the current system date to the latest available booking date
    // need to modify to include use case for create and update
    @Override
    public void updateAllInventory() {
        
        LocalDate currentDate = LocalDate.now();
        
        Query query = em.createQuery("SELECT i FROM Inventory i WHERE i.date >= :currentDate");
        query.setParameter("currentDate", currentDate);
        
        List<Inventory> inventories = (List<Inventory>) query.getResultList();
        
        for(Inventory inventory : inventories) {
            updateInventory(inventory.getInventoryId());
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
    
    @Override
    public Inventory retrieveInventoryById(Long inventoryId) {
        
        Query query = em.createQuery("SELECT i FROM Inventory i WHERE i.inventoryId = :inInventoryId");
        query.setParameter("inInventoryId", inventoryId);
        
        try {
            
            return (Inventory) query.getSingleResult();
        
        } catch (NoResultException | NonUniqueResultException ex) {
            
            System.out.println("Inventory with ID : " + inventoryId + " does not exist!");
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
        List<List<RoomEntity>> listOfRoomsForDifferentRoomTypes = inventory.getAvailableRoom(); // availableRoom is not disable and is vacant
        
        Integer roomTypeIndex = 0;
        Boolean availableThroughout;
        Integer countOfRoomAvailableThroughout;
        // One reservation might have more than one room
        List<ReservationEntity> reservationList = reservationEntityControllerLocal.retrieveReservationByStartAndEndDate(startDate, endDate);
        List<ReservationLineItemEntity> reservationLineItems;
        
        Integer numOfReservationThatOverlapWithBooking = 0;
        
        // Check through all reservation
        for (ReservationEntity reservation : reservationList) {
            
            reservationLineItems = reservation.getReservationLineItemEntities();
            
            // For all reservation, check through the reservationLineItem
            for(ReservationLineItemEntity reservationLineItem : reservationLineItems ) {
                
                numOfReservationThatOverlapWithBooking += reservationLineItem.getNumOfRoomBooked();
            }
        }
         
        // Loop through each room type
        for(List<RoomEntity> listOfRooms : listOfRoomsForDifferentRoomTypes) {
            
            countOfRoomAvailableThroughout = 0;
            
            // Loop through each room of a particular room type
            for(RoomEntity room : listOfRooms) {
                
                availableThroughout = Boolean.TRUE;
                
                // Loop through the booking date starting from the startDate to the endDate
                // From the highlighted line, the function already ensure all the room retrieved will be available for startDate
                // Therefore, the search loop can start a day after the startDate
                for(LocalDate date = startDate.plusDays(1); !date.isAfter(endDate) ; date.plusDays(1)) {
                    
                    // If the particular room is not available for the entire duration, set availbleThroughout to be false
                    if (!roomExist(room, date, roomTypeIndex)) {
                        availableThroughout = Boolean.FALSE;
                        break;
                    }
                }
                
                // As long there is enough room that is available throughout to fulfill the numOfRoomRequired
                // Need to check with reservation also, if reservation exist
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
    @Override
    public Boolean roomExist(RoomEntity room, LocalDate date, Integer roomTypeIndex) {
        
        Inventory inventory = getInventoryByDate(date);
        
        List<List<RoomEntity>> listOfRoomEntity = inventory.getAvailableRoom();
        
        if ( listOfRoomEntity.get(roomTypeIndex).indexOf(room) != -1 ) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        } 
    }
    
    private void updateInventory(Long inventoryId) {
        
        Inventory inventory = retrieveInventoryById(inventoryId);
        
        Query query = em.createQuery("SELECT rt FROM RoomTypeEntity rt WHERE rt.isDisabled = :inBoolean");
        query.setParameter("inBoolean", Boolean.FALSE);
        
        // Get a list of roomTypes that is not disabled
        List<RoomTypeEntity> roomTypes = (List<RoomTypeEntity>) query.getResultList();
        // List<RoomEntity> availableRoom = new ArrayList<>();   
        Integer totalNumOfRoomAvailable = 0;
        
        List<RoomEntity> roomForEachRoomType;
        List<RoomEntity> rooms;
        
        // Set availableRoom to new List
        inventory.setAvailableRoom(new ArrayList<>());
        // For each room type
        for(RoomTypeEntity roomType : roomTypes) {
            roomForEachRoomType = new ArrayList<>();
            // Get the list of room
            rooms = roomType.getRoom();           
            // Loop through the list of room and check for room that is not disabled and add to the list of roomForEachRoomType
            for(RoomEntity room : rooms) {
                // Not disable and vacant
                if ( room.getIsDisabled().equals(Boolean.FALSE) && room.getRoomStatus().equals(RoomStatus.VACANT) ) {
                    roomForEachRoomType.add(room);
                    totalNumOfRoomAvailable++;
                }   
            }
            inventory.getAvailableRoom().add(roomForEachRoomType);
            // Iterate by the index (roomForEachRoomType will correspond the to RoomType at any give index)
        }
        // Add the list of roomForEachRoomType to the list of availableRoom (which is a list of availableRoom consisting lists of all room for the particular roomType
        inventory.setTotalNumOfRoomAvailable(totalNumOfRoomAvailable);
    }
}
