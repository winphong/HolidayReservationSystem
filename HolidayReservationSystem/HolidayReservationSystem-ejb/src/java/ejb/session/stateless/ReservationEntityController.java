/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.EmployeeEntity;
import entity.GuestEntity;
import entity.OnlineReservationEntity;
import entity.PartnerEntity;
import entity.PartnerReservationEntity;
import entity.ReservationEntity;
import entity.ReservationLineItemEntity;
import entity.RoomEntity;
import entity.WalkinReservationEntity;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.ReservationLineItemNotFoundException;
import util.exception.ReservationNotFoundException;

/**
 *
 * @author twp10
 */
@Stateless
public class ReservationEntityController implements ReservationEntityControllerRemote, ReservationEntityControllerLocal {

    @EJB
    private EmployeeEntityControllerLocal employeeEntityControllerLocal;
    @EJB
    private GuestEntityControllerLocal guestEntityControllerLocal;
    @EJB
    private PartnerEntityControllerLocal partnerEntityControllerLocal;

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    @Resource
    private EJBContext eJBContext;

    @Override
    public ReservationEntity createReservation(String identity, Long Id, List<ReservationLineItemEntity> reservationLineItems, BigDecimal totalAmount, ReservationEntity newReservationEntity) throws Exception {

        if (newReservationEntity != null) {

            try {

                if (identity.equalsIgnoreCase("Guest")) {

                    GuestEntity guest = guestEntityControllerLocal.retrieveGuestById(Id);
                    ((OnlineReservationEntity) newReservationEntity).setGuest(guest);

                } else if (identity.equalsIgnoreCase("Employee")) {

                    EmployeeEntity employee = employeeEntityControllerLocal.retrieveEmployeeById(Id);
                    ((WalkinReservationEntity) newReservationEntity).setEmployee(employee);

                } else if (identity.equalsIgnoreCase("Partner")) {

                    PartnerEntity partner = partnerEntityControllerLocal.retrievePartnerById(Id);
                    ((PartnerReservationEntity) newReservationEntity).setPartner(partner);
                }

                em.persist(newReservationEntity);

                for (ReservationLineItemEntity reservationLineItem : reservationLineItems) {

                    em.persist(reservationLineItem);
                    reservationLineItem.setReservation(newReservationEntity);
                    newReservationEntity.getReservationLineItemEntities().add(reservationLineItem);
                }

                newReservationEntity.setTotalAmount(totalAmount);

                em.flush();

                return newReservationEntity;

            } catch (IllegalArgumentException ex) {

                eJBContext.setRollbackOnly();
                throw new IllegalArgumentException("Error");
            }

        } else {

            throw new Exception("Reservation information not provided");
        }
    }
    
    //******************************  Retrieve exception ************************************//

    @Override
    public List<ReservationLineItemEntity> retrieveFirstException() throws ReservationLineItemNotFoundException {
        try {
            Query query = em.createQuery("SELECT rli FROM ReservationLineItemEntity rli WHERE rli.isUpgraded = :isUpgraded");
            query.setParameter("isUpgraded", Boolean.TRUE);
            return (List<ReservationLineItemEntity>) query.getResultList();
        } catch (NoResultException ex) {
            throw new ReservationLineItemNotFoundException("Reservation line item not found");
        }
    }

    @Override
    public List<ReservationLineItemEntity> retrieveSecondException() throws ReservationLineItemNotFoundException {
        try {
            Query query = em.createQuery("SELECT rli FROM ReservationLineItemEntity rli WHERE rli.isAllocated = :isAllocated AND rli.isUpgraded = :isUpgraded");
            query.setParameter("isAllocated", Boolean.FALSE);
            query.setParameter("isUpgraded", Boolean.TRUE);
            return (List<ReservationLineItemEntity>) query.getResultList();
        } catch (NoResultException ex) {
            throw new ReservationLineItemNotFoundException("Reservation line item not found");
        }
    }
    
    //******************************************************************************************************//

    @Override
    public List<ReservationEntity> retrieveReservationByDateOrderByDescEndDate(LocalDate date) {

        Date sqlDate = Date.valueOf(date);
        // Retrieve a list of reservationItem where the startDate is equal to current date and order by the longest period of booking
        Query query = em.createQuery("SELECT r FROM ReservationEntity r WHERE r.startDate = :inDate ORDER BY r.endDate DESC");
        query.setParameter("inDate", sqlDate);

        return query.getResultList();
    }

    @Override
    public ReservationEntity retrieveReservationById(Long reservationId) throws ReservationNotFoundException {

        ReservationEntity reservation = em.find(ReservationEntity.class, reservationId);

        if (reservation != null) {
            return reservation;
        } else {
            throw new ReservationNotFoundException("throw new exception reservation ID " + reservationId + " not found");
        }
    }

    @Override
    public List<RoomEntity> retrieveRoomsByReservationId(Long reservationId) throws ReservationNotFoundException {
        ReservationEntity reservation;
        try {
            reservation = retrieveReservationById(reservationId);
            List<RoomEntity> rooms = reservation.getRooms();
            return rooms;
        } catch (ReservationNotFoundException ex) {
            throw new ReservationNotFoundException("Reservation " + reservationId + "not found!");
        }
    }
    
    @Override
    public List<ReservationLineItemEntity> retrieveItemsByReservationId(Long reservationId) throws ReservationNotFoundException {
        try {
            Query query = em.createQuery("SELECT rli FROM ReservationLineItemEntity rli WHERE rli.reservation.reservationId = :inReservationId");
            query.setParameter("inReservationId", reservationId);
            return (List<ReservationLineItemEntity>) query.getResultList();
        } catch (NoResultException ex) {
            throw new ReservationNotFoundException("Reservation not found");
        }
    }

    @Override
    public ReservationLineItemEntity retrieveReservationLineItemById(Long reservationLineItemId) throws ReservationLineItemNotFoundException {
        ReservationLineItemEntity reservationItem = em.find(ReservationLineItemEntity.class, reservationLineItemId);

        if (reservationItem != null) {
            return reservationItem;
        } else {
            throw new ReservationLineItemNotFoundException("Reservation ID " + reservationLineItemId + " not found");
        }
    }

}
