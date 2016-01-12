package gui.table.displayWindow;

import java.io.File;

import structure.GroupInfo;

public class InfoContentText {

	GroupInfo m_groupInfo = null;
	StringBuffer m_Text = new StringBuffer();
	String newLine = System.getProperty("line.separator");

	public String create(GroupInfo groupInfo, String name) {
		m_groupInfo = groupInfo;
		
		int nameLength = name.length();
		writeDecoration(nameLength); 	//decorate
		writeLine(m_groupInfo.getHeader(), true);
		writeDecoration(nameLength); 	//decorate

		writeEmtpyLine();
		writeLine("Autor   : " + m_groupInfo.getAutor(), true);
		writeLine("Id      : " + m_groupInfo.getId(), true);
		writeLine("Type    : " + m_groupInfo.getType(), true);
		writeLine("Version : " + m_groupInfo.getVersion(), true);
		writeLine("Path    : " + m_groupInfo.getPath(), true);
		if (m_groupInfo.getArraylist().size() != 0) {
			writeLine("Files", true);
			for (File file : m_groupInfo.getArraylist()) {
				writeLine(" * " + file.getName(), true);
			}
			
		}
		writeEmtpyLine();
		writeLine(m_groupInfo.getDescription(), true);
		return m_Text.toString();
	}

	private void writeDecoration(int nameLength) {
		for (int i = 1; i <= nameLength; i++) {
			boolean newLine = false;
			if (i == nameLength) 
				newLine = true;
			writeLine("=", newLine);
		}
		
	}

	private void writeEmtpyLine() {
		m_Text.append(newLine);

	}

	private void writeLine(String line, boolean newLine) {
		m_Text.append(line);
		if (newLine) 
			m_Text.append(System.getProperty("line.separator"));
	}

}
