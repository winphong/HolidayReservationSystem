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
import entity.RoomTypeEntity;
import entity.WalkinReservationEntity;
import java.time.LocalDate;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
    public ReservationEntity createReservation(String identity, Long Id, ReservationEntity newReservationEntity) throws Exception {
        
        if ( newReservationEntity != null ) {
            
            try {
                
                if ( identity.equalsIgnoreCase("Guest") ) {
                    
                    GuestEntity guest = guestEntityControllerLocal.retrieveGuestById(Id);
                    ((OnlineReservationEntity) newReservationEntity).setGuest(guest);  
                    
                } else if ( identity.equalsIgnoreCase("Employee") ) {
                    
                    EmployeeEntity employee = employeeEntityControllerLocal.retrieveEmployeeById(Id);
                    ((WalkinReservationEntity) newReservationEntity).setEmployee(employee);  
                    
                } else if ( identity.equalsIgnoreCase("Partner") ) {
                    
                    PartnerEntity partner = partnerEntityControllerLocal.retrievePartnerById(Id);
                    ((PartnerReservationEntity) newReservationEntity).setPartner(partner);  
                } 
                
                em.persist(newReservationEntity);
                
                for(ReservationLineItemEntity reservationLineItem : newReservationEntity.getReservationLineItemEntities()) {
                    
                    em.persist(reservationLineItem);
                    reservationLineItem.setReservation(newReservationEntity);
                    newReservationEntity.getReservationLineItemEntities().add(reservationLineItem);
                }
                
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
    
    // No longer needed 
    /*public WalkinReservationEntity createReservationForEmployee(Long employeeId, WalkinReservationEntity newWalkinReservationEntity) throws Exception {
        
        if ( newWalkinReservationEntity != null ) {
            
            try {
                
                EmployeeEntity employee = employeeEntityControllerLocal.retrieveEmployeeById(employeeId);
                newWalkinReservationEntity.setEmployee(employee);  
                
                em.persist(employee);
                
                for(ReservationLineItemEntity reserviationLineItem : newWalkinReservationEntity.getReservationLineItemEntities()) {
                    
                    em.persist(reserviationLineItem);
                }
                
                em.flush();
                return newWalkinReservationEntity;
         
            } catch (IllegalArgumentException ex) {
                
                eJBContext.setRollbackOnly();
                throw new IllegalArgumentException("Error");
            }
            
            
        } else {
            
            throw new Exception("Reservation information not provided");
        }
    }*/
    
    @Override
    public List<ReservationEntity> retrieveReservationByDateOrderByDescEndDate(LocalDate date) {
        
        // Retrieve a list of reservation where the startDate is equal to current date and order by the longest period of booking
        Query query = em.createQuery("SELECT r FROM ReservationEntity r WHERE r.startDate = :inDate ORDER BY r.endDate DESC");
        query.setParameter("inDate", date);
        
        return query.getResultList(); 
    }
    
    @Override
    public ReservationEntity retrieveReservationById(Long reservationId) {
        
        ReservationEntity reservation = em.find(ReservationEntity.class, reservationId);
        
        if ( reservation != null ) {
            return reservation;
        } else {
            System.out.println("throw new exception reservation ID " + reservationId + " not found");
            return null;
        }
    }
}
