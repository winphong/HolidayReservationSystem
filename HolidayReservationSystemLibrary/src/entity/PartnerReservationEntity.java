/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author Asus
 */
@Entity
public class PartnerReservationEntity extends ReservationEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(nullable = false)
    private String customerFirstName;
    @Column(nullable = false)
    private String customerLastName;
    @Column(nullable = false)
    private String customerEmail;
    @Column(nullable = false, length = 8)
    private String customerContactNumber;

    @ManyToOne
    private PartnerEntity partner;

    public PartnerReservationEntity() {
    }

    public PartnerReservationEntity(PartnerEntity partner, String customerFirstName, String customerLastName, String customerEmail, String customerContactNumber, Date bookingDate, Date startDate, Date endDate, Boolean isCheckedIn) {
        super(bookingDate, startDate, endDate, isCheckedIn);
        this.partner = partner;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerEmail = customerEmail;
        this.customerContactNumber = customerContactNumber;
    }

    @Override
    public Long getReservationId() {
        return super.getReservationId(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setReservationId(Long id) {
        super.setReservationId(id); //To change body of generated methods, choose Tools | Templates.
    }
    
//    public Long getPartnerReservationId() {
//        return partnerReservationId;
//    }
//
//    public void setPartnerReservationId(Long partnerReservationId) {
//        this.partnerReservationId = partnerReservationId;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (partnerReservationId != null ? partnerReservationId.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the partnerReservationId fields are not set
//        if (!(object instanceof PartnerReservationEntity)) {
//            return false;
//        }
//        PartnerReservationEntity other = (PartnerReservationEntity) object;
//        if ((this.partnerReservationId == null && other.partnerReservationId != null) || (this.partnerReservationId != null && !this.partnerReservationId.equals(other.partnerReservationId))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "entity.PartnerReservationEntity[ id=" + partnerReservationId + " ]";
//    }

    /**
     * @return the partner
     */
    public PartnerEntity getPartner() {
        return partner;
    }

    /**
     * @param partner the partner to set
     */
    public void setPartner(PartnerEntity partner) {
        this.partner = partner;
    }

    /**
     * @return the customerFirstName
     */
    public String getCustomerFirstName() {
        return customerFirstName;
    }

    /**
     * @param customerFirstName the customerFirstName to set
     */
    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    /**
     * @return the customerLastName
     */
    public String getCustomerLastName() {
        return customerLastName;
    }

    /**
     * @param customerLastName the customerLastName to set
     */
    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    /**
     * @return the customerEmail
     */
    public String getCustomerEmail() {
        return customerEmail;
    }

    /**
     * @param customerEmail the customerEmail to set
     */
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    /**
     * @return the customerContactNumber
     */
    public String getCustomerContactNumber() {
        return customerContactNumber;
    }

    /**
     * @param customerContactNumber the customerContactNumber to set
     */
    public void setCustomerContactNumber(String customerContactNumber) {
        this.customerContactNumber = customerContactNumber;
    }

    
}
