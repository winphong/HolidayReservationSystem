/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long inventoryId;
    private LocalDate date;
    private List<RoomTypeEntity> roomTypes;
    private Integer totalNumOfRoomAvailable;
    private List<Integer> numOfRoomForEachRoomType;

    
    public Inventory() {
        updateInventory();
    }

    public Inventory(LocalDate date) {
        this();
        
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
    
    public void updateInventory() {
        
        Query query = em.createQuery("SELECT rt FROM RoomType rt WHERE isDisabled = FALSE");
            
        roomTypes = query.getResultList();
        
        totalNumOfRoomAvailable = 0;
        
        Integer count;
        
        List<Integer> numOfRoom = null;
        
        for(RoomTypeEntity roomType : roomTypes) {
            
            List<RoomEntity> rooms = roomType.getRoom();
            
            count = 0;
            for(RoomEntity room : rooms) {
                
                if ( room.getIsDisabled().equals(Boolean.FALSE) ) {
                    totalNumOfRoomAvailable += 1;
                    count++;
                }   
            }
            numOfRoom.add(count);
        }
        
        numOfRoomForEachRoomType = numOfRoom;
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
    
    public List<Integer> getNumOfRoomForEachRoomType() {
        return numOfRoomForEachRoomType;
    }

    public void setNumOfRoomForEachRoomType(List<Integer> numOfRoomForEachRoomType) {
        this.numOfRoomForEachRoomType = numOfRoomForEachRoomType;
    }
    
}
