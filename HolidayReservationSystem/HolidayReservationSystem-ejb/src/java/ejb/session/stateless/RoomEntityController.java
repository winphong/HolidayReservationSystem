/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomEntity;
import entity.RoomTypeEntity;
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
public class RoomEntityController implements RoomEntityControllerRemote, RoomEntityControllerLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public RoomEntity createNewRoom(RoomEntity newRoom, RoomTypeEntity roomType) {
           
        em.persist(newRoom);
        
        roomType.getRoom().add(newRoom);
        newRoom.setRoomType(roomType);
        
        em.flush();
        
        return newRoom;
    }
    
    // Retrieved from retrieveRoomByRoomNumber
    public void updateRoom(RoomEntity room) {
       
        if ( room != null ) {
            
            RoomEntity roomToUpdate = retrieveRoomByRoomNumber(room.getRoomNumber());
            
            roomToUpdate.setGuest(room.getGuest());
            roomToUpdate.setRoomStatus(room.getRoomStatus());
            roomToUpdate.setRoomType(room.getRoomType());
            roomToUpdate.setIsDisabled(room.getIsDisabled());
            
        } else {
            
            System.out.println("Room does not exist!");
        }
        
    }
    
    // If room occupied, disable the room
    public void disableRoom(RoomEntity room) {
        
        room.setIsDisabled(Boolean.TRUE);
    }
    
    // If room not occupied and disabled, delete the room
    public void deleteRoom(RoomEntity room) {
        
        RoomTypeEntity roomType = room.getRoomType();
        
        roomType.getRoom().remove(room);
        room.setRoomType(null);
        
        em.remove(room);
        em.flush();
    }
    
    public RoomEntity retrieveRoomByRoomNumber(String roomNumber) {
        
        Query query = em.createQuery("SELECT r FROM Room r WHERE r.roomNumber=:inRoomNumber");
        query.setParameter("inRoomNumber", roomNumber);
        
        try {
            
            return (RoomEntity) query.getSingleResult();
            
        } catch ( NoResultException | NonUniqueResultException ex ) {            
            
            System.out.println("Room does not exist!");
        }
        
        return null;
    }
    
    public void viewAllRoom() {
        
        Query query = em.createQuery("SELECT r FROM Room r");

        List<RoomEntity> rooms = (List<RoomEntity>) query.getResultList();
        
        for(RoomEntity room:rooms) {
            System.out.println("Room number: " + room.getRoomNumber());
            /* + " || RoomEntity type: " + room.getRoomType().getName()
                                + " || RoomEntity status: " + room.getRoomStatus() + " || Guest: " + room.getGuest()); */
        }
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
