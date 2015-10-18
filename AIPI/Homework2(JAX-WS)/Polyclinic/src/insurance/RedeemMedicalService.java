
package insurance;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for redeemMedicalService complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="redeemMedicalService">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="physicianId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="patientId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="medicalServiceId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "redeemMedicalService", propOrder = {
    "physicianId",
    "patientId",
    "date",
    "medicalServiceId",
    "price"
})
public class RedeemMedicalService {

    protected int physicianId;
    protected int patientId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date;
    protected int medicalServiceId;
    protected double price;

    /**
     * Gets the value of the physicianId property.
     * 
     */
    public int getPhysicianId() {
        return physicianId;
    }

    /**
     * Sets the value of the physicianId property.
     * 
     */
    public void setPhysicianId(int value) {
        this.physicianId = value;
    }

    /**
     * Gets the value of the patientId property.
     * 
     */
    public int getPatientId() {
        return patientId;
    }

    /**
     * Sets the value of the patientId property.
     * 
     */
    public void setPatientId(int value) {
        this.patientId = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Gets the value of the medicalServiceId property.
     * 
     */
    public int getMedicalServiceId() {
        return medicalServiceId;
    }

    /**
     * Sets the value of the medicalServiceId property.
     * 
     */
    public void setMedicalServiceId(int value) {
        this.medicalServiceId = value;
    }

    /**
     * Gets the value of the price property.
     * 
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     */
    public void setPrice(double value) {
        this.price = value;
    }

}
