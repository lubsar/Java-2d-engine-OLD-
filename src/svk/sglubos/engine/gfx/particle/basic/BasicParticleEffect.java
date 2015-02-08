package svk.sglubos.engine.gfx.particle.basic;

import java.util.Random;

import svk.sglubos.engine.gfx.particle.ParticleEffect;
import svk.sglubos.engine.gfx.particle.ParticleEntity;
import svk.sglubos.engine.gfx.particle.components.ParticleRenderer;
import svk.sglubos.engine.gfx.particle.components.ParticleShapeFormer;
import svk.sglubos.engine.gfx.particle.components.ParticleUpdater;

public class BasicParticleEffect extends ParticleEffect {
	private ParticleEntity[] particleTemplate;
	protected ParticleEntity[] particles;
	private int x;
	private int y;
	
	public BasicParticleEffect(long lifeTime, byte timeFormat, int numParticles, double initialXVelocity, double intialYVelocity, int x, int y) {
		super(lifeTime, timeFormat);
		
		particles = new ParticleEntity[numParticles];
		particleTemplate = new ParticleEntity[numParticles];
		
		for(int i = 0; i < particles.length; i++) {
			particleTemplate[i] = new ParticleEntity(0, 0, initialXVelocity + new Random().nextDouble(), intialYVelocity  + new Random().nextDouble());
			particles[i] = new ParticleEntity(0, 0, initialXVelocity + new Random().nextDouble(), intialYVelocity  + new Random().nextDouble());
		}
		
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void form(ParticleShapeFormer former) {
		former.formShape(this, f);
		//TODO fix
		for(int i = 0; i < particles.length; i++) {
			particles[i].addPosition(x, y);
		}
		
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
