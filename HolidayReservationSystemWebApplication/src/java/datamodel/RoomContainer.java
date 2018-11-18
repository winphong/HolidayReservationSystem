/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import entity.RoomEntity;
import entity.RoomTypeEntity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Asus
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="RoomContainer")
public class RoomContainer {
    RoomEntity room;
    RoomTypeEntity roomType;
    public RoomEntity getRoom(){
        return this.room;
    }
    
    public void setRoom(RoomEntity room){
        this.room = room;
        roomType = room.getRoomType();
    }
    
    public RoomContainer(){
        room = null;
        roomType = null;
    }
    
    public RoomContainer(RoomEntity room){
        setRoom(room);
    }
}
