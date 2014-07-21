package ss14xmlproject.xquery.result;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class MetadataResult implements Result {

	private static final String XMl_TAG_PHOTOGRAHPER = "Photographer";
	private static final String XMl_TAG_CREATOR = "Creator";
	private static final String XMl_TAG_TITLE = "Title";
	private static final String XMl_TAG_PLACE = "Place";
	private static final String XMl_TAG_SYSTEMATICS = "Systematics";
	private static final String XMl_TAG_OWNERSHIP = "Ownership";
	private static final String XMl_TAG_DATE = "Date";
	private static final String XMl_TAG_CREDIT = "Credit";
	private static final String XMl_TAG_CONTENT = "Content";
	private static final String XMl_TAG_YEAR = "Year";
	
	private Exception exception = null;
	private String photographer;
	private String creator;
	private String title;
	private String place;
	private String systematics;
	private String ownership;
	private String date;
	private String credit;
	private String content;
	private String year;

	@Override
	public void initialize(String data) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
		    InputSource is = new InputSource(new StringReader(data));
		    Document document = builder.parse(is);
		    this.photographer = document.getElementsByTagName(XMl_TAG_PHOTOGRAHPER).item(0).getTextContent();
		    this.creator = document.getElementsByTagName(XMl_TAG_CREATOR).item(0).getTextContent();
		    this.title = document.getElementsByTagName(XMl_TAG_TITLE).item(0).getTextContent();
		    this.place = document.getElementsByTagName(XMl_TAG_PLACE).item(0).getTextContent();
		    this.systematics = document.getElementsByTagName(XMl_TAG_SYSTEMATICS).item(0).getTextContent();
		    this.ownership = document.getElementsByTagName(XMl_TAG_OWNERSHIP).item(0).getTextContent();
		    this.date = document.getElementsByTagName(XMl_TAG_DATE).item(0).getTextContent();
		    this.credit = document.getElementsByTagName(XMl_TAG_CREDIT).item(0).getTextContent();
		    this.content = document.getElementsByTagName(XMl_TAG_CONTENT).item(0).getTextContent();
		    this.year = document.getElementsByTagName(XMl_TAG_YEAR).item(0).getTextContent();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
			this.exception  = e;
		}
	}

	public Exception getException() {
		return exception;
	}

	public String getPhotographer() {
		return photographer;
	}

	public String getCreator() {
		return creator;
	}

	public String getTitle() {
		return title;
	}

	public String getPlace() {
		return place;
	}

	public String getSystematics() {
		return systematics;
	}

	public String getOwnership() {
		return ownership;
	}

	public String getDate() {
		return date;
	}

	public String getCredit() {
		return credit;
	}

	public String getContent() {
		return content;
	}

	public String getYear() {
		return year;
	}
	
	@Override
	public String toString() {
		return "MetadataResult [exception=" + exception + ", photographer="
				+ photographer + ", creator=" + creator + ", title=" + title
				+ ", place=" + place + ", systematics=" + systematics
				+ ", ownership=" + ownership + ", date=" + date + ", credit="
				+ credit + ", content=" + content + ", year=" + year + "]";
	}
}
