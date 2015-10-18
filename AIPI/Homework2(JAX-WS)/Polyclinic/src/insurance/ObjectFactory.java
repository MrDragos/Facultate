
package insurance;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the insurance package. 
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

    private final static QName _RedeemMedicalServiceResponse_QNAME = new QName("http://insurance/", "redeemMedicalServiceResponse");
    private final static QName _RedeemMedicalService_QNAME = new QName("http://insurance/", "redeemMedicalService");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: insurance
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RedeemMedicalServiceResponse }
     * 
     */
    public RedeemMedicalServiceResponse createRedeemMedicalServiceResponse() {
        return new RedeemMedicalServiceResponse();
    }

    /**
     * Create an instance of {@link RedeemMedicalService }
     * 
     */
    public RedeemMedicalService createRedeemMedicalService() {
        return new RedeemMedicalService();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RedeemMedicalServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://insurance/", name = "redeemMedicalServiceResponse")
    public JAXBElement<RedeemMedicalServiceResponse> createRedeemMedicalServiceResponse(RedeemMedicalServiceResponse value) {
        return new JAXBElement<RedeemMedicalServiceResponse>(_RedeemMedicalServiceResponse_QNAME, RedeemMedicalServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RedeemMedicalService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://insurance/", name = "redeemMedicalService")
    public JAXBElement<RedeemMedicalService> createRedeemMedicalService(RedeemMedicalService value) {
        return new JAXBElement<RedeemMedicalService>(_RedeemMedicalService_QNAME, RedeemMedicalService.class, null, value);
    }

}
