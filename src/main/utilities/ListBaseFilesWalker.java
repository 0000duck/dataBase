package main.utilities;

import java.io.File;
import java.util.ArrayList;

import utilities.ProjectParam;

public class ListBaseFilesWalker {

	private ArrayList<File> listFiles;

	public ListBaseFilesWalker() {
		listFiles = new ArrayList<File>();
	}

	public ArrayList<File> walk(String path) {

		File root = new File(path);
		File[] list = root.listFiles();

		if (list == null)
			return null;

		for (File file : list) {
			if (file.isDirectory()) {
//				System.out.println("found directory:" + file.getAbsoluteFile());
				walk(file.getAbsolutePath());
			} else {
				if ((file.getName()).toLowerCase().endsWith(ProjectParam.DATA_BASE_FILE_EXT.toLowerCase())) {
//					System.out.println("Found database file:" + file.getAbsoluteFile());
					listFiles.add(file);
				}
			}
		}
		return listFiles;
	}
}