package gui.addDialog;

import gui.addDialog.fileList.FileListAll;
import gui.addDialog.fileList.FileListSelected;
import gui.addDialog.tree.TreeDataBase;
import gui.draw.MainFrame;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import main.Main;
import structure.GroupComplete;
import structure.GroupInfo;
import structure.ShowMessageLabel;
import utilities.ProjectParam;

public class DialogFrame {

	private JFrame frame;
	private JDialog jDialog; 
	
	private ShowMessageLabel stateShownField;
	
	//TODO: make this variables non static, fix access from outside
	public static JTextField textHeader;
	public static JLabel textId;
	public static JTextField textType;
	public static JTextField textVersion;
	public static JTextField textAutor;
	public static JTextField textKeywords;
	public static JTextArea textDescription;
	
	private static TreeDataBase treeDataBase;
	
	public static JLabel saveDirectory; 
	
	private JButton directoryButton; 
	
	public static FileListAll fileList = null;
	public static FileListSelected fileListSelected = null;
	
	int row;
	boolean edit;
	
	public DialogFrame() {
		this.edit = false;
		this.createFrame();
		this.newFile();
		jDialog.setVisible(true);
		//TODO: check if parameter passed to JDialog should be parent?
	}
	
	public DialogFrame(int row) {
		this.edit = true;
		this.row = row;
		this.createFrame();
		this.editFile();
		jDialog.setVisible(true);
		

	}

	private void createFrame() {
		//frame = new JFrame( "Create *.dataBase File");
		//frame.setPreferredSize(new Dimension(570,712));
		//frame.setLocationRelativeTo(CustomConstant.MAIN_FRAME);
		jDialog= new JDialog(ProjectParam.MAIN_FRAME,Dialog.ModalityType.APPLICATION_MODAL);
		jDialog.setTitle("Create DataBase File");
     	jDialog.setModal(true);
     	jDialog.setSize(new Dimension(570,712));
     	jDialog.setLocationRelativeTo(ProjectParam.MAIN_FRAME);
		addComponents();

//		jDialog.setLocationRelativeTo(CustomConstant.MAIN_FRAME);
		//frame.pack();
		//frame.setVisible(true);
        
	}
	
	private static int getNextId() {

		int tmpId = 0;
		for (GroupComplete element : Main.KeplastDataBaseList.getElementGroup()) {
			int actId = Integer.parseInt(element.getGroupInfo().getId());
			if (actId > tmpId) {
				tmpId = actId;
			}
		}
		return tmpId + 1;
	}

	//TODO: use generic code to produce leading zeros
	private static String addDigits(int Id) {
		String s = Integer.toString(Id);
		int length = s.length();
		String nullCont = "";
		int count = ProjectParam.ID_DIGITS - length;
		for (int i = 0; i < count; i++) {
			nullCont = nullCont.concat("0");
		}

		String idString = nullCont + s;
		return idString;
	}

	private void editFile() {
		GroupComplete complete = Main.KeplastDataBaseList.getElementGroup().get(row);
		GroupInfo info = complete.getGroupInfo();

		textHeader.setText(info.getHeader());
		textId.setText(info.getId());
		textType.setText(info.getType());
		//TODO: use InputVerifier to check if input is OK
		textVersion.setText(info.getVersion());
		textAutor.setText(info.getAutor());
		textKeywords.setText(info.getKeyWords());
		textDescription.setText(info.getDescription());
		saveDirectory.setText(info.getPath());
		saveDirectory.setToolTipText(saveDirectory.getText());
		fileList.addListEntries(saveDirectory.getText());
		directoryButton.setEnabled(false);
		fileList.moveRegardingFiles(info.getArraylist());
	}
	
	private void newFile () {
		 textId.setText(addDigits(getNextId()));
		 saveDirectory.setText(ProjectParam.ROOT_PATH );
		 saveDirectory.setToolTipText(saveDirectory.getText());
		 fileList.addListEntries(saveDirectory.getText());
		 directoryButton.setEnabled(true);
	}	

