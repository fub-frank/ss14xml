package ss14xmlproject.services.metadata.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Metadata")
public class MetadataResponse {
	@XmlElement(name = "contents")
	protected ContentContainer contents;
	
	@XmlElement(name = "architects")
	protected ArchitectContainer architects;
	
	@XmlElement(name = "photographers")
	protected PhotographerContainer photographers;
	
	@XmlElement(name = "locations")
	protected LocationContainer locations;
	
	@XmlElement(name = "credit")
	protected String credit;
	
	@XmlElement(name = "date")
	protected String date;
	
	@XmlElement(name = "ownership")
	protected String ownership;
	
	@XmlElement(name = "systematics")
	protected String systematics;
	
	@XmlElement(name = "title")
	protected String title;
	
	@XmlElement(name = "year")
	protected String year;

	public MetadataResponse() {
		this.contents = new ContentContainer();
		this.architects = new ArchitectContainer();
		this.photographers = new PhotographerContainer();
		this.locations = new LocationContainer();
	}
	
	public ContentContainer getContents() {
		return this.contents;
	}

	public ArchitectContainer getArchitects() {
		return this.architects;
	}
	
	public LocationContainer getLocations() {
		return this.locations;
	}

	public PhotographerContainer getPhotographers() {
		return this.photographers;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getOwnership() {
		return ownership;
	}

	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}

	public String getSystematics() {
		return systematics;
	}

	public void setSystematics(String systematics) {
		this.systematics = systematics;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setContents(ContentContainer contents) {
		this.contents = contents;
	}

	public void setArchitects(ArchitectContainer architects) {
		this.architects = architects;
	}

	public void setPhotographers(PhotographerContainer photographers) {
		this.photographers = photographers;
	}
	
	
}
