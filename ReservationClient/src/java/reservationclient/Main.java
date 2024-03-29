/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservationclient;

import ejb.session.stateful.OnlineReservationEntityControllerRemote;
import ejb.session.stateless.EjbTimerSessionBeanRemote;
import ejb.session.stateless.GuestEntityControllerRemote;
import ejb.session.stateless.InventoryControllerRemote;
import ejb.session.stateless.ReservationEntityControllerRemote;
import ejb.session.stateless.RoomEntityControllerRemote;
import ejb.session.stateless.RoomRateEntityControllerRemote;
import ejb.session.stateless.RoomTypeEntityControllerRemote;
import javax.ejb.EJB;

/**
 *
 * @author twp10
 */
public class Main {

    @EJB
    private static EjbTimerSessionBeanRemote ejbTimerSessionBeanRemote;

    @EJB
    private static ReservationEntityControllerRemote reservationEntityControllerRemote;

    @EJB
    private static InventoryControllerRemote inventoryControllerRemote;

    @EJB
    private static RoomRateEntityControllerRemote roomRateEntityControllerRemote;

    @EJB
    private static RoomEntityControllerRemote roomEntityControllerRemote;

    @EJB
    private static RoomTypeEntityControllerRemote roomTypeEntityControllerRemote;

    @EJB
    private static OnlineReservationEntityControllerRemote onlineReservationEntityControllerRemote;

    @EJB
    private static GuestEntityControllerRemote guestEntityControllerRemote;
    
    

    public static void main(String[] args) throws Exception {
        GuestMainApp mainApp = new GuestMainApp(roomRateEntityControllerRemote, roomEntityControllerRemote, roomTypeEntityControllerRemote, onlineReservationEntityControllerRemote, inventoryControllerRemote, guestEntityControllerRemote, reservationEntityControllerRemote, ejbTimerSessionBeanRemote);
        mainApp.runApp();
    }
    
}
