package gps;
import java.io.FileReader;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import au.com.bytecode.opencsv.CSVReader;

public class csv2xmlwithgps {
	public static void main(String[] args) throws Exception {
		CSVReader reader = new CSVReader(new FileReader("berlinische-gallerie.csv"),',','"',1);
	    String [] nextLine;
	    int zeilenIndex = 0;
	    while ((nextLine = reader.readNext()) != null) {
	        // nextLine[] is an array of values from the line

	    	xmlCreater(nextLine);
	    	//System.out.println(zeilenIndex + ":" + fotografin + "# " + architektKünstler + "# " + bezeichnungGebäudeOrt + "# "+ortsangabeUndStrassenangabe+ "# "+systematik+besitz+ "# "+dateiname+ "# "+anzahlKontaktabzügeJeKarte+ "# "+datumDerInventarisierung+ "# "+creditline+motivInhalt+ "# "+entstehungsjahr+ "# "+original_filename+ "# "+flickr_id+ "# "+url_t+ "# "+url_s+ "# "+url_sq+ "# "+url_m+ "# "+url_o+ "# "+pathalias+ "# "+title);
	    	System.out.println();
	    	System.out.println();
	    	System.out.println();
	    	zeilenIndex = zeilenIndex+1;

	    	
	    }
	    
	    reader.close();
	    
	   
	}

	private static void xmlCreater(String[] nextLine) throws Exception {
		/*
    	String fotografin = nextLine[0].replace("\n", ",");
    	String architektKünstler = nextLine[1].replace("\n", ",");
    	String bezeichnungGebäudeOrt = nextLine[2].replace("\n", ",");
    	String systematik = nextLine[4].replace("\n", ",");
    	String besitz = nextLine[5].replace("\n", ",");
    	*/
    	String dateiname = nextLine[6].replace("\n", ",");
    	/*
    	String anzahlKontaktabzügeJeKarte = nextLine[7].replace("\n", ",");
    	String datumDerInventarisierung = nextLine[8].replace("\n", ",");
    	String creditline = nextLine[9].replace("\n", ",");
    	String motivInhalt = nextLine[10].replace("\n", ",");
    	String entstehungsjahr = nextLine[11].replace("\n", ",");
    	String original_filename = nextLine[12].replace("\n", ",");
    	*/
    	String flickr_id = nextLine[13].replace("\n", ",");
    	/*
    	String url_t = nextLine[14].replace("\n", ",");
    	String url_s = nextLine[15].replace("\n", ",");
    	String url_sq = nextLine[16].replace("\n", ",");
    	String url_m = nextLine[17].replace("\n", ",");
    	String url_o = nextLine[18].replace("\n", ",");
    	String pathalias = nextLine[19].replace("\n", ",");
    	String title = nextLine[20].replace("\n", ",");
    	*/
    	String breitengrad = nextLine[21];
    	String längengrad = nextLine[22];
    	String radius = nextLine[23];
    	
    	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
 		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
  
 		// Wurzel
 		Document doc = docBuilder.newDocument();
 		Element rootElement = doc.createElement("Foto");
 		rootElement.setAttribute("xsi:schemaLocation", "MitGpsSchema.xsd");
 		doc.appendChild(rootElement);
 		/*
 		//Fotografin
 		Element fotografinTag = doc.createElement("Fotografin");
 		fotografinTag.appendChild(doc.createTextNode(fotografin));
 		rootElement.appendChild(fotografinTag);
 		
 		//ArchitektKünstler
 		Element architektKünstlerTag = doc.createElement("ArchitektKünstler");
 		architektKünstlerTag.appendChild(doc.createTextNode(architektKünstler));
 		rootElement.appendChild(architektKünstlerTag);
 		
 		//BezeichnungGebäudeOrt
 		Element bezeichnungGebäudeOrtTag = doc.createElement("BezeichnungGebäudeOrt");
 		bezeichnungGebäudeOrtTag.appendChild(doc.createTextNode(bezeichnungGebäudeOrt));
 		rootElement.appendChild(bezeichnungGebäudeOrtTag);
 		
 		//OrtsangabeStraßenangabe
 		Element ortsangabeStraßenangabeTag = doc.createElement("OrtsangabeStraßenangabe");
 		ortsangabeStraßenangabeTag.appendChild(doc.createTextNode(ortsangabeUndStrassenangabe));
 		rootElement.appendChild(ortsangabeStraßenangabeTag);
 		
 		//Systematik
 		Element systematikTag = doc.createElement("Systematik");
 		systematikTag.appendChild(doc.createTextNode(systematik));
 		rootElement.appendChild(systematikTag);
 		
 		//Besitz
 		Element besitzTag = doc.createElement("Besitz");
 		besitzTag.appendChild(doc.createTextNode(besitz));
 		rootElement.appendChild(besitzTag);
 		*/
 		//Dateiname
 		Element dateinameTag = doc.createElement("Dateiname");
 		dateinameTag.appendChild(doc.createTextNode(dateiname));
 		rootElement.appendChild(dateinameTag);

 		/*
 		//AnzahlKontaktabzügeJeKarteikarte
 		Element anzahlTag = doc.createElement("AnzahlKontaktabzügeJeKarteikarte");
 		anzahlTag.appendChild(doc.createTextNode(anzahlKontaktabzügeJeKarte));
 		rootElement.appendChild(anzahlTag);
 		
 		//DatumDerInventarisierung
 		Element datumDerInventarisierungTag = doc.createElement("DatumDerInventarisierung");
 		datumDerInventarisierungTag.appendChild(doc.createTextNode(datumDerInventarisierung));
 		rootElement.appendChild(datumDerInventarisierungTag);
 		
 		//Creditline
 		Element creditlineTag = doc.createElement("Creditline");
 		creditlineTag.appendChild(doc.createTextNode(creditline));
 		rootElement.appendChild(creditlineTag);
 		
 		//MotivInhalt
 		Element motivInhaltTag = doc.createElement("MotivInhalt");
 		motivInhaltTag.appendChild(doc.createTextNode(motivInhalt));
 		rootElement.appendChild(motivInhaltTag);
 		
 		//Entstehungsjahr
 		Element entstehungsjahrTag = doc.createElement("Entstehungsjahr");
 		entstehungsjahrTag.appendChild(doc.createTextNode(entstehungsjahr));
 		rootElement.appendChild(entstehungsjahrTag);
 		
 		//original_filename
 		Element original_filenameTag = doc.createElement("original_filename");
 		original_filenameTag.appendChild(doc.createTextNode(original_filename));
 		rootElement.appendChild(original_filenameTag);
 		*/
 		//flickerid
 		Element flickeridTag = doc.createElement("flickerid");
 		flickeridTag.appendChild(doc.createTextNode(flickr_id));
 		rootElement.appendChild(flickeridTag);
 		/*
 		//url_t
 		Element url_tTag = doc.createElement("url_t");
 		url_tTag.appendChild(doc.createTextNode(url_t));
 		rootElement.appendChild(url_tTag);
 		
 		//url_s
 		Element url_sTag = doc.createElement("url_s");
 		url_sTag.appendChild(doc.createTextNode(url_s));
 		rootElement.appendChild(url_sTag);
 		
 		//url_sq
 		Element url_sqTag = doc.createElement("url_sq");
 		url_sqTag.appendChild(doc.createTextNode(url_sq));
 		rootElement.appendChild(url_sqTag);
 		
 		//url_m
 		Element url_mTag = doc.createElement("url_m");
 		url_mTag.appendChild(doc.createTextNode(url_m));
 		rootElement.appendChild(url_mTag);
 		
 		//url_o
 		Element url_oTag = doc.createElement("url_o");
 		url_oTag.appendChild(doc.createTextNode(url_o));
 		rootElement.appendChild(url_oTag);
 		
 		//pathalias
 		Element pathaliasTag = doc.createElement("pathalias");
 		pathaliasTag.appendChild(doc.createTextNode(pathalias));
 		rootElement.appendChild(pathaliasTag);
 		
 		//title
 		Element titleTag = doc.createElement("title");
 		titleTag.appendChild(doc.createTextNode(title));
 		rootElement.appendChild(titleTag);
 		*/
 		//längengrad
 		Element längengradTag = doc.createElement("längengrad");
 		längengradTag.appendChild(doc.createTextNode(längengrad));
 		rootElement.appendChild(längengradTag);
 		
 		
 		//breitengrad
 		Element breitengradTag = doc.createElement("breitengrad");
 		breitengradTag.appendChild(doc.createTextNode(breitengrad));
 		rootElement.appendChild(breitengradTag);
 		
 		//Radius
 		Element radiusTag = doc.createElement("Radius");
 		radiusTag.appendChild(doc.createTextNode(radius));
 		rootElement.appendChild(radiusTag);
 		
 		// write the content into xml file
 		TransformerFactory transformerFactory = TransformerFactory.newInstance();
 		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
 		StreamResult result = new StreamResult(new File(flickr_id+".xml"));
 	 
 		// Output to console for testing
 		//StreamResult result = new StreamResult(System.out);
 	 
 		transformer.transform(source, result);
 	 
 		System.out.println("File saved!");
	}

	
}
