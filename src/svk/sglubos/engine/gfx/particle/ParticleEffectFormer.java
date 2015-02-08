package svk.sglubos.engine.gfx.particle;

import java.util.Random;

//TODO

public class ParticleEffectFormer {
	private static RectangleFormer f = new ParticleEffectFormer.RectangleFormer();
	
	public static final int VELOCITY_RANDOM = -1;
	public static final int VELOCITY_AROUND = 0;
	public static final int VELOCITY_NORTH = 1;
	public static final int VELOCITY_SOUTH = 2;
	public static final int VELOCITY_WEST = 4;
	public static final int VELOCITY_EAST = 8;
	
	public static final int FILLMODE_FILLED = 0;
	public static final int FILLMODE_EDGES = 1;
	
	
	public static void formLine(ParticleEntity[] particles, ParticleFormation.LineFormation f) {
		formLine(particles, f.getEndX(), f.getEndY(), f.getVelocity(), f.getFillMode(), f.isRandomness(), f.isOverLap(), f.getParticleWidth(), f.getParticleHeight());
	}
	
	public static void formRectangle(ParticleEntity[] particles, ParticleFormation.RectangleFormation f) {
		formRectangle(particles, f.getWidth(), f.getHeight(), f.getVelocity(), f.getFillMode(), f.isRandomness(), f.isOverLap(), f.getParticleWidth(), f.getParticleHeight());
	}
	
	public static void formLine(ParticleEntity[] particles, int endX, int endY, int direction, int fillMode, boolean coordinateRandomnes, boolean overlap, int particleWidth, int particleHeight) {
		
	}
	
	public static void formRectangle(ParticleEntity[] particles, int width, int height, int direction, int fillMode, boolean randomnes, boolean overlap, int particleWidth, int particleHeight) {
		f.position(particles, width, height, fillMode, randomnes, overlap, particleWidth, particleHeight);
	}
	
	private static class RectangleFormer {
		private final Random r = new Random();
		
		public void position(ParticleEntity[] particles, int width, int height, int fillMode, boolean randomnes, boolean overlap, int particleWidth, int particleHeight){
			if(randomnes) {
				randomPosition(particles, width, height, fillMode, overlap, particleWidth, particleHeight);
			} else {
				
			}
		}
		
		
		
		private void randomPosition(ParticleEntity[] particles, int width, int height, int fillMode, boolean overlap, int particleWidth, int particleHeight) {
				switch(fillMode) {
				case FILLMODE_EDGES:
					fillEdges(particles, width, height, overlap, particleWidth, particleHeight);
				break;
				case FILLMODE_FILLED:
					fill(particles, width, height, overlap, particleWidth, particleHeight);
				break;
				default:
				break;
				}
		}
		
		private void drawEdge(ParticleEntity[] particles, int startX, int startY, boolean overlap, int endX, int endY, int particleWidth, int particleHeight, int startIndex, int count) {
			boolean vertical = startX == endX ? true : false;
			if(overlap) {
				if(vertical) {
					for(int i = startIndex; i < startIndex + count; i++) {
						particles[i].setPosition(startX, r.nextInt(endY - startY));					
					}
				} else {
					for(int i = startIndex; i < startIndex + count; i++) {
						particles[i].setPosition(r.nextInt(endX - startX), startY);
					}
				}
			}
		}
		
		private void fillEdges(ParticleEntity[] particles, int width, int height, boolean overlap, int particleWidth, int particleHeight) {
			int northEdge = r.nextInt(particles.length / 2);
			int southEdge = r.nextInt(particles.length / 2);
			int westEdge = r.nextInt(particles.length - (northEdge + southEdge));
			int eastEdge = particles.length - (northEdge + southEdge + westEdge);
			
			drawEdge(particles, 0, 0, overlap, width, 0, particleWidth, particleHeight, 0, northEdge);
			drawEdge(particles, width, 0, overlap, width, height, particleWidth, particleHeight, northEdge, eastEdge);
			drawEdge(particles, 0, 0, overlap, 0, height, particleWidth, particleHeight, northEdge + eastEdge, westEdge);
			drawEdge(particles, 0, height, overlap, width, height, particleWidth, particleHeight, northEdge + eastEdge + westEdge, southEdge);
			drawEdge(particles, 0, 0, overlap, width, height, particleWidth, particleHeight, 0, 100);
		}
		
		private void fill(ParticleEntity[] particles, int width, int height, boolean overlap, int particleWidth, int particleHeight) {
			if(overlap) {
				for(int i = 0; i < particles.length; i++) {
					particles[i].setPosition(r.nextInt(width), r.nextInt(height));										
				}
				return;
			}
			
			int widthParts = width / particleWidth;
			int heightParts = height / particleHeight;
			
			int avaible = widthParts * heightParts;
			boolean[] occupied = new boolean[widthParts * heightParts];
			
			for(int i = 0; i < particles.length; i++) {
				int x = r.nextInt(widthParts);
				int y = r.nextInt(heightParts);
				boolean isOccupied = occupied[x + y * widthParts];
				
				if(avaible <= 0) {
					particles[i].setPosition(x * particleWidth, y * particleHeight);
					continue;
				}
				
				if(!isOccupied) {
					occupied[x + y * widthParts] = true;
					particles[i].setPosition(x * particleWidth, y * particleHeight);
					avaible--;
					continue;
				} 
				
				if(occupied[x + y * widthParts] && avaible != 0) {
					for(int yy = x; yy < heightParts - x; yy++) {
						for(int xx = y; xx < widthParts - y; xx++) {
							if(!occupied[xx + yy * widthParts]){
								occupied[xx + yy * widthParts] = true;
								particles[i].setPosition(xx * particleWidth, yy * particleHeight);
								avaible--;
							}
						}
					}
				}
			}
		}
	}
}
