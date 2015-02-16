package svk.sglubos.engine.gfx.particle.components;

import svk.sglubos.engine.gfx.ScreenComponent;
import svk.sglubos.engine.gfx.particle.ParticleEmision;

//TODO documment

public abstract class ParticleRenderer extends ScreenComponent {
	protected boolean update = false;
	
	public abstract void render(ParticleEmision e);
	
	public void tick() {
	}
	
	public boolean isUpdatable() {
		return update;
	}
}
