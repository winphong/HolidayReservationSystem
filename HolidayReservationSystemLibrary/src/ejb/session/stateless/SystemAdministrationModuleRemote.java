/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Employee;
import entity.Partner;
import java.util.List;

/**
 *
 * @author twp10
**/

public interface SystemAdministrationModuleRemote {

    Partner createNewPartner(Partner newPartner);

    List<Partner> viewAllPartner();

    List<Employee> viewAllEmployee();

    Employee createNewEmployee(Employee newEmployee);
    
}
