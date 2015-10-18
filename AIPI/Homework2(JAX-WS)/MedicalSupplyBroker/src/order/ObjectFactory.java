
package order;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the order package. 
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

    private final static QName _CancelReservationResponse_QNAME = new QName("http://order/", "cancelReservationResponse");
    private final static QName _GetDescription_QNAME = new QName("http://order/", "getDescription");
    private final static QName _GetName_QNAME = new QName("http://order/", "getName");
    private final static QName _CancelOrderResponse_QNAME = new QName("http://order/", "cancelOrderResponse");
    private final static QName _GetMedicalSuppliesResponse_QNAME = new QName("http://order/", "getMedicalSuppliesResponse");
    private final static QName _GetNameResponse_QNAME = new QName("http://order/", "getNameResponse");
    private final static QName _GetDescriptionResponse_QNAME = new QName("http://order/", "getDescriptionResponse");
    private final static QName _CancelOrder_QNAME = new QName("http://order/", "cancelOrder");
    private final static QName _MakeOrder_QNAME = new QName("http://order/", "makeOrder");
    private final static QName _MakeReservationResponse_QNAME = new QName("http://order/", "makeReservationResponse");
    private final static QName _GetMedicalSupplies_QNAME = new QName("http://order/", "getMedicalSupplies");
    private final static QName _MakeOrderResponse_QNAME = new QName("http://order/", "makeOrderResponse");
    private final static QName _MakeReservation_QNAME = new QName("http://order/", "makeReservation");
    private final static QName _CancelReservation_QNAME = new QName("http://order/", "cancelReservation");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: order
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CancelOrderResponse }
     * 
     */
    public CancelOrderResponse createCancelOrderResponse() {
        return new CancelOrderResponse();
    }

    /**
     * Create an instance of {@link GetMedicalSuppliesResponse }
     * 
     */
    public GetMedicalSuppliesResponse createGetMedicalSuppliesResponse() {
        return new GetMedicalSuppliesResponse();
    }

    /**
     * Create an instance of {@link GetName }
     * 
     */
    public GetName createGetName() {
        return new GetName();
    }

    /**
     * Create an instance of {@link CancelOrder }
     * 
     */
    public CancelOrder createCancelOrder() {
        return new CancelOrder();
    }

    /**
     * Create an instance of {@link GetDescriptionResponse }
     * 
     */
    public GetDescriptionResponse createGetDescriptionResponse() {
        return new GetDescriptionResponse();
    }

    /**
     * Create an instance of {@link GetNameResponse }
     * 
     */
    public GetNameResponse createGetNameResponse() {
        return new GetNameResponse();
    }

    /**
     * Create an instance of {@link CancelReservationResponse }
     * 
     */
    public CancelReservationResponse createCancelReservationResponse() {
        return new CancelReservationResponse();
    }

    /**
     * Create an instance of {@link GetDescription }
     * 
     */
    public GetDescription createGetDescription() {
        return new GetDescription();
    }

    /**
     * Create an instance of {@link MakeReservation }
     * 
     */
    public MakeReservation createMakeReservation() {
        return new MakeReservation();
    }

    /**
     * Create an instance of {@link MakeOrderResponse }
     * 
     */
    public MakeOrderResponse createMakeOrderResponse() {
        return new MakeOrderResponse();
    }

    /**
     * Create an instance of {@link CancelReservation }
     * 
     */
    public CancelReservation createCancelReservation() {
        return new CancelReservation();
    }

    /**
     * Create an instance of {@link MakeOrder }
     * 
     */
    public MakeOrder createMakeOrder() {
        return new MakeOrder();
    }

    /**
     * Create an instance of {@link GetMedicalSupplies }
     * 
     */
    public GetMedicalSupplies createGetMedicalSupplies() {
        return new GetMedicalSupplies();
    }

    /**
     * Create an instance of {@link MakeReservationResponse }
     * 
     */
    public MakeReservationResponse createMakeReservationResponse() {
        return new MakeReservationResponse();
    }

    /**
     * Create an instance of {@link MedicalSupply }
     * 
     */
    public MedicalSupply createMedicalSupply() {
        return new MedicalSupply();
    }

    /**
     * Create an instance of {@link Offer }
     * 
     */
    public Offer createOffer() {
        return new Offer();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelReservationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://order/", name = "cancelReservationResponse")
    public JAXBElement<CancelReservationResponse> createCancelReservationResponse(CancelReservationResponse value) {
        return new JAXBElement<CancelReservationResponse>(_CancelReservationResponse_QNAME, CancelReservationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDescription }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://order/", name = "getDescription")
    public JAXBElement<GetDescription> createGetDescription(GetDescription value) {
        return new JAXBElement<GetDescription>(_GetDescription_QNAME, GetDescription.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://order/", name = "getName")
    public JAXBElement<GetName> createGetName(GetName value) {
        return new JAXBElement<GetName>(_GetName_QNAME, GetName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelOrderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://order/", name = "cancelOrderResponse")
    public JAXBElement<CancelOrderResponse> createCancelOrderResponse(CancelOrderResponse value) {
        return new JAXBElement<CancelOrderResponse>(_CancelOrderResponse_QNAME, CancelOrderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMedicalSuppliesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://order/", name = "getMedicalSuppliesResponse")
    public JAXBElement<GetMedicalSuppliesResponse> createGetMedicalSuppliesResponse(GetMedicalSuppliesResponse value) {
        return new JAXBElement<GetMedicalSuppliesResponse>(_GetMedicalSuppliesResponse_QNAME, GetMedicalSuppliesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://order/", name = "getNameResponse")
    public JAXBElement<GetNameResponse> createGetNameResponse(GetNameResponse value) {
        return new JAXBElement<GetNameResponse>(_GetNameResponse_QNAME, GetNameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDescriptionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://order/", name = "getDescriptionResponse")
    public JAXBElement<GetDescriptionResponse> createGetDescriptionResponse(GetDescriptionResponse value) {
        return new JAXBElement<GetDescriptionResponse>(_GetDescriptionResponse_QNAME, GetDescriptionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelOrder }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://order/", name = "cancelOrder")
    public JAXBElement<CancelOrder> createCancelOrder(CancelOrder value) {
        return new JAXBElement<CancelOrder>(_CancelOrder_QNAME, CancelOrder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeOrder }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://order/", name = "makeOrder")
    public JAXBElement<MakeOrder> createMakeOrder(MakeOrder value) {
        return new JAXBElement<MakeOrder>(_MakeOrder_QNAME, MakeOrder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeReservationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://order/", name = "makeReservationResponse")
    public JAXBElement<MakeReservationResponse> createMakeReservationResponse(MakeReservationResponse value) {
        return new JAXBElement<MakeReservationResponse>(_MakeReservationResponse_QNAME, MakeReservationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMedicalSupplies }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://order/", name = "getMedicalSupplies")
    public JAXBElement<GetMedicalSupplies> createGetMedicalSupplies(GetMedicalSupplies value) {
        return new JAXBElement<GetMedicalSupplies>(_GetMedicalSupplies_QNAME, GetMedicalSupplies.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeOrderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://order/", name = "makeOrderResponse")
    public JAXBElement<MakeOrderResponse> createMakeOrderResponse(MakeOrderResponse value) {
        return new JAXBElement<MakeOrderResponse>(_MakeOrderResponse_QNAME, MakeOrderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeReservation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://order/", name = "makeReservation")
    public JAXBElement<MakeReservation> createMakeReservation(MakeReservation value) {
        return new JAXBElement<MakeReservation>(_MakeReservation_QNAME, MakeReservation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelReservation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://order/", name = "cancelReservation")
    public JAXBElement<CancelReservation> createCancelReservation(CancelReservation value) {
        return new JAXBElement<CancelReservation>(_CancelReservation_QNAME, CancelReservation.class, null, value);
    }

}
