package gui.renderer;

import gui.table.FilterTable;
import gui.utilities.UtilitiesHiglight;

import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import structure.DummyIconPath;
import utilities.ProjectParam;

public class AdvancedCellRenderer extends TableCellRendererJTextArea {

//	private TableCellRenderer wrappedCellRenderer;
//	static int LENGTH_OF_TEXT_AREA = 20;
		

	public AdvancedCellRenderer() {
		super();
	}

	
	 @Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		 
	    /* 
	    * Implementation Note :
	    * It is important that no 'new' objects be present in this 
	    * implementation (excluding exceptions):
	    * if the table is large, then a large number of objects would be 
	    * created during rendering.
	    */
		 
		Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		if (FilterTable.text.length() != 0) {
			if (value instanceof String) {
				UtilitiesHiglight.findText((String)value,FilterTable.text); //FIXME value must be checked for String 
				if ((UtilitiesHiglight.start != -1) && (column != (FilterTable.columnNrDialog)) && (column != (FilterTable.columnNrExp))) {
					super.setBackground(ProjectParam.HIGHLIGHT_COL);
				} else {
					super.setBackground(null);
				}	
			
			} else {
				super.setBackground(null);	
			}
		} else {
			renderer.setBackground(row % 2 == 0 ? null : ProjectParam.ALTERNATING_ROW_COL);
		}
		return renderer;
	}
	 
 
//	 @Override
//    protected void setValue(Object value) {
//		if (value instanceof JTextArea) {
//			//setIcon(null);
//			//setText(t);
//			super.setValue(((JTextArea) value).getText());
//			//JTextArea jTextArea = (JTextArea) value;
//			//super.setText(jTextArea.getText());
//			//super.setText(jTextArea);   
//		} else if (value instanceof DummyIconPath) {
//			Toolkit toolkit = Toolkit.getDefaultToolkit();
//			String tmp = main.Main.ROOTH_PATH
//					+ "\\src\\img\\Folder_20x20.png";
//			ImageIcon icon = new ImageIcon(toolkit.getImage(tmp));
//			//super.setIcon(icon);
//			setText(null);
//		} else {
//			//setIcon(null);
//	        super.setText((value == null) ? "" : value.toString());
//		}
//    	
//
//    }
	 
//		private static JTextArea splitText(JTextArea jTextArea){
//			JTextArea jTextAreaSplit = new JTextArea();
//			jTextAreaSplit = jTextArea;
//			int length = jTextAreaSplit.getText().length();
//			int SplitNbr = length/LENGTH_OF_TEXTAREA;
//			
//			if (SplitNbr <= 0){
//				return jTextAreaSplit = jTextArea;
//			}
//			else {
//				
//				String Final = jTextArea.getText();
//				String Start = Final.substring(0,LENGTH_OF_TEXTAREA);
//				String End = Final.substring(LENGTH_OF_TEXTAREA,length);
//				jTextAreaSplit.setText(Start);
//				String Add;
//				
//				for (int i = 1; i <= SplitNbr; i++) {
//					int length1 = End.length();
//					if (length1 <= LENGTH_OF_TEXTAREA) {
//						Add = End.substring(0,length1);						
//					}
//					else {
//						Add = End.substring(0,LENGTH_OF_TEXTAREA);
//						End = End.substring(LENGTH_OF_TEXTAREA,length1);
//					}
//					jTextAreaSplit.append("\n" + Add);
//				}
//				System.out.println(jTextAreaSplit.getText());
//				return jTextAreaSplit; 
//			}
//		}



}
