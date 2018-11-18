/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Asus
 */

@XmlRootElement
        
public class ReserveRoomRsp {
    private Boolean reserve;

    public ReserveRoomRsp() {
    }

    public ReserveRoomRsp(Boolean reserve) {
        this.reserve = reserve;
    }

    /**
     * @return the reserve
     */
    public Boolean getReserve() {
        return reserve;
    }

    /**
     * @param reserve the reserve to set
     */
    public void setReserve(Boolean reserve) {
        this.reserve = reserve;
    }
    
    
}
