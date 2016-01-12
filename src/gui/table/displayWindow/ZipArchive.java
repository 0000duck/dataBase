package gui.table.displayWindow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import structure.GroupInfo;
import utilities.LogFile;
import utilities.ProjectParam;

public class ZipArchive {

	private static final String INFO_FILE_NAME = "info.txt";

	public static void create(GroupInfo groupInfo) {
		try {
			int length = groupInfo.getArraylist().toArray().length;
			
			String exportName = groupInfo.getHeader().replaceAll("\\s+", "") + groupInfo.getVersion(); 
			String exportNameValid = exportName.replaceAll("[^a-zA-Z0-9.-]", "_");
			File file = (new File(ProjectParam.USERHOME + /*"\\Desktop" +*/  "\\" + exportNameValid + ".zip"));
			System.out.println(file);
			FileOutputStream fos = new FileOutputStream(file);
			ZipOutputStream zos = new ZipOutputStream(fos);
			for (int i = 0; i < length; i++) {
				addToZipFile(new File (groupInfo.getPath() + "\\" +  ((File) groupInfo.getArraylist().get(i)).getName()), zos);
			}
			//add info file
			addToZipString((new InfoContentText()).create(groupInfo, exportNameValid), INFO_FILE_NAME, zos);
			zos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void addToZipString(String infoContent, String fileName, ZipOutputStream zos) throws IOException{

		ZipEntry zipEntry = new ZipEntry(fileName);
		zos.putNextEntry(zipEntry);
		
		//TODO encoding?
		byte content[] = infoContent.getBytes();
		zos.write(content);
		zos.closeEntry();
	}

	public static void addToZipFile(File file, ZipOutputStream zos) throws FileNotFoundException, IOException {

		System.out.println("Writing '" + file + "' to zip file");

		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			String name = file.getName();
			ZipEntry zipEntry = new ZipEntry(name);
			zos.putNextEntry(zipEntry);
			byte[] bytes = new byte[1024];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zos.write(bytes, 0, length);
			}
			zos.closeEntry();
			fis.close();			
		} else {
			LogFile.write("File can not be zipped. File does not exist: " + file.getAbsolutePath());
		}

	}

}