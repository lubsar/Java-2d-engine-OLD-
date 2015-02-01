package svk.sglubos.engine.gfx.particle.basic;

import svk.sglubos.engine.gfx.particle.ParticleEffect;
import svk.sglubos.engine.gfx.particle.ParticleEntity;

public abstract class BasicParticleEffect extends ParticleEffect {
	protected ParticleEntity[] particles;
	
	
	public BasicParticleEffect(long lifeTime, byte timeFormat, int numParticles, double initialXVelocity, double intialYVelocity, int x, int y) {
		super(lifeTime, timeFormat);
		
		particles = new ParticleEntity[numParticles];
		for(int i = 0; i < particles.length; i++) {
			particles[0] = new ParticleEntity(x, y, initialXVelocity, intialYVelocity);
		}
		
	}


	@Override
	public void tick() {
		
	}

}
