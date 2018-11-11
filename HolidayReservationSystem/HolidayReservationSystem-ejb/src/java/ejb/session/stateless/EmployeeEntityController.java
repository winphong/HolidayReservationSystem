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
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author twp10
 */
@Stateless
@Local(EmployeeEntityControllerLocal.class)
@Remote(EmployeeEntityControllerRemote.class)

public class EmployeeEntityController implements EmployeeEntityControllerRemote, EmployeeEntityControllerLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    private EmployeeEntity employee;

    public EmployeeEntity employeeLogin(String username, String password) throws InvalidLoginCredentialException{

        try {
            Query query = em.createQuery("SELECT e FROM EmployeeEntity e WHERE e.username = :inUsername");

            query.setParameter("inUsername", username);

            if (employee.getPassword().equals(password)) {

                employee = (EmployeeEntity) query.getSingleResult();
                return employee;

            } else {
               throw new InvalidLoginCredentialException("Invalid password!");
            }
        } catch (InvalidLoginCredentialException ex) {
            System.out.println("Invalid user!");
        }
        return null;
    }

    @Override
    public EmployeeEntity createNewEmployee(EmployeeEntity newEmployee) {

        em.persist(newEmployee);
        em.flush();

        return newEmployee;
    }

    @Override
    public List<EmployeeEntity> viewAllEmployee() {

        Query query = em.createQuery("SELECT e FROM EmployeeEntity e");

        return (List<EmployeeEntity>) query.getResultList();
    }
    
    public EmployeeEntity retrieveEmployeeByUsername(String username) {
        
        Query query = em.createQuery("SELECT e FROM EmployeeEntity e WHERE e.username = :inUsername");
        query.setParameter("inUsername", username);
        
        try {
            
            return (EmployeeEntity) query.getSingleResult();
            
        } catch ( NoResultException | NonUniqueResultException ex ) {
            
            System.err.println(ex.getMessage());
        }
        
        return null;
    }
    
    public EmployeeEntity retrieveEmployeeById(Long employeeId) {
        
        try {
            
            return em.find(EmployeeEntity.class, employeeId);
        
        } catch (IllegalArgumentException ex) {
            
            throw new IllegalArgumentException("No id");
            
        }
    }
}
