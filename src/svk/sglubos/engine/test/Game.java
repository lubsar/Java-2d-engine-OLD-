package svk.sglubos.engine.test;

import java.awt.Color;

import svk.sglubos.engine.gfx.GameWindow;
import svk.sglubos.engine.gfx.Screen;
import svk.sglubos.engine.gfx.particle.ParticleEmiter;
import svk.sglubos.engine.gfx.particle.ParticleFactory;
import svk.sglubos.engine.gfx.particle.ParticleTemplate;
import svk.sglubos.engine.gfx.particle.basic.BasicParticleFactory;
import svk.sglubos.engine.gfx.particle.components.ParticleFormation;
import svk.sglubos.engine.gfx.particle.components.basic.BasicParticleFormer;
import svk.sglubos.engine.gfx.particle.components.basic.BasicParticleInitializer;
import svk.sglubos.engine.gfx.particle.components.basic.BasicParticleRenderer;
import svk.sglubos.engine.gfx.particle.components.basic.BasicParticleUpdater;
import svk.sglubos.engine.input.Mouse;
import svk.sglubos.engine.utils.Timer;

/**
 * Temporary class.
 * Game loop & stuff for testing
 */

public class Game implements Runnable{
	private Screen mainScreen;
	private GameWindow window;
	private ParticleEmiter emiter;
	
	private ParticleFactory basic;
	private ParticleTemplate temp;
	private BasicParticleRenderer renderer;
	
	//Constructor
	public Game(){
		start();
	}
	
	/**
	 * Initializes game content before starting game loop; 
	 */
	public void init(){
		window = new GameWindow(1280, 720,"game",1.1119);
		mainScreen = window.getScreen();
		emiter = new ParticleEmiter();
		basic = new BasicParticleFactory();
		renderer  = new BasicParticleRenderer(2,2, Color.red);
		mainScreen.addScreenComponent(renderer);
		
		temp = basic.createParticleTemplate(500, Timer.DELAY_FORMAT_MILLISECS, 500, renderer, new BasicParticleUpdater(), new BasicParticleInitializer(), new BasicParticleFormer.RectangleFormer(), new ParticleFormation.RectangleFormation(0, BasicParticleFormer.FILLMODE_EDGES, true, 1, 1, true, 60, 60));
		
		Mouse.bind(window.getRenderCanvas());
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
//				System.out.println("[DEBUG] ticks: " + ticks + "fps: " + fps);
				System.out.println(Mouse.getRotation());
				lastTimeDebugOutput += 1000;
				fps = 0;
				ticks = 0;
			}
		}
	}
	
	/**
	 * Updates game content.
	 */
	
	int i = 1;
	
	public void tick(){
		emiter.emit(temp, basic, 30, 20, Timer.DELAY_FORMAT_MILLISECS, 50, 50, 0.25, 0);
		emiter.tick();
	}
	
	/**
	 * Renders game content. 
	 */
	public void render(){
		mainScreen.prepare();
		
		emiter.render();
		
		mainScreen.disposeGraphics();
		window.showRenderedContent();
	}
}
