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
		mainScreen = window.getScreen();
		mainScreen.setFont(new Font("null", Font.BOLD, 15));
		
		Mouse.bind(window.getRenderCanvas());
		Keyboard.bind(window.getRenderCanvas());
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
			
			try {
				Thread.sleep(6);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
				
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
	boolean saved = false;
	public void tick(){
		if(Keyboard.isAnyKeyPressed()) {
			msg += Keyboard.getRecordedKeySequence();
			Keyboard.recordKeyCharacters();
		}
	}
	
	/**
	 * Renders game content. 
	 */
	public void render(){
		mainScreen.prepare();
		
		mainScreen.setColor(Color.white);
		mainScreen.renderString(msg, 30, 30);
		
		mainScreen.disposeGraphics();
		window.showRenderedContent();
	}
}
