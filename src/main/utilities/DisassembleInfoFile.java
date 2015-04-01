package main.utilities;

import gui.addDialog.CreateInfoDataBaseFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import structure.GroupInfo;

public class DisassembleInfoFile {
	
	private static 	GroupInfo 	Element;
	final 	static 	String 		RawPath				= "RawPath";
	final 	static	String		LineBreak			= "\r\n"; 	
	
	private static 	File 		fileInt 			= null;
			
	private static	String 		lookForOld			= "";
	private static  int 		endIdx				= 0;
	private static 	int 		length				= 0;

	public static GroupInfo checkInfoFile(File file){
		String tmp = null;
		tmp = readFile(file.toString());
		fileInt = file;
		tmp = deleteLinBrake(tmp);
		disassemble(tmp);
		return Element;
	}
	
	private static void disassemble(String tmp) {
		System.out.println(tmp);
		Element = new GroupInfo();
		Element.setVersion(ParseString(tmp,CreateInfoDataBaseFile.conVersion));
		Element.setAutor(ParseString(tmp,CreateInfoDataBaseFile.conAutor));
		Element.setKeywords(ParseString(tmp,CreateInfoDataBaseFile.conKeywords));
		Element.setHeader(ParseString(tmp,CreateInfoDataBaseFile.conHeader));
		Element.setDescription(ParseString(tmp,CreateInfoDataBaseFile.conDescription));
		Element.setId(ParseString(tmp,CreateInfoDataBaseFile.conId));
		Element.setType(ParseString(tmp,CreateInfoDataBaseFile.conType));
		Element.setPath(GetPath(fileInt));
		int i = 0;
		while (true) {
			i++;
			String tmp1 = ParseString(tmp,CreateInfoDataBaseFile.conFile);
			if (!tmp1.isEmpty()) {
				Element.setFileListElement(new File(tmp1));	
			} else {
				return;
			}
			
		}
		
	}

	private static String GetPath(File tmp) {
		String Path = fileInt.getParent();
		return Path;
	}


	private static String  ParseString(String input, String lookFor) {
		int idx;
		if ((lookFor == lookForOld) && (!lookForOld.isEmpty())) {
			idx = endIdx + length;
		} else {
			idx = 0;
		}
		lookForOld = lookFor;
		int startIdx = input.indexOf(lookFor,idx);
		length	 = lookFor.length();
		endIdx = input.indexOf(lookFor, startIdx + length);
		if ((startIdx == -1) || (endIdx == -1)) {
			return "";
		}
		String subString = input.substring(startIdx + length, endIdx);
		return subString;
				
	}


	public static String readFile(String filename)	{
	   String content = null;
	   File file = new File(filename); //for ex foo.txt
	   try {
	       FileReader reader = new FileReader(file);
	       char[] chars = new char[(int) file.length()];
	       reader.read(chars);
	       content = new String(chars);
	       reader.close();
	   } catch (IOException e) {
	       e.printStackTrace();
	   }
	   return content;
	}
	
	private static String deleteLinBrake (String input) {
		input.replace(System.lineSeparator(),"");
		return input;
	};
	
	
	
}