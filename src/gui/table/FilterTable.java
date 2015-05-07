package gui.table;

import gui.addDialog.DialogFrame;
import gui.renderer.AdvancedCellRenderer;
import gui.renderer.TableCellRendererJTextArea;
import gui.utilities.LoadImageIcon;
import gui.utilities.TapTapTap;
import gui.utilities.UtilitiesHiglight;
import gui.utilities.WaitLayerUI1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultRowSorter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.text.JTextComponent;
import javax.swing.text.LayeredHighlighter;

import com.sun.media.sound.ModelAbstractChannelMixer;
import com.sun.naming.internal.ResourceManager;

import main.Main;
import main.utilities.ConvertStructureToArray;
import main.utilities.SerializeDataStr;
import structure.DummyFileInfo;
import structure.DummyIconPath;
import structure.Group;
import structure.GroupCollection;
import sun.security.util.ResourcesMgr;
import utilities.ProjectParam;
import utilities.LogFile;

public class FilterTable extends JPanel implements Serializable {
	
	private static final String header = "Header";
	private static final String type = "Type";
	private static final String description = "Description";
	private static final String keywords = "Keywords";
	private static final String id = "Id";
	private static final String autor = "Autor";
	private static final String version = "Version";
	private static final String empty = ""; // Empty
	private static final String all = "-All-";

	public static final int columnNrExp = 7;
	public static final int columnNrDialog = 8;
	
	public static String text = "";
	
	public boolean updateActive = false;
	
	TapTapTap tap;
	
//	JLabel displayState;
	
	WaitLayerUI1  busyLayer;
	
	JButton refreshButton;
	JButton rootButton;
	JButton addButton;
	JButton logFileButton;
	
	private Highlighter hilit = null;
	private Highlighter.HighlightPainter painter = null;

	//Objects for Table
	private DefaultTableModelExtend model;
	private JTable jTable;

	//Table sorter
	private TableRowSorter<DefaultTableModelExtend> rowSorter;

	//Filter of list
	private JTextField jtfFilterField;

	//combobox select column for filter
	private JComboBox cbFilterVariant = null;
	
	private JTextArea displCellDetailsLabel;
	private JTextArea jTextLabelDetail;
	
	public static DialogFrame dialogFrame;

	final String[] columnNames = { header, type, description, keywords, id,	autor, version, empty, empty};
	final String[] FilterVariants = { all, header, type, description, keywords,	id, autor, version };
	final int[] allFilterColumns = {0,1,2,3,4,5,6};

	public FilterTable() {
		filterTableCreate();
	}

	private void filterTableCreate() {

//		final String[] columnNames = { header, type, description, keywords, id,
//		autor, version, empty,empty };
//final String[] FilterVariants = { all, header, type, description, keywords,
//		id, autor, version };

// create objects for table and layout

		
		Object[][] arrayTwoDim;
//if (Main.arrayTwoDim != null){
//	arrayTwoDim = Main.arrayTwoDim;
//} else {
		SerializeDataStr serialize = new SerializeDataStr();
		GroupCollection serializedData = (GroupCollection) serialize.getSerialized();
		if (serialize.fileExists) {
			Main.KeplastDataBaseList = serializedData;
			arrayTwoDim = ConvertStructureToArray.convert(serializedData);
		} else {
			arrayTwoDim = new Object[][] {};			
		}
			//{null,null,null,null,null,null,null,null,null},
			//{null,null,null,null,null,null,null,null,null}
//}

		model = new DefaultTableModelExtend(arrayTwoDim, columnNames);
		jTable = new JTable(model);
		AdvancedCellRenderer cellRenderer = new AdvancedCellRenderer();
		jTable.setDefaultRenderer(Object.class, cellRenderer);
		jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		rowSorter = new TableRowSorter<DefaultTableModelExtend>((DefaultTableModelExtend) jTable.getModel());
		jTable.setRowSorter(rowSorter);
		jtfFilterField = new JTextField();

//		hilit = new DefaultHighlighter();
//		painter = new DefaultHighlighter.DefaultHighlightPainter(HILIT_COLOR);

		JTableProperties();  //set width

		jTable.setRowHeight(35);
		jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jTable.setAutoscrolls(false);
		jTable.setAutoCreateRowSorter(false);
		
		jTable.getTableHeader().setReorderingAllowed(false);

		// Disable table's cell selection.
		//
		//jTable.setCellSelectionEnabled(false);
		// jTable.setColumnSelectionAllowed(false);
		// jTable.setRowSelectionAllowed(false);

		//
		// Settings table focus to false completely remove selection
		// capability from the table component.
		//
		 //jTable.setFocusable(false);

		// assign object to Layout
		// new panel in south of frame border panel
		
		//---------------------------------- FRAME SOUTH
		BorderLayout frameBotLayout = new BorderLayout();
		JPanel panelBottomframe = new JPanel(frameBotLayout);
		JPanel panelBottomSouth = new JPanel(new BorderLayout());
		JPanel panelBottomNorth = new JPanel(new BorderLayout(10,10));
		JPanel panelDummyEast = new JPanel (new BorderLayout());

		panelBottomframe.setBorder(BorderFactory.createRaisedBevelBorder());
		
		
		panelBottomNorth.setBorder(new EmptyBorder(5,5,15,5));
		panelBottomSouth.setBorder(new EmptyBorder(5,5,5,5));
		
		jTextLabelDetail = new JTextArea();
		

		displCellDetailsLabel = new JTextArea(6,0);
		displCellDetailsLabel.setLineWrap(true);
		displCellDetailsLabel.setWrapStyleWord(true);
		displCellDetailsLabel.setEditable(false);
		displCellDetailsLabel.setText("- no selection - ");
		//displCellDetailsLabel.setMaximumSize(new Dimension(0,50));
		//displCellDetailsLabel.setPreferredSize(new Dimension(0,50));
		displCellDetailsLabel.setFont(new Font("Arial", Font.ITALIC, 13));
		displCellDetailsLabel.setBackground(UIManager.getColor(panelBottomframe));
		//jTextLabelDetail.setMinimumSize(new Dimension(100,100));
		hilit = new DefaultHighlighter();
		displCellDetailsLabel.setHighlighter(hilit);
		painter = new DefaultHighlighter.DefaultHighlightPainter(ProjectParam.ALTERNATING_ROW_COL);

		panelBottomframe.add(panelBottomNorth,BorderLayout.CENTER);
		panelBottomframe.add(panelBottomSouth,BorderLayout.SOUTH);
		
		
		//tap = new TapTapTap();
		//tap.setBorder(new EmptyBorder(5,5,5,5));
		
		
		//panelBottomframe.add(tap,BorderLayout.EAST);
		
		//JPanel bottomRightLeft = new JPanel (new BorderLayout());
		//bottomRightLeft.add(tap,BorderLayout.EAST);
		//bottomRightLeft.add(panelBottomframe,BorderLayout.WEST);
		//panelBottomframe.setPreferredSize(new Dimension(1200,100));
		//panelBottomframe.setMaximumSize(new Dimension(300,200));
		//JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelBottomframe, tap);
		//splitPane.rigt
		
		//panelBottomNorth.setMinimumSize(new Dimension(100,50));
		
		//Embed cell display label in scroll bar panel
		JScrollPane scrollbarCellDetails = new JScrollPane(displCellDetailsLabel);
		//scrollbarCellDetails.setPreferredSize(new Dimension(0,100));


		//panelBottomNorth.add(new JSeparator(),BorderLayout.NORTH);
		panelBottomNorth.add(scrollbarCellDetails,BorderLayout.CENTER);
		scrollbarCellDetails.setBorder(new EmptyBorder(0,0,0,0));
		//panelBottomNorth.setMaximumSize(new Dimension(25,25));
		
		
		JLabel labelFilter = new JLabel("Filter ");
		labelFilter.setFont(new Font("Arial", Font.BOLD, 13));
		panelBottomSouth.add(labelFilter, BorderLayout.WEST);
		
		panelBottomSouth.add(jtfFilterField, BorderLayout.CENTER);
		
		
		ListSelectionListener tableSelectionListener = new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				//System.out.println("selection changed " + e.toString());
				try {
					
					//get correct row even if  table is filtered
					int selRow = jTable.getSelectedRow();
					int row =  jTable.convertRowIndexToModel(selRow);	
					int column = jTable.getSelectedColumn();
					
					JTextArea show = new JTextArea();
					if (model.getValueAt(row, column) instanceof JTextArea) {
						show = (JTextArea) model.getValueAt(row, column);
						
					} else if (model.getValueAt(row, column) instanceof DummyIconPath) {
						show.setText("- no selection - ");
					} else if (model.getValueAt(row, column) instanceof DummyFileInfo) {
						show.setText("- no selection - ");
					} else {
						show.setText((String) model.getValueAt(row, column));
					}
					displCellDetailsLabel.setText(show.getText());
					displCellDetailsLabel.setForeground(null);
					displCellDetailsLabel.setFont(new Font("Arial",Font.ITALIC, 13));	
					
				} catch (Exception e2) {
					System.out.println("Out of Bounds exception during table selection changed");
					// TODO: handle exception
				}



			}
			
		};
		
		jTable.getColumnModel().getSelectionModel().addListSelectionListener(tableSelectionListener);
		jTable.getSelectionModel().addListSelectionListener(tableSelectionListener);
		
		// open explorer
		jTable.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				
				//get correct row even if  table is filtered
				int row =  jTable.convertRowIndexToModel(jTable.getSelectedRow());
				int column = jTable.getSelectedColumn();
				
				if (e.getClickCount() == 2) {
					
					//open explorer
					if (column == columnNrExp) {
						// String tmpDirect = (String)
						// jTable.getModel().getValueAt(row, column);

						try {
							String OpenDirect = Main.KeplastDataBaseList
									.getElementGroup().get(row).getGroupInfo()
									.getPath();
							Desktop.getDesktop().open(new File(OpenDirect));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
					//open dialog
					if (column == columnNrDialog) {
						dialogFrame = new DialogFrame(row);
					}
				}
				
//				if (e.getClickCount() == 1) {
//
//					JTextArea show = new JTextArea();
//					if (model.getValueAt(row, column) instanceof JTextArea) {
//						show = (JTextArea) model.getValueAt(row, column);
//						
//					} else if (model.getValueAt(row, column) instanceof DummyIconPath) {
//						show.setText("- no selection - ");
//					} else if (model.getValueAt(row, column) instanceof DummyFileInfo) {
//						show.setText("- no selection - ");
//					} else {
//						show.setText((String) model.getValueAt(row, column));
//					}
//					displCellDetailsLabel.setText(show.getText());
//					displCellDetailsLabel.setForeground(null);
//					displCellDetailsLabel.setFont(new Font("Arial",Font.ITALIC, 13));
//
//				}

			}

		});

		// filter combobox update
		cbFilterVariant = new JComboBox(FilterVariants); // Why final
		cbFilterVariant.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				filters();

			}
		});
		panelBottomSouth.add(cbFilterVariant, BorderLayout.EAST);
		
		
		//------------------------------- FRAME EAST
		//Button toolbar east
		ImageIcon iconRefresh = LoadImageIcon.createImageIcon("/img/Refresh_50x50.png","");
		ImageIcon iconAdd = LoadImageIcon.createImageIcon("/img/Edit_50x50.png","");
		ImageIcon iconRoot = LoadImageIcon.createImageIcon("/img/Explorer_Window_40x40.png","");
		ImageIcon iconlogFile = LoadImageIcon.createImageIcon("/img/File_40x40.png","");
		
		GridLayout gridLayout = new GridLayout(4,1,5,5);
		refreshButton = new JButton (iconRefresh);
		refreshButton.setPreferredSize(new Dimension(45,45));
		addButton = new JButton (iconAdd);
		addButton.setPreferredSize(new Dimension(45,45));
		rootButton = new JButton (iconRoot);
		rootButton.setPreferredSize(new Dimension(45,45));
		
		logFileButton = new JButton(iconlogFile);
		logFileButton.setPreferredSize(new Dimension(45,45));

		
		if (Main.errorApplication) {
			refreshButton.setEnabled(false);
			addButton.setEnabled(false);
			rootButton.setEnabled(false);
		}
			
		
		JPanel JPanelFrameEASTEAST = new JPanel (new BorderLayout());
		JPanelFrameEASTEAST.setBorder(new EmptyBorder(5,0,5,5));

		JPanel panelFrameWestNorth = new JPanel(gridLayout);
		JPanelFrameEASTEAST.add(panelFrameWestNorth,BorderLayout.SOUTH);
		panelFrameWestNorth.add(refreshButton);
		panelFrameWestNorth.add(addButton);
		panelFrameWestNorth.add(rootButton);
		panelFrameWestNorth.add(logFileButton);
		
		JPanel panelFrameWest = new JPanel (new BorderLayout(10,20));
		//panelFrameWest.setPreferredSize(new Dimension (80,2000));
		//panelFrameWest.setBorder(new EmptyBorder(5,5,5,15));
		
		//panelFrameWest.add(new JSeparator(JSeparator.VERTICAL), BorderLayout.WEST);
		panelFrameWest.add(JPanelFrameEASTEAST,BorderLayout.EAST);
		//------------------------------- FRAME EAST
		
		//FRAME NORTH
