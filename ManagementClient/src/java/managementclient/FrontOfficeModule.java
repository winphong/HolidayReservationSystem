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
import ejb.session.stateless.EjbTimerSessionBeanRemote;
import ejb.session.stateless.InventoryControllerRemote;
import ejb.session.stateless.ReservationEntityControllerRemote;
import ejb.session.stateless.RoomTypeEntityControllerRemote;
import entity.OnlineReservationEntity;
import entity.PartnerReservationEntity;
import entity.ReservationEntity;
import entity.RoomTypeEntity;
import entity.WalkinReservationEntity;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import javax.ejb.EJB;
import util.exception.ReservationNotFoundException;
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
    @EJB
    private EjbTimerSessionBeanRemote ejbTimerSessionBeanRemote;

    private EmployeeEntity currentEmployee;

    public FrontOfficeModule() {
    }

    public FrontOfficeModule(ReservationEntityControllerRemote reservationEntityControllerRemote, GuestEntityControllerRemote guestEntityControllerRemote, RoomEntityControllerRemote roomEntityControllerRemote, InventoryControllerRemote inventoryControllerRemote, RoomTypeEntityControllerRemote roomTypeEntityControllerRemote, WalkinReservationEntityControllerRemote walkInReservationEntityControllerRemote, OnlineReservationEntityControllerRemote onlineReservationEntityControllerRemote, EmployeeEntity currentEmployee, EjbTimerSessionBeanRemote ejbTimerSessionBeanRemote) {

        this();
        this.reservationEntityControllerRemote = reservationEntityControllerRemote;
        this.guestEntityControllerRemote = guestEntityControllerRemote;
        this.roomEntityControllerRemote = roomEntityControllerRemote;
        this.inventoryControllerRemote = inventoryControllerRemote;
        this.roomTypeEntityControllerRemote = roomTypeEntityControllerRemote;
        this.walkInReservationEntityControllerRemote = walkInReservationEntityControllerRemote;
        this.onlineReservationEntityControllerRemote = onlineReservationEntityControllerRemote;
        this.ejbTimerSessionBeanRemote = ejbTimerSessionBeanRemote;
        this.currentEmployee = currentEmployee;
    }

    public void menuFrontOffice() throws InvalidAccessRightException, Exception {
        if (currentEmployee.getAccessRight() != EmployeeAccessRight.GUESTRELATIONOFFICER) {
            throw new InvalidAccessRightException("You don't have MANAGER rights to access the front office module.");
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
            System.out.println("4: Allocate Room");
            System.out.println("5: Make room ready");
            System.out.println("-----------------------");
            System.out.println("6: Back\n");
            System.out.println();
            response = 0;

            while (response < 1 || response > 5) {
                System.out.print("Enter option: ");

                response = scanner.nextInt();

                if (response == 1) {
                    searchRoom();
                } else if (response == 2) {
                    guestCheckin();
                } else if (response == 3) {
                    guestCheckout();
                } else if (response == 4) {
                    allocateRoom();
                } else if (response == 5) {
                    finishUpHouseKeeping();
                } else if (response == 6) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 6) {
                break;
            }
        }
    }

    private void searchRoom() throws RoomTypeNotFoundException, Exception {

        System.out.println("*** Merlion Hotel HoRS :: Front Office :: Walk-in Search Room ***\n");
        Scanner scanner = new Scanner(System.in);

        //DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/mm");
        System.out.println("Enter start date (dd/mm/yyyy): ");
        String startDate = scanner.nextLine().trim();
        System.out.println("Enter end date (dd/mm/yyyy): ");
        String endDate = scanner.nextLine().trim();
        System.out.println("Enter number of rooms required: ");
        Integer numOfRoomRequired = scanner.nextInt();

        List<RoomTypeEntity> availableRoomTypes = inventoryControllerRemote.searchAvailableRoom(startDate, endDate, numOfRoomRequired);

        Integer index = 1;
        
        System.out.println("Available Room Type(s)");

        for (RoomTypeEntity availableRoomType : availableRoomTypes) {

            System.out.println(availableRoomType.getName());
            index++;
        }

        System.out.print("\nReserve room? (Y/N) > ");
        String response = scanner.next().toUpperCase();

        if (response.equalsIgnoreCase("Y")) {
            makeReservation(startDate, endDate, numOfRoomRequired);
        }
    }

    private void makeReservation(String startDate, String endDate, Integer numOfRoomRequired) throws RoomTypeNotFoundException, Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.print("\nSelect available room type: ");
        String roomTypeName = scanner.nextLine().trim();
        System.out.print("Enter first name: ");
        String guestFirstName = scanner.nextLine().trim();
        System.out.print("Enter last name: ");
        String guestLastName = scanner.nextLine().trim();
        System.out.print("Enter guest identification number: ");
        String guestIdentificationNumber = scanner.nextLine().trim();
        System.out.print("Enter contact number: ");
        String guestContactNumber = scanner.nextLine().trim();
        System.out.print("Enter email: ");
        String guestEmail = scanner.nextLine().trim();
        LocalDate start = LocalDate.of(Integer.parseInt(startDate.substring(6)), Integer.parseInt(startDate.substring(3, 5)), Integer.parseInt(startDate.substring(0, 2)));
            LocalDate end = LocalDate.of(Integer.parseInt(endDate.substring(6)), Integer.parseInt(endDate.substring(3, 5)), Integer.parseInt(endDate.substring(0, 2)));
        //ReservationEntity newReservation = new WalkinReservationEntity();
        try {

            RoomTypeEntity roomType = roomTypeEntityControllerRemote.retrieveRoomTypeByName(roomTypeName);

            walkInReservationEntityControllerRemote.reserveRoom(roomType, start, end, numOfRoomRequired);

        } catch (RoomTypeNotFoundException ex) {

            System.err.println(ex.getMessage());
        }

        System.out.print("\nWould you to reserve another room? (Y/N): > ");
        String response = scanner.nextLine().trim();

        if (response.equalsIgnoreCase("Y")) {
            searchRoomAgain(startDate, endDate, guestFirstName,
                    guestLastName, guestIdentificationNumber, guestContactNumber, guestEmail);
        } else {

            System.out.println("\nChecking out!!");
            ReservationEntity newReservation = walkInReservationEntityControllerRemote.checkOut(currentEmployee.getEmployeeId(), start, end, guestFirstName,
                    guestLastName, guestIdentificationNumber, guestContactNumber, guestEmail);

            // If the reservation's start date = current date, the call walkInAllocateRoom method;
            if (newReservation.getStartDate().equals(Date.valueOf(LocalDate.now()))) {
                ejbTimerSessionBeanRemote.allocateRoom(newReservation.getReservationId());
            }
        }
    }

    private void searchRoomAgain(String startDate, String endDate, String guestFirstName, String guestLastName,
            String guestIdentificationNumber, String guestContactNumber, String guestEmail) throws RoomTypeNotFoundException, Exception {

        System.out.println("*** Merlion Hotel HoRS :: Front Office :: Walk-in Search Room ***\n");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter number of rooms required: ");
        Integer numOfRoomRequired = scanner.nextInt();

        List<RoomTypeEntity> availableRoomTypes = inventoryControllerRemote.searchAvailableRoom(startDate, endDate, numOfRoomRequired);
        
        Integer index = 1;
        

        System.out.println("Available Room Type(s):");
        for (RoomTypeEntity availableRoomType : availableRoomTypes) {

            System.out.println(availableRoomType.getName());
            index++;
        }

        System.out.print("\nReserve room? (Y/N) > ");
        String response = scanner.next().toUpperCase();
        LocalDate start = LocalDate.of(Integer.parseInt(startDate.substring(6)), Integer.parseInt(startDate.substring(3, 5)), Integer.parseInt(startDate.substring(0, 2)));
            LocalDate end = LocalDate.of(Integer.parseInt(endDate.substring(6)), Integer.parseInt(endDate.substring(3, 5)), Integer.parseInt(endDate.substring(0, 2)));
        if (response.equalsIgnoreCase("Y")) {
            makeReservationAgain(startDate, endDate, numOfRoomRequired, guestFirstName, guestLastName, guestIdentificationNumber, guestContactNumber, guestEmail);

        } else {
            System.out.println("\nChecking out!!");
            ReservationEntity newReservation = walkInReservationEntityControllerRemote.checkOut(currentEmployee.getEmployeeId(), start, end, guestFirstName,
                    guestLastName, guestIdentificationNumber, guestContactNumber, guestEmail);

            // If the reservation's start date = current date, the call walkInAllocateRoom method;
            if (newReservation.getStartDate().equals(Date.valueOf(LocalDate.now()))) {
                ejbTimerSessionBeanRemote.allocateRoom(newReservation.getReservationId());
            }
        }
    }

    private void makeReservationAgain(String startDate, String endDate, Integer numOfRoomRequired, String guestFirstName,
            String guestLastName, String guestIdentificationNumber, String guestContactNumber, String guestEmail) throws RoomTypeNotFoundException, Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nSelect available room type: > ");
        String roomTypeName = scanner.nextLine().trim();
        LocalDate start = LocalDate.of(Integer.parseInt(startDate.substring(6)), Integer.parseInt(startDate.substring(3, 5)), Integer.parseInt(startDate.substring(0, 2)));
            LocalDate end = LocalDate.of(Integer.parseInt(endDate.substring(6)), Integer.parseInt(endDate.substring(3, 5)), Integer.parseInt(endDate.substring(0, 2)));

        //ReservationEntity newReservation = new WalkinReservationEntity();
        try {

            RoomTypeEntity roomType = roomTypeEntityControllerRemote.retrieveRoomTypeByName(roomTypeName);

            walkInReservationEntityControllerRemote.reserveRoom(roomType, start, end, numOfRoomRequired);

        } catch (RoomTypeNotFoundException ex) {

            System.err.println(ex.getMessage());
        }

        System.out.print("\nWould you to reserve another room? (Y/N): ");
        String response = scanner.nextLine().trim();

        if (response.equalsIgnoreCase("Y")) {
            searchRoomAgain(startDate, endDate, guestFirstName,
                    guestLastName, guestIdentificationNumber, guestContactNumber, guestEmail);
        } else {

            System.out.println("\nChecking out!!");
            ReservationEntity newReservation = walkInReservationEntityControllerRemote.checkOut(currentEmployee.getEmployeeId(), start, end, guestFirstName,
                    guestLastName, guestIdentificationNumber, guestContactNumber, guestEmail);

            // If the reservation's start date = current date, the call walkInAllocateRoom method;
            if (newReservation.getStartDate().equals(Date.valueOf(LocalDate.now()))) {
                ejbTimerSessionBeanRemote.allocateRoom(newReservation.getReservationId());
            }
        }
    }

    public void guestCheckin() throws ReservationNotFoundException {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Reservation ID: ");
        Long reservationId = scanner.nextLong();
        System.out.println("\nPlease select the type of reservation: ");
        System.out.println("***************************************");
        System.out.println("  Online Reservation  ");
        System.out.println("  Partner Reservation  ");
        System.out.println("  Walkin Reservation  ");
        System.out.println("***************************************");
        System.out.println("Type of reservation : ");
        String typeOfReservation = scanner.next() + scanner.nextLine();
       
        ReservationEntity reservation = reservationEntityControllerRemote.retrieveReservationById(reservationId);
        
        try {
            if ( roomEntityControllerRemote.checkIn(reservationId, typeOfReservation) ) {
                if ( typeOfReservation.equals("Walkin Reservation") ) {
                    System.out.println(((WalkinReservationEntity) reservationEntityControllerRemote.retrieveReservationById(reservationId)).getGuestLastName() + " has been successfully checked in");
                } else if ( typeOfReservation.equals("Partner Reservation") ) {
                    System.out.println(((PartnerReservationEntity) reservationEntityControllerRemote.retrieveReservationById(reservationId)).getCustomerFirstName()+ " has been successfully checked in");
                } else if ( typeOfReservation.equals("Online Reservation") ) {
                    System.out.println(((OnlineReservationEntity) reservationEntityControllerRemote.retrieveReservationById(reservationId)).getGuest().getFirstName() + " has been successfully checked in");
                } else {
                    System.out.println("Failure check in");
                }
                 
            } else {
                System.out.println("Not all rooms ready for check in");
            }
        } catch (ReservationNotFoundException ex) {
            System.out.println("Error checking in: Reservation not found!");
        }

    }

    public void guestCheckout() throws RoomNotFoundException {

        Scanner scanner = new Scanner(System.in);

        // next reservation become your current reservation
        // if nextReservation != null; set currentReservation = nextReservation
        // set to Allocated
        while (true) {
            try {
                System.out.print("\nEnter room number to checkout: > ");
                String roomNumber = scanner.nextLine().trim();
                System.out.println();
                
                if (roomEntityControllerRemote.checkOut(roomNumber)) {
                    
                    System.out.println("Room " + roomNumber + " successfully checked out!");
                } else {
                    System.out.println("\nRoom is not occupied");
                }
                System.out.print("Do you wish to perform another check out? (Y/N): > ");
                String response = scanner.nextLine().trim();
                if (response.equalsIgnoreCase("N")) {
                    break;
                }
            } catch (ReservationNotFoundException ex) {
                System.out.println("Error checking out: " + ex.getMessage());
            }
        }
    }
    
    private void allocateRoom() throws ReservationNotFoundException, RoomTypeNotFoundException, Exception {
        ejbTimerSessionBeanRemote.allocateRoom();
    }
    
    private void finishUpHouseKeeping() {
        ejbTimerSessionBeanRemote.finishUpHousekeeping();
    }
}
