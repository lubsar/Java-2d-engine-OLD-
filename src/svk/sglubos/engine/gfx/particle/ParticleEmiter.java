package svk.sglubos.engine.gfx.particle;

import java.util.ArrayList;
import java.util.List;

public class ParticleEmiter {
	
	private List<ParticleEffect> effects = new ArrayList<ParticleEffect>();
	
	public void emit(ParticleTemplate template, ParticleFactory factory, int emisions, long delay, byte timeFormat, int x, int y, double velocityX, double velocityY) {
		effects.add(new ParticleEffect(factory, template, y, delay, timeFormat, x, y, velocityX, velocityY));
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
