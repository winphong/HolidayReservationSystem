/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.ws;

import ejb.session.stateless.PartnerEntityControllerLocal;
import entity.PartnerEntity;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author Asus
 */
@WebService(serviceName = "HolidayReservationSystemWebService")
@Stateless()
public class PartnerWebService {

    @EJB
    private PartnerEntityControllerLocal partnerEntityControllerLocal;

    public PartnerEntity partnerLogin(String username, String password) throws InvalidLoginCredentialException{
        return partnerEntityControllerLocal.partnerLogin(username, password);
    }
}
