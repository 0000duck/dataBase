package structure;

import java.util.ArrayList;
import java.util.List;

public class GroupCollection { //TODO: delete this class

	private List<GroupComplete> groupElements;

	public GroupCollection() {
		groupElements = new ArrayList<GroupComplete>();
	}

	public List<GroupComplete> getElementGroup() {
		return groupElements;
	}

	public void addListElement(GroupComplete keplastGroup) {
		groupElements.add(keplastGroup);
	}
}
