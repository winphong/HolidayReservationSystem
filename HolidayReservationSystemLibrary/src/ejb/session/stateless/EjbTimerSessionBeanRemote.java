/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import javax.ejb.Remote;
import util.exception.ReservationNotFoundException;
import util.exception.RoomTypeNotFoundException;

/**
 *
 * @author twp10
 */
@Remote
public interface EjbTimerSessionBeanRemote {

    public void allocateRoom() throws ReservationNotFoundException, RoomTypeNotFoundException, Exception;

    public void finishUpHousekeeping();

    public void allocateRoom(Long reservationId) throws ReservationNotFoundException, RoomTypeNotFoundException, Exception;
    
}
