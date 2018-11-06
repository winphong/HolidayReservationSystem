/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author Asus
 */

@Embeddable
public class RoomNumber implements Serializable{
    private Integer floor;
    private Integer number;

    public RoomNumber() {
    }

    public RoomNumber(Integer floor, Integer number) {
        this();
        this.floor = floor;
        this.number = number;
    }

    /**
     * @return the floor
     */
    public Integer getFloor() {
        return floor;
    }

    /**
     * @param floor the floor to set
     */
    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    /**
     * @return the number
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(Integer number) {
        this.number = number;
    }
    
    
}
