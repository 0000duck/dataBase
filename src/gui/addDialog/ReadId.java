package gui.addDialog;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import utilities.ProjectParam;

public class ReadId {

	final static String FILENAME = ProjectParam.ROOT_PATH + ProjectParam.DATA_FOLDER + "Id.txt";

	public static int readIdAndIncrement(File idFile) {
		int id = -1; // Id read from the file

		RandomAccessFile raf = null;
		FileChannel fc = null;
		FileLock lock = null;
		try {
			raf = new RandomAccessFile(idFile, "rw");
			fc = raf.getChannel();

			lock = fc.tryLock();
			if (lock != null) {
				String str = raf.readLine().trim();
				id = Integer.parseInt(str);

				// Write next Id to use
				String newStr = Integer.toString(id + 1);
				raf.seek(0);
				// make sure the file is empty if the new content is smaller than the old onw
				raf.setLength(0);
				raf.writeBytes(newStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (lock != null)// FIXME should we release the lock before closing the file?
					lock.release();
				if (fc != null)
					fc.close();
				if (raf != null)
					raf.close();
			} catch (IOException e) {
			}
		}
		return id;
	}

}
