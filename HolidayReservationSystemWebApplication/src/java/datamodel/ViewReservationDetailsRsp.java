/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import entity.PartnerReservationEntity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Asus
 */
@XmlRootElement

public class ViewReservationDetailsRsp {
    
    private PartnerReservationEntity partnerReservationEntity;

    public ViewReservationDetailsRsp() {
    }

    public ViewReservationDetailsRsp(PartnerReservationEntity partnerReservationEntity) {
        this.partnerReservationEntity = partnerReservationEntity;
    }

    /**
     * @return the partnerReservationEntity
     */
    public PartnerReservationEntity getPartnerReservationEntity() {
        return partnerReservationEntity;
    }

    /**
     * @param partnerReservationEntity the partnerReservationEntity to set
     */
    public void setPartnerReservationEntity(PartnerReservationEntity partnerReservationEntity) {
        this.partnerReservationEntity = partnerReservationEntity;
    }
    
}
