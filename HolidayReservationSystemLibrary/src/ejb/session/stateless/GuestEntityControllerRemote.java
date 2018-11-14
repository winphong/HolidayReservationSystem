/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.GuestEntity;
import entity.OnlineReservationEntity;
import java.util.List;
import util.exception.GuestNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author twp10
 */
public interface GuestEntityControllerRemote {

    public GuestEntity guestLogin(String username, String password) throws GuestNotFoundException, InvalidLoginCredentialException;

    public GuestEntity registerGuest(GuestEntity newGuest);
    
}
