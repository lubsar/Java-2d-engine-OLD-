package svk.sglubos.engine.gfx.particle.components;

import svk.sglubos.engine.gfx.particle.ParticleEffect;
import svk.sglubos.engine.gfx.particle.ParticleEntity;
import svk.sglubos.engine.gfx.particle.basic.BasicParticleEffect;

public class DefaultParticleUpdater extends ParticleUpdater {

	@Override
	public void tick(ParticleEffect effect) {
		ParticleEntity[] particles = ((BasicParticleEffect) effect).getParticles(); 
		
		for(ParticleEntity particle : particles) {
			particle.addVelocity(-0.00485, - 0.00456);
			particle.tick();
		}
	}

}
