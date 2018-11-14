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
import util.exception.EmployeeNotFoundException;
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

    @Override
    public EmployeeEntity employeeLogin(String username, String password) throws EmployeeNotFoundException, InvalidLoginCredentialException{

        try {
            EmployeeEntity employee = retrieveEmployeeByUsername(username);
            if (employee.getPassword().equals(password)){
                return employee;
            }
            else{
                throw new InvalidLoginCredentialException("Invalid password!");
            }
        } catch (EmployeeNotFoundException ex) {
            throw new InvalidLoginCredentialException("Invalid user!");
        } 
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
    
    @Override
    public EmployeeEntity retrieveEmployeeByUsername(String username) throws EmployeeNotFoundException{
        
        Query query = em.createQuery("SELECT e FROM EmployeeEntity e WHERE e.username = :inUsername");
        query.setParameter("inUsername", username);
        
        try {
            
            return (EmployeeEntity) query.getSingleResult();
            
        } catch ( NoResultException | NonUniqueResultException ex ) {
            
            throw new EmployeeNotFoundException("Employee with Username " + username + " does not exist!");
        } catch (Exception ex){
            throw new EmployeeNotFoundException("Error: Employee not found! " + ex.getMessage());
        }

    }
    
    public EmployeeEntity retrieveEmployeeById(Long employeeId) {
        
        try {
            
            return em.find(EmployeeEntity.class, employeeId);
        
        } catch (IllegalArgumentException ex) {
            
            throw new IllegalArgumentException("No id");
            
        }
    }
}
