package svk.sglubos.engine.test;

import svk.sglubos.engine.gfx.GameWindow;
import svk.sglubos.engine.gfx.Screen;
import svk.sglubos.engine.input.KeyBoard;
import svk.sglubos.engine.input.Mouse;

/**
 * Temporary class.
 * Game loop & stuff for testing
 */

public class Game implements Runnable{
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
		window = new GameWindow(500, 500, "game", 1);
		mainScreen = window.getScreen();
		
		Mouse.bind(window.getRenderCanvas());
		KeyBoard.bind(window.getRenderCanvas());
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
