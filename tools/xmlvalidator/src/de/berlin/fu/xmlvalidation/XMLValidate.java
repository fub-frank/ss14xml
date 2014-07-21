package de.berlin.fu.xmlvalidation;


import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

public class XMLValidate implements Runnable{

	// Variables
	private File fXsd;
	private File fXml;
	private boolean bShowValid;
	
	public XMLValidate(File fXsd, File fXml, boolean bShowValid) {
		this.fXsd = fXsd;
		this.fXml = fXml;
		this.bShowValid = bShowValid;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		validateXML();
	}
	
	
	private void validateXML() {
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		
		try {
			Schema schema = factory.newSchema(fXsd);
			
			javax.xml.validation.Validator validator = schema.newValidator();
			
			Source source = new StreamSource(fXml);
			
			try {
				validator.validate(source);
				if (bShowValid) {
					System.out.println(fXml.getName() + " is valid.");
				}
			} catch (SAXException e) {
				System.out.println(fXml.getName() + " is not valid because ");
				System.out.println(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
	}
}


//------------------------------------------------------------------------
// End of File
