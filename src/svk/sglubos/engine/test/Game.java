package svk.sglubos.engine.test;

import java.awt.Color;
import java.util.Random;

import svk.sglubos.engine.gfx.GameWindow;
import svk.sglubos.engine.gfx.Screen;
import svk.sglubos.engine.gfx.sprite.Sprite;

/**
 * Temporary class.
 * Game loop & stuff for testing
 */

public class Game implements Runnable{
	
	String render = "null";
	
	private Sprite test;
	
	private Screen mainScreen;
	private Screen debugScreen;
	
	private GameWindow mainWindow;
	private GameWindow debugWindow;
	
	//Constructor
	public Game(){
		start();
	}
	
	/**
	 * Initializes game content before starting game loop; 
	 */
	public void init(){
		int[] pixels = new int[50*50];
		for(int i = 0; i < pixels.length;i++){
			pixels[i] = 0xFF00FF;
		}
		
		test = new Sprite(50,50,pixels);
		
		mainWindow = new GameWindow(500,500,"Game",2.0);
		debugWindow = new GameWindow(640,300,"Debug",Color.BLUE);
		
		mainScreen = mainWindow.getScreen();
		debugScreen = debugWindow.getScreen();
		
//		mainScreen.setIngoreOffset(true);
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
		double nanoSecPerTick = Math.pow(10, 9)/60;
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
				
			if((System.currentTimeMillis() - lastTimeDebugOutput) >= 1000){
				render = "[DEBUG] ticks: " + ticks + "fps: " + fps;
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
		debugScreen.prepare();
		
		mainScreen.setColor(Color.RED);
		
		mainScreen.renderArc(0, 0, 50, 50, 90, 180);
		mainScreen.renderFilledArc(50, 0, 50, 50, 90, 180);
		
		mainScreen.renderOval(100, 0, 50, 50);
		mainScreen.renderFiledOval(150, 0, 50, 50);
		
		mainScreen.renderRectangle(200, 0, 50, 50);
		mainScreen.renderFilledRectangle(250, 0, 50, 50);
		
		mainScreen.renderLine(300, 0, 349, 0);
		
		mainScreen.renderSprite(test, 350, 0);
		mainScreen.renderString("auto", 400, 10);
		
		debugScreen.setColor(Color.white);
		debugScreen.renderString(render, 0, 15);
		
		mainScreen.disposeGraphics();
		debugScreen.disposeGraphics();
		
		mainWindow.showContent();
		debugWindow.showContent();
	}
	
}
