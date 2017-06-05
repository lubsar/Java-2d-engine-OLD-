/*
 *	Copyright 2017 Ľubomír Hlavko
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

package svk.sglubos.engine.math;

public class Vec2f {
	public float x,y;
	
	public Vec2f() {}
	
	public Vec2f(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void add(Vec2f vec) {
		x = x + vec.x;
		y = y + vec.y;
	}
	
	public void subtract(Vec2f vec) {
		x = x - vec.x;
		y = y - vec.y;
	}
	
	public void scale(float scale) {
		x *= scale;
		y *= scale;
	}
	
	public float dot(Vec2f vec) {
		return x * vec.x + y * vec.y;
	}
	
	public float length() {
		return (float) Math.sqrt(x * x + y * y); 
	}
	
	public static Vec2f add(Vec2f vec1, Vec2f vec2) {
		return new Vec2f(vec1.x + vec2.x, vec1.y + vec2.y);
	}
	
	public static Vec2f subtract(Vec2f vec1, Vec2f vec2) {
		return new Vec2f(vec1.x - vec2.x, vec1.y - vec2.y);
	}
	
	public static Vec2f scale(Vec2f vec, float scale) {
		return new Vec2f(vec.x * scale, vec.y * scale);
	}
	
	public static float dot(Vec2f vec1, Vec2f vec2) {
		return vec1.x * vec2.x + vec1.y * vec2.y;
	}
}
