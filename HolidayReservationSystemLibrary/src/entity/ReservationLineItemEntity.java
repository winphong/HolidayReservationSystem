/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Asus
 */
@Entity
public class ReservationLineItemEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationLineItemId;
    @Column(nullable = false)
    private Integer numOfRoomBooked;
    @Column(scale=2, nullable = false)
    private BigDecimal totalAmount;
    @Column(nullable = false)
    private RoomTypeEntity roomType;
    @Column(nullable = false)
    private Boolean isAllocated;
    
    /*
    @ManyToOne
    private RoomTypeEntity roomType;
    @OneToMany (mappedBy = "reservationlineitem")
    private List<RoomRatePerNightEntity> roomRatePerNight;
    */
    
    @ManyToOne
    private ReservationEntity reservation;
    
    public ReservationLineItemEntity() {
        isAllocated = Boolean.FALSE;
    }

    public ReservationLineItemEntity(BigDecimal totalAmount, Integer numOfRoom, RoomTypeEntity roomType) {
        this();
        this.numOfRoomBooked = numOfRoom;
        this.totalAmount = totalAmount;
        this.roomType = roomType;
    }
    
    public Long getReservationLineItemId() {
        return reservationLineItemId;
    }

    public void setReservationLineItemId(Long reservationLineItemId) {
        this.reservationLineItemId = reservationLineItemId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reservationLineItemId != null ? reservationLineItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the reservationLineItemId fields are not set
        if (!(object instanceof ReservationLineItemEntity)) {
            return false;
        }
        ReservationLineItemEntity other = (ReservationLineItemEntity) object;
        if ((this.reservationLineItemId == null && other.reservationLineItemId != null) || (this.reservationLineItemId != null && !this.reservationLineItemId.equals(other.reservationLineItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ReservationLineItemEntity[ id=" + reservationLineItemId + " ]";
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
     * @return the numOfRoomBooked
     */
    public Integer getNumOfRoomBooked() {
        return numOfRoomBooked;
    }

    /**
     * @param numOfRoomBooked the numOfRoomBooked to set
     */
    public void setNumOfRoomBooked(Integer numOfRoomBooked) {
        this.numOfRoomBooked = numOfRoomBooked;
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
     * @return the isAllocated
     */
    public Boolean getIsAllocated() {
        return isAllocated;
    }

    /**
     * @param isAllocated the isAllocated to set
     */
    public void setIsAllocated(Boolean isAllocated) {
        this.isAllocated = isAllocated;
    }
}
