/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import entity.PartnerReservationEntity;
import util.exception.ReservationNotFoundException;

/**
 *
 * @author Asus
 */

public interface PartnerReservationEntityControllerLocal {
    public PartnerReservationEntity retrieveReservationById(Long id) throws ReservationNotFoundException;
}
