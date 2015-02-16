package svk.sglubos.engine.gfx.particle.components.basic;
//TODO documment
import svk.sglubos.engine.gfx.particle.ParticleEmision;
import svk.sglubos.engine.gfx.particle.ParticleEntity;
import svk.sglubos.engine.gfx.particle.components.ParticleInitializer;

public class BasicParticleInitializer implements ParticleInitializer{
	private int x;
	private int y;
	
	private double initialVelocityX;
	private double initialVelocityY;
	
	@Override
	public void init(ParticleEmision effect) {
		ParticleEntity[] particles = effect.getParticles();
		
		for(int i = 0; i < particles.length; i++) {
			particles[i] = new ParticleEntity(x, y, initialVelocityX, initialVelocityY);
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getInitialVelocityX() {
		return initialVelocityX;
	}

	public void setInitialVelocityX(double initialVelocityX) {
		this.initialVelocityX = initialVelocityX;
	}

	public double getInitialVelocityY() {
		return initialVelocityY;
	}

	public void setInitialVelocityY(double initialVelocityY) {
		this.initialVelocityY = initialVelocityY;
	}
}
