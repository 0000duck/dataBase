package gui.table.displayWindow;


import gui.utilities.LoadImageIcon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.sun.xml.internal.ws.api.server.Container;

import structure.GroupInfo;
import utilities.ProjectParam;

/*
 * create Dialog/ Window for displaying
 * Content of an Entry in Data Base 
 */

public class DisplayDialog extends JDialog {

	private static DisplayDialog m_instance = null; 
	private static GroupInfo m_groupInfo = null;
	
	private static JLabel m_displayHeader = null;
	private static JLabel m_autorLabel = null;
	private static JLabel m_versionLabel = null;
	private static JLabel m_typeLabel = null;
	private static JTextArea m_descBody = null;
	private static LinkList m_linkList = null;

	private DisplayDialog () {
		super(ProjectParam.MAIN_FRAME, "Information");
		//super.setAlwaysOnTop(true);
		//super.setLocation(MouseInfo.getPointerInfo().getLocation());
		super.setMinimumSize(new Dimension(200,200));
		super.setPreferredSize(new Dimension(500,500));
		super.setMaximumSize(new Dimension(500,500));
		//super.setPreferredSize(new Dimension(200,200));
		addDialogListener();
		createDialogContent();
				//super.setUndecorated(true);
		super.pack();
		super.setFocusable(true);
		super.setLocationRelativeTo(ProjectParam.MAIN_FRAME);
		super.setVisible(true); 
	}


	
	private void addDialogListener() {
		super.addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				closeDialog();
			}
		});
	}



	/*
	 * Create instance of Dialog
	 * Only one Dialog can be shown per time
	 */
	
	public static void setDialogInputData(GroupInfo groupInfo) {
		m_groupInfo = groupInfo;
		getInstance();
	}
	
	
	public static DisplayDialog getInstance () {
		if (m_instance != null) {
			changeText();
			//m_instance.setVisible(true);
			
		} else {
			m_instance = new DisplayDialog();	
		}
		return m_instance;
	}
	
	private static void closeDialog() {
		//if (m_instance != null){
			m_instance.dispose();
			m_instance = null;	
		//}
	}
	

	private static void changeText() {
		m_displayHeader.setText(m_groupInfo.getHeader());
		m_versionLabel.setText(m_groupInfo.getVersion());
		m_autorLabel.setText(m_groupInfo.getAutor());
		m_typeLabel.setText(m_groupInfo.getType());
		m_descBody.setText(m_groupInfo.getDescription());
		m_linkList.updateList(m_groupInfo);
	}

	private void createDialogContent() {
		
		/*
		 * Dialog Frame layout
		 */
		BorderLayout borderLayout = new BorderLayout();
		this.setLayout(borderLayout);
		
		/*
		 * Add etched panel for nice view
		 */
		
		BorderLayout borderLayoutPanel = new BorderLayout();
		JPanel mainPanel = new JPanel(borderLayoutPanel);
		mainPanel.setBorder(BorderFactory.createEtchedBorder());
		this.add(mainPanel, BorderLayout.CENTER);

		/*
		 * Add Panel for frame gap
		 */
		BorderLayout borderLayoutgapPanel = new BorderLayout();
		borderLayoutgapPanel.setVgap(15);
	    JPanel gapPanel = new JPanel(borderLayoutgapPanel);
		gapPanel.setBorder(new EmptyBorder(10,10,10,10));
		mainPanel.add(gapPanel,BorderLayout.CENTER);


		/*
		 * Add Header
		 */
		//create new JPanel - Boarder Layout
		BorderLayout borderLayoutHeader = new BorderLayout();
		JPanel northPanel = new JPanel();
		northPanel.setLayout(borderLayoutHeader);
		borderLayoutHeader.setVgap(10);
		gapPanel.add(northPanel,BorderLayout.NORTH);
		
		// Header Text
		m_displayHeader = new JLabel();
		//displayHeader.setEditable(false);
		m_displayHeader.setText(m_groupInfo.getHeader());
		m_displayHeader.setFont(new Font("Arial",Font.BOLD,27));
		m_displayHeader.setBackground(ProjectParam.ALTERNATING_ROW_COL);
		northPanel.add(m_displayHeader,BorderLayout.NORTH);
		
		// Version, Type, Autor
		GridLayout addInfoGridLayout = new GridLayout(1,4);
		addInfoGridLayout.setVgap(3);
		JPanel addInfoPanel = new JPanel(addInfoGridLayout);
		northPanel.add(addInfoPanel,BorderLayout.SOUTH);
			
		Font addInfoFont = new Font("Arial",Font.BOLD,13);
		m_versionLabel = new JLabel(m_groupInfo.getVersion());
		m_versionLabel.setFont(addInfoFont);
		addInfoPanel.add(m_versionLabel);
		m_typeLabel = new JLabel(m_groupInfo.getType());
		m_typeLabel.setFont(addInfoFont);
		addInfoPanel.add(m_typeLabel);
		m_autorLabel = new JLabel(m_groupInfo.getAutor());
		m_autorLabel.setFont(addInfoFont);
		addInfoPanel.add(m_autorLabel);
		ImageIcon iconInfo = LoadImageIcon.createImageIcon("/img/info_50x50.png","");
		final JLabel labelIconInfo = new JLabel();
		labelIconInfo.setIcon(iconInfo);
		//addInfoPanel.add(labelIconInfo);
		labelIconInfo.addMouseListener(new MouseListener () {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getComponent().getName() == labelIconInfo.getName()){
					closeDialog();
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		});
		ImageIcon iconZip = LoadImageIcon.createImageIcon("/img/ZipFolder_50x50.png","");
		final JLabel labelIconZip = new JLabel();
		labelIconZip.setIcon(iconZip);
		addInfoPanel.add(labelIconZip);
		labelIconZip.addMouseListener(new MouseListener () {
			@Override
			public void mouseClicked(MouseEvent e) {
				ZipArchive.create(m_groupInfo);
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			
		
			
			
		});

		
		/* 
		 * Add Body
		 */
		m_descBody = new JTextArea();
		//descBody.setEditable(false);
		//descBody.setEnabled(false);
		m_descBody.setEditable(false);
		m_descBody.setLineWrap(true);
		m_descBody.setWrapStyleWord(true);
		m_descBody.setFont(new Font("Arial", Font.PLAIN, 14));
		m_descBody.setBackground(UIManager.getColor(this));
		m_descBody.setForeground(null);
		m_descBody.setText(m_groupInfo.getDescription().toString());
		JScrollPane descScrollPanel = new JScrollPane(m_descBody);
		descScrollPanel.setAutoscrolls(false);
		descScrollPanel.setViewportBorder(null);
		descScrollPanel.setBorder(null);
		gapPanel.add(descScrollPanel,BorderLayout.CENTER);
		
		/*
		 * Add Document Links
		 */
		m_linkList = new LinkList(m_groupInfo);
		JScrollPane linkScrollPane = new JScrollPane(m_linkList);
		linkScrollPane.setPreferredSize(new Dimension(200,50));
		linkScrollPane.setBorder(null);
		gapPanel.add(linkScrollPane, BorderLayout.SOUTH);

	}
}
