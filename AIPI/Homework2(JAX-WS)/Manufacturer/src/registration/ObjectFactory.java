
package registration;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the registration package. 
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

    private final static QName _UnregisterManufacturerResponse_QNAME = new QName("http://registration/", "unregisterManufacturerResponse");
    private final static QName _RegisterManufacturerResponse_QNAME = new QName("http://registration/", "registerManufacturerResponse");
    private final static QName _UnregisterManufacturer_QNAME = new QName("http://registration/", "unregisterManufacturer");
    private final static QName _RegisterManufacturer_QNAME = new QName("http://registration/", "registerManufacturer");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: registration
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UnregisterManufacturerResponse }
     * 
     */
    public UnregisterManufacturerResponse createUnregisterManufacturerResponse() {
        return new UnregisterManufacturerResponse();
    }

    /**
     * Create an instance of {@link RegisterManufacturerResponse }
     * 
     */
    public RegisterManufacturerResponse createRegisterManufacturerResponse() {
        return new RegisterManufacturerResponse();
    }

    /**
     * Create an instance of {@link UnregisterManufacturer }
     * 
     */
    public UnregisterManufacturer createUnregisterManufacturer() {
        return new UnregisterManufacturer();
    }

    /**
     * Create an instance of {@link RegisterManufacturer }
     * 
     */
    public RegisterManufacturer createRegisterManufacturer() {
        return new RegisterManufacturer();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnregisterManufacturerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://registration/", name = "unregisterManufacturerResponse")
    public JAXBElement<UnregisterManufacturerResponse> createUnregisterManufacturerResponse(UnregisterManufacturerResponse value) {
        return new JAXBElement<UnregisterManufacturerResponse>(_UnregisterManufacturerResponse_QNAME, UnregisterManufacturerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterManufacturerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://registration/", name = "registerManufacturerResponse")
    public JAXBElement<RegisterManufacturerResponse> createRegisterManufacturerResponse(RegisterManufacturerResponse value) {
        return new JAXBElement<RegisterManufacturerResponse>(_RegisterManufacturerResponse_QNAME, RegisterManufacturerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnregisterManufacturer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://registration/", name = "unregisterManufacturer")
    public JAXBElement<UnregisterManufacturer> createUnregisterManufacturer(UnregisterManufacturer value) {
        return new JAXBElement<UnregisterManufacturer>(_UnregisterManufacturer_QNAME, UnregisterManufacturer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterManufacturer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://registration/", name = "registerManufacturer")
    public JAXBElement<RegisterManufacturer> createRegisterManufacturer(RegisterManufacturer value) {
        return new JAXBElement<RegisterManufacturer>(_RegisterManufacturer_QNAME, RegisterManufacturer.class, null, value);
    }

}
