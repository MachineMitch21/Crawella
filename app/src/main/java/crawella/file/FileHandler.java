package crawella.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileHandler {

	private File createFileIfNotExists(String fileName) throws IOException {
		File file = new File(fileName);
		file.createNewFile();
		return file;
	}

	/**
	 * 
	 * @param fileName The name of the file
	 * @param dataToWrite The data to write to the file
	 * @throws IOException
	 */
	public void write(String fileName, String dataToWrite) throws IOException {
		File file = createFileIfNotExists(fileName);
		write(file, dataToWrite);
	}

	/**
	 * 
	 * @param file
	 * @param dataToWrite
	 * @throws IOException
	 */
	public void write(File file, String dataToWrite) throws IOException {
		OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
		os.write(dataToWrite.getBytes());
		os.flush();
		os.close();
	}
	
	/**
	 * 
	 * @param fileName
	 * @param dataToWrite
	 * @throws IOException
	 */
	public void append(String fileName, String dataToWrite) throws IOException {
		File file = createFileIfNotExists(fileName);
		append(file, dataToWrite);
	}

	/**
	 * 
	 * @param file
	 * @param dataToWrite
	 * @throws IOException
	 */
	public void append(File file, String dataToWrite) throws IOException {
		OutputStream os = new BufferedOutputStream(new FileOutputStream(file, true));
		os.write(dataToWrite.getBytes());
		os.flush();
		os.close();
	}

	public String read(String fileName) throws IOException {
		File file = new File(fileName);
		return read(file);
	}

	public String read(File file) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(file));
		byte[] bytes = new byte[(int) file.length()];
		is.read(bytes);
		is.close();
		return new String(bytes);
	}
}
