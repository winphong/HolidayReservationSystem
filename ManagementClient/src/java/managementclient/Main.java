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
import javax.ejb.EJB;
import ejb.session.stateful.WalkinReservationEntityControllerRemote;
import ejb.session.stateless.InventoryControllerRemote;
import ejb.session.stateless.ReservationEntityControllerRemote;

/**
 *
 * @author twp10
 */
public class Main {

    @EJB
    private static WalkinReservationEntityControllerRemote walkinReservationEntityController;

    @EJB
    private static OnlineReservationEntityControllerRemote onlineReservationEntityController;

    @EJB
    private static InventoryControllerRemote inventoryControllerRemote;

    @EJB
    private static GuestEntityControllerRemote guestEntityControllerRemote;

    @EJB
    private static ReservationEntityControllerRemote reservationEntityControllerRemote;

    @EJB
    private static RoomRateEntityControllerRemote roomRateEntityControllerRemote;

    @EJB
    private static RoomEntityControllerRemote roomEntityControllerRemote;

    @EJB
    private static RoomTypeEntityControllerRemote roomTypeEntityControllerRemote;

    @EJB
    private static PartnerEntityControllerRemote partnerEntityControllerRemote;

    @EJB
    private static EmployeeEntityControllerRemote employeeEntityControllerRemote;

    
    public static void main(String[] args) throws Exception {
        MainApp mainApp = new MainApp(guestEntityControllerRemote, reservationEntityControllerRemote, roomRateEntityControllerRemote, roomEntityControllerRemote, roomTypeEntityControllerRemote, partnerEntityControllerRemote, employeeEntityControllerRemote, inventoryControllerRemote, walkinReservationEntityController, onlineReservationEntityController);
        mainApp.runApp();
    }
    
}
