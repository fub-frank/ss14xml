package ss14xmlproject.services.metadata.response;


import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Locations")
public class LocationContainer {
	@XmlElement(name = "location")
	protected List<Location> location;

	public LocationContainer() {
		this.location = new LinkedList<Location>();
	}

	public List<Location> getLocation() {
		return location;
	}
}
