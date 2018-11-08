/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.EmployeeEntity;
import entity.GuestEntity;
import entity.OnlineReservationEntity;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    private GuestEntity guest;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public GuestEntity guestLogin(String username, String password) throws InvalidLoginCredentialException {

        try {

            Query query = em.createQuery("SELECT g FROM GuestEntity g WHERE g.username = :inUsername");
            query.setParameter("inUsername", username);

            if (guest.getPassword().equals(password)) {
                guest = (GuestEntity) query.getSingleResult();
                return guest;
            } else {
                throw new InvalidLoginCredentialException("Invalid password!");
            }

        } catch (NoResultException | NonUniqueResultException ex) {

            throw new InvalidLoginCredentialException("Invalid credential!");

        }

    }

    public GuestEntity registerGuest(GuestEntity newGuest) {

        em.persist(newGuest);
        em.flush();

        return newGuest;
    }
    
}
