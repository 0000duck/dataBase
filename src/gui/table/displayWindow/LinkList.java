package gui.table.displayWindow;

import java.awt.Desktop;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import structure.GroupInfo;

public class LinkList extends JList {
	
	private static GroupInfo m_groupInfo = null;
		
	public LinkList (final GroupInfo groupInfo){
		super();
		updateList(groupInfo);
		super.setBackground(UIManager.getColor(this));
		super.setLayoutOrientation(JList.VERTICAL_WRAP);
		//super.setPreferredSize(new Dimension(30,35));
		super.setCellRenderer(new LinkListCellRenderer());
		
		super.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (!event.getValueIsAdjusting()) {
					JList list = (JList) event.getSource();
					int selection = list.getSelectedIndex();
					File selectedFile = (File) list.getSelectedValue();
					try {
						System.out.println(m_groupInfo.getPath() + "\\" + selectedFile.getName());
						Desktop.getDesktop().open(new File(m_groupInfo.getPath() + "\\" + selectedFile.getName()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	public void updateList(final GroupInfo groupInfo) {
		m_groupInfo = groupInfo;
		super.setListData(groupInfo.getArraylist().toArray());
		
	}
}
