/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Room;
import entity.RoomType;
import java.math.BigDecimal;
import java.util.List;
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
public class HotelOperationModule implements HotelOperationModuleRemote, HotelOperationModuleLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public RoomType createNewRoomType(RoomType newRoomType) {
        
        em.persist(newRoomType);
        em.flush();
        
        return newRoomType;
    }
    
    public RoomType retrieveRoomTypeByName(String name) {
        
        Query query = em.createQuery("SELECT rt FROM RoomType rt WHERE rt.name=:inName");
        query.setParameter("inName", name);
        
        try {
            
            return (RoomType) query.getSingleResult();
            
        } catch (NoResultException | NonUniqueResultException ex) {
            
            System.out.println("Room type " + name + " does not exist!");
        }
        
        return null;
    }

    // roomType retrieved the retrieveRoomTypeByName(String name) method
    public void updateRoomType(RoomType roomType) {
        
        RoomType roomTypeToUpdate = retrieveRoomTypeByName(roomType.getName());
        
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
    
    public void deleteRoomType(RoomType roomType) {
        
        if ( roomType.getIsDisabled().equals(Boolean.FALSE) ) {
            
            
            
        } else {
            
        }
    }
    
    public void viewAllRoomType() {
        
        Query query = em.createQuery("SELECT rt FROM RoomType rt");
            
        List<RoomType> roomType = (List<RoomType>) query.getResultList();
        
        for(RoomType roomTypes: roomType) {
            
            System.out.println("Room type: " + roomTypes.getName());
        }
    }
   
    /**
     * 
     *
     * -------------------------  ROOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOM  ------------------------------------*
     * 
     *
     **/
    public Room createNewRoom(Room newRoom) {
        
        em.persist(newRoom);
        em.flush();
        
        return newRoom;
    }
    
    // Retrieved from retrieveRoomByRoomNumber
    public void updateRoom(Room room) {
       
        if ( room != null ) {
            
            Room roomToUpdate = retrieveRoomByRoomNumber(room.getRoomNumber());
            
            roomToUpdate.setGuest(room.getGuest());
            roomToUpdate.setRoomStatus(room.getRoomStatus());
            roomToUpdate.setRoomType(room.getRoomType());
            roomToUpdate.setIsDisabled(room.getIsDisabled());
            
        } else {
            
            System.out.println("Room does not exist!");
        }
        
    }
    
    public void disableRoom(Room room) {
        
    }
    public void deleteRoom(Room room) {
        
        //
    }
    
    public Room retrieveRoomByRoomNumber(String roomNumber) {
        
        Query query = em.createQuery("SELECT r FROM Room r WHERE r.roomNumber=:inRoomNumber");
        query.setParameter("inRoomNumber", roomNumber);
        
        try {
            
            return (Room) query.getSingleResult();
            
        } catch ( NoResultException | NonUniqueResultException ex ) {            
            
            System.out.println("Room does not exist!");
        }
        
        return null;
    }
    
    public void viewAllRoom() {
        
        Query query = em.createQuery("SELECT r FROM Room r");

        List<Room> room = (List<Room>) query.getResultList();
        
        for(Room rooms:room) {
            System.out.println("Room number: " + rooms.getRoomNumber());
            /* + " || Room type: " + rooms.getRoomType().getName()
                                + " || Room status: " + rooms.getRoomStatus() + " || Guest: " + rooms.getGuest()); */
        }
    }
    
    
    
    
   
}
