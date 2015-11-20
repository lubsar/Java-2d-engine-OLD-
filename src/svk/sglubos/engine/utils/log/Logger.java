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
package svk.sglubos.engine.utils.log;

import java.util.HashMap;
import java.util.Map;

public class Logger {
	static final Map<String, Log> globalLogs = new HashMap<String, Log>();
	static Log master = null;
	
	public static void setMasterLog(Log log) {
		if(master != null) {
			master.close();
		}
		Logger.master = log;
	}

	//TODO exception
	public static void log(String... strings) {
		if(master != null) {
			master.log(strings);			
		}
	}
	
	//TODO exception
	public static void close() {
		if(master != null) {
			master.close();
		}
	}
	
	public static boolean isWritable() {
		if(master == null) {
			return false;
		}
		return  master.isWritable(); 
	}
	
	public static Log addGlobalLog(String logID, Log log) {
		globalLogs.put(logID, log);
		return log;
	}
	
	public static Log getGlobalLog(String logID) {
		Log glob = globalLogs.get(logID);
		if(glob == null) {
			//TODO exception
		}
		return globalLogs.get(logID);
	}
}