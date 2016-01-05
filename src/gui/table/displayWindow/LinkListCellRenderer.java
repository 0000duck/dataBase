package gui.table.displayWindow;

import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;

import utilities.ProjectParam;

public class LinkListCellRenderer extends DefaultListCellRenderer {
	
	final static ImageIcon m_TextFieldIcon = new ImageIcon(ProjectParam.ROOT_PATH_APPL + "\\src\\img\\File_15x15_.png");
																				
	public LinkListCellRenderer() {
		super();
	}
	
	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		setText(value.toString());
		setFont(new Font("Arial",Font.PLAIN,12));
	    setIcon(m_TextFieldIcon);

		return this;
	}
	
}
