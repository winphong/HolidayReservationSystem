/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.EmployeeEntity;
import java.util.List;

/**
 *
 * @author twp10
 */
public interface EmployeeEntityControllerRemote {

    public EmployeeEntity employeeLogin(String username, String password);

    public EmployeeEntity createNewEmployee(EmployeeEntity newEmployee);

    public List<EmployeeEntity> viewAllEmployee();
    
}
