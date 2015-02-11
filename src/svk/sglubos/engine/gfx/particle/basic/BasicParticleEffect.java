package svk.sglubos.engine.gfx.particle.basic;

import svk.sglubos.engine.gfx.particle.ParticleEffect;
import svk.sglubos.engine.gfx.particle.ParticleEntity;
import svk.sglubos.engine.gfx.particle.components.ParticleInitializer;
import svk.sglubos.engine.gfx.particle.components.ParticleShapeFormer;

public class BasicParticleEffect extends ParticleEffect {
	protected ParticleEntity[] particles;
	
	public BasicParticleEffect(long lifeTime, byte timeFormat) {
		super(lifeTime, timeFormat);
	}
	
	@Override
	public void form(ParticleShapeFormer former) {
		former.formShape(this, formation);
	}
	
	@Override
	public void initialize(ParticleInitializer initializer) {
		initializer.init(this);
		particles = initializer.getParticles();
	}
	
	@Override
	public void tick() {
		timer.update();
		updater.tick(this);
		if(renderer.isUpdatable())
			renderer.tick();
	}
	
	@Override
	public void render() {
		renderer.render(this);
	}
	
	
	public ParticleEntity[] getParticles() {
		return particles;
	}

}
