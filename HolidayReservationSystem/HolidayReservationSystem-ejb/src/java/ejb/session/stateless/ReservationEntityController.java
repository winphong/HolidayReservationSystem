/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Inventory;
import entity.ReservationEntity;
import entity.RoomEntity;
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
@Local (ReservationEntityControllerLocal.class)
@Remote (ReservationEntityControllerRemote.class)

public class ReservationEntityController implements ReservationEntityControllerRemote, ReservationEntityControllerLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    // Need to be tracked
    @Override
    public void reserveRoom(ReservationEntity newReservation) {
        
        em.persist(newReservation);
        
        List<RoomTypeEntity> listOfRoomTypes = newReservation.getRoomType();
        
        for(RoomTypeEntity roomType : listOfRoomTypes) {
            
            roomType.getReservation().add(newReservation);
            newReservation.getRoomType().add(roomType);
        }
        
        em.flush();
    }
    
    @Override
    public List<ReservationEntity> retrieveReservationByStartAndEndDate(LocalDate bookingStartDate, LocalDate bookingEndDate) {
        
        Query query = em.createQuery("SELECT r FROM ReservationEntity r WHERE (r.startDate < :bookingStartDate AND r.startDate <= :bookingEndDate) OR (:bookingStartDate >= r.startDate AND :bookingStartDate > r.endDate AND :bookingEndDate >= r.startDate)");
        query.setParameter("bookingStartDate", bookingStartDate);
        query.setParameter("bookingEndDate", bookingEndDate);

        try {
            
            return (List<ReservationEntity>) query.getResultList();
        
        } catch (NoResultException | NonUniqueResultException ex ) {
            
            System.out.println("No reservation || No non-unique reservation");
        }
        return null;
    }
}
