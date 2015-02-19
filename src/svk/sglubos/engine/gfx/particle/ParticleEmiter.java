package svk.sglubos.engine.gfx.particle;

import java.util.ArrayList;
import java.util.List;

public class ParticleEmiter {
	
	private List<ParticleEffect> effects = new ArrayList<ParticleEffect>();
	
	public void emit(ParticleEffect effect, int x, int y, double velocityX, double velocityY) {
		effect.setX(x);
		effect.setY(y);
		effect.setVelocityX(velocityX);
		effect.setVelocityY(velocityY);
		effects.add(effect);
	}
	
	public void emit(ParticleEffect effect) {
		effects.add(effect);
	}
	
	public void tick() {
		for(ParticleEffect e : effects) {
			e.tick();
		}
	}
	
	public void render() {
		for(ParticleEffect e : effects) {
			e.render();
		}
	}
}
