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
    
    @EJB
    private InventoryControllerLocal inventoryControllerLocal;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    // Set the room to BOOKED, don't need to assign yet
    public void reserveRoom(RoomTypeEntity roomType, LocalDate startDate, LocalDate endDate, Integer numOfRoomRequired) {
        
        Inventory inventory = inventoryControllerLocal.getInventoryByDate(startDate);
        
        Integer roomTypeIndex = inventory.getRoomTypes().indexOf(roomType);
        
        List<List<RoomEntity>> listOfRoomsForDifferentRoomTypes = inventory.getAvailableRoom();
        
        List<RoomEntity> rooms = listOfRoomsForDifferentRoomTypes.get(roomTypeIndex);
        
        Boolean availableThroughout;
        Integer countOfRoomAvailableThroughout = 0;
        
        // Loop through each room of a given roomType
        for(RoomEntity room : rooms) {
            
            availableThroughout = Boolean.TRUE;
            
            // Loop through the booking date
            for(LocalDate date = startDate.plusDays(1); !date.isAfter(endDate) ; date.plusDays(1)) {

                if (inventoryControllerLocal.roomExist(room, date, roomTypeIndex)) { 
                    continue;
                } else {
                    availableThroughout = Boolean.FALSE;
                    break;
                }
            }

            /* Pre-condition: There are atleast n number of room that satisfy the numOfRoomRequired
            ------------  Calculate how many rooms are there that satisfy the requirement --------------
            */
            if ( availableThroughout.equals(Boolean.TRUE) ) {
                countOfRoomAvailableThroughout++;
            }
        }
        
        List<ReservationEntity> listOfReservations = retrieveReservationByStartAndEndDate(startDate, endDate);
    }
    
    public List<ReservationEntity> retrieveReservationByStartAndEndDate(LocalDate inputStartDate, LocalDate inputEndDate) {
        
        Query query = em.createQuery("SELECT r FROM ReservationEntity r WHERE r.startDate = :inputStartDate AND r.endDate = :inputEndDate");
        query.setParameter("inputStartDate", inputStartDate);
        query.setParameter("inputEndDate", inputEndDate);

        try {
            
            return (List<ReservationEntity>) query.getResultList();
        
        } catch (NoResultException | NonUniqueResultException ex ) {
            
            System.out.println("No reservation || No non-unique reservation");
        }
        return null;
    }
}
