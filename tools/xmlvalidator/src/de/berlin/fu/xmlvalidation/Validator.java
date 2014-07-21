package de.berlin.fu.xmlvalidation;

import java.io.File;

import de.berlin.fu.filter.FileExtensionFilter;

/*************************************************************************
 * -----------------------------------------------------------------------
 * Project : XMLValidator
 * Package : de.berlin.fu.xmlvalidation
 * Module : Validator.java
 * Author : goerick.m
 * -----------------------------------------------------------------------
 * (c) Copyright 2014
 * -----------------------------------------------------------------------
 * Created : 09.06.2014
 * LastChanged : 
 * -----------------------------------------------------------------------
 * Description : 
 * -----------------------------------------------------------------------
 * History :
 ************************************************************************/

public class Validator {
	
	public static boolean bShowValid = true;

	public static void main(String[] args) {
		int iargs = args.length;
		if ((iargs == 1 && args[0].endsWith("-help")) || iargs < 2) {
			usage();
			return;
		}
		
		
		File fXsd = new File(args[0]);
		File fFolder;
		File[] fXmls = new File[iargs-1];
		if (args[1].equals("-o")) {
			if (iargs > 2) {
				fFolder = new File(args[2]);
				if (fFolder.isDirectory()) {
					fXmls = fFolder.listFiles(new FileExtensionFilter(".xml"));
					bShowValid = false;
				}
			}
		} else {
			for (int i = 1; i < iargs; i++) {
				if (args[i].endsWith(".xml")) {
					fXmls[i-1] = new File(args[i]);
				}
			}
		}
		
		if (!fXsd.getName().endsWith(".xsd") && isArrayEmpty(fXmls)) {
			usage();
			return;
		}
		
		for (int j = 0; j < iargs-1; j++ ) {
			if (fXmls[j] != null) {
				XMLValidate xmlVal = new XMLValidate(fXsd, fXmls[j], bShowValid);
				xmlVal.run();
			}
		}
	}

	private static void usage() {
		System.out.println("Usage:");
		System.out.println("-------------------------------------------------");
		System.out.println("XMLValidation.jar schema.xsd file1.xml file2.xml ...");
		System.out.println("XMLValidation.jar schema.xsd -o folder");
		System.out.println("First file must the schema xsd.");
		System.out.println("followed by xml files or '-o' with a folder");
	}
	
	public static boolean isArrayEmpty(Object[] obj) {
		if (obj.length == 0) {
			return true;
		}
		for (int i = 1; i < obj.length; i++) {
			if (obj[i] != null) {
				return false;
			}
		}
		return true;
	}
	
}


//------------------------------------------------------------------------
// End of File
