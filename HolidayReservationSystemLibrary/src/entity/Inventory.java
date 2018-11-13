/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.RoomStatus;

/**
 *
 * @author twp10
 */
@Entity
public class Inventory implements Serializable {
    
    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;
    private LocalDate date;
    private List<RoomTypeEntity> roomTypes = null;
    private Integer totalNumOfRoomAvailable = 0;
    private List<List<RoomEntity>> availableRoom = null;

    
    public Inventory() {
    }

    public Inventory(EntityManager em, LocalDate date) {
        this();
        this.em = em;
        this.date = date;
    }
    
    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (inventoryId != null ? inventoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the inventoryId fields are not set
        if (!(object instanceof Inventory)) {
            return false;
        }
        Inventory other = (Inventory) object;
        if ((this.inventoryId == null && other.inventoryId != null) || (this.inventoryId != null && !this.inventoryId.equals(other.inventoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Inventory[ id=" + inventoryId + " ]";
    }
    
    // Everytime a room / roomType is created/ updated / deleted, inventory must be updated from the current system date to the latest available booking date
    // need to modify to include use case for create and update
    public void updateInventory() {
        
        Query query = em.createQuery("SELECT rt FROM RoomTypeEntity rt WHERE rt.isDisabled = :inBoolean");
        query.setParameter("inBoolean", Boolean.FALSE);
        
        // Get a list of roomTypes that is not disabled
        roomTypes = (List<RoomTypeEntity>) query.getResultList();
        // Reset availableRoom to a new ArrayList first before adding to the list again
        availableRoom = new ArrayList<>();
        // 
        totalNumOfRoomAvailable = 0;
        
        // For each room type
        for(RoomTypeEntity roomType : roomTypes) {
            
            List<RoomEntity> roomForEachRoomType = null;
            // Get the list of room
            List<RoomEntity> rooms = roomType.getRoom();           
            // Loop through the list of room and check for room that is not disabled and add to the list of roomForEachRoomType
            for(RoomEntity room : rooms) {
                
                if ( room.getIsDisabled().equals(Boolean.FALSE) && room.getRoomStatus().equals(RoomStatus.VACANT) ) {
                    roomForEachRoomType.add(room);
                    totalNumOfRoomAvailable++;
                }   
            }
            // Add the list of roomForEachRoomType to the list of availableRoom (which is a list of availableRoom consisting lists of all room for the particular roomType
            getAvailableRoom().add(roomForEachRoomType);
            // Iterate by the index (roomForEachRoomType will correspond the to RoomType at any give index)
        }
    }

    /**
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * @return the roomTypes
     */
    public List<RoomTypeEntity> getRoomTypes() {
        return roomTypes;
    }

    /**
     * @param roomTypes the roomTypes to set
     */
    public void setRoomTypes(List<RoomTypeEntity> roomTypes) {
        this.roomTypes = roomTypes;
    }

    /**
     * @return the totalNumOfRoomAvailable
     */
    public Integer getTotalNumOfRoomAvailable() {
        return totalNumOfRoomAvailable;
    }

    /**
     * @param totalNumOfRoomAvailable the totalNumOfRoomAvailable to set
     */
    public void setTotalNumOfRoomAvailable(Integer totalNumOfRoomAvailable) {
        this.totalNumOfRoomAvailable = totalNumOfRoomAvailable;
    }
    
    /**
     * @return the availableRoom
     */
    public List<List<RoomEntity>> getAvailableRoom() {
        return availableRoom;
    }

    /**
     * @param availableRoom the availableRoom to set
     */
    public void setAvailableRoom(List<List<RoomEntity>> availableRoom) {
        this.availableRoom = availableRoom;
    }
    
}
