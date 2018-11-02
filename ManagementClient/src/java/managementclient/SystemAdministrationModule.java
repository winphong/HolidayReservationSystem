/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managementclient;

import ejb.session.stateless.EmployeeEntityControllerRemote;
import ejb.session.stateless.PartnerEntityControllerRemote;
import entity.EmployeeEntity;
import entity.PartnerEntity;
import entity.PartnerEntity_;
import java.util.List;
import java.util.Scanner;
import util.enumeration.EmployeeAccessRight;
import util.exception.InvalidAccessRightException;

/**
 *
 * @author Asus
 */
public class SystemAdministrationModule {

    private EmployeeEntityControllerRemote employeeEntityControllerRemote;
    private PartnerEntityControllerRemote partnerEntityControllerRemote;
    private EmployeeEntity currentEmployee;

    public SystemAdministrationModule() {
    }

    public SystemAdministrationModule(EmployeeEntityControllerRemote employeeEntityControllerRemote, PartnerEntityControllerRemote partnerEntityControllerRemote, EmployeeEntity currentEmployee) {
        this();
        this.employeeEntityControllerRemote = employeeEntityControllerRemote;
        this.partnerEntityControllerRemote = partnerEntityControllerRemote;
        this.currentEmployee = currentEmployee;
    }

    public void menuSystemAdministration() throws InvalidAccessRightException {
        if (currentEmployee.getAccessRight() != EmployeeAccessRight.SYSTEMADMINISTRATOR) {
            throw new InvalidAccessRightException("You don't have MANAGER rights to access the system administration module.");
        }

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Merlion Hotel HoRS :: System Administration ***\n");
            System.out.println("-----------------------");
            System.out.println("1: Create New Employee");
            System.out.println("2: View All Employees");
            System.out.println("-----------------------");
            System.out.println("3: Create New Partner");
            System.out.println("4: View All Partners");
            System.out.println("-----------------------");
            System.out.println("5: Back\n");
            System.out.println();
            response = 0;

            while (response < 1 || response > 5) {
                System.out.print("Enter option: ");

                response = scanner.nextInt();

                if (response == 1) {
                    doCreateNewEmployee();
                } else if (response == 2) {
                    doViewAllEmployees();
                } else if (response == 3) {
                    doCreateNewPartner();
                } else if (response == 4) {
                    doViewAllPartners();
                } else if (response == 5) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 5) {
                break;
            }
        }
    }

    private void doCreateNewEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS :: System Administration :: Create New Employee ***\n");
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        EmployeeAccessRight employeeAccessRight;
        while (true) {
            System.out.println("1: System Administrator");
            System.out.println("2: Operation Manager");
            System.out.println("3. Sales Manager");
            System.out.println("4. Guest Relation Officer");
            System.out.println();
            System.out.print("Select Access Right: ");
            Integer accessRightInt = scanner.nextInt();

            if (accessRightInt >= 1 && accessRightInt <= 4) {
                employeeAccessRight = EmployeeAccessRight.values()[accessRightInt - 1];
                break;
            } else {
                System.out.println("Invalid option, please try again!\n");
            }
        }
        System.out.print("Enter User Name: ");
        String userName = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter Email Address: ");
        String email = scanner.nextLine();

        EmployeeEntity newEmployee = new EmployeeEntity(firstName, lastName, userName, password, employeeAccessRight, phoneNumber, email);
        employeeEntityControllerRemote.createNewEmployee(newEmployee);
        System.out.println("New Employee with Id " + newEmployee.getEmployeeId() + " created.");
    }
    
    private void doViewAllEmployees(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS :: System Administration :: View All Employees ***\n");
        List<EmployeeEntity> employeeEntities = employeeEntityControllerRemote.viewAllEmployee();
        System.out.printf("%12s%20s%20s%25s%20s%20s%10s%20s\n", "Employee ID", "First Name", "Last Name", "Access Right", "Username", "Password", "Phone Number", "Email Address");
        
        for (EmployeeEntity employeeEntity: employeeEntities)
        {
            System.out.printf("%12s%20s%20s%25s%20s%20s%10s%20s\n",employeeEntity.getEmployeeId(),employeeEntity.getFirstName(),employeeEntity.getLastName(),employeeEntity.getAccessRight(),employeeEntity.getUsername(),employeeEntity.getPassword(),employeeEntity.getPhoneNumber(),employeeEntity.getEmail());
        }
        System.out.print("Press any key to continue...: ");
        scanner.nextLine();
    }
    
    private void doCreateNewPartner (){
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS :: System Administration :: Create New Partner ***\n");
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Registration Id: ");
        String registrationId = scanner.nextLine();
        System.out.print("Enter User Name: ");
        String userName = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        Long phoneNumber = scanner.nextLong();
        System.out.print("Enter Email Address: ");
        String email = scanner.nextLine();
        
        PartnerEntity newPartner = new PartnerEntity(firstName,lastName,userName,password,phoneNumber,registrationId,email);
        partnerEntityControllerRemote.createNewPartner(newPartner);
        System.out.println("New Partner with Id " + newPartner.getPartnerId() + " created.");
    }
    
    private void doViewAllPartners(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Merlion Hotel HoRS :: System Administration :: View All Employees ***\n");
        List<PartnerEntity> partnerEntities = partnerEntityControllerRemote.viewAllPartner();
        System.out.printf("%12s%20s%20s%20s%20s%20s%10s%20s\n", "Employee ID", "First Name", "Last Name", "Registration Id", "Username", "Password", "Phone Number", "Email Address");
        
        for (PartnerEntity partnerEntity: partnerEntities)
        {
            System.out.printf("%12s%20s%20s%20s%20s%20s%10s%20s\n",partnerEntity.getPartnerId(),partnerEntity.getFirstName(),partnerEntity.getLastName(),partnerEntity.getCompanyRegistrationId(),partnerEntity.getUserName(),partnerEntity.getPassword(),partnerEntity.getPhoneNumber(),partnerEntity.getEmail());
        }
        System.out.print("Press any key to continue...: ");
        scanner.nextLine();
    }
}
