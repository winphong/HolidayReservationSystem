/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDate;
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
public class WalkinReservationEntity extends ReservationEntity implements Serializable {

    private static long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String guestFirstName;
    @Column(nullable = false)
    private String guestLastName;
    @Column(length=8, nullable = false, unique = true)
    private Long guestPhoneNumber;
    @Column(unique = true)
    private String guestEmail;
    @Column(length = 9, unique = true, nullable = false)
    private String guestIdentificationNumber;
    
    @ManyToOne
    private EmployeeEntity employee;

    public WalkinReservationEntity() {
    }

    public WalkinReservationEntity(String guestFirstName, String guestLastName, Long guestPhoneNumber, String guestEmail, String guestIdentificationNumber, EmployeeEntity employee, LocalDate startDate, LocalDate endDate, Boolean isCheckedIn) {
        super(startDate, endDate, isCheckedIn);
        this.guestFirstName = guestFirstName;
        this.guestLastName = guestLastName;
        this.guestPhoneNumber = guestPhoneNumber;
        this.guestEmail = guestEmail;
        this.guestIdentificationNumber = guestIdentificationNumber;
        this.employee = employee;
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
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WalkinReservationEntity)) {
            return false;
        }
        WalkinReservationEntity other = (WalkinReservationEntity) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.WalkinReservationEntity[ id=" + getId() + " ]";
    }

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }

    /**
     * @return the guestFirstName
     */
    public String getGuestFirstName() {
        return guestFirstName;
    }

    /**
     * @param guestFirstName the guestFirstName to set
     */
    public void setGuestFirstName(String guestFirstName) {
        this.guestFirstName = guestFirstName;
    }

    /**
     * @return the guestLastName
     */
    public String getGuestLastName() {
        return guestLastName;
    }

    /**
     * @param guestLastName the guestLastName to set
     */
    public void setGuestLastName(String guestLastName) {
        this.guestLastName = guestLastName;
    }

    /**
     * @return the guestPhoneNumber
     */
    public Long getGuestPhoneNumber() {
        return guestPhoneNumber;
    }

    /**
     * @param guestPhoneNumber the guestPhoneNumber to set
     */
    public void setGuestPhoneNumber(Long guestPhoneNumber) {
        this.guestPhoneNumber = guestPhoneNumber;
    }

    /**
     * @return the guestEmail
     */
    public String getGuestEmail() {
        return guestEmail;
    }

    /**
     * @param guestEmail the guestEmail to set
     */
    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    /**
     * @return the guestIdentificationNumber
     */
    public String getGuestIdentificationNumber() {
        return guestIdentificationNumber;
    }

    /**
     * @param guestIdentificationNumber the guestIdentificationNumber to set
     */
    public void setGuestIdentificationNumber(String guestIdentificationNumber) {
        this.guestIdentificationNumber = guestIdentificationNumber;
    }

    /**
     * @return the employee
     */
    public EmployeeEntity getEmployee() {
        return employee;
    }

    /**
     * @param employee the employee to set
     */
    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }
    
}
