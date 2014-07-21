package ss14xmlproject.services.metadata.response;


import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Contents")
public class ContentContainer {
	@XmlElement(name = "content")
	protected List<String> content;

	public ContentContainer() {
		this.content = new LinkedList<String>();
	}

	public List<String> getContent() {
		return content;
	}

	public void setContent(List<String> content) {
		this.content = content;
	}
}
