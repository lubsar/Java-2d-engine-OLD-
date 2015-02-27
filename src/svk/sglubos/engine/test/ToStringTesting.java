package svk.sglubos.engine.test;

import java.awt.Color;
import java.awt.GraphicsEnvironment;

import svk.sglubos.engine.gfx.GameWindow;
import svk.sglubos.engine.gfx.RenderCanvas;
import svk.sglubos.engine.gfx.Screen;
import svk.sglubos.engine.gfx.animation.BufferedImageAnimation;
import svk.sglubos.engine.utils.Timer;

public class ToStringTesting {
	
	private Screen screen = new Screen(500, 500, Color.red);
	private GameWindow window = new GameWindow(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(), 500, 500, "test", 1.0, Color.RED);
	private RenderCanvas canvas = new RenderCanvas(screen, 1.5);
	
	private BufferedImageAnimation bimAnimation = new BufferedImageAnimation(null, 100, Timer.DELAY_FORMAT_MILLISECS, 200);
	
	public ToStringTesting() {
		print(screen);
		System.out.println();
		print(window);
		System.out.println();
		print(canvas);
		System.out.println();
		print(bimAnimation);
		System.out.println();
	}
	
	public static void main(String... args) {
		new ToStringTesting();
	}
	
	public void print(Object o) {
		System.out.println(o.toString());
	}
}
