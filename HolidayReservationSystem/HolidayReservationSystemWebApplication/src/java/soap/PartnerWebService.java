/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soap;

import datamodel.PartnerLoginRsp;
import ejb.session.stateless.PartnerEntityControllerRemote;
import entity.PartnerEntity;
import static entity.PartnerReservationEntity_.partner;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ws.rs.core.Response;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author Asus
 */
@WebService(serviceName = "PartnerWebService")
public class PartnerWebService {

    @EJB
    private PartnerEntityControllerRemote partnerEntityControllerRemote;

    public Response partnerLogin(String username, String password) throws InvalidLoginCredentialException {
        PartnerEntity partner = partnerEntityControllerRemote.partnerLogin(username, password);
        PartnerLoginRsp partnerLoginRsp = new PartnerLoginRsp(partner);
        return Response.status(Response.Status.OK).entity(partnerLoginRsp).build();
    }
}
