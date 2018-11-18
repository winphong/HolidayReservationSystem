/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author twp10
 */
@Entity
public class RoomTypeEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomTypeId;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false, length = 512)
    private String description;
    @Column(nullable = false, scale = 2) // What is size for again??
    private BigDecimal size;
    @Column(nullable = false)
    private String bed;
    @Column(nullable = false)
    private Integer capacity;
    @Column(nullable = false)
    private String amenities;
    @Column(unique = true, nullable = false)
    private Integer tier;
    @Column(nullable = false)
    private Boolean isDisabled;
    
    @OneToMany(mappedBy="roomType", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RoomEntity> room;
    
    @OneToMany(mappedBy="roomType", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RoomRateEntity> roomRate;
    
    @OneToOne(mappedBy="roomType")
    private ReservationLineItemEntity reservationItem;

    public RoomTypeEntity() {
    }

    public RoomTypeEntity(String name, String description, BigDecimal size, String bed, Integer capacity, String amenities, Integer tier) {
        this();
        this.name = name;
        this.description = description;
        this.size = size;
        this.bed = bed;
        this.capacity = capacity;
        this.amenities = amenities;
        this.tier = tier;
        this.isDisabled = Boolean.FALSE;
    }
    
    
    
    public Long getRoomTypeId() {
        return roomTypeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomTypeId != null ? roomTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the roomTypeId fields are not set
        if (!(object instanceof RoomTypeEntity)) {
            return false;
        }
        RoomTypeEntity other = (RoomTypeEntity) object;
        if ((this.roomTypeId == null && other.roomTypeId != null) || (this.roomTypeId != null && !this.roomTypeId.equals(other.roomTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RoomType[ id=" + roomTypeId + " ]";
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the size
     */
    public BigDecimal getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(BigDecimal size) {
        this.size = size;
    }

    /**
     * @return the bed
     */
    public String getBed() {
        return bed;
    }

    /**
     * @param bed the bed to set
     */
    public void setBed(String bed) {
        this.bed = bed;
    }

    /**
     * @return the capacity
     */
    public Integer getCapacity() {
        return capacity;
    }

    /**
     * @param capacity the capacity to set
     */
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    /**
     * @return the amenities
     */
    public String getAmenities() {
        return amenities;
    }

    /**
     * @param amenities the amenities to set
     */
    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    /**
     * @return the tier
     */
    public Integer getTier() {
        return tier;
    }

    /**
     * @param tier the tier to set
     */
    public void setTier(Integer tier) {
        this.tier = tier;
    }

    /**
     * @return the isDisabled
     */
    public Boolean getIsDisabled() {
        return isDisabled;
    }

    /**
     * @param isDisabled the isDisabled to set
     */
    public void setIsDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    /**
     * @return the room
     */
    public List<RoomEntity> getRoom() {
        return room;
    }

    /**
     * @param room the room to set
     */
    public void setRoom(List<RoomEntity> room) {
        this.room = room;
    }

    /**
     * @return the roomRate
     */
    public List<RoomRateEntity> getRoomRate() {
        return roomRate;
    }

    /**
     * @param roomRate the roomRate to set
     */
    public void setRoomRate(List<RoomRateEntity> roomRate) {
        this.roomRate = roomRate;
    }

    /**
     * @return the reservationItem
     */
    public ReservationLineItemEntity getReservationItem() {
        return reservationItem;
    }

    /**
     * @param reservationItem the reservationItem to set
     */
    public void setReservationItem(ReservationLineItemEntity reservationItem) {
        this.reservationItem = reservationItem;
    }
 
}
