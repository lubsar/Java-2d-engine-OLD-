package svk.sglubos.engine.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import svk.sglubos.engine.utils.Strings;
//TODO document, exceptions, saving, refactor (remove methods / reduce code duplication)
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
	
	public static void saveStringToFile(String string, File file) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
			writer.write(string);
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveStringToFileSeparateLines(String string, File file) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			String[] strings = string.split(Strings.LINE_SEPARATOR);
			
			for(String s : strings) {
				writer.write(s);
				writer.newLine();
			}
			
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveStringToFileSeparateLines(String string,String separator, File file) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			String[] strings = string.split(separator);
			
			for(String s : strings) {
				writer.write(s);
				writer.newLine();
			}
			
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveStringToFile(String string, String path) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path)));
			
			writer.write(string);
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveStringToFileSeparateLines(String string, String path) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path)));
			String[] strings = string.split(Strings.LINE_SEPARATOR);
			
			for(String s : strings) {
				writer.write(s);
				writer.newLine();
			}
			
			writer.flush();
			writer.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveStringToFileSeparateLines(String string, String separator, String path) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path)));
			String[] strings = string.split(separator);
			
			for(String s : strings) {
				writer.write(s);
				writer.newLine();
			}
			
			writer.flush();
			writer.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveStringArrayToFile(String[] strings, File file, boolean separateStringsByLine) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
			for(String s : strings) {
				writer.write(s);
				if(separateStringsByLine)
					writer.newLine();
			}
			
			writer.flush();
			writer.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveStringArrayToFile(String[] strings, String path, boolean separateStringsByLine) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path)));
		
			for(String s : strings) {
				writer.write(s);
				if(separateStringsByLine)
					writer.newLine();
			}
		
			writer.flush();
			writer.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
