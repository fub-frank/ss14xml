package ss14xmlproject.services.metadata.response;


import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Photographers")
public class PhotographerContainer {
	@XmlElement(name = "photographer")
	protected List<Photographer> photographers;

	public PhotographerContainer() {
		this.photographers = new LinkedList<Photographer>();
	}

	public List<Photographer> getPhotographer() {
		return photographers;
	}
}
