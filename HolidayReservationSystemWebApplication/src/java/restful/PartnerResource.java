/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import datamodel.ConverterRsp;
import datamodel.PartnerLoginRsp;
import datamodel.PartnerReserveRoomRsp;
import datamodel.PartnerSearchRoomRsp;
import datamodel.ReserveRoomRsp;
import datamodel.RetrieveAllReservationItemsRsp;
import datamodel.RetrieveRoomTypeRsp;
import datamodel.ViewAllReservationsRsp;
import datamodel.ViewReservationDetailsRsp;
import ejb.session.stateful.PartnerReservationEntityControllerLocal;
import ejb.session.stateless.InventoryControllerLocal;
import ejb.session.stateless.PartnerEntityControllerLocal;
import ejb.session.stateless.ReservationEntityControllerLocal;
import ejb.session.stateless.RoomTypeEntityControllerLocal;
import entity.PartnerEntity;
import entity.PartnerReservationEntity;
import entity.ReservationEntity;
import entity.ReservationLineItemEntity;
import entity.RoomTypeEntity;
import static entity.RoomTypeEntity_.room;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.InvalidLoginCredentialException;
import util.exception.ReservationNotFoundException;
import util.exception.RoomTypeNotFoundException;

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
    private PartnerReservationEntityControllerLocal partnerReservationEntityControllerLocal = lookupPartnerReservationEntityControllerLocal();
    private InventoryControllerLocal inventoryControllerLocal = lookupInventoryControllerLocal();
    private RoomTypeEntityControllerLocal roomTypeEntityControllerLocal = lookupRoomTypeEntityControllerLocal();
    private ReservationEntityControllerLocal reservationEntityControllerLocal = lookupReservationEntityControllerLocal();
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
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response retrieveAllReservations(@QueryParam("partnerId") Long partnerId){
        List <PartnerReservationEntity> reservations = partnerEntityControllerLocal.retrieveAllReservations(partnerId);
        ViewAllReservationsRsp viewAllReservationsRsp = new ViewAllReservationsRsp(reservations);
        return Response.status(Response.Status.OK).entity(viewAllReservationsRsp).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response retrieveReservationById(@QueryParam("id")Long id) throws ReservationNotFoundException{
        PartnerReservationEntity reservation = partnerReservationEntityControllerLocal.retrieveReservationById(id);
        ViewReservationDetailsRsp viewReservationDetailsRsp = new ViewReservationDetailsRsp(reservation);
        return Response.status(Response.Status.OK).entity(viewReservationDetailsRsp).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response searchAvailableRoom(@QueryParam("startDate")String startDate, @QueryParam("endDate")String endDate, @QueryParam("numOfRoomRequired")Integer numOfRoomRequired){
        List <RoomTypeEntity> availableRooms = inventoryControllerLocal.searchAvailableRoom(startDate, endDate, numOfRoomRequired);
        PartnerSearchRoomRsp partnerSearchRoomRsp = new PartnerSearchRoomRsp(availableRooms);
        return Response.status(Response.Status.OK).entity(partnerSearchRoomRsp).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response retrieveRoomTypeByName (@QueryParam("name")String name) throws RoomTypeNotFoundException{
        RoomTypeEntity roomType = roomTypeEntityControllerLocal.retrieveRoomTypeByName(name);
        RetrieveRoomTypeRsp retrieveRoomTypeRsp = new RetrieveRoomTypeRsp(roomType);
        return Response.status(Response.Status.OK).entity(retrieveRoomTypeRsp).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response checkOut (@QueryParam("partnerId")Long partnerId, @QueryParam("customerFirstName")String customerFirstName, @QueryParam("customerLastName")String customerLastName, @QueryParam("customerIdentificationNumber")String customerIdentificationNumber, @QueryParam("customerContactNumber")String customerContactNumber, @QueryParam("customerEmail")String customerEmail, @QueryParam("startDate") String startDate, @QueryParam("endDate")String endDate) throws Exception{
        ReservationEntity reservation = partnerReservationEntityControllerLocal.checkOut(partnerId, customerFirstName, customerLastName, customerIdentificationNumber, customerContactNumber, customerEmail, startDate, endDate);
        PartnerReserveRoomRsp partnerReserveRoomRsp = new PartnerReserveRoomRsp(reservation);
        return Response.status(Response.Status.OK).entity(partnerReserveRoomRsp).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response reserveRoom(@QueryParam("roomTypeName")String roomTypeName, @QueryParam("startDate")String startDate, @QueryParam("endDate")String endDate, @QueryParam("numOfRoomRequired")Integer numOfRoomRequired) throws RoomTypeNotFoundException, Exception{
        Boolean reserve = partnerReservationEntityControllerLocal.reserveRoom(roomTypeName, startDate, endDate, numOfRoomRequired);
        ReserveRoomRsp reserveRoomRsp = new ReserveRoomRsp(reserve);
        return Response.status(Response.Status.OK).entity(reserveRoomRsp).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response retrieveItemsByReservationId(@QueryParam("reservationId")Long reservationId) throws ReservationNotFoundException{
        List<ReservationLineItemEntity> items = reservationEntityControllerLocal.retrieveItemsByReservationId(reservationId);
        RetrieveAllReservationItemsRsp retrieveAllReservationItemsRsp = new RetrieveAllReservationItemsRsp(items);
        return Response.status(Response.Status.OK).entity(retrieveAllReservationItemsRsp).build();
    }

    private PartnerEntityControllerLocal lookupPartnerEntityControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (PartnerEntityControllerLocal) c.lookup("java:global/HolidayReservationSystem/HolidayReservationSystem-ejb/PartnerEntityController!ejb.session.stateless.PartnerEntityControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    private PartnerReservationEntityControllerLocal lookupPartnerReservationEntityControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (PartnerReservationEntityControllerLocal) c.lookup("java:global/HolidayReservationSystem/HolidayReservationSystem-ejb/PartnerReservationEntityController!ejb.session.stateful.PartnerReservationEntityControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    private InventoryControllerLocal lookupInventoryControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (InventoryControllerLocal) c.lookup("java:global/HolidayReservationSystem/HolidayReservationSystem-ejb/InventoryController!ejb.session.stateless.InventoryControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    private RoomTypeEntityControllerLocal lookupRoomTypeEntityControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (RoomTypeEntityControllerLocal) c.lookup("java:global/HolidayReservationSystem/HolidayReservationSystem-ejb/RoomTypeEntityController!ejb.session.stateless.RoomTypeEntityControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    private ReservationEntityControllerLocal lookupReservationEntityControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ReservationEntityControllerLocal) c.lookup("java:global/HolidayReservationSystem/HolidayReservationSystem-ejb/ReservationEntityController!ejb.session.stateless.ReservationEntityControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
