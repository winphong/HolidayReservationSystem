/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import ejb.session.stateless.InventoryControllerLocal;
import ejb.session.stateless.ReservationEntityControllerLocal;
import ejb.session.stateless.RoomTypeEntityControllerLocal;
import entity.GuestEntity;
import entity.OnlineReservationEntity;
import entity.ReservationEntity;
import entity.ReservationLineItemEntity;
import entity.RoomRateEntity;
import entity.RoomTypeEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
import util.exception.ReservationNotFoundException;
import util.exception.RoomTypeNotFoundException;

/**
 *
 * @author Asus
 */
@Stateful
@Local (OnlineReservationEntityControllerLocal.class)
@Remote (OnlineReservationEntityControllerRemote.class)

public class OnlineReservationEntityController implements OnlineReservationEntityControllerRemote, OnlineReservationEntityControllerLocal {

    @EJB
    private InventoryControllerLocal inventoryControllerLocal;

    @EJB
    private RoomTypeEntityControllerLocal roomTypeEntityControllerLocal;

    @EJB
    private ReservationEntityControllerLocal reservationEntityControllerLocal;
    
    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;
    
    private List<ReservationLineItemEntity> reservationLineItems;
    private Integer totalLineItem;
    private BigDecimal totalAmount;

    public OnlineReservationEntityController() {
        initialiseState();
    }
    
    private void initialiseState() {
        reservationLineItems = new ArrayList<>();
        totalLineItem = 0;
        totalAmount = new BigDecimal("0.00");
    }
    
    @Override
    public List<OnlineReservationEntity> viewAllReservationsOfGuest(GuestEntity guest) {

        Query query = em.createQuery("SELECT ore FROM OnlineReservationEntity ore WHERE ore.guest =:inGuest");
        query.setParameter("inGuest", guest);
        List<OnlineReservationEntity> reservations = (List<OnlineReservationEntity>) query.getResultList();

        return reservations;
    }
    
    @Override
    public OnlineReservationEntity retrieveReservationById(Long id) throws ReservationNotFoundException {

        Query query = em.createQuery("SELECT ore FROM OnlineReservationEntity ore WHERE ore.onlineReservationId=:inId");
        query.setParameter("inId", id);

        try {

            return (OnlineReservationEntity) query.getSingleResult();

        } catch (NoResultException | NonUniqueResultException ex) {

            throw new ReservationNotFoundException("Reservation " + id + "does not exist!");
        }
    }

    @Override
    public void reserveRoom(String roomTypeName, LocalDate startDate, LocalDate endDate, Integer numOfRoomRequired) throws RoomTypeNotFoundException, Exception {
        
        RoomTypeEntity roomType = roomTypeEntityControllerLocal.retrieveRoomTypeByName(roomTypeName);
        
        //Calculate total amount from room rate per night
        List<RoomRateEntity> roomRatesForABooking = new ArrayList<>();
        List<RoomRateEntity> roomRates;
        Integer resultIndexForNormalRate;
        Integer resultIndexForPeakRate;
        Integer resultIndexForPromotionRate;
        Date currentDate;
        Boolean peakIsValid;
        Boolean promotionIsValid;
        BigDecimal subTotal;
        
        for(LocalDate date = startDate.plusDays(1); !date.isAfter(endDate); date.plusDays(1)) {

            roomRates = roomType.getRoomRate();
            
            resultIndexForNormalRate = roomRates.indexOf("Normal Rate");
            resultIndexForPeakRate = roomRates.indexOf("Peak Rate");
            resultIndexForPromotionRate = roomRates.indexOf("Promotion Rate");
            
            currentDate  = java.sql.Date.valueOf(date);
            
            peakIsValid = ( !currentDate.before(roomRates.get(resultIndexForPeakRate).getValidFrom()) && !currentDate.after(roomRates.get(resultIndexForPeakRate).getValidTill()));
            promotionIsValid = ( !currentDate.before(roomRates.get(resultIndexForPromotionRate).getValidFrom()) && !currentDate.after(roomRates.get(resultIndexForPromotionRate).getValidTill()));
            
            
            if ( resultIndexForNormalRate != -1 && resultIndexForPeakRate != -1 && resultIndexForPromotionRate != 1 ){
                
                if ( promotionIsValid ) {              
                    roomRatesForABooking.add(roomRates.get(resultIndexForPromotionRate));  
                } else if ( peakIsValid ) {
                    roomRatesForABooking.add(roomRates.get(resultIndexForPeakRate));    
                } else {                
                    roomRatesForABooking.add(roomRates.get(resultIndexForNormalRate));
                } 
                
            } else if ( resultIndexForPeakRate != -1 && resultIndexForPromotionRate != 1 ) {

                if ( peakIsValid ) {                    
                    roomRatesForABooking.add(roomRates.get(resultIndexForPeakRate));                
                } else {                    
                    roomRatesForABooking.add(roomRates.get(resultIndexForPromotionRate));
                }
                
            } else if ( resultIndexForNormalRate != -1 && resultIndexForPeakRate != -1 ) {
                
                if ( peakIsValid ) {                   
                    roomRatesForABooking.add(roomRates.get(resultIndexForPeakRate));                
                } else {                    
                    roomRatesForABooking.add(roomRates.get(resultIndexForNormalRate));
                }
                
            } else if ( resultIndexForNormalRate != -1 && resultIndexForPromotionRate != -1) {
                
                if ( promotionIsValid ) {                  
                    roomRatesForABooking.add(roomRates.get(resultIndexForPromotionRate));                
                } else {                    
                    roomRatesForABooking.add(roomRates.get(resultIndexForNormalRate));
                }
                
            } else if ( resultIndexForNormalRate != -1 ) {
                
                roomRatesForABooking.add(roomRates.get(resultIndexForNormalRate));
            
            } else {                
                // To be edited
                throw new Exception("No room rate");
            }
        }
        
        subTotal = BigDecimal.ZERO;
        
        for (RoomRateEntity roomRatePerNight : roomRatesForABooking) {
            
            // No need for room rate classes
            subTotal = subTotal.add(roomRatePerNight.getRatePerNight().multiply(new BigDecimal(numOfRoomRequired)));
        }
        
        roomRatesForABooking = null;
        // *************** roomType might have lazy fetching issue *********************
        reservationLineItems.add(new ReservationLineItemEntity(subTotal, numOfRoomRequired, roomType));
        totalLineItem++;
        totalAmount.add(subTotal);
    }
    
    @Override
    public ReservationEntity checkOut(Long guestId, LocalDate startDate, LocalDate endDate) throws Exception {
        
        String identity = "Guest";
        ReservationEntity newReservation = reservationEntityControllerLocal.createReservation(identity, guestId, new OnlineReservationEntity(LocalDate.now(), startDate, endDate, Boolean.FALSE));
        
        initialiseState();
        
        return newReservation;
    }
}
