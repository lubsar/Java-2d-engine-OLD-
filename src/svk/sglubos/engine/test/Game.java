package svk.sglubos.engine.test;

import java.awt.Color;

import javax.swing.JFrame;

import svk.sglubos.engine.gfx.RenderCanvas;
import svk.sglubos.engine.gfx.Screen;

/**
 * Temporary class.
 * Game loop & stuff for testing
 */

public class Game implements Runnable{
	
	String render = "null";
	
	private JFrame mainFrame = new JFrame("Game");
	private JFrame debugFrame = new JFrame("DEBUG");
	
	private RenderCanvas mainCanvas;
	private RenderCanvas debugCanvas;
	
	private Screen mainScreen;
	private Screen debugScreen;
	
	//Constructor
	public Game(){
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(true);
		
		debugFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		debugFrame.setResizable(false);
		
		start();
	}
	
	/**
	 * Initializes game content before starting game loop; 
	 */
	public void init(){
		mainScreen = new Screen(500,500,Color.BLACK);
		debugScreen = new Screen(640,300,Color.blue);
		
		mainScreen.setIngoreOffset(true);
		
		mainCanvas = new RenderCanvas(mainScreen,1.0);
		debugCanvas = new RenderCanvas(debugScreen,1.0);
		
		mainFrame.add(mainCanvas);
		mainFrame.pack();
		mainFrame.setVisible(true);
		
		debugFrame.add(debugCanvas);
		debugFrame.pack();
		debugFrame.setVisible(true);
		
		mainCanvas.init(2);
		debugCanvas.init(2);
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
	
	int x = 0;
	public void tick(){
		mainScreen.setOffset(x++, 0);
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
		
		debugScreen.setColor(Color.white);
		debugScreen.renderString(render, 0, 15);
		
		mainScreen.disposeGraphics();
		debugScreen.disposeGraphics();
		
		mainCanvas.showRenderedContent();
		debugCanvas.showRenderedContent();
	}
	
}
