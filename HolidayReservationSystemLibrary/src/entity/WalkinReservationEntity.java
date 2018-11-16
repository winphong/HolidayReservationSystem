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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;

/**
 *
 * @author Asus
 */
@Entity
public class WalkinReservationEntity extends ReservationEntity implements Serializable {

    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long walkInReservationId;
    
    @Column(nullable = false)
    private String guestFirstName;
    @Column(nullable = false)
    private String guestLastName;
    @Column(length=8, nullable = false, unique = true)
    private String guestContactNumber;
    @Column(unique = true)
    private String guestEmail;
    @Column(length = 9, unique = true, nullable = false)
    private String guestIdentificationNumber;
    
    @ManyToOne
    private EmployeeEntity employee;

    public WalkinReservationEntity() {
    }

    public WalkinReservationEntity(String guestFirstName, String guestLastName, String guestContactNumber, String guestEmail, String guestIdentificationNumber, Date bookingDate, Date startDate, Date endDate, Boolean isCheckedIn) {
        super(bookingDate, startDate, endDate, isCheckedIn);
        this.guestFirstName = guestFirstName;
        this.guestLastName = guestLastName;
        this.guestContactNumber = guestContactNumber;
        this.guestEmail = guestEmail;
        this.guestIdentificationNumber = guestIdentificationNumber;
    }

    @Override
    public Long getReservationId() {
        return super.getReservationId(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setReservationId(Long id) {
        super.setReservationId(id); //To change body of generated methods, choose Tools | Templates.
    }
    

    /*@Override
    public int hashCode() {
        int hash = 0;
        hash += (getWalkInReservationId() != null ? getWalkInReservationId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WalkinReservationEntity)) {
            return false;
        }
        WalkinReservationEntity other = (WalkinReservationEntity) object;
        if ((this.getWalkInReservationId() == null && other.getWalkInReservationId() != null) || (this.getWalkInReservationId() != null && !this.walkInReservationId.equals(other.walkInReservationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.WalkinReservationEntity[ id=" + getWalkInReservationId() + " ]";
    }*/
//
//    /**
//     * @return the serialVersionUID
//     */
//    public static long getSerialVersionUID() {
//        return serialVersionUID;
//    }
//
//    /**
//     * @param aSerialVersionUID the serialVersionUID to set
//     */
//    public static void setSerialVersionUID(long aSerialVersionUID) {
//        serialVersionUID = aSerialVersionUID;
//    }

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
    public String getGuestContactNumber() {
        return guestContactNumber;
    }

    /**
     * @param guestPhoneNumber the guestPhoneNumber to set
     */
    public void setGuestContactNumber(String guestPhoneNumber) {
        this.guestContactNumber = guestPhoneNumber;
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
