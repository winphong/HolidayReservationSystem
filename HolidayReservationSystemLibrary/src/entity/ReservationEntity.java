/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column (nullable = false)
    private LocalDate startDate;
    @Column (nullable = false)
    private LocalDate endDate;
    @Column (nullable = false)
    private LocalDate bookingDate;
    private Boolean isCheckedIn = false;
    private Boolean isAllocated = false;
    @Column (scale = 2)
    private BigDecimal totalAmount;
    
    @OneToMany(mappedBy = "reservation")
    private List<ReservationLineItemEntity> reservationLineItemEntities;

    public ReservationEntity() {
        this.reservationLineItemEntities = new ArrayList<ReservationLineItemEntity>();
    }

    public ReservationEntity(LocalDate bookingDate, LocalDate startDate, LocalDate endDate, Boolean isCheckedIn) {
        
        this();
        this.bookingDate = LocalDate.now();
        this.startDate = startDate;
        this.endDate = endDate;
        this.isCheckedIn = isCheckedIn;
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
        if (!(object instanceof ReservationEntity)) {
            return false;
        }
        ReservationEntity other = (ReservationEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Reservation[ id=" + id + " ]";
    }

    /**
     * @return the startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(LocalDate endDate) {
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

    /**
     * @return the bookingDate
     */
    public LocalDate getBookingDate() {
        return bookingDate;
    }

    /**
     * @param bookingDate the bookingDate to set
     */
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }
    
}
