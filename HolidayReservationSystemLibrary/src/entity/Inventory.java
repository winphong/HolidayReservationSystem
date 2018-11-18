/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author twp10
 */
@Entity
public class Inventory implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;
    @Column(nullable = false)
    private Date date;
    //@Column(nullable = false)
    private Integer totalNumOfRoomAvailable;
    @Column(nullable = false)
    private List<List<RoomEntity>> availableRoom;

    
    public Inventory() {
        this.totalNumOfRoomAvailable = 0;
        this.availableRoom = new ArrayList<>();
    }

    public Inventory(Date date) {
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

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
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
