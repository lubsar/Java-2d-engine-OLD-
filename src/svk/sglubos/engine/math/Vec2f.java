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
