/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managementclient;

import ejb.session.stateful.OnlineReservationEntityControllerRemote;
import ejb.session.stateless.GuestEntityControllerRemote;
import ejb.session.stateless.RoomEntityControllerRemote;
import entity.EmployeeEntity;
import util.enumeration.EmployeeAccessRight;
import util.exception.InvalidAccessRightException;
import ejb.session.stateful.WalkinReservationEntityControllerRemote;
import ejb.session.stateless.InventoryControllerRemote;
import ejb.session.stateless.ReservationEntityControllerRemote;
import ejb.session.stateless.RoomTypeEntityControllerRemote;
import entity.ReservationEntity;
import entity.RoomEntity;
import entity.RoomTypeEntity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import javax.ejb.EJB;
import util.enumeration.RoomStatus;
import util.exception.RoomNotFoundException;
import util.exception.RoomTypeNotFoundException;

/**
 *
 * @author Asus
 */
public class FrontOfficeModule {
    
    @EJB
    private ReservationEntityControllerRemote reservationEntityControllerRemote;
    @EJB
    private GuestEntityControllerRemote guestEntityControllerRemote;
    @EJB
    private RoomEntityControllerRemote roomEntityControllerRemote;
    @EJB
    private InventoryControllerRemote inventoryControllerRemote;
    @EJB
    private RoomTypeEntityControllerRemote roomTypeEntityControllerRemote;
    @EJB
    private WalkinReservationEntityControllerRemote walkInReservationEntityControllerRemote;
    @EJB
    private OnlineReservationEntityControllerRemote onlineReservationEntityControllerRemote;
    
    private EmployeeEntity currentEmployee;
    
    
    
    
    public FrontOfficeModule() {
    }

