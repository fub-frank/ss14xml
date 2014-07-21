package de.fu_berlin.inf.xmlprojectapp.content.historic.serialisation;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

@XMLObject("//ImageInfo")
public class HistoricImageInfo
{
	@XMLField("filename")
	public String filename;

	@XMLField("imagecount")
	public int imagecount;
}
