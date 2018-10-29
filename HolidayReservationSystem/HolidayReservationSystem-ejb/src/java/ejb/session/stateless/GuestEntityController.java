/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.GuestEntity;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author twp10
 */
@Stateless
@Local (GuestEntityControllerLocal.class)
@Remote (GuestEntityControllerRemote.class)

public class GuestEntityController implements GuestEntityControllerRemote, GuestEntityControllerLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public GuestEntity guestLogin() {
        
        return null;
    }
    
    public GuestEntity registerGuest(GuestEntity newGuest) {
        
        em.persist(newGuest);
        em.flush();
        
        return newGuest;
    }
    
    
}