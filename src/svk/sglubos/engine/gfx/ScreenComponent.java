package svk.sglubos.engine.gfx;

import java.awt.Graphics;

public interface ScreenComponent {
	
	public void bind(Graphics graphics, int[] pixels);
	public void unbind();
}
