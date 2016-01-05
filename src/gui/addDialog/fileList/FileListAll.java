package gui.addDialog.fileList;

import gui.addDialog.DialogFrame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
import javax.swing.TransferHandler;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.TreePath;

import utilities.ProjectParam;

public class FileListAll extends JPanel {
	
	static JList fileList;
	public static DefaultListModel<String> listModelAll;
	String clickedPath;
	int selectedidx;
	
	static JPopupMenu popup;
	
	public FileListAll() {
		super();
	
		listModelAll = new DefaultListModel<String>();
		fileList = new JList<String>(listModelAll);

		//fileList.setLayoutOrientation(JList.VERTICAL_WRAP);
		//fileList.setVisibleRowCount(-1);
			
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.add(fileList);
		scrollPane.setPreferredSize(new Dimension(250,300));
		JPanel j = new JPanel(new BorderLayout());
		j.add(scrollPane,BorderLayout.CENTER);
		//scrollPane.setFont(new Font("Arial",Font.PLAIN,12));
		ListCellRendExtAll listRenderer = new ListCellRendExtAll();
		fileList.setCellRenderer(listRenderer);

		super.setLayout(new BorderLayout());
		super.add(j,BorderLayout.CENTER);
		//setPreferredSize(new Dimension(500,200));
		setBorder(new EmptyBorder(0,2,2,2));

		fileList.setDragEnabled(true);
		fileList.setTransferHandler(new TransferHandler() {

			@Override
			public boolean canImport(TransferHandler.TransferSupport info) {
				// we only import FileList
				if (!info.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
					return false;
				}
				return true;
			}

			@Override
			public boolean importData(TransferHandler.TransferSupport info) {
				if (!info.isDrop()) {
					return false;
				}

				// Check for FileList flavor
				if (!info.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
					// displayDropLocation("List doesn't accept a drop of this type.");
					return false;
				}

				// Get the fileList that is being dropped.
				Transferable t = info.getTransferable();
				List<File> data;
				try {
					data = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);
				} catch (Exception e) {
					return false;
				}
				DefaultListModel model = (DefaultListModel) fileList.getModel();
				for (File file : data) {

					// 1. Add Elements in JList
					model.addElement(file.getName());

					// 2. Copy Files to target
					Path copySourcePath = Paths.get(file.getPath());
					Path copyTargetPath = Paths.get(DialogFrame.getSaveDirectory() + "\\" + file.getName());

					try {
						Files.copy(copySourcePath, copyTargetPath);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				return true;
			}
		});

		popup = new JPopupMenu();

		Action aAdd = new AbstractAction("Add") {
			public void actionPerformed(ActionEvent e) {

				addElement(clickedPath, selectedidx);

			}

		};

		popup.add(aAdd);

		fileList.addMouseListener(new PopupTrigger());
	}
	
	public void addListEntries(String directory) {
		removeAllEntries();
		File folder = new File(directory);
		
		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				String lower = name.toLowerCase();
				if (lower.startsWith("~")) //Office temporary files
					return false;
				else if (lower.equals("thumbs.db"))
					return false;
				else if (lower.endsWith(".database"))
					return false;

				return true;
			}
		};
		
		File[] listOfFiles = folder.listFiles(filter);

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		  		  JLabel tmpJLabel = new JLabel();
		  		  tmpJLabel.setText(listOfFiles[i].getPath());
		  		  listModelAll.addElement(listOfFiles[i].getName());
//		      } else if (listOfFiles[i].isDirectory()) {
//		        System.out.println("Directory " + listOfFiles[i].getName());
		      }
		    }

	}
	
	
	public void removeAllEntries(){
		listModelAll.removeAllElements();
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
					//popup.setVisible(true);
					popup.updateUI();
					clickedPath = selString;
					selectedidx = fileList.getSelectedIndex();
					//m_clickedPath = path;
				}
				
			}
		}
	}
	
	public void moveRegardingFiles(ArrayList<File> arrayList){
		for (File file: arrayList) {
			for (int j = 0; j < listModelAll.getSize(); j++) {
				if (file.getName().equalsIgnoreCase((String)listModelAll.getElementAt(j))) {
					addElement((String)listModelAll.getElementAt(j),j);
				}
			}			
		}
	}
	
	public void addElement(String path, int idx) {
		DialogFrame.fileListSelected.listModelSelected.addElement(path);
		DialogFrame.fileList.listModelAll.remove(idx);
		
	}

}


