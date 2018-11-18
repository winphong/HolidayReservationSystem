/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import entity.ReservationEntity;
import entity.ReservationLineItemEntity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Asus
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ReservationLineItemContainer {
    ReservationLineItemEntity item;
    ReservationEntity reservation;
    public ReservationLineItemEntity getItem(){
        return this.item;
    }
    
    public void setItem(ReservationLineItemEntity item){
        this.item = item;
        reservation= item.getReservation();
    }
    
    public ReservationLineItemContainer(){
        item = null;
        reservation = null;
    }
    
    public ReservationLineItemContainer(ReservationLineItemEntity item){
        setItem(item);
    }
}
