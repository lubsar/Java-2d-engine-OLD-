package svk.sglubos.engine.test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import svk.sglubos.engine.IO.ImagePort;
import svk.sglubos.engine.gfx.GameRenderingWindow;
import svk.sglubos.engine.gfx.Screen;
import svk.sglubos.engine.gfx.particle.ParticleEffect;
import svk.sglubos.engine.gfx.particle.basic.BasicRectangleParticleEffect;
import svk.sglubos.engine.gfx.sprite.Sprite;
import svk.sglubos.engine.gfx.sprite.SpriteAnimation;
import svk.sglubos.engine.gfx.sprite.SpriteSheet;
import svk.sglubos.engine.utils.Timer;
import svk.sglubos.engine.utils.TimerTask;

/**
 * Temporary class.
 * Game loop & stuff for testing
 */

public class Game implements Runnable{
	
	String render = "null";
	
	private Sprite test;
	
	private ScreenComponentTest r = new ScreenComponentTest();
	
	private Screen mainScreen = new Screen(500,500, Color.black);
	private SpriteSheet sheet = new SpriteSheet(ImagePort.loadImage("G:\\Dokumenty\\eclipseWS\\GameEngine\\res\\Animation I.png"),32,32);
	private SpriteAnimation anim;
//	private Screen debugScreen;
	
	private GameRenderingWindow pan;
	
	private ArrayList<ParticleEffect> effects = new ArrayList<ParticleEffect>();
	
	private ParticleEffect e = new BasicRectangleParticleEffect(500, Timer.DELAY_FORMAT_MILLISECS,Color.WHITE,10,10,100);
	
//	private GameWindow mainWindow;
//	private GameWindow debugWindow;
	
	//Constructor
	public Game(){
		start();
	}
	
	/**
	 * Initializes game content before starting game loop; 
	 */
//	Sprite[] spr = {sheet.getSprite(0, 0, 32, 32), sheet.getSprite(1, 0, 32, 32), sheet.getSprite(2, 0, 32, 32), sheet.getSprite(1, 1, 32, 32)};
	public void init(){
		int[] pixels = new int[10*10];
		for(int i = 0; i < pixels.length;i++){
			pixels[i] = 0xFF00FF;
		}
		
		effects.add(e);
		
		anim = new SpriteAnimation(sheet, 60, 1, 6, Timer.DELAY_FORMAT_MILLISECS);
		
		test = new Sprite(10,10,pixels);
		
//		mainScreen = new Screen(1920, 1080,Color.black);
//		debugScreen = new Screen(640,300,Color.BLUE);
		
//		mainWindow = new GameRenderingWindow("Game",mainScreen.getRenderLayer(),1000,1000);
//		debugWindow = new GameFullScreenWindow("Debug",debugScreen.getRenderLayer(),640,300);
		
//		mainWindow = new GameFullScreenWindow(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(),mainScreen.getRenderLayer(),500,500);
//		debugWindow = new GameWindow(640,300,"Debug",Color.BLUE);
//		mainWindow = new GameWindow(GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0],500, 500,"game",2,Color.BLACK);
		
//		mainScreen = mainWindow.getScreen();
		mainScreen.addScreenComponent(r);
		
		pan = new GameRenderingWindow(mainScreen.getRenderLayer(),1.1119);
//		debugScreen = debugWindow.getScreen();
	}
	
	public void start(){
		new Thread(this,"game").start();
	}
	
	Timer particleSpawner = new Timer(new TimerTask(){
		public void timeSwitch() {
			effects.add(new BasicRectangleParticleEffect(1000, Timer.DELAY_FORMAT_MILLISECS,Color.WHITE,10,10,100));
		}
	}, Timer.DELAY_FORMAT_MILLISECS, 100);
	
	
	Timer t = new Timer(new TimerTask(){
		
		@Override
		public void timeSwitch() {
			clearEffects();
		}
		
	}, Timer.DELAY_FORMAT_SECS, 10);
	
	//Game loop
	@Override
	public void run() {
		init();
		t.startInfiniteLoop();
		particleSpawner.startInfiniteLoop();
		anim.start(true);
		
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
				render = "[DEBUG] ticks: " + ticks + "fps: " + fps;
				System.out.println(render);
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
		anim.tick();
		t.update();
		particleSpawner.update();
		
		Iterator<ParticleEffect> i = effects.iterator();
		while(i.hasNext()) {
			ParticleEffect e = i.next();
			if(e.isAlive()) {
				e.tick();
			}
		}
	}
	
	private void clearEffects() {
		Iterator<ParticleEffect> i = effects.iterator();
		while(i.hasNext()) {
			ParticleEffect e = i.next();
			if(!e.isAlive()) {
				i.remove();
			}
		}
	}
	
	/**
	 * Renders game content. 
	 */
	public void render(){
		mainScreen.prepare();
//		debugScreen.prepare();
		
		mainScreen.setColor(Color.RED);
		
//		mainScreen.renderArc(0, 0, 50, 50, 90, 180);
//		mainScreen.renderFilledArc(50, 0, 50, 50, 90, 180);
		
		mainScreen.renderOval(100, 0, 50, 50);
		mainScreen.renderFilledOval(150, 0, 50, 50);
		
		mainScreen.renderRectangle(200, 0, 50, 50);
		mainScreen.renderFilledRectangle(250, 0, 50, 50);
		
		mainScreen.renderLine(300, 0, 349, 0);
		
//		mainScreen.renderSprite(spr[0], 0, 0, a);
		mainScreen.renderString("auto", 400, 10);
		
		mainScreen.setColor(Color.CYAN);
		
		Iterator<ParticleEffect> i = effects.iterator();
		while(i.hasNext()) {
			ParticleEffect e = i.next();
			if(e.isAlive()) {
				e.render(mainScreen);
			}
		}
		
//		r.shadeItAll();
		
//		debugScreen.setColor(Color.white);
//		debugScreen.renderString(render, 0, 90);
		
		mainScreen.disposeGraphics();
//		debugScreen.disposeGraphics();
		
//		mainWindow.render();
//		debugWindow.render();
		
		pan.render();
//		mainWindow.showRenderedContent();
//		debugWindow.showContent();
	}
}
