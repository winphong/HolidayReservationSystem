/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managementclient;

import ejb.session.stateless.EmployeeEntityControllerRemote;
import ejb.session.stateless.ReservationEntityControllerRemote;
import ejb.session.stateless.RoomEntityControllerRemote;
import ejb.session.stateless.RoomRateEntityControllerRemote;
import ejb.session.stateless.RoomTypeEntityControllerRemote;
import entity.EmployeeEntity;
import java.math.BigDecimal;
import java.util.Scanner;
import util.exception.InvalidAccessRightException;

/**
 *
 * @author Asus
 */
public class HotelOperationModule {
    private EmployeeEntityControllerRemote employeeEntityControllerRemote;
    private RoomTypeEntityControllerRemote roomTypeEntityControllerRemote;
    private RoomEntityControllerRemote roomEntityControllerRemote;
    private RoomRateEntityControllerRemote roomRateEntityControllerRemote;
    private ReservationEntityControllerRemote reservationEntityControllerRemote;
    private EmployeeEntity currentEmployee;

    public HotelOperationModule() {
    }

    public HotelOperationModule(EmployeeEntityControllerRemote employeeEntityControllerRemote, RoomTypeEntityControllerRemote roomTypeEntityControllerRemote, RoomEntityControllerRemote roomEntityControllerRemote, RoomRateEntityControllerRemote roomRateEntityControllerRemote, ReservationEntityControllerRemote reservationEntityControllerRemote, EmployeeEntity currentEmployee) {
        this();
        this.employeeEntityControllerRemote = employeeEntityControllerRemote;
        this.roomTypeEntityControllerRemote = roomTypeEntityControllerRemote;
        this.roomEntityControllerRemote = roomEntityControllerRemote;
        this.roomRateEntityControllerRemote = roomRateEntityControllerRemote;
        this.reservationEntityControllerRemote = reservationEntityControllerRemote;
        this.currentEmployee = currentEmployee;
    }
    
    public void menuHotelOperation(){
        
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Merlion Hotel HoRS :: Hotel Operation ***\n");
            System.out.println("-----------------------------------------");
            System.out.println("1: Create New Room Type");
            System.out.println("2: View Room Type Details");
            System.out.println("3: View All Room Types");
            System.out.println("-----------------------------------------");
            System.out.println("4: Create New Room");
            System.out.println("5: View Room Details");
            System.out.println("6: View All Rooms");
            System.out.println("-----------------------------------------");
            System.out.println("7: View Room Allocation Exception Report");
            System.out.println("-----------------------------------------");
            System.out.println("8: Back\n");
            response = 0;
            
            while (response < 1 || response > 8) {
                System.out.print("Enter option: ");

                response = scanner.nextInt();

                if (response == 1) {
                    doCreateNewRoomType();
                } else if (response == 2) {
                    doViewRoomTypeDetails();
                } else if (response == 3) {
                    doViewAllRoomTypes();
                } else if (response == 4) {
                    doCreateNewRoom();
                } else if (response == 5) {
                    doViewRoomDetails();
                } else if (response == 6) {
                    doViewAllRooms();
                } else if (response == 7) {
                    doViewRoomAllocationExceptionReport();
                } else if (response == 8) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            if (response == 8) {
                break;
            }
        }
    }
    
    public void menuHotelOperationSales() throws InvalidAccessRightException{
        
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Merlion Hotel HoRS :: Hotel Operation ***\n");
            System.out.println("---------------------------");
            System.out.println("1: Create New Room Rate");
            System.out.println("2: View Room Rate Details");
            System.out.println("3: View All Room Rates");
            System.out.println("---------------------------");
            System.out.println("4: Back\n");
            
            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("Enter option: ");

                response = scanner.nextInt();

                if (response == 1) {
                    doCreateNewRoomRate();
                } else if (response == 2) {
                    doViewRoomRateDetails();
                } else if (response == 3) {
                    doViewAllRoomRates();
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
    
    public void doCreateNewRoomType(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: Create New Room Type ***\n");
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Size: ");
        BigDecimal size = scanner.nextBigDecimal();
        System.out.print("Enter Bed: ");
        String bed = scanner.nextLine();
        System.out.print("Enter capacity: ");
        Integer capacity = scanner.nextInt();
        System.out.print("Enter amenities: ");
        String amenities = scanner.nextLine();
        System.out.print("Enter Tier: ");
        Integer tier = scanner.nextInt();
    }
    
    public void doViewRoomTypeDetails(){
        
    }
    
    public void doViewAllRoomTypes(){
        
    }
    
    public void doCreateNewRoom(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS :: System Administration :: Create New Employee ***\n");
    }
    
    public void doViewRoomDetails(){
        
    }
    
    public void doViewAllRooms(){
        
    }
    
    public void doViewRoomAllocationExceptionReport(){
        
    }
    
    public void doCreateNewRoomRate(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS :: System Administration :: Create New Employee ***\n");
    }
    
    public void doViewRoomRateDetails(){
        
    }
    
    public void doViewAllRoomRates(){
        
    }
}
