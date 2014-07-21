package ss14xmlproject.services.metadata.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArchitectWorkItem")
public class Work {

	@XmlElement(name = "name")
	private String name;
	@XmlElement(name = "thumbnail")
	private String thumbnail;
	@XmlElement(name = "wikiId")
	private int wikiId;

	public Work(String name, String thumbnail, int wikiId) {
		this.name = name;
		this.thumbnail = thumbnail;
		this.wikiId = wikiId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public int getWikiId() {
		return wikiId;
	}

	public void setWikiId(int wikiId) {
		this.wikiId = wikiId;
	}

}
