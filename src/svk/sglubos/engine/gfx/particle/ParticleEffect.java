package svk.sglubos.engine.gfx.particle;

import java.util.ArrayList;

import svk.sglubos.engine.gfx.particle.components.ParticleFormation;
import svk.sglubos.engine.gfx.particle.components.ParticleFormer;
import svk.sglubos.engine.gfx.particle.components.ParticleInitializer;
import svk.sglubos.engine.gfx.particle.components.ParticleRenderer;
import svk.sglubos.engine.gfx.particle.components.ParticleUpdater;
import svk.sglubos.engine.utils.Timer;
import svk.sglubos.engine.utils.TimerTask;

public class ParticleEffect {
	protected Timer timer;
	protected TimerTask emit = () -> emit();
	
	protected boolean alive;
	
	protected long delay;
	protected byte timeFormat;
	
	protected int x;
	protected int y;
	
	protected int numParticles;
	
	protected double velocityX;
	protected double velocityY;
	
	protected long emisionLifeTime;
	protected byte emisionTimeFormat;
	
	protected ParticleUpdater updater;
	protected ParticleRenderer renderer;
	protected ParticleInitializer initializer;
	protected ParticleFormer former;
	protected ParticleFormation formation;
	
	protected ParticleFactory factory;
	protected ArrayList<ParticleEmision> emisions = new ArrayList<ParticleEmision>();
	
	public ParticleEffect(ParticleFactory factory, ParticleEmisionTemplate template, int emisions, long delay, byte timeFormat) {
		this.factory = factory;
		this.timeFormat = timeFormat;
		this.delay = delay;
		
		this.numParticles = template.getNumParticles();
		this.renderer = template.getRenderer();
		this.updater = template.getUpdater();
		this.initializer = template.getInitializer();
		this.former = template.getFormer();
		this.formation = template.getFormation();
		
		timer = new Timer(emit, timeFormat, delay);
		
		if(emisions == -1 ) {
			timer.startInfiniteCycle();
			return;
		}
		timer.startLoop(emisions);
	}
	
	public void tick() {
		timer.update();
		for(ParticleEmision e : emisions) {
			if(e.isAlive()) {
				updater.tick(e);
				if(renderer.isUpdatable())
					renderer.tick();
			}
		}
	}
	
	public void render() {
		for(ParticleEmision e : emisions) {
			if(e.isAlive()) {
				renderer.render(e);
			}
		}
	}
	
	public void emit() {
		emisions.add(factory.createParticleEmision(x, y, velocityX, velocityY, numParticles,delay, emisionTimeFormat, initializer, former, formation));
	}
	
	public ParticleRenderer getParticleParticleRenderer() {
		return renderer;
	}

	public void setParticleRenderer(ParticleRenderer renderer) {
		this.renderer = renderer;
	}

	public ParticleUpdater getParticleUpdater() {
		return updater;
	}

	public void setParticleUpdater(ParticleUpdater updater) {
		this.updater = updater;
	}

	public ParticleFormer getParticleFormer() {
		return former;
	}

	public void setParticleFormer(ParticleFormer former) {
		this.former = former;
	}

	public ParticleFormation getParticleFormation() {
		return formation;
	}

	public void setParticleFormation(ParticleFormation formation) {
		this.formation = formation;
	}

	public ParticleInitializer getParticleInitializer() {
		return initializer;
	}

	public void setParticleInitializer(ParticleInitializer initializer) {
		this.initializer = initializer;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}
}
