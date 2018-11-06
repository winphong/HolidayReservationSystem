/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managementclient;

import ejb.session.stateful.ReservationEntityControllerRemote;
import ejb.session.stateless.GuestEntityControllerRemote;
import ejb.session.stateless.RoomEntityControllerRemote;
import entity.EmployeeEntity;
import util.enumeration.EmployeeAccessRight;
import util.exception.InvalidAccessRightException;

/**
 *
 * @author Asus
 */
public class FrontOfficeModule {
    
    private ReservationEntityControllerRemote reservationEntityControllerRemote;
    private GuestEntityControllerRemote guestEntityControllerRemote;
    private RoomEntityControllerRemote roomEntityControllerRemote;
    private EmployeeEntity currentEmployee;

    public FrontOfficeModule() {
    }
    
    public void menuFrontOffice() throws InvalidAccessRightException{
        if (currentEmployee.getAccessRight() != EmployeeAccessRight.GUESTRELATIONOFFICER) {
            throw new InvalidAccessRightException("You don't have MANAGER rights to access the system administration module.");
        }
    }
}
