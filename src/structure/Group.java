package structure;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable{

	private ArrayList<File> ElementGroup;

	public Group() {
		ElementGroup = new ArrayList<File>();
	}

	public ArrayList<File> getElementGroup() {
		return ElementGroup;
	}

	public void addListElement(File fileBase) {
		ElementGroup.add(fileBase);
	}

	public void addList(ArrayList<File> list) {
		ElementGroup = list;
	}
	
	
	//getter methods

//	public static void main(String[] args) {
//
//		Group keplastGroup = new Group();
//		
//		for (int i = 0; i < 3; i++) {
//			FileBase file1Base = new FileBase();
//			file1Base.setDescription("Feature for ramping down servo valve Output");
//			file1Base.setHeader("Servo Valve Ramp");
//			//file1Base.setId(00005);
//			file1Base.setKeywords("ServoValve,Offset,Ramp");
//			file1Base.setVersion("1.0");
//			file1Base.setPath("D:\\Keba\\TestPath\\");
//			keplastGroup.addListElement(file1Base);
//		}
//	}

}
