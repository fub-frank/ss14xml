package ss14xmlproject.services.imagedata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Data")
public class ImageWrapper {
	
	@XmlElement(name = "data")
	protected byte[] data;


	public ImageWrapper() {
	}
	
	public ImageWrapper(byte[] image) {
		this.data = image;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}
