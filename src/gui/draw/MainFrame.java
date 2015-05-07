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
		ProjectParam.FILTER_TABLE = new FilterTable();
		this.add(ProjectParam.FILTER_TABLE);
		this.setPreferredSize(new Dimension(1300, 800));
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
