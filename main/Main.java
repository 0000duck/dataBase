package main;

import gui.draw.MainFrame;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import main.utilities.ConvertStructureToArray;
import main.utilities.DisassembleInfoFile;
import main.utilities.ListBaseFilesWalker;
import main.utilities.ReadConfig;
import structure.Group;
import structure.GroupCollection;
import structure.GroupComplete;
import structure.GroupInfo;
import utilities.CustomConstant;
import utilities.LogFile;

public class Main {

	public static Object[][] arrayTwoDim; //TODO: convert to a 2 dimensional array only in the HMI classes
	public static GroupCollection KeplastDataBaseList; //TODO make this non static and pass to MainFrame
	
	public static void main(String[] args) throws IOException {
		
		//Initial steps during start up
		
		//Read Configuration File
		ReadConfig configFile = new ReadConfig();
		if (!configFile.fileExists) {
			LogFile.write("Program stopped. Path not found or Config File not exists");
			System.exit(1);
		};
		
		//CustomConstant.ROOT_PATH = new File(".").getCanonicalPath(); //
	    LogFile.setLogFilename(CustomConstant.ROOT_PATH + "\\dataBaseLog.txt");
	    CustomConstant.MAIN_FRAME = new MainFrame();  //Draw HMI
	    CustomConstant.FILTER_TABLE.update(); //add Data in Table
		
	}
	
	static public void searchFiles () {
		// --------------------------- List File Directory ------------------------------------//

		new ReadConfig();
    	ListBaseFilesWalker list = new ListBaseFilesWalker();
    	//String searchPath = "\\\\keba\\project\\gba\\abteilungen\\ae\\KePlast\\Applications";
    	
    	ArrayList<File> listFileBase = list.walk(CustomConstant.ROOT_PATH);
    	
    	// ---------------------- Compare/Search KePlast Base Files----------------------------//
		KeplastDataBaseList = new GroupCollection();
		//parse files -> collect in structured list
		for (File file : listFileBase) {
		
			LogFile.write(file.getPath());

			// Disassemble info file, prepare content data
			GroupInfo groupInfo = new GroupInfo();
			groupInfo = DisassembleInfoFile.checkInfoFile(file);
			GroupComplete groupComplete = new GroupComplete();
			groupComplete.setGroupInfo(groupInfo);

			// TODO check for syntax errors
			// System.out.println("Syntex error in file or wrong file type: " + file);

			// Search for Files in current Folder
			Group group = new Group();
			group.addList(compare(file, groupInfo));
			groupComplete.setGroup(group);

			//Add Feature/Document Group to List
			KeplastDataBaseList.addListElement(groupComplete);
		}
		
		// ---------------------- Compare/Search KePlast Base Files----------------------------//

		arrayTwoDim = ConvertStructureToArray.convert(KeplastDataBaseList);	
	}

	private static ArrayList<File> compare(File fileInfoBase, GroupInfo groupInfo) {
		File dir = new File(fileInfoBase.getParent().toString());
		File[] fileList = dir.listFiles();
		ArrayList<File> filteredList = new ArrayList<File>();
		if (groupInfo.getArraylist() != null) {
			for (File infoFile : groupInfo.getArraylist()) {
				boolean match = false;
				for (File actFile : fileList) {
					// System.out.println((infoFile.toString() + " = " + actFile.getName().toString()));
					if (((infoFile.toString()).equalsIgnoreCase(actFile.getName().toString()) && actFile.isFile())) {
						filteredList.add(actFile);
						match = true;
					}
				}
				if (!match) {
					LogFile.write("File " + infoFile + " regarding to " + fileInfoBase.getName()
							+ " not found in directory");
				}
			}
		} else {
			LogFile.write("No files specified in:" + fileInfoBase.getName());
		}

		return filteredList;
	}
}