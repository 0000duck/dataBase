package main;

import gui.draw.MainFrame;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import main.utilities.ConvertStructureToArray;
import main.utilities.DisassembleInfoFile;
import main.utilities.ListBaseFilesWalker;
import main.utilities.ReadConfig;
import structure.Group;
import structure.GroupCollection;
import structure.GroupComplete;
import structure.GroupInfo;
import utilities.LogFile;
import utilities.ProjectParam;

public class Main {

	public static Object[][] arrayTwoDim; //TODO: convert to a 2 dimensional array only in the HMI classes
	public static GroupCollection KeplastDataBaseList; //TODO make this non static and pass to MainFrame
	public static boolean errorApplication = false; //application error can be: path not found, configuration file not found,....
	
	public static void main(String[] args) throws IOException {
		
		//Initial steps during start up
		
		//Set up log file
		File logFile = new File(ProjectParam.USERHOME, "dataBaseLog.txt");
		LogFile.setLogFile(logFile);
		
		//get root path of *.jar file
		getApplicationRoot();
	
		//Read Configuration File
		ReadConfig configFile = new ReadConfig();
		checkConfigFileResult(configFile);
		
		//Check if data folder exists
		File dataPath = new File(ProjectParam.ROOT_PATH + ProjectParam.DATA_FOLDER);
		if (!(dataPath.isDirectory())) {
			dataPath.mkdir();
		}
		
	    ProjectParam.MAIN_FRAME = new MainFrame();  //Draw HMI
	    
	    if (!errorApplication) {
			    ProjectParam.FILTER_TABLE.update(); //add Data in Table	
		}
		
	}
	
	/*
	 * after configuration file is read content should be checked
	 * for validity 
	 */
	
	private static void checkConfigFileResult(ReadConfig configFile) {
		if (!configFile.fileExists) {
			LogFile.write("Program stopped. Path not found or Config File not exists");
			errorApplication = true;
			//System.exit(1);
			
		} else if (!(new File(ProjectParam.ROOT_PATH)).isDirectory()) {
			LogFile.write("Program stopped. Configured Root is no found directory");
			errorApplication = true;
		}
	}

	private static void getApplicationRoot() {
		File fileRoot = new File("Root.txt");
		String absPathRoot = fileRoot.getAbsolutePath();
		//String fileNameRoot = fileRoot.getPath();
		String rootPathAppl = absPathRoot.substring(0,absPathRoot.lastIndexOf(File.separator));
		ProjectParam.ROOT_PATH_APPL = rootPathAppl;
		LogFile.write("Appl Root path: " + ProjectParam.ROOT_PATH_APPL);
		
	}

	static public void searchFiles () {
		// --------------------------- List File Directory ------------------------------------//
    	ListBaseFilesWalker list = new ListBaseFilesWalker();
    	//String searchPath = "\\\\keba\\project\\gba\\abteilungen\\ae\\KePlast\\Applications";
    	
    	ArrayList<File> listFileBase = list.walk(ProjectParam.ROOT_PATH);
    	
    	// ---------------------- Compare/Search KePlast Base Files----------------------------//
    	GroupCollection KeplastDataBaseListTmp = new GroupCollection();
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
			KeplastDataBaseListTmp.addListElement(groupComplete);
		}
		
		//set new updated list
		
		KeplastDataBaseList = KeplastDataBaseListTmp;
		// ---------------------- Compare/Search KePlast Base Files----------------------------//

		arrayTwoDim = ConvertStructureToArray.convert(KeplastDataBaseListTmp);	
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