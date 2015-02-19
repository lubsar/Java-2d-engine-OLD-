package svk.sglubos.engine.gfx.particle.basic;

import svk.sglubos.engine.gfx.particle.ParticleEffect;
import svk.sglubos.engine.gfx.particle.ParticleEmision;
import svk.sglubos.engine.gfx.particle.ParticleFactory;
import svk.sglubos.engine.gfx.particle.ParticleEmisionTemplate;
import svk.sglubos.engine.gfx.particle.components.ParticleFormation;
import svk.sglubos.engine.gfx.particle.components.ParticleFormer;
import svk.sglubos.engine.gfx.particle.components.ParticleInitializer;
import svk.sglubos.engine.gfx.particle.components.ParticleRenderer;
import svk.sglubos.engine.gfx.particle.components.ParticleUpdater;
import svk.sglubos.engine.gfx.particle.components.basic.BasicParticleInitializer;

//TODO documment

public class BasicParticleFactory extends ParticleFactory{

	@Override
	public ParticleEmision createParticleEmision(int x, int y, double velocityX, double velocityY, int numParticles, long lifeTime, byte timeFormat, ParticleInitializer initializer, ParticleFormer former, ParticleFormation formation) {
		ParticleEmision emision = new ParticleEmision(lifeTime, timeFormat, numParticles);
		((BasicParticleInitializer) initializer).setX(x);
		((BasicParticleInitializer) initializer).setY(y);
		((BasicParticleInitializer) initializer).setInitialVelocityX(velocityX);
		((BasicParticleInitializer) initializer).setInitialVelocityY(velocityY);
		initializer.init(emision);
		former.formShape(emision, formation);
		
		return emision;
	}
	
	@Override
	public ParticleEmisionTemplate createParticleTemplate(long life,byte timeFormat,int numParticles, ParticleRenderer renderer, ParticleUpdater updater, ParticleInitializer initializer, ParticleFormer former, ParticleFormation formation) {
		return new ParticleEmisionTemplate(life, timeFormat, numParticles, renderer, updater, former, formation, initializer);
	}

	@Override
	public ParticleEffect createParticleEffect(int emisions, long delay, byte timeFormat, ParticleEmisionTemplate template) {
		return new ParticleEffect(this, template, emisions, delay, timeFormat);
	}
}
