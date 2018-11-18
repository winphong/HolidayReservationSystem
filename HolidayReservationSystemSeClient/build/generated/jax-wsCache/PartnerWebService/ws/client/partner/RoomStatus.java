
package ws.client.partner;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for roomStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="roomStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="VACANT"/>
 *     &lt;enumeration value="OCCUPIED"/>
 *     &lt;enumeration value="MAINTENANCE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "roomStatus")
@XmlEnum
public enum RoomStatus {

    VACANT,
    OCCUPIED,
    MAINTENANCE;

    public String value() {
        return name();
    }

    public static RoomStatus fromValue(String v) {
        return valueOf(v);
    }

}
