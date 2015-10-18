
package reservation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="manufacturer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="medicalSupply" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="quantitys" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "manufacturer",
    "medicalSupply",
    "quantitys"
})
public class MakeReservation {

    protected String manufacturer;
    protected String medicalSupply;
    protected int quantitys;

    /**
     * Gets the value of the manufacturer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Sets the value of the manufacturer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManufacturer(String value) {
        this.manufacturer = value;
    }

    /**
     * Gets the value of the medicalSupply property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedicalSupply() {
        return medicalSupply;
    }

    /**
     * Sets the value of the medicalSupply property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedicalSupply(String value) {
        this.medicalSupply = value;
    }

    /**
     * Gets the value of the quantitys property.
     * 
     */
    public int getQuantitys() {
        return quantitys;
    }

    /**
     * Sets the value of the quantitys property.
     * 
     */
    public void setQuantitys(int value) {
        this.quantitys = value;
    }

}
