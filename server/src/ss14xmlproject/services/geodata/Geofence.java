package ss14xmlproject.services.geodata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Geofence")
public class Geofence {
	@XmlElement(name = "flickerid")
	protected String flickerid;

	@XmlElement(name = "longitude")
	protected double longitude;
	
	@XmlElement(name = "latitude")
	protected double latitude;
	
	@XmlElement(name = "radius")
	protected double radius;
	
	public Geofence() {
		
	}
	
	public Geofence(String flickerId, double longitude, double latitude, double radius) {
		this.flickerid = flickerId;
		this.longitude = longitude;
		this.latitude = latitude;
		this.radius = radius;
	}
	
	public String getFlickerid() {
		return this.flickerid;
	}
	
	public void setFlickerid(String flickerid) {
		this.flickerid = flickerid;
	}
	
	public double getLongitude() {
		return this.longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public double getLatitude() {
		return this.latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getRadius() {
		return this.radius;
	}
	
	public void setRadius(double radius) {
		this.radius = radius;
	}
}
