
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
    private final static QName _RetrieveReservationById_QNAME = new QName("http://ws.ejb/", "retrieveReservationById");
    private final static QName _InvalidLoginCredentialException_QNAME = new QName("http://ws.ejb/", "InvalidLoginCredentialException");
    private final static QName _RetrieveReservationByIdResponse_QNAME = new QName("http://ws.ejb/", "retrieveReservationByIdResponse");
    private final static QName _RetrieveAllReservations_QNAME = new QName("http://ws.ejb/", "retrieveAllReservations");
    private final static QName _ReservationNotFoundException_QNAME = new QName("http://ws.ejb/", "ReservationNotFoundException");
    private final static QName _PartnerLoginResponse_QNAME = new QName("http://ws.ejb/", "partnerLoginResponse");
    private final static QName _RetrieveAllReservationsResponse_QNAME = new QName("http://ws.ejb/", "retrieveAllReservationsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws.client.partner
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InvalidLoginCredentialException }
     * 
     */
    public InvalidLoginCredentialException createInvalidLoginCredentialException() {
        return new InvalidLoginCredentialException();
    }

    /**
     * Create an instance of {@link RetrieveReservationByIdResponse }
     * 
     */
    public RetrieveReservationByIdResponse createRetrieveReservationByIdResponse() {
        return new RetrieveReservationByIdResponse();
    }

    /**
     * Create an instance of {@link RetrieveAllReservations }
     * 
     */
    public RetrieveAllReservations createRetrieveAllReservations() {
        return new RetrieveAllReservations();
    }

    /**
     * Create an instance of {@link ReservationNotFoundException }
     * 
     */
    public ReservationNotFoundException createReservationNotFoundException() {
        return new ReservationNotFoundException();
    }

    /**
     * Create an instance of {@link PartnerLoginResponse }
     * 
     */
    public PartnerLoginResponse createPartnerLoginResponse() {
        return new PartnerLoginResponse();
    }

    /**
     * Create an instance of {@link RetrieveAllReservationsResponse }
     * 
     */
    public RetrieveAllReservationsResponse createRetrieveAllReservationsResponse() {
        return new RetrieveAllReservationsResponse();
    }

    /**
     * Create an instance of {@link PartnerLogin }
     * 
     */
    public PartnerLogin createPartnerLogin() {
        return new PartnerLogin();
    }

    /**
     * Create an instance of {@link RetrieveReservationById }
     * 
     */
    public RetrieveReservationById createRetrieveReservationById() {
        return new RetrieveReservationById();
    }

    /**
     * Create an instance of {@link Date }
     * 
     */
    public Date createDate() {
        return new Date();
    }

    /**
     * Create an instance of {@link ReservationLineItemEntity }
     * 
     */
    public ReservationLineItemEntity createReservationLineItemEntity() {
        return new ReservationLineItemEntity();
    }

    /**
     * Create an instance of {@link PartnerReservationEntity }
     * 
     */
    public PartnerReservationEntity createPartnerReservationEntity() {
        return new PartnerReservationEntity();
    }

    /**
     * Create an instance of {@link PartnerEntity }
     * 
     */
    public PartnerEntity createPartnerEntity() {
        return new PartnerEntity();
    }

    /**
     * Create an instance of {@link RoomRateEntity }
     * 
     */
    public RoomRateEntity createRoomRateEntity() {
        return new RoomRateEntity();
    }

    /**
     * Create an instance of {@link RoomEntity }
     * 
     */
    public RoomEntity createRoomEntity() {
        return new RoomEntity();
    }

    /**
     * Create an instance of {@link RoomTypeEntity }
     * 
     */
    public RoomTypeEntity createRoomTypeEntity() {
        return new RoomTypeEntity();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveReservationById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ejb/", name = "retrieveReservationById")
    public JAXBElement<RetrieveReservationById> createRetrieveReservationById(RetrieveReservationById value) {
        return new JAXBElement<RetrieveReservationById>(_RetrieveReservationById_QNAME, RetrieveReservationById.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveReservationByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ejb/", name = "retrieveReservationByIdResponse")
    public JAXBElement<RetrieveReservationByIdResponse> createRetrieveReservationByIdResponse(RetrieveReservationByIdResponse value) {
        return new JAXBElement<RetrieveReservationByIdResponse>(_RetrieveReservationByIdResponse_QNAME, RetrieveReservationByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveAllReservations }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ejb/", name = "retrieveAllReservations")
    public JAXBElement<RetrieveAllReservations> createRetrieveAllReservations(RetrieveAllReservations value) {
        return new JAXBElement<RetrieveAllReservations>(_RetrieveAllReservations_QNAME, RetrieveAllReservations.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveAllReservationsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ejb/", name = "retrieveAllReservationsResponse")
    public JAXBElement<RetrieveAllReservationsResponse> createRetrieveAllReservationsResponse(RetrieveAllReservationsResponse value) {
        return new JAXBElement<RetrieveAllReservationsResponse>(_RetrieveAllReservationsResponse_QNAME, RetrieveAllReservationsResponse.class, null, value);
    }

}