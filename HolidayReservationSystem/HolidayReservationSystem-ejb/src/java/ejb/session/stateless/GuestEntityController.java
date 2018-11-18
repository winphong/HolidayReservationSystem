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
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.GuestNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author twp10
 */
@Stateless
@Local(GuestEntityControllerLocal.class)
@Remote(GuestEntityControllerRemote.class)

public class GuestEntityController implements GuestEntityControllerRemote, GuestEntityControllerLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public GuestEntity guestLogin(String username, String password) throws GuestNotFoundException, InvalidLoginCredentialException {
        em.flush();
        try {
            GuestEntity guest = retrieveGuestByUsername(username);
            
            if (guest.getPassword().equals(password)) {
                return guest;
            } else {
                throw new InvalidLoginCredentialException("Invalid password!");
            }
            
        } catch (NoResultException ex) {
            throw new GuestNotFoundException("Invalid guest!");
        }

    }

    @Override
    public GuestEntity registerGuest(GuestEntity newGuest) {

        em.persist(newGuest);
        em.flush();

        return newGuest;
    }
    
    @Override
    public GuestEntity retrieveGuestByUsername(String username) throws GuestNotFoundException{
        
        Query query = em.createQuery("SELECT g FROM GuestEntity g WHERE g.username = :inUsername");
        query.setParameter("inUsername", username);
        
        try {
            
            return (GuestEntity) query.getSingleResult();
            
        } catch ( NoResultException | NonUniqueResultException ex ) {
            
            throw new GuestNotFoundException ("Guest with Username " + username + " does not exist!");
        }
    }
    
    @Override
    public GuestEntity retrieveGuestById(Long guestId) {
        
        try {
            
            return em.find(GuestEntity.class, guestId);
        
        } catch (IllegalArgumentException ex) {
            
            throw new IllegalArgumentException("No id");
            
        }
    }
}
