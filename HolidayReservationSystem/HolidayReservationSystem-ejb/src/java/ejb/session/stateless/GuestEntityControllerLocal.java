/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.GuestEntity;
import util.exception.GuestNotFoundException;

/**
 *
 * @author twp10
 */
public interface GuestEntityControllerLocal {

    public GuestEntity retrieveGuestByUsername(String username)throws GuestNotFoundException;

    public GuestEntity retrieveGuestById(Long guestId);
    
}
