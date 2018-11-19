/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomEntity;
import entity.RoomRateEntity;
import entity.RoomTypeEntity;
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
import util.exception.RoomTypeNotFoundException;
import util.exception.UpdateInventoryException;

/**
 *
 * @author twp10
 */
@Stateless
@Local(RoomTypeEntityControllerLocal.class)
@Remote(RoomTypeEntityControllerRemote.class)

public class RoomTypeEntityController implements RoomTypeEntityControllerRemote, RoomTypeEntityControllerLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    @EJB
    private InventoryControllerLocal inventoryControllerLocal;
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public RoomTypeEntity createNewRoomType(RoomTypeEntity newRoomType) throws UpdateInventoryException {

        em.persist(newRoomType);
        em.flush();
        try {
            inventoryControllerLocal.updateAllInventory();
        } catch (UpdateInventoryException ex) {
            throw new UpdateInventoryException("Error updating Inventory!");
        }
        return newRoomType;
    }

    @Override
    public RoomTypeEntity retrieveRoomTypeById(Long roomTypeId) throws RoomTypeNotFoundException {

        RoomTypeEntity roomType = em.find(RoomTypeEntity.class, roomTypeId);

        if (roomType != null) {

            return roomType;

        } else {

            throw new RoomTypeNotFoundException("Room type of Id" + roomTypeId + " does not exist!");
        }
    }

    @Override
    public RoomTypeEntity retrieveRoomTypeByName(String name) throws RoomTypeNotFoundException {

        Query query = em.createQuery("SELECT rt FROM RoomTypeEntity rt WHERE rt.name = :inName");
        query.setParameter("inName", name);

        try {

            return (RoomTypeEntity) query.getSingleResult();

        } catch (NoResultException | NonUniqueResultException ex) {

            throw new RoomTypeNotFoundException("Room type " + name + " does not exist!");
        }
    }

    // roomType retrieved the retrieveRoomTypeByName(String name) method
    @Override
    public void updateRoomType(RoomTypeEntity roomType, Long id) {
        RoomTypeEntity roomTypeToUpdate;
        try {
            roomTypeToUpdate = retrieveRoomTypeById(id);
            if (roomType.getName() != null) {
                roomTypeToUpdate.setName(roomType.getName());
            }
            if (roomType.getDescription() != null) {
                roomTypeToUpdate.setDescription(roomType.getDescription());
            }
            if (roomType.getRoomSize() != null) {
                roomTypeToUpdate.setRoomSize(roomType.getRoomSize());
            }
            if (roomType.getCapacity() != null) {
                roomTypeToUpdate.setCapacity(roomType.getCapacity());
            }
            if (roomType.getBed() != null) {
                roomTypeToUpdate.setBed(roomType.getBed());
            }
            if (roomType.getAmenities() != null) {
                roomTypeToUpdate.setAmenities(roomType.getAmenities());
            }
            if (roomType.getTier() != null) {
                roomTypeToUpdate.setTier(roomType.getTier());
            }
            inventoryControllerLocal.updateAllInventory();
        } catch (RoomTypeNotFoundException ex) {
            System.out.println("Room type does not exist!");
        } catch (UpdateInventoryException ex) {
            System.out.println("Error updating Inventory!");
        }
    }

    // If roomType.getReservation != null || roomType.getRoom != null 
    @Override
    public void disableRoomType(Long id) {
        RoomTypeEntity roomType = em.find(RoomTypeEntity.class, id);
        roomType.setIsDisabled(Boolean.TRUE);
        em.flush();
        try {
            inventoryControllerLocal.updateAllInventory();
        } catch (UpdateInventoryException ex) {
            System.out.println("Error updating Inventory!");
        }
    }

    // If roomType.getReservation == null && roomType.getRoom == null && roomType.getRoomRate == null, delete
    @Override
    public void deleteRoomType(Long id) {
        
        
        RoomTypeEntity roomType = em.find(RoomTypeEntity.class, id);
        
        // Delete room rate
        roomType.getRoomRate().size();
        List <RoomRateEntity> roomRates = roomType.getRoomRate();
        roomType.setRoomRate(null);
        for (RoomRateEntity roomRate: roomRates){
            em.remove(roomRate);
        }
        
        // Settle room type
        roomType.getRoom().size();
        List<RoomEntity> rooms = roomType.getRoom();
        Boolean ableToDelete = Boolean.TRUE;
        
        for(RoomEntity room : rooms) {
            if ( room.getCurrentReservation() != null || room.getNextReservation() != null ) {
                ableToDelete = Boolean.FALSE;
            }
        }

        if ( ableToDelete ) {
            roomType.setRoom(null);
            
            for (RoomEntity room: rooms){
                em.remove(room);
            }
            
            em.remove(roomType);
        } else {
            disableRoomType(id);
        }
        
        try {
            inventoryControllerLocal.updateAllInventory();
        } catch (UpdateInventoryException ex) {
            System.out.println("Error updating Inventory!");
        }
    }

    @Override
    public List<RoomTypeEntity> viewAllRoomType() {

        Query query = em.createQuery("SELECT rt FROM RoomTypeEntity rt");

        List<RoomTypeEntity> roomTypes = (List<RoomTypeEntity>) query.getResultList();

        return roomTypes;
    }

    @Override
    public List<RoomTypeEntity> retrieveAllRoomType() {

        Query query = em.createQuery("SELECT rt FROM RoomTypeEntity rt WHERE rt.isDisabled = :Boolean");
        query.setParameter("Boolean", Boolean.FALSE);

        List<RoomTypeEntity> roomTypes = (List<RoomTypeEntity>) query.getResultList();
        for (RoomTypeEntity roomType : roomTypes) {
            roomType.getRoom().size();
            roomType.getRoomRate().size();
        }

        return roomTypes;
    }

    @Override
    public void updateTier(int tier) {

        List<RoomTypeEntity> roomTypes = viewAllRoomType();
        for (RoomTypeEntity roomType : roomTypes) {
            roomType.setTier(roomType.getTier() - roomTypes.size());
            em.persist(roomType);
            em.flush();
        }

        for (RoomTypeEntity roomType : roomTypes) {
            if (roomType.getTier() + roomTypes.size() >= tier) {
                roomType.setTier(roomType.getTier() + roomTypes.size() + 1);
            } else {
                roomType.setTier(roomType.getTier() + roomTypes.size());
            }
            em.persist(roomType);
            em.flush();
        }
    }
    
    @Override
    public List <RoomEntity> retrieveRooms (Long id) throws RoomTypeNotFoundException{
        try{
            RoomTypeEntity roomType = retrieveRoomTypeById(id);
            roomType.getRoom().size();
            return roomType.getRoom();
        }
        catch (RoomTypeNotFoundException ex){
            throw new RoomTypeNotFoundException(ex.getMessage());
        }

    }
    
    @Override
    public RoomTypeEntity retrieveRoomTypeByTier (int tier) throws RoomTypeNotFoundException{
        Query query = em.createQuery("SELECT rt FROM RoomTypeEntity rt WHERE rt.tier = :inTier");
        query.setParameter("inTier", tier);

        try {
            return (RoomTypeEntity) query.getSingleResult();

        } catch (NoResultException ex) {
            throw new RoomTypeNotFoundException("Room type of tier " + tier + " does not exist!");
        }
    }
    
    @Override
    public Integer retrieveHighestRoomTier() {
        
        Query query = em.createQuery("SELECT MAX(rt.tier) FROM RoomTypeEntity rt, RoomEntity r WHERE r.roomType.roomTypeId = rt.roomTypeId");
        
        return (Integer) query.getSingleResult();
    }
}
