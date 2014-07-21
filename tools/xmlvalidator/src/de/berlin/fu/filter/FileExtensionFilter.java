package de.berlin.fu.filter;

import java.io.File;
import java.io.FilenameFilter;

public class FileExtensionFilter implements FilenameFilter {

	private String strExtension;
	
	public FileExtensionFilter(String strExtension) {
		this.strExtension = strExtension;
	}
	@Override
	public boolean accept(File file, String name) {
		return name.endsWith(strExtension);
	}

}
