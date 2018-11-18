/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import entity.ReservationLineItemEntity;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Asus
 */
@XmlRootElement

public class RetrieveAllReservationItemsRsp {
    private List<ReservationLineItemEntity> items;

    public RetrieveAllReservationItemsRsp() {
    }

    public RetrieveAllReservationItemsRsp(List<ReservationLineItemEntity> items) {
        this.items = items;
    }

    /**
     * @return the items
     */
    public List<ReservationLineItemEntity> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<ReservationLineItemEntity> items) {
        this.items = items;
    }
}
