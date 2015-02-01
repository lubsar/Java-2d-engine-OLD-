package svk.sglubos.engine.test;

import java.awt.Color;

import svk.sglubos.engine.gfx.GameRenderingWindow;
import svk.sglubos.engine.gfx.Screen;
import svk.sglubos.engine.gfx.particle.ParticleEffectFormer;
import svk.sglubos.engine.gfx.particle.ParticleFormation;
import svk.sglubos.engine.gfx.particle.basic.BasicRectangleParticleEffect;
import svk.sglubos.engine.utils.Timer;

/**
 * Temporary class.
 * Game loop & stuff for testing
 */

public class Game implements Runnable{
	private Screen mainScreen = new Screen(1280,720, Color.black);
	private GameRenderingWindow window;
	
	private BasicRectangleParticleEffect e = new BasicRectangleParticleEffect(-1, Timer.DELAY_FORMAT_MILLISECS, new Color(255, 137, 65), 2, 2, 1000, new ParticleFormation.RectangleFormation(0, ParticleEffectFormer.FILLMODE_EDGES, true, 6, 6, true, 100, 50));
	
	//Constructor
	public Game(){
		start();
	}
	
	/**
	 * Initializes game content before starting game loop; 
	 */
	public void init(){
		window = new GameRenderingWindow(mainScreen.getRenderLayer(),1.1119);
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
		double nanoSecPerTick = Math.pow(10, 9) / 120;
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
		e.tick();
	}
	
	/**
	 * Renders game content. 
	 */
	public void render(){
		mainScreen.prepare();
		
		if(e.isAlive()) {
			e.render(mainScreen);
		}
		
		mainScreen.disposeGraphics();
		window.render();
	}
}
