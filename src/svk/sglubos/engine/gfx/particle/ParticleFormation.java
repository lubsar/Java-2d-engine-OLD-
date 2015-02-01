package svk.sglubos.engine.gfx.particle;
//TODO documment
public abstract class ParticleFormation {
	private int velocity;
	private int fillMode;
	private int particleWidth;
	private int particleHeight;
	
	private boolean overLap;
	private boolean randomness;
	
	public ParticleFormation(int velocity, int fillMode, boolean randomness, int particleWidth, int particleHeight, boolean overLap) {
		this.velocity = velocity;
		this.fillMode = fillMode;
		this.randomness = randomness;
		this.particleWidth = particleWidth;
		this.particleHeight = particleHeight;
		this.overLap = overLap;
	}
	
	public int getVelocity() {
		return velocity;
	}

	public int getFillMode() {
		return fillMode;
	}

	public boolean isRandomness() {
		return randomness;
	}

	public int getParticleWidth() {
		return particleWidth;
	}

	public int getParticleHeight() {
		return particleHeight;
	}

	public boolean isOverLap() {
		return overLap;
	}

	public static class LineFormation extends ParticleFormation {
		private int endX;
		private int endY;
		
		public LineFormation(int velocity, int fillMode, boolean randomness, int particleWidth, int particleHeight, boolean overLap, int endX, int endY) {
			super(velocity, fillMode, randomness, particleWidth, particleHeight, overLap);
			
			this.endX = endX;
			this.endY = endY;
		}

		public int getEndX() {
			return endX;
		}

		public int getEndY() {
			return endY;
		}
	}
	
	public static class RectangleFormation extends ParticleFormation {
		private int width;
		private int height;
		
		public RectangleFormation(int velocity, int fillMode, boolean randomness, int particleWidth, int particleHeight, boolean overLap, int width, int height) {
			super(velocity, fillMode, randomness, particleWidth, particleHeight, overLap);
			
			this.width = width;
			this.height = height;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}
	}
	
	public static class OvalFormation extends ParticleFormation {
		private int width;
		private int height;
		
		public OvalFormation(int velocity, int fillMode, boolean randomness, int particleWidth, int particleHeight, boolean overLap, int width, int height) {
			super(velocity, fillMode, randomness, particleWidth, particleHeight, overLap);
			
			this.width = width;
			this.height = height;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}
	}
}
