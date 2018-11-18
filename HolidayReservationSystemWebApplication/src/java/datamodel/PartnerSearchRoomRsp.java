/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import entity.RoomTypeEntity;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Asus
 */
@XmlRootElement

public class PartnerSearchRoomRsp {
    
    private List<RoomTypeEntity> availableRooms;

    public PartnerSearchRoomRsp() {
    }

    public PartnerSearchRoomRsp(List<RoomTypeEntity> availableRooms) {
        this.availableRooms = availableRooms;
    }

    
    public List<RoomTypeEntity> getAvailableRooms() {
        return availableRooms;
    }

    /**
     * @param availableRooms the availableRooms to set
     */
    public void setAvailableRooms(List<RoomTypeEntity> availableRooms) {
        this.availableRooms = availableRooms;
    }
}
