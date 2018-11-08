/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import entity.PartnerEntity;
import entity.PartnerReservationEntity;
import java.util.List;
import util.exception.ReservationNotFoundException;

/**
 *
 * @author Asus
 */
public interface PartnerReservationEntityControllerRemote {

    public List<PartnerReservationEntity> viewAllReservations(PartnerEntity partner);

    public PartnerReservationEntity retrieveReservationById(Long id) throws ReservationNotFoundException;
    
}
