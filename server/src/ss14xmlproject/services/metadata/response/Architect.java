package ss14xmlproject.services.metadata.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Architect")
public class Architect {
	@XmlElement(name = "firstName")
	protected String firstName;
	@XmlElement(name = "lastName")
	protected String lastName;
	@XmlElement(name = "additionalInfo")
	protected AdditionalArchitectInfo additionalInfo;
	@XmlElement(name = "additionalWorks")
	protected AdditionalArchitectWorkInfo additionalWork;
	
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
	public AdditionalArchitectInfo getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(AdditionalArchitectInfo additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	public AdditionalArchitectWorkInfo getAdditionalWork() {
		return additionalWork;
	}
	public void setAdditionalWork(AdditionalArchitectWorkInfo additionalWork) {
		this.additionalWork = additionalWork;
	}


}
