package gui.table.displayWindow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import structure.GroupInfo;
import utilities.LogFile;
import utilities.ProjectParam;

public class ZipArchive {

	public static void create(GroupInfo groupInfo) {
		try {
			int length = groupInfo.getArraylist().toArray().length;
			File file = new File(ProjectParam.USERHOME + /*"\\Desktop" +*/  "\\" + groupInfo.getHeader().replaceAll("\\s+", "") + groupInfo.getVersion() + ".zip");
			System.out.println(file);
			FileOutputStream fos = null;
			fos = new FileOutputStream(file);
			ZipOutputStream zos = new ZipOutputStream(fos);
			for (int i = 0; i < length; i++) {
				addToZipFile(new File (groupInfo.getPath() + "\\" +  ((File) groupInfo.getArraylist().get(i)).getName()), zos);
			}
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
	
	

	public static void addToZipFile(File file, ZipOutputStream zos) throws FileNotFoundException, IOException {

		System.out.println("Writing '" + file + "' to zip file");

		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			String str = file.getName();
			ZipEntry zipEntry = new ZipEntry(str);
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