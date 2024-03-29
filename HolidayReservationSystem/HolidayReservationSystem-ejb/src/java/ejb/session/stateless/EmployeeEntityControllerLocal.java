/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.EmployeeEntity;
import util.exception.EmployeeNotFoundException;

/**
 *
 * @author twp10
 */
public interface EmployeeEntityControllerLocal {
    
     public EmployeeEntity createNewEmployee(EmployeeEntity newEmployee);

    public EmployeeEntity retrieveEmployeeById(Long employeeId);

    public EmployeeEntity retrieveEmployeeByUsername(String username) throws EmployeeNotFoundException;
}
