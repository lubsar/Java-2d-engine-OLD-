package svk.sglubos.engine.test;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;

import svk.sglubos.engine.gfx.GameWindow;
import svk.sglubos.engine.gfx.Screen;
import svk.sglubos.engine.input.Keyboard;
import svk.sglubos.engine.input.Mouse;
import svk.sglubos.engine.utils.MessageHandler;

/**
 * Temporary class.
 * Game loop & stuff for testing
 */

public class Game implements Runnable {
	private Screen mainScreen;
	private GameWindow window;
	
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
	
	public void tick(){
		if(Keyboard.isPressed(KeyEvent.VK_ESCAPE)) {
			MessageHandler.printMessage("DEBUG", "any keyPressed");
			System.out.println(window.toString());
		}
	}
	
	/**
	 * Renders game content. 
	 */
	public void render(){
		mainScreen.prepare();
		
		mainScreen.disposeGraphics();
		window.showRenderedContent();
	}
}
