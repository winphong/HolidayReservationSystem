/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package externalmanagementclient;

import ejb.session.stateful.PartnerReservationEntityControllerRemote;
import ejb.session.stateless.InventoryControllerRemote;
import ejb.session.stateless.PartnerEntityControllerRemote;
import ejb.session.stateless.ReservationEntityControllerRemote;
import ejb.session.stateless.RoomEntityControllerRemote;
import ejb.session.stateless.RoomRateEntityControllerRemote;
import ejb.session.stateless.RoomTypeEntityControllerRemote;
import entity.PartnerEntity;
import entity.PartnerReservationEntity;
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

    private static ReservationEntityControllerRemote reservationEntityControllerRemote;
    private static RoomEntityControllerRemote roomEntityControllerRemote;
    private static RoomRateEntityControllerRemote roomRateEntityControllerRemote;
    private static RoomTypeEntityControllerRemote roomTypeEntityControllerRemote;
    private static InventoryControllerRemote inventoryControllerRemote;
    private static PartnerReservationEntityControllerRemote partnerReservationEntityControllerRemote;
    private static PartnerEntityControllerRemote partnerEntityControllerRemote;    
    
    private PartnerEntity currentPartner;
    
    public MainApp() {
    }

    public MainApp(RoomEntityControllerRemote roomEntityControllerRemote, RoomRateEntityControllerRemote roomRateEntityControllerRemote, RoomTypeEntityControllerRemote roomTypeEntityControllerRemote, InventoryControllerRemote inventoryControllerRemote, PartnerReservationEntityControllerRemote partnerReservationEntityControllerRemote, PartnerEntityControllerRemote partnerEntityControllerRemote, ReservationEntityControllerRemote reservationEntityControllerRemote) {
        this();
        this.roomEntityControllerRemote = roomEntityControllerRemote;
        this.roomRateEntityControllerRemote = roomRateEntityControllerRemote;
        this.roomTypeEntityControllerRemote = roomTypeEntityControllerRemote;
        this.inventoryControllerRemote = inventoryControllerRemote;
        this.partnerReservationEntityControllerRemote = partnerReservationEntityControllerRemote;
        this.partnerEntityControllerRemote = partnerEntityControllerRemote;
        this.reservationEntityControllerRemote = reservationEntityControllerRemote;
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
                        partnerLogin();                       
                        menuMain();

                    } catch (InvalidLoginCredentialException ex) {
                        System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                    }
                } else if (response == 2) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 2) {
                break;
            }
        }
    }
    
    private void partnerLogin() throws InvalidLoginCredentialException {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";

        System.out.println("*** Merlion Hotel HoRS Reservation Client :: Login ***\n");
        System.out.print("Enter Username: ");
        username = scanner.nextLine().trim();
        System.out.print("Enter Password: ");
        password = scanner.nextLine().trim();

        if (username.length() > 0 && password.length() > 0) {
            currentPartner = partnerEntityControllerRemote.partnerLogin(username, password);
            System.out.println("Login successful!\n");
        } else {
            throw new InvalidLoginCredentialException("Missing login credential!");
        }
    }
    
    private void menuMain(){
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Merlion Hotel HoRS Reservation Client ***\n");
            System.out.println("You are logined as " + currentPartner.getCompanyName());
            System.out.println("1: Search Hotel Room");
            System.out.println("2: View Partner Reservation Details");
            System.out.println("3: View All Partner Reservations");
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
        System.out.println("*** Merlion Hotel HoRS Reservation Client :: View Partner Reservation Details***\n");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Reservation Id: ");
        Long id = scanner.nextLong();
        try{
            PartnerReservationEntity reservation = partnerReservationEntityControllerRemote.retrieveReservationById(id);
            System.out.printf("%20s%20s%15s%15s%15s\n", "Reservation Id", "Reservation Date", "Start Date", "End Date", "Total Amount");
            System.out.printf("%20s%20s%15s%15s%15s\n", reservation.getReservationId(), reservation.getBookingDate(), reservation.getStartDate(), reservation.getEndDate(), reservation.getTotalAmount());
            
            List <ReservationLineItemEntity> items = reservation.getReservationLineItemEntities();
            System.out.println("Reservation Details: ");
            System.out.println("-------------------------");
            System.out.printf("%20s%20s%15s\n", "Room Type", "Number of Rooms", "Total Amount");
            for (ReservationLineItemEntity item : items){
                System.out.printf("%20s%20s%15s\n", item.getRoomType(), item.getNumOfRoomBooked(), item.getTotalAmount());
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
        System.out.println("*** Merlion Hotel HoRS Reservation Client :: View All Partner Reservations***\n");
        List <PartnerReservationEntity> reservations = partnerReservationEntityControllerRemote.viewAllReservations(currentPartner);
        if (reservations.size()>0){
            System.out.println();
            System.out.printf("%20s%20s%15s%15s%15s\n", "Reservation Id", "Reservation Date", "Start Date", "End Date", "Total Amount");
            for (PartnerReservationEntity reservation: reservations){
                System.out.printf("%20s%20s%15s%15s%15s\n", reservation.getReservationId(), reservation.getBookingDate(), reservation.getStartDate(), reservation.getEndDate(), reservation.getTotalAmount());
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
