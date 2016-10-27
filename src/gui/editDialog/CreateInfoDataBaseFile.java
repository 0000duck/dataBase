package gui.editDialog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

import gui.editDialog.tabFiles.FileListSelected;
import structure.GroupInfo;
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

	private GroupInfo dataGroupInfo = null;

	private String absolutePath = null;
	private String name = null;

//	public CreateInfoDataBaseFile() {
//		int Id = DialogFrame.getNextId();
//		name = ProjectParam.ROOT_PATH + "\\" + String.format("%08d",Id) + infoFileType;
//		makeFile();
//	}

	public CreateInfoDataBaseFile(String path) {
		absolutePath = path;
                Id = DialogFrame.getNextIdFile();
		name = path + "\\" + "KP_" + String.format(formatter,Id) + infoFileType;
		makeFile();
	}
	
	public CreateInfoDataBaseFile(String path, String Idtmp) {
		absolutePath = path;
                Id = Integer.parseInt(Idtmp);
		name = path + "\\" + "KP_" + String.format(formatter,Id) + infoFileType;
		makeFile();
	}

	public void makeFile() {
		try {

			fileWriter = new FileWriter(name);

			//Collect Data of Dialog
			dataGroupInfo = new GroupInfo();
			dataGroupInfo.setHeader(DialogFrame.basicInfo.textHeader.getText() );			
			dataGroupInfo.setId(String.format(formatter,Id));
			dataGroupInfo.setType(((String) DialogFrame.basicInfo.comboBoxType.getSelectedItem()).toString());
			dataGroupInfo.setVersion(DialogFrame.basicInfo.textVersion.getText());
			dataGroupInfo.setAutor(DialogFrame.basicInfo.textAutor.getText());
			dataGroupInfo.setKeywords(DialogFrame.basicInfo.textKeywords.getText() );		
			dataGroupInfo.setDescription(DialogFrame.basicInfo.textDescription.getText());
			for (int i = 0; i < FileListSelected.listModelSelected.getSize(); i++) {
				dataGroupInfo.setFileListElement(new File((String) FileListSelected.listModelSelected.getElementAt(i)));
			}
			dataGroupInfo.setPath(absolutePath);		

			//write File
			writeLine(conHeader + DialogFrame.basicInfo.textHeader.getText() + conHeader);
			writeLine(conId + String.format(formatter,Id) + conId);
			writeLine(conType + ((String) DialogFrame.basicInfo.comboBoxType.getSelectedItem()).toString() + conType);
			writeLine(conVersion + DialogFrame.basicInfo.textVersion.getText() + conVersion);
			writeLine(conAutor + DialogFrame.basicInfo.textAutor.getText() + conAutor);
			writeLine(conKeywords + DialogFrame.basicInfo.textKeywords.getText() + conKeywords);
			writeLine(conDescription + DialogFrame.basicInfo.textDescription.getText() + conDescription);
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
	
	public GroupInfo getGroupInfoData (){
		return dataGroupInfo;
	}
}
