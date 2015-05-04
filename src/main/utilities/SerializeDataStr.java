package main.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Date;

import structure.GroupCollection;
import structure.GroupComplete;
import utilities.ProjectParam;
import main.Main;

public class SerializeDataStr {
	
	final static String filename = ProjectParam.ROOTH_PATH_APPL + ProjectParam.DATA_FOLDER + "row_data_ser";
	public boolean fileExists = false;
	
//	public Serlialization() {
//		
//	}
	
	public void doSerializing() {
		OutputStream fos = null;

		try {
			fos = new FileOutputStream(filename);
			ObjectOutputStream o = new ObjectOutputStream(fos);
			o.writeObject(Main.KeplastDataBaseList);
			//o.writeObject(new Date());
		} catch (IOException e) {
			System.err.println(e);
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public GroupCollection getSerialized() {
		InputStream fis = null;

		try
		{
		  fis = new FileInputStream(filename);
		  fileExists = true;
		  ObjectInputStream o = new ObjectInputStream(fis);
		  GroupCollection groupCollection = (GroupCollection) o.readObject();
		  //Date date     = (Date) o.readObject();
		  return groupCollection;
		}
		catch ( IOException e ) { 
			//System.err.println( e );
			fileExists = false;
		}
		catch ( ClassNotFoundException e ) { System.err.println( e ); }
		finally { try { fis.close(); } catch ( Exception e ) { } }
		return null;		
	}

}
