package ss14xmlproject.services.metadata.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Photographer")
public class Photographer {
	@XmlElement(name = "firstName")
	protected String firstName;
	@XmlElement(name = "lastName")
	protected String lastName;
	@XmlElement(name = "additionalInfo")
	protected AdditionalPhotographerInfo additionalInfo;
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public AdditionalPhotographerInfo getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(AdditionalPhotographerInfo additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
}
