/*
 *	Copyright 2015 Ľubomír Hlavko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package svk.sglubos.engine.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import svk.sglubos.engine.utils.Constants;
//TODO document, exceptions, saving, refactor (remove methods / reduce code duplication)
public class TextPort {
	public static String loadTextAsString(String path) {
		File f = new File(path);
		return loadTextAsString(f);
	}
	
	public static String loadTextAsString(String path, String separator) {
		File f = new File(path);
		return loadTextAsString(f, separator);
	}
	
	public static String loadTextAsString(File file) {
		return loadTextAsString(file, Constants.LINE_SEPARATOR);
	}
	
	public static String loadTextAsString(File file, String separator) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return loadTextAsString(reader, separator);
	}
	
	public static String loadTextAsString(Reader reader) {
		return loadTextAsString(reader, Constants.LINE_SEPARATOR);
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
		File f = new File(path);
		return loadTextAsStringArray(f);
	}
	
	public static String[] loadTextAsStringArray(File file) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return loadTextAsStringArray(reader);
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
	
	public static void saveStringToFile(String string, String path) {
		saveStringToFile(string, new File(path));
	}
	
	public static void saveStringToFileSeparateLines(String string, String separator, File file) {
		String[] strings = string.split(separator);
		saveStringArrayToFile(strings, file, true);
	}
	
	public static void saveStringToFileSeparateLines(String string, File file) {
		String[] strings = string.split(Constants.LINE_SEPARATOR);
		saveStringArrayToFile(strings, file, true);
	}
	
	public static void saveStringToFileSeparateLines(String string, String path) {
		File f = new File(path);
		saveStringToFileSeparateLines(string, f);
	}
	
	public static void saveStringToFileSeparateLines(String string, String separator, String path) {
		File f = new File(path);
		saveStringToFileSeparateLines(string, separator, f);
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
		File f = new File(path);
		saveStringArrayToFile(strings, f, separateStringsByLine);
	}
}
