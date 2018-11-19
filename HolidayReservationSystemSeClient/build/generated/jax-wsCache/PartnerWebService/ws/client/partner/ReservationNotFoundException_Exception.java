
package ws.client.partner;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2-hudson-740-
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "ReservationNotFoundException", targetNamespace = "http://ws.ejb/")
public class ReservationNotFoundException_Exception
    extends java.lang.Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private ReservationNotFoundException faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public ReservationNotFoundException_Exception(String message, ReservationNotFoundException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public ReservationNotFoundException_Exception(String message, ReservationNotFoundException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: ws.client.partner.ReservationNotFoundException
     */
    public ReservationNotFoundException getFaultInfo() {
        return faultInfo;
    }

}
