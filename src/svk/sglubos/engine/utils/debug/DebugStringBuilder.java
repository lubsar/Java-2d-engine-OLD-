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

import svk.sglubos.engine.utils.Constants;

//TODO document build fancy string formatter
public class DebugStringBuilder implements Constants{
	private StringBuilder builder = new StringBuilder();
	
	
	public void append(String string) {
		builder.append(string);
	}
	
	public void appendTab() {
		builder.append(TABULATOR);
	}
	
	public void appendln(String string) {
		builder.append(string);
		builder.append(LINE_SEPARATOR);
	}
	
	public void append(String... strings) {
		for(String s : strings) {
			builder.append(s);
		}
	}
	
	public void appendln(String... strings) {
		for(String s : strings) {
			builder.append(s);
		}
		builder.append(LINE_SEPARATOR);
	}
	
	public void appendln() {
		builder.append(LINE_SEPARATOR);
	}
	
	public void appendLineSeparator() {
		builder.append(LINE_SEPARATOR);
	}
	
	public void appendTabln(String string) {
		builder.append(TABULATOR);
		builder.append(string);
		builder.append(LINE_SEPARATOR);
	}
	
	public void appendClassData(Class<?> clas, int hashcode) {
		builder.append(clas.getName());
		builder.append("@");
		builder.append(Integer.toHexString(hashcode));
	}
	
	public void appendObjectToString(String string, Object object) {
		builder.append(string);
		try{
			builder.append(object.toString());			
		} catch (NullPointerException e) {
			builder.append("null");
		}
	}
	
	public void appendObjectToStringTab(String string, Object object) {
		builder.append(TABULATOR);
		appendObjectToString(string, object);
	}
	
	public void appendObjectToStringTabln(String string, Object object) {
		builder.append(TABULATOR);
		appendObjectToStringln(string, object);
	}
	
	public void appendObjectToStringln(String string, Object object) {
		builder.append(string);
		try{
			builder.append(object.toString());			
		} catch (NullPointerException e) {
			builder.append("null");
		}
		builder.append(LINE_SEPARATOR);
	}
	
	public void appendObjectArrayln(String string, Object[] object) {
		
	}
	
	public void appendClassDataBracket(Class<?> clas, int hashcode) {
		appendClassData(clas, hashcode);
		builder.append(" {");
		builder.append(LINE_SEPARATOR);
	}
	
	public void appendCloseBracket() {
		builder.append("}");
	}
	
	public String getString() {
		return builder.toString();
	}
}
