package svk.sglubos.engine.gfx.particle;

import svk.sglubos.engine.gfx.particle.components.ParticleFormation;
import svk.sglubos.engine.gfx.particle.components.ParticleFormer;
import svk.sglubos.engine.gfx.particle.components.ParticleInitializer;
import svk.sglubos.engine.gfx.particle.components.ParticleRenderer;
import svk.sglubos.engine.gfx.particle.components.ParticleUpdater;


public abstract class ParticleFactory {
	public abstract ParticleEmision createParticleEmision(int x, int y, double velocityX, double velocityY, int numParticles,long lifeTime, byte timeFormat, ParticleInitializer initializer, ParticleFormer former, ParticleFormation formation);
	public abstract ParticleEmisionTemplate createParticleTemplate(long life, byte timeFormat,int numParticles, ParticleRenderer renderer, ParticleUpdater updater, ParticleInitializer initializer, ParticleFormer former, ParticleFormation formation);
	public abstract ParticleEffect createParticleEffect(int emisions, long delay, byte timeFormat, ParticleEmisionTemplate template);
}
