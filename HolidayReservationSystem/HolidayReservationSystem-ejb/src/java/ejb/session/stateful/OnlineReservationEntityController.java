/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import entity.GuestEntity;
import entity.OnlineReservationEntity;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.ReservationNotFoundException;

/**
 *
 * @author Asus
 */
@Stateful
@Local (OnlineReservationEntityControllerLocal.class)
@Remote (OnlineReservationEntityControllerRemote.class)
public class OnlineReservationEntityController implements OnlineReservationEntityControllerRemote, OnlineReservationEntityControllerLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    
    public List<OnlineReservationEntity> viewAllReservations(GuestEntity guest) {

        Query query = em.createQuery("SELECT or FROM OnlineReservationEntity or WHERE or.guest =:inGuest");
        query.setParameter("inGuest", guest);
        List<OnlineReservationEntity> reservations = (List<OnlineReservationEntity>) query.getResultList();

        return reservations;
    }
    
    public OnlineReservationEntity retrieveReservationById(Long id) throws ReservationNotFoundException {

        Query query = em.createQuery("SELECT or FROM OnlineReservationEntity or WHERE or.id=:inId");
        query.setParameter("inId", id);

        try {

            return (OnlineReservationEntity) query.getSingleResult();

        } catch (NoResultException | NonUniqueResultException ex) {

            throw new ReservationNotFoundException("Reservation " + id + "does not exist!");
        }
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
