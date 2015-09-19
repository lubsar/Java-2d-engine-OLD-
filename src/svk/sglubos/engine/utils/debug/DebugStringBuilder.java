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
public class DebugStringBuilder implements Constants {
	private StringBuilder builder = new StringBuilder();
	public int layer;
	
	public void append(String string) {
		if(!string.contains("\r") && !string.contains("\n") && !string.contains(LINE_SEPARATOR)) {
			appendTabs();
			builder.append(string);
			return;
		}
		
		if(string.contains(LINE_SEPARATOR)) {
			string = string.replaceAll(LINE_SEPARATOR, "\n");
		}
		
		String[] strings = string.split("[\r\n]" );
		
		for(int i = 0; i < strings.length; i++) {
			appendTabs();
			builder.append(strings[i]);
			if(i < strings.length -1 )
				builder.append(LINE_SEPARATOR);
		}
	}
	
	public void append(String... strings) {
		for(String s : strings) {
			appendln(s);
		}
	}
	
	public void append(Class<?> clas, int hashcode) {
		appendTabs();
		builder.append(clas.getName());
		builder.append("@");
		builder.append(Integer.toHexString(hashcode));
		builder.append(" {");
		builder.append(LINE_SEPARATOR);
	}
	
	public void append(Object object, String name) {
		appendTabs();
		builder.append('(');
		builder.append(object.getClass());
		builder.append(')');
		append(name);
		append(" = [");
		try{
			append(object.toString());			
		} catch (NullPointerException e) {
			builder.append("null");
		}
		
		append(" ]");
		builder.append(LINE_SEPARATOR);
	}
	
	public void append(Object[] objects, String name) {
		appendTabs();
		builder.append('(');
		builder.append(objects.getClass());
		builder.append(')');
		append(name);
		append(" = [");
		  for(int i = 0; i < objects.length; i++) {
			  try{
				  append(objects[i].toString());			
			  } catch (NullPointerException e) {
				  builder.append("null");
			  }
			  
			  if(i < objects.length - 1) {
				  builder.append(',');
			  }
			  
			  builder.append(LINE_SEPARATOR);
		  }
		
		append(" ]");
		builder.append(LINE_SEPARATOR);
	}
	
	public void append(String name, Object primitive) {
		appendTabs();
		append(name);
		append(" = ");
		try{
			append(primitive.toString());			
		} catch (NullPointerException e) {
			builder.append("null");
		}
		
		builder.append(LINE_SEPARATOR);
	}
	
	public void append(String name, Object[] primitives) {
		appendTabs();
		append(name);
		append(" = [");
		  for(int i = 0; i < primitives.length; i++) {
			  try{
				  append(primitives[i].toString());			
			  } catch (NullPointerException e) {
				  builder.append("null");
			  }
			  
			  if(i < primitives.length - 1) {
				  builder.append(',');
			  }
			  
			  builder.append(LINE_SEPARATOR);
		  }
		
		append(" ]");
		builder.append(LINE_SEPARATOR);
	}
	
	public void appendTab() {
		appendTabs();
		builder.append(TABULATOR);
	}
	
	public void appendln(String string) {
		append(string);
		builder.append(LINE_SEPARATOR);
	}
	
	public void appendCloseBracket() {
		appendTabs();
		builder.append("}");
	}
	
	private void appendTabs() {
		if(layer == 0) {
			return;
		}
		
		for(int i = 0; i < layer; i++) {
			builder.append(TABULATOR);
		}
	}
	
	public void setLayer(int layer) {
		this.layer = layer;
	}
	
	public String getString() {
		return builder.toString();
	}
}