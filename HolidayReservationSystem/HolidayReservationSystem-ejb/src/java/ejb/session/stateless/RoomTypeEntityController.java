/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Inventory;
import entity.RoomTypeEntity;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author twp10
 */
@Stateless
@Local (RoomTypeEntityControllerLocal.class)
@Remote (RoomTypeEntityControllerRemote.class)

public class RoomTypeEntityController implements RoomTypeEntityControllerRemote, RoomTypeEntityControllerLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;
    
    @EJB
    private InventoryControllerLocal inventoryControllerLocal;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public RoomTypeEntity createNewRoomType(RoomTypeEntity newRoomType) {
        
        em.persist(newRoomType);
        em.flush();
        
        return newRoomType;
    }
    
    public RoomTypeEntity retrieveRoomTypeByName(String name) {
        
        Query query = em.createQuery("SELECT rt FROM RoomType rt WHERE rt.name=:inName");
        query.setParameter("inName", name);
        
        try {
            
            return (RoomTypeEntity) query.getSingleResult();
            
        } catch (NoResultException | NonUniqueResultException ex) {
            
            System.out.println("Room type " + name + " does not exist!");
        }
        
        return null;
    }

    // roomType retrieved the retrieveRoomTypeByName(String name) method
    public void updateRoomType(RoomTypeEntity roomType) {
        
        RoomTypeEntity roomTypeToUpdate = retrieveRoomTypeByName(roomType.getName());
        
        roomTypeToUpdate.setName(roomType.getName());
        roomTypeToUpdate.setDescription(roomType.getDescription());
        roomTypeToUpdate.setSize(roomType.getSize());
        roomTypeToUpdate.setCapacity(roomType.getCapacity());
        roomTypeToUpdate.setBed(roomType.getBed());
        roomTypeToUpdate.setAmenities(roomType.getAmenities());
        roomTypeToUpdate.setTier(roomType.getTier());
        roomTypeToUpdate.setReservation(roomType.getReservation());
        roomTypeToUpdate.setRoom(roomType.getRoom());
        roomTypeToUpdate.setRoomRate(roomType.getRoomRate());
        roomTypeToUpdate.setIsDisabled(roomType.getIsDisabled()); 
    }
    
    // If roomType.getReservation != null || roomType.getRoom != null 
    public void disableRoomType(RoomTypeEntity roomType) {
        
        roomType.setIsDisabled(Boolean.TRUE);
    }
    
    // If roomType.getReservation == null && roomType.getRoom == null && roomType.getRoomRate == null, delete
    
    public void deleteRoomType(RoomTypeEntity roomType) {
        
        em.remove(roomType);
        em.flush();
    }
    
    public void viewAllRoomType() {
        
        Query query = em.createQuery("SELECT rt FROM RoomType rt");
            
        List<RoomTypeEntity> roomTypes = (List<RoomTypeEntity>) query.getResultList();
        
        for(RoomTypeEntity roomType: roomTypes) {
            
            System.out.println("Room type: " + roomType.getName());
        }
    }
    
    public List<RoomTypeEntity> retrieveAllRoomType() {
        
        Query query = em.createQuery("SELECT rt FROM RoomType rt WHERE isDisable = FALSE");
            
        List<RoomTypeEntity> roomTypes = (List<RoomTypeEntity>) query.getResultList();
        
        return roomTypes;
    }
    
    
}
