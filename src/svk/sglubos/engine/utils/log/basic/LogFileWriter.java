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

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import svk.sglubos.engine.utils.debug.DebugStringBuilder;
import svk.sglubos.engine.utils.log.LogWriter;

//TODO documment
public class LogFileWriter implements LogWriter{
	private BufferedWriter writer;
	private boolean writable;
	
	public LogFileWriter(String path) {
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		writable = true;
	}
	
	//TODO exception
	public void write(String... strings) {
		for(String s : strings) {
			try {
				writer.write(s);
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//TODO exception
	public void close() {
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		writable = false;
	}

	@Override
	public boolean isWritable() {
		return writable;
	}
	
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
			
		ret.append(this.getClass(), hashCode());
		ret.increaseLayer();
		ret.append(writer, "writer");
		ret.append("writable", writable);
		ret.decreaseLayer();
		ret.appendCloseBracket();
			
		return ret.getString();
	}
}
