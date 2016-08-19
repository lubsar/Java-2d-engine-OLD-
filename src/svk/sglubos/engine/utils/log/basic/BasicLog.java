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
package svk.sglubos.engine.utils.log.basic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import svk.sglubos.engine.utils.debug.DebugStringBuilder;
import svk.sglubos.engine.utils.log.Log;
import svk.sglubos.engine.utils.log.LoggingUtilities;

public class BasicLog extends Log {
	private boolean timeStamps;
	
	public BasicLog(String id, boolean timeStamps, File file, String csn) throws FileNotFoundException, UnsupportedEncodingException {
		super(id, file, csn);
		this.timeStamps = timeStamps;
	}

	public BasicLog(String id, boolean timeStamps, File file) throws FileNotFoundException {
		super(id, file);
		this.timeStamps = timeStamps;
	}

	public BasicLog(String id, boolean timeStamps, OutputStream out, boolean autoFlush, String encoding)
			throws UnsupportedEncodingException {
		super(id, out, autoFlush, encoding);
		this.timeStamps = timeStamps;
	}

	public BasicLog(String id, boolean timeStamps, OutputStream out, boolean autoFlush) {
		super(id, out, autoFlush);
		this.timeStamps = timeStamps;
	}

	public BasicLog(String id, boolean timeStamps, OutputStream out) {
		super(id, out);
		this.timeStamps = timeStamps;
	}

	public BasicLog(String id, boolean timeStamps, String fileName, String csn) throws FileNotFoundException, UnsupportedEncodingException {
		super(id, fileName, csn);
		this.timeStamps = timeStamps;
	}

	public BasicLog(String id, boolean timeStamps, String fileName) throws FileNotFoundException {
		super(id, fileName);
		this.timeStamps = timeStamps;
	}
	
	@Override
	public void print(boolean b) {
		stamp();
		super.print(b);
	}

	@Override
	public void print(char c) {
		stamp();
		super.print(c);
	}

	@Override
	public void print(int i) {
		stamp();
		super.print(i);
	}

	@Override
	public void print(long l) {
		stamp();
		super.print(l);
	}

	@Override
	public void print(float f) {
		stamp();
		super.print(f);
	}

	@Override
	public void print(double d) {
		stamp();
		super.print(d);
	}

	@Override
	public void print(char[] s) {
		stamp();
		super.print(s);
	}

	@Override
	public void print(String s) {
		stamp();
		super.print(s);
	}

	@Override
	public void print(Object obj) {
		stamp();
		super.print(obj);
	}

	@Override
	public void println() {
		stamp();
		super.println();
	}

	@Override
	public void println(boolean x) {
		stamp();
		super.println(x);
	}

	@Override
	public void println(char x) {
		stamp();
		super.println(x);
	}

	@Override
	public void println(int x) {
		stamp();
		super.println(x);
	}

	@Override
	public void println(long x) {
		stamp();
		super.println(x);
	}

	@Override
	public void println(float x) {
		stamp();
		super.println(x);
	}

	@Override
	public void println(double x) {
		stamp();
		super.println(x);
	}

	@Override
	public void println(char[] x) {
		stamp();
		super.println(x);
	}

	@Override
	public void println(String x) {
		stamp();
		super.println(x);
	}

	@Override
	public void println(Object x) {
		stamp();
		super.println(x);
	}

	@Override
	public PrintStream printf(String format, Object... args) {
		stamp();
		return super.printf(format, args);
	}

	@Override
	public PrintStream printf(Locale l, String format, Object... args) {
		stamp();
		return super.printf(l, format, args);
	}

	@Override
	public PrintStream append(CharSequence csq) {
		stamp();
		return super.append(csq);
	}

	@Override
	public PrintStream append(CharSequence csq, int start, int end) {
		stamp();
		return super.append(csq, start, end);
	}

	@Override
	public PrintStream append(char c) {
		stamp();
		return super.append(c);
	}
	
	@Override
	public void log(String... strings) {
		stamp();
		super.log(strings);
	}
	
	private void stamp() {
		if(timeStamps) {
			super.print("[" + LoggingUtilities.getTime() + "] ");
		}
	}
	
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
			
		ret.append(this.getClass(), hashCode());
		ret.increaseLayer();
		ret.appendln(super.toString());
		ret.append("timeStamps", timeStamps);
		ret.decreaseLayer();
		ret.appendCloseBracket();
			
		return ret.getString();
	}
}