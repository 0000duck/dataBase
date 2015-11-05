package gui.table.displayWindow;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JTextField;

import utilities.ProjectParam;

/*
 * create Dialog/ Window for displaying
 * Content of an Entry in Data Base 
 */

public class DisplayDialog extends JDialog {

	private static DisplayDialog instance; 
	
	public static DisplayDialog getInstanceAvailability() {
		if (instance != null) {
			instance.dispose();
			instance.setVisible(false);
			instance = null;
		}
		return new DisplayDialog();
		

	}
	
	private DisplayDialog () {
		super(ProjectParam.MAIN_FRAME, "Element Details");
		super.setAlwaysOnTop(true);
		super.setLocation(MouseInfo.getPointerInfo().getLocation());
		super.setPreferredSize(new Dimension(300,300));
		super.setSize(new Dimension(300,300));
		//addListener();
		createDialogContent();
		super.setVisible(true); 	

	}

//	private void addListener() {
//		super.addWindowListener(new WindowAdapter() {
//		    @Override
//		    public void windowClosed(WindowEvent e) {
//		        ;
//		    }
//		});
//	}

	private void createDialogContent() {
		
		//set up dialog
		BorderLayout BorderLayout = new BorderLayout();
		this.setLayout(BorderLayout);
		JTextField displayHeader = new JTextField();
		displayHeader.setEditable(false);
		displayHeader.setText("Hello");
		displayHeader.setFont(new Font("Arial",Font.BOLD,16));
		this.add(displayHeader,BorderLayout.NORTH);

		
	}
	
//	public static void hideWindow() {
//		setVisible(false);
//		dispose();
//	}
//	

	
}
