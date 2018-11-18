/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import entity.RoomTypeEntity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Asus
 */
@XmlRootElement

public class RetrieveRoomTypeRsp {
    
    private RoomTypeEntity roomType;


    public RetrieveRoomTypeRsp() {
    }
    
    
    public RetrieveRoomTypeRsp(RoomTypeEntity roomType) {
        this.roomType = roomType;
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
    
    
}
