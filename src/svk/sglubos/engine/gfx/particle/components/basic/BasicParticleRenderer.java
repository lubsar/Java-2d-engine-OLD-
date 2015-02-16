package svk.sglubos.engine.gfx.particle.components.basic;
//TODO documment
import java.awt.Color;

import svk.sglubos.engine.gfx.particle.ParticleEmision;
import svk.sglubos.engine.gfx.particle.ParticleEntity;
import svk.sglubos.engine.gfx.particle.components.ParticleRenderer;

public class BasicParticleRenderer extends ParticleRenderer {
	private Color color;
	private int width;
	private int height;
	
	public BasicParticleRenderer(int width, int height, Color color) {
		this.color = color;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void render(ParticleEmision e) {
		ParticleEntity[] particles = e.getParticles();
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

	public Color getColor() {
		return color;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
