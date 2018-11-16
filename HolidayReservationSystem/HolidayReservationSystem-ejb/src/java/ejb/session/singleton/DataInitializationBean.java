/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.EmployeeEntityControllerLocal;
import ejb.session.stateless.InventoryControllerLocal;
import ejb.session.stateless.PartnerEntityControllerLocal;
import entity.EmployeeEntity;
import entity.Inventory;
import entity.PartnerEntity;
import entity.RoomTypeEntity;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.EmployeeAccessRight;
import util.exception.UpdateInventoryException;

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
    @EJB
    private InventoryControllerLocal inventoryControllerLocal;
    
    @PostConstruct
    public void postConstruct() {        
        
        if (em.find(EmployeeEntity.class, new Long(1)) == null) {
            initializeEmployee();
            initializePartner();
        }
        
        if (em.find(RoomTypeEntity.class, new Long(1)) == null){
            initializeRoomType();
            //initializeRoom();
        }
        
        if (em.find(Inventory.class, new Long(137)) == null) {
            try {
                initializeInventory();
            } catch (UpdateInventoryException ex) {
                Logger.getLogger(DataInitializationBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        try {
            updateInventory();
        } catch (UpdateInventoryException ex) {
            Logger.getLogger(DataInitializationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void initializeEmployee(){
        //system admin
        EmployeeEntity employee = new EmployeeEntity("Win", "Phong", "sysadmin", "password", EmployeeAccessRight.SYSTEMADMINISTRATOR, "12345678", "sysadmin1@gmail.com");
        em.persist(employee);
        
        employee = new EmployeeEntity("Win", "Phong", "opmanager", "password", EmployeeAccessRight.OPERATIONMANAGER, "86195650", "opmanager@gmail.com");
        em.persist(employee);
        
        employee = new EmployeeEntity("Win", "Phong", "frontoffice", "password", EmployeeAccessRight.GUESTRELATIONOFFICER, "31734509", "frontoffice@gmail.com");
        em.persist(employee);
    }
    
    public void initializePartner(){
        PartnerEntity partner = new PartnerEntity("Holiday.com","holiday","password","01234567","A0000000A","holiday@gmail.com");
        em.persist(partner);
        //partnerEntityControllerLocal.createNewPartner(partner);
    }
    
    public void initializeRoomType() {
        RoomTypeEntity roomType = new RoomTypeEntity("Deluxe Room", "A comfortable yet affordable room for you and your loved ones", new BigDecimal(12.00), "Double", 2, "TV and Hot tub", 1);
        em.persist(roomType);
        roomType = new RoomTypeEntity("Premium Room", "Bagus!", new BigDecimal(24.00), "Double", 2, "Teapot", 2);
        em.persist(roomType);
    }
    
    /* public void initializeRoom() {
        
        RoomEntity room = new RoomEntity("0313");
        room.setRoomType(roomType);
        em.persist(room);
        room = new RoomEntity("Premium Room", "Bagus!", new BigDecimal(24.00), "Double", 2, "Teapot", 2);
        em.persist(room);
    } */
    
    public void initializeInventory() throws UpdateInventoryException{
        
        Inventory inventory;
        
        for(LocalDate date = LocalDate.now(); !date.isAfter(LocalDate.now().plusWeeks(1)); date = date.plusDays(1)) {      
            inventory = new Inventory(Date.valueOf(date));
            em.persist(inventory);
        }
        
        try{
            inventoryControllerLocal.updateAllInventory();
        }
        catch (Exception ex){
            throw new UpdateInventoryException(System.err.toString());
        }
    }
    
    private void updateInventory() throws UpdateInventoryException {
        
        try{
            inventoryControllerLocal.updateAllInventory();
        }
        catch (Exception ex){
            throw new UpdateInventoryException(System.err.toString());
        }
    }
}
