
package ws.client.partner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for reservationEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="reservationEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bookingDate" type="{http://ws.ejb/}date" minOccurs="0"/>
 *         &lt;element name="endDate" type="{http://ws.ejb/}date" minOccurs="0"/>
 *         &lt;element name="isAllocated" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="isCheckedIn" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="reservationId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="reservationLineItemEntities" type="{http://ws.ejb/}reservationLineItemEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="rooms" type="{http://ws.ejb/}roomEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="startDate" type="{http://ws.ejb/}date" minOccurs="0"/>
 *         &lt;element name="totalAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reservationEntity", propOrder = {
    "bookingDate",
    "endDate",
    "isAllocated",
    "isCheckedIn",
    "reservationId",
    "reservationLineItemEntities",
    "rooms",
    "startDate",
    "totalAmount"
})
@XmlSeeAlso({
    PartnerReservationEntity.class
})
public abstract class ReservationEntity {

    protected Date bookingDate;
    protected Date endDate;
    protected Boolean isAllocated;
    protected Boolean isCheckedIn;
    protected Long reservationId;
    @XmlElement(nillable = true)
    protected List<ReservationLineItemEntity> reservationLineItemEntities;
    @XmlElement(nillable = true)
    protected List<RoomEntity> rooms;
    protected Date startDate;
    protected BigDecimal totalAmount;

    /**
     * Gets the value of the bookingDate property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getBookingDate() {
        return bookingDate;
    }

    /**
     * Sets the value of the bookingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setBookingDate(Date value) {
        this.bookingDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setEndDate(Date value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the isAllocated property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsAllocated() {
        return isAllocated;
    }

    /**
     * Sets the value of the isAllocated property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsAllocated(Boolean value) {
        this.isAllocated = value;
    }

    /**
     * Gets the value of the isCheckedIn property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsCheckedIn() {
        return isCheckedIn;
    }

    /**
     * Sets the value of the isCheckedIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsCheckedIn(Boolean value) {
        this.isCheckedIn = value;
    }

    /**
     * Gets the value of the reservationId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getReservationId() {
        return reservationId;
    }

    /**
     * Sets the value of the reservationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setReservationId(Long value) {
        this.reservationId = value;
    }

    /**
     * Gets the value of the reservationLineItemEntities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reservationLineItemEntities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReservationLineItemEntities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReservationLineItemEntity }
     * 
     * 
     */
    public List<ReservationLineItemEntity> getReservationLineItemEntities() {
        if (reservationLineItemEntities == null) {
            reservationLineItemEntities = new ArrayList<ReservationLineItemEntity>();
        }
        return this.reservationLineItemEntities;
    }

    /**
     * Gets the value of the rooms property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rooms property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRooms().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RoomEntity }
     * 
     * 
     */
    public List<RoomEntity> getRooms() {
        if (rooms == null) {
            rooms = new ArrayList<RoomEntity>();
        }
        return this.rooms;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setStartDate(Date value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the totalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the value of the totalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalAmount(BigDecimal value) {
        this.totalAmount = value;
    }

}
