
package ss14xmlproject.services.imagedata.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getImage", namespace = "http://schema.df-root.com/imagedata")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getImage", namespace = "http://schema.df-root.com/imagedata", propOrder = {
    "filename",
    "number"
})
public class GetImage {

    @XmlElement(name = "filename", namespace = "")
    private String filename;
    @XmlElement(name = "number", namespace = "")
    private int number;

    /**
     * 
     * @return
     *     returns String
     */
    public String getFilename() {
        return this.filename;
    }

    /**
     * 
     * @param filename
     *     the value for the filename property
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * 
     * @return
     *     returns int
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * 
     * @param number
     *     the value for the number property
     */
    public void setNumber(int number) {
        this.number = number;
    }

}
