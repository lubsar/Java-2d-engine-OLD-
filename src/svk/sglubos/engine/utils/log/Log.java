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

import svk.sglubos.engine.utils.debug.DebugStringBuilder;

//TODO documment
public abstract class Log {
	String id;
	protected LogWriter writer;
	
	public Log() {}
	
	public abstract void log(String... strings);
	
	public void close() {
		writer.close();
	}
	
	public boolean isWritable() {
		return writer.isWritable();
	}
	
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		
		ret.append(this.getClass(), hashCode());
		ret.increaseLayer();
		ret.append("id", (Object)id);
		ret.append(writer, "writer");
		ret.decreaseLayer();
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}