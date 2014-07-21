package de.fu_berlin.inf.xmlprojectapp.content.historic.serialisation;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

@XMLObject("//Image")
public class HistoricImage
{
	@XMLField("data")
	public String data;
}