    public FrontOfficeModule(ReservationEntityControllerRemote reservationEntityControllerRemote, GuestEntityControllerRemote guestEntityControllerRemote, RoomEntityControllerRemote roomEntityControllerRemote, InventoryControllerRemote inventoryControllerRemote, RoomTypeEntityControllerRemote roomTypeEntityControllerRemote, WalkinReservationEntityControllerRemote walkInReservationEntityControllerRemote, OnlineReservationEntityControllerRemote onlineReservationEntityControllerRemote, EmployeeEntity currentEmployee) {
        this.reservationEntityControllerRemote = reservationEntityControllerRemote;
        this.guestEntityControllerRemote = guestEntityControllerRemote;
        this.roomEntityControllerRemote = roomEntityControllerRemote;
        this.inventoryControllerRemote = inventoryControllerRemote;
        this.roomTypeEntityControllerRemote = roomTypeEntityControllerRemote;
        this.walkInReservationEntityControllerRemote = walkInReservationEntityControllerRemote;
        this.onlineReservationEntityControllerRemote = onlineReservationEntityControllerRemote;
        this.currentEmployee = currentEmployee;
    }

    
    public void menuFrontOffice() throws InvalidAccessRightException, Exception{
        if (currentEmployee.getAccessRight() != EmployeeAccessRight.GUESTRELATIONOFFICER) {
            throw new InvalidAccessRightException("You don't have MANAGER rights to access the system administration module.");
        }
        
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Merlion Hotel HoRS :: Front Office ***\n");
            System.out.println("-----------------------");
            System.out.println("1: Walk-in Search Room");
            System.out.println("-----------------------");
            System.out.println("2: Check-in Guest");
            System.out.println("3: Check-out Guest");
            System.out.println("-----------------------");
            System.out.println("4: Back\n");
            System.out.println();
            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("Enter option: ");

                response = scanner.nextInt();

                if (response == 1) {
                    searchRoom();
                } else if (response == 2) {
                    guestCheckin();
                } else if (response == 3) {
                    guestCheckout();
                } else if (response == 4) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 4) {
                break;
            }
        }
    }
    
    private void searchRoom() throws RoomTypeNotFoundException, Exception{
        
        System.out.println("*** Merlion Hotel HoRS :: Front Office :: Walk-in Search Room ***\n");
        Scanner scanner = new Scanner(System.in);
        
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/mm");
        
        System.out.println("Enter start date (dd/mm): ");
        String date = scanner.nextLine().trim();
        LocalDate startDate = LocalDate.parse(date, dateFormat);
        System.out.println("Enter end date (dd/mm): ");
        date = scanner.nextLine().trim();
        LocalDate endDate = LocalDate.parse(date, dateFormat);
        System.out.println("Enter number of rooms required: ");
        Integer numOfRoomRequired = scanner.nextInt();
        
        List<RoomTypeEntity> availableRoomTypes = inventoryControllerRemote.searchAvailableRoom(startDate, endDate, numOfRoomRequired);
        
        Integer index = 1;
        
        for(RoomTypeEntity availableRoomType : availableRoomTypes) {
            
            System.out.println(index + ": " + availableRoomType.getName());
            index++;
        }
        
        System.out.print("/nReserve room? (Y/N) > ");
        String response = scanner.nextLine().trim().toUpperCase();
        
        if ( response.equals("Y") ) {
            makeReservation(startDate, endDate, numOfRoomRequired);
        }
    }
    
    private void searchRoomAgain(LocalDate startDate, LocalDate endDate, String guestFirstName, String guestLastName, 
            String guestIdentificationNumber, String guestContactNumber, String guestEmail) throws RoomTypeNotFoundException, Exception{
        
        System.out.println("*** Merlion Hotel HoRS :: Front Office :: Walk-in Search Room ***\n");
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter number of rooms required: ");
        Integer numOfRoomRequired = scanner.nextInt();
        
        List<RoomTypeEntity> availableRoomTypes = inventoryControllerRemote.searchAvailableRoom(startDate, endDate, numOfRoomRequired);
        
        Integer index = 1;
        
        for(RoomTypeEntity availableRoomType : availableRoomTypes) {
            
            System.out.println(index + ": " + availableRoomType.getName());
            index++;
        }
        
        System.out.print("/nReserve room? (Y/N) > ");
        String response = scanner.nextLine().trim();
        
        if ( response.equalsIgnoreCase("Y") ) {
            makeReservationAgain(startDate, endDate, numOfRoomRequired, guestFirstName, guestLastName, guestIdentificationNumber, guestContactNumber, guestEmail);
        }
    }
    
    private void makeReservation(LocalDate startDate, LocalDate endDate, Integer numOfRoomRequired) throws RoomTypeNotFoundException, Exception{
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("/nSelect available room type: >");
        String roomTypeName = scanner.nextLine().trim();
        System.out.print("/nEnter first name: ");
        String guestFirstName = scanner.nextLine().trim();
        System.out.print("/nEnter last name: ");
        String guestLastName = scanner.nextLine().trim();
        System.out.print("/nEnter guest identification number: ");
        String guestIdentificationNumber = scanner.nextLine().trim();
        System.out.print("/nEnter contact number: ");
        String guestContactNumber = scanner.nextLine().trim();
        System.out.print("/nEnter email: ");
        String guestEmail = scanner.nextLine().trim();
        
        //ReservationEntity newReservation = new WalkinReservationEntity();
        try {
            
            //RoomTypeEntity roomType = roomTypeEntityControllerRemote.retrieveRoomTypeByName(roomTypeName);
            
            walkInReservationEntityControllerRemote.reserveRoom(roomTypeName, startDate, endDate, numOfRoomRequired);
            
        } catch (RoomTypeNotFoundException ex) {
            
            System.err.println(ex.getMessage());
        }
        
        System.out.print("\nWould you to reserve another room? (Y/N): ");
        String response = scanner.nextLine().trim();
        
        if ( response.equalsIgnoreCase("Y") ) {
            searchRoomAgain(startDate, endDate, guestFirstName, 
                    guestLastName, guestIdentificationNumber, guestContactNumber, guestEmail);
        } else {
            walkInReservationEntityControllerRemote.checkOut(currentEmployee.getEmployeeId(), startDate, endDate, guestFirstName, 
                    guestLastName, guestIdentificationNumber, guestContactNumber, guestEmail);
        }
    }
    
    private void makeReservationAgain(LocalDate startDate, LocalDate endDate, Integer numOfRoomRequired, String guestFirstName, 
            String guestLastName, String guestIdentificationNumber, String guestContactNumber, String guestEmail) throws RoomTypeNotFoundException, Exception
    {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("/nSelect available room type: >");
        String roomTypeName = scanner.nextLine().trim();
        
        //ReservationEntity newReservation = new WalkinReservationEntity();
        try {
            
            RoomTypeEntity roomType = roomTypeEntityControllerRemote.retrieveRoomTypeByName(roomTypeName);
            
            walkInReservationEntityControllerRemote.reserveRoom(roomTypeName, startDate, endDate, numOfRoomRequired);
            
        } catch (RoomTypeNotFoundException ex) {
            
            System.err.println(ex.getMessage());
        }
        
        System.out.print("\nWould you to reserve another room? (Y/N): ");
        String response = scanner.nextLine().trim();
        
        if ( response.equalsIgnoreCase("Y") ) {
            searchRoomAgain(startDate, endDate, guestFirstName, 
                    guestLastName, guestIdentificationNumber, guestContactNumber, guestEmail);
        } else {
            walkInReservationEntityControllerRemote.checkOut(currentEmployee.getEmployeeId(), startDate, endDate, guestFirstName, 
                    guestLastName, guestIdentificationNumber, guestContactNumber, guestEmail);
        }
    }
    
    public void guestCheckin(){
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Reservation ID: >");
        Long reservationId = scanner.nextLong();
        System.out.print("/nName of guest: ");
        String guestName = scanner.nextLine().trim();
        
        ReservationEntity reservation = reservationEntityControllerRemote.retrieveReservationById(reservationId);
        
        List<RoomEntity> rooms = reservation.getRooms();
        
        Boolean allRoomsReadyForCheckIn = Boolean.TRUE;
        
        for(RoomEntity room : rooms) {
            // If any of the rooms reserved is not ready for check in
            if ( room.getIsReady().equals(Boolean.FALSE) ) {
                allRoomsReadyForCheckIn = Boolean.FALSE;
                break;
            }
        }      
// Should we split out and show which room is available and which room is not?? 
        
        if ( allRoomsReadyForCheckIn.equals(Boolean.TRUE) ) {     
            for(RoomEntity room : rooms) {
                if ( room.getIsReady().equals(Boolean.TRUE) ) {
                    room.setGuest(guestName);
                    room.setRoomStatus(RoomStatus.OCCUPIED);
                }
            }
        } else {
            System.out.println("Not all rooms ready for check in");
        }
    }
    
    public void guestCheckout() throws RoomNotFoundException{
        
        Scanner scanner = new Scanner(System.in);
        
        // next reservation become your current reservation
        // if nextReservation != null; set currentReservation = nextReservation
        // set to Allocated
        
        while(true) {
            System.out.print("\nEnter room number to checkout: >");
            String roomNumber = scanner.nextLine().trim();
            RoomEntity room = roomEntityControllerRemote.retrieveRoomByRoomNumber(roomNumber);
            
            if ( room.getRoomStatus().equals(RoomStatus.OCCUPIED) ) {
                
                if ( room.getNextReservation() != null ) {
                    room.setCurrentReservation(room.getNextReservation());
                    room.setNextReservation(null);
                } else {
                    room.setCurrentReservation(null);
                    room.setRoomStatus(RoomStatus.VACANT);
                }
            //  room.getCurrentReservation().getRooms().remove(room);
                room.setIsReady(Boolean.FALSE);
                room.setGuest(null);
                System.out.println("Room " + room.getRoomNumber() + " successfully checked out!");
// TO-DO: Set timer to change all room to isReady at 2pm
            } else {
                System.out.println("\nRoom is not occupied");
            }
            System.out.print("Do you wish to perform another check out? (Y/N): >");
            String response = scanner.nextLine().trim();
            if (response.equalsIgnoreCase("N")) {
                break;
            }
        }
    }
}
