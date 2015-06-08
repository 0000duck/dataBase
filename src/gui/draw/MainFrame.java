package gui.draw;

import gui.table.FilterTable;
import gui.utilities.TapTapTap;

import java.awt.Dimension;

import javax.swing.*;

import utilities.ProjectParam;

public class MainFrame extends JFrame {

	// public static FilterTable filterTable;

	public MainFrame() {
		

		super(ProjectParam.DataBaseVersion);

		//Filter Table

		ProjectParam.FILTER_TABLE = new FilterTable();
		
		//Info Panel
		JPanel infoPanel = new JPanel();

		
		//create SplitPane
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,true,infoPanel,ProjectParam.FILTER_TABLE);  
		//splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(0);
		splitPane.setLastDividerLocation(20);
		splitPane.setDividerSize(10);
		splitPane.setDividerSize(5);
		//splitPane.setre
		this.add(splitPane);
		this.setPreferredSize(new Dimension(1300, 800));
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
