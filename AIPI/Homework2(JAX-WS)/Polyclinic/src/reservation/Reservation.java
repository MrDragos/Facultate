
package reservation;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "Reservation", targetNamespace = "http://reservation/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Reservation {


    /**
     * 
     * @return
     *     returns java.util.List<reservation.Manufacturer>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getManufacturers", targetNamespace = "http://reservation/", className = "reservation.GetManufacturers")
    @ResponseWrapper(localName = "getManufacturersResponse", targetNamespace = "http://reservation/", className = "reservation.GetManufacturersResponse")
    @Action(input = "http://reservation/Reservation/getManufacturersRequest", output = "http://reservation/Reservation/getManufacturersResponse")
    public List<Manufacturer> getManufacturers();

    /**
     * 
     * @param reservationId
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "cancelReservation", targetNamespace = "http://reservation/", className = "reservation.CancelReservation")
    @ResponseWrapper(localName = "cancelReservationResponse", targetNamespace = "http://reservation/", className = "reservation.CancelReservationResponse")
    @Action(input = "http://reservation/Reservation/cancelReservationRequest", output = "http://reservation/Reservation/cancelReservationResponse")
    public boolean cancelReservation(
        @WebParam(name = "reservationId", targetNamespace = "")
        int reservationId);

    /**
     * 
     * @param manufacturer
     * @return
     *     returns java.util.List<reservation.MedicalSupply>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getMedicalSupplies", targetNamespace = "http://reservation/", className = "reservation.GetMedicalSupplies")
    @ResponseWrapper(localName = "getMedicalSuppliesResponse", targetNamespace = "http://reservation/", className = "reservation.GetMedicalSuppliesResponse")
    @Action(input = "http://reservation/Reservation/getMedicalSuppliesRequest", output = "http://reservation/Reservation/getMedicalSuppliesResponse")
    public List<MedicalSupply> getMedicalSupplies(
        @WebParam(name = "manufacturer", targetNamespace = "")
        Manufacturer manufacturer);

    /**
     * 
     * @param reservationId
     * @return
     *     returns reservation.Offer
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getReservationStatus", targetNamespace = "http://reservation/", className = "reservation.GetReservationStatus")
    @ResponseWrapper(localName = "getReservationStatusResponse", targetNamespace = "http://reservation/", className = "reservation.GetReservationStatusResponse")
    @Action(input = "http://reservation/Reservation/getReservationStatusRequest", output = "http://reservation/Reservation/getReservationStatusResponse")
    public Offer getReservationStatus(
        @WebParam(name = "reservationId", targetNamespace = "")
        int reservationId);

    /**
     * 
     * @param reservationId
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "makeOrder", targetNamespace = "http://reservation/", className = "reservation.MakeOrder")
    @ResponseWrapper(localName = "makeOrderResponse", targetNamespace = "http://reservation/", className = "reservation.MakeOrderResponse")
    @Action(input = "http://reservation/Reservation/makeOrderRequest", output = "http://reservation/Reservation/makeOrderResponse")
    public boolean makeOrder(
        @WebParam(name = "reservationId", targetNamespace = "")
        int reservationId);

    /**
     * 
     * @param reservationId
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getOrderStatus", targetNamespace = "http://reservation/", className = "reservation.GetOrderStatus")
    @ResponseWrapper(localName = "getOrderStatusResponse", targetNamespace = "http://reservation/", className = "reservation.GetOrderStatusResponse")
    @Action(input = "http://reservation/Reservation/getOrderStatusRequest", output = "http://reservation/Reservation/getOrderStatusResponse")
    public int getOrderStatus(
        @WebParam(name = "reservationId", targetNamespace = "")
        int reservationId);

    /**
     * 
     * @param reservationId
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "cancelOrder", targetNamespace = "http://reservation/", className = "reservation.CancelOrder")
    @ResponseWrapper(localName = "cancelOrderResponse", targetNamespace = "http://reservation/", className = "reservation.CancelOrderResponse")
    @Action(input = "http://reservation/Reservation/cancelOrderRequest", output = "http://reservation/Reservation/cancelOrderResponse")
    public boolean cancelOrder(
        @WebParam(name = "reservationId", targetNamespace = "")
        int reservationId);

    /**
     * 
     * @param medicalSupply
     * @param quantitys
     * @param manufacturer
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "makeReservation", targetNamespace = "http://reservation/", className = "reservation.MakeReservation")
    @ResponseWrapper(localName = "makeReservationResponse", targetNamespace = "http://reservation/", className = "reservation.MakeReservationResponse")
    @Action(input = "http://reservation/Reservation/makeReservationRequest", output = "http://reservation/Reservation/makeReservationResponse")
    public int makeReservation(
        @WebParam(name = "manufacturer", targetNamespace = "")
        String manufacturer,
        @WebParam(name = "medicalSupply", targetNamespace = "")
        String medicalSupply,
        @WebParam(name = "quantitys", targetNamespace = "")
        int quantitys);

}
