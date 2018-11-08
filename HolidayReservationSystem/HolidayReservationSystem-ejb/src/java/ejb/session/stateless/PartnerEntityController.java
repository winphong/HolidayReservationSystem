/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PartnerEntity;
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
    
    public PartnerEntity partnerLogin(String username, String password) throws InvalidLoginCredentialException {

        try {

            Query query = em.createQuery("SELECT p FROM PartnerEntity g WHERE g.username = :inUsername");
            query.setParameter("inUsername", username);

            if (partner.getPassword().equals(password)) {
                partner = (PartnerEntity) query.getSingleResult();
                return partner;
            } else {
                throw new InvalidLoginCredentialException("Invalid password!");
            }

        } catch (NoResultException | NonUniqueResultException ex) {

            throw new InvalidLoginCredentialException("Invalid credential!");

        }

    }
    
}
