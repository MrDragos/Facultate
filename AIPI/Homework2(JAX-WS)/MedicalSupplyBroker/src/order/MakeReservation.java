
package order;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for makeReservation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="makeReservation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="reservationId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="manufacturer_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="medicalSupplyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="medicalSupplyQuantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "makeReservation", propOrder = {
    "reservationId",
    "manufacturerId",
    "medicalSupplyName",
    "medicalSupplyQuantity"
})
public class MakeReservation {

    protected int reservationId;
    @XmlElement(name = "manufacturer_id")
    protected String manufacturerId;
    protected String medicalSupplyName;
    protected int medicalSupplyQuantity;

    /**
     * Gets the value of the reservationId property.
     * 
     */
    public int getReservationId() {
        return reservationId;
    }

    /**
     * Sets the value of the reservationId property.
     * 
     */
    public void setReservationId(int value) {
        this.reservationId = value;
    }

    /**
     * Gets the value of the manufacturerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManufacturerId() {
        return manufacturerId;
    }

    /**
     * Sets the value of the manufacturerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManufacturerId(String value) {
        this.manufacturerId = value;
    }

    /**
     * Gets the value of the medicalSupplyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedicalSupplyName() {
        return medicalSupplyName;
    }

    /**
     * Sets the value of the medicalSupplyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedicalSupplyName(String value) {
        this.medicalSupplyName = value;
    }

    /**
     * Gets the value of the medicalSupplyQuantity property.
     * 
     */
    public int getMedicalSupplyQuantity() {
        return medicalSupplyQuantity;
    }

    /**
     * Sets the value of the medicalSupplyQuantity property.
     * 
     */
    public void setMedicalSupplyQuantity(int value) {
        this.medicalSupplyQuantity = value;
    }

}
