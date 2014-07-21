package de.fu_berlin.inf.xmlprojectapp.content.historic.envelopes;

import com.alexgilleran.icesoap.envelope.impl.BaseSOAP11Envelope;
import com.alexgilleran.icesoap.xml.XMLParentNode;

public class GetImageEnvelope extends BaseSOAP11Envelope
{
	private static String IMGDATA_NAMESPACE = "http://schema.df-root.com/imagedata";

	public GetImageEnvelope(String filename, int number)
	{
		this.declarePrefix("imagedata", GetImageEnvelope.IMGDATA_NAMESPACE);
		XMLParentNode getImage = this.getBody().addNode(
		        GetImageEnvelope.IMGDATA_NAMESPACE, "getImage");
		getImage.addTextNode("", "filename", filename);
		getImage.addTextNode("", "number", String.valueOf(number));
	}
}
