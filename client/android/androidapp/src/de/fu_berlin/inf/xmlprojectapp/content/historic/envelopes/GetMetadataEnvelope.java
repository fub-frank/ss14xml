package de.fu_berlin.inf.xmlprojectapp.content.historic.envelopes;

import com.alexgilleran.icesoap.envelope.impl.BaseSOAP11Envelope;
import com.alexgilleran.icesoap.xml.XMLParentNode;

public class GetMetadataEnvelope extends BaseSOAP11Envelope
{
	private static String METADATA_NAMESPACE = "http://schema.df-root.com/metadata";

	public GetMetadataEnvelope(String flickerid)
	{
		this.declarePrefix("metadata", GetMetadataEnvelope.METADATA_NAMESPACE);
		XMLParentNode getMetadata = this.getBody().addNode(
		        GetMetadataEnvelope.METADATA_NAMESPACE, "getMetadata");
		getMetadata.addTextNode("", "flickerId", flickerid);
	}
}
