package svk.sglubos.engine.gfx;

import java.awt.image.BufferedImage;

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
