
package ss14xmlproject.services.imagedata.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getImageInfoResponse", namespace = "http://schema.df-root.com/imagedata")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getImageInfoResponse", namespace = "http://schema.df-root.com/imagedata")
public class GetImageInfoResponse {

    @XmlElement(name = "Imageinfo", namespace = "")
    private ss14xmlproject.services.imagedata.ImageInfo imageinfo;

    /**
     * 
     * @return
     *     returns ImageInfo
     */
    public ss14xmlproject.services.imagedata.ImageInfo getImageinfo() {
        return this.imageinfo;
    }

    /**
     * 
     * @param imageinfo
     *     the value for the imageinfo property
     */
    public void setImageinfo(ss14xmlproject.services.imagedata.ImageInfo imageinfo) {
        this.imageinfo = imageinfo;
    }

}
