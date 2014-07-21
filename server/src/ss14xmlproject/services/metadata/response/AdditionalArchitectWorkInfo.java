package ss14xmlproject.services.metadata.response;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "additionalworks")
public class AdditionalArchitectWorkInfo {
	@XmlElement(name = "work")
	List<Work> work;
	
	public AdditionalArchitectWorkInfo() {
	}

	public List<Work> getWork() {
		return work;
	}

	public void setWork(List<Work> work) {
		this.work = work;
	}
}
