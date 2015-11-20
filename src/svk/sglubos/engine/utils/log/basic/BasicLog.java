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
package svk.sglubos.engine.utils.log.basic;

import svk.sglubos.engine.utils.log.Log;
import svk.sglubos.engine.utils.log.LoggingUtilities;

public class BasicLog extends Log {
	private boolean timeStamps;
	
	public BasicLog(String path, boolean timeStamps) {
		writer = new LogFileWriter(path);
		this.timeStamps = timeStamps;
	}
	
	@Override
	public void log(String... strings) {
		if(timeStamps) {
			writer.write("[" + LoggingUtilities.getTime() + "]");
		}
		writer.write(strings);
	}
}