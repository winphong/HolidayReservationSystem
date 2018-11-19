
package ws.client.partner;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ws.client.partner package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _PartnerLogin_QNAME = new QName("http://ws.ejb/", "partnerLogin");
    private final static QName _SearchAvailableRoom_QNAME = new QName("http://ws.ejb/", "searchAvailableRoom");
    private final static QName _CheckOut_QNAME = new QName("http://ws.ejb/", "checkOut");
    private final static QName _RetrieveItemsByReservationIdResponse_QNAME = new QName("http://ws.ejb/", "retrieveItemsByReservationIdResponse");
    private final static QName _RetrieveReservationByIdResponse_QNAME = new QName("http://ws.ejb/", "retrieveReservationByIdResponse");
    private final static QName _ReserveRoomResponse_QNAME = new QName("http://ws.ejb/", "reserveRoomResponse");
    private final static QName _ReservationNotFoundException_QNAME = new QName("http://ws.ejb/", "ReservationNotFoundException");
    private final static QName _PartnerLoginResponse_QNAME = new QName("http://ws.ejb/", "partnerLoginResponse");
    private final static QName _SearchAvailableRoomResponse_QNAME = new QName("http://ws.ejb/", "searchAvailableRoomResponse");
    private final static QName _RetrieveAllReservationsResponse_QNAME = new QName("http://ws.ejb/", "retrieveAllReservationsResponse");
    private final static QName _RetrieveItemsByReservationId_QNAME = new QName("http://ws.ejb/", "retrieveItemsByReservationId");
    private final static QName _ReserveRoom_QNAME = new QName("http://ws.ejb/", "reserveRoom");
    private final static QName _RetrieveReservationById_QNAME = new QName("http://ws.ejb/", "retrieveReservationById");
    private final static QName _CheckOutResponse_QNAME = new QName("http://ws.ejb/", "checkOutResponse");
    private final static QName _RetrieveRoomTypeByName_QNAME = new QName("http://ws.ejb/", "retrieveRoomTypeByName");
    private final static QName _RetrieveRoomTypeByNameResponse_QNAME = new QName("http://ws.ejb/", "retrieveRoomTypeByNameResponse");
    private final static QName _Exception_QNAME = new QName("http://ws.ejb/", "Exception");
    private final static QName _InvalidLoginCredentialException_QNAME = new QName("http://ws.ejb/", "InvalidLoginCredentialException");
    private final static QName _RoomTypeNotFoundException_QNAME = new QName("http://ws.ejb/", "RoomTypeNotFoundException");
    private final static QName _RetrieveAllReservations_QNAME = new QName("http://ws.ejb/", "retrieveAllReservations");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws.client.partner
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link RetrieveAllReservationsResponse }
     * 
     */
    public RetrieveAllReservationsResponse createRetrieveAllReservationsResponse() {
        return new RetrieveAllReservationsResponse();
    }

    /**
     * Create an instance of {@link RetrieveItemsByReservationIdResponse }
     * 
     */
    public RetrieveItemsByReservationIdResponse createRetrieveItemsByReservationIdResponse() {
        return new RetrieveItemsByReservationIdResponse();
    }

    /**
     * Create an instance of {@link PartnerEntity }
     * 
     */
    public PartnerEntity createPartnerEntity() {
        return new PartnerEntity();
    }

    /**
     * Create an instance of {@link RoomTypeEntity }
     * 
     */
    public RoomTypeEntity createRoomTypeEntity() {
        return new RoomTypeEntity();
    }

    /**
     * Create an instance of {@link RetrieveRoomTypeByName }
     * 
     */
    public RetrieveRoomTypeByName createRetrieveRoomTypeByName() {
        return new RetrieveRoomTypeByName();
    }

    /**
     * Create an instance of {@link RetrieveReservationById }
     * 
     */
    public RetrieveReservationById createRetrieveReservationById() {
        return new RetrieveReservationById();
    }

    /**
     * Create an instance of {@link CheckOutResponse }
     * 
     */
    public CheckOutResponse createCheckOutResponse() {
        return new CheckOutResponse();
    }

    /**
     * Create an instance of {@link RetrieveAllReservations }
     * 
     */
    public RetrieveAllReservations createRetrieveAllReservations() {
        return new RetrieveAllReservations();
    }

    /**
     * Create an instance of {@link InvalidLoginCredentialException }
     * 
     */
    public InvalidLoginCredentialException createInvalidLoginCredentialException() {
        return new InvalidLoginCredentialException();
    }

    /**
     * Create an instance of {@link CheckOut }
     * 
     */
    public CheckOut createCheckOut() {
        return new CheckOut();
    }

    /**
     * Create an instance of {@link ReserveRoomResponse }
     * 
     */
    public ReserveRoomResponse createReserveRoomResponse() {
        return new ReserveRoomResponse();
    }

    /**
     * Create an instance of {@link RetrieveReservationByIdResponse }
     * 
     */
    public RetrieveReservationByIdResponse createRetrieveReservationByIdResponse() {
        return new RetrieveReservationByIdResponse();
    }

    /**
     * Create an instance of {@link ReserveRoom }
     * 
     */
    public ReserveRoom createReserveRoom() {
        return new ReserveRoom();
    }

    /**
     * Create an instance of {@link RetrieveItemsByReservationId }
     * 
     */
    public RetrieveItemsByReservationId createRetrieveItemsByReservationId() {
        return new RetrieveItemsByReservationId();
    }

    /**
     * Create an instance of {@link SearchAvailableRoomResponse }
     * 
     */
    public SearchAvailableRoomResponse createSearchAvailableRoomResponse() {
        return new SearchAvailableRoomResponse();
    }

    /**
     * Create an instance of {@link PartnerLoginResponse }
     * 
     */
    public PartnerLoginResponse createPartnerLoginResponse() {
        return new PartnerLoginResponse();
    }

    /**
     * Create an instance of {@link RoomTypeNotFoundException }
     * 
     */
    public RoomTypeNotFoundException createRoomTypeNotFoundException() {
        return new RoomTypeNotFoundException();
    }

    /**
     * Create an instance of {@link ReservationNotFoundException }
     * 
     */
    public ReservationNotFoundException createReservationNotFoundException() {
        return new ReservationNotFoundException();
    }

    /**
     * Create an instance of {@link SearchAvailableRoom }
     * 
     */
    public SearchAvailableRoom createSearchAvailableRoom() {
        return new SearchAvailableRoom();
    }

    /**
     * Create an instance of {@link PartnerReservationEntity }
     * 
     */
    public PartnerReservationEntity createPartnerReservationEntity() {
        return new PartnerReservationEntity();
    }

    /**
     * Create an instance of {@link ReservationLineItemEntity }
     * 
     */
    public ReservationLineItemEntity createReservationLineItemEntity() {
        return new ReservationLineItemEntity();
    }

    /**
     * Create an instance of {@link PartnerLogin }
     * 
     */
    public PartnerLogin createPartnerLogin() {
        return new PartnerLogin();
    }

    /**
     * Create an instance of {@link Date }
     * 
     */
    public Date createDate() {
        return new Date();
    }

    /**
     * Create an instance of {@link RetrieveRoomTypeByNameResponse }
     * 
     */
    public RetrieveRoomTypeByNameResponse createRetrieveRoomTypeByNameResponse() {
        return new RetrieveRoomTypeByNameResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PartnerLogin }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ejb/", name = "partnerLogin")
    public JAXBElement<PartnerLogin> createPartnerLogin(PartnerLogin value) {
        return new JAXBElement<PartnerLogin>(_PartnerLogin_QNAME, PartnerLogin.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchAvailableRoom }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ejb/", name = "searchAvailableRoom")
    public JAXBElement<SearchAvailableRoom> createSearchAvailableRoom(SearchAvailableRoom value) {
        return new JAXBElement<SearchAvailableRoom>(_SearchAvailableRoom_QNAME, SearchAvailableRoom.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckOut }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ejb/", name = "checkOut")
    public JAXBElement<CheckOut> createCheckOut(CheckOut value) {
        return new JAXBElement<CheckOut>(_CheckOut_QNAME, CheckOut.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveItemsByReservationIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ejb/", name = "retrieveItemsByReservationIdResponse")
    public JAXBElement<RetrieveItemsByReservationIdResponse> createRetrieveItemsByReservationIdResponse(RetrieveItemsByReservationIdResponse value) {
        return new JAXBElement<RetrieveItemsByReservationIdResponse>(_RetrieveItemsByReservationIdResponse_QNAME, RetrieveItemsByReservationIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveReservationByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ejb/", name = "retrieveReservationByIdResponse")
    public JAXBElement<RetrieveReservationByIdResponse> createRetrieveReservationByIdResponse(RetrieveReservationByIdResponse value) {
        return new JAXBElement<RetrieveReservationByIdResponse>(_RetrieveReservationByIdResponse_QNAME, RetrieveReservationByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserveRoomResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ejb/", name = "reserveRoomResponse")
    public JAXBElement<ReserveRoomResponse> createReserveRoomResponse(ReserveRoomResponse value) {
        return new JAXBElement<ReserveRoomResponse>(_ReserveRoomResponse_QNAME, ReserveRoomResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReservationNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ejb/", name = "ReservationNotFoundException")
    public JAXBElement<ReservationNotFoundException> createReservationNotFoundException(ReservationNotFoundException value) {
        return new JAXBElement<ReservationNotFoundException>(_ReservationNotFoundException_QNAME, ReservationNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PartnerLoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ejb/", name = "partnerLoginResponse")
    public JAXBElement<PartnerLoginResponse> createPartnerLoginResponse(PartnerLoginResponse value) {
        return new JAXBElement<PartnerLoginResponse>(_PartnerLoginResponse_QNAME, PartnerLoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchAvailableRoomResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ejb/", name = "searchAvailableRoomResponse")
    public JAXBElement<SearchAvailableRoomResponse> createSearchAvailableRoomResponse(SearchAvailableRoomResponse value) {
        return new JAXBElement<SearchAvailableRoomResponse>(_SearchAvailableRoomResponse_QNAME, SearchAvailableRoomResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveAllReservationsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ejb/", name = "retrieveAllReservationsResponse")
    public JAXBElement<RetrieveAllReservationsResponse> createRetrieveAllReservationsResponse(RetrieveAllReservationsResponse value) {
        return new JAXBElement<RetrieveAllReservationsResponse>(_RetrieveAllReservationsResponse_QNAME, RetrieveAllReservationsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveItemsByReservationId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ejb/", name = "retrieveItemsByReservationId")
    public JAXBElement<RetrieveItemsByReservationId> createRetrieveItemsByReservationId(RetrieveItemsByReservationId value) {
        return new JAXBElement<RetrieveItemsByReservationId>(_RetrieveItemsByReservationId_QNAME, RetrieveItemsByReservationId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserveRoom }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ejb/", name = "reserveRoom")
    public JAXBElement<ReserveRoom> createReserveRoom(ReserveRoom value) {
        return new JAXBElement<ReserveRoom>(_ReserveRoom_QNAME, ReserveRoom.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveReservationById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ejb/", name = "retrieveReservationById")
    public JAXBElement<RetrieveReservationById> createRetrieveReservationById(RetrieveReservationById value) {
        return new JAXBElement<RetrieveReservationById>(_RetrieveReservationById_QNAME, RetrieveReservationById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckOutResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ejb/", name = "checkOutResponse")
    public JAXBElement<CheckOutResponse> createCheckOutResponse(CheckOutResponse value) {
        return new JAXBElement<CheckOutResponse>(_CheckOutResponse_QNAME, CheckOutResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveRoomTypeByName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ejb/", name = "retrieveRoomTypeByName")
    public JAXBElement<RetrieveRoomTypeByName> createRetrieveRoomTypeByName(RetrieveRoomTypeByName value) {
        return new JAXBElement<RetrieveRoomTypeByName>(_RetrieveRoomTypeByName_QNAME, RetrieveRoomTypeByName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveRoomTypeByNameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ejb/", name = "retrieveRoomTypeByNameResponse")
    public JAXBElement<RetrieveRoomTypeByNameResponse> createRetrieveRoomTypeByNameResponse(RetrieveRoomTypeByNameResponse value) {
        return new JAXBElement<RetrieveRoomTypeByNameResponse>(_RetrieveRoomTypeByNameResponse_QNAME, RetrieveRoomTypeByNameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ejb/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidLoginCredentialException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ejb/", name = "InvalidLoginCredentialException")
    public JAXBElement<InvalidLoginCredentialException> createInvalidLoginCredentialException(InvalidLoginCredentialException value) {
        return new JAXBElement<InvalidLoginCredentialException>(_InvalidLoginCredentialException_QNAME, InvalidLoginCredentialException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RoomTypeNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ejb/", name = "RoomTypeNotFoundException")
    public JAXBElement<RoomTypeNotFoundException> createRoomTypeNotFoundException(RoomTypeNotFoundException value) {
        return new JAXBElement<RoomTypeNotFoundException>(_RoomTypeNotFoundException_QNAME, RoomTypeNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveAllReservations }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ejb/", name = "retrieveAllReservations")
    public JAXBElement<RetrieveAllReservations> createRetrieveAllReservations(RetrieveAllReservations value) {
        return new JAXBElement<RetrieveAllReservations>(_RetrieveAllReservations_QNAME, RetrieveAllReservations.class, null, value);
    }

}
