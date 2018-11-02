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
import util.exception.RoomTypeNotFoundException;

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
    public RoomTypeEntity createNewRoomType(RoomTypeEntity newRoomType) {

        em.persist(newRoomType);
        em.flush();
        inventoryControllerLocal.updateInventory();
        return newRoomType;
    }

    @Override
    public RoomTypeEntity retrieveRoomTypeById(Long id) throws RoomTypeNotFoundException {

        Query query = em.createQuery("SELECT rt FROM RoomTypeEntity rt WHERE rt.roomTypeId=:inId");
        query.setParameter("inId", id);

        try {

            return (RoomTypeEntity) query.getSingleResult();

        } catch (NoResultException | NonUniqueResultException ex) {

            throw new RoomTypeNotFoundException("Room type of Id" + id + " does not exist!");
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
            roomTypeToUpdate.setReservation(roomType.getReservation());
            roomTypeToUpdate.setRoom(roomType.getRoom());
            roomTypeToUpdate.setRoomRate(roomType.getRoomRate());
            roomTypeToUpdate.setIsDisabled(roomType.getIsDisabled());
            inventoryControllerLocal.updateInventory();
        } catch (RoomTypeNotFoundException ex) {
            System.out.println("Room type does not exist!");
        }
    }

    // If roomType.getReservation != null || roomType.getRoom != null 
    @Override
    public void disableRoomType(RoomTypeEntity roomType) {

        roomType.setIsDisabled(Boolean.TRUE);
        inventoryControllerLocal.updateInventory();
    }

    // If roomType.getReservation == null && roomType.getRoom == null && roomType.getRoomRate == null, delete
    @Override
    public void deleteRoomType(RoomTypeEntity roomType) {

        em.remove(roomType);
        em.flush();
        inventoryControllerLocal.updateInventory();
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

        return roomTypes;
    }
}
