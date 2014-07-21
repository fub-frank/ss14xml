
package ss14xmlproject.services.imagedata.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getImageResponse", namespace = "http://schema.df-root.com/imagedata")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getImageResponse", namespace = "http://schema.df-root.com/imagedata")
public class GetImageResponse {

    @XmlElement(name = "Image", namespace = "", nillable = true)
    private byte[] image;

    /**
     * 
     * @return
     *     returns byte[]
     */
    public byte[] getImage() {
        return this.image;
    }

    /**
     * 
     * @param image
     *     the value for the image property
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

}