//		displayState = new JLabel();
//		displayState.setOpaque(true);
//		displayState.setFont(new Font("Arial",Font.PLAIN,14));
//		setStateText("Table update in progress", Color.WHITE, CustomConstant.NO_MATCH_COL, new Dimension(25,25));
		
		JPanel mainNortPanel = new JPanel(new BorderLayout());
		mainNortPanel.setBorder(new EmptyBorder(0,5,0,5));
//		mainNortPanel.add(displayState,BorderLayout.CENTER);
		
		//--------------------------------- FRAME
		this.setLayout(new BorderLayout());
		
		this.add(mainNortPanel, BorderLayout.NORTH);
		this.add(panelFrameWest, BorderLayout.EAST);
		
		panelFrameWest.setBorder(BorderFactory.createRaisedBevelBorder());

		// add 2. Layer panel in frame south
		this.add(panelBottomframe, BorderLayout.SOUTH);

		// add scroll panel in center of frame

		JScrollPane jScrollPan = new JScrollPane(jTable);
		jScrollPan.setBorder(new EmptyBorder(5,5,5,5));
		//this.add(new JSeparator(JSeparator.VERTICAL), BorderLayout.EAST);
		
		JPanel jPanelType = new JPanel (new BorderLayout());
		jPanelType.add(jScrollPan,BorderLayout.CENTER);
		
		busyLayer = new WaitLayerUI1();
		//JPanel jEnvelopePanel = new JPanel(new BorderLayout());
		//jEnvelopePanel.add(busyLayer, BorderLayout.CENTER);

		
		JLayer<JPanel> jLayer = new JLayer<JPanel>(jPanelType,busyLayer);
		this.add(jLayer, BorderLayout.CENTER);
		
		
		//jScrollPan.setSize(new Dimension(1200,1000));
		//this.setSize(new Dimension(1200,1000));
		
		refreshButton.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent action) {
				update();
			}

		});

		addButton.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent action) {
				dialogFrame = new DialogFrame();
			}	
		});
		
		rootButton.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent action) {
				try {
					Desktop.getDesktop().open(new File(ProjectParam.ROOT_PATH ));
					//tap.setBusy();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		});

		
		/*
		 * open local saved log file
		 */
		logFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LogFile.openfile();
			}
		});
		
		jtfFilterField.getDocument().addDocumentListener(
				new DocumentListener() {

					@Override
					public void insertUpdate(DocumentEvent e) {
						filters();
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						filters();
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
						throw new UnsupportedOperationException(
								"Not supported yet."); // To change body of
														// generated methods,
														// choose Tools |
														// Templates.
					}

				});

	}

	private void JTableProperties() {
		// set column width
		jTable.getColumnModel().getColumn(0).setPreferredWidth(120);
		jTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		jTable.getColumnModel().getColumn(2).setPreferredWidth(450);
		jTable.getColumnModel().getColumn(3).setPreferredWidth(250);
		jTable.getColumnModel().getColumn(4).setPreferredWidth(60);
		jTable.getColumnModel().getColumn(5).setPreferredWidth(60);
		jTable.getColumnModel().getColumn(6).setPreferredWidth(60);
		jTable.getColumnModel().getColumn(7).setPreferredWidth(35);
		jTable.getColumnModel().getColumn(8).setPreferredWidth(30);
	}

	// execute filter table
	private void filters() {
		text = jtfFilterField.getText();

		if (text.trim().length() == 0) {
			rowSorter.setRowFilter(null);
			jtfFilterField.setBackground(Color.WHITE);
			hilit.removeAllHighlights();
		} else {

			hilit.removeAllHighlights();
			String labelText = displCellDetailsLabel.getText();
			int cuttPos = 0;
			do {
				UtilitiesHiglight.findText(labelText,text);
				if (UtilitiesHiglight.start != -1) {
					try {
						hilit.addHighlight(cuttPos + UtilitiesHiglight.start, cuttPos + UtilitiesHiglight.end, painter);
					} catch (BadLocationException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					 labelText = labelText.substring(UtilitiesHiglight.end, labelText.length());
					 cuttPos = cuttPos + UtilitiesHiglight.end;
				}
			} while (UtilitiesHiglight.start != -1);


			int index = cbFilterVariant.getSelectedIndex();
			if (index == 0) { // search all
				rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, allFilterColumns)); // ?i case insensitive
			} else
				// search in specific column
				rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, index - 1)); // ?i case insensitive
			}
		
		
		//((JTextArea)jTable.getValueAt(0,0)).setFont(new Font("Imperial",Font.ITALIC,20));
		//System.out.println(((JTextArea)jTable.getValueAt(0,0)).getText());
		
		int Count = rowSorter.getViewRowCount();
		if (Count == 0) {
			jtfFilterField.setBackground(ProjectParam.NO_MATCH_COL);
		} else {
			jtfFilterField.setBackground(Color.WHITE);
		}
	}
	
	
	
	public void update() {
		if (!updateActive) {
			updateActive = true;
			LogFile.write("Start update data");
			busyLayer.setBusy();
			refreshButton.setEnabled(false);
			JTableProperties();
			new UpdateDataBase().execute();
		}
		

		
//		model.setDataVector(Main.arrayTwoDim, columnNames);
//		repaintTable();
//		JTableProperties();
//		refreshButton.setEnabled(true);
//		addButton.setEnabled(true);
//		rootButton.setEnabled(true);
//		//setStateText(null, null,CustomConstant.ALTERNATING_ROW_COL,null);
//		busyLayer.resetBusy();
//		LogFile.write("End update data");
	}
	
