package svk.sglubos.engine.gfx;

import java.awt.Graphics;

public abstract class ScreenComponent {
	protected boolean bind;
	protected Graphics g;
	protected int[] pixels;
	
	public void bind(Graphics g, int[] pixels) {
		this.bind = true;
		this.g = g;
		this.pixels = pixels;
	}
	
	public void unbind() {
		this.bind = false;
		this.g = null;
		this.pixels = null;
	}
}
