package svk.sglubos.engine.test;

import java.awt.Graphics;

import svk.sglubos.engine.gfx.ScreenComponent;

public class ScreenComponentTest implements ScreenComponent {
	private boolean unbind;
	private int[] pixels;
	
	@Override
	public void bind(Graphics g, int[] pixels) {
		unbind = false;
		this.pixels = pixels;
	}
	
	public void shadeItAll() {
		if(unbind) {
			return;
		}
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = pixels[i] & 0xFF;
		}
	}
	
	@Override
	public void unbind() {
		unbind = true;
	}

}
