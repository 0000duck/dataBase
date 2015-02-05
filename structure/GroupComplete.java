package structure;

public class GroupComplete {

	private Group group;
	private GroupInfo groupInfo;

	public GroupComplete() {
		groupInfo = new GroupInfo();
		group = new Group();
	}

	public Group getGroup() {
		return group;
	}

	public GroupInfo getGroupInfo() {
		return groupInfo;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public void setGroupInfo(GroupInfo groupInfo) {
		this.groupInfo = groupInfo;
	}

}
