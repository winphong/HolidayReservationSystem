/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import entity.PartnerReservationEntity;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Asus
 */
@XmlRootElement
public class ViewAllReservationsRsp {
    
    private List<PartnerReservationEntity> reservations;

    public ViewAllReservationsRsp() {
    }

    public ViewAllReservationsRsp(List<PartnerReservationEntity> reservations) {
        this.reservations = reservations;
    }

    /**
     * @return the reservations
     */
    public List<PartnerReservationEntity> getReservations() {
        return reservations;
    }

    /**
     * @param reservations the reservations to set
     */
    public void setReservations(List<PartnerReservationEntity> reservations) {
        this.reservations = reservations;
    }
}
