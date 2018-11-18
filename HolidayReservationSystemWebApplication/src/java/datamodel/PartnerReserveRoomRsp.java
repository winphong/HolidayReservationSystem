/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import entity.ReservationEntity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Asus
 */
@XmlRootElement

public class PartnerReserveRoomRsp {
    
    private ReservationEntity reservation;

    public PartnerReserveRoomRsp() {
    }

    public PartnerReserveRoomRsp(ReservationEntity reservation) {
        this.reservation = reservation;
    }

    /**
     * @return the reservation
     */
    public ReservationEntity getReservation() {
        return reservation;
    }

    /**
     * @param reservation the reservation to set
     */
    public void setReservation(ReservationEntity reservation) {
        this.reservation = reservation;
    }
    
    
}
