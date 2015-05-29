/*
 *	Copyright 2015 ¼ubomír Hlavko
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
package svk.sglubos.engine.utils.debug;

import java.io.PrintStream;

public class MessageHandler {
	public static final String WARNING = "WARNING";
	public static final String INFO = "INFO";
	public static final String ERROR = "ERROR";
	
	private static PrintStream printStream = System.out;
	private static PrintStream errorStream = System.err;
	
	public static void printMessage(String tag, String message) {
		if(tag.equals(ERROR)) {
			errorStream.println("ENGINE" + ": [" +ERROR + "] " + message );
			return;
		}
			
		printStream.println("ENGINE" + ": [" +tag + "] " + message );
	}
	
	public static void printMessage(String prefix, String tag, String message) {
		if(tag.equals(ERROR)){
			errorStream.println(prefix + ": [" + ERROR + "] " + message );
			return;
		}
		System.out.println(prefix + ": [" +tag + "] " + message );
	}
	
	public static void setPrintStream(PrintStream stream) {
		printStream = stream;
	}
	
	public static void setErrorStream(PrintStream stream) {
		errorStream = stream;
	}
}
