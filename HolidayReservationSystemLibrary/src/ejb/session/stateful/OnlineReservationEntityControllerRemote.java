/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import entity.GuestEntity;
import entity.OnlineReservationEntity;
import java.util.List;
import util.exception.ReservationNotFoundException;

/**
 *
 * @author Asus
 */
public interface OnlineReservationEntityControllerRemote {

    public List<OnlineReservationEntity> viewAllReservations(GuestEntity guest);

    public OnlineReservationEntity retrieveReservationById(Long id) throws ReservationNotFoundException;
    
}
