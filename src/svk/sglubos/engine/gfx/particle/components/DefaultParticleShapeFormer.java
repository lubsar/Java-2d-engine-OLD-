package svk.sglubos.engine.gfx.particle.components;

import svk.sglubos.engine.gfx.particle.ParticleEffect;
import svk.sglubos.engine.gfx.particle.ParticleEffectFormer;
import svk.sglubos.engine.gfx.particle.ParticleFormation;
import svk.sglubos.engine.gfx.particle.ParticleFormation.RectangleFormation;
import svk.sglubos.engine.gfx.particle.basic.BasicParticleEffect;

public class DefaultParticleShapeFormer implements ParticleShapeFormer{

	@Override
	public void formShape(ParticleEffect effect, ParticleFormation f) {
		ParticleEffectFormer.formRectangle( ((BasicParticleEffect) effect).getParticles(), (RectangleFormation) f);
	}

}
