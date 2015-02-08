package svk.sglubos.engine.gfx.particle.basic;

import java.awt.Color;

import svk.sglubos.engine.gfx.Screen;
import svk.sglubos.engine.gfx.particle.ParticleEffectFormer;
import svk.sglubos.engine.gfx.particle.ParticleFormation;
import svk.sglubos.engine.gfx.particle.components.DefaultParticleRenderer;
import svk.sglubos.engine.gfx.particle.components.DefaultParticleShapeFormer;
import svk.sglubos.engine.gfx.particle.components.DefaultParticleUpdater;

public class BasicParticleFactory {
	private static BasicParticleEffect effect;
	public static DefaultParticleRenderer renderer = new DefaultParticleRenderer(Color.WHITE, 2, 2);
	public static DefaultParticleUpdater updater = new DefaultParticleUpdater();
	public static DefaultParticleShapeFormer former = new DefaultParticleShapeFormer();
	public static ParticleFormation formation = new ParticleFormation.RectangleFormation(0, ParticleEffectFormer.FILLMODE_FILLED, true, 2, 2, true, 300, 120);
	
	public static void bind(Screen screen) {
		screen.addScreenComponent(renderer);
	}
	
	public static BasicParticleEffect test(long life, byte timeformat, double initialVelocityX, double initialVelocityY, int number, int x, int y) {
		effect = new BasicParticleEffect(life, timeformat, number, initialVelocityX, initialVelocityY, x, y);
		effect.setParticleFormation(formation);
		effect.form(former);
		effect.setParticleRender(renderer);
		effect.setParticleUpdater(updater);
		return effect;
	}
	
}
