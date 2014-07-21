package ss14xmlproject.services.metadata.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PhotographerPersonInfo")
public class AdditionalPhotographerInfo {
	@XmlElement(name = "shortDescription")
	protected String shortDescription;
	@XmlElement(name = "birthdate")
	protected String birthdate;
	@XmlElement(name = "deathdate")
	protected String deathdate;
	@XmlElement(name = "description")
	protected String description;
	@XmlElement(name = "thumbnail")
	protected String thumbnail;
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public String getDeathdate() {
		return deathdate;
	}
	public void setDeathdate(String deathdate) {
		this.deathdate = deathdate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
}
