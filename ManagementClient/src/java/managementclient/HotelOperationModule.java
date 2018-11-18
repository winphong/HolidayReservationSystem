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
import ejb.session.stateless.ReservationEntityControllerRemote;
import entity.ReservationLineItemEntity;
import entity.RoomRateEntity;
import java.sql.Date;
import java.time.LocalDate;
import util.exception.CreateNewRoomException;
import util.exception.CreateNewRoomRateException;
import util.exception.ReservationLineItemNotFoundException;
import util.exception.RoomRateNotFoundException;
import util.exception.UpdateInventoryException;

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
    private WalkinReservationEntityControllerRemote walkinReservationEntityControllerRemote;
    private EmployeeEntity currentEmployee;

    public HotelOperationModule() {
    }

    public HotelOperationModule(EmployeeEntityControllerRemote employeeEntityControllerRemote, RoomTypeEntityControllerRemote roomTypeEntityControllerRemote, RoomEntityControllerRemote roomEntityControllerRemote, RoomRateEntityControllerRemote roomRateEntityControllerRemote, ReservationEntityControllerRemote reservationEntityControllerRemote, WalkinReservationEntityControllerRemote walkinReservationEntityControllerRemote, EmployeeEntity currentEmployee) {
        this();
        this.employeeEntityControllerRemote = employeeEntityControllerRemote;
        this.roomTypeEntityControllerRemote = roomTypeEntityControllerRemote;
        this.roomEntityControllerRemote = roomEntityControllerRemote;
        this.roomRateEntityControllerRemote = roomRateEntityControllerRemote;
        this.reservationEntityControllerRemote = reservationEntityControllerRemote;
        this.walkinReservationEntityControllerRemote = walkinReservationEntityControllerRemote;
        this.currentEmployee = currentEmployee;
    }

    // For operation manager
    public void menuHotelOperation() throws ReservationLineItemNotFoundException {

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
                    System.out.println();
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
        scanner.nextLine();
        System.out.print("Enter Bed: ");
        String bed = scanner.nextLine();
        System.out.print("Enter capacity: ");
        Integer capacity = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter amenities: ");
        String amenities = scanner.nextLine();
        System.out.print("Enter Tier: ");
        Integer tier = scanner.nextInt();
        RoomTypeEntity newRoomType = new RoomTypeEntity(name, description, size, bed, capacity, amenities, tier);
        roomTypeEntityControllerRemote.updateTier(tier);
        try {
            newRoomType = roomTypeEntityControllerRemote.createNewRoomType(newRoomType);
        } catch (UpdateInventoryException ex) {
            System.out.println("Error updating Inventory!");
        }
        System.out.println("New Room Type with Id " + newRoomType.getRoomTypeId() + " created.");
        System.out.println();
    }

    public void doViewRoomTypeDetails() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: View Room Type Details ***\n");
        System.out.print("Enter Name of Room Type to view details: ");
        String name = scanner.nextLine();
        System.out.println();
        RoomTypeEntity currentRoomTypeEntity;
        try {
            currentRoomTypeEntity = roomTypeEntityControllerRemote.retrieveRoomTypeByName(name);
            System.out.printf("%15s%20s%20s%10s%10s%10s%40s%8s%15s\n", "Room Type Id", "Name", "Description", "Size", "Bed", "Capacity", "Amenities", "Tier", "Is Disabled");
            System.out.printf("%15s%20s%20s%10s%10s%10s%40s%8s%15s\n", currentRoomTypeEntity.getRoomTypeId(), currentRoomTypeEntity.getName(), currentRoomTypeEntity.getDescription(), currentRoomTypeEntity.getRoomSize(), currentRoomTypeEntity.getBed(), currentRoomTypeEntity.getCapacity(), currentRoomTypeEntity.getAmenities(), currentRoomTypeEntity.getTier(), currentRoomTypeEntity.getIsDisabled());
            System.out.println("------------------------");
            System.out.println("1: Update Room Type");
            System.out.println("2: Delete Room Type");
            System.out.println("3: Back\n");
            System.out.println();
            System.out.print("Enter option: ");
            response = scanner.nextInt();
            System.out.println();
            if (response == 1) {
                doUpdateRoomType(currentRoomTypeEntity.getRoomTypeId());
            } else if (response == 2) {
                doDeleteRoomType(currentRoomTypeEntity.getRoomTypeId());
            }
        } catch (RoomTypeNotFoundException ex) {
            System.out.println("Room type of Id" + name + " does not exist!");
            System.out.println();
        }

    }

    public void doUpdateRoomType(Long id) {
        Scanner scanner = new Scanner(System.in);
        String input;
        try {
            RoomTypeEntity roomTypeEntity = roomTypeEntityControllerRemote.retrieveRoomTypeById(id);
            RoomTypeEntity updateRoomType = new RoomTypeEntity();
            System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: Update Room Type ***\n");

            System.out.print("Enter Name (blank if no change): ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                updateRoomType.setName(input);
            }

            System.out.print("Enter Description (blank if no change): ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                updateRoomType.setDescription(input);
            }

            System.out.print("Enter Size (blank if no change): ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                updateRoomType.setRoomSize(new BigDecimal(input));
            }

            System.out.print("Enter Bed (blank if no change): ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                updateRoomType.setBed(input);
            }

            System.out.print("Enter Capacity (blank if no change): ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                updateRoomType.setCapacity(new Integer(input));
            }

            System.out.print("Enter Amenities (blank if no change): ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                updateRoomType.setAmenities(input);
            }

            System.out.print("Enter Tier (blank if no change): ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                Integer tier = new Integer(input);
                roomTypeEntityControllerRemote.updateTier(tier);
            }
            roomTypeEntityControllerRemote.updateRoomType(updateRoomType, roomTypeEntity.getRoomTypeId());
            System.out.println("Room Type updated successfully!\n");
            System.out.println();
        } catch (RoomTypeNotFoundException ex) {
            System.out.println("Error in updating room type: " + ex.getMessage());
            System.out.println();
        }
    }

    //need to modify again because of isDiabled and also tier
    public void doDeleteRoomType(Long id) {
        System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: Delete Room Type ***\n");
        try {
            RoomTypeEntity roomTypeEntity = roomTypeEntityControllerRemote.retrieveRoomTypeById(id);
            List<RoomEntity> rooms = roomTypeEntityControllerRemote.retrieveRooms(id);
            Boolean roomInUse = false;
            for (RoomEntity room : rooms) {
                if (room.getRoomStatus() != RoomStatus.VACANT) {
                    roomInUse = true;
                    break;
                }
            }
            if (roomInUse == false) {
                String name = roomTypeEntity.getName();
                roomTypeEntityControllerRemote.deleteRoomType(roomTypeEntity.getRoomTypeId());
                System.out.println("Room Type " + name + " successfully deleted.");
                System.out.println();
            } else {
                roomTypeEntityControllerRemote.disableRoomType(roomTypeEntity.getRoomTypeId());
                System.out.println("Unable to delete room type: Room in use! Room is disabled.");
                System.out.println();
            }
        } catch (RoomTypeNotFoundException ex) {
            System.out.println("Error in updating room type: " + ex.getMessage());
            System.out.println();
        }
    }

    public void doViewAllRoomTypes() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: View All Room Types ***\n");
        List<RoomTypeEntity> roomTypes = roomTypeEntityControllerRemote.viewAllRoomType();
        if (roomTypes.size() > 0) {
            System.out.println();
            System.out.printf("%15s%20s%20s%10s%10s%10s%40s%8s%15s\n", "Room Type Id", "Name", "Description", "Size", "Bed", "Capacity", "Amenities", "Tier", "Is Disabled");
            for (RoomTypeEntity roomType : roomTypes) {
                System.out.printf("%15s%20s%20s%10s%10s%10s%40s%8s%15s\n", roomType.getRoomTypeId(), roomType.getName(), roomType.getDescription(), roomType.getRoomSize(), roomType.getBed(), roomType.getCapacity(), roomType.getAmenities(), roomType.getTier(), roomType.getIsDisabled());
            }
            System.out.println();
            System.out.print("Press any key to continue...: ");
            scanner.nextLine();
            System.out.println();
        } else {
            System.out.println("No room types available.");
            System.out.println();
        }
    }

    public void doCreateNewRoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: Create New Room ***\n");
        System.out.println();
        List<RoomTypeEntity> roomTypes = roomTypeEntityControllerRemote.retrieveAllRoomType();
        System.out.printf("%15s%20s\n", "Room Type Id", "Name");
        for (RoomTypeEntity roomType : roomTypes) {
            System.out.printf("%15s%20s\n", roomType.getRoomTypeId(), roomType.getName());
        }
        System.out.print("Enter id of Room Type: ");
        Long id = scanner.nextLong();
        try {
            RoomTypeEntity roomType = roomTypeEntityControllerRemote.retrieveRoomTypeById(id);
            scanner.nextLine();
            System.out.print("Enter room number: ");
            String number = scanner.nextLine().trim();
            RoomEntity newRoom = new RoomEntity(number);
            try {
                newRoom = roomEntityControllerRemote.createNewRoom(newRoom, roomType.getRoomTypeId());
                System.out.println("Room " + newRoom.getRoomId() + " successfully created!\n");
                System.out.println();
            } catch (CreateNewRoomException ex) {
                System.out.println("Error: New Room not created!" + ex.getMessage());
                System.out.println();
            }
        } catch (RoomTypeNotFoundException ex) {
            System.out.println("Room type of Id" + id + " does not exist!");
            System.out.println();
        }
    }

    public void doUpdateRoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: Update Room ***\n");
        System.out.println();
        System.out.print("Enter Room Number to update: ");
        String roomNumber = scanner.nextLine().trim();
        RoomEntity room = new RoomEntity();

        String input;
        System.out.print("Enter Room Number (blank if no change): ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            room.setRoomNumber(input);
        }

        System.out.println("1: Vacant");
        System.out.println("2: Occupied");
        System.out.println("3: Maintenance");
        System.out.print("Select room status (blank if no change): ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            Integer roomStatusInt = Integer.valueOf(input);
            if (roomStatusInt >= 1 && roomStatusInt <= 3) {
                room.setRoomStatus(RoomStatus.values()[roomStatusInt - 1]);
                if (roomStatusInt == 2) {
                    System.out.print("Enter name of guest occupying the room: ");
                    String name = scanner.nextLine();
                    room.setGuest(name);
                }
            } else {
                System.out.println("Invalid option! Room status not updated.");
                System.out.println();
                System.out.print("Enter name of guest occupying the room (blank if no change): ");
                input = scanner.nextLine().trim();
                if (input.length() > 0) {
                    room.setGuest(input);
                }
            }
        }

        roomEntityControllerRemote.updateRoom(room, roomNumber);
        System.out.println("Room Type updated successfully!\n");
        System.out.println();

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
            if (room.getRoomStatus().equals(RoomStatus.VACANT)) {
                roomEntityControllerRemote.deleteRoom(room.getRoomId(), room.getRoomType().getRoomTypeId());
                System.out.println("Room successfully deleted!");
                System.out.println();
            } else {
                roomEntityControllerRemote.disableRoom(room.getRoomId());
                System.out.println("Room in use! Room is diabled.");
                System.out.println();
            }
        } catch (RoomNotFoundException ex) {
            System.out.println("Room " + roomNumber + " does not exist!");
            System.out.println();
        }
    }

    public void doViewAllRooms() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: View All Rooms ***\n");
        System.out.println();
        List<RoomEntity> rooms = roomEntityControllerRemote.viewAllRoom();
        if (rooms.size() > 0) {
            System.out.printf("%10s%15s%15s%15s%20s%15s\n", "Room Id", "Room Number", "Room Type", "Room Status", "Guest", "Is Disabled");
            for (RoomEntity room : rooms) {
                System.out.printf("%10s%15s%15s%15s%20s%15s\n", room.getRoomId(), room.getRoomNumber(), room.getRoomType().getName(), room.getRoomStatus(), room.getGuest(), room.getIsDisabled());
            }
            System.out.println();
            System.out.print("Press any key to continue...: ");
            scanner.nextLine();
            System.out.println();
        } else {
            System.out.println("No rooms available.");
            System.out.println();
        }
    }

    //to be modified
    public void doViewRoomAllocationExceptionReport() throws ReservationLineItemNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: View Room Allocation Exception Report ***\n");
        System.out.println();
        System.out.println("*** Exception: Upgraded to Next Higher Room Type ***\n");
        System.out.println();
        
        try {
            List <ReservationLineItemEntity> listOfSuccessfulUpgrade = reservationEntityControllerRemote.retrieveFirstException();
            System.out.printf("%24s%15s%15s%35s\n", "Reservation Line Item Id", "Start Date", "End Date", "Number of Successful Room Upgrade");
            for (ReservationLineItemEntity successfullUpgrade : listOfSuccessfulUpgrade){
                System.out.printf("%24s%15s%15s%35s\n", successfullUpgrade.getReservationLineItemId(), successfullUpgrade.getReservation().getStartDate(),
                        successfullUpgrade.getReservation().getEndDate(), successfullUpgrade.getNumOfSuccesfulUpgrade());
//                List <ReservationLineItemEntity> items = reservationEntityControllerRemote.retrieveItemsByReservationId(reservation.getReservationId());
//                for (ReservationLineItemEntity item: items){
//                    System.out.printf("%18s%15s%15s%20s%18s\n", item.getReservation(), reservation.getStartDate(), reservation.getEndDate(), item.getRoomType(), item.getNumOfRoomBooked());
//                }
                System.out.println();
            }
        } catch (ReservationLineItemNotFoundException ex) {
           System.out.println("No exceptions found.");
        }
        
        System.out.println();
        System.out.println("*** Exception: No Upgrade Available ***\n");
        System.out.println();
        
        List <ReservationLineItemEntity> listOfFailureUpgrade = reservationEntityControllerRemote.retrieveSecondException();
        System.out.printf("%24s%15s%15s%35s\n", "Reservation Line Item Id", "Start Date", "End Date", "Number of Failure Room Upgrade");
        for (ReservationLineItemEntity failureUpgrade : listOfFailureUpgrade){
            System.out.printf("%24s%15s%15s%35s\n", failureUpgrade.getReservationLineItemId(), failureUpgrade.getReservation().getStartDate(),
                        failureUpgrade.getReservation().getEndDate(), failureUpgrade.getNumOfFailureUpgrade());
            
//                List <ReservationLineItemEntity> items = reservationEntityControllerRemote.retrieveItemsByReservationId(reservation.getReservationId());
//                for (ReservationLineItemEntity item: items){
//                    System.out.printf("%18s%15s%15s%20s%18s\n", item.getReservation(), reservation.getStartDate(), reservation.getEndDate(), item.getRoomType(), item.getNumOfRoomBooked());
//                }
            System.out.println();
        }
    
        System.out.print("Press any key to continue...: ");
        scanner.nextLine();
        System.out.println();
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
                try {
                    newRate = roomRateEntityControllerRemote.createNewRoomRate(newRate, roomType.getRoomTypeId());
                    System.out.println("Room rate " + newRate.getRoomRateId() + " successfully created!\n");
                    System.out.println();
                } catch (CreateNewRoomRateException ex) {
                    System.out.println("Error creating new room rate: " + ex.getMessage());
                    System.out.println();
                }
            }
        } else {
            RoomRateEntity newRate = new RoomRateEntity(name, rate);
            try {
                newRate = roomRateEntityControllerRemote.createNewRoomRate(newRate, roomType.getRoomTypeId());
                System.out.println("Room rate " + newRate.getRoomRateId() + " successfully created!\n");
                System.out.println();
            } catch (CreateNewRoomRateException ex) {
                System.out.println("Error creating new room rate: " + ex.getMessage());
                System.out.println();
            }
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
                System.out.printf("%15s%30s%20s%15s%15s%15s\n", "Room Rate Id", "Room Rate Name", "Rate per Night", "Valid From", "Valid Till", "Is Disabled");
                System.out.printf("%15s%30s%20s%15s%15s%15s\n", roomRate.getRoomRateId(), roomRate.getName(), roomRate.getRatePerNight(), roomRate.getValidFrom(), roomRate.getValidTill(), roomRate.getIsDisabled());
            } else {
                System.out.printf("%15s%30s%20s%15s\n", "Room Rate Id", "Room Rate Name", "Rate per Night", "Is Disabled");
                System.out.printf("%15s%30s%20s%15s\n", roomRate.getRoomRateId(), roomRate.getName(), roomRate.getRatePerNight(), roomRate.getIsDisabled());
            }
            System.out.println("------------------------");
            System.out.println("1: Update Room Rate");
            System.out.println("2: Delete Room Rate");
            System.out.println("3: Back\n");
            System.out.println();
            System.out.print("Enter option: ");
            int response = scanner.nextInt();
            System.out.println();
            if (response == 1) {
                doUpdateRoomRate(roomRate);
            } else if (response == 2) {
                doDeleteRoomRate(roomRate);
            }
        } catch (RoomRateNotFoundException ex) {
            System.out.println(name + " does not exist!");
            System.out.println();
        }
    }

    public void doUpdateRoomRate(RoomRateEntity roomRate) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: Update Room Rate ***\n");
        System.out.println();
        System.out.print("Enter Name (blank if no change): ");
        String input = scanner.nextLine();
        RoomRateEntity newRoomRate = new RoomRateEntity();
        if (input.length() > 0) {
            newRoomRate.setName(input);
        }
        System.out.print("Enter Rate per Night (blank if no change): ");
        input = scanner.nextLine();
        if (input.length() > 0) {
            newRoomRate.setRatePerNight(new BigDecimal(input));
        }
        System.out.print("Enter Valid Date From (dd/mm/yyyy, blank if no change): ");
        input = scanner.nextLine();
        LocalDate startFromDate = LocalDate.of(Integer.parseInt(input.substring(6)), Integer.parseInt(input.substring(3, 5)), Integer.parseInt(input.substring(0, 2)));
        
        if (input.length() > 0) {
            newRoomRate.setValidFrom(Date.valueOf(startFromDate));
        }
        
        System.out.print("Enter Valid Date Till (dd/MM/yyyy, blank if no change): ");
        input = scanner.nextLine();
        LocalDate validTillDate = LocalDate.of(Integer.parseInt(input.substring(6)), Integer.parseInt(input.substring(3, 5)), Integer.parseInt(input.substring(0, 2)));
        if (input.length() > 0) {
            newRoomRate.setValidTill(Date.valueOf(validTillDate));
        }

        roomRateEntityControllerRemote.updateRoomRate(newRoomRate, roomRate.getRoomRateId());
        System.out.println("Room Rate updated successfully!\n");
    }

    public void doDeleteRoomRate(RoomRateEntity roomRate) {
        System.out.println("*** Merlion Hotel HoRS :: Hotel Operation :: Delete Room Rate ***\n");
        RoomTypeEntity roomType = roomRate.getRoomType();
        if (roomType.getIsDisabled().equals(Boolean.FALSE)) {
            try {
                List<RoomEntity> rooms = roomTypeEntityControllerRemote.retrieveRooms(roomRate.getRoomType().getRoomTypeId());
                Boolean roomInUse = false;
                for (RoomEntity room : rooms) {
                    // Or maybe allocated
                    if (room.getRoomStatus().equals(RoomStatus.OCCUPIED)) {
                        roomInUse = true;
                        break;
                    }
                }
                if (roomInUse.equals(Boolean.FALSE)) {
                    roomRateEntityControllerRemote.deleteRoomRate(roomRate.getRoomRateId());
                    System.out.println("Room Rate deleted successfully.");
                    System.out.println();
                } else {
                    roomRateEntityControllerRemote.disableRoomRate(roomRate.getRoomRateId());
                    System.out.println("Unable to delete room rate: room type in use! Room Rate is disabled.");
                    System.out.println();
                }
            } catch (RoomTypeNotFoundException ex) {
                System.out.println("Error deleting room rate: " + ex.getMessage());
            }
        } else {
            roomRateEntityControllerRemote.deleteRoomRate(roomRate.getRoomRateId());
            System.out.println("Room Rate deleted successfully.");
            System.out.println();
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
            System.out.println();
        } else {
            System.out.println("No room rates available.");
            System.out.println();
        }
    }
}
