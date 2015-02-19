package svk.sglubos.engine.gfx.particle.components.basic;
//TODO documment
import svk.sglubos.engine.gfx.particle.ParticleEmision;
import svk.sglubos.engine.gfx.particle.ParticleEntity;
import svk.sglubos.engine.gfx.particle.components.ParticleUpdater;

public class BasicParticleUpdater extends ParticleUpdater {

	@Override
	public void tick(ParticleEmision effect) {
		ParticleEntity[] particles =  effect.getParticles(); 
		for(ParticleEntity particle : particles) {
			particle.tick();
		}
	}

}
