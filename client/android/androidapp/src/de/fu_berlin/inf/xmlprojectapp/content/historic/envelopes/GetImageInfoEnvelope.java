package de.fu_berlin.inf.xmlprojectapp.content.historic.envelopes;

import com.alexgilleran.icesoap.envelope.impl.BaseSOAP11Envelope;
import com.alexgilleran.icesoap.xml.XMLParentNode;

public class GetImageInfoEnvelope extends BaseSOAP11Envelope
{
	private static String IMGDATA_NAMESPACE = "http://schema.df-root.com/imagedata";

	public GetImageInfoEnvelope(String flickerid)
	{
		this.declarePrefix("imagedata", GetImageInfoEnvelope.IMGDATA_NAMESPACE);
		XMLParentNode getImageInfo = this.getBody().addNode(
		        GetImageInfoEnvelope.IMGDATA_NAMESPACE, "getImageInfo");
		getImageInfo.addTextNode("", "flickerId", flickerid);
	}
}
