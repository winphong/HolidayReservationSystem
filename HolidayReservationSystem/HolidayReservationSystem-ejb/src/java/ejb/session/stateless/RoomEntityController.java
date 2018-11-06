/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Inventory;
import entity.RoomEntity;
import entity.RoomNumber;
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
import util.exception.RoomNotFoundException;

/**
 *
 * @author twp10
 */
@Stateless
@Local(RoomEntityControllerLocal.class)
@Remote(RoomEntityControllerRemote.class)

public class RoomEntityController implements RoomEntityControllerRemote, RoomEntityControllerLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    @EJB
    private InventoryControllerLocal inventoryControllerLocal;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public RoomEntity createNewRoom(RoomEntity newRoom, RoomTypeEntity roomType) {

        em.persist(newRoom);

        roomType.getRoom().add(newRoom);
        newRoom.setRoomType(roomType);

        // Need to update inventory from the day of update to the last available inventory object
        inventoryControllerLocal.updateInventory();

        em.flush();

        return newRoom;
    }

    // Retrieved from retrieveRoomByRoomNumber
    public void updateRoom(RoomEntity room) {

        RoomEntity roomToUpdate;
        try {
            roomToUpdate = retrieveRoomByRoomNumber(room.getRoomNumber());
            roomToUpdate.setGuest(room.getGuest());
            roomToUpdate.setRoomStatus(room.getRoomStatus());
            roomToUpdate.setRoomType(room.getRoomType());
            roomToUpdate.setIsDisabled(room.getIsDisabled());
        } catch (RoomNotFoundException ex) {
            System.out.println("Room does not exist!");
        }

    }

    // need to modify this
    public void disableRoom(RoomEntity room) {

        room.setIsDisabled(Boolean.TRUE);

        inventoryControllerLocal.updateInventory();
    }

    // If VACANT, delete the room
    public void deleteRoom(RoomEntity room) {

        RoomTypeEntity roomType = room.getRoomType();

        roomType.getRoom().remove(room);
        room.setRoomType(null);

        em.remove(room);

        if (room.getIsDisabled().equals(Boolean.FALSE)) {
            inventoryControllerLocal.updateInventory();
        }

        em.flush();
    }

    public RoomEntity retrieveRoomByRoomNumber(RoomNumber roomNumber) throws RoomNotFoundException {

        Query query = em.createQuery("SELECT r FROM RoomEntity r WHERE r.roomNumber=:inRoomNumber");
        query.setParameter("inRoomNumber", roomNumber);

        try {

            return (RoomEntity) query.getSingleResult();

        } catch (NoResultException | NonUniqueResultException ex) {

            throw new RoomNotFoundException("Room " + roomNumber + "does not exist!");
        }
    }

    public List<RoomEntity> viewAllRoom() {

        Query query = em.createQuery("SELECT r FROM RoomEntity r");

        List<RoomEntity> rooms = (List<RoomEntity>) query.getResultList();

        return rooms;
    }
}
