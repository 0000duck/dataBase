package gui.table;

import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class DefaultTableModelExtend extends DefaultTableModel {
    
	Object[][] dataInt;
	
	public DefaultTableModelExtend(Object[][] data, Object[] columnNames) {
		super (data,columnNames);
		dataInt = data;
	}
	
	@Override
    public void setDataVector(Object[][] dataVector, Object[] columnIdentifiers) {
    	dataInt = dataVector;
    	setDataVector(convertToVector(dataVector), convertToVector(columnIdentifiers));
    }
	
	@Override
	public boolean isCellEditable(int row, int column) {
    	//PRE:  row > 0, column > 0
    	//POST: FCTVAL == false always
       	return false;
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		
		if (dataInt[row][column] instanceof JTextArea) {
			JTextArea jTextArea = (JTextArea) dataInt[row][column];
			return jTextArea.getText();
		} else 
		return super.getValueAt(row,column);
	}
	
//	public JTextArea getJTextArea (int row, int column){
//	if (dataInt[row][column] instanceof JTextArea) {
//		return (JTextArea) dataInt[row][column];
//	}
//	return null;
//			
//	}
	
	
}