//	void setStateText(String text, Color forground, Color background, Dimension dimension){
//		displayState.setBackground(background);
//		displayState.setForeground(forground);
//		displayState.setText(text);	
//		displayState.setPreferredSize(dimension);
//	}
	


	public void repaintTable () {
		model.fireTableStructureChanged();
		model.fireTableDataChanged();
	}

	
	class UpdateDataBase extends SwingWorker<Object[][], Object> {
		
	    @Override public Object[][] doInBackground()
	    {
	    	Main.searchFiles();
	    	SerializeDataStr serialize = new SerializeDataStr();
	    	serialize.doSerializing();
	    	return Main.arrayTwoDim;
			
	    }

	    @Override protected void done()
	    {
	      try
	      {
	  		model.setDataVector(get(), columnNames);
			repaintTable();
			JTableProperties();
			refreshButton.setEnabled(true);
			//addButton.setEnabled(true);
			//rootButton.setEnabled(true);
			//setStateText(null, null,CustomConstant.ALTERNATING_ROW_COL,null);
			busyLayer.resetBusy();
			LogFile.write("End update data");
			updateActive = false;
	      }
	      catch ( /* InterruptedException, ExecutionException */ Exception e ) { }
	    }
	  }

//	  public static void main( String[] args )
//	  {
//	    new SwingWorkerDemo().setVisible( true );
//	  }

}

