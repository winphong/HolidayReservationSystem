/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservationclient;

import ejb.session.stateful.OnlineReservationEntityControllerRemote;
import ejb.session.stateless.EjbTimerSessionBeanRemote;
import ejb.session.stateless.GuestEntityControllerRemote;
import ejb.session.stateless.InventoryControllerRemote;
import ejb.session.stateless.ReservationEntityControllerRemote;
import ejb.session.stateless.RoomEntityControllerRemote;
import ejb.session.stateless.RoomRateEntityControllerRemote;
import ejb.session.stateless.RoomTypeEntityControllerRemote;
import entity.GuestEntity;
import entity.OnlineReservationEntity;
import entity.ReservationEntity;
import entity.ReservationLineItemEntity;
import entity.RoomTypeEntity;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import util.exception.GuestNotFoundException;
import util.exception.InvalidLoginCredentialException;
import util.exception.ReservationNotFoundException;
import util.exception.RoomTypeNotFoundException;

/**
 *
 * @author Asus
 */
public class GuestMainApp {

    private ReservationEntityControllerRemote reservationEntityControllerRemote;
    private RoomRateEntityControllerRemote roomRateEntityControllerRemote;
    private RoomEntityControllerRemote roomEntityControllerRemote;
    private RoomTypeEntityControllerRemote roomTypeEntityControllerRemote;
    private OnlineReservationEntityControllerRemote onlineReservationEntityControllerRemote;
    private InventoryControllerRemote inventoryControllerRemote;
    private GuestEntityControllerRemote guestEntityControllerRemote;
    private EjbTimerSessionBeanRemote ejbTimerSessionBeanRemote;

    private GuestEntity currentGuest;

    public GuestMainApp() {
    }

    public GuestMainApp(RoomRateEntityControllerRemote roomRateEntityControllerRemote, RoomEntityControllerRemote roomEntityControllerRemote, RoomTypeEntityControllerRemote roomTypeEntityControllerRemote, OnlineReservationEntityControllerRemote onlineReservationEntityControllerRemote, InventoryControllerRemote inventoryControllerRemote, GuestEntityControllerRemote guestEntityControllerRemote, ReservationEntityControllerRemote reservationEntityControllerRemote, EjbTimerSessionBeanRemote ejbTimerSessionBeanRemote) {
        this();
        this.roomRateEntityControllerRemote = roomRateEntityControllerRemote;
        this.roomEntityControllerRemote = roomEntityControllerRemote;
        this.roomTypeEntityControllerRemote = roomTypeEntityControllerRemote;
        this.onlineReservationEntityControllerRemote = onlineReservationEntityControllerRemote;
        this.inventoryControllerRemote = inventoryControllerRemote;
        this.guestEntityControllerRemote = guestEntityControllerRemote;
        this.reservationEntityControllerRemote = reservationEntityControllerRemote;
        this.ejbTimerSessionBeanRemote = ejbTimerSessionBeanRemote;
    }

