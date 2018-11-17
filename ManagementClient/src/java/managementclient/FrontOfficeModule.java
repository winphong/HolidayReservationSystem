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
import entity.ReservationEntity;
import entity.RoomEntity;
import entity.RoomTypeEntity;
import entity.WalkinReservationEntity;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import util.enumeration.RoomStatus;
import util.exception.CheckInException;
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
            System.out.println("-----------------------");
            System.out.println("5: Back\n");
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

    private void searchRoom() throws RoomTypeNotFoundException, Exception {

        System.out.println("*** Merlion Hotel HoRS :: Front Office :: Walk-in Search Room ***\n");
        Scanner scanner = new Scanner(System.in);

        //DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/mm");
        System.out.println("Enter start date (dd/mm/yyyy): ");
        String date = scanner.nextLine().trim();
        LocalDate startDate = LocalDate.of(Integer.parseInt(date.substring(6)), Integer.parseInt(date.substring(3, 5)), Integer.parseInt(date.substring(0, 2)));
        System.out.println("Enter end date (dd/mm/yyyy): ");
        date = scanner.nextLine().trim();
        LocalDate endDate = LocalDate.of(Integer.parseInt(date.substring(6)), Integer.parseInt(date.substring(3, 5)), Integer.parseInt(date.substring(0, 2)));
        System.out.println("Enter number of rooms required: ");
        Integer numOfRoomRequired = scanner.nextInt();

        List<RoomTypeEntity> availableRoomTypes = inventoryControllerRemote.searchAvailableRoom(startDate, endDate, numOfRoomRequired);

        Integer index = 1;

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

    private void makeReservation(LocalDate startDate, LocalDate endDate, Integer numOfRoomRequired) throws RoomTypeNotFoundException, Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.print("\nSelect available room type: >");
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

        //ReservationEntity newReservation = new WalkinReservationEntity();
        try {

            RoomTypeEntity roomType = roomTypeEntityControllerRemote.retrieveRoomTypeByName(roomTypeName);

            walkInReservationEntityControllerRemote.reserveRoom(roomType, startDate, endDate, numOfRoomRequired);

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
            ReservationEntity newReservation = walkInReservationEntityControllerRemote.checkOut(currentEmployee.getEmployeeId(), startDate, endDate, guestFirstName,
                    guestLastName, guestIdentificationNumber, guestContactNumber, guestEmail);

            // If the reservation's start date = current date, the call walkInAllocateRoom method;
            if (newReservation.getStartDate().equals(Date.valueOf(LocalDate.now()))) {
                roomEntityControllerRemote.walkInAllocateRoom(newReservation.getReservationId());
            }
        }
    }

    private void searchRoomAgain(LocalDate startDate, LocalDate endDate, String guestFirstName, String guestLastName,
            String guestIdentificationNumber, String guestContactNumber, String guestEmail) throws RoomTypeNotFoundException, Exception {

        System.out.println("*** Merlion Hotel HoRS :: Front Office :: Walk-in Search Room ***\n");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter number of rooms required: ");
        Integer numOfRoomRequired = scanner.nextInt();

        List<RoomTypeEntity> availableRoomTypes = inventoryControllerRemote.searchAvailableRoom(startDate, endDate, numOfRoomRequired);

        Integer index = 1;

        for (RoomTypeEntity availableRoomType : availableRoomTypes) {

            System.out.println(availableRoomType.getName());
            index++;
        }

        System.out.print("\nReserve room? (Y/N) > ");
        String response = scanner.next().toUpperCase();

        if (response.equalsIgnoreCase("Y")) {
            makeReservationAgain(startDate, endDate, numOfRoomRequired, guestFirstName, guestLastName, guestIdentificationNumber, guestContactNumber, guestEmail);

        } else {
            System.out.println("\nChecking out!!");
            ReservationEntity newReservation = walkInReservationEntityControllerRemote.checkOut(currentEmployee.getEmployeeId(), startDate, endDate, guestFirstName,
                    guestLastName, guestIdentificationNumber, guestContactNumber, guestEmail);

            // If the reservation's start date = current date, the call walkInAllocateRoom method;
            if (newReservation.getStartDate().equals(Date.valueOf(LocalDate.now()))) {
                roomEntityControllerRemote.walkInAllocateRoom(newReservation.getReservationId());
            }
        }
    }

    private void makeReservationAgain(LocalDate startDate, LocalDate endDate, Integer numOfRoomRequired, String guestFirstName,
            String guestLastName, String guestIdentificationNumber, String guestContactNumber, String guestEmail) throws RoomTypeNotFoundException, Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nSelect available room type: >");
        String roomTypeName = scanner.nextLine().trim();

        //ReservationEntity newReservation = new WalkinReservationEntity();
        try {

            RoomTypeEntity roomType = roomTypeEntityControllerRemote.retrieveRoomTypeByName(roomTypeName);

            walkInReservationEntityControllerRemote.reserveRoom(roomType, startDate, endDate, numOfRoomRequired);

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
            ReservationEntity newReservation = walkInReservationEntityControllerRemote.checkOut(currentEmployee.getEmployeeId(), startDate, endDate, guestFirstName,
                    guestLastName, guestIdentificationNumber, guestContactNumber, guestEmail);

            // If the reservation's start date = current date, the call walkInAllocateRoom method;
            if (newReservation.getStartDate().equals(Date.valueOf(LocalDate.now()))) {
                roomEntityControllerRemote.walkInAllocateRoom(newReservation.getReservationId());
            }
        }
    }

    public void guestCheckin() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Reservation ID: ");
        Long reservationId = scanner.nextLong();
        scanner.nextLine();
        System.out.print("Name of guest: ");
        String guestName = scanner.nextLine().trim();

        Boolean checkIn;
        try {
            checkIn = reservationEntityControllerRemote.checkIn(reservationId, guestName);
            if (checkIn) {
                System.out.println("Check In Successful!");
            } else {
                System.out.println("Error checking in!");
            }
        } catch (CheckInException ex) {
            System.out.println("Error checking in: " + ex.getMessage());
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
                System.out.print("\nEnter room number to checkout: >");
                String roomNumber = scanner.nextLine().trim();
                System.out.println();
                
                if (roomEntityControllerRemote.checkOut(roomNumber)) {
                    
                    System.out.println("Room " + roomNumber + " successfully checked out!");
                } else {
                    System.out.println("\nRoom is not occupied");
                }
                System.out.print("Do you wish to perform another check out? (Y/N): >");
                String response = scanner.nextLine().trim();
                if (response.equalsIgnoreCase("N")) {
                    break;
                }
            } catch (ReservationNotFoundException ex) {
                System.out.println("Error checking out: " + ex.getMessage());
            }
        }
    }

    public void allocateRoom() {

        ejbTimerSessionBeanRemote.allocateRoom();
    }
}
