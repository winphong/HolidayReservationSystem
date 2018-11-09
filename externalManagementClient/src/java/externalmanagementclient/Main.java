/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package externalmanagementclient;

import ejb.session.stateful.PartnerReservationEntityControllerRemote;
import ejb.session.stateless.InventoryControllerRemote;
import ejb.session.stateless.PartnerEntityControllerRemote;
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
    private static RoomEntityControllerRemote roomEntityControllerRemote;

    @EJB
    private static RoomRateEntityControllerRemote roomRateEntityControllerRemote;

    @EJB
    private static RoomTypeEntityControllerRemote roomTypeEntityControllerRemote;

    @EJB
    private static InventoryControllerRemote inventoryControllerRemote;

    @EJB
    private static PartnerReservationEntityControllerRemote partnerReservationEntityControllerRemote;

    @EJB
    private static PartnerEntityControllerRemote partnerEntityControllerRemote;

    
    public static void main(String[] args) {
        MainApp mainApp = new MainApp();
        mainApp.runApp();
    }
    
}
