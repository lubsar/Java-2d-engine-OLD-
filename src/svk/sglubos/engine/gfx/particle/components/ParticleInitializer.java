package svk.sglubos.engine.gfx.particle.components;

import svk.sglubos.engine.gfx.particle.ParticleEffect;
import svk.sglubos.engine.gfx.particle.ParticleEntity;

public interface ParticleInitializer {
	
	public void init(ParticleEffect effect);
	public ParticleEntity[] getParticles();
}
