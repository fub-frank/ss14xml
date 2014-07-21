package de.fu_berlin.inf.xmlprojectapp.content.historic.serialisation;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

@XMLObject("//Geofence")
public class HistoricLocation
{
	@XMLField("flickerid")
	public String flickerid;

	@XMLField("latitude")
	public double latitude;

	@XMLField("longitude")
	public double longitude;

	@XMLField("radius")
	public float radius;
}
