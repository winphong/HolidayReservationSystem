/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import util.enumeration.ReservationType;

/**
 *
 * @author twp10
 */
@Entity
public class ReservationEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer numOfRoom;
    private Date startDate;
    private Date endDate;
    private Boolean isCheckedIn;
    @Enumerated(EnumType.STRING)
    private ReservationType reservationType;
    
    // Client that uses the system to make reservation: Employee/Guest/Partner
    @ManyToOne
    private Client client; 
    
    @ManyToMany(mappedBy="reservation")
    private List <RoomTypeEntity> roomType;

    public ReservationEntity() {
        this.roomType = new ArrayList<>();
    }

    public ReservationEntity(Integer numOfRoom, Date startDate, Date endDate, Boolean isCheckedIn, ReservationType reservationType) {
        
        this();
        
        this.numOfRoom = numOfRoom;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isCheckedIn = isCheckedIn;
        this.reservationType = reservationType;
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
     * @return the numOfRoom
     */
    public Integer getNumOfRoom() {
        return numOfRoom;
    }

    /**
     * @param numOfRoom the numOfRoom to set
     */
    public void setNumOfRoom(Integer numOfRoom) {
        this.numOfRoom = numOfRoom;
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
     * @return the reservationType
     */
    public ReservationType getReservationType() {
        return reservationType;
    }

    /**
     * @param reservationType the reservationType to set
     */
    public void setReservationType(ReservationType reservationType) {
        this.reservationType = reservationType;
    }

    /**
     * @return the client
     */
    public Client getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * @return the roomType
     */
    public List <RoomTypeEntity> getRoomType() {
        return roomType;
    }

    /**
     * @param roomType the roomType to set
     */
    public void setRoomType(List <RoomTypeEntity> roomType) {
        this.roomType = roomType;
    }
    
}
