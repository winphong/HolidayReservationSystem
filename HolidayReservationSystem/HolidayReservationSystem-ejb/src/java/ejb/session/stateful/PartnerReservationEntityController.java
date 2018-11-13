/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import entity.PartnerEntity;
import entity.PartnerReservationEntity;
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
@Local (PartnerReservationEntityControllerLocal.class)
@Remote (PartnerReservationEntityControllerRemote.class)
public class PartnerReservationEntityController implements PartnerReservationEntityControllerRemote, PartnerReservationEntityControllerLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    public List<PartnerReservationEntity> viewAllReservations(PartnerEntity partner) {

        Query query = em.createQuery("SELECT pr FROM PartnerReservationEntity pr WHERE pr.partner =:inPartner");
        query.setParameter("inPartner", partner);
        List<PartnerReservationEntity> reservations = (List<PartnerReservationEntity>) query.getResultList();

        return reservations;
    }
    
    public PartnerReservationEntity retrieveReservationById(Long id) throws ReservationNotFoundException {

        Query query = em.createQuery("SELECT ore FROM PartnerReservationEntity ore WHERE ore.partnerReservationId =:inId");
        query.setParameter("inId", id);

        try {

            return (PartnerReservationEntity) query.getSingleResult();

        } catch (NoResultException | NonUniqueResultException ex) {

            throw new ReservationNotFoundException("Reservation " + id + "does not exist!");
        }
    }
}
