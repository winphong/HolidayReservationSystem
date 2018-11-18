/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

/**
 *
 * @author twp10
 */
@Entity
@Inheritance (strategy=InheritanceType.JOINED)
public abstract class ReservationEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;
    @Column (nullable = false)
    private Date startDate;
    @Column (nullable = false)
    private Date endDate;
    @Column (nullable = false)
    private Date bookingDate;
    @Column (nullable = false)
    private Boolean isCheckedIn;
    @Column (nullable = false)
    private Boolean isNotAllocated;
    @Column (nullable = false, scale = 2)
    private BigDecimal totalAmount;
    
    @OneToMany(mappedBy = "reservation")
    private List<ReservationLineItemEntity> reservationLineItemEntities;

    @OneToMany(mappedBy = "currentReservation")
    private List<RoomEntity> rooms;

    public ReservationEntity() {
        this.isNotAllocated = Boolean.TRUE;
        totalAmount = new BigDecimal("0");
        this.reservationLineItemEntities = new ArrayList<>();
        this.rooms = new ArrayList<>();
    }

    public ReservationEntity(Date bookingDate, Date startDate, Date endDate, Boolean isCheckedIn) {
        this();
        this.bookingDate = Date.valueOf(LocalDate.now());
        this.startDate = startDate;
        this.endDate = endDate;
        this.isCheckedIn = isCheckedIn;
    }
    
    
    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long id) {
        this.reservationId = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reservationId != null ? reservationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReservationEntity)) {
            return false;
        }
        ReservationEntity other = (ReservationEntity) object;
        if ((this.reservationId == null && other.reservationId != null) || (this.reservationId != null && !this.reservationId.equals(other.reservationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Reservation[ id=" + reservationId + " ]";
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the isCheckedIn
     */
    public Boolean getIsCheckedIn() {
        return isCheckedIn;
    }

    /**
     * @param isCheckedIn the isCheckedIn to set
     */
    public void setIsCheckedIn(Boolean isCheckedIn) {
        this.isCheckedIn = isCheckedIn;
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
     * @return the reservationLineItemEntities
     */
    public List<ReservationLineItemEntity> getReservationLineItemEntities() {
        return reservationLineItemEntities;
    }

    /**
     * @param reservationLineItemEntities the reservationLineItemEntities to set
     */
    public void setReservationLineItemEntities(List<ReservationLineItemEntity> reservationLineItemEntities) {
        this.reservationLineItemEntities = reservationLineItemEntities;
    }

    /**
     * @return the bookingDate
     */
    public Date getBookingDate() {
        return bookingDate;
    }

    /**
     * @param bookingDate the bookingDate to set
     */
    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    /**
     * @return the rooms
     */
    public List<RoomEntity> getRooms() {
        return rooms;
    }

    /**
     * @param rooms the rooms to set
     */
    public void setRooms(List<RoomEntity> rooms) {
        this.rooms = rooms;
    }

    /**
     * @return the isNotAllocated
     */
    public Boolean getIsNotAllocated() {
        return isNotAllocated;
    }

    /**
     * @param isNotAllocated the isNotAllocated to set
     */
    public void setIsNotAllocated(Boolean isNotAllocated) {
        this.isNotAllocated = isNotAllocated;
    }    
}
