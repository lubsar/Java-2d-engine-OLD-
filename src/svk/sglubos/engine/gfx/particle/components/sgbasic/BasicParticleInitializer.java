package svk.sglubos.engine.gfx.particle.components.sgbasic;

import svk.sglubos.engine.gfx.particle.ParticleEffect;
import svk.sglubos.engine.gfx.particle.ParticleEntity;
import svk.sglubos.engine.gfx.particle.components.ParticleInitializer;

public class BasicParticleInitializer implements ParticleInitializer{
	private int x;
	private int y;
	
	private ParticleEntity[] particles;
	
	private double initialVelocityX;
	private double initialVelocityY;
	
	public BasicParticleInitializer(int x, int y, double initialVelocityX, double initialVelocityY, int numberOfParticles) {
		this.x = x;
		this.y = y;
		this.initialVelocityX = initialVelocityX;
		this.initialVelocityY = initialVelocityY;
		
		particles = new ParticleEntity[numberOfParticles];
	}

	@Override
	public void init(ParticleEffect effect) {
		for(int i = 0; i < particles.length; i++) {
			particles[i] = new ParticleEntity(x, y, initialVelocityX, initialVelocityY);
		}
	}

	@Override
	public ParticleEntity[] getParticles() {
		return particles;
	}
}
