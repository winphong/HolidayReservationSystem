/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author twp10
 */
@Entity
public class PartnerEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partnerId;
    @Column(nullable = false)
    private String companyName;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(length = 8, nullable = false, unique = true)
    private String phoneNumber;
    @Column(length = 10, nullable = false, unique = true)
    private String companyRegistrationId;
    @Column(unique = true)
    private String email;
    
    @OneToMany(mappedBy="partner")
    private List <PartnerReservationEntity> reservation;

    public PartnerEntity() {
        this.reservation = new ArrayList<>();
    }

    public PartnerEntity(String companyName, String userName, String password, String phoneNumber, String companyRegistrationId, String email) {
        
        this();
        this.companyName = companyName;
        this.username = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.companyRegistrationId = companyRegistrationId;
        this.email = email;        
    }
    
    
    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (partnerId != null ? partnerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the partnerId fields are not set
        if (!(object instanceof PartnerEntity)) {
            return false;
        }
        PartnerEntity other = (PartnerEntity) object;
        if ((this.partnerId == null && other.partnerId != null) || (this.partnerId != null && !this.partnerId.equals(other.partnerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Partner[ id=" + partnerId + " ]";
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return username;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.username = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the companyRegistrationId
     */
    public String getCompanyRegistrationId() {
        return companyRegistrationId;
    }

    /**
     * @param companyRegistrationId the companyRegistrationId to set
     */
    public void setCompanyRegistrationId(String companyRegistrationId) {
        this.companyRegistrationId = companyRegistrationId;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the reservation
     */
    public List <PartnerReservationEntity> getReservation() {
        return reservation;
    }

    /**
     * @param reservation the reservation to set
     */
    public void setReservation(List <PartnerReservationEntity> reservation) {
        this.reservation = reservation;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
