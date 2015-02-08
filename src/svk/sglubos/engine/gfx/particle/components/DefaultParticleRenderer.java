package svk.sglubos.engine.gfx.particle.components;

import java.awt.Color;

import svk.sglubos.engine.gfx.particle.ParticleEffect;
import svk.sglubos.engine.gfx.particle.ParticleEntity;
import svk.sglubos.engine.gfx.particle.basic.BasicParticleEffect;

public class DefaultParticleRenderer extends ParticleRenderer {
	private Color color;
	private int width;
	private int height;
	
	public DefaultParticleRenderer(Color color, int width, int height) {
		this.color = color;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void render(ParticleEffect e) {
		ParticleEntity[] particles = ((BasicParticleEffect) e).getParticles();
		g.setColor(color);
		for(ParticleEntity entity : particles) {
			g.fillRect((int) entity.getX(), (int) entity.getY(), width, height);
		}
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		 this.height = height;
	}
}
