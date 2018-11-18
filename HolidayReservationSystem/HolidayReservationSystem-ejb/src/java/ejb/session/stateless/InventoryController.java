/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Inventory;
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
import ejb.session.stateful.WalkinReservationEntityControllerLocal;
import entity.ReservationEntity;
import entity.ReservationLineItemEntity;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import util.enumeration.RoomStatus;
import util.exception.UpdateInventoryException;

/**
 *
 * @author twp10
 */
@Stateless
@Local(InventoryControllerLocal.class)
@Remote(InventoryControllerRemote.class)

public class InventoryController implements InventoryControllerRemote, InventoryControllerLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    @EJB
    private WalkinReservationEntityControllerLocal walkInReservationEntityControllerLocal;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    private List<ReservationLineItemEntity> lineItemsForCurrentReservation = new ArrayList<>();
    
    // Everytime a room / roomType is created/ updated / deleted, inventory must be updated from the current system date to the latest available booking date
    // need to modify to include use case for create and update
    @Override
    public void updateAllInventory() throws UpdateInventoryException{

        Date currentDate = Date.valueOf(LocalDate.now());

        try {
            Query query = em.createQuery("SELECT i FROM Inventory i WHERE i.date >= :currentDate");
            query.setParameter("currentDate", currentDate);

            List<Inventory> inventories = (List<Inventory>) query.getResultList();

            for (Inventory inventory : inventories) {
                updateInventory(inventory.getInventoryId());
            }
        }
        catch (NoResultException ex){
            throw new UpdateInventoryException(ex.getMessage());
        }
    }

    @Override
    public Inventory getInventoryByDate(Date inputDate) {
        
        System.out.println(inputDate);
        
        Query query = em.createQuery("SELECT i FROM Inventory i WHERE i.date = :inputDate");
        query.setParameter("inputDate", inputDate);

        try {
            
            Inventory inventory = (Inventory) query.getSingleResult();
            if ( inventory.getAvailableRoom() == null ) {
                System.out.println("Pui");
            }
            return inventory;

        } catch (NoResultException | NonUniqueResultException ex) {

            throw new NoResultException("No result exception");
        }
    }

    @Override
    public Inventory retrieveInventoryById(Long inventoryId) {

        Query query = em.createQuery("SELECT i FROM Inventory i WHERE i.inventoryId = :inInventoryId");
        query.setParameter("inInventoryId", inventoryId);

        try {

            return (Inventory) query.getSingleResult();

        } catch (NoResultException | NonUniqueResultException ex) {

            System.out.println("Inventory with ID : " + inventoryId + " does not exist!");
        }

        return null;
    }

    // Check from startDate to endDate whether there is any available room
    // Check each room and each date and make sure there is atleast one that is 
    @Override
    public List<RoomTypeEntity> searchAvailableRoom(String startDate, String endDate, Integer numOfRoomRequired) {
        
        LocalDate start = LocalDate.of(Integer.parseInt(startDate.substring(6)), Integer.parseInt(startDate.substring(3, 5)), Integer.parseInt(startDate.substring(0, 2)));
        LocalDate end = LocalDate.of(Integer.parseInt(endDate.substring(6)), Integer.parseInt(endDate.substring(3, 5)), Integer.parseInt(endDate.substring(0, 2)));
        List<RoomTypeEntity> availableRoomType = new ArrayList<>();
        
        Inventory inventory = getInventoryByDate(Date.valueOf(start));

        // Get using getAvailableRoom will return room that is not disabled
        // If use getRoomTypes.get(index).getRoom() will return all room, including those that has been disabled
        List<List<RoomEntity>> listOfRoomsForDifferentRoomTypes = inventory.getAvailableRoom(); // availableRoom is not disable and is vacant

        //Integer roomTypeIndex = 0;
//        Boolean availableThroughout;
//        Integer countOfRoomAvailableThroughout;
        // One reservation might have more than one room
        List<ReservationEntity> reservationList = walkInReservationEntityControllerLocal.retrieveReservationByStartAndEndDate(start, end);
        List<ReservationLineItemEntity> reservationLineItems;

        //Integer numOfReservationThatOverlapWithBooking = 0;
        
//**** Need to create a list to keep track of reservation for room of each type 
        List<Integer> numOfRoomOfEachTypeRequiredForReservation = new ArrayList<>(); // size equal number of type of room available
        
        // Maybe array easier??
        for(int i = 0; i < listOfRoomsForDifferentRoomTypes.size(); i++) {
            numOfRoomOfEachTypeRequiredForReservation.add(0);
        }
        
        // Check through all reservation in the database
        for (ReservationEntity reservation : reservationList) {

            reservationLineItems = reservation.getReservationLineItemEntities();

            // For all reservation, check through the reservationLineItem
            for (ReservationLineItemEntity reservationLineItem : reservationLineItems) {

                // Loop through the list, if matches, add the int to another list
                for(List<RoomEntity> listOfRooms : listOfRoomsForDifferentRoomTypes) {
                    
                    RoomTypeEntity roomType = listOfRooms.get(0).getRoomType();
                    
                    if ( reservationLineItem.getRoomType().equals(roomType) ) {
                        numOfRoomOfEachTypeRequiredForReservation.set(listOfRoomsForDifferentRoomTypes.indexOf(listOfRooms), numOfRoomOfEachTypeRequiredForReservation.get(listOfRoomsForDifferentRoomTypes.indexOf(listOfRooms)) + reservationLineItem.getNumOfRoomBooked());
                    }
                }
            }
        }
        
//        setLineItemsForCurrentReservation(walkInReservationEntityControllerLocal.getReservationLineItems());
//        
//        if ( !walkInReservationEntityControllerLocal.getTotalAmount().equals(new BigDecimal("0.00")) ) {
//            throw new Error("this got error");
//        }
        
        // Check through the current "running" reservation
        for(ReservationLineItemEntity reservationLineItem : lineItemsForCurrentReservation ) {
            
            for(List<RoomEntity> listOfRooms : listOfRoomsForDifferentRoomTypes) {
                    
                RoomTypeEntity roomType = listOfRooms.get(0).getRoomType();
                
                if ( reservationLineItem.getRoomType().equals(roomType) ) {
                    numOfRoomOfEachTypeRequiredForReservation.set(listOfRoomsForDifferentRoomTypes.indexOf(listOfRooms), numOfRoomOfEachTypeRequiredForReservation.get(listOfRoomsForDifferentRoomTypes.indexOf(listOfRooms)) + reservationLineItem.getNumOfRoomBooked());
                }
            }
        }
        
        // Check if the available 
        for (List<RoomEntity> listOfRooms : listOfRoomsForDifferentRoomTypes) {
            
            if (listOfRooms.size() - numOfRoomOfEachTypeRequiredForReservation.get(listOfRoomsForDifferentRoomTypes.indexOf(listOfRooms)) >= numOfRoomRequired) {
                availableRoomType.add(listOfRooms.get(listOfRoomsForDifferentRoomTypes.indexOf(listOfRooms)).getRoomType());
            }
        }
            

        /*// Loop through each room type given by the inventory of booking start date
        for (List<RoomEntity> listOfRooms : listOfRoomsForDifferentRoomTypes) {

            countOfRoomAvailableThroughout = 0;

            // Loop through each room of a particular room type
            for (RoomEntity room : listOfRooms) {

                availableThroughout = Boolean.TRUE;

                // Loop through the booking date starting from the boking startDate to the booking endDate
                // From the highlighted line, the function already ensure all the room retrieved will be available for startDate
                // Therefore, the search loop can start a day after the startDate
                for (LocalDate date = startDate.plusDays(1); !date.isAfter(endDate); date = date.plusDays(1)) {

                    // If the particular room is not available for the entire duration, set availbleThroughout to be false
                    if (!roomExist(room, date, listOfRoomsForDifferentRoomTypes.indexOf(listOfRooms))) {
                        availableThroughout = Boolean.FALSE;
                        break;
                    }
                }

                // As long there is enough room that is available throughout to fulfill the numOfRoomRequired
                // Need to check with reservation also, if reservation exist
                if (availableThroughout.equals(Boolean.TRUE)) {

                    countOfRoomAvailableThroughout++;

                    if ((countOfRoomAvailableThroughout - numOfRoomOfEachTypeRequiredForReservation.get(listOfRoomsForDifferentRoomTypes.indexOf(listOfRooms))) >= numOfRoomRequired) {
                        availableRoomType.add(room.getRoomType());
                        break;
                    }
                }
            }
            //roomTypeIndex++;
        } */
        return availableRoomType;
    }

    // Helper method
    // roomTypeIndex - indicate which roomType we are searching from
    @Override
    public Boolean roomExist(RoomEntity room, LocalDate date, Integer roomTypeIndex) {

        Inventory inventory = getInventoryByDate(Date.valueOf(date));

        List<List<RoomEntity>> listOfRoomEntity = inventory.getAvailableRoom();

        if (listOfRoomEntity.get(roomTypeIndex).indexOf(room) != -1) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    private void updateInventory(Long inventoryId) {

        Inventory inventory = retrieveInventoryById(inventoryId);

        Query query = em.createQuery("SELECT rt FROM RoomTypeEntity rt WHERE rt.isDisabled = :inBoolean");
        query.setParameter("inBoolean", Boolean.FALSE);

        // Get a list of roomTypes that is not disabled
        List<RoomTypeEntity> roomTypes = (List<RoomTypeEntity>) query.getResultList();
        // List<RoomEntity> availableRoom = new ArrayList<>();   
        Integer totalNumOfRoomAvailable = 0;

        List<RoomEntity> roomForEachRoomType;
        List<RoomEntity> rooms;

        // Set availableRoom to new List
        inventory.getAvailableRoom().clear();
        // For each room type
        for (RoomTypeEntity roomType : roomTypes) {
            roomForEachRoomType = new ArrayList<>();
            // Get the list of room
            rooms = roomType.getRoom();
            // Loop through the list of room and check for room that is not disabled and add to the list of roomForEachRoomType
            for (RoomEntity room : rooms) {
                // Not disable and vacant
                if (room.getIsDisabled().equals(Boolean.FALSE) && room.getRoomStatus().equals(RoomStatus.VACANT)) {
                    roomForEachRoomType.add(room);
                    totalNumOfRoomAvailable++;
                }
            }
            inventory.getAvailableRoom().add(roomForEachRoomType);
            // Iterate by the index (roomForEachRoomType will correspond the to RoomType at any give index)
        }
        // Add the list of roomForEachRoomType to the list of availableRoom (which is a list of availableRoom consisting lists of all room for the particular roomType
        inventory.setTotalNumOfRoomAvailable(totalNumOfRoomAvailable);
    }

    /**
     * @return the lineItemsForCurrentReservation
     */
    @Override
    public List<ReservationLineItemEntity> getLineItemsForCurrentReservation() {
        return lineItemsForCurrentReservation;
    }

    /**
     * @param lineItemsForCurrentReservation the lineItemsForCurrentReservation to set
     */
    @Override
    public void setLineItemsForCurrentReservation(List<ReservationLineItemEntity> lineItemsForCurrentReservation) {
        this.lineItemsForCurrentReservation = lineItemsForCurrentReservation;
    }
}
