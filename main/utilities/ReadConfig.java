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
		String line = lineReader.readLine();
		line = line.trim();
		parseLine(line);
		lineReader.close();
	}

	private void parseLine(String line) {
		if (line.contains(ROOT_PATH)) {
			String path = line.substring(ROOT_PATH.length());

			ProjectParam.ROOT_PATH = path;
			LogFile.write("Directory:" + path);
		}

	}
}
