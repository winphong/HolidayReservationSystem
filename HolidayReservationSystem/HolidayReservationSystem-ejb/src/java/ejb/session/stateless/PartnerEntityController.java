/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PartnerEntity;
import entity.PartnerReservationEntity;
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
@Local (PartnerEntityControllerLocal.class)
@Remote (PartnerEntityControllerRemote.class)
public class PartnerEntityController implements PartnerEntityControllerRemote, PartnerEntityControllerLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;
    private PartnerEntity partner;

    public PartnerEntity createNewPartner(PartnerEntity newPartner) {
        
        em.persist(newPartner);
        em.flush();
        
        return newPartner;
    }
    
    public List<PartnerEntity> viewAllPartner() {
        
        Query query = em.createQuery("SELECT p FROM PartnerEntity p");
        
        return (List<PartnerEntity>) query.getResultList(); 
    }
    
    @Override
    public PartnerEntity partnerLogin(String username, String password) throws InvalidLoginCredentialException {

        try {

            PartnerEntity partner = retrievePartnerByUsername(username);

            if (partner.getPassword().equals(password)) {
                return partner;
            } else {
                throw new InvalidLoginCredentialException("Invalid password!");
            }

        } catch (NoResultException | NonUniqueResultException ex) {

            throw new InvalidLoginCredentialException("Invalid credential!");

        }

    }
    
    public PartnerEntity retrievePartnerByUsername(String username) {
        
        Query query = em.createQuery("SELECT p FROM PartnerEntity p WHERE p.username = :inUsername");
        query.setParameter("inUsername", username);
        
        try {
            
            return (PartnerEntity) query.getSingleResult();
            
        } catch ( NoResultException | NonUniqueResultException ex ) {
            
            System.err.println(ex.getMessage());
        }
        
        return null;
    }
    
    public PartnerEntity retrievePartnerById(Long partnerId) {
        
        try {
            
            return em.find(PartnerEntity.class, partnerId);
        
        } catch (IllegalArgumentException ex) {
            
            throw new IllegalArgumentException("No id");
            
        }
    }
    
    @Override
    public List <PartnerReservationEntity> retrieveAllReservations (Long partnerId){
        PartnerEntity partner = retrievePartnerById(partnerId);
        return partner.getReservation();
    }
}
