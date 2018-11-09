/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import entity.ReservationEntity;



/**
 *
 * @author Asus
 */

public interface WalkinReservationEntityControllerRemote {
    public void reserveRoom(ReservationEntity newReservation);
}
