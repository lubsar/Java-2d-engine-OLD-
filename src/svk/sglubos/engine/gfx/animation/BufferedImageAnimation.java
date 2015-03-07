package svk.sglubos.engine.gfx.animation;

import java.awt.image.BufferedImage;

import svk.sglubos.engine.gfx.Screen;
import svk.sglubos.engine.utils.debug.DebugStringBuilder;

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
	
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		
		ret.appendClassDataBracket(getClass(), hashCode());
		ret.appendTabln(super.toString());
		ret.appendObjectToStringTabln("images = ", images);
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}
