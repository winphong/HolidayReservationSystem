/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author Asus
 */
@Entity
public class OnlineReservationEntity extends ReservationEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @ManyToOne
    private GuestEntity guest;

    public OnlineReservationEntity() {
    }

    public OnlineReservationEntity(Date bookingDate, Date startDate, Date endDate, Boolean isCheckedIn) {
        super(bookingDate, startDate, endDate, isCheckedIn);
    }

    @Override
    public Long getReservationId() {
        return super.getReservationId(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setReservationId(Long id) {
        super.setReservationId(id); //To change body of generated methods, choose Tools | Templates.
    }
    
    

//    public Long getOnlineReservationId() {
//        return onlineReservationId;
//    }
//
//    public void setOnlineReservationId(Long id) {
//        this.onlineReservationId = id;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (onlineReservationId != null ? onlineReservationId.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof OnlineReservationEntity)) {
//            return false;
//        }
//        OnlineReservationEntity other = (OnlineReservationEntity) object;
//        if ((this.onlineReservationId == null && other.onlineReservationId != null) || (this.onlineReservationId != null && !this.onlineReservationId.equals(other.onlineReservationId))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "entity.OnlineReservationEntity[ id=" + onlineReservationId + " ]";
//    }

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
