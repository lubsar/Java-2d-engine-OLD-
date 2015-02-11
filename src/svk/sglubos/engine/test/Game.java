package svk.sglubos.engine.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;

import svk.sglubos.engine.gfx.GameWindow;
import svk.sglubos.engine.gfx.Screen;
import svk.sglubos.engine.gfx.particle.ParticleEffect;
import svk.sglubos.engine.gfx.particle.basic.BasicParticleFactory;
import svk.sglubos.engine.utils.Timer;

/**
 * Temporary class.
 * Game loop & stuff for testing
 */

public class Game implements Runnable{
	private Screen mainScreen;
	private GameWindow window;
	
	private ArrayList<ParticleEffect> effects = new ArrayList<ParticleEffect>();
	
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
	
	int i = 1000;
	
	public void tick(){
		ListIterator<ParticleEffect> iter = effects.listIterator();
		while(iter.hasNext()) {
			ParticleEffect i = iter.next();
			if(i.isAlive()) {
				i.tick();
			} else {
				i = null;
				iter.set(null);
				iter.remove();
			}
		}
		if(i > 0) {
			System.out.println(i);
			effects.add(BasicParticleFactory.test(new Random().nextInt(500) + 5000, Timer.DELAY_FORMAT_MILLISECS, new Random().nextDouble(), new Random().nextDouble(), new Random().nextInt(300) + 300 , new Random().nextInt(1280), new Random().nextInt(720), mainScreen));			
			i--;
		}
	}
	
	/**
	 * Renders game content. 
	 */
	public void render(){
		mainScreen.prepare();
		Iterator<ParticleEffect> iter = effects.iterator();
		while(iter.hasNext()) {
			ParticleEffect i = iter.next();
			if(i.isAlive()) {
				i.render();
			}
		}
		
		mainScreen.disposeGraphics();
		window.showRenderedContent();
	}
}
