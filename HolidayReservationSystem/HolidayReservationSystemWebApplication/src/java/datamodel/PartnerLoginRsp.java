/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import entity.PartnerEntity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Asus
 */

@XmlRootElement

public class PartnerLoginRsp {
    
    private PartnerEntity partner;

    public PartnerLoginRsp() {
    }

    public PartnerLoginRsp(PartnerEntity partner) {
        this.partner = partner;
    }
    
    

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
}
