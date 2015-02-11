package svk.sglubos.engine.gfx.particle.basic;

import java.awt.Color;

import svk.sglubos.engine.gfx.Screen;
import svk.sglubos.engine.gfx.particle.ParticleEffectFormer;
import svk.sglubos.engine.gfx.particle.components.ParticleFormation;
import svk.sglubos.engine.gfx.particle.components.ParticleInitializer;
import svk.sglubos.engine.gfx.particle.components.ParticleRenderer;
import svk.sglubos.engine.gfx.particle.components.ParticleShapeFormer;
import svk.sglubos.engine.gfx.particle.components.ParticleUpdater;
import svk.sglubos.engine.gfx.particle.components.sgbasic.BasicParticleInitializer;
import svk.sglubos.engine.gfx.particle.components.sgbasic.BasicParticleRenderer;
import svk.sglubos.engine.gfx.particle.components.sgbasic.BasicParticleShapeFormer;
import svk.sglubos.engine.gfx.particle.components.sgbasic.BasicParticleUpdater;

public class BasicParticleFactory {
	public BasicParticleEffect createParticleEffect(long life, byte timeFromat, ParticleInitializer initializer, ParticleShapeFormer former, ParticleFormation formation, ParticleRenderer renderer, ParticleUpdater updater) {
		BasicParticleEffect e = new BasicParticleEffect(life, timeFromat);
		e.initialize(initializer);
		e.setParticleFormation(formation);
		e.form(former);
		e.setParticleRender(renderer);
		e.setParticleUpdater(updater);
		return e;
	}
	
	public static BasicParticleEffect test(long life, byte timeformat, double initialVelocityX, double initialVelocityY, int number, int x, int y, Screen screen) {
		BasicParticleEffect effect = new BasicParticleEffect(life, timeformat);
		
		BasicParticleRenderer renderer = new BasicParticleRenderer(Color.WHITE, 2, 2);
		screen.addScreenComponent(renderer);
		
		effect.initialize(new BasicParticleInitializer(x, y, initialVelocityX, initialVelocityY, number));
		effect.setParticleFormation(new ParticleFormation.RectangleFormation(0, ParticleEffectFormer.FILLMODE_FILLED, true, 2, 2, true, 300, 120));
		effect.form(new BasicParticleShapeFormer());
		effect.setParticleRender(renderer);
		effect.setParticleUpdater(new BasicParticleUpdater());
		return effect;
	}
	
}
