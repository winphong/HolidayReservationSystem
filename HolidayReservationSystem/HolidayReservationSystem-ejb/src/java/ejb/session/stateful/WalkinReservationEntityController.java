/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import ejb.session.stateless.EmployeeEntityControllerLocal;
import ejb.session.stateless.ReservationEntityControllerLocal;
import ejb.session.stateless.RoomRateEntityControllerLocal;
import ejb.session.stateless.RoomTypeEntityControllerLocal;
import entity.ReservationEntity;
import entity.ReservationLineItemEntity;
import entity.RoomRateEntity;
import entity.RoomTypeEntity;
import entity.WalkinReservationEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.RoomTypeNotFoundException;

/**
 *
 * @author Asus
 */
@Stateful
@Local (WalkinReservationEntityControllerLocal.class)
@Remote (WalkinReservationEntityControllerRemote.class)

public class WalkinReservationEntityController implements WalkinReservationEntityControllerRemote, WalkinReservationEntityControllerLocal {

    @EJB
    private RoomRateEntityControllerLocal roomRateEntityControllerLocal;
    @EJB
    private RoomTypeEntityControllerLocal roomTypeEntityControllerLocal;
    @EJB
    private ReservationEntityControllerLocal reservationEntityControllerLocal;
    @EJB
    private EmployeeEntityControllerLocal employeeEntityControllerLocal;

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;
    
    private List<ReservationLineItemEntity> reservationLineItems;
    private Integer totalLineItem;
    private BigDecimal totalAmount;

    public WalkinReservationEntityController() {
        initialiseState();
    }
    
    private void initialiseState() {
        reservationLineItems = new ArrayList<>();
        totalLineItem = 0;
        totalAmount = new BigDecimal("0.00");
    }

    // Need to be tracked
    @Override
    public void reserveRoom(String roomTypeName, LocalDate startDate, LocalDate endDate, Integer numOfRoomRequired) throws RoomTypeNotFoundException {

        RoomTypeEntity roomType = roomTypeEntityControllerLocal.retrieveRoomTypeByName(roomTypeName);
        
        Query query = em.createQuery("SELECT rr FROM RoomRateEntity rr WHERE rr.name := :inRoomRateName");
        query.setParameter("inRoomRateName", "Published Rate");
        
        RoomRateEntity publishedRoomRate = null;
        
        try {
            
            publishedRoomRate = (RoomRateEntity) query.getSingleResult();
        
        } catch (NoResultException ex) {
            
            throw new NoResultException("Published room rate throw " + ex.getMessage());
        }
        //Calculate total amount from room rate per night
        BigDecimal subTotal = publishedRoomRate.getRatePerNight().multiply(new BigDecimal(endDate.compareTo(startDate))).multiply(new BigDecimal(numOfRoomRequired));
        
        reservationLineItems.add(new ReservationLineItemEntity(subTotal, numOfRoomRequired, roomType));
        totalLineItem++;
        totalAmount.add(subTotal);
    }
    
    @Override
    public List<ReservationEntity> retrieveReservationByStartAndEndDate(LocalDate bookingStartDate, LocalDate bookingEndDate) {
        
        Query query = em.createQuery("SELECT r FROM ReservationEntity r WHERE (r.startDate < :bookingStartDate AND r.startDate <= :bookingEndDate) OR "
                + "(:bookingStartDate >= r.startDate AND :bookingStartDate > r.endDate AND :bookingEndDate >= r.startDate)");
        query.setParameter("bookingStartDate", bookingStartDate);
        query.setParameter("bookingEndDate", bookingEndDate);

        try {
            
            // Return a list of reservation where the date collides with the hotel search
            return (List<ReservationEntity>) query.getResultList();
        
        } catch (NoResultException | NonUniqueResultException ex ) {
            
            System.out.println("No reservation || No non-unique reservation");
        }
        return null;
    }
    
    @Override
    public ReservationEntity checkOut(Long employeeId, LocalDate startDate, LocalDate endDate, String guestFirstName, String guestLastName, 
            String guestIdentificationNumber, String guestContactNumber, String guestEmail) throws Exception {
        
        String identity = "Employee";
        ReservationEntity newReservation = reservationEntityControllerLocal.createReservation(identity, employeeId, new WalkinReservationEntity(guestFirstName, 
                guestLastName, guestContactNumber, guestEmail, guestIdentificationNumber, LocalDate.now(), startDate, endDate, Boolean.FALSE));
        
        initialiseState();
        
        return newReservation;
    }
}
