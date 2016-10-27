package gui.editDialog;

import main.Main;
import structure.Group;
import structure.GroupCollection;
import structure.GroupComplete;
import structure.GroupInfo;

public class UpdateDataList {

	//GroupCollection dataGroupCollection = null;
	//GroupInfo dataGroupInfo = null;
		
	public UpdateDataList(GroupInfo dataGroupInfo, boolean edit, int row) {

		if (edit) {
			//edit
			Main.KeplastDataBaseList.getElementGroup().get(row).setGroupInfo(dataGroupInfo);	
		} else {
			//new
			GroupComplete groupComplete = new GroupComplete();
			groupComplete.setGroupInfo(dataGroupInfo);
			Main.KeplastDataBaseList.addListElement(groupComplete);
	 	}
	}

}

