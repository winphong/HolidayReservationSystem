
package ws.client.partner;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for roomEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="roomEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="currentReservation" type="{http://ws.ejb/}reservationEntity" minOccurs="0"/>
 *         &lt;element name="guest" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isDisabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="isReady" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="nextReservation" type="{http://ws.ejb/}reservationEntity" minOccurs="0"/>
 *         &lt;element name="roomNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="roomStatus" type="{http://ws.ejb/}roomStatus" minOccurs="0"/>
 *         &lt;element name="roomType" type="{http://ws.ejb/}roomTypeEntity" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "roomEntity", propOrder = {
    "currentReservation",
    "guest",
    "isDisabled",
    "isReady",
    "nextReservation",
    "roomNumber",
    "roomStatus",
    "roomType"
})
public class RoomEntity {

    protected ReservationEntity currentReservation;
    protected String guest;
    protected Boolean isDisabled;
    protected Boolean isReady;
    protected ReservationEntity nextReservation;
    protected String roomNumber;
    protected RoomStatus roomStatus;
    protected RoomTypeEntity roomType;

    /**
     * Gets the value of the currentReservation property.
     * 
     * @return
     *     possible object is
     *     {@link ReservationEntity }
     *     
     */
    public ReservationEntity getCurrentReservation() {
        return currentReservation;
    }

    /**
     * Sets the value of the currentReservation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReservationEntity }
     *     
     */
    public void setCurrentReservation(ReservationEntity value) {
        this.currentReservation = value;
    }

    /**
     * Gets the value of the guest property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGuest() {
        return guest;
    }

    /**
     * Sets the value of the guest property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGuest(String value) {
        this.guest = value;
    }

    /**
     * Gets the value of the isDisabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDisabled() {
        return isDisabled;
    }

    /**
     * Sets the value of the isDisabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDisabled(Boolean value) {
        this.isDisabled = value;
    }

    /**
     * Gets the value of the isReady property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsReady() {
        return isReady;
    }

    /**
     * Sets the value of the isReady property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsReady(Boolean value) {
        this.isReady = value;
    }

    /**
     * Gets the value of the nextReservation property.
     * 
     * @return
     *     possible object is
     *     {@link ReservationEntity }
     *     
     */
    public ReservationEntity getNextReservation() {
        return nextReservation;
    }

    /**
     * Sets the value of the nextReservation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReservationEntity }
     *     
     */
    public void setNextReservation(ReservationEntity value) {
        this.nextReservation = value;
    }

    /**
     * Gets the value of the roomNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     * Sets the value of the roomNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoomNumber(String value) {
        this.roomNumber = value;
    }

    /**
     * Gets the value of the roomStatus property.
     * 
     * @return
     *     possible object is
     *     {@link RoomStatus }
     *     
     */
    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    /**
     * Sets the value of the roomStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoomStatus }
     *     
     */
    public void setRoomStatus(RoomStatus value) {
        this.roomStatus = value;
    }

    /**
     * Gets the value of the roomType property.
     * 
     * @return
     *     possible object is
     *     {@link RoomTypeEntity }
     *     
     */
    public RoomTypeEntity getRoomType() {
        return roomType;
    }

    /**
     * Sets the value of the roomType property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoomTypeEntity }
     *     
     */
    public void setRoomType(RoomTypeEntity value) {
        this.roomType = value;
    }

}
