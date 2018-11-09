/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Asus
 */
@Entity
public class ReservationLineItemEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer numberOfRooms = 0;
    @Column(scale=2)
    private BigDecimal totalAmount;
    
    @OneToOne
    private RoomTypeEntity roomType;
    @ManyToOne
    private ReservationEntity reservation;
    @OneToMany (mappedBy = "reservationlineitem")
    private List<RoomRatePerNightEntity> roomRatePerNight;

    public ReservationLineItemEntity() {
    }

    public ReservationLineItemEntity(BigDecimal totalAmount, RoomTypeEntity roomType, ReservationEntity reservation) {
        this();
        this.totalAmount = totalAmount;
        this.roomType = roomType;
        this.reservation = reservation;
        this.roomRatePerNight = new ArrayList<RoomRatePerNightEntity>();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReservationLineItemEntity)) {
            return false;
        }
        ReservationLineItemEntity other = (ReservationLineItemEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ReservationLineItemEntity[ id=" + id + " ]";
    }

    /**
     * @return the totalAmount
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * @param totalAmount the totalAmount to set
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * @return the roomType
     */
    public RoomTypeEntity getRoomType() {
        return roomType;
    }

    /**
     * @param roomType the roomType to set
     */
    public void setRoomType(RoomTypeEntity roomType) {
        this.roomType = roomType;
    }

    /**
     * @return the reservation
     */
    public ReservationEntity getReservation() {
        return reservation;
    }

    /**
     * @param reservation the reservation to set
     */
    public void setReservation(ReservationEntity reservation) {
        this.reservation = reservation;
    }

    /**
     * @return the roomRatePerNight
     */
    public List<RoomRatePerNightEntity> getRoomRatePerNight() {
        return roomRatePerNight;
    }

    /**
     * @param roomRatePerNight the roomRatePerNight to set
     */
    public void setRoomRatePerNight(List<RoomRatePerNightEntity> roomRatePerNight) {
        this.roomRatePerNight = roomRatePerNight;
    }

    /**
     * @return the numberOfRooms
     */
    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    /**
     * @param numberOfRooms the numberOfRooms to set
     */
    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }
    
}
