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
package svk.sglubos.engine.utils.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import svk.sglubos.engine.utils.debug.DebugStringBuilder;

public abstract class Log extends PrintStream {
	String id;
	
	public Log(String id, File file) throws FileNotFoundException {
		super(file);
		this.id = id;
	}
	
	public Log(String id, File file, String csn) throws FileNotFoundException, UnsupportedEncodingException {
		super(file, csn);
		this.id = id;
	}

	public Log(String id, OutputStream out, boolean autoFlush, String encoding) throws UnsupportedEncodingException {
		super(out, autoFlush, encoding);
		this.id = id;
	}

	public Log(String id, OutputStream out, boolean autoFlush) {
		super(out, autoFlush);
		this.id = id;
	}

	public Log(String id, OutputStream out) {
		super(out);
		this.id = id;
	}

	public Log(String id, String fileName, String csn) throws FileNotFoundException, UnsupportedEncodingException {
		super(fileName, csn);
		this.id = id;
	}

	public Log(String id, String fileName) throws FileNotFoundException {
		super(fileName);
		this.id = id;
	}
	
	public void log(String... strings) {
		for(String string : strings) {
			super.print(string);
		}
	}
	
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		
		ret.append(this.getClass(), hashCode());
		ret.increaseLayer();
		ret.appendln(super.toString());
		ret.append("id", (Object)id);
		ret.decreaseLayer();
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}