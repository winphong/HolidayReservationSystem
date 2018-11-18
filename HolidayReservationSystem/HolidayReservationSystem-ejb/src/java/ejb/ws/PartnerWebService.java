/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.ws;

import ejb.session.stateful.PartnerReservationEntityControllerLocal;
import ejb.session.stateless.InventoryControllerLocal;
import ejb.session.stateless.PartnerEntityControllerLocal;
import ejb.session.stateless.RoomTypeEntityControllerLocal;
import entity.PartnerEntity;
import entity.PartnerReservationEntity;
import entity.ReservationEntity;
import entity.RoomTypeEntity;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import util.exception.InvalidLoginCredentialException;
import util.exception.ReservationNotFoundException;
import util.exception.RoomTypeNotFoundException;

/**
 *
 * @author Asus
 */
@WebService(serviceName = "HolidayReservationSystemWebService")
@Stateless()
public class PartnerWebService {

    @EJB
    private RoomTypeEntityControllerLocal roomTypeEntityControllerLocal;

    @EJB
    private InventoryControllerLocal inventoryControllerLocal;

    @EJB
    private PartnerReservationEntityControllerLocal partnerReservationEntityControllerLocal;

    @EJB
    private PartnerEntityControllerLocal partnerEntityControllerLocal;
    

    public PartnerEntity partnerLogin(String username, String password) throws InvalidLoginCredentialException{
        return partnerEntityControllerLocal.partnerLogin(username, password);
    }
    
    public PartnerReservationEntity retrieveReservationById(Long id) throws ReservationNotFoundException{
        return partnerReservationEntityControllerLocal.retrieveReservationById(id);
    }
    
    public List <PartnerReservationEntity> retrieveAllReservations (Long partnerId){
        return partnerEntityControllerLocal.retrieveAllReservations(partnerId);
    }
    
    public List<RoomTypeEntity> searchAvailableRoom(String startDate, String endDate, Integer numOfRoomRequired){
        return inventoryControllerLocal.searchAvailableRoom(startDate, endDate, numOfRoomRequired);
    }
    
    public RoomTypeEntity retrieveRoomTypeByName (String name)throws RoomTypeNotFoundException{
        return roomTypeEntityControllerLocal.retrieveRoomTypeByName(name);
    }
    
    public ReservationEntity checkOut(Long partnerId, String customerFirstName, String customerLastName, String customerIdentificationNumber, String customerContactNumber, String customerEmail, String startDate, String endDate) throws Exception{
        return partnerReservationEntityControllerLocal.checkOut(partnerId, customerFirstName, customerLastName, customerIdentificationNumber, customerContactNumber, customerEmail, startDate, endDate);
    }
    
    public Boolean reserveRoom(String roomTypeName, String startDate, String endDate, Integer numOfRoomRequired) throws RoomTypeNotFoundException, Exception{
        return partnerReservationEntityControllerLocal.reserveRoom(roomTypeName, startDate, endDate, numOfRoomRequired);
    }
}
