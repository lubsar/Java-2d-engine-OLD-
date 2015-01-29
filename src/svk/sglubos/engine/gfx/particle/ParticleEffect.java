package svk.sglubos.engine.gfx.particle;

import svk.sglubos.engine.gfx.Screen;
import svk.sglubos.engine.utils.Timer;
import svk.sglubos.engine.utils.TimerTask;

public abstract class ParticleEffect {
	protected long lifeTime;
	protected boolean alive;
	
	protected Timer timer;
	protected TimerTask task = () -> lifeTimeOver();
	
	public ParticleEffect(long lifeTime, byte timeFormat) {
		this.lifeTime = lifeTime;
		alive = true;
		timer = new Timer(task, timeFormat, lifeTime);
		timer.start();
	}
	
	protected void updateTimer() {
		if(timer.isRunning())
			timer.update();
	}
	
	protected void lifeTimeOver() {
		alive = false;
	}
	
	public abstract void render(Screen screen);
	public abstract void tick();
	
	public long getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(long lifeTime) {
		this.lifeTime = lifeTime;
	}

	public boolean isAlive() {
		return alive;
	}
}
