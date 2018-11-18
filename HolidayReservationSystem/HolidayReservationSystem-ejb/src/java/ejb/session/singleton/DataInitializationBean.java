/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.EmployeeEntityControllerLocal;
import ejb.session.stateless.InventoryControllerLocal;
import ejb.session.stateless.PartnerEntityControllerLocal;
import ejb.session.stateless.RoomRateEntityControllerLocal;
import ejb.session.stateless.RoomTypeEntityControllerLocal;
import entity.EmployeeEntity;
import entity.Inventory;
import entity.PartnerEntity;
import entity.RoomEntity;
import entity.RoomRateEntity;
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
import util.exception.RoomTypeNotFoundException;
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
    @EJB
    private RoomTypeEntityControllerLocal roomTypeEntityControllerLocal;

    @PostConstruct
    public void postConstruct() {

        if (em.find(EmployeeEntity.class, new Long(1)) == null) {
            initializeEmployee();
            initializePartner();
        }

        if (em.find(RoomTypeEntity.class, new Long(1)) == null) {
            initializeRoomType();
        }
        
        if (em.find(RoomEntity.class, new Long(1)) == null){
            initializeRoom();
        }
        
        if (em.find(RoomRateEntity.class, new Long(1))==null){
            initializeRoomRate();
        }

        if (em.find(Inventory.class, new Long(1)) == null) {
            try {
                initializeInventory();
            } catch (UpdateInventoryException ex) {
                Logger.getLogger(DataInitializationBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void initializeEmployee() {
        //system admin
        EmployeeEntity employee = new EmployeeEntity("Win", "Phong", "sysadmin", "password", EmployeeAccessRight.SYSTEMADMINISTRATOR, "12345678", "sysadmin1@gmail.com");
        em.persist(employee);
        employee = new EmployeeEntity("Wei", "Xiang", "opmanager", "password", EmployeeAccessRight.OPERATIONMANAGER, "86195650", "opmanager@gmail.com");
        em.persist(employee);
        employee = new EmployeeEntity("Kai", "Ming", "frontoffice", "password", EmployeeAccessRight.GUESTRELATIONOFFICER, "31734509", "frontoffice@gmail.com");
        em.persist(employee);
        employee = new EmployeeEntity("Jun", "Jie", "sales", "password", EmployeeAccessRight.SALESMANAGER, "16161616", "sales@gmail.com");
        em.persist(employee);
    }

    public void initializePartner() {
        PartnerEntity partner = new PartnerEntity("Holiday.com", "holiday", "password", "01234567", "A0000000A", "holiday@gmail.com");
        em.persist(partner);
        //partnerEntityControllerLocal.createNewPartner(partner);
    }

    public void initializeRoomType() {
        RoomTypeEntity roomType = new RoomTypeEntity("Deluxe Room", "This is a Deluxe Room", new BigDecimal(12.00), "Two Single Bed", 2, "Teapot and TV", 1);
        em.persist(roomType);
        roomType = new RoomTypeEntity("Premier Room", "This is a Premier Room!", new BigDecimal(24.00), "One King Sized Bed", 2, "TV and Bath Tub", 2);
        em.persist(roomType);
        roomType = new RoomTypeEntity("Family Room", "This is a Family Room", new BigDecimal(48.00), "One King Sized Bed and Two Single Bed", 4, "Refrigerator and TV", 3);
        em.persist(roomType);
        roomType = new RoomTypeEntity("Junior Suite", "This is a Junior Suite", new BigDecimal(48.00), "One King Sized Bed", 2, "Kitchenette and Bath Tub", 4);
        em.persist(roomType);
        roomType = new RoomTypeEntity("Grand Suite", "This is a Grand Suite", new BigDecimal(60.00), "One King Sized Bed", 2, "Living Room and Kitchenette", 5);
        em.persist(roomType);
    }

    public void initializeInventory() throws UpdateInventoryException {

        Inventory inventory;

        for (LocalDate date = LocalDate.now(); !date.isAfter(LocalDate.now().plusYears(1)); date = date.plusDays(1)) {
            inventory = new Inventory(Date.valueOf(date));
            em.persist(inventory);
        }

        try {
            inventoryControllerLocal.updateAllInventory();
        } catch (Exception ex) {
            throw new UpdateInventoryException(System.err.toString());
        }
    }

    public void initializeRoomRate() {
        //Deluxe Room
        try {
            RoomRateEntity roomRate = new RoomRateEntity("Published Rate", new BigDecimal("2000"));
            RoomTypeEntity roomType = roomTypeEntityControllerLocal.retrieveRoomTypeByName("Deluxe Room");
            roomRate.setRoomType(roomType);
            roomType.getRoomRate().add(roomRate);
            em.persist(roomRate);
            roomRate = new RoomRateEntity("Normal Rate", new BigDecimal("1800"));
            roomRate.setRoomType(roomType);
            roomType.getRoomRate().add(roomRate);
            em.persist(roomRate);
            //Family Room
            roomRate = new RoomRateEntity("Published Rate", new BigDecimal("1500"));
            roomType = roomTypeEntityControllerLocal.retrieveRoomTypeByName("Family Room");
            roomRate.setRoomType(roomType);
            roomType.getRoomRate().add(roomRate);
            em.persist(roomRate);
            roomRate = new RoomRateEntity("Normal Rate", new BigDecimal("1200"));
            roomRate.setRoomType(roomType);
            roomType.getRoomRate().add(roomRate);
            em.persist(roomRate);
            //Grand Suite
            roomRate = new RoomRateEntity("Published Rate", new BigDecimal("1200"));
            roomType = roomTypeEntityControllerLocal.retrieveRoomTypeByName("Grand Suite");
            roomRate.setRoomType(roomType);
            roomType.getRoomRate().add(roomRate);
            em.persist(roomRate);
            roomRate = new RoomRateEntity("Normal Rate", new BigDecimal("1000"));
            roomRate.setRoomType(roomType);
            roomType.getRoomRate().add(roomRate);
            em.persist(roomRate);
            //Junior Suite
            roomRate = new RoomRateEntity("Published Rate", new BigDecimal("1000"));
            roomType = roomTypeEntityControllerLocal.retrieveRoomTypeByName("Junior Suite");
            roomRate.setRoomType(roomType);
            roomType.getRoomRate().add(roomRate);
            em.persist(roomRate);
            roomRate = new RoomRateEntity("Normal Rate", new BigDecimal("800"));
            roomRate.setRoomType(roomType);
            roomType.getRoomRate().add(roomRate);
            em.persist(roomRate);
            //Premier Room
            roomRate = new RoomRateEntity("Published Rate", new BigDecimal("800"));
            roomType = roomTypeEntityControllerLocal.retrieveRoomTypeByName("Premier Room");
            roomRate.setRoomType(roomType);
            roomType.getRoomRate().add(roomRate);
            em.persist(roomRate);
            roomRate = new RoomRateEntity("Normal Rate", new BigDecimal("500"));
            roomRate.setRoomType(roomType);
            roomType.getRoomRate().add(roomRate);
            em.persist(roomRate);
        } catch (RoomTypeNotFoundException ex) {
                Logger.getLogger(DataInitializationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initializeRoom() {
        //Deluxe Room
        try {
            RoomEntity room = new RoomEntity("0101");
            RoomTypeEntity roomType = roomTypeEntityControllerLocal.retrieveRoomTypeByName("Deluxe Room");
            room.setRoomType(roomType);
            roomType.getRoom().add(room);
            em.persist(room);
            room = new RoomEntity("0102");
            room.setRoomType(roomType);
            roomType.getRoom().add(room);
            em.persist(room);
            room = new RoomEntity("0103");
            room.setRoomType(roomType);
            roomType.getRoom().add(room);
            em.persist(room);
            room = new RoomEntity("0104");
            room.setRoomType(roomType);
            roomType.getRoom().add(room);
            em.persist(room);
            room = new RoomEntity("0105");
            room.setRoomType(roomType);
            roomType.getRoom().add(room);
            em.persist(room);
            //Family Room
            room = new RoomEntity("0201");
            roomType = roomTypeEntityControllerLocal.retrieveRoomTypeByName("Family Room");
            room.setRoomType(roomType);
            roomType.getRoom().add(room);
            em.persist(room);
            room = new RoomEntity("0202");
            room.setRoomType(roomType);
            roomType.getRoom().add(room);
            em.persist(room);
            room = new RoomEntity("0203");
            room.setRoomType(roomType);
            roomType.getRoom().add(room);
            em.persist(room);
            room = new RoomEntity("0204");
            room.setRoomType(roomType);
            roomType.getRoom().add(room);
            em.persist(room);
            room = new RoomEntity("0205");
            room.setRoomType(roomType);
            roomType.getRoom().add(room);
            em.persist(room);
            //Grand Suite
            room = new RoomEntity("0301");
            roomType = roomTypeEntityControllerLocal.retrieveRoomTypeByName("Grand Suite");
            room.setRoomType(roomType);
            roomType.getRoom().add(room);
            em.persist(room);
            room = new RoomEntity("0302");
            room.setRoomType(roomType);
            roomType.getRoom().add(room);
            em.persist(room);
            room = new RoomEntity("0303");
            room.setRoomType(roomType);
            roomType.getRoom().add(room);
            em.persist(room);
            room = new RoomEntity("0304");
            room.setRoomType(roomType);
            roomType.getRoom().add(room);
            em.persist(room);
            room = new RoomEntity("0305");
            room.setRoomType(roomType);
            roomType.getRoom().add(room);
            em.persist(room);
            //Junior Suite
            room = new RoomEntity("0401");
            roomType = roomTypeEntityControllerLocal.retrieveRoomTypeByName("Junior Suite");
            room.setRoomType(roomType);
            roomType.getRoom().add(room);
            em.persist(room);
            room = new RoomEntity("0402");
            room.setRoomType(roomType);
            roomType.getRoom().add(room);
            em.persist(room);
            room = new RoomEntity("0403");
            room.setRoomType(roomType);
            roomType.getRoom().add(room);
            em.persist(room);
            room = new RoomEntity("0404");
            room.setRoomType(roomType);
            roomType.getRoom().add(room);
            em.persist(room);
            room = new RoomEntity("0405");
            room.setRoomType(roomType);
            roomType.getRoom().add(room);
            em.persist(room);
            //Premier Room
            room = new RoomEntity("0501");
            roomType = roomTypeEntityControllerLocal.retrieveRoomTypeByName("Premier Room");
            room.setRoomType(roomType);
            roomType.getRoom().add(room);
            em.persist(room);
            room = new RoomEntity("0502");
            room.setRoomType(roomType);
            roomType.getRoom().add(room);
            em.persist(room);
            room = new RoomEntity("0503");
            room.setRoomType(roomType);
            roomType.getRoom().add(room);
            em.persist(room);
            room = new RoomEntity("0504");
            room.setRoomType(roomType);
            roomType.getRoom().add(room);
            em.persist(room);
            room = new RoomEntity("0505");
            room.setRoomType(roomType);
            roomType.getRoom().add(room);
            em.persist(room);
        
        } catch (RoomTypeNotFoundException ex) {
                Logger.getLogger(DataInitializationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
