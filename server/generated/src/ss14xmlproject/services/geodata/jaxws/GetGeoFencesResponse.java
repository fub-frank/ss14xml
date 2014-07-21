
package ss14xmlproject.services.geodata.jaxws;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getGeoFencesResponse", namespace = "http://schema.df-root.com/geodata")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getGeoFencesResponse", namespace = "http://schema.df-root.com/geodata")
public class GetGeoFencesResponse {

    @XmlElement(name = "Geofences", namespace = "")
    private List<ss14xmlproject.services.geodata.Geofence> geofences;

    /**
     * 
     * @return
     *     returns List<Geofence>
     */
    public List<ss14xmlproject.services.geodata.Geofence> getGeofences() {
        return this.geofences;
    }

    /**
     * 
     * @param geofences
     *     the value for the geofences property
     */
    public void setGeofences(List<ss14xmlproject.services.geodata.Geofence> geofences) {
        this.geofences = geofences;
    }

}