	private void addComponents() {
		
		//Text Label
		Font labelFont = new Font("Arial", Font.PLAIN, 13);
		
		JLabel labelHeader = new JLabel ("Header");
		labelHeader.setFont(labelFont);
		JLabel labelId = new JLabel ("Id");
		labelId.setFont(labelFont);
		JLabel labelType = new JLabel ("Type");
		labelType.setFont(labelFont);
		JLabel labelVersion = new JLabel ("Version");
		labelVersion.setFont(labelFont);
		JLabel labelAutor = new JLabel ("Autor");
		labelAutor.setFont(labelFont);
		JLabel labelKeywords = new JLabel ("Keywords");
		labelKeywords.setFont(labelFont);
		JLabel labelDescription = new JLabel ("Description");
		labelDescription.setFont(labelFont);
		
		saveDirectory = new JLabel();
		saveDirectory.setFont(labelFont);
		
		int textWidth, textWidthShort;
		textWidth = 40;
		textWidthShort = 20;
		
		//Input Field
		 textHeader = new JTextField (textWidth);

		 textId = new JLabel ();
		 textId.setFont(labelFont);
		 //textId.setEditable(false);

		 textType = new JTextField(textWidth);
		 textVersion = new JTextField(textWidth);
		 textAutor = new JTextField (textWidth);
		 textKeywords = new JTextField (textWidth);
		
		 textDescription = new JTextArea (6,textWidth);
		 JScrollPane scrollpaneDescription = new JScrollPane(textDescription);
		 textDescription.setLineWrap(true);
		 textDescription.setWrapStyleWord(true);
		
		
		SpringLayout springLayout = new SpringLayout();
		
		Font headerFont = new Font("Arial", Font.BOLD, 13);
		
		JPanel inputPanel = new JPanel (springLayout);
		inputPanel.setBorder(BorderFactory.createTitledBorder(null,"Info File Content",0,0,headerFont));
		JPanel outerPanel = new JPanel (new BorderLayout());
		outerPanel.add(inputPanel,BorderLayout.CENTER);
		outerPanel.setBorder(new EmptyBorder(15,15,10,15));
		//inputPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		inputPanel.setPreferredSize(new Dimension (100,280));
		JPanel mainPanel = new JPanel (new BorderLayout());
		JPanel mainCenter = new JPanel (new BorderLayout());
		JPanel mainCenterMain = new JPanel (new BorderLayout());
		
		treeDataBase = new TreeDataBase();
		mainCenterMain.setBorder(BorderFactory.createTitledBorder(null, "Add/Remove Files from Group",0,0,headerFont));
		JPanel outerPanel2 = new JPanel (new BorderLayout(20,20));
		outerPanel2.setBorder(new EmptyBorder(0,15,10,15));
		JPanel outerPanel3 = new JPanel (new BorderLayout());
		outerPanel3.setBorder(BorderFactory.createTitledBorder(null,"Save Directory",0,0,headerFont));
		JPanel innerPanel3 = new JPanel(new BorderLayout());
		innerPanel3.add(saveDirectory,BorderLayout.CENTER);
		innerPanel3.add(treeDataBase,BorderLayout.NORTH);
		innerPanel3.setBorder(new EmptyBorder(0,2,2,2));
		outerPanel3.add(innerPanel3,BorderLayout.CENTER);
		outerPanel2.add(outerPanel3,BorderLayout.NORTH);
		outerPanel2.add(mainCenterMain,BorderLayout.CENTER);
		mainCenter.add(outerPanel,BorderLayout.NORTH);
		mainCenter.add(outerPanel2,BorderLayout.CENTER);
		mainPanel.add(mainCenter,BorderLayout.CENTER);
		
		//------------------------------------------- FRAME CENTER

		inputPanel.add(labelHeader);
		inputPanel.add(textHeader);
		inputPanel.add(labelId);
		inputPanel.add(textId);
		inputPanel.add(labelType);
		inputPanel.add(textType);
		inputPanel.add(labelVersion);
		inputPanel.add(textVersion);
		inputPanel.add(labelAutor);
		inputPanel.add(textAutor);
		inputPanel.add(labelKeywords);
		inputPanel.add(textKeywords);
		inputPanel.add(labelDescription);
		inputPanel.add(scrollpaneDescription);

		int width1, width2;
		width1 = 2;
		width2 = 70;

		springLayout.putConstraint(SpringLayout.WEST, labelHeader, width1, SpringLayout.WEST, inputPanel);		
		springLayout.putConstraint(SpringLayout.NORTH, labelHeader, 5, SpringLayout.NORTH, inputPanel);	
		springLayout.putConstraint(SpringLayout.WEST, textHeader, width2, SpringLayout.WEST, inputPanel);		
		springLayout.putConstraint(SpringLayout.NORTH, textHeader, 5, SpringLayout.NORTH, inputPanel);	
				
		springLayout.putConstraint(SpringLayout.WEST, labelId, width1, SpringLayout.WEST, inputPanel);
		springLayout.putConstraint(SpringLayout.NORTH, labelId, 30, SpringLayout.NORTH, inputPanel);
		springLayout.putConstraint(SpringLayout.WEST, textId, width2, SpringLayout.WEST, inputPanel);
		springLayout.putConstraint(SpringLayout.NORTH, textId, 30, SpringLayout.NORTH, inputPanel);
		
		springLayout.putConstraint(SpringLayout.WEST, labelType, width1, SpringLayout.WEST, inputPanel);
		springLayout.putConstraint(SpringLayout.NORTH, labelType, 55, SpringLayout.NORTH, inputPanel);
		springLayout.putConstraint(SpringLayout.WEST, textType, width2, SpringLayout.WEST, inputPanel);
		springLayout.putConstraint(SpringLayout.NORTH, textType, 55, SpringLayout.NORTH, inputPanel);

		springLayout.putConstraint(SpringLayout.WEST, labelVersion, width1, SpringLayout.WEST, inputPanel);
		springLayout.putConstraint(SpringLayout.NORTH, labelVersion, 80, SpringLayout.NORTH, inputPanel);
		springLayout.putConstraint(SpringLayout.WEST, textVersion, width2, SpringLayout.WEST, inputPanel);
		springLayout.putConstraint(SpringLayout.NORTH, textVersion, 80, SpringLayout.NORTH, inputPanel);

		springLayout.putConstraint(SpringLayout.WEST, labelAutor, width1, SpringLayout.WEST, inputPanel);
		springLayout.putConstraint(SpringLayout.NORTH, labelAutor, 105, SpringLayout.NORTH, inputPanel);
		springLayout.putConstraint(SpringLayout.WEST, textAutor, width2, SpringLayout.WEST, inputPanel);
		springLayout.putConstraint(SpringLayout.NORTH, textAutor, 105, SpringLayout.NORTH, inputPanel);

		springLayout.putConstraint(SpringLayout.WEST, labelKeywords, width1, SpringLayout.WEST, inputPanel);
		springLayout.putConstraint(SpringLayout.NORTH, labelKeywords, 130, SpringLayout.NORTH, inputPanel);
		springLayout.putConstraint(SpringLayout.WEST, textKeywords, width2, SpringLayout.WEST, inputPanel);
		springLayout.putConstraint(SpringLayout.NORTH, textKeywords, 130, SpringLayout.NORTH, inputPanel);

		springLayout.putConstraint(SpringLayout.WEST, labelDescription, width1, SpringLayout.WEST, inputPanel);
		springLayout.putConstraint(SpringLayout.NORTH, labelDescription, 155, SpringLayout.NORTH, inputPanel);
		springLayout.putConstraint(SpringLayout.WEST, scrollpaneDescription, width2, SpringLayout.WEST, inputPanel);
		springLayout.putConstraint(SpringLayout.NORTH, scrollpaneDescription, 155, SpringLayout.NORTH, inputPanel);
		
		fileList = new FileListAll();
		mainCenterMain.add(fileList,BorderLayout.WEST);
		fileListSelected = new FileListSelected();
		mainCenterMain.add(fileListSelected,BorderLayout.EAST);
		

		
		stateShownField = new ShowMessageLabel();
		//stateShownField.setText("Hello");
		stateShownField.setFont(new Font("Arial",Font.PLAIN,12));
		stateShownField.setForeground(ProjectParam.NO_MATCH_COL);
		//outerPanel2.add(stateShownField,BorderLayout.SOUTH);
		
		
		directoryButton = new JButton("Select");
		directoryButton.setFont(labelFont);
		directoryButton.setPreferredSize(new Dimension(80,25));
		innerPanel3.add(directoryButton,BorderLayout.EAST);
		directoryButton.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent action) {
				if (!(treeDataBase.m_display.getText().isEmpty()) 
					&& !(treeDataBase.m_display.getText().equalsIgnoreCase(saveDirectory.getText()))) {
					saveDirectory.setText(treeDataBase.m_display.getText());
					saveDirectory.setToolTipText(saveDirectory.getText());
					fileList.addListEntries(saveDirectory.getText());
					fileListSelected.removeAllEntries();
//				} else {
//					fileList.removeAll();
				}
			}	
		});
		
		
		// FRAME SOUTH
		JButton createButton = new JButton("Save");
		createButton.setFont(labelFont);
		createButton.setPreferredSize(new Dimension(15, 30));
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent action) {
				CheckUserInput checkInput = new CheckUserInput(); // Main.KeplastDataBaseList.getElementGroup().get(row).getGroupInfo1().getPath()
				if (checkInput.versionCheck) {
//					if (edit) {
					if (!(saveDirectory.getText().isEmpty())) {
						new CreateInfoDataBaseFile(saveDirectory.getText());	
					}	
//					} else {
//						CreateInfoDataBaseFile createFile = new CreateInfoDataBaseFile();
//					}
					textVersion.setBackground(null);
	//				addDialog.setVisible(false);
	//				addDialog.dispose();
					//frame.dispose();
					jDialog.dispose();
	//				FilterTable.dialogFrame = null;		
					ProjectParam.FILTER_TABLE.update();
				} else {
					textVersion.setBackground(ProjectParam.NO_MATCH_COL);
					stateShownField.setTimerText("Syntex Version Input wrong", 2);
				}

			}
		});

		// JPanel panelSouthButton = new JPanel (newGridLayout(1,1))
		JPanel mainPanelSouth = new JPanel(new BorderLayout());

		mainPanelSouth.setBorder(new EmptyBorder(0, 240, 10, 240));
		mainPanelSouth.add(createButton, BorderLayout.CENTER);
		mainPanel.add(mainPanelSouth, BorderLayout.SOUTH);

		jDialog.add(mainPanel);
//		frame.setResizable(false);

	}
}
