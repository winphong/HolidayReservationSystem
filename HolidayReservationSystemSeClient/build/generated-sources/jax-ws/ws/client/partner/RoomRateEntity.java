
package ws.client.partner;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for roomRateEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="roomRateEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="isDisabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ratePerNight" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="roomRateId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="roomType" type="{http://ws.ejb/}roomTypeEntity" minOccurs="0"/>
 *         &lt;element name="validFrom" type="{http://ws.ejb/}date" minOccurs="0"/>
 *         &lt;element name="validTill" type="{http://ws.ejb/}date" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "roomRateEntity", propOrder = {
    "isDisabled",
    "name",
    "ratePerNight",
    "roomRateId",
    "roomType",
    "validFrom",
    "validTill"
})
public class RoomRateEntity {

    protected Boolean isDisabled;
    protected String name;
    protected BigDecimal ratePerNight;
    protected Long roomRateId;
    protected RoomTypeEntity roomType;
    protected Date validFrom;
    protected Date validTill;

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
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the ratePerNight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRatePerNight() {
        return ratePerNight;
    }

    /**
     * Sets the value of the ratePerNight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRatePerNight(BigDecimal value) {
        this.ratePerNight = value;
    }

    /**
     * Gets the value of the roomRateId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRoomRateId() {
        return roomRateId;
    }

    /**
     * Sets the value of the roomRateId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRoomRateId(Long value) {
        this.roomRateId = value;
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
     * Gets the value of the validFrom property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getValidFrom() {
        return validFrom;
    }

    /**
     * Sets the value of the validFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setValidFrom(Date value) {
        this.validFrom = value;
    }

    /**
     * Gets the value of the validTill property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getValidTill() {
        return validTill;
    }

    /**
     * Sets the value of the validTill property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setValidTill(Date value) {
        this.validTill = value;
    }

}
