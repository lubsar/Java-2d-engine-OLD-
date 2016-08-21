package svk.sglubos.engine.test;

import java.awt.Color;

import svk.sglubos.engine.core.SyncedCore;
import svk.sglubos.engine.gfx.GameWindow;
import svk.sglubos.engine.gfx.Screen;

public class NoiseTest extends SyncedCore{
	private GameWindow window;
	private Screen screen;
	int[] pixels;
	private Noiser noiser;
	
	public NoiseTest() {
		super(10, true);
		start();
	}

	@Override
	protected void init() {
		window = new GameWindow(1900, 900, "NOISE", 1);
		screen = window.getScreen();
		noiser = new Noiser();
		pixels = screen.getPixels();
		
		screen.clear();
		screen.setColor(Color.red);
		screen.renderString(String.format("fps: %d", getFPS()), 0, 0);
		
		for(int y = 0; y < screen.getHeight(); y++) {
			for(int x = 0; x < screen.getWidth(); x++) {
				int noise = (int)(noiser.generateHeight(x) * 0xFF) & 0xff;
				pixels[x + y * screen.getWidth()] = 0xFF << 24 | noise << 16 | noise << 8 | noise;
			}
		}
		
		window.showRenderedContent();
	}

	@Override
	protected void tick() {
		
	}

	@Override
	protected void render() {
//		screen.clear();
//		screen.setColor(Color.red);
//		screen.renderString(String.format("fps: %d", getFPS()), 0, 0);
//		
//		for(int y = 0; y < 600; y++) {
//			for(int x = 0; x < 800; x++) {
//				int val = (int)(noiser.generateHeight(x, y) * 0xFF);
//				pixels[x + y * 600] = 0xFF << 24 |val << 16 | val << 8 | val;
//			}
//		}
//		
//		window.showRenderedContent();
	}

	@Override
	protected void stopped() {
		
	}
	
	
	public static void main(String[] args) {
		new NoiseTest();
	}
}
