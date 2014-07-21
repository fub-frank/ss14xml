package ss14xmlproject.services.metadata.response;


import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Architects")
public class ArchitectContainer {
	@XmlElement(name = "architect")
	protected List<Architect> architect;

	public ArchitectContainer() {
		this.architect = new LinkedList<Architect>();
	}

	public List<Architect> getArchitect() {
		return architect;
	}
}
