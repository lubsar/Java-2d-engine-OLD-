package svk.sglubos.engine.gfx.particle.basic;

import java.awt.Color;
import java.awt.Graphics;

import svk.sglubos.engine.gfx.Screen;
import svk.sglubos.engine.gfx.particle.ParticleEffect;
import svk.sglubos.engine.gfx.particle.ParticleEffectFormer;
import svk.sglubos.engine.gfx.particle.ParticleEntity;
import svk.sglubos.engine.gfx.particle.ParticleFormation;
//TODO
public class BasicRectangleParticleEffect extends ParticleEffect {
	private Color color;
	private int particleWidth;
	private int particleHeight;
	private ParticleEntity[] particles;
	
	int tempX, tempY;
	boolean home;
	
	public BasicRectangleParticleEffect(long lifeTime, byte timeFormat, Color color, int particleWidth, int particleHeight, int numberOfParticles, ParticleFormation.RectangleFormation f) {
		super(lifeTime, timeFormat);
		this.color = color;
		this.particleWidth = particleWidth;
		this.particleHeight = particleHeight;
		
		particles = new ParticleEntity[numberOfParticles];
		
		for(int i = 0; i < particles.length; i++) {
			particles[i] = new ParticleEntity(0,0,0,0);
		}
		
		ParticleEffectFormer.formRectangle(particles, f);
	}
	
	@Override
	public void render(Screen screen) {
		Graphics g = screen.getGraphics();
		g.setColor(color);
		for(ParticleEntity part : particles) {
			g.fillRect((int)part.getX(), (int) part.getY(), particleWidth, particleHeight);
		}
	}
	
	public void setPath(int x, int y) {
		this.tempX = x;
		this.tempY = y;
		home = true;
	}
	
	@Override
	public void tick() {
		updateTimer();
		for(ParticleEntity part : particles) {
			part.tick();
		}
	}
	
	public ParticleEntity[] getParticles() {
		return particles;
	}
}
