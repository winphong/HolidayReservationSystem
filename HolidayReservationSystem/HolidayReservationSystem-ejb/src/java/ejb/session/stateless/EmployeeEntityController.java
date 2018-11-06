/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.EmployeeEntity;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author twp10
 */
@Stateless
@Local (EmployeeEntityControllerLocal.class)
@Remote (EmployeeEntityControllerRemote.class)

public class EmployeeEntityController implements EmployeeEntityControllerRemote, EmployeeEntityControllerLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;
    
    private EmployeeEntity employee;    
    
    public EmployeeEntity employeeLogin(String username, String password) {
        
        Query query = em.createQuery("SELECT e FROM EmployeeEntity e WHERE e.username = :inUsername AND e.password = :inPassword");
        query.setParameter("inUsername", username);
        query.setParameter("inPassword", password);
        
        try {
            
            employee = (EmployeeEntity) query.getSingleResult();
            
            return employee;
        
        } catch ( NoResultException | NonUniqueResultException ex ) {
            
            System.out.println("Invalid credential!");
            
        }
        return null;
    }
    
    public EmployeeEntity createNewEmployee(EmployeeEntity newEmployee) {
        
        em.persist(newEmployee);
        em.flush();
        
        return newEmployee;
    }
    
    public List<EmployeeEntity> viewAllEmployee() {
        
        Query query = em.createQuery("SELECT e FROM EmployeeEntity e");
        
        return (List<EmployeeEntity>) query.getResultList(); 
    }
    
    
}
