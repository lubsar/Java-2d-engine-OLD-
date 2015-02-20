package svk.sglubos.engine.gfx.animation;

import java.awt.image.BufferedImage;

import svk.sglubos.engine.gfx.Screen;

//TODO Documentation
public class BufferedImageAnimation extends Animation {
	protected BufferedImage[] images;
	
	public BufferedImageAnimation(BufferedImage[] images, long frameDelay, byte timeFormat, int frames) {
		super(frameDelay, timeFormat, frames);
		this.images = images;
	}

	@Override
	public void render(Screen screen, int x, int y) {
		screen.renderImage(images[currentFrame], x, y);
	}
}
