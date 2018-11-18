
package ws.client.partner;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for reservationLineItemEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="reservationLineItemEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="isAllocated" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="isUpgraded" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="numOfFailureUpgrade" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numOfRoomBooked" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numOfSuccesfulNormalAllocation" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numOfSuccesfulUpgrade" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="reservation" type="{http://ws.ejb/}reservationEntity" minOccurs="0"/>
 *         &lt;element name="reservationLineItemId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="roomType" type="{http://ws.ejb/}roomTypeEntity" minOccurs="0"/>
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
@XmlType(name = "reservationLineItemEntity", propOrder = {
    "isAllocated",
    "isUpgraded",
    "numOfFailureUpgrade",
    "numOfRoomBooked",
    "numOfSuccesfulNormalAllocation",
    "numOfSuccesfulUpgrade",
    "reservation",
    "reservationLineItemId",
    "roomType",
    "totalAmount"
})
public class ReservationLineItemEntity {

    protected Boolean isAllocated;
    protected Boolean isUpgraded;
    protected Integer numOfFailureUpgrade;
    protected Integer numOfRoomBooked;
    protected Integer numOfSuccesfulNormalAllocation;
    protected Integer numOfSuccesfulUpgrade;
    protected ReservationEntity reservation;
    protected Long reservationLineItemId;
    protected RoomTypeEntity roomType;
    protected BigDecimal totalAmount;

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
     * Gets the value of the isUpgraded property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsUpgraded() {
        return isUpgraded;
    }

    /**
     * Sets the value of the isUpgraded property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsUpgraded(Boolean value) {
        this.isUpgraded = value;
    }

    /**
     * Gets the value of the numOfFailureUpgrade property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumOfFailureUpgrade() {
        return numOfFailureUpgrade;
    }

    /**
     * Sets the value of the numOfFailureUpgrade property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumOfFailureUpgrade(Integer value) {
        this.numOfFailureUpgrade = value;
    }

    /**
     * Gets the value of the numOfRoomBooked property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumOfRoomBooked() {
        return numOfRoomBooked;
    }

    /**
     * Sets the value of the numOfRoomBooked property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumOfRoomBooked(Integer value) {
        this.numOfRoomBooked = value;
    }

    /**
     * Gets the value of the numOfSuccesfulNormalAllocation property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumOfSuccesfulNormalAllocation() {
        return numOfSuccesfulNormalAllocation;
    }

    /**
     * Sets the value of the numOfSuccesfulNormalAllocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumOfSuccesfulNormalAllocation(Integer value) {
        this.numOfSuccesfulNormalAllocation = value;
    }

    /**
     * Gets the value of the numOfSuccesfulUpgrade property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumOfSuccesfulUpgrade() {
        return numOfSuccesfulUpgrade;
    }

    /**
     * Sets the value of the numOfSuccesfulUpgrade property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumOfSuccesfulUpgrade(Integer value) {
        this.numOfSuccesfulUpgrade = value;
    }

    /**
     * Gets the value of the reservation property.
     * 
     * @return
     *     possible object is
     *     {@link ReservationEntity }
     *     
     */
    public ReservationEntity getReservation() {
        return reservation;
    }

    /**
     * Sets the value of the reservation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReservationEntity }
     *     
     */
    public void setReservation(ReservationEntity value) {
        this.reservation = value;
    }

    /**
     * Gets the value of the reservationLineItemId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getReservationLineItemId() {
        return reservationLineItemId;
    }

    /**
     * Sets the value of the reservationLineItemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setReservationLineItemId(Long value) {
        this.reservationLineItemId = value;
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
