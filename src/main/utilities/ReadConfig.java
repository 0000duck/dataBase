package main.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import utilities.ProjectParam;
import utilities.LogFile;

public class ReadConfig {

	public boolean fileExists = false;
	final static String FILENAME = "dataBase.config";
	final static String ROOT_PATH = "RootPath:";
	final static String SELECT_OPTIONS = "SelectOptions:";

	public ReadConfig() {

		File configFile = new File(FILENAME);
		if (doesExist(configFile)) {
			try {
				readConfig(configFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

		}

	}

	private boolean doesExist(File configFile) { // TODO maybe put into ReadConfig()
		fileExists = configFile.exists();
		if (fileExists) {
			LogFile.write("Config File " + configFile + " found");
			return true;
		}
		LogFile.write("Config File: " + configFile + " does not exist");
		return false;

	}

	private void readConfig(File configFile) throws IOException {

		BufferedReader lineReader = new BufferedReader(new FileReader(configFile));
		String line;
		do {
			line = lineReader.readLine();
			if (line != null) {
				line = line.trim();	
				parseLine(line);
			}
						
		} while (line != null);

		lineReader.close();
	}

	private void parseLine(String line) {
		
		//get root path
		if (line.contains(ROOT_PATH)) {
			String path = line.substring(ROOT_PATH.length());

			ProjectParam.ROOT_PATH = path;
			LogFile.write("Directory:" + path);
		}
		
		//get select option for dialog
		if (line.contains(SELECT_OPTIONS)) {
			String selectOptions = line.substring(SELECT_OPTIONS.length());
			LogFile.write("Select options String:" + selectOptions);
			ProjectParam.SELECT_OPTIONS = selectOptions.split("|");
		}

	}
}
