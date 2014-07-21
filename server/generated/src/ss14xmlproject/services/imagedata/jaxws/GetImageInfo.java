
package ss14xmlproject.services.imagedata.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getImageInfo", namespace = "http://schema.df-root.com/imagedata")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getImageInfo", namespace = "http://schema.df-root.com/imagedata")
public class GetImageInfo {

    @XmlElement(name = "flickerId", namespace = "")
    private String flickerId;

    /**
     * 
     * @return
     *     returns String
     */
    public String getFlickerId() {
        return this.flickerId;
    }

    /**
     * 
     * @param flickerId
     *     the value for the flickerId property
     */
    public void setFlickerId(String flickerId) {
        this.flickerId = flickerId;
    }

}
