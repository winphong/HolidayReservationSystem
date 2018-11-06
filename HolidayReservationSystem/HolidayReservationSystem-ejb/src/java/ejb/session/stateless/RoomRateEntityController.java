/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomRateEntity;
import entity.RoomTypeEntity;
import java.util.List;
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
@Local (RoomRateEntityControllerLocal.class)
@Remote (RoomRateEntityControllerRemote.class)

public class RoomRateEntityController implements RoomRateEntityControllerRemote, RoomRateEntityControllerLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    
    // Client ask for roomType and roomRate
    public RoomRateEntity createNewRoomRate(RoomRateEntity newRoomRate, RoomTypeEntity roomType) {
        
        em.persist(newRoomRate);
        
        roomType.getRoomRate().add(newRoomRate);
        newRoomRate.setRoomType(roomType);
        
        em.flush();
        
        return newRoomRate;
    }
    
    public RoomRateEntity retrieveRoomRateByName(String name) {
        
        Query query = em.createQuery("SELECT rr FROM RoomRateEntity rr WHERE rr.name=:inName");
        query.setParameter("inName", name);
        
        try {
            
            return (RoomRateEntity) query.getSingleResult();
            
        } catch (NoResultException | NonUniqueResultException ex) {
            
            System.out.println("Room rate " + name + " does not exist!");
        }
        
        return null;
    }
    
    public void updateRoomRate(RoomRateEntity roomRate) {
        
        if ( roomRate != null ) {
            
            RoomRateEntity roomRateToUpdate = retrieveRoomRateByName(roomRate.getName());
            
            roomRateToUpdate.setName(roomRate.getName());
            roomRateToUpdate.setRoomType(roomRate.getRoomType());
            roomRateToUpdate.setIsDisabled(roomRate.getIsDisabled());
        }
    }
    
    // If roomRate.getRoomType.getReservation != null, disable
    public void disableRoomRate(RoomRateEntity roomRate) {
        
        roomRate.setIsDisabled(Boolean.TRUE);
    }
    
    // If roomRate.getRoomType.getReservation == null, delete
    public void deleteRoomRate(RoomRateEntity roomRate) {
        
        RoomTypeEntity roomType = roomRate.getRoomType();
        
        roomType.getRoomRate().remove(roomRate);
        roomRate.setRoomType(null);
        
        em.remove(roomRate);
        em.flush();
    }
    
    public void viewAllRoomRate() {
        
        Query query = em.createQuery("SELECT rr FROM RoomRateEntity rr");
        
        List<RoomRateEntity> roomRates = (List<RoomRateEntity>) query.getResultList();
        
        for(RoomRateEntity roomRate: roomRates) {
            
            System.out.println("Room rate: " + roomRate.getName());
        }
    }
}
