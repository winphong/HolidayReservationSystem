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
import entity.RoomTypeEntity;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.EmployeeAccessRight;

/**
 *
 * @author Asus
 */
@Singleton
@LocalBean
@Startup

public class DataInitializationBean {
    
    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    @EJB
    private EmployeeEntityControllerLocal employeeEntityControllerLocal;
    
    @EJB
    private PartnerEntityControllerLocal partnerEntityControllerLocal;
    
    @PostConstruct
    public void postConstruct(){
        
        if (em.find(EmployeeEntity.class, 1) == null) {
            initializeEmployee();
            initializePartner();
            initializeRoomType();
        }
        
    }

    public void initializePartner(){
        PartnerEntity partner = new PartnerEntity("Holiday.com","holiday","password","01234567","A0000000A","holiday@gmail.com");
        em.persist(partner);
        //partnerEntityControllerLocal.createNewPartner(partner);
    }
    
    public void initializeEmployee(){
        
        //system admin
        EmployeeEntity employee = new EmployeeEntity("manager", "sysadmin", "sysadmin", "password", EmployeeAccessRight.SYSTEMADMINISTRATOR, "00000000", "sysadmin1@gmail.com");
        em.persist(employee);
        //employeeEntityControllerLocal.createNewEmployee(employee);
    }
    
    public void initializeRoomType() {
        
        RoomTypeEntity roomType = new RoomTypeEntity("Deluxe Room", "A comfortable yet affordable room for you and your loved ones", ("12.00"), "Double", 2, "TV and Hot tub", 1);
        em.persist(roomType);
    }

}
