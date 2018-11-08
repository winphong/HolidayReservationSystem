/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDate;
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
public class OnlineReservationEntity extends ReservationEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private GuestEntity guest;

    public OnlineReservationEntity() {
    }

    public OnlineReservationEntity(LocalDate bookingDate, LocalDate startDate, LocalDate endDate, Boolean isCheckedIn) {
        super(bookingDate, startDate, endDate, isCheckedIn);
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
        if (!(object instanceof OnlineReservationEntity)) {
            return false;
        }
        OnlineReservationEntity other = (OnlineReservationEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.OnlineReservationEntity[ id=" + id + " ]";
    }

    /**
     * @return the guest
     */
    public GuestEntity getGuest() {
        return guest;
    }

    /**
     * @param guest the guest to set
     */
    public void setGuest(GuestEntity guest) {
        this.guest = guest;
    }
    
}
