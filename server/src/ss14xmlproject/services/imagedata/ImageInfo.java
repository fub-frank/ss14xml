package ss14xmlproject.services.imagedata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Imageinfo")
public class ImageInfo {
	@XmlElement(name = "filename")
	protected String filename;

	@XmlElement(name = "imagecount")
	protected int imagecount;
	
	public ImageInfo() {
		
	}
	
	public ImageInfo(String filename, int imagecount) {
		this.filename = filename;
		this.imagecount = imagecount;
	}
	
	public String getFilename() {
		return this.filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public int getImagecount() {
		return this.imagecount;
	}
	
	public void setImagecount(int imagecount) {
		this.imagecount = imagecount;
	}
}
