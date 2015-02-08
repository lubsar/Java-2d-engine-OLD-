package svk.sglubos.engine.gfx.particle;

import svk.sglubos.engine.gfx.particle.components.ParticleRenderer;
import svk.sglubos.engine.gfx.particle.components.ParticleShapeFormer;
import svk.sglubos.engine.gfx.particle.components.ParticleUpdater;
import svk.sglubos.engine.utils.Timer;
import svk.sglubos.engine.utils.TimerTask;

public abstract class ParticleEffect {
	protected long lifeTime;
	protected boolean alive;
	
	protected Timer timer;
	protected TimerTask task = () -> lifeTimeOver();
	protected ParticleFormation f;
	protected ParticleRenderer renderer;
	protected ParticleUpdater updater;
	
	public ParticleEffect(long lifeTime, byte timeFormat) {
		this.lifeTime = lifeTime;
		alive = true;
		timer = new Timer(task, timeFormat, lifeTime);
		timer.startCycle();
	}
	
	protected void updateTimer() {
		if(timer.isRunning() && lifeTime != -1)
			timer.update();
	}
	
	protected void lifeTimeOver() {
		alive = false;
	}
	
	public abstract void tick();
	public abstract void render();
	public abstract void form(ParticleShapeFormer former);
	
	public long getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(long lifeTime) {
		this.lifeTime = lifeTime;
	}

	public boolean isAlive() {
		return alive;
	}
	
	public void setParticleFormation(ParticleFormation formation) {
		this.f = formation;
	}
	
	public void setParticleRender(ParticleRenderer render) {
		this.renderer = render;
	}
	
	public void setParticleUpdater(ParticleUpdater updater) {
		this.updater = updater;
	}
}
