package ss14xmlproject.services.metadata.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Location")
public class Location {
	@XmlElement(name = "city")
	protected String city;
	@XmlElement(name = "street")
	protected String street;
	@XmlElement(name = "suburb")
	protected String suburb;
	
	@XmlElement(name = "additionalLocationInfo")
	protected AdditionalLocationInfo locationInfo;
	
	
	public Location(String city, String street, String suburb) {
		this.city = city;
		this.street = street;
		this.suburb = suburb;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public String getSuburb() {
		return suburb;
	}


	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}


	public AdditionalLocationInfo getLocationInfo() {
		return locationInfo;
	}


	public void setLocationInfo(AdditionalLocationInfo locationInfo) {
		this.locationInfo = locationInfo;
	}
}
