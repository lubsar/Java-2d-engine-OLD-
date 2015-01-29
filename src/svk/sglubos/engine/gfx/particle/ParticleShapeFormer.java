package svk.sglubos.engine.gfx.particle;

import java.util.Random;

//TODO create

public class ParticleShapeFormer {
	public static final int RANDOM = -1;
	public static final int AROUND = 0;
	public static final int NORTH = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 4;
	public static final int EAST = 8;
	
	public static void formLine(ParticleEntity[] particles, int startX, int startY, int endX, int endY, int direction, boolean coordinateRandomnes) {
		
	}
	
	public static void formRectangle(ParticleEntity[] particles, int width, int height, int direction, boolean fromEdge, boolean randomnes) {
		
	}
	private static Random r = new Random();
	
	public static void temp(ParticleEntity[] particles, int width, int height) {
		for(ParticleEntity e : particles) {
			e.setX(r.nextInt(width));
			e.setVelocityY(r.nextInt(height) - 23);
		}
	}
}
