/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.EmployeeEntityControllerLocal;
import ejb.session.stateless.PartnerEntityControllerLocal;
import entity.EmployeeEntity;
import entity.PartnerEntity;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import util.enumeration.EmployeeAccessRight;

/**
 *
 * @author Asus
 */
@Singleton
@LocalBean
@Startup
public class DataInitializationBean {

    @EJB
    private EmployeeEntityControllerLocal employeeEntityControllerLocal;

    @EJB
    private PartnerEntityControllerLocal partnerEntityControllerLocal;
    
    @PostConstruct
    public void postConstruct(){
        initializeEmployee();
        initializePartner();
    }

    public void initializePartner(){
        PartnerEntity newPartner = new PartnerEntity("Holiday.com","holiday","password","01234567","A0000000A","holiday@gmail.com");
        partnerEntityControllerLocal.createNewPartner(newPartner);
    }
    
    public void initializeEmployee(){
        
        //system admin
        EmployeeEntity newEmployee = new EmployeeEntity("manager", "sysadmin", "sysadmin", "password", EmployeeAccessRight.SYSTEMADMINISTRATOR, "00000000", "sysadmin1@gmail.com");
        employeeEntityControllerLocal.createNewEmployee(newEmployee);
    }
}
