package svk.sglubos.engine.gfx;

import java.awt.Graphics;

public abstract class ScreenComponent {
	protected Graphics g;
	protected Screen screen;
	protected int[] pixels;
	
	protected boolean bound;
	
	public void bind(Screen screen, Graphics g, int[] pixels) {
		this.bound = true;
		this.g = g;
		this.pixels = pixels;
		this.screen = screen;
	}
	
	public void unbind() {
		bound = false;
		g = null;
		pixels = null;
		screen = null;
	}
}
