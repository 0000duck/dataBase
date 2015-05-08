package gui.addDialog;

import gui.addDialog.fileList.FileListSelected;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

import utilities.ProjectParam;

public class CreateInfoDataBaseFile {

	private FileWriter fileWriter;

	public final static String conHeader = "<Header>";
	public final static String conId = "<Id>";
	public final static String conType = "<Type>";
	public final static String conVersion = "<Version>";
	public final static String conAutor = "<Autor>";
	public final static String conKeywords = "<Keywords>";
	public final static String conDescription = "<Description>";
	public final static String conFile = "<File>";

	public final static String infoFileType = ".dataBase";
	
	private final static String formatter = "%05d";
	private int Id;

	private String name = null;

//	public CreateInfoDataBaseFile() {
//		int Id = DialogFrame.getNextId();
//		name = ProjectParam.ROOT_PATH + "\\" + String.format("%08d",Id) + infoFileType;
//		makeFile();
//	}

	public CreateInfoDataBaseFile(String path) {
		Id = DialogFrame.getNextIdFile();
		name = path + "\\" + "KP_" + String.format(formatter,Id) + infoFileType;
		makeFile();
	}
	
	public CreateInfoDataBaseFile(String path, String Idtmp) {
		Id = Integer.parseInt(Idtmp);
		name = path + "\\" + "KP_" + String.format(formatter,Id) + infoFileType;
		makeFile();
	}

	public void makeFile() {
		try {

			fileWriter = new FileWriter(name);

			writeLine(conHeader + DialogFrame.textHeader.getText() + conHeader);
			writeLine(conId + String.format(formatter,Id) + conId);
			writeLine(conType + ((String) DialogFrame.comboBoxType.getSelectedItem()).toString() + conType);
			writeLine(conVersion + DialogFrame.textVersion.getText() + conVersion);
			writeLine(conAutor + DialogFrame.textAutor.getText() + conAutor);
			writeLine(conKeywords + DialogFrame.textKeywords.getText() + conKeywords);
			writeLine(conDescription + DialogFrame.textDescription.getText() + conDescription);
			for (int i = 0; i < FileListSelected.listModelSelected.getSize(); i++) {
				writeLine(conFile + (String) FileListSelected.listModelSelected.getElementAt(i) + conFile);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fileWriter != null)
				try {
					fileWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	public void writeLine(String line) throws IOException {
		fileWriter.write(line);
		fileWriter.append(System.getProperty("line.separator")); // e.g. "\n"
	}
}
