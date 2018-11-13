/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managementclient;

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
import ejb.session.stateful.WalkinReservationEntityControllerRemote;
import entity.RoomRateEntity;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import util.exception.RoomRateNotFoundException;

/**
 *
 * @author Asus
 */
public class HotelOperationModule {

    private EmployeeEntityControllerRemote employeeEntityControllerRemote;
    private RoomTypeEntityControllerRemote roomTypeEntityControllerRemote;
    private RoomEntityControllerRemote roomEntityControllerRemote;
    private RoomRateEntityControllerRemote roomRateEntityControllerRemote;
    private WalkinReservationEntityControllerRemote reservationEntityControllerRemote;
    private EmployeeEntity currentEmployee;

    public HotelOperationModule() {
    }

    public HotelOperationModule(EmployeeEntityControllerRemote employeeEntityControllerRemote, RoomTypeEntityControllerRemote roomTypeEntityControllerRemote, RoomEntityControllerRemote roomEntityControllerRemote, RoomRateEntityControllerRemote roomRateEntityControllerRemote, WalkinReservationEntityControllerRemote reservationEntityControllerRemote, EmployeeEntity currentEmployee) {
        this();
        this.employeeEntityControllerRemote = employeeEntityControllerRemote;
        this.roomTypeEntityControllerRemote = roomTypeEntityControllerRemote;
        this.roomEntityControllerRemote = roomEntityControllerRemote;
        this.roomRateEntityControllerRemote = roomRateEntityControllerRemote;
        this.reservationEntityControllerRemote = reservationEntityControllerRemote;
        this.currentEmployee = currentEmployee;
    }
    
    // For operation manager
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

    // For sales manager
    public void menuHotelOperationSales() throws InvalidAccessRightException, RoomTypeNotFoundException {

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
        //List<RoomTypeEntity> roomTypes = roomTypeEntityControllerRemote.viewAllRoomType();
        for (RoomTypeEntity roomType : roomTypeEntityControllerRemote.viewAllRoomType()) {
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
        //List<RoomEntity> rooms = roomTypeEntity.getRoom();
        Boolean roomInUse = false;
        for (RoomEntity room : roomTypeEntity.getRoom()) {
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
            System.out.println("Unable to delete room type: Room in use! Room is disabled.");
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
            System.out.println();
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
        //List<RoomTypeEntity> roomTypes = roomTypeEntityControllerRemote.retrieveAllRoomType();
        System.out.printf("%15s%20s\n", "Room Type Id", "Name");
        for (RoomTypeEntity roomType : roomTypeEntityControllerRemote.retrieveAllRoomType()) {
            System.out.printf("%15s%20s\n", roomType.getRoomTypeId(), roomType.getName());
        }
        System.out.print("Enter id of Room Type: ");
        Long id = scanner.nextLong();
        try {
            RoomTypeEntity roomType = roomTypeEntityControllerRemote.retrieveRoomTypeById(id);
            System.out.print("Enter room floor: ");
            String floor = scanner.nextLine().trim();
            System.out.print("Enter room number: ");
            String number = scanner.nextLine().trim();
            // RoomNumber roomNumber = new RoomNumber(floor, number);
            RoomEntity newRoom = new RoomEntity(floor.concat(number));
            newRoom = roomEntityControllerRemote.createNewRoom(newRoom, roomType);
            System.out.println("Room " + newRoom.getRoomId() + " successfully created!\n");
        } catch (RoomTypeNotFoundException ex) {
            System.out.println("Room type of Id" + id + " does not exist!");
        }
    }

    public void doUpdateRoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: Update Room ***\n");
        System.out.println();
        System.out.print("Enter Room Number to update: ");
        String roomNumber = scanner.nextLine().trim();
        RoomEntity room;
        try {
            room = roomEntityControllerRemote.retrieveRoomByRoomNumber(roomNumber);
            String input;
            System.out.print("Enter Room Number (blank if no change): ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                room.setRoomNumber(input);
            }

            System.out.println("1: Vacant");
            System.out.println("2: Allocated");
            System.out.println("3: Occupied");
            System.out.println("4: Maintenance");
// Remove housekeeping? Use room.isReady attribute to indicate
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
            
            System.out.print("Enter name of guest occupying the room (blank if no change): ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                room.setGuest(input);
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
        System.out.print("Enter Room Number to delete: ");
        String roomNumber = scanner.nextLine().trim();
        RoomEntity room;
        try {
            room = roomEntityControllerRemote.retrieveRoomByRoomNumber(roomNumber);
            if (room.getRoomStatus().equals(RoomStatus.VACANT) ) {
                roomEntityControllerRemote.deleteRoom(room);
            } else {
                roomEntityControllerRemote.disableRoom(room);
            }
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
        if (rooms.size() > 0) {
            for (RoomEntity room : rooms) {
                System.out.printf("%10s%15s%15s%15s%20s%15s\n", room.getRoomId(), room.getRoomNumber(), room.getRoomType(), room.getRoomStatus(), room.getGuest(), room.getIsDisabled());
            }
            System.out.println();
            System.out.print("Press any key to continue...: ");
            scanner.nextLine();
        } else {
            System.out.println("No rooms available.");
        }
    }

    //to be modified
    public void doViewRoomAllocationExceptionReport() {

    }

    public void doCreateNewRoomRate() throws RoomTypeNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: Create New Room Rate ***\n");
        System.out.println();
        System.out.print("Enter Name of Room Rate: ");
        String name = scanner.nextLine();
        doViewAllRoomTypes();
        System.out.print("Enter Id of Room Type: ");
        Long roomTypeId = scanner.nextLong();
        
        RoomTypeEntity roomType = roomTypeEntityControllerRemote.retrieveRoomTypeById(roomTypeId);
        
        System.out.print("Enter Rate per Night: ");
        BigDecimal rate = scanner.nextBigDecimal();
        scanner.nextLine();
        System.out.print("Enter Valid Date From (blank if not applicable): ");
        String date1 = scanner.nextLine().trim();
        if (date1.length() > 0) {
            Date validFrom = Date.valueOf(date1);
            System.out.print("Enter Valid Date Till (blank if not applicable): ");
            String date2 = scanner.nextLine().trim();
            if (date2.length() > 0) {
                Date validTill = Date.valueOf(date2);
                RoomRateEntity newRate = new RoomRateEntity(name, rate, validFrom, validTill);
                roomRateEntityControllerRemote.createNewRoomRate(newRate, roomType);
                System.out.println("Room rate " + newRate.getRoomRateId() + " successfully created!\n");
            }
        } else {
            RoomRateEntity newRate = new RoomRateEntity(name, rate);
            roomRateEntityControllerRemote.createNewRoomRate(newRate, roomType);
            System.out.println("Room rate " + newRate.getRoomRateId() + " successfully created!\n");
        }

    }

    public void doViewRoomRateDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: View Room Rates ***\n");
        System.out.println();
        System.out.print("Enter name of Room Rate to view details: ");
        String name = scanner.nextLine().trim();
        try {
            RoomRateEntity roomRate = roomRateEntityControllerRemote.retrieveRoomRateByName(name);
            Boolean validDate = roomRate.getValidFrom() != null;
            if (validDate == true) {
                System.out.printf("%15s%20s%20s%15s%15s%15s\n", "Room Rate Id", "Room Rate Name", "Rate per Night", "Valid From", "Valid Till", "Is Disabled");
                System.out.printf("%15s%20s%20s%15s%15s%15s\n", roomRate.getRoomRateId(), roomRate.getName(), roomRate.getRatePerNight(), roomRate.getValidFrom(), roomRate.getValidTill(), roomRate.getIsDisabled());
            } else {
                System.out.printf("%15s%20s%20s%15s\n", "Room Rate Id", "Room Rate Name", "Rate per Night", "Is Disabled");
                System.out.printf("%15s%20s%20s%15s\n", roomRate.getRoomRateId(), roomRate.getName(), roomRate.getRatePerNight(), roomRate.getIsDisabled());
            }
            System.out.println("------------------------");
            System.out.println("1: Update Room Rate");
            System.out.println("2: Delete Room Rate");
            System.out.println("3: Back\n");
            System.out.println();
            System.out.print("Enter option: ");
            int response = scanner.nextInt();
            if (response == 1) {
                doUpdateRoomRate(roomRate);
            } else if (response == 2) {
                doDeleteRoomRate(roomRate);
            }
        } catch (RoomRateNotFoundException ex) {
            System.out.println(name + " does not exist!");
        }
    }

    public void doUpdateRoomRate(RoomRateEntity roomRate) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: Update Room Rate ***\n");
        System.out.println();
        System.out.print("Enter Name (blank if no change): ");
        String input = scanner.nextLine();
        if (input.length() > 0) {
            roomRate.setName(input);
        }
        System.out.print("Enter Rate per Night (blank if no change): ");
        input = scanner.nextLine();
        if (input.length() > 0) {
            roomRate.setRatePerNight(new BigDecimal(input));
        }
        System.out.print("Enter Valid Date From (dd/MM/yyyy, blank if no change): ");
        input = scanner.nextLine();
        if (input.length() > 0) {
            try {
                roomRate.setValidFrom(new SimpleDateFormat("dd/MM/yyyy").parse(input));
            } catch (ParseException ex) {
                System.out.println("Invalid date!");
            }
        }

        System.out.print("Enter Valid Date Till (dd/MM/yyyy, blank if no change): ");
        input = scanner.nextLine();
        if (input.length() > 0) {
            try {
                roomRate.setValidTill(new SimpleDateFormat("dd/MM/yyyy").parse(input));
            } catch (ParseException ex) {
                System.out.println("Invalid date!");
            }
        }

        roomRateEntityControllerRemote.updateRoomRate(roomRate);
        System.out.println("Room Rate updated successfully!\n");
    }

    public void doDeleteRoomRate(RoomRateEntity roomRate) {
        System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: Delete Room Rate ***\n");
        RoomTypeEntity roomType = roomRate.getRoomType();
        if (roomType.getIsDisabled().equals(Boolean.FALSE)) {
            //List<RoomEntity> rooms = roomType.getRoom();
            Boolean roomInUse = false;
            for (RoomEntity room : roomType.getRoom()) {
                // Or maybe allocated
                if (room.getRoomStatus().equals(RoomStatus.OCCUPIED) || room.getRoomStatus().equals(RoomStatus.ALLOCATED)) {
                    roomInUse = true;
                    break;
                }
            }
            if (roomInUse.equals(Boolean.FALSE)) {
                roomRateEntityControllerRemote.deleteRoomRate(roomRate);
                System.out.println("Room Rate deleted successfully.");
            }
            else {
                roomRateEntityControllerRemote.disableRoomRate(roomRate);
                System.out.println("Unable to delete room rate: room type in use! Room Rate is disabled.");
            }
        }
        else{
            roomRateEntityControllerRemote.deleteRoomRate(roomRate);
            System.out.println("Room Rate deleted successfully.");
        }
    }
    

    public void doViewAllRoomRates() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: View All Room Rates ***\n");
        System.out.println();
        System.out.printf("%15s%20s%20s%15s%15s%15s\n", "Room Rate Id", "Room Rate Name", "Rate per Night", "Valid From", "Valid Till", "Is Disabled");
        List<RoomRateEntity> roomRates = roomRateEntityControllerRemote.viewAllRoomRate();
        if (roomRates.size() > 0) {
            for (RoomRateEntity roomRate : roomRates) {
                System.out.printf("%15s%20s%20s%15s%15s%15s\n", roomRate.getRoomRateId(), roomRate.getName(), roomRate.getRatePerNight(), roomRate.getValidFrom(), roomRate.getValidTill(), roomRate.getIsDisabled());
            }
            System.out.println();
            System.out.print("Press any key to continue...: ");
            scanner.nextLine();
        } else {
            System.out.println("No room rates available.");
        }
    }
}
