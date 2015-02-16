package svk.sglubos.engine.gfx.particle;

//TODO documment

import svk.sglubos.engine.utils.Timer;
import svk.sglubos.engine.utils.TimerTask;

public class ParticleEmision {
	protected long lifeTime;
	protected boolean alive;
	
	protected Timer timer;
	protected TimerTask task = () -> lifeTimeOver();
	protected ParticleEntity[] particles;
	
	public ParticleEmision(long lifeTime, byte timeFormat, int numParticles) {
		this.lifeTime = lifeTime;
		alive = true;
		timer = new Timer(task, timeFormat, lifeTime);
		timer.startCycle();
		particles = new ParticleEntity[numParticles];
	}
	
	protected void updateTimer() {
		if(timer.isRunning() && lifeTime != -1)
			timer.update();
	}
	
	protected void lifeTimeOver() {
		alive = false;
	}
	
	public long getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(long lifeTime) {
		this.lifeTime = lifeTime;
	}

	public boolean isAlive() {
		return alive;
	}
	
	public byte getTimeformat() {
		return timer.getTimeFormat();
	}
	
	public ParticleEntity[] getParticles() {
		return particles;
	}
}
