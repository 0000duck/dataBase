package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogFile {

	private static File logFile;
	private final static DateFormat df = new SimpleDateFormat("yyyy.MM.dd  hh:mm:ss ");

	private LogFile() {
	}

	public static void setLogFile(File file) {
		logFile = file;
		logFile.delete();

		try {
			write("LOG file: " + logFile.getAbsolutePath());
		} catch (Exception e) {
			System.out.println(stack2string(e));
		}
	}

	public static void write(String msg) {
		write(logFile, msg);
	}

	public static void write(Exception e) {
		write(logFile, stack2string(e));
	}

	public static void write(File file, String msg) {
		try {
			Date now = new Date();
			String currentTime = LogFile.df.format(now);
			FileWriter aWriter = new FileWriter(file, true);
			aWriter.write(currentTime + " " + msg + System.getProperty("line.separator"));
			System.out.println(currentTime + " " + msg);
			aWriter.flush();
			aWriter.close();
		} catch (Exception e) {
			System.out.println(stack2string(e));
		}
	}

	private static String stack2string(Exception e) {
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return "------\r\n" + sw.toString() + "------\r\n";
		} catch (Exception e2) {
			return "bad stack2string";
		}
	}
	
	/*
	 * open log file
	 */
	public static void openfile() {
		if (logFile.exists()) {
			try {
				Desktop.getDesktop().open(logFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
   
}