package main.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import utilities.CustomConstant;
import utilities.LogFile;

public class ReadConfig {

	File configFile;
	public boolean fileExists = false;
	final static String fileName = "dataBase.config";
	final static String rootPath = "RootPath:";
	
	public ReadConfig() {

		configFile = new File(fileName);
		if (doesExist()) {
			try {
				readConfig();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			
		}

	}

	private boolean doesExist() {
		fileExists = configFile.exists();
		if (fileExists) {
			LogFile.write("Config File " + configFile + " found");
			return true;
		}
		LogFile.write("Config File: " + configFile + " does not exist");
		return false;

	}

	private void readConfig() throws IOException {

		BufferedReader lineReader = new BufferedReader(new FileReader(configFile));
		String line = lineReader.readLine();
		line.trim();
		setDirectoryPath(line);
		lineReader.close();

	}

	private void setDirectoryPath(String line) {
		boolean directory;
		directory = line.contains(rootPath);
		if (directory) {
			LogFile.write("Directory:" + line.substring(rootPath.length()));
			CustomConstant.ROOT_PATH = line.substring(rootPath.length());
		}

	}
}
	
	
