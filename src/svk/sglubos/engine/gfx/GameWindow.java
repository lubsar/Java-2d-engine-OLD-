package svk.sglubos.engine.gfx;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {
	protected Screen screen;
	protected BufferedImage renderLayer;
	protected RenderCanvas canvas;

	public GameWindow(int width, int height, String title) {
		this(width, height, title, 1.0, Color.black);
	}
	
	public GameWindow(int width,int height, String title, double canvasScale){
		this(width, height, title, canvasScale, Color.black);
	}
	
	public GameWindow(int width, int height, String title, Color color){
		this(width, height, title, 1.0, color);
	}

	public GameWindow(int width, int height, String title, double canvasScale, Color defaultScreenColor) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		screen = new Screen(width, height, defaultScreenColor);
		canvas = new RenderCanvas(screen, canvasScale);

		add(canvas);
		pack();
		setVisible(true);

		canvas.init(2);
	}

	public Screen getScreen() {
		return screen;
	}

	public void showContent() {
		canvas.showRenderedContent();
	}
}
