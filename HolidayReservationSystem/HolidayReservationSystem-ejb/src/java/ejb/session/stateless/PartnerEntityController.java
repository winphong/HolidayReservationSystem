/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PartnerEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author twp10
 */
@Stateless
public class PartnerEntityController implements PartnerEntityControllerRemote, PartnerEntityControllerLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    public PartnerEntity createNewPartner(PartnerEntity newPartner) {
        
        em.persist(newPartner);
        em.flush();
        
        return newPartner;
    }
    
    public List<PartnerEntity> viewAllPartner() {
        
        Query query = em.createQuery("SELECT p FROM PartnerEntity p");
        
        return (List<PartnerEntity>) query.getResultList(); 
    }
    
}
