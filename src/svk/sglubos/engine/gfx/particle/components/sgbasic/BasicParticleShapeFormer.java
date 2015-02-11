package svk.sglubos.engine.gfx.particle.components.sgbasic;

import svk.sglubos.engine.gfx.particle.ParticleEffect;
import svk.sglubos.engine.gfx.particle.ParticleEffectFormer;
import svk.sglubos.engine.gfx.particle.basic.BasicParticleEffect;
import svk.sglubos.engine.gfx.particle.components.ParticleFormation;
import svk.sglubos.engine.gfx.particle.components.ParticleFormation.RectangleFormation;
import svk.sglubos.engine.gfx.particle.components.ParticleShapeFormer;

public class BasicParticleShapeFormer implements ParticleShapeFormer{

	@Override
	public void formShape(ParticleEffect effect, ParticleFormation f) {
		ParticleEffectFormer.formRectangle( ((BasicParticleEffect) effect).getParticles(), (RectangleFormation) f);
	}

}
