
package reservation;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the reservation package. 
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

    private final static QName _GetMedicalSupplies_QNAME = new QName("http://reservation/", "getMedicalSupplies");
    private final static QName _GetReservationStatus_QNAME = new QName("http://reservation/", "getReservationStatus");
    private final static QName _MakeReservationResponse_QNAME = new QName("http://reservation/", "makeReservationResponse");
    private final static QName _MakeOrder_QNAME = new QName("http://reservation/", "makeOrder");
    private final static QName _CancelReservation_QNAME = new QName("http://reservation/", "cancelReservation");
    private final static QName _GetOrderStatus_QNAME = new QName("http://reservation/", "getOrderStatus");
    private final static QName _MakeOrderResponse_QNAME = new QName("http://reservation/", "makeOrderResponse");
    private final static QName _GetReservationStatusResponse_QNAME = new QName("http://reservation/", "getReservationStatusResponse");
    private final static QName _GetOrderStatusResponse_QNAME = new QName("http://reservation/", "getOrderStatusResponse");
    private final static QName _MakeReservation_QNAME = new QName("http://reservation/", "makeReservation");
    private final static QName _GetManufacturers_QNAME = new QName("http://reservation/", "getManufacturers");
    private final static QName _CancelReservationResponse_QNAME = new QName("http://reservation/", "cancelReservationResponse");
    private final static QName _CancelOrder_QNAME = new QName("http://reservation/", "cancelOrder");
    private final static QName _GetManufacturersResponse_QNAME = new QName("http://reservation/", "getManufacturersResponse");
    private final static QName _CancelOrderResponse_QNAME = new QName("http://reservation/", "cancelOrderResponse");
    private final static QName _GetMedicalSuppliesResponse_QNAME = new QName("http://reservation/", "getMedicalSuppliesResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: reservation
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
     * Create an instance of {@link CancelOrder }
     * 
     */
    public CancelOrder createCancelOrder() {
        return new CancelOrder();
    }

    /**
     * Create an instance of {@link GetManufacturersResponse }
     * 
     */
    public GetManufacturersResponse createGetManufacturersResponse() {
        return new GetManufacturersResponse();
    }

    /**
     * Create an instance of {@link CancelReservationResponse }
     * 
     */
    public CancelReservationResponse createCancelReservationResponse() {
        return new CancelReservationResponse();
    }

    /**
     * Create an instance of {@link GetManufacturers }
     * 
     */
    public GetManufacturers createGetManufacturers() {
        return new GetManufacturers();
    }

    /**
     * Create an instance of {@link GetOrderStatusResponse }
     * 
     */
    public GetOrderStatusResponse createGetOrderStatusResponse() {
        return new GetOrderStatusResponse();
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
     * Create an instance of {@link GetReservationStatusResponse }
     * 
     */
    public GetReservationStatusResponse createGetReservationStatusResponse() {
        return new GetReservationStatusResponse();
    }

    /**
     * Create an instance of {@link GetOrderStatus }
     * 
     */
    public GetOrderStatus createGetOrderStatus() {
        return new GetOrderStatus();
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
     * Create an instance of {@link GetReservationStatus }
     * 
     */
    public GetReservationStatus createGetReservationStatus() {
        return new GetReservationStatus();
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
     * Create an instance of {@link Manufacturer }
     * 
     */
    public Manufacturer createManufacturer() {
        return new Manufacturer();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMedicalSupplies }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://reservation/", name = "getMedicalSupplies")
    public JAXBElement<GetMedicalSupplies> createGetMedicalSupplies(GetMedicalSupplies value) {
        return new JAXBElement<GetMedicalSupplies>(_GetMedicalSupplies_QNAME, GetMedicalSupplies.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReservationStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://reservation/", name = "getReservationStatus")
    public JAXBElement<GetReservationStatus> createGetReservationStatus(GetReservationStatus value) {
        return new JAXBElement<GetReservationStatus>(_GetReservationStatus_QNAME, GetReservationStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeReservationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://reservation/", name = "makeReservationResponse")
    public JAXBElement<MakeReservationResponse> createMakeReservationResponse(MakeReservationResponse value) {
        return new JAXBElement<MakeReservationResponse>(_MakeReservationResponse_QNAME, MakeReservationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeOrder }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://reservation/", name = "makeOrder")
    public JAXBElement<MakeOrder> createMakeOrder(MakeOrder value) {
        return new JAXBElement<MakeOrder>(_MakeOrder_QNAME, MakeOrder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelReservation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://reservation/", name = "cancelReservation")
    public JAXBElement<CancelReservation> createCancelReservation(CancelReservation value) {
        return new JAXBElement<CancelReservation>(_CancelReservation_QNAME, CancelReservation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrderStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://reservation/", name = "getOrderStatus")
    public JAXBElement<GetOrderStatus> createGetOrderStatus(GetOrderStatus value) {
        return new JAXBElement<GetOrderStatus>(_GetOrderStatus_QNAME, GetOrderStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeOrderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://reservation/", name = "makeOrderResponse")
    public JAXBElement<MakeOrderResponse> createMakeOrderResponse(MakeOrderResponse value) {
        return new JAXBElement<MakeOrderResponse>(_MakeOrderResponse_QNAME, MakeOrderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReservationStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://reservation/", name = "getReservationStatusResponse")
    public JAXBElement<GetReservationStatusResponse> createGetReservationStatusResponse(GetReservationStatusResponse value) {
        return new JAXBElement<GetReservationStatusResponse>(_GetReservationStatusResponse_QNAME, GetReservationStatusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrderStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://reservation/", name = "getOrderStatusResponse")
    public JAXBElement<GetOrderStatusResponse> createGetOrderStatusResponse(GetOrderStatusResponse value) {
        return new JAXBElement<GetOrderStatusResponse>(_GetOrderStatusResponse_QNAME, GetOrderStatusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeReservation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://reservation/", name = "makeReservation")
    public JAXBElement<MakeReservation> createMakeReservation(MakeReservation value) {
        return new JAXBElement<MakeReservation>(_MakeReservation_QNAME, MakeReservation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetManufacturers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://reservation/", name = "getManufacturers")
    public JAXBElement<GetManufacturers> createGetManufacturers(GetManufacturers value) {
        return new JAXBElement<GetManufacturers>(_GetManufacturers_QNAME, GetManufacturers.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelReservationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://reservation/", name = "cancelReservationResponse")
    public JAXBElement<CancelReservationResponse> createCancelReservationResponse(CancelReservationResponse value) {
        return new JAXBElement<CancelReservationResponse>(_CancelReservationResponse_QNAME, CancelReservationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelOrder }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://reservation/", name = "cancelOrder")
    public JAXBElement<CancelOrder> createCancelOrder(CancelOrder value) {
        return new JAXBElement<CancelOrder>(_CancelOrder_QNAME, CancelOrder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetManufacturersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://reservation/", name = "getManufacturersResponse")
    public JAXBElement<GetManufacturersResponse> createGetManufacturersResponse(GetManufacturersResponse value) {
        return new JAXBElement<GetManufacturersResponse>(_GetManufacturersResponse_QNAME, GetManufacturersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelOrderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://reservation/", name = "cancelOrderResponse")
    public JAXBElement<CancelOrderResponse> createCancelOrderResponse(CancelOrderResponse value) {
        return new JAXBElement<CancelOrderResponse>(_CancelOrderResponse_QNAME, CancelOrderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMedicalSuppliesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://reservation/", name = "getMedicalSuppliesResponse")
    public JAXBElement<GetMedicalSuppliesResponse> createGetMedicalSuppliesResponse(GetMedicalSuppliesResponse value) {
        return new JAXBElement<GetMedicalSuppliesResponse>(_GetMedicalSuppliesResponse_QNAME, GetMedicalSuppliesResponse.class, null, value);
    }

}
