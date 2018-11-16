/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package holidayreservationsystemseclient;

import java.util.Scanner;
import ws.client.partner.InvalidLoginCredentialException_Exception;
import ws.client.partner.PartnerEntity;

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
            System.out.println("*** Welcome to Merlion Hotel HoRS Reservation Client ***\n");
            System.out.println("1: Login");
            System.out.println("2: Exit\n");
            System.out.println();
            response = 0;

            while (response <= 1 || response > 3) {
                System.out.print("Enter option: ");
                response = scanner.nextInt();

                if (response == 1) {
                    try {
                        partnerLoginMenu();
                    } catch (InvalidLoginCredentialException_Exception ex) {
                        System.out.println("Error logging in: " + ex.getMessage());
                    }
                        System.out.println("Login successful!\n");
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

        System.out.println("*** Merlion Hotel HoRS Reservation Client :: Login ***\n");
        System.out.print("Enter Username: ");
        username = scanner.nextLine().trim();
        System.out.print("Enter Password: ");
        password = scanner.nextLine().trim();

        if (username.length() > 0 && password.length() > 0) {
            currentPartner = partnerLogin(username, password);
            System.out.println("Welcome, " + currentPartner.getCompanyName() + "!");
        } else {
            throw new InvalidLoginCredentialException_Exception("Missing login credential!");
        }
    }

    private static PartnerEntity partnerLogin(java.lang.String arg0, java.lang.String arg1) throws InvalidLoginCredentialException_Exception {
        ws.client.partner.HolidayReservationSystemWebService service = new ws.client.partner.HolidayReservationSystemWebService();
        ws.client.partner.PartnerWebService port = service.getPartnerWebServicePort();
        return port.partnerLogin(arg0, arg1);
    }
    
}
