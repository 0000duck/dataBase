package gui.editDialog.tabFiles;

import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;

import utilities.ProjectParam;

public class ListCellRendExtAll extends DefaultListCellRenderer {
	
	 private static final ImageIcon ICON_DISK = new ImageIcon(ProjectParam.ROOT_PATH + "\\src\\img\\File_15x15.png");
	 
//	 static String checkString;
 
  public ListCellRendExtAll() {
    super();
  }

  
  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
    setText(value.toString());
    
//    checkString = "KP_" + DialogFrame.textId.getText();
//    if (value.toString().startsWith(checkString)) {
//    	 setFont(new Font("Arial",Font.BOLD,12));
//    } else {
//    	setFont(new Font("Arial",Font.PLAIN,12));
//	}
    setFont(new Font("Arial",Font.PLAIN,12));
    setIcon(ICON_DISK);
    if (isSelected) {
      setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());
    } else {
      setBackground(list.getBackground());
      setForeground(list.getForeground());
    }
    return this;
  }
}

