package svk.sglubos.engine.gfx.particle.components;

import svk.sglubos.engine.gfx.ScreenComponent;
import svk.sglubos.engine.gfx.particle.ParticleEffect;

public abstract class ParticleRenderer extends ScreenComponent {
	protected boolean update = false;
	
	public abstract void render(ParticleEffect e);
	
	public void tick() {
	}
	
	public boolean isUpdatable() {
		return update;
	}
}
