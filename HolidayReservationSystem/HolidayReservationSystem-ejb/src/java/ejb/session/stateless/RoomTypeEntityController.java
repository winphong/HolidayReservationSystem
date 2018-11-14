/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomTypeEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public RoomTypeEntity createNewRoomType(RoomTypeEntity newRoomType) throws UpdateInventoryException{

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

        Query query = em.createQuery("SELECT rt FROM RoomTypeEntity rt WHERE rt.name=:inName");
        query.setParameter("inName", name);

        try {

            return (RoomTypeEntity) query.getSingleResult();

        } catch (NoResultException | NonUniqueResultException ex) {

            throw new RoomTypeNotFoundException("Room type " + name + " does not exist!");
        }
    }

    // roomType retrieved the retrieveRoomTypeByName(String name) method
    @Override
    public void updateRoomType(RoomTypeEntity roomType) {
        RoomTypeEntity roomTypeToUpdate;
        try {
            roomTypeToUpdate = retrieveRoomTypeByName(roomType.getName());
            roomTypeToUpdate.setName(roomType.getName());
            roomTypeToUpdate.setDescription(roomType.getDescription());
            roomTypeToUpdate.setSize(roomType.getSize());
            roomTypeToUpdate.setCapacity(roomType.getCapacity());
            roomTypeToUpdate.setBed(roomType.getBed());
            roomTypeToUpdate.setAmenities(roomType.getAmenities());
            roomTypeToUpdate.setTier(roomType.getTier());
            roomTypeToUpdate.setRoom(roomType.getRoom());
            roomTypeToUpdate.setRoomRate(roomType.getRoomRate());
            roomTypeToUpdate.setIsDisabled(roomType.getIsDisabled());
            inventoryControllerLocal.updateAllInventory();
        } catch (RoomTypeNotFoundException ex) {
            System.out.println("Room type does not exist!");
        } catch (UpdateInventoryException ex) {
            System.out.println("Error updating Inventory!");
        }
    }

    // If roomType.getReservation != null || roomType.getRoom != null 
    @Override
    public void disableRoomType(RoomTypeEntity roomType) {

        roomType.setIsDisabled(Boolean.TRUE);
        try {
            inventoryControllerLocal.updateAllInventory();
        } catch (UpdateInventoryException ex) {
            System.out.println("Error updating Inventory!");
        }
    }

    // If roomType.getReservation == null && roomType.getRoom == null && roomType.getRoomRate == null, delete
    @Override
    public void deleteRoomType(RoomTypeEntity roomType) {

        em.remove(roomType);
        em.flush();
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

        Query query = em.createQuery("SELECT rt FROM RoomTypeEntity rt WHERE rt.isDisabled = FALSE");

        List<RoomTypeEntity> roomTypes = (List<RoomTypeEntity>) query.getResultList();
        for (RoomTypeEntity roomType : roomTypes) {
           roomType.getRoom().size();
           roomType.getRoomRate().size();
        }

        return roomTypes;
    }

    public void updateTier(int tier) {

        List<RoomTypeEntity> roomTypes = viewAllRoomType();
        for (RoomTypeEntity roomType : roomTypes) {
            if (roomType.getTier() >= tier) {
                roomType.setTier(roomType.getTier() + 1);
            }

        }
    }
}