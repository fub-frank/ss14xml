
package ss14xmlproject.services.imagedata.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2.4
 * 
 */
@XmlRootElement(name = "IOException", namespace = "http://schema.df-root.com/imagedata")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IOException", namespace = "http://schema.df-root.com/imagedata")
public class IOExceptionBean {

    private String message;

    /**
     * 
     * @return
     *     returns String
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * 
     * @param message
     *     the value for the message property
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
