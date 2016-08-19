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
package svk.sglubos.engine.utils.debug;

import java.io.PrintStream;

import svk.sglubos.engine.utils.log.Log;

public class MessageHandler {
	public static final String WARNING = "WARNING";
	public static final String INFO = "INFO";
	public static final String ERROR = "ERROR";
	
	private static boolean logPrint = false;
	private static boolean logError = false;
	
	private static PrintStream printLog = null;
	private static PrintStream errorLog = null;
	
	public static PrintStream printStream = System.out;
	public static PrintStream errorStream = System.err;
	
	public static void printMessage(String tag, String message) {
		printMessage("ENGINE", tag, message);
	}

	public static void printMessage(String prefix, String tag, String message) {
		if(tag.equals(ERROR)){
			String msg = prefix + ": [" + ERROR + "] " + message;
			if(errorStream != null) {
				errorStream.println(msg);
			}
			if(logError) {
				errorLog.println(msg);
			}
			return;
		}
		
		String msg = prefix + ": [" +tag + "] " + message;
		if(printStream != null){
			printStream.println(msg);
		}
		if(logPrint) {
			printLog.println(msg);
		}
	}
	
	public static void setPrintLogging(Log log, boolean enabled) {
		if(log != null) {
			printLog = log;
		}
		if(enabled && printLog == null) {
			throw new RuntimeException("Print log is not initialized");
		}
		logPrint = enabled;
	}
	
	public static void setErrorLogging(Log log, boolean enabled) {
		if(log != null) {
			errorLog = log;
		}
		if(enabled && errorLog == null) {
			throw new RuntimeException("Error log is not initialized");
		}
		
		logError = enabled;
	}
}
