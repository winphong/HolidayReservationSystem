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
import javax.persistence.OneToOne;

/**
 *
 * @author Asus
 */
@Entity
public class RoomRatePerNightEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (nullable = false)
    private BigDecimal rate;
    
    @OneToOne(mappedBy = "roomratepernight")
    private RoomRateEntity roomRateEntity;
    
    @ManyToOne
    private ReservationLineItemEntity reservationLineItemEntity;

    public RoomRatePerNightEntity() {
    }

    public RoomRatePerNightEntity(BigDecimal rate, RoomRateEntity roomRateEntity, ReservationLineItemEntity reservationLineItemEntity) {
        this();
        this.rate = rate;
        this.roomRateEntity = roomRateEntity;
        this.reservationLineItemEntity = reservationLineItemEntity;
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
        if (!(object instanceof RoomRatePerNightEntity)) {
            return false;
        }
        RoomRatePerNightEntity other = (RoomRatePerNightEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RoomRatePerNightEntity[ id=" + id + " ]";
    }

    /**
     * @return the rate
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * @return the reservationLineItemEntity
     */
    public ReservationLineItemEntity getReservationLineItemEntity() {
        return reservationLineItemEntity;
    }

    /**
     * @param reservationLineItemEntity the reservationLineItemEntity to set
     */
    public void setReservationLineItemEntity(ReservationLineItemEntity reservationLineItemEntity) {
        this.reservationLineItemEntity = reservationLineItemEntity;
    }

    /**
     * @return the roomRateEntity
     */
    public RoomRateEntity getRoomRateEntity() {
        return roomRateEntity;
    }

    /**
     * @param roomRateEntity the roomRateEntity to set
     */
    public void setRoomRateEntity(RoomRateEntity roomRateEntity) {
        this.roomRateEntity = roomRateEntity;
    }
    
}
