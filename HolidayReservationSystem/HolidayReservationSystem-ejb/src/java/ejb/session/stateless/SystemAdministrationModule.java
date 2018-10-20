/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Employee;
import entity.Partner;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author twp10
 */
@Stateless
@Local (SystemAdministrationModuleLocal.class)
@Remote (SystemAdministrationModuleRemote.class)

public class SystemAdministrationModule implements SystemAdministrationModuleRemote, SystemAdministrationModuleLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    @Override
    public Employee createNewEmployee(Employee newEmployee) {
        
        em.persist(newEmployee);
        em.flush();
        
        return newEmployee;
    }
    
    @Override
    public List<Employee> viewAllEmployee() {
        
        Query query = em.createQuery("SELECT e FROM Employee e");
        
        return (List<Employee>) query.getResultList(); 
    }
    
    @Override
    public Partner createNewPartner(Partner newPartner) {
        
        em.persist(newPartner);
        em.flush();
        
        return newPartner;
    }
    
    @Override
    public List<Partner> viewAllPartner() {
        
        Query query = em.createQuery("SELECT p FROM Partner p");
        
        return (List<Partner>) query.getResultList(); 
    }
    
}
