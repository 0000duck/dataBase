package gui.editDialog.tabGenInfo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import gui.editDialog.DialogFrame;
import main.Main;
import structure.GroupComplete;
import structure.GroupInfo;
import utilities.ProjectParam;

public class BasicInfo extends JPanel {

	//TODO: make this variables non static, fix access from outside
	public JTextField textHeader;
	//public static JLabel textId;
	//public static JTextField textType;
	public JComboBox<String[]> comboBoxType;
	public JTextField textVersion;
	public JTextField textAutor;
	public JTextField textKeywords;
	public JTextArea textDescription;
	public JCheckBox chkIntern;
	
	public BasicInfo () {
		
		int textWidth, textWidthShort;
		textWidth = 70;
		textWidthShort = 20;
		
		//Text Label
		Font labelFont = new Font("Arial", Font.PLAIN, 13);
		
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
		
		infoFileContentPanel.setPreferredSize(new Dimension (0,240));
		
		this.setLayout(new BorderLayout());
		this.add(infoFileContentPanel,BorderLayout.CENTER);
		this.setBorder(new EmptyBorder(5,5,5,5));

		JPanel addInfoFileContentPanel = new JPanel (new BorderLayout());
		addInfoFileContentPanel.setBorder(BorderFactory.createTitledBorder(null,"Additional Info",0,0,headerFont));
		
		JPanel addinfoFileContentPanelCenter = new JPanel(new GridLayout(1,1));
	
		//Input Intern
		JLabel labelIntern = new JLabel ("Intern");
		labelIntern.setFont(labelFont);
		JPanel containerIntern = new JPanel(new BorderLayout());
		labelIntern.setPreferredSize(new Dimension(textWidth,0));
		containerIntern.add(labelIntern,BorderLayout.WEST);
		chkIntern = new JCheckBox();
		containerIntern.add(chkIntern,BorderLayout.CENTER);
		
		addinfoFileContentPanelCenter.add(containerIntern);
		addInfoFileContentPanel.add(addinfoFileContentPanelCenter, BorderLayout.CENTER);
		
		this.add(addInfoFileContentPanel,BorderLayout.SOUTH);
		

		
	}
	
	public void editFile() {
		GroupComplete complete = Main.KeplastDataBaseList.getElementGroup().get(DialogFrame.row);
		DialogFrame.infoData = complete.getGroupInfo();

		textHeader.setText(DialogFrame.infoData.getHeader());
		//textId.setText(info.getId());
		//textType.setText(info.getType());
		comboBoxType.setSelectedItem(DialogFrame.infoData.getType());
		//TODO: use InputVerifier to check if input is OK
		textVersion.setText(DialogFrame.infoData.getVersion());
		textAutor.setText(DialogFrame.infoData.getAutor());
		chkIntern.setSelected(DialogFrame.infoData.getIntern());
		textKeywords.setText(DialogFrame.infoData.getKeyWords());
		textDescription.setText(DialogFrame.infoData.getDescription());
		DialogFrame.saveDirectory.setText(DialogFrame.infoData.getPath());
		DialogFrame.saveDirectory.setToolTipText(DialogFrame.saveDirectory.getText());
		DialogFrame.fileList.addListEntries(DialogFrame.saveDirectory.getText());
		DialogFrame.directoryButton.setEnabled(false);
		DialogFrame.fileList.moveRegardingFiles(DialogFrame.infoData.getArraylist());
	}
	
	public void newFile () {
		 //textId.setText(addDigits(getNextIdFile()));
		DialogFrame.saveDirectory.setText(ProjectParam.ROOT_PATH );
		DialogFrame.saveDirectory.setToolTipText(DialogFrame.saveDirectory.getText());
		DialogFrame.fileList.addListEntries(DialogFrame.saveDirectory.getText());
		DialogFrame.directoryButton.setEnabled(true);
		 
		 //prefill textfields
		 textVersion.setText("1.0");
		 textAutor.setText(System.getProperty("user.name"));
		 
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
