package main.utilities;


import javax.swing.JTextArea;

import structure.DummyFileInfo;
import structure.DummyIconPath;
import structure.GroupCollection;
import structure.GroupComplete;

public class ConvertStructureToArray {

		//private static final int LENGTH_OF_TEXTAREA = 50;
		
		private static Object[][] 	arrayTwoDim;
		private static int 			sizeColumn = 9, sizeRow;
		
		public static Object[][] convert (GroupCollection groupCollection) {
			
			sizeRow = groupCollection.getElementGroup().size();
			arrayTwoDim = new Object[sizeRow][sizeColumn]; //FIXME: is size really necessary?
			
			int actRow = 0;
			
			for (GroupComplete element : groupCollection.getElementGroup()){
				
				int actColumn = 0;
				
			  	//1. Header
				JTextArea jTextArea1 = new JTextArea();
				jTextArea1.setOpaque(true);
				jTextArea1.setText(element.getGroupInfo().getHeader());
				arrayTwoDim[actRow][actColumn] = jTextArea1;
				actColumn++;
				
				//2. Type
				JTextArea jTextArea2 = new JTextArea();
				jTextArea2.setOpaque(true);
				jTextArea2.setText(element.getGroupInfo().getType());
				arrayTwoDim[actRow][actColumn] = jTextArea2;
				actColumn++;
				
				//3. Description
				JTextArea jTextArea3 = new JTextArea();
				jTextArea3.setOpaque(true);
				jTextArea3.setText(element.getGroupInfo().getDescription());
			    jTextArea3.setLineWrap(true);
			    jTextArea3.setWrapStyleWord(true);
				arrayTwoDim[actRow][actColumn] = jTextArea3;
				actColumn++;	
				
				//4. Keywords
				JTextArea jTextArea4 = new JTextArea();
				jTextArea4.setOpaque(true);
				jTextArea4.setText(element.getGroupInfo().getKeyWords());
				arrayTwoDim[actRow][actColumn] = jTextArea4;
				actColumn++;	
				
				//5. Id
				JTextArea jTextArea5 = new JTextArea();
				jTextArea5.setOpaque(true);
				jTextArea5.setText(element.getGroupInfo().getId());
				arrayTwoDim[actRow][actColumn] = jTextArea5;
				actColumn++;
				

				//6. Autor
				JTextArea jTextArea6 = new JTextArea();
				jTextArea6.setOpaque(true);
				jTextArea6.setText(element.getGroupInfo().getAutor());
				arrayTwoDim[actRow][actColumn] = jTextArea6;
				actColumn++;
				
				//7. Version
				JTextArea jTextArea7 = new JTextArea();
				jTextArea7.setOpaque(true);
				jTextArea7.setText(element.getGroupInfo().getVersion());
				arrayTwoDim[actRow][actColumn] = jTextArea7;
				actColumn++;
				
				//8. Dummy Object for Icon 
				DummyIconPath dummyIconPath = new DummyIconPath();
				arrayTwoDim[actRow][actColumn] = dummyIconPath;
				actColumn++;

				//9. Dummy Object for Icon 
				DummyFileInfo dummyFileInfo = new DummyFileInfo();
				arrayTwoDim[actRow][actColumn] = dummyFileInfo;
				actColumn++;
								
				actRow++;
			}
			return arrayTwoDim;
		}
		
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
