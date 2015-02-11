package svk.sglubos.engine.gfx.particle.components.sgbasic;

import svk.sglubos.engine.gfx.particle.ParticleEffect;
import svk.sglubos.engine.gfx.particle.ParticleEntity;
import svk.sglubos.engine.gfx.particle.basic.BasicParticleEffect;
import svk.sglubos.engine.gfx.particle.components.ParticleUpdater;

public class BasicParticleUpdater extends ParticleUpdater {

	@Override
	public void tick(ParticleEffect effect) {
		ParticleEntity[] particles = ((BasicParticleEffect) effect).getParticles(); 
		
		for(ParticleEntity particle : particles) {
			particle.tick();
		}
	}

}
