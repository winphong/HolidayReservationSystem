/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managementclient;

import ejb.session.stateful.OnlineReservationEntityControllerRemote;
import ejb.session.stateless.EmployeeEntityControllerRemote;
import ejb.session.stateless.GuestEntityControllerRemote;
import ejb.session.stateless.PartnerEntityControllerRemote;
import ejb.session.stateless.RoomEntityControllerRemote;
import ejb.session.stateless.RoomRateEntityControllerRemote;
import ejb.session.stateless.RoomTypeEntityControllerRemote;
import entity.EmployeeEntity;
import java.util.Scanner;
import util.enumeration.EmployeeAccessRight;
import util.exception.InvalidAccessRightException;
import util.exception.InvalidLoginCredentialException;
import ejb.session.stateful.WalkinReservationEntityControllerRemote;
import ejb.session.stateless.InventoryControllerRemote;
import ejb.session.stateless.ReservationEntityControllerRemote;
import util.exception.EmployeeNotFoundException;
import util.exception.RoomTypeNotFoundException;

/**
 *
 * @author Asus
 */
public class MainApp {

    private static GuestEntityControllerRemote guestEntityControllerRemote;
    private static ReservationEntityControllerRemote reservationEntityControllerRemote;
    private static WalkinReservationEntityControllerRemote walkinReservationEntityControllerRemote;
    private static OnlineReservationEntityControllerRemote onlineReservationEntityControllerRemote;
    private static RoomRateEntityControllerRemote roomRateEntityControllerRemote;
    private static RoomEntityControllerRemote roomEntityControllerRemote;
    private static RoomTypeEntityControllerRemote roomTypeEntityControllerRemote;
    private static PartnerEntityControllerRemote partnerEntityControllerRemote;
    private static EmployeeEntityControllerRemote employeeEntityControllerRemote;
    private static InventoryControllerRemote inventoryControllerRemote;

    private SystemAdministrationModule systemAdministrationModule;
    private HotelOperationModule hotelOperationModule;
    private FrontOfficeModule frontOfficeModule;

    private EmployeeEntity currentEmployee;

    public MainApp() {
    }

    public MainApp(GuestEntityControllerRemote guestEntityControllerRemote, ReservationEntityControllerRemote reservationEntityControllerRemote, RoomRateEntityControllerRemote roomRateEntityControllerRemote, RoomEntityControllerRemote roomEntityControllerRemote, RoomTypeEntityControllerRemote roomTypeEntityControllerRemote, PartnerEntityControllerRemote partnerEntityControllerRemote, EmployeeEntityControllerRemote employeeEntityControllerRemote, InventoryControllerRemote inventoryControllerRemote, WalkinReservationEntityControllerRemote walkinReservationEntityControllerRemote, OnlineReservationEntityControllerRemote onlineReservationEntityControllerRemote) {
        this.guestEntityControllerRemote = guestEntityControllerRemote;
        this.reservationEntityControllerRemote = reservationEntityControllerRemote;
        this.roomRateEntityControllerRemote = roomRateEntityControllerRemote;
        this.roomEntityControllerRemote = roomEntityControllerRemote;
        this.roomTypeEntityControllerRemote = roomTypeEntityControllerRemote;
        this.partnerEntityControllerRemote = partnerEntityControllerRemote;
        this.employeeEntityControllerRemote = employeeEntityControllerRemote;
        this.inventoryControllerRemote = inventoryControllerRemote;
        this.walkinReservationEntityControllerRemote = walkinReservationEntityControllerRemote;
        this.onlineReservationEntityControllerRemote = onlineReservationEntityControllerRemote;
    }

