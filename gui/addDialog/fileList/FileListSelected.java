package gui.addDialog.fileList;

import gui.addDialog.DialogFrame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.TreePath;

public class FileListSelected extends JPanel {
	
	static JList fileList;
	public static DefaultListModel listModelSelected;
		
	String clickedPath;
	int selectedidx;
	
	static JPopupMenu popup;
	
	public FileListSelected() {
		super();
		
		listModelSelected = new DefaultListModel();
		fileList = new JList(listModelSelected);
		//fileList.setLayoutOrientation(JList.VERTICAL_WRAP);
		//fileList.setVisibleRowCount(-1);
			
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.add(fileList);
		scrollPane.setPreferredSize(new Dimension(250,300));
		JPanel j = new JPanel(new BorderLayout());
		j.add(scrollPane,BorderLayout.CENTER);
		//scrollPane.setFont(new Font("Arial",Font.PLAIN,12));
		ListCellRendExtSelected listRenderer = new ListCellRendExtSelected();
		fileList.setCellRenderer(listRenderer);

		super.setLayout(new BorderLayout());
		super.add(j,BorderLayout.CENTER);
		//setPreferredSize(new Dimension(500,200));
		setBorder(new EmptyBorder(0,2,2,2));

		popup = new JPopupMenu();
		
//		Action aAdd = new AbstractAction("Add") {
//			public void actionPerformed(ActionEvent e) {
//
//				.add(clickedPath);
//				listModelSelected.remove(selectedidx);
//
//			}
//		};
//		popup.add(aAdd);
		
		Action aRemove = new AbstractAction("Remove") {
			public void actionPerformed(ActionEvent e) {
				removeElement();
			}


		};
		popup.add(aRemove);
		fileList.addMouseListener(new PopupTrigger());
	}
	
	public void addListEntries(String directory) {
		removeAllEntries();
		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		  		  JLabel tmpJLabel = new JLabel();
		  		  tmpJLabel.setText(listOfFiles[i].getPath());
		  		  listModelSelected.addElement(listOfFiles[i].getName());
		    	  System.out.println("File " + listOfFiles[i].getName());
//		      } else if (listOfFiles[i].isDirectory()) {
//		        System.out.println("Directory " + listOfFiles[i].getName());
		      }
		    }

	}
	
	
	public void removeAllEntries(){
		listModelSelected.removeAllElements();
	}
	
	class PopupTrigger extends MouseAdapter {
		public void mouseReleased(MouseEvent e) {
			if (e.isPopupTrigger()) {
				int x = e.getX();
				int y = e.getY();
				//TreePath path = fileList.getPathForLocation(x, y);
				String selString = (String) fileList.getSelectedValue();
				if (selString != null && !selString.isEmpty()) {
					popup.show(fileList, x, y);
					popup.updateUI();
					clickedPath = selString;
					selectedidx = fileList.getSelectedIndex();
					//m_clickedPath = path;
				}
				
			}
		}
	}
	
	public void removeElement() {
		DialogFrame.fileList.listModelAll.addElement(clickedPath);
		DialogFrame.fileListSelected.listModelSelected.remove(selectedidx);
	}

}


