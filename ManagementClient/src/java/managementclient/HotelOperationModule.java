/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managementclient;

import ejb.session.stateful.ReservationEntityControllerRemote;
import ejb.session.stateless.EmployeeEntityControllerRemote;
import ejb.session.stateless.RoomEntityControllerRemote;
import ejb.session.stateless.RoomRateEntityControllerRemote;
import ejb.session.stateless.RoomTypeEntityControllerRemote;
import entity.EmployeeEntity;
import entity.RoomEntity;
import entity.RoomTypeEntity;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import util.enumeration.RoomStatus;
import util.exception.InvalidAccessRightException;
import util.exception.RoomNotFoundException;
import util.exception.RoomTypeNotFoundException;

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
    
    public void menuHotelOperation() {
        
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
            System.out.println("5: Update Room");
            System.out.println("6: Delete Room");
            System.out.println("7: View All Rooms");
            System.out.println("-----------------------------------------");
            System.out.println("8: View Room Allocation Exception Report");
            System.out.println("-----------------------------------------");
            System.out.println("9: Back\n");
            response = 0;
            
            while (response < 1 || response > 9) {
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
                    doUpdateRoom();
                } else if (response == 6) {
                    doDeleteRoom();
                } else if (response == 7) {
                    doViewAllRooms();
                } else if (response == 8) {
                    doViewRoomAllocationExceptionReport();
                } else if (response == 9) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            if (response == 9) {
                break;
            }
        }
    }
    
    public void menuHotelOperationSales() throws InvalidAccessRightException {
        
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
            System.out.println();
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
    
    public void doCreateNewRoomType() {
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
        List<RoomTypeEntity> roomTypes = roomTypeEntityControllerRemote.viewAllRoomType();
        for (RoomTypeEntity roomType : roomTypes) {
            if (roomType.getTier() >= tier) {
                roomType.setTier(roomType.getTier() + 1);
            }
        }
        RoomTypeEntity newRoomType = new RoomTypeEntity(name, description, size, bed, capacity, amenities, tier);
        newRoomType = roomTypeEntityControllerRemote.createNewRoomType(newRoomType);
        System.out.println("New Employee with Id " + newRoomType.getRoomTypeId() + " created.");
    }
    
    public void doViewRoomTypeDetails() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: View Room Type Details ***\n");
        System.out.print("Enter Name of Room Type to view details: ");
        String name = scanner.nextLine();
        RoomTypeEntity currentRoomTypeEntity;
        try {
            currentRoomTypeEntity = roomTypeEntityControllerRemote.retrieveRoomTypeByName(name);
            System.out.printf("%15s%20s%20s%10s%10s%10s%20s%%8s%12s\n", "Room Type Id", "Name", "Description", "Size", "Bed", "Capacity", "Amenities", "Tier", "Is Disabled");
            System.out.printf("%15s%20s%20s%10s%10s%10s%20s%%8s%12s\n", currentRoomTypeEntity.getRoomTypeId(), currentRoomTypeEntity.getName(), currentRoomTypeEntity.getDescription(), currentRoomTypeEntity.getSize(), currentRoomTypeEntity.getBed(), currentRoomTypeEntity.getCapacity(), currentRoomTypeEntity.getAmenities(), currentRoomTypeEntity.getTier(), currentRoomTypeEntity.getIsDisabled());
            System.out.println("------------------------");
            System.out.println("1: Update Room Type");
            System.out.println("2: Delete Room Type");
            System.out.println("3: Back\n");
            System.out.println();
            System.out.print("Enter option: ");
            response = scanner.nextInt();
            if (response == 1) {
                doUpdateRoomType(currentRoomTypeEntity);
            } else if (response == 2) {
                doDeleteRoomType(currentRoomTypeEntity);
            }
        } catch (RoomTypeNotFoundException ex) {
            System.out.println("Room type of Id" + name + " does not exist!");
        }
        
    }
    
    public void doUpdateRoomType(RoomTypeEntity roomTypeEntity) {
        Scanner scanner = new Scanner(System.in);
        String input;
        
        System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: Update Room Type ***\n");
        
        System.out.print("Enter Name (blank if no change): ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            roomTypeEntity.setName(input);
        }
        
        System.out.print("Enter Description (blank if no change): ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            roomTypeEntity.setDescription(input);
        }
        
        System.out.print("Enter Size (blank if no change): ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            roomTypeEntity.setSize(new BigDecimal(input));
        }
        
        System.out.print("Enter Bed (blank if no change): ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            roomTypeEntity.setBed(input);
        }
        
        System.out.print("Enter Capacity (blank if no change): ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            roomTypeEntity.setCapacity(new Integer(input));
        }
        
        System.out.print("Enter Amenities (blank if no change): ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            roomTypeEntity.setAmenities(input);
        }
        
        System.out.print("Enter Tier (blank if no change): ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            Integer tier = new Integer(input);
            List<RoomTypeEntity> roomTypes = roomTypeEntityControllerRemote.viewAllRoomType();
            for (RoomTypeEntity roomType : roomTypes) {
                if (roomType.getTier() >= tier) {
                    roomType.setTier(roomType.getTier() + 1);
                }
            }
            roomTypeEntity.setTier(tier);
        }
        
        roomTypeEntityControllerRemote.updateRoomType(roomTypeEntity);
        System.out.println("Room Type updated successfully!\n");
    }

    //need to modify again because of isDiabled and also tier
    public void doDeleteRoomType(RoomTypeEntity roomTypeEntity) {
        System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: Delete Room Type ***\n");
        List<RoomEntity> rooms = roomTypeEntity.getRoom();
        Boolean roomInUse = false;
        for (RoomEntity room : rooms) {
            if (room.getRoomStatus() != RoomStatus.VACANT) {
                roomInUse = true;
                break;
            }
        }
        if (roomInUse == false) {
            String name = roomTypeEntity.getName();
            roomTypeEntityControllerRemote.deleteRoomType(roomTypeEntity);
            System.out.println("Room Type " + name + " successfully deleted.");
        } else {
            System.out.println("Unable to delete room type: Room in use! Room is diabled.");
        }
    }
    
    public void doViewAllRoomTypes() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: View All Room Types ***\n");
        List<RoomTypeEntity> roomTypes = roomTypeEntityControllerRemote.viewAllRoomType();
        if (roomTypes.size() > 0) {
            System.out.println();
            for (RoomTypeEntity roomType : roomTypes) {
                System.out.printf("%15s%20s%20s%10s%10s%10s%20s%%8s%12s\n", "Room Type Id", "Name", "Description", "Size", "Bed", "Capacity", "Amenities", "Tier", "Is Disabled");
                System.out.printf("%15s%20s%20s%10s%10s%10s%20s%%8s%12s\n", roomType.getRoomTypeId(), roomType.getName(), roomType.getDescription(), roomType.getSize(), roomType.getBed(), roomType.getCapacity(), roomType.getAmenities(), roomType.getTier(), roomType.getIsDisabled());
            }
            System.out.print("Press any key to continue...: ");
            scanner.nextLine();
        } else {
            System.out.println("No room types available.");
        }
    }
    
    public void doCreateNewRoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: Create New Room ***\n");
        System.out.println();
        List<RoomTypeEntity> roomType = roomTypeEntityControllerRemote.retrieveAllRoomType();
        System.out.printf("%15s%20s\n", "Room Type Id", "Name");
        for (RoomTypeEntity roomTypeEntity : roomType) {
            System.out.printf("%15s%20s\n", roomTypeEntity.getRoomTypeId(), roomTypeEntity.getName());
        }
        System.out.print("Enter Id of Room Type: ");
        Long id = scanner.nextLong();
        try {
            RoomTypeEntity newRoomType = roomTypeEntityControllerRemote.retrieveRoomTypeById(id);
            System.out.print("Enter room number: ");
            Long roomNumber = scanner.nextLong();
            RoomEntity newRoom = new RoomEntity(roomNumber);
            newRoom = roomEntityControllerRemote.createNewRoom(newRoom, newRoomType);
            System.out.println("Room " + newRoom.getRoomId() + " successfully created!\n");
        } catch (RoomTypeNotFoundException ex) {
            System.out.println("Room type of Id" + id + " does not exist!");
        }
    }
    
    public void doViewRoomDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: View Room Details ***\n");
        System.out.println();
        System.out.print("Enter Room Number to view details: ");
        Long roomNumber = scanner.nextLong();
        RoomEntity room;
        try {
            room = roomEntityControllerRemote.retrieveRoomByRoomNumber(roomNumber);
            
            System.out.println("------------------------");
            System.out.println("1: Update Room");
            System.out.println("2: Delete Room");
            System.out.println("3: Back\n");
            System.out.println();
            System.out.print("Enter option: ");
        } catch (RoomNotFoundException ex) {
            System.out.println("Room " + roomNumber + " does not exist!");
        }
    }
    
    public void doUpdateRoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: Update Room ***\n");
        System.out.println();
        System.out.print("Enter Room Number to view details: ");
        Long roomNumber = scanner.nextLong();
        RoomEntity room;
        try {
            room = roomEntityControllerRemote.retrieveRoomByRoomNumber(roomNumber);
            String input;
            System.out.print("Enter Room Number (blank if no change): ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                room.setRoomNumber(Long.valueOf(input));
            }
            System.out.println("1: Vacant");
            System.out.println("2: Booked");
            System.out.println("3: Occupied");
            System.out.println("4: Maintenance");
            System.out.println("5: Housekeeping");
            System.out.print("Select room status (blank if no change): ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                Integer roomStatusInt = Integer.valueOf(input);
                if (roomStatusInt >= 1 && roomStatusInt <= 5) {
                    room.setRoomStatus(RoomStatus.values()[roomStatusInt - 1]);
                } else {
                    System.out.println("Invalid option! Room status not updated.");
                }
            }
            
            if (room.getRoomStatus() != RoomStatus.OCCUPIED) {
                System.out.print("Enter Guest Id occupying room (blank if no change): ");
                input = scanner.nextLine().trim();
                if (input.length() > 0) {
                    room.setGuest(Long.valueOf(input));
                }
            }

            roomEntityControllerRemote.updateRoom(room);
            System.out.println("Room Type updated successfully!\n");
        } catch (RoomNotFoundException ex) {
            System.out.println("Room " + roomNumber + " does not exist!");
        }
    }

    //to be modified
    public void doDeleteRoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: Delete Room ***\n");
        System.out.println();
        System.out.print("Enter Room Number to view details: ");
        Long roomNumber = scanner.nextLong();
        RoomEntity room;
        try {
            room = roomEntityControllerRemote.retrieveRoomByRoomNumber(roomNumber);
        } catch (RoomNotFoundException ex) {
            System.out.println("Room " + roomNumber + " does not exist!");
        }
    }
    
    public void doViewAllRooms() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: View All Rooms ***\n");
        System.out.println();
        System.out.printf("%10s%15s%15s%15s%20s%15s\n", "Room Id", "Room Number", "Room Type", "Room Status", "Guest", "Is Disabled");
        List<RoomEntity> rooms = roomEntityControllerRemote.viewAllRoom();
        for (RoomEntity room : rooms) {
            System.out.printf("%10s%15s%15s%15s%20s%15s\n", room.getRoomId(), room.getRoomNumber(), room.getRoomType(), room.getRoomStatus(), room.getGuest(), room.getIsDisabled());
        }
        System.out.print("Press any key to continue...: ");
        scanner.nextLine();
    }

    //to be modified
    public void doViewRoomAllocationExceptionReport() {
        
    }
    
    public void doCreateNewRoomRate() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: Create New Room Rate ***\n");
    }
    
    public void doViewRoomRateDetails() {
        
    }
    
    public void doViewAllRoomRates() {
        
    }
}
