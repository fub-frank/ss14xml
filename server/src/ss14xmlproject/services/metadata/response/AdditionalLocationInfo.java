package ss14xmlproject.services.metadata.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LocationInfo")
public class AdditionalLocationInfo {
	@XmlElement(name = "comment")
	public String comment;
	@XmlElement(name = "latitude")
	public Double latitude;
	@XmlElement(name = "longitude")
	public Double longitude;
	@XmlElement(name = "thumbnail")
	public String thumbnail;
	@XmlElement(name = "wikiId")
	public Integer wikiId;
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public Integer getWikiId() {
		return wikiId;
	}
	public void setWikiId(Integer wikiId) {
		this.wikiId = wikiId;
	}
	
}
