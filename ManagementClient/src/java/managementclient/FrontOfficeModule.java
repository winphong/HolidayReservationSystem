/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managementclient;

import ejb.session.stateless.GuestEntityControllerRemote;
import ejb.session.stateless.RoomEntityControllerRemote;
import entity.EmployeeEntity;
import util.enumeration.EmployeeAccessRight;
import util.exception.InvalidAccessRightException;
import ejb.session.stateful.WalkinReservationEntityControllerRemote;
import java.util.Scanner;

/**
 *
 * @author Asus
 */
public class FrontOfficeModule {
    
    private WalkinReservationEntityControllerRemote reservationEntityControllerRemote;
    private GuestEntityControllerRemote guestEntityControllerRemote;
    private RoomEntityControllerRemote roomEntityControllerRemote;
    private EmployeeEntity currentEmployee;

    public FrontOfficeModule() {
    }
    
    public void menuFrontOffice() throws InvalidAccessRightException{
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
    
    public void searchRoom(){
        System.out.println("*** Merlion Hotel HoRS :: Front Office :: Walk-in Search Room ***\n");
        
    }
    
    public void guestCheckin(){
        
    }
    
    public void guestCheckout(){
        
    }
}
