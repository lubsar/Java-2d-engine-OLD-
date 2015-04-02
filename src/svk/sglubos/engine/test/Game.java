package svk.sglubos.engine.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

import svk.sglubos.engine.gfx.GameWindow;
import svk.sglubos.engine.gfx.Screen;
import svk.sglubos.engine.input.Keyboard;
import svk.sglubos.engine.input.Mouse;

/**
 * Temporary class.
 * Game loop & stuff for testing
 */

public class Game implements Runnable {
	private Screen mainScreen;
	private GameWindow window;
	private GameWindow windows;
	
	String msg = "";
	
	//Constructor
	public Game(){
		start();
	}
	
	/**
	 * Initializes game content before starting game loop; 
	 */
	public void init(){
		window = new GameWindow(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(), 500, 500, "game", 1, Color.black);
		windows = new GameWindow(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(), 500, 500, "game", 1, Color.black);
		mainScreen = window.getScreen();
		mainScreen.setFont(new Font("null", Font.BOLD, 15));
		
		Mouse.bind(window.getRenderCanvas());
		Mouse.bind(windows.getRenderCanvas());
		Keyboard.bind(window.getRenderCanvas());
		Keyboard.recordKeyCharacters();
	}
	
	public void start(){
		new Thread(this,"game").start();
	}
	
	//Game loop
	@Override
	public void run() {
		init();
		
		long lastTime = System.nanoTime();
		long lastTimeDebugOutput = System.currentTimeMillis();
		double delta = 0;
		double nanoSecPerTick = Math.pow(10, 9) / 20;
		int fps = 0;
		int ticks = 0;
		
		while(true){
			long now = System.nanoTime();
			delta += (now - lastTime) /nanoSecPerTick;
			lastTime = now;
				
			while(delta >= 1){
				delta--;
				tick();
				ticks++;
			}
		
			render();
			fps++;
			
//			try {
//				Thread.sleep(6);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
				
			if((System.currentTimeMillis() - lastTimeDebugOutput) >= 1000){
				System.out.println("[DEBUG] ticks: " + ticks + "fps: " + fps);
				lastTimeDebugOutput += 1000;
				fps = 0;
				ticks = 0;
			}
		}
	}
	
	/**
	 * Updates game content.
	 */
	
	public void tick(){
		if(Keyboard.isAnyKeyPressed()) {
			msg += Keyboard.getRecordedKeySequence();
			Keyboard.recordKeyCharacters();
			System.out.println( mainScreen.getWidth() * mainScreen.getHeight());
		}
	}
	
	/**
	 * Renders game content. 
	 */
	public void render(){
		mainScreen.prepare();
		
		for(int y = 0; y < mainScreen.getHeight(); y++) {
			for(int x = 0; x < mainScreen.getWidth(); x++) {
				mainScreen.renderFilledRectangle(x, y, 1, 1,new Color(255,23,125));
			}
		}
		
		mainScreen.disposeGraphics();
		window.showRenderedContent();
	}
}
