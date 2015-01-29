package svk.sglubos.engine.gfx.particle.basic;

import java.awt.Color;
import java.awt.Graphics;

import svk.sglubos.engine.gfx.Screen;
import svk.sglubos.engine.gfx.particle.ParticleEffect;
import svk.sglubos.engine.gfx.particle.ParticleEntity;
import svk.sglubos.engine.gfx.particle.ParticleShapeFormer;
//TODO
public class BasicRectangleParticleEffect extends ParticleEffect {
	private Color color;
	private int particleWidth;
	private int particleHeight;
	private ParticleEntity[] particles;
	
	public BasicRectangleParticleEffect(long lifeTime, byte timeFormat, Color color, int particleWidth, int particleHeight, int numberOfParticles) {
		super(lifeTime, timeFormat);
		this.color = color;
		this.particleWidth = particleWidth;
		this.particleHeight = particleHeight;
		
		particles = new ParticleEntity[numberOfParticles];
		
		for(int i = 0; i < particles.length; i++) {
			particles[i] = new ParticleEntity(0,0,0,1);
		}
		
		ParticleShapeFormer.temp(particles, 500, 30);
	}

	@Override
	public void render(Screen screen) {
		Graphics g = screen.getGraphics();
		g.setColor(color);
		for(ParticleEntity part : particles) {
			g.fillRect(part.getX(), part.getY(), particleWidth, particleHeight);
		}
	}

	@Override
	public void tick() {
		updateTimer();
		for(ParticleEntity part : particles) {
			part.tick();
		}
	}
}
