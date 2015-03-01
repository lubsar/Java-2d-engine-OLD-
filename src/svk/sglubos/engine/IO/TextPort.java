package svk.sglubos.engine.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import svk.sglubos.engine.utils.Strings;
//TODO document, exceptions, saving
public class TextPort {
	public static String loadTextAsString(String path) {
		StringBuilder ret = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
			String line = "";
			
			do {
				line = reader.readLine();
				ret.append(line);
				ret.append(Strings.LINE_SEPARATOR);
			}while(line != null);
			
			reader.close();
		} catch (IOException e ) {
			e.printStackTrace();
		}
		
		return ret.toString();
	}
	
	public static String loadTextAsString(String path, String separator) {
		StringBuilder ret = new StringBuilder();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
			String line = "";
			
			do {
				line = reader.readLine();
				ret.append(line);
				ret.append(separator);
			} while (line != null);
			
			reader.close();
		} catch (IOException e ) {
			e.printStackTrace();
		}
		
		return ret.toString();
	}
	
	public static String loadTextAsString(File file) {
		StringBuilder ret = new StringBuilder();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "";
			
			do {
				line = reader.readLine();
				ret.append(line);
				ret.append(Strings.LINE_SEPARATOR);
			} while (line != null);
			
			reader.close();
		} catch (IOException e ) {
			e.printStackTrace();
		}
		
		return ret.toString();
	}
	
	public static String loadTextAsString(File file, String separator) {
		StringBuilder ret = new StringBuilder();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "";
			
			do {
				line = reader.readLine();
				ret.append(line);
				ret.append(separator);
			} while (line != null);
			
			reader.close();
		} catch (IOException e ) {
			e.printStackTrace();
		}
		
		return ret.toString();
	}
	
	public static String loadTextAsString(Reader reader) {
		StringBuilder ret = new StringBuilder();
		
		try {
			BufferedReader sreader = new BufferedReader(reader);
			String line = "";
			
			do {
				line = sreader.readLine();
				ret.append(line);
				ret.append(Strings.LINE_SEPARATOR);
			} while (line != null);
			
			sreader.close();
		} catch (IOException e ) {
			e.printStackTrace();
		}
		
		return ret.toString();
	}
	
	public static String loadTextAsString(Reader reader, String separator) {
		StringBuilder ret = new StringBuilder();
		
		try {
			BufferedReader sreader = new BufferedReader(reader);
			String line = "";
			
			do {
				line = sreader.readLine();
				ret.append(line);
				ret.append(separator);
			} while (line != null);
			
			sreader.close();
		} catch (IOException e ) {
			e.printStackTrace();
		}
		
		return ret.toString();
	}
	
	public static String[] loadTextAsStringArray(String path) {
		List<String> ret = new ArrayList<String>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
			String line = "";
			
			do {
				line = reader.readLine();
				ret.add(line);
			} while (line != null);
			
			reader.close();
		} catch (IOException e ) {
			e.printStackTrace();
		}
		
		return  (String[]) ret.toArray();
	}
	
	public static String[] loadTextAsStringArray(File file) {
		List<String> ret = new ArrayList<String>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "";
			
			do {
				line = reader.readLine();
				ret.add(line);
			} while (line != null);
			
			reader.close();
		} catch (IOException e ) {
			e.printStackTrace();
		}
		
		return  (String[]) ret.toArray();
	}
	
	public static String[] loadTextAsStringArray(Reader reader) {
		List<String> ret = new ArrayList<String>();
		
		try {
			BufferedReader sreader = new BufferedReader(reader);
			String line = "";
			
			do {
				line = sreader.readLine();
				ret.add(line);
			} while (line != null);
			
			sreader.close();
		} catch (IOException e ) {
			e.printStackTrace();
		}
		
		return  (String[]) ret.toArray();
	}
}
