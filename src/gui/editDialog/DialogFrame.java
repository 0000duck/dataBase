package gui.editDialog;

import gui.editDialog.tabFiles.FileListAll;
import gui.editDialog.tabFiles.FileListSelected;
import gui.editDialog.tabFiles.TreeDataBase;
import gui.editDialog.tabGenInfo.BasicInfo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.TabbedPaneUI;

import main.Main;
import main.utilities.ConvertStructureToArray;
import structure.GroupComplete;
import structure.GroupInfo;
import structure.ShowMessageLabel;
import utilities.ProjectParam;

public class DialogFrame {

	private static JDialog jDialog;
	private ShowMessageLabel stateShownField;
	public static BasicInfo basicInfo = new BasicInfo();
	private static TreeDataBase treeDataBase;
	public static GroupInfo infoData;
	public static JLabel saveDirectory;
	public static JButton directoryButton;
	public static FileListAll fileList = null;
	public static FileListSelected fileListSelected = null;
	public static int row;
	public static boolean edit;

	public DialogFrame() {
		DialogFrame.edit = false;
		this.createFrame();
		basicInfo.newFile();
		jDialog.setVisible(true);
		// TODO: check if parameter passed to JDialog should be parent?
	}

	public DialogFrame(int row) {
		DialogFrame.edit = true;
		DialogFrame.row = row;
		this.createFrame();
		basicInfo.editFile();
		jDialog.setVisible(true);
	}

	public static String getSaveDirectory() {
		return saveDirectory.getText();
	}