    public void runApp() throws Exception {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to Merlion Hotel HoRS Reservation Client ***\n");
            System.out.println("1: Login");
            System.out.println("2. Register");
            System.out.println("3: Exit\n");
            System.out.println();
            response = 0;

            while (response < 1 || response > 3) {
                System.out.print("Enter option: ");
                response = scanner.nextInt();

                if (response == 1) {
                    try {
                        if ( guestLogin() ) {
                            System.out.println("Login successful!\n");
                            menuMain();
                        }
                    } catch (InvalidLoginCredentialException ex) {
                        System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                    }
                } else if (response == 2) {
                    register();
                } else if (response == 3) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 3) {
                break;
            }
        }
    }

    private Boolean guestLogin() throws InvalidLoginCredentialException {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";

        System.out.println("*** Merlion Hotel HoRS Reservation Client :: Login ***\n");
        System.out.print("Enter Username: ");
        username = scanner.nextLine().trim();
        System.out.print("Enter Password: ");
        password = scanner.nextLine().trim();

        if (username.length() > 0 && password.length() > 0) {
            try {
                currentGuest = guestEntityControllerRemote.guestLogin(username, password);
                return Boolean.TRUE;
            } catch (GuestNotFoundException ex) {
                System.out.println("Guest with username " + username + "does not exist!");
                return Boolean.FALSE;
            } catch (InvalidLoginCredentialException ex) {
                System.out.println("Invalid login credential");
                return Boolean.FALSE;
            }
        } else {
            System.out.println("Missing login credential!");
            return Boolean.FALSE;
        }
    }

    private void register() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS Reservation Client :: Register ***\n");
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Identification Number: ");
        String id = scanner.nextLine();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        GuestEntity newGuest = new GuestEntity(firstName, lastName, id, username, password, phoneNumber, email);
        guestEntityControllerRemote.registerGuest(newGuest);
        System.out.println("You have successfully registered as a new guest!");
    }

    private void menuMain() throws Exception {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Merlion Hotel HoRS Reservation Client ***\n");
            System.out.println("You are logined as " + currentGuest.getFirstName() + " " + currentGuest.getLastName());
            System.out.println("1: Search Hotel Room");
            System.out.println("2: View My Reservation Details");
            System.out.println("3: View All My Reservations");
            System.out.println("4: Logout\n");
            System.out.println();
            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("Enter option: ");

                response = scanner.nextInt();

                if (response == 1) {
                    searchRoom();
                } else if (response == 2) {
                    viewReservationDetails();
                } else if (response == 3) {
                    viewAllReservations();
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

    private void searchRoom() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS Reservation Client :: Search Room ***\n");
        System.out.println();
        //DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/mm");
        System.out.println("Enter start date (dd/mm/yyyy): ");
        String startDate = scanner.nextLine().trim();
        System.out.println("Enter end date (dd/mm/yyyy): ");
        String endDate = scanner.nextLine().trim();
        System.out.println("Enter number of rooms required: ");
        Integer numOfRoomRequired = scanner.nextInt();
        System.out.println();
        List<RoomTypeEntity> availableRoomTypes = inventoryControllerRemote.searchAvailableRoom(startDate, endDate, numOfRoomRequired);
        
        Integer index = 1;

        for (RoomTypeEntity availableRoomType : availableRoomTypes) {

            System.out.println(availableRoomType.getName());
            index++;
        }

        System.out.print("\nReserve room? (Y/N) : ");
        String response = scanner.next().toUpperCase();

        if (response.equalsIgnoreCase("Y")) {
            makeReservation(startDate, endDate, numOfRoomRequired);
        }
    }

    // Overloaded method
    private void searchRoomAgain(String startDate, String endDate) throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS Reservation Client :: Search Room ***\n");
        System.out.println();
        System.out.println("Enter number of rooms required: ");
        Integer numOfRoomRequired = scanner.nextInt();

        List<RoomTypeEntity> availableRoomTypes = inventoryControllerRemote.searchAvailableRoom(startDate, endDate, numOfRoomRequired);

        Integer index = 1;

        for (RoomTypeEntity availableRoomType : availableRoomTypes) {

            System.out.println(index + ": " + availableRoomType.getName());
            index++;
        }
        System.out.println();
        System.out.print("\nReserve room? (Y/N) : ");
        String response = scanner.nextLine().trim();

        if (response.equalsIgnoreCase("Y")) {
            makeReservation(startDate, endDate, numOfRoomRequired);
        }
    }

    private void makeReservation(String startDate, String endDate, Integer numOfRoomRequired) throws RoomTypeNotFoundException, Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS Reservation Client :: Reserve Room ***\n");
        System.out.println();
        System.out.print("\nSelect available room type: ");
        String roomTypeName = scanner.nextLine().trim();
        LocalDate start = LocalDate.of(Integer.parseInt(startDate.substring(6)), Integer.parseInt(startDate.substring(3, 5)), Integer.parseInt(startDate.substring(0, 2)));
        LocalDate end = LocalDate.of(Integer.parseInt(endDate.substring(6)), Integer.parseInt(endDate.substring(3, 5)), Integer.parseInt(endDate.substring(0, 2)));
        try {

            RoomTypeEntity roomType = roomTypeEntityControllerRemote.retrieveRoomTypeByName(roomTypeName);

            onlineReservationEntityControllerRemote.reserveRoom(roomType.getName(), start, end, numOfRoomRequired);

        } catch (RoomTypeNotFoundException ex) {

            System.err.println(ex.getMessage());
        }

        System.out.print("\nWould you to make another reservation for the same period(Y/N): ");
        String response = scanner.nextLine().trim();

        if (response.equalsIgnoreCase("Y")) {
            searchRoomAgain(startDate, endDate);
        } else {

            System.out.println("\nChecking out!!");

            ReservationEntity newReservation = onlineReservationEntityControllerRemote.checkOut(currentGuest.getGuestId(), start, end);

            // If the reservation's start date = current date, the call walkInAllocateRoom method;
            if (newReservation.getStartDate().equals(Date.valueOf(LocalDate.now()))) {
                ejbTimerSessionBeanRemote.allocateRoom(newReservation.getReservationId());
            }
        }
    }

    /* private void searchRoomAgain(LocalDate startDate, LocalDate endDate, String guestFirstName, String guestLastName,
        String guestIdentificationNumber, String guestContactNumber, String guestEmail) throws RoomTypeNotFoundException, Exception {

        System.out.println("*** Merlion Hotel HoRS :: Front Office :: Walk-in Search Room ***\n");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter number of rooms required: ");
        Integer numOfRoomRequired = scanner.nextInt();

        List<RoomTypeEntity> availableRoomTypes = inventoryControllerRemote.searchAvailableRoom(startDate.toString(), endDate.toString(), numOfRoomRequired);

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
            ReservationEntity newReservation = onlineReservationEntityControllerRemote.checkOut(currentGuest.getGuestId(), startDate, endDate);

            // If the reservation's start date = current date, the call walkInAllocateRoom method;
            if (newReservation.getStartDate().equals(Date.valueOf(LocalDate.now()))) {
                ejbTimerSessionBeanRemote.allocateRoom(newReservation.getReservationId());
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

            onlineReservationEntityControllerRemote.reserveRoom(roomType.getName(), startDate, endDate, numOfRoomRequired);

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
            ReservationEntity newReservation = onlineReservationEntityControllerRemote.checkOut(currentGuest.getGuestId(), startDate, endDate);

            // If the reservation's start date = current date, the call walkInAllocateRoom method;
            if (newReservation.getStartDate().equals(Date.valueOf(LocalDate.now()))) {
                ejbTimerSessionBeanRemote.allocateRoom(newReservation.getReservationId());
            }

//            ReservationEntity newReservation = onlineReservationEntityControllerRemote.checkOut(currentGuest.getGuestId(), start, end);
//            System.out.println("Reservation " + newReservation.getReservationId() + " made successfully!");
//            System.out.println();

        }
    } */

    private void viewReservationDetails() {
        System.out.println("*** Merlion Hotel HoRS Reservation Client :: View My Reservation Details***\n");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Reservation Id: ");
        Long id = scanner.nextLong();
        try {
            OnlineReservationEntity reservation = onlineReservationEntityControllerRemote.retrieveReservationById(id);
            System.out.printf("%20s%20s%15s%15s%15s\n", "Reservation Id", "Reservation Date", "Start Date", "End Date", "Total Amount");
            System.out.printf("%20s%20s%15s%15s%15s\n", reservation.getReservationId(), reservation.getBookingDate(), reservation.getStartDate(), reservation.getEndDate(), reservation.getTotalAmount());

            List<ReservationLineItemEntity> reservationLineItems = reservation.getReservationLineItemEntities();
            System.out.println("Reservation Details: ");
            System.out.println("-------------------------");
            System.out.printf("%20s%20s%15s\n", "Room Type", "Number of Rooms", "Total Amount");
            for (ReservationLineItemEntity reservationLineItem : reservationLineItems) {
                
                
                System.out.printf("%20s%20s%15s\n", reservationLineItem.getRoomType().getName(), reservationLineItem.getNumOfRoomBooked(), reservationLineItem.getTotalAmount());
            }

            System.out.println();
            System.out.print("Press any key to continue...: ");
            scanner.nextLine();
        } catch (ReservationNotFoundException ex) {
            System.out.println("Reservation " + id + " not found!");
        }
    }

    private void viewAllReservations() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS Reservation Client :: View All My Reservations***\n");
        List<OnlineReservationEntity> reservations = onlineReservationEntityControllerRemote.viewAllReservationsOfGuest(currentGuest);
        if (reservations.size() > 0) {
            System.out.println();
            System.out.printf("%20s%20s%15s%15s%15s\n", "Reservation Id", "Reservation Date", "Start Date", "End Date", "Total Amount");
            for (OnlineReservationEntity reservation : reservations) {
                System.out.printf("%20s%20s%15s%15s%15s\n", reservation.getReservationId(), reservation.getBookingDate(), reservation.getStartDate(), reservation.getEndDate(), reservation.getTotalAmount());
            }
            System.out.println();
            System.out.print("Press any key to continue...: ");
            scanner.nextLine();
        } else {
            System.out.println("No reservations available.");
        }
    }
}
