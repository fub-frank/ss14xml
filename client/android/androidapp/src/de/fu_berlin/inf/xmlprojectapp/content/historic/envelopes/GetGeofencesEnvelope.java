package de.fu_berlin.inf.xmlprojectapp.content.historic.envelopes;

import com.alexgilleran.icesoap.envelope.impl.BaseSOAP11Envelope;

public class GetGeofencesEnvelope extends BaseSOAP11Envelope
{
	private static String GEODATA_NAMESPACE = "http://schema.df-root.com/geodata";

	public GetGeofencesEnvelope()
	{
		this.declarePrefix("geodata", GetGeofencesEnvelope.GEODATA_NAMESPACE);
		this.getBody().addNode(GetGeofencesEnvelope.GEODATA_NAMESPACE,
		        "getGeoFences");
	}
}
