package gui.utilities;

import javax.swing.ImageIcon;

import utilities.LogFile;

public class LoadImageIcon {
	private LoadImageIcon() {
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	public static ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = LoadImageIcon.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			LogFile.write("Couldn't find file: " + path);
			return null;
		}
	}

}
