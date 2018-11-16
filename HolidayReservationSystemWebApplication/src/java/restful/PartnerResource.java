/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import datamodel.PartnerLoginRsp;
import ejb.session.stateless.PartnerEntityControllerLocal;
import entity.PartnerEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.InvalidLoginCredentialException;

/**
 * REST Web Service
 *
 * @author Asus
 */
@Path("Partner")
public class PartnerResource {

    @Context
    private UriInfo context;
    
    private PartnerEntityControllerLocal partnerEntityControllerLocal = lookupPartnerEntityControllerLocal();

    /**
     * Creates a new instance of PartnerResource
     */
    public PartnerResource() {
    }

    /**
     * Retrieves representation of an instance of restful.PartnerResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response partnerLogin(@QueryParam("username") String username, @QueryParam("password") String password) throws InvalidLoginCredentialException{
        //TODO return proper representation object
        PartnerEntity partner = partnerEntityControllerLocal.partnerLogin(username, password);
        if (partner == null){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        PartnerLoginRsp partnerLoginRsp = new PartnerLoginRsp(partner);
        return Response.status(Response.Status.OK).entity(partnerLoginRsp).build();
    }

//    /**
//     * PUT method for updating or creating an instance of PartnerResource
//     * @param content representation for the resource
//     */
//    @PUT
//    @Consumes(MediaType.APPLICATION_XML)
//    public void putXml(String content) {
//    }

    private PartnerEntityControllerLocal lookupPartnerEntityControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (PartnerEntityControllerLocal) c.lookup("java:global/HolidayReservationSystem/HolidayReservationSystem-ejb/PartnerEntityController!ejb.session.stateless.PartnerEntityControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
