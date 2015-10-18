
package registration;

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
@WebService(name = "Registration", targetNamespace = "http://registration/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Registration {


    /**
     * 
     * @param registrationId
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "unregisterManufacturer", targetNamespace = "http://registration/", className = "registration.UnregisterManufacturer")
    @ResponseWrapper(localName = "unregisterManufacturerResponse", targetNamespace = "http://registration/", className = "registration.UnregisterManufacturerResponse")
    @Action(input = "http://registration/Registration/unregisterManufacturerRequest", output = "http://registration/Registration/unregisterManufacturerResponse")
    public boolean unregisterManufacturer(
        @WebParam(name = "registrationId", targetNamespace = "")
        int registrationId);

    /**
     * 
     * @param name
     * @param description
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "registerManufacturer", targetNamespace = "http://registration/", className = "registration.RegisterManufacturer")
    @ResponseWrapper(localName = "registerManufacturerResponse", targetNamespace = "http://registration/", className = "registration.RegisterManufacturerResponse")
    @Action(input = "http://registration/Registration/registerManufacturerRequest", output = "http://registration/Registration/registerManufacturerResponse")
    public int registerManufacturer(
        @WebParam(name = "name", targetNamespace = "")
        String name,
        @WebParam(name = "description", targetNamespace = "")
        String description);

}
