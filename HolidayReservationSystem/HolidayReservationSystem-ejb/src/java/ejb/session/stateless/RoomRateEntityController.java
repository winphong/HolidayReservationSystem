/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

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
import util.exception.CreateNewRoomRateException;
import util.exception.RoomRateNotFoundException;

/**
 *
 * @author twp10
 */
@Stateless
@Local(RoomRateEntityControllerLocal.class)
@Remote(RoomRateEntityControllerRemote.class)

public class RoomRateEntityController implements RoomRateEntityControllerRemote, RoomRateEntityControllerLocal {

    @EJB
    private RoomTypeEntityControllerLocal roomTypeEntityController;

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    // Client ask for roomType and roomRate
    @Override
    public RoomRateEntity createNewRoomRate(RoomRateEntity newRoomRate, Long id) throws CreateNewRoomRateException {
        try {
            RoomTypeEntity roomType = roomTypeEntityController.retrieveRoomTypeById(id);
            em.persist(newRoomRate);

            roomType.getRoomRate().add(newRoomRate);
            newRoomRate.setRoomType(roomType);

            em.flush();

            return newRoomRate;
        } catch (Exception ex) {
            throw new CreateNewRoomRateException("Error creating new room rate: " + ex.getMessage());
        }
    }

    @Override
    public RoomRateEntity retrieveRoomRateByName(String name) throws RoomRateNotFoundException {

        Query query = em.createQuery("SELECT rr FROM RoomRateEntity rr WHERE rr.name=:inName");
        query.setParameter("inName", name);

        try {

            return (RoomRateEntity) query.getSingleResult();

        } catch (NoResultException | NonUniqueResultException ex) {

            throw new RoomRateNotFoundException("Room rate " + name + " does not exist!");
        }
    }

    public RoomRateEntity retrieveRoomRateById(Long id) throws RoomRateNotFoundException {

        Query query = em.createQuery("SELECT rr FROM RoomRateEntity rr WHERE rr.roomRateId =:inId");
        query.setParameter("inId", id);

        try {

            return (RoomRateEntity) query.getSingleResult();

        } catch (NoResultException | NonUniqueResultException ex) {

            throw new RoomRateNotFoundException("Room rate " + id + " does not exist!");
        }
    }

    public void updateRoomRate(RoomRateEntity roomRate, Long id) {

        try {
            RoomRateEntity roomRateToUpdate = retrieveRoomRateById(id);
            if (roomRate.getName() != null) {
                roomRateToUpdate.setName(roomRate.getName());
            }
            if (roomRate.getRatePerNight() != null) {
                roomRateToUpdate.setRatePerNight(roomRate.getRatePerNight());
            }
            if (roomRate.getValidFrom() != null) {
                roomRateToUpdate.setValidFrom(roomRate.getValidFrom());
            }
            if (roomRate.getValidTill() != null) {
                roomRateToUpdate.setValidTill(roomRate.getValidTill());
            }
        } catch (RoomRateNotFoundException ex) {
            System.out.println("Room Rate " + roomRate.getRoomRateId() + " does not exist!");
        }
    }

    // If roomRate.getRoomType.getReservation != null, disable
    @Override
    public void disableRoomRate(Long id) {

        RoomRateEntity roomRate = em.find(RoomRateEntity.class, id);
        roomRate.setIsDisabled(Boolean.TRUE);
        em.flush();

    }

    // If roomRate.getRoomType.getReservation == null, delete
    @Override
    public void deleteRoomRate(Long id) {
        RoomRateEntity roomRate = em.find(RoomRateEntity.class, id);
        RoomTypeEntity roomType = em.find(RoomTypeEntity.class, roomRate.getRoomType().getRoomTypeId());
        roomType.getRoomRate().remove(roomRate);
        em.remove(roomRate);
        em.flush();

    }

    public List<RoomRateEntity> viewAllRoomRate() {

        Query query = em.createQuery("SELECT rr FROM RoomRateEntity rr");

        List<RoomRateEntity> roomRates = (List<RoomRateEntity>) query.getResultList();

        return roomRates;
    }
}