    MainApp(GuestEntityControllerRemote guestEntityControllerRemote, WalkinReservationEntityControllerRemote reservationEntityControllerRemote, RoomRateEntityControllerRemote roomRateEntityControllerRemote, RoomEntityControllerRemote roomEntityControllerRemote, RoomTypeEntityControllerRemote roomTypeEntityControllerRemote, PartnerEntityControllerRemote partnerEntityControllerRemote, EmployeeEntityControllerRemote employeeEntityControllerRemote) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void runApp() throws Exception {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to Merlion Hotel HoRS Management Client ***\n");
            System.out.println("1: Login");
            System.out.println("2: Exit\n");
            System.out.println();
            response = 0;

            while (response < 1 || response > 2) {
                System.out.print("Enter option: ");
                response = scanner.nextInt();

                if (response == 1) {
                    try {
                        employeeLogin();
                        System.out.println("Login successful!\n");

                        systemAdministrationModule = new SystemAdministrationModule(employeeEntityControllerRemote, partnerEntityControllerRemote, currentEmployee);
                        hotelOperationModule = new HotelOperationModule(employeeEntityControllerRemote, roomTypeEntityControllerRemote, roomEntityControllerRemote, roomRateEntityControllerRemote, reservationEntityControllerRemote, walkinReservationEntityControllerRemote, currentEmployee);
                        frontOfficeModule = new FrontOfficeModule(reservationEntityControllerRemote, guestEntityControllerRemote, roomEntityControllerRemote, inventoryControllerRemote, roomTypeEntityControllerRemote, walkinReservationEntityControllerRemote, onlineReservationEntityControllerRemote, currentEmployee);
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

    private void employeeLogin() throws InvalidLoginCredentialException {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";

        System.out.println("*** Merlion Hotel HoRS Management Client :: Login ***\n");
        System.out.print("Enter Username: ");
        username = scanner.nextLine().trim();
        System.out.print("Enter Password: ");
        password = scanner.nextLine().trim();

        if (username.length() > 0 && password.length() > 0) {
            try{
                currentEmployee = employeeEntityControllerRemote.employeeLogin(username, password);
            }
            catch (EmployeeNotFoundException ex){
                System.out.println("Employee with username " + username + "does not exist!");
            }
        } else {
            throw new InvalidLoginCredentialException("Missing login credential!");
        }
    }

    private void menuMain() throws RoomTypeNotFoundException, Exception {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Merlion Hotel HoRS Management Client ***\n");
            System.out.println("You are logined as " + currentEmployee.getFirstName() + " " + currentEmployee.getLastName() + " with " + currentEmployee.getAccessRight().toString() + " rights\n");
            System.out.println("1: System Administration");
            System.out.println("2: Hotel Operation");
            System.out.println("3: Front Office");
            System.out.println("4: Logout\n");
            System.out.println();
            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("Enter option: ");

                response = scanner.nextInt();

                if (response == 1) {
                    try {
                        systemAdministrationModule.menuSystemAdministration();
                    } catch (InvalidAccessRightException ex) {
                        System.out.println("Invalid option, please try again!: " + ex.getMessage() + "\n");
                    }
                } else if (response == 2) {
                    try {
                        if (currentEmployee.getAccessRight() != EmployeeAccessRight.OPERATIONMANAGER && currentEmployee.getAccessRight() !=EmployeeAccessRight.SALESMANAGER) {
                            throw new InvalidAccessRightException("You don't have MANAGER rights to access the hotel operation module.");
                        }
                        else if (currentEmployee.getAccessRight() != EmployeeAccessRight.OPERATIONMANAGER){
                            hotelOperationModule.menuHotelOperationSales();
                        }
                        hotelOperationModule.menuHotelOperation();
                    } catch (InvalidAccessRightException ex) {
                        System.out.println("Invalid option, please try again!: " + ex.getMessage() + "\n");
                    }
                } else if (response == 3) {
                    try {
                        frontOfficeModule.menuFrontOffice();
                    } catch (InvalidAccessRightException ex) {
                        System.out.println("Invalid option, please try again!: " + ex.getMessage() + "\n");
                    }
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
}
