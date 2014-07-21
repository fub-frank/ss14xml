package ss14xmlproject;

import java.io.IOException;

import javax.xml.ws.Endpoint;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.basex.examples.api.BaseXClient;

import ss14xmlproject.services.geodata.GeodataService;
import ss14xmlproject.services.imagedata.ImagedataService;
import ss14xmlproject.services.metadata.MetadataService;

public class SoapService {
	
	public static String IMAGE_URL = "http://xml.df-root.com/images/";
	
	public static String BASEX_SERVER = "df-root.com";
	public static int BASEX_PORT = 1984;
	public static String BASEX_USER = "readonly";
	public static String BASEX_PASS = "ss14xml";
	public static String BASEX_DATABASE = "ss14xml";
	
	public static String SERVER = "localhost";
	public static String PORT = "8370";
	public static String GEODATA_ENDPOINT = "http://" + SERVER + ":" + PORT + "/geodata";
	public static String IMAGEDATA_ENDPOINT = "http://" + SERVER + ":" + PORT + "/imagedata";
	public static String METADATA_ENDPOINT = "http://" + SERVER + ":" + PORT + "/metadata";
	
	public SoapService() throws IOException {
		// logger needed for apache jena (sparql library)
		SimpleLayout layout = new SimpleLayout();
		ConsoleAppender consoleAppender = new ConsoleAppender(layout);
		Logger.getRootLogger().addAppender(consoleAppender);
		Logger.getRootLogger().setLevel(Level.WARN);
		
		
		// Connection to the xml database
		BaseXClient baseX = new BaseXClient(BASEX_SERVER, BASEX_PORT, BASEX_USER, BASEX_PASS);
		
		GeodataService geodataService = new GeodataService();
		geodataService.setBaseXSession(baseX);
		Endpoint.publish(GEODATA_ENDPOINT, geodataService);
		System.out.println("Webservice ready on " + GEODATA_ENDPOINT);
		
		ImagedataService imageService = new ImagedataService();
		imageService.setBaseXSession(baseX);
		Endpoint.publish(IMAGEDATA_ENDPOINT, imageService);
		System.out.println("Webservice ready on " + IMAGEDATA_ENDPOINT);
		
		MetadataService metaService = new MetadataService();
		metaService.setBaseXSession(baseX);
		Endpoint.publish(METADATA_ENDPOINT, metaService);
		System.out.println("Webservice ready on " + METADATA_ENDPOINT);
	}
	
	public static void main(String[] args) throws IOException {
		if (args.length > 0) {
			SERVER = args[0];
			GEODATA_ENDPOINT = "http://" + SERVER + ":" + PORT + "/geodata";
			IMAGEDATA_ENDPOINT = "http://" + SERVER + ":" + PORT + "/imagedata";
			METADATA_ENDPOINT = "http://" + SERVER + ":" + PORT + "/metadata";
		}
		new SoapService();
	}
}
