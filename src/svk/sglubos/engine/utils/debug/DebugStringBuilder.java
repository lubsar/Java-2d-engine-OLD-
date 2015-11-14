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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
		append("(");
		if(object == null ) {
			builder.append("null");
		} else {
			builder.append(object.getClass());
		}
		builder.append(')');
		builder.append(name);
		builder.append(" = [");
		builder.append(LINE_SEPARATOR);
		if(object == null) {
			layer++;
			appendln("null");
			layer--;
		} else {
			layer++;
			appendln(object.toString());
			layer--;
		}
		
		appendln("]");
	}
	
	public void append(Object[] objects, String name) {
		append("(");
		if(objects == null ) {
			builder.append("null)");
		} else {
			builder.append(objects.getClass());						
		}
		
		builder.append(')');
		builder.append(name);
		builder.append(" = [" + LINE_SEPARATOR);
		if(objects != null) {
			addLayer();
			for(int i = 0; i < objects.length; i++) {
				if(objects[i] == null) {
					appendln("null");
				} else {
					appendln(objects[i].toString());
				}
			}
			removeLayer();
		}
		
		appendln("]");
	}
	
	public void append(String name, Object primitive) {
		append(name);
		builder.append(" = ");
		if(primitive == null) {
			builder.append("null");
		}else {
			builder.append(primitive.toString());						
		}
		
		builder.append(LINE_SEPARATOR);
	}
	
	public void append(String name, Object[] primitives) {
		append(name);
		builder.append(" = [");
		  for(int i = 0; i < primitives.length; i++) {
			  try{
				  appendln(primitives[i].toString());			
			  } catch (NullPointerException e) {
				  builder.append("null");
			  }
			  
			  if(i < primitives.length - 1) {
				  builder.append(',');
			  }
			  
			  builder.append(LINE_SEPARATOR);
		  }
		
		append("]");
		builder.append(LINE_SEPARATOR);
	}
	
	public void append (Iterator<Object> iter, String name) {
		append(name);
		builder.append('<');
		builder.append(iter.getClass());
		builder.append('>');
		append(" = [");
		  while(iter.hasNext()){
			 Object o = iter.next();
			  try{
				  appendln(o.toString());			
			  } catch (NullPointerException e) {
				  builder.append("null");
			  }
			  
			  if(iter.hasNext()) {
				  builder.append(',');
			  }
			  
			  builder.append(LINE_SEPARATOR);
		  }
		
		append(" ]");
		builder.append(LINE_SEPARATOR);
	}
	
	public void append(HashMap<Object, Object> map, String name) {
		append(name);
		Iterator<Object> keys = new ArrayList<Object>(map.keySet()).iterator();
		Iterator<Object> values = new ArrayList<Object>(map.values()).iterator();
		
		builder.append(" = [");
		while(keys.hasNext()){
			Object key = keys.next();
			Object value = values.next();
			builder.append('[');
			
			try{
				appendln(key.toString());
			} catch (NullPointerException e) {
				builder.append("null");
			}
			
			builder.append(',');
			builder.append(LINE_SEPARATOR);
			
			if(value == null) {
				builder.append("null");				
			} else {
				appendln(value.toString());				
			}

			builder.append(']');
			  
			if(keys.hasNext()) {
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
	
	public void addLayer() {
		layer++;
	}
	
	public void removeLayer() {
		layer--;
	}
	
	public String getString() {
		return builder.toString();
	}
}