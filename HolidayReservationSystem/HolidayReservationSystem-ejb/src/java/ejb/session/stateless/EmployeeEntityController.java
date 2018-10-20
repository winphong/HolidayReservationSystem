/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Employee;
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
    
    private Employee employee;    
    
    public Employee employeeLogin(String username, String password) {
        
        Query query = em.createQuery("SELECT e FROM Employee e WHERE e.userName = :inUsername AND e.password = :inPassword");
        query.setParameter("inUsername", username);
        query.setParameter("inPassword", password);
        
        try {
            
            employee = (Employee) query.getSingleResult();
            
            if (employee.getIsLoggedIn().equals(Boolean.TRUE)) {
                
                System.out.println("Employee is already logged in!");
            
            } else {
            
                employee.setIsLoggedIn(Boolean.TRUE);
                System.out.println("Log in successful!");
            }
            
            return employee;
        
        } catch ( NoResultException | NonUniqueResultException ex ) {
            
            System.out.println("Invalid credential!");
            
        }
        return null;
    }
    
    public void employeeLogout() {
        
        if (employee.getIsLoggedIn().equals(Boolean.FALSE)) {
            
            System.out.println("Employee is not logged in!");
        
        } else {
            
            employee.setIsLoggedIn(Boolean.FALSE);
            System.out.println("Succesfully logged out!");
        }
    }
    
    
}
