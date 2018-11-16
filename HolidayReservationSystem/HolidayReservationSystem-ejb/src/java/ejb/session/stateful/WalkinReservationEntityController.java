/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import ejb.session.stateless.EmployeeEntityControllerLocal;
import ejb.session.stateless.InventoryControllerLocal;
import ejb.session.stateless.ReservationEntityControllerLocal;
import ejb.session.stateless.RoomRateEntityControllerLocal;
import ejb.session.stateless.RoomTypeEntityControllerLocal;
import entity.ReservationEntity;
import entity.ReservationLineItemEntity;
import entity.RoomRateEntity;
import entity.RoomTypeEntity;
import entity.WalkinReservationEntity;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    @EJB
    private InventoryControllerLocal inventoryControllerLocal;

    

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
    public void reserveRoom(RoomTypeEntity roomType, LocalDate startDate, LocalDate endDate, Integer numOfRoomRequired) throws RoomTypeNotFoundException {

        List<RoomRateEntity> roomRatesOfGivenRoomType = roomTypeEntityControllerLocal.retrieveRoomTypeById(roomType.getRoomTypeId()).getRoomRate();
        
//        Query query = em.createQuery("SELECT rr FROM RoomRateEntity rr WHERE rr.name = :inRoomRateName AND rr.roomType ");
//        query.setParameter("inRoomRateName", "Published Rate");
//  
        try {
            
            RoomRateEntity publishedRoomRate = null;
            
            for(RoomRateEntity roomRate : roomRatesOfGivenRoomType) {
                
                if (roomRate.getName().equals("Published Rate")) {
                    publishedRoomRate = roomRate;
                }
            }
            
            BigDecimal subTotal = publishedRoomRate.getRatePerNight().multiply(new BigDecimal(startDate.until(endDate, ChronoUnit.DAYS))).multiply(new BigDecimal(numOfRoomRequired));
        
            ReservationLineItemEntity reservationLineItemEntity = new ReservationLineItemEntity(subTotal, numOfRoomRequired, roomType);

            reservationLineItems.add(reservationLineItemEntity);
            totalLineItem++;
            totalAmount = totalAmount.add(subTotal);
            
            // Update the lineItemsForCurrentReservation
            updateLineItemForCurrentReservationAtInventoryController();
            
        } catch (NullPointerException ex) {
            
            throw new NullPointerException("Published room rate throw " + ex.getMessage());
        }
        //Calculate total amount from room rate per night  
    }
  
    @Override
    public ReservationEntity checkOut(Long employeeId, LocalDate startDate, LocalDate endDate, String guestFirstName, String guestLastName, 
            String guestIdentificationNumber, String guestContactNumber, String guestEmail) throws Exception {
        
        String identity = "Employee";
        ReservationEntity newReservation = reservationEntityControllerLocal.createReservation(identity, employeeId, reservationLineItems, totalAmount, new WalkinReservationEntity(guestFirstName, 
                guestLastName, guestContactNumber, guestEmail, guestIdentificationNumber, Date.valueOf(LocalDate.now()), Date.valueOf(startDate), Date.valueOf(endDate), Boolean.FALSE));
        
        inventoryControllerLocal.getLineItemsForCurrentReservation().clear();
        
        initialiseState();
        
        return newReservation;
    }
    
      
    @Override
    public List<ReservationEntity> retrieveReservationByStartAndEndDate(LocalDate bookingStartDate, LocalDate bookingEndDate) {
        
        Query query = em.createQuery("SELECT r FROM ReservationEntity r WHERE (r.startDate < :bookingStartDate AND r.endDate > :bookingStartDate) OR "
                + " r.startDate = :bookingStartDate");
                //+ " (r.startDate >= :bookingStartDate AND r.startDate <= :bookingEndDate)");
                
                                //+ "(:bookingStartDate >= r.startDate AND :bookingStartDate > r.endDate AND :bookingEndDate >= r.startDate)"); -- most likely useless
        query.setParameter("bookingStartDate", Date.valueOf(bookingStartDate));
        //query.setParameter("bookingEndDate", Date.valueOf(bookingEndDate));

        try {
            // Return a list of reservation where the date collides with the hotel search
            return (List<ReservationEntity>) query.getResultList();
        
        } catch (NoResultException | NonUniqueResultException ex ) {
            
            System.out.println("No reservation || No non-unique reservation");
        }
        return null;
    }
    
    private void updateLineItemForCurrentReservationAtInventoryController() {      
        inventoryControllerLocal.setLineItemsForCurrentReservation(reservationLineItems);
    }   
}