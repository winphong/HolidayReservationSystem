/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.EmployeeEntity;
import java.util.List;
import util.exception.EmployeeNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author twp10
 */
public interface EmployeeEntityControllerRemote {

    public EmployeeEntity employeeLogin(String username, String password) throws EmployeeNotFoundException, InvalidLoginCredentialException;

    public EmployeeEntity createNewEmployee(EmployeeEntity newEmployee);

    public List<EmployeeEntity> viewAllEmployee();
    
}
