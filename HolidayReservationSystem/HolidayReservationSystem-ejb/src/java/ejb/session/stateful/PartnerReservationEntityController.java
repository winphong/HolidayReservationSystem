/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import ejb.session.stateless.InventoryControllerLocal;
import ejb.session.stateless.ReservationEntityControllerLocal;
import ejb.session.stateless.RoomTypeEntityControllerLocal;
import entity.PartnerEntity;
import entity.PartnerReservationEntity;
import entity.ReservationEntity;
import entity.ReservationLineItemEntity;
import entity.RoomRateEntity;
import entity.RoomTypeEntity;
import java.math.BigDecimal;
import java.sql.Date;
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
import util.exception.ReservationNotFoundException;
import util.exception.RoomRateNotFoundException;
import util.exception.RoomTypeNotFoundException;

/**
 *
 * @author Asus
 */
@Stateful
@Local (PartnerReservationEntityControllerLocal.class)
@Remote (PartnerReservationEntityControllerRemote.class)
public class PartnerReservationEntityController implements PartnerReservationEntityControllerRemote, PartnerReservationEntityControllerLocal {

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

    public void persist(Object object) {
        em.persist(object);
    }

    public List<PartnerReservationEntity> viewAllReservations(PartnerEntity partner) {

        Query query = em.createQuery("SELECT pr FROM PartnerReservationEntity pr WHERE pr.partner =:inPartner");
        query.setParameter("inPartner", partner);
        List<PartnerReservationEntity> reservations = (List<PartnerReservationEntity>) query.getResultList();

        return reservations;
    }
    
    @Override
    public PartnerReservationEntity retrieveReservationById(Long id) throws ReservationNotFoundException {

        Query query = em.createQuery("SELECT ore FROM OnlineReservationEntity ore WHERE ore.id=:inId");
        query.setParameter("inId", id);

        try {

            return (PartnerReservationEntity) query.getSingleResult();

        } catch (NoResultException | NonUniqueResultException ex) {

            throw new ReservationNotFoundException("Reservation " + id + "does not exist!");
        }
    }
    
    @Override
    public Boolean reserveRoom(String roomTypeName, String startDate, String endDate, Integer numOfRoomRequired) throws RoomTypeNotFoundException, Exception {
        
        LocalDate start = LocalDate.of(Integer.parseInt(startDate.substring(6)), Integer.parseInt(startDate.substring(3, 5)), Integer.parseInt(startDate.substring(0, 2)));
        LocalDate end = LocalDate.of(Integer.parseInt(endDate.substring(6)), Integer.parseInt(endDate.substring(3, 5)), Integer.parseInt(endDate.substring(0, 2)));
        RoomTypeEntity roomType = roomTypeEntityControllerLocal.retrieveRoomTypeByName(roomTypeName);
        //Calculate total amount from room rate per night
        List<RoomRateEntity> roomRatesForABooking = new ArrayList<>();
        
        for(LocalDate date = start.plusDays(1); !date.isAfter(end); date = date.plusDays(1)) {

            List<RoomRateEntity> roomRates = roomType.getRoomRate();
            
            Integer resultIndexForNormalRate = roomRates.indexOf("Normal Rate");
            Integer resultIndexForPeakRate = roomRates.indexOf("Peak Rate");
            Integer resultIndexForPromotionRate = roomRates.indexOf("Promotion Rate");
            
            Date currentDate  = Date.valueOf(date);
            
            Boolean peakIsValid = ( !currentDate.before(roomRates.get(resultIndexForPeakRate).getValidFrom()) && !currentDate.after(roomRates.get(resultIndexForPeakRate).getValidTill()));
            Boolean promotionIsValid = ( !currentDate.before(roomRates.get(resultIndexForPromotionRate).getValidFrom()) && !currentDate.after(roomRates.get(resultIndexForPromotionRate).getValidTill()));
            
            
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
                throw new RoomRateNotFoundException("No room rate");
            }
        }
        
        BigDecimal subTotal = BigDecimal.ZERO;
        
        for (RoomRateEntity roomRatePerNight : roomRatesForABooking) {
            
            // No need for room rate classes
            subTotal = subTotal.add(roomRatePerNight.getRatePerNight().multiply(new BigDecimal(numOfRoomRequired)));
        }
        
        // *************** roomType might have lazy fetching issue *********************
        reservationLineItems.add(new ReservationLineItemEntity(subTotal, numOfRoomRequired, roomType));
        totalLineItem++;
        totalAmount.add(subTotal);
        
        // Update the lineItemsForCurrentReservation
        updateLineItemForCurrentReservationAtInventoryController();
        return true;
    }
    
    @Override
    public ReservationEntity checkOut(Long partnerId, String customerFirstName, String customerLastName, String customerIdentificationNumber, String customerContactNumber, String customerEmail, String startDate, String endDate) throws Exception {
        LocalDate start = LocalDate.of(Integer.parseInt(startDate.substring(6)), Integer.parseInt(startDate.substring(3, 5)), Integer.parseInt(startDate.substring(0, 2)));
        LocalDate end = LocalDate.of(Integer.parseInt(endDate.substring(6)), Integer.parseInt(endDate.substring(3, 5)), Integer.parseInt(endDate.substring(0, 2)));
        String identity = "Partner";
        ReservationEntity newReservation = reservationEntityControllerLocal.createReservation(identity, partnerId, reservationLineItems, totalAmount, new PartnerReservationEntity(customerFirstName, customerLastName, customerIdentificationNumber, customerContactNumber, customerEmail, Date.valueOf(LocalDate.now()), Date.valueOf(start), Date.valueOf(end), Boolean.FALSE));
        
        initialiseState();
        
        return newReservation;
    }
    
    private void initialiseState() {
        reservationLineItems = new ArrayList<>();
        totalLineItem = 0;
        totalAmount = new BigDecimal("0.00");
    }
    
    private void updateLineItemForCurrentReservationAtInventoryController() {      
        inventoryControllerLocal.setLineItemsForCurrentReservation(reservationLineItems);
    } 

}
