/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import util.enumeration.RoomStatus;
import static util.enumeration.RoomStatus.VACANT;

/**
 *
 * @author twp10
 */
@Entity
public class RoomEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    @Column(length = 4, nullable = false, unique = true)
    private String roomNumber;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomStatus roomStatus;
    private String guest;
    private Boolean isReady;
    private Boolean isDisabled;

    @ManyToOne
    private RoomTypeEntity roomType;
    
    @ManyToOne
    private ReservationEntity currentReservation;
    
    @ManyToOne
    private ReservationEntity nextReservation;

    public RoomEntity() {
        this.isDisabled = Boolean.FALSE;
        this.roomStatus = VACANT;
        this.isReady = Boolean.TRUE;
    }

    public RoomEntity(String roomNumber) {
        this();
        this.roomNumber = roomNumber;
    }
    
    
    public Long getRoomId() {
        return roomId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomId != null ? roomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the roomId fields are not set
        if (!(object instanceof RoomEntity)) {
            return false;
        }
        RoomEntity other = (RoomEntity) object;
        if ((this.roomId == null && other.roomId != null) || (this.roomId != null && !this.roomId.equals(other.roomId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Room[ id=" + roomId + " ]";
    }

    /**
     * @return the roomNumber
     */
    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     * @param roomNumber the roomNumber to set
     */
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * @return the roomStatus
     */
    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    /**
     * @param roomStatus the roomStatus to set
     */
    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    /**
     * @return the guest
     */
    public String getGuest() {
        return guest;
    }

    /**
     * @param guest the guest to set
     */
    public void setGuest(String guest) {
        this.guest = guest;
    }

    /**
     * @return the roomType
     */
    public RoomTypeEntity getRoomType() {
        return roomType;
    }

    /**
     * @param roomType the roomType to set
     */
    public void setRoomType(RoomTypeEntity roomType) {
        this.roomType = roomType;
    }

    /**
     * @return the isDisabled
     */
    public Boolean getIsDisabled() {
        return isDisabled;
    }

    /**
     * @param isDisabled the isDisabled to set
     */
    public void setIsDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    /**
     * @return the reservation
     */
    public ReservationEntity getCurrentReservation() {
        return currentReservation;
    }

    /**
     * @param reservation the reservation to set
     */
    public void setCurrentReservation(ReservationEntity reservation) {
        this.currentReservation = reservation;
    }

    /**
     * @return the nextReservation
     */
    public ReservationEntity getNextReservation() {
        return nextReservation;
    }

    /**
     * @param nextReservation the nextReservation to set
     */
    public void setNextReservation(ReservationEntity nextReservation) {
        this.nextReservation = nextReservation;
    }

    /**
     * @return the isReady
     */
    public Boolean getIsReady() {
        return isReady;
    }

    /**
     * @param isReady the isReady to set
     */
    public void setIsReady(Boolean isReady) {
        this.isReady = isReady;
    }
    
}
