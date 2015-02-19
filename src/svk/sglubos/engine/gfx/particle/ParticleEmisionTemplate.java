package svk.sglubos.engine.gfx.particle;

import svk.sglubos.engine.gfx.particle.components.ParticleFormation;
import svk.sglubos.engine.gfx.particle.components.ParticleFormer;
import svk.sglubos.engine.gfx.particle.components.ParticleInitializer;
import svk.sglubos.engine.gfx.particle.components.ParticleRenderer;
import svk.sglubos.engine.gfx.particle.components.ParticleUpdater;

public class ParticleEmisionTemplate {
	protected ParticleRenderer renderer;
	protected ParticleUpdater updater;
	protected ParticleFormer former;
	protected ParticleFormation formation;
	protected ParticleInitializer initializer;
	
	protected long lifeTime;
	protected byte timeFormat;
	protected int numParticles;
	
	public ParticleEmisionTemplate(long lifeTime, byte timeFormat, int numParticles, ParticleRenderer renderer, ParticleUpdater updater, ParticleFormer former, ParticleFormation formation, ParticleInitializer initializer) {
		this.renderer = renderer;
		this.updater = updater;
		this.former = former;
		this.formation = formation;
		this.initializer = initializer;
		this.lifeTime = lifeTime;
		this.timeFormat = timeFormat;
		this.numParticles = numParticles;
	}

	public ParticleRenderer getRenderer() {
		return renderer;
	}

	public ParticleUpdater getUpdater() {
		return updater;
	}

	public ParticleFormer getFormer() {
		return former;
	}

	public ParticleFormation getFormation() {
		return formation;
	}

	public ParticleInitializer getInitializer() {
		return initializer;
	}
	
	public long getLifeTime() {
		return lifeTime;
	}
	
	public byte getTimeFormat() {
		return timeFormat;
	}
	
	public int getNumParticles() {
		return numParticles;
	}
}
