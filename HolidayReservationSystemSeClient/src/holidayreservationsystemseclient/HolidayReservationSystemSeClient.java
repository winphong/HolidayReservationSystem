/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package holidayreservationsystemseclient;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import ws.client.partner.Exception_Exception;
import ws.client.partner.InvalidLoginCredentialException_Exception;
import ws.client.partner.PartnerEntity;
import ws.client.partner.PartnerReservationEntity;
import ws.client.partner.ReservationEntity;
import ws.client.partner.ReservationLineItemEntity;
import ws.client.partner.ReservationNotFoundException_Exception;
import ws.client.partner.RoomTypeEntity;
import ws.client.partner.RoomTypeNotFoundException_Exception;

/**
 *
 * @author Asus
 */
public class HolidayReservationSystemSeClient {

    private static PartnerEntity currentPartner;

    public static void main(String[] args) {
        runApp();
    }

    public static void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to Merlion Hotel Holiday Reservation System ***\n");
            System.out.println("1: Login");
            System.out.println("2: Exit\n");
            System.out.println();
            response = 0;

            while (response < 1 || response > 3) {
                System.out.print("Enter option: ");
                response = scanner.nextInt();

                if (response == 1) {
                    try {
                        partnerLoginMenu();
                        System.out.println("Login successful!\n");
                        menuMain();
                    } catch (InvalidLoginCredentialException_Exception ex) {
                        System.out.println("Error logging in: " + ex.getMessage());
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

    private static void partnerLoginMenu() throws InvalidLoginCredentialException_Exception {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";

        System.out.println("*** Merlion Hotel Holiday Reservation System :: Login ***\n");
        System.out.print("Enter Username: ");
        username = scanner.nextLine().trim();
        System.out.print("Enter Password: ");
        password = scanner.nextLine().trim();

        if (username.length() > 0 && password.length() > 0) {
            try {
                currentPartner = partnerLogin(username, password);
            } catch (InvalidLoginCredentialException_Exception ex) {
                System.out.println("Missing login credential!");
            }
        }
    }

    private static void menuMain() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Merlion Hotel Holiday Reservation System ***\n");
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

    private static void searchRoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS Reservation Client :: Search Room ***\n");
        System.out.println();
        //DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/mm");
        System.out.println("Enter start date (dd/mm/yyyy): ");
        String startDate = scanner.nextLine().trim();
        System.out.println("Enter end date (dd/mm/yyyy): ");
        String endDate = scanner.nextLine().trim();
        System.out.print("Enter customer first name: ");
        String customerFirstName = scanner.nextLine().trim();
        System.out.print("Enter customer last name: ");
        String customerLastName = scanner.nextLine().trim();
        System.out.print("Enter customer identification number: ");
        String customerIdentificationNumber = scanner.nextLine().trim();
        System.out.print("Enter customer contact number: ");
        String customerContactNumber = scanner.nextLine().trim();
        System.out.print("Enter customer email: ");
        String customerEmail = scanner.nextLine().trim();
        System.out.println("Enter number of rooms required: ");
        Integer numOfRoomRequired = scanner.nextInt();
        System.out.println();
        List<RoomTypeEntity> availableRoomTypes = searchAvailableRoom(startDate, endDate, numOfRoomRequired);

        Integer index = 1;

        for (RoomTypeEntity availableRoomType : availableRoomTypes) {

            System.out.println(availableRoomType.getName());
            index++;
        }

        System.out.print("\nReserve room? (Y/N) : ");
        String response = scanner.next().toUpperCase();

        if (response.equalsIgnoreCase("Y")) {
            try {
                makeReservation(startDate, endDate, customerFirstName, customerLastName, customerIdentificationNumber, customerContactNumber, customerEmail, numOfRoomRequired);
            }
            catch(Exception ex){
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    // Overloaded method
    private static void searchRoomAgain(String customerFirstName, String customerLastName, String customerIdentificationNumber, String customerContactNumber, String customerEmail) throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS Reservation Client :: Search Room ***\n");
        System.out.println();
        System.out.print("Enter start date (dd/mm/yyyy): ");
        String startDate = scanner.nextLine().trim();
        System.out.print("Enter end date (dd/mm/yyyy): ");
        String endDate = scanner.nextLine().trim();
        System.out.println("Enter number of rooms required: ");
        Integer numOfRoomRequired = scanner.nextInt();

        List<RoomTypeEntity> availableRoomTypes = searchAvailableRoom(startDate, endDate, numOfRoomRequired);

        Integer index = 1;

        for (RoomTypeEntity availableRoomType : availableRoomTypes) {

            System.out.println(index + ": " + availableRoomType.getName());
            index++;
        }
        System.out.println();
        System.out.print("\nReserve room? (Y/N) : ");
        String response = scanner.nextLine().trim();

        if (response.equalsIgnoreCase("Y")) {
            makeReservation(startDate, endDate, customerFirstName, customerLastName, customerIdentificationNumber, customerContactNumber, customerEmail, numOfRoomRequired);
        }
    }

    private static void makeReservation(String startDate, String endDate, String customerFirstName, String customerLastName, String customerIdentificationNumber, String customerContactNumber, String customerEmail,Integer numOfRoomRequired) throws RoomTypeNotFoundException_Exception, Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS Reservation Client :: Reserve Room ***\n");
        System.out.println();
        System.out.print("\nSelect available room type: ");
        String roomTypeName = scanner.nextLine().trim();
        
        try {

            RoomTypeEntity roomType = retrieveRoomTypeByName(roomTypeName);

            if (reserveRoom(roomType.getName(), startDate, endDate,numOfRoomRequired) == true){
                System.out.println("Room successfully reserved!");
            }

        } catch (RoomTypeNotFoundException_Exception ex) {

            System.err.println(ex.getMessage());
        }

        System.out.print("\nReserve another room for the same customer? (Y/N): ");
        String response = scanner.nextLine().trim();

        if (response.equalsIgnoreCase("Y")) {
            searchRoomAgain(customerFirstName, customerLastName, customerIdentificationNumber, customerContactNumber, customerEmail);
        } else {

            System.out.println("\nChecking out!!");
            ReservationEntity newReservation = checkOut(currentPartner.getPartnerId(), customerFirstName, customerLastName, customerIdentificationNumber, customerContactNumber, customerEmail, startDate, endDate);
            System.out.println("Reservation " + newReservation.getReservationId() + " made successfully!");
            System.out.println();
        }
    }

    private static void searchRoomAgain(String startDate, String endDate, String customerFirstName, String customerLastName,
            String customerIdentificationNumber, String customerContactNumber, String customerEmail) throws RoomTypeNotFoundException_Exception, Exception {

        System.out.println("*** Merlion Hotel HoRS :: Front Office :: Walk-in Search Room ***\n");
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Enter number of rooms required: ");
        Integer numOfRoomRequired = scanner.nextInt();

        List<RoomTypeEntity> availableRoomTypes = searchAvailableRoom(startDate, endDate, numOfRoomRequired);

        Integer index = 1;

        for (RoomTypeEntity availableRoomType : availableRoomTypes) {

            System.out.println(availableRoomType.getName());
            index++;
        }
        System.out.println();
        System.out.print("\nReserve room? (Y/N) : ");
        String response = scanner.next().toUpperCase();

        if (response.equalsIgnoreCase("Y")) {
            makeReservation(startDate, endDate, customerFirstName, customerLastName, customerIdentificationNumber, customerContactNumber, customerEmail, numOfRoomRequired);

        } else {
            System.out.println("\nChecking out!!");
            ReservationEntity newReservation = checkOut(currentPartner.getPartnerId(), customerFirstName, customerLastName, customerIdentificationNumber, customerContactNumber, customerEmail, startDate, endDate);
            System.out.println("Reservation " + newReservation.getReservationId() + " made successfully!");
            System.out.println();
        }
    }

    private static void viewReservationDetails() {
        System.out.println("*** Merlion Hotel Holiday Reservation System :: View Partner Reservation Details***\n");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Reservation Id: ");
        Long id = scanner.nextLong();
        try {
            PartnerReservationEntity reservation = retrieveReservationById(id);
            System.out.printf("%20s%20s%15s%15s%15s\n", "Reservation Id", "Reservation Date", "Start Date", "End Date", "Total Amount");
            System.out.printf("%20s%20s%15s%15s%15s\n", reservation.getReservationId(), reservation.getBookingDate(), reservation.getStartDate(), reservation.getEndDate(), reservation.getTotalAmount());

            List<ReservationLineItemEntity> items = reservation.getReservationLineItemEntities();
            System.out.println("Reservation Details: ");
            System.out.println("-------------------------");
            System.out.printf("%20s%20s%15s\n", "Room Type", "Number of Rooms", "Total Amount");
            for (ReservationLineItemEntity item : items) {
                System.out.printf("%20s%20s%15s\n", item.getRoomType(), item.getNumOfRoomBooked(), item.getTotalAmount());
            }

            System.out.println();
            System.out.print("Press any key to continue...: ");
            scanner.nextLine();
        } catch (ReservationNotFoundException_Exception ex) {
            System.out.println("Reservation " + id + " not found!");
        }
    }

    private static void viewAllReservations() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel Holiday Reservation System :: View All Partner Reservations***\n");
        List<PartnerReservationEntity> reservations = retrieveAllReservations(currentPartner.getPartnerId());
        if (reservations.size() > 0) {
            System.out.println();
            System.out.printf("%20s%20s%15s%15s%15s\n", "Reservation Id", "Reservation Date", "Start Date", "End Date", "Total Amount");
            for (PartnerReservationEntity reservation : reservations) {
                System.out.printf("%20s%20s%15s%15s%15s\n", reservation.getReservationId(), reservation.getBookingDate(), reservation.getStartDate(), reservation.getEndDate(), reservation.getTotalAmount());
            }
            System.out.println();
            System.out.print("Press any key to continue...: ");
            scanner.nextLine();
        } else {
            System.out.println("No reservations available.");
        }
    }

    private static PartnerEntity partnerLogin(java.lang.String arg0, java.lang.String arg1) throws InvalidLoginCredentialException_Exception {
        ws.client.partner.HolidayReservationSystemWebService service = new ws.client.partner.HolidayReservationSystemWebService();
        ws.client.partner.PartnerWebService port = service.getPartnerWebServicePort();
        return port.partnerLogin(arg0, arg1);
    }

    private static PartnerReservationEntity retrieveReservationById(java.lang.Long arg0) throws ReservationNotFoundException_Exception {
        ws.client.partner.HolidayReservationSystemWebService service = new ws.client.partner.HolidayReservationSystemWebService();
        ws.client.partner.PartnerWebService port = service.getPartnerWebServicePort();
        return port.retrieveReservationById(arg0);
    }

    private static java.util.List<ws.client.partner.PartnerReservationEntity> retrieveAllReservations(java.lang.Long arg0) {
        ws.client.partner.HolidayReservationSystemWebService service = new ws.client.partner.HolidayReservationSystemWebService();
        ws.client.partner.PartnerWebService port = service.getPartnerWebServicePort();
        return port.retrieveAllReservations(arg0);
    }

    private static RoomTypeEntity retrieveRoomTypeByName(java.lang.String arg0) throws RoomTypeNotFoundException_Exception {
        ws.client.partner.HolidayReservationSystemWebService service = new ws.client.partner.HolidayReservationSystemWebService();
        ws.client.partner.PartnerWebService port = service.getPartnerWebServicePort();
        return port.retrieveRoomTypeByName(arg0);
    }

    private static ReservationEntity checkOut(java.lang.Long arg0, java.lang.String arg1, java.lang.String arg2, java.lang.String arg3, java.lang.String arg4, java.lang.String arg5, java.lang.String arg6, java.lang.String arg7) throws Exception_Exception {
        ws.client.partner.HolidayReservationSystemWebService service = new ws.client.partner.HolidayReservationSystemWebService();
        ws.client.partner.PartnerWebService port = service.getPartnerWebServicePort();
        return port.checkOut(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
    }

    private static Boolean reserveRoom(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2, java.lang.Integer arg3) throws Exception_Exception, RoomTypeNotFoundException_Exception {
        ws.client.partner.HolidayReservationSystemWebService service = new ws.client.partner.HolidayReservationSystemWebService();
        ws.client.partner.PartnerWebService port = service.getPartnerWebServicePort();
        return port.reserveRoom(arg0, arg1, arg2, arg3);
    }

    private static java.util.List<ws.client.partner.RoomTypeEntity> searchAvailableRoom(java.lang.String arg0, java.lang.String arg1, java.lang.Integer arg2) {
        ws.client.partner.HolidayReservationSystemWebService service = new ws.client.partner.HolidayReservationSystemWebService();
        ws.client.partner.PartnerWebService port = service.getPartnerWebServicePort();
        return port.searchAvailableRoom(arg0, arg1, arg2);
    }

}
