package svk.sglubos.engine.test;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;

import javax.swing.JFrame;

import svk.sglubos.engine.gfx.Screen;

/**
 * Temporary class.
 * Game loop & stuff for testing
 */

public class Game implements Runnable{
	
	String render = "null";
	
	private JFrame mainFrame = new JFrame("Game");
	private JFrame debugFrame = new JFrame("DEBUG");
	
	private Canvas mainCanvas = new Canvas();
	private Canvas debugCanvas = new Canvas();
	
	private Screen mainScreen;
	private Screen debugScreen;
	
	//Constructor
	public Game(){
		mainCanvas.setSize(1280,720);
		debugCanvas.setSize(640,300);
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		mainFrame.add(mainCanvas);
		mainFrame.pack();
		
		mainFrame.setVisible(true);
		
		debugFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		debugFrame.setResizable(true);
		debugFrame.add(debugCanvas);
		debugFrame.pack();
		
		debugFrame.setVisible(true);
		
		start();
	}
	
	/**
	 * Initializes game content before starting game loop; 
	 */
	public void init(){
		mainScreen = new Screen(mainCanvas,3,Color.GREEN);
		debugScreen = new Screen(debugCanvas,3,new Color(0,0,168));
		
		debugScreen.setWith(800);
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
				render();
				fps++;
			}
				
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
	 * 
	 */
	public void render(){
		mainScreen.ClearToDefaultColor();
		debugScreen.ClearToDefaultColor();
		
		mainScreen.renderSquare(300, 300,0,0);
		debugScreen.renderString(render, 0, 15);
		
		
		mainScreen.show();
		debugScreen.show();
	}
	
}
