package gui.addDialog;

import gui.addDialog.fileList.FileListAll;
import gui.addDialog.fileList.FileListSelected;
import gui.addDialog.tree.TreeDataBase;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComboBoxUI;

import com.sun.java.swing.plaf.motif.MotifBorders.BevelBorder;

import main.Main;
import main.utilities.ConvertStructureToArray;
import structure.GroupComplete;
import structure.GroupInfo;
import structure.ShowMessageLabel;
import sun.font.TextLabel;
import utilities.ProjectParam;

public class DialogFrame {

	private static JDialog jDialog; 
	
	private ShowMessageLabel stateShownField;
	
	//TODO: make this variables non static, fix access from outside
	public static JTextField textHeader;
	//public static JLabel textId;
	//public static JTextField textType;
	public static JComboBox<String[]> comboBoxType;
	public static JTextField textVersion;
	public static JTextField textAutor;
	public static JTextField textKeywords;
	public static JTextArea textDescription;
	
	private static TreeDataBase treeDataBase;
	private GroupInfo info;
	
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
	
	public static String getSaveDirectory() {
		return saveDirectory.getText();
	}

	private void createFrame() {
		//frame = new JFrame( "Create *.dataBase File");
		//frame.setPreferredSize(new Dimension(570,712));
		//frame.setLocationRelativeTo(CustomConstant.MAIN_FRAME);
		jDialog= new JDialog(ProjectParam.MAIN_FRAME,Dialog.ModalityType.APPLICATION_MODAL);
		jDialog.setTitle("Create DataBase File");
     	jDialog.setModal(true);
     	jDialog.setSize(new Dimension(570,712));
     	jDialog.setMinimumSize(new Dimension(450,600));
     	jDialog.setLocationRelativeTo(ProjectParam.MAIN_FRAME);
		addComponents();

//		jDialog.setLocationRelativeTo(CustomConstant.MAIN_FRAME);
		//frame.pack();
		//frame.setVisible(true);
        
	}
	
	public static int getNextId() {

		int tmpId = 0;
		for (GroupComplete element : Main.KeplastDataBaseList.getElementGroup()) {
			int actId = Integer.parseInt(element.getGroupInfo().getId());
			if (actId > tmpId) {
				tmpId = actId;
			}
		}
		return tmpId + 1;
	}
	
	public static int getNextIdFile(){
		int id = ReadId.readIdAndIncrement(new File(ReadId.FILENAME));
		return id;
	}


	private void editFile() {
		GroupComplete complete = Main.KeplastDataBaseList.getElementGroup().get(row);
		info = complete.getGroupInfo();

		textHeader.setText(info.getHeader());
		//textId.setText(info.getId());
		//textType.setText(info.getType());
		comboBoxType.setSelectedItem(info.getType());
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
		 //textId.setText(addDigits(getNextIdFile()));
		 saveDirectory.setText(ProjectParam.ROOT_PATH );
		 saveDirectory.setToolTipText(saveDirectory.getText());
		 fileList.addListEntries(saveDirectory.getText());
		 directoryButton.setEnabled(true);
		 
		 //prefill textfields
		 textVersion.setText("1.0");
		 textAutor.setText(System.getProperty("user.name"));
		 
	}	


	private void addComponents() {
		
		//Text Label
		Font labelFont = new Font("Arial", Font.PLAIN, 13);
		
	
		int textWidth, textWidthShort;
		textWidth = 70;
		textWidthShort = 20;
		
		//Input Header
		JLabel labelHeader = new JLabel ("Header");
		labelHeader.setFont(labelFont);
		JPanel containerHeader = new JPanel(new BorderLayout());
		labelHeader.setPreferredSize(new Dimension(textWidth,0));
		containerHeader.add(labelHeader,BorderLayout.WEST);
		textHeader = new JTextField ();
		containerHeader.add(textHeader,BorderLayout.CENTER);
		
//		//Input Id
//		JLabel labelId = new JLabel ("Id");
//		labelId.setFont(labelFont);
//		 textId = new JLabel ();
//		 textId.setFont(labelFont);
//		 //textId.setEditable(false);
		
		
		//Input Type
		JLabel labelType = new JLabel ("Type");
		labelType.setFont(labelFont);
		JPanel containerType = new JPanel(new BorderLayout());
		labelType.setPreferredSize(new Dimension(textWidth,0));
		containerType.add(labelType,BorderLayout.WEST);
		comboBoxType = new JComboBox(ProjectParam.SELECT_OPTIONS);
		comboBoxType.setRenderer(new ListCellRendererCust());
		comboBoxType.setFocusable(false);
		comboBoxType.setForeground(Color.BLACK);
		comboBoxType.setBackground(Color.WHITE);
		comboBoxType.setBorder(new EmptyBorder(0,0,0,1));
		comboBoxType.setFont(new Font("Arial",Font.PLAIN,13));
		//textType = new JTextField();
		containerType.add(comboBoxType,BorderLayout.CENTER);
		
		//Input Version
		JLabel labelVersion = new JLabel ("Version");
		labelVersion.setFont(labelFont);
		JPanel containerVersion = new JPanel(new BorderLayout());
		labelVersion.setPreferredSize(new Dimension(textWidth,0));
		containerVersion.add(labelVersion,BorderLayout.WEST);
		textVersion = new JTextField();
		containerVersion.add(textVersion,BorderLayout.CENTER);
		
		//Input Autor
		JLabel labelAutor = new JLabel ("Autor");
		labelAutor.setFont(labelFont);
		JPanel containerAutor = new JPanel(new BorderLayout());
		labelAutor.setPreferredSize(new Dimension(textWidth,0));
		containerAutor.add(labelAutor,BorderLayout.WEST);
		textAutor = new JTextField();
		containerAutor.add(textAutor,BorderLayout.CENTER);
		
		//Input Keywords
		JLabel labelKeywords = new JLabel ("Keywords");
		labelKeywords.setFont(labelFont);
		JPanel containerKeywords = new JPanel(new BorderLayout());
		labelKeywords.setPreferredSize(new Dimension(textWidth,0));
		containerKeywords.add(labelKeywords,BorderLayout.WEST);
		textKeywords = new JTextField();
		containerKeywords.add(textKeywords,BorderLayout.CENTER);
		
		//Input Description
		JLabel labelDescription = new JLabel ("Description");
		labelDescription.setFont(labelFont);
		JPanel containerDescription = new JPanel(new BorderLayout());
		containerDescription.add(labelDescription,BorderLayout.WEST);
		labelDescription.setPreferredSize(new Dimension(textWidth,0));
		textDescription = new JTextArea (6,0);
		JScrollPane scrollpaneDescription = new JScrollPane(textDescription);
		textDescription.setLineWrap(true);
		textDescription.setWrapStyleWord(true);
		containerDescription.add(scrollpaneDescription,BorderLayout.CENTER);
		
		saveDirectory = new JLabel();
		saveDirectory.setFont(labelFont);
		
//		SpringLayout springLayout = new SpringLayout();
		
		Font headerFont = new Font("Arial", Font.BOLD, 14);
		
		JPanel infoFileContentPanel = new JPanel (new BorderLayout());
		infoFileContentPanel.setBorder(BorderFactory.createTitledBorder(null,"Info File Content",0,0,headerFont));
		
		JPanel infoFileContentPanelCenter = new JPanel(new GridLayout(5,1));

		infoFileContentPanelCenter.add(containerHeader);
		infoFileContentPanelCenter.add(containerType);
		infoFileContentPanelCenter.add(containerVersion);
		infoFileContentPanelCenter.add(containerAutor);
		infoFileContentPanelCenter.add(containerKeywords);
		
		infoFileContentPanel.add(containerDescription,BorderLayout.SOUTH);
		infoFileContentPanel.add(infoFileContentPanelCenter,BorderLayout.CENTER);
		
		JPanel mainCenterOuterGapPanel = new JPanel (new BorderLayout());
		mainCenterOuterGapPanel.add(infoFileContentPanel,BorderLayout.CENTER);
		mainCenterOuterGapPanel.setBorder(new EmptyBorder(5,5,5,5));
	
		//inputPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		infoFileContentPanel.setPreferredSize(new Dimension (0,240));
		JPanel mainPanel = new JPanel (new BorderLayout());
		BorderLayout listMainPanelEmptyBorder = new BorderLayout(0,0);
		JPanel mainCenterPanel = new JPanel (listMainPanelEmptyBorder);
		
		JPanel listMainPanel = new JPanel (new BorderLayout());
		listMainPanel.setBorder(BorderFactory.createTitledBorder(null, "Add/Remove Files from Group",0,0,headerFont));
		
		//listMainPanel.setBorder(new EmptyBorder(5,0,0,0));
		//JPanel outerPanel2 = new JPanel (new BorderLayout(20,20));
		GridLayout mainCenterSouthGrid = new GridLayout(2,1);
		mainCenterSouthGrid.setVgap(5);
		JPanel mainCenterSouthPanel = new JPanel (mainCenterSouthGrid);
		//mainCenterSouthPanel.setBorder(new EmptyBorder(0,5,0,5));
		
		JPanel treeMainPanel = new JPanel (new BorderLayout());
		treeMainPanel.setBorder(BorderFactory.createTitledBorder(null,"Save Directory",0,0,headerFont));
		JPanel treeMainInnerPanel = new JPanel(new BorderLayout());
		//treeMainInnerPanel.setBorder(new EmptyBorder(5,5,5,5));
		JPanel treeMainInnerSouthPanel = new JPanel(new BorderLayout());
		treeMainInnerSouthPanel.add(saveDirectory,BorderLayout.CENTER);
		treeMainInnerPanel.add(treeMainInnerSouthPanel,BorderLayout.SOUTH);
		treeDataBase = new TreeDataBase();
		treeMainInnerPanel.add(treeDataBase,BorderLayout.CENTER);
		//treeMainInnerPanel.setBorder(new EmptyBorder(0,2,2,2));
		treeMainPanel.add(treeMainInnerPanel,BorderLayout.CENTER);
		mainCenterSouthPanel.add(treeMainPanel,BorderLayout.NORTH);
		mainCenterSouthPanel.add(listMainPanel,BorderLayout.CENTER);
		mainCenterPanel.add(mainCenterOuterGapPanel,BorderLayout.NORTH);
		
		BorderLayout mainCenterSouthOuterGapPanelEmptyBorder = new BorderLayout(5,5);
		JPanel mainCenterSouthOuterGapPanel = new JPanel (mainCenterSouthOuterGapPanelEmptyBorder);
		mainCenterSouthOuterGapPanel.add(mainCenterSouthPanel,BorderLayout.CENTER);
		mainCenterSouthOuterGapPanel.setBorder(new EmptyBorder(0,5,0,5));
		
		mainCenterPanel.add(mainCenterSouthOuterGapPanel,BorderLayout.CENTER);
		mainPanel.add(mainCenterPanel,BorderLayout.CENTER);
		
		//Add Remove Files from list
		JPanel listArrangePanel = new JPanel(new GridLayout(1,2));
		listMainPanel.add(listArrangePanel, BorderLayout.CENTER);
		
		fileList = new FileListAll();
		listArrangePanel.add(fileList);
		fileListSelected = new FileListSelected();
		listArrangePanel.add(fileListSelected);
		

		
		stateShownField = new ShowMessageLabel();
		//stateShownField.setText("Hello");
		stateShownField.setFont(new Font("Arial",Font.PLAIN,12));
		stateShownField.setForeground(ProjectParam.NO_MATCH_COL);
		//outerPanel2.add(stateShownField,BorderLayout.SOUTH);
		
		
		directoryButton = new JButton("Select");
		directoryButton.setFont(labelFont);
		directoryButton.setPreferredSize(new Dimension(80,25));
		treeMainInnerSouthPanel.add(directoryButton,BorderLayout.EAST);
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
		createButton.setPreferredSize(new Dimension(100, 25));
//		createButton.setMaximumSize(new Dimension(15, 30));
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent action) {
				CheckUserInput checkInput = new CheckUserInput(); // Main.KeplastDataBaseList.getElementGroup().get(row).getGroupInfo1().getPath()
				if (checkInput.versionCheck) {
					if (!(saveDirectory.getText().isEmpty())) {
						if (edit) {
						        CreateInfoDataBaseFile createFile = new CreateInfoDataBaseFile(saveDirectory.getText(),info.getId());
						        new UpdateDataList(createFile.getGroupInfoData(), edit, row);
						} else {
							CreateInfoDataBaseFile createFile = new CreateInfoDataBaseFile(saveDirectory.getText());
						        new UpdateDataList(createFile.getGroupInfoData(), edit, row);
						}
					}
					textVersion.setBackground(null);
					// addDialog.setVisible(false);
					// addDialog.dispose();
					// frame.dispose();
					jDialog.dispose();
					// FilterTable.dialogFrame = null;
					//ProjectParam.FILTER_TABLE.update();
					ProjectParam.FILTER_TABLE.updateTableData(ConvertStructureToArray.convert(Main.KeplastDataBaseList));
				} else {
					textVersion.setBackground(ProjectParam.NO_MATCH_COL);
					stateShownField.setTimerText("Syntex Version Input wrong", 2);
				}

			}
		});

		//Flow layout so button only has requested size and does not span the total horizontal width
		JPanel panelSave = new JPanel(new FlowLayout());
		
		// JPanel panelSouthButton = new JPanel (newGridLayout(1,1))
		JPanel mainPanelSouth = new JPanel(new BorderLayout());

		panelSave.add(createButton);
		mainPanelSouth.add(panelSave, BorderLayout.CENTER);
		mainPanel.add(mainPanelSouth, BorderLayout.SOUTH);

		jDialog.add(mainPanel);
//		frame.setResizable(false);

	}
	
	
	class ListCellRendererCust extends JLabel implements ListCellRenderer<Object> {
		public ListCellRendererCust() {
			setOpaque(true);
		}

		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {

			setText(value.toString());
			setFont(new Font("Arial",Font.PLAIN,13));
			
			Color background;
			Color foreground;
			
			if (isSelected){
				background = Color.WHITE;
				foreground = Color.BLACK;	
			} else {
				background = Color.WHITE;
				foreground = Color.GRAY;	
			}

			setBackground(background);
			setForeground(foreground);

			return this;
		}
	}


	
}