	private void createFrame() {
		jDialog = new JDialog(ProjectParam.MAIN_FRAME, Dialog.ModalityType.APPLICATION_MODAL);
		jDialog.setTitle("Create DataBase File");
		jDialog.setModal(true);
		jDialog.setSize(new Dimension(570, 500));
		jDialog.setMinimumSize(new Dimension(450, 300));
		jDialog.setLocationRelativeTo(ProjectParam.MAIN_FRAME);
		addComponents();
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

	public static int getNextIdFile() {
		int id = ReadId.readIdAndIncrement(new File(ReadId.FILENAME));
		return id;
	}

	private void addComponents() {

		//remove blue border and gabs of tabbed pane
		UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(0,0,0,0));
		
		JTabbedPane tabbedPane = new JTabbedPane();
		//tabbedPane.setBackground(ProjectParam.MESSAGE_COL);
		
		
		// Text Label
		Font labelFont = new Font("Arial", Font.PLAIN, 13);
	
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(tabbedPane, BorderLayout.CENTER);
		
		BorderLayout listMainPanelEmptyBorder = new BorderLayout(0, 0);
		JPanel mainCenterPanel = new JPanel(listMainPanelEmptyBorder);

		Font headerFont = new Font("Arial", Font.BOLD, 14);

		JPanel listMainPanel = new JPanel(new BorderLayout());
		listMainPanel
				.setBorder(BorderFactory.createTitledBorder(null, "Add/Remove Files from Group", 0, 0, headerFont));

		// listMainPanel.setBorder(new EmptyBorder(5,0,0,0));
		// JPanel outerPanel2 = new JPanel (new BorderLayout(20,20));
		GridLayout mainCenterSouthGrid = new GridLayout(2, 1);
		mainCenterSouthGrid.setVgap(5);
		JPanel mainCenterSouthPanel = new JPanel(mainCenterSouthGrid);
		// mainCenterSouthPanel.setBorder(new EmptyBorder(0,5,0,5));

		JPanel treeMainPanel = new JPanel(new BorderLayout());
		treeMainPanel.setBorder(BorderFactory.createTitledBorder(null, "Save Directory", 0, 0, headerFont));
		JPanel treeMainInnerPanel = new JPanel(new BorderLayout());
		// treeMainInnerPanel.setBorder(new EmptyBorder(5,5,5,5));
		JPanel treeMainInnerSouthPanel = new JPanel(new BorderLayout());
		saveDirectory = new JLabel();
		saveDirectory.setFont(labelFont);
		treeMainInnerSouthPanel.add(saveDirectory, BorderLayout.CENTER);
		treeMainInnerPanel.add(treeMainInnerSouthPanel, BorderLayout.SOUTH);
		treeDataBase = new TreeDataBase();
		treeMainInnerPanel.add(treeDataBase, BorderLayout.CENTER);
		// treeMainInnerPanel.setBorder(new EmptyBorder(0,2,2,2));
		treeMainPanel.add(treeMainInnerPanel, BorderLayout.CENTER);
		// mainCenterSouthPanel.add(treeMainPanel,BorderLayout.NORTH);
		//mainCenterSouthPanel.add(listMainPanel,BorderLayout.CENTER);
		mainCenterPanel.add(basicInfo, BorderLayout.NORTH);
		tabbedPane.addTab("Info", mainCenterPanel);
		tabbedPane.addTab("Directory", treeMainPanel);
		tabbedPane.addTab("Files", listMainPanel);
		

		BorderLayout mainCenterSouthOuterGapPanelEmptyBorder = new BorderLayout(5, 5);
		JPanel mainCenterSouthOuterGapPanel = new JPanel(mainCenterSouthOuterGapPanelEmptyBorder);
		mainCenterSouthOuterGapPanel.add(mainCenterSouthPanel, BorderLayout.CENTER);
		mainCenterSouthOuterGapPanel.setBorder(new EmptyBorder(0, 5, 0, 5));

		mainCenterPanel.add(mainCenterSouthOuterGapPanel, BorderLayout.CENTER);
		//mainPanel.add(mainCenterPanel, BorderLayout.CENTER);
		

		// Add Remove Files from list
		JPanel listArrangePanel = new JPanel(new GridLayout(1, 2));
		listMainPanel.add(listArrangePanel, BorderLayout.CENTER);

		fileList = new FileListAll();
		listArrangePanel.add(fileList);
		fileListSelected = new FileListSelected();
		listArrangePanel.add(fileListSelected);

		stateShownField = new ShowMessageLabel();
		// stateShownField.setText("Hello");
		stateShownField.setFont(new Font("Arial", Font.PLAIN, 12));
		stateShownField.setForeground(ProjectParam.NO_MATCH_COL);
		// outerPanel2.add(stateShownField,BorderLayout.SOUTH);

		directoryButton = new JButton("Select");
		directoryButton.setBackground(Color.WHITE);
		directoryButton.setFont(labelFont);
		directoryButton.setPreferredSize(new Dimension(80, 25));
		treeMainInnerSouthPanel.add(directoryButton, BorderLayout.EAST);
		directoryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent action) {
				if (!(treeDataBase.m_display.getText().isEmpty())
						&& !(treeDataBase.m_display.getText().equalsIgnoreCase(saveDirectory.getText()))) {
					saveDirectory.setText(treeDataBase.m_display.getText());
					saveDirectory.setToolTipText(saveDirectory.getText());
					fileList.addListEntries(saveDirectory.getText());
					fileListSelected.removeAllEntries();
					// } else {
					// fileList.removeAll();
				}
			}
		});

		// FRAME SOUTH
		JButton createButton = new JButton("Save");
		createButton.setBackground(Color.WHITE);
		createButton.setFont(labelFont);
		createButton.setPreferredSize(new Dimension(100, 25));
		// createButton.setMaximumSize(new Dimension(15, 30));
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent action) {
				CheckUserInput checkInput = new CheckUserInput(); // Main.KeplastDataBaseList.getElementGroup().get(row).getGroupInfo1().getPath()
				if (checkInput.versionCheck) {
					if (!(saveDirectory.getText().isEmpty())) {
						if (edit) {
							CreateInfoDataBaseFile createFile = new CreateInfoDataBaseFile(saveDirectory.getText(),
									infoData.getId());
							new UpdateDataList(createFile.getGroupInfoData(), edit, row);
						} else {
							CreateInfoDataBaseFile createFile = new CreateInfoDataBaseFile(saveDirectory.getText());
							new UpdateDataList(createFile.getGroupInfoData(), edit, row);
						}
					}
					basicInfo.textVersion.setBackground(null);
					// addDialog.setVisible(false);
					// addDialog.dispose();
					// frame.dispose();
					jDialog.dispose();
					// FilterTable.dialogFrame = null;
					// ProjectParam.FILTER_TABLE.update();
					ProjectParam.FILTER_TABLE
							.updateTableData(ConvertStructureToArray.convert(Main.KeplastDataBaseList));
				} else {
					basicInfo.textVersion.setBackground(ProjectParam.NO_MATCH_COL);
					stateShownField.setTimerText("Syntex Version Input wrong", 2);
				}

			}
		});

		// Flow layout so button only has requested size and does not span the total horizontal width
		JPanel panelSave = new JPanel(new FlowLayout());

		// JPanel panelSouthButton = new JPanel (newGridLayout(1,1))
		JPanel mainPanelSouth = new JPanel(new BorderLayout());

		panelSave.add(createButton);
		mainPanelSouth.add(panelSave, BorderLayout.CENTER);
		mainPanel.add(mainPanelSouth, BorderLayout.SOUTH);

		jDialog.add(mainPanel);
		// frame.setResizable(false);

	}
	


}
