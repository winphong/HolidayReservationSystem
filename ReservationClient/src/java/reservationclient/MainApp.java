/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservationclient;

import ejb.session.stateful.OnlineReservationEntityControllerRemote;
import ejb.session.stateless.GuestEntityControllerRemote;
import ejb.session.stateless.InventoryControllerRemote;
import ejb.session.stateless.RoomEntityControllerRemote;
import ejb.session.stateless.RoomRateEntityControllerRemote;
import ejb.session.stateless.RoomTypeEntityControllerRemote;
import entity.GuestEntity;
import entity.OnlineReservationEntity;
import entity.ReservationLineItemEntity;
import java.util.List;
import java.util.Scanner;
import util.exception.InvalidLoginCredentialException;
import util.exception.ReservationNotFoundException;

/**
 *
 * @author Asus
 */
public class MainApp {
    
    private RoomRateEntityControllerRemote roomRateEntityControllerRemote;
    private RoomEntityControllerRemote roomEntityControllerRemote;
    private RoomTypeEntityControllerRemote roomTypeEntityControllerRemote;
    private OnlineReservationEntityControllerRemote onlineReservationEntityControllerRemote;
    private InventoryControllerRemote inventoryControllerRemote;
    private GuestEntityControllerRemote guestEntityControllerRemote;
    
    private GuestEntity currentGuest;

    public MainApp() {
    }
    
    public MainApp(RoomRateEntityControllerRemote roomRateEntityControllerRemote, RoomEntityControllerRemote roomEntityControllerRemote, RoomTypeEntityControllerRemote roomTypeEntityControllerRemote, OnlineReservationEntityControllerRemote onlineReservationEntityControllerRemote, InventoryControllerRemote inventoryControllerRemote, GuestEntityControllerRemote guestEntityControllerRemote) {
        this();
        this.roomRateEntityControllerRemote = roomRateEntityControllerRemote;
        this.roomEntityControllerRemote = roomEntityControllerRemote;
        this.roomTypeEntityControllerRemote = roomTypeEntityControllerRemote;
        this.onlineReservationEntityControllerRemote = onlineReservationEntityControllerRemote;
        this.inventoryControllerRemote = inventoryControllerRemote;
        this.guestEntityControllerRemote = guestEntityControllerRemote;
    }
    
    public void runApp() {
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
                        guestLogin();
                        System.out.println("Login successful!\n");
                        menuMain();

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
    
    private void guestLogin() throws InvalidLoginCredentialException {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";

        System.out.println("*** Merlion Hotel HoRS Reservation Client :: Login ***\n");
        System.out.print("Enter Username: ");
        username = scanner.nextLine().trim();
        System.out.print("Enter Password: ");
        password = scanner.nextLine().trim();

        if (username.length() > 0 && password.length() > 0) {
            currentGuest = guestEntityControllerRemote.guestLogin(username, password);
        } else {
            throw new InvalidLoginCredentialException("Missing login credential!");
        }
    }
    
    private void register(){
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
        GuestEntity newGuest = new GuestEntity(firstName, lastName, username, password, phoneNumber, email);
        guestEntityControllerRemote.registerGuest(newGuest);
        System.out.println("You have successfully registered as a new guest!");
    }
    
    private void menuMain(){
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
    
    private void searchRoom(){
        reserveRoom();
    }
    
    private void reserveRoom(){
        
    }
    
    private void viewReservationDetails(){
        System.out.println("*** Merlion Hotel HoRS Reservation Client :: View My Reservation Details***\n");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Reservation Id: ");
        Long id = scanner.nextLong();
        try{
            OnlineReservationEntity reservation = onlineReservationEntityControllerRemote.retrieveReservationById(id);
            System.out.printf("%20s%20s%15s%15s%15s\n", "Reservation Id", "Reservation Date", "Start Date", "End Date", "Total Amount");
            System.out.printf("%20s%20s%15s%15s%15s\n", reservation.getId(), reservation.getBookingDate(), reservation.getStartDate(), reservation.getEndDate(), reservation.getTotalAmount());
            
            List <ReservationLineItemEntity> items = reservation.getReservationLineItemEntities();
            System.out.println("Reservation Details: ");
            System.out.println("-------------------------");
            System.out.printf("%20s%20s%15s\n", "Room Type", "Number of Rooms", "Total Amount");
            for (ReservationLineItemEntity item : items){
                System.out.printf("%20s%20s%15s\n", item.getRoomType(), item.getNumberOfRooms(), item.getTotalAmount());
            }
            
            System.out.println();
            System.out.print("Press any key to continue...: ");
            scanner.nextLine();
        }
        catch (ReservationNotFoundException ex){
            System.out.println("Reservation " + id + " not found!");
        }
    }
    
    private void viewAllReservations(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS Reservation Client :: View All My Reservations***\n");
        List <OnlineReservationEntity> reservations = onlineReservationEntityControllerRemote.viewAllReservations(currentGuest);
        if (reservations.size()>0){
            System.out.println();
            System.out.printf("%20s%20s%15s%15s%15s\n", "Reservation Id", "Reservation Date", "Start Date", "End Date", "Total Amount");
            for (OnlineReservationEntity reservation: reservations){
                System.out.printf("%20s%20s%15s%15s%15s\n", reservation.getId(), reservation.getBookingDate(), reservation.getStartDate(), reservation.getEndDate(), reservation.getTotalAmount());
            }
            System.out.println();
            System.out.print("Press any key to continue...: ");
            scanner.nextLine();
        }
        else{
            System.out.println("No reservations available.");
        }
    }
}
