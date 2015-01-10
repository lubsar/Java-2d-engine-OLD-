package svk.sglubos.engine.test;

import java.awt.Color;

import svk.sglubos.engine.IO.ImagePort;
import svk.sglubos.engine.gfx.Animation;
import svk.sglubos.engine.gfx.GameWindow;
import svk.sglubos.engine.gfx.Screen;
import svk.sglubos.engine.gfx.sprite.Sprite;
import svk.sglubos.engine.gfx.sprite.SpriteAnimation;
import svk.sglubos.engine.gfx.sprite.SpriteSheet;

/**
 * Temporary class.
 * Game loop & stuff for testing
 */

public class Game implements Runnable{
	
	String render = "null";
	
	private Sprite test;
	
	private ScreenComponentTest r = new ScreenComponentTest();
	
	private Screen mainScreen;
	private SpriteSheet sheet = new SpriteSheet(ImagePort.loadImage("G:\\Dokumenty\\eclipseWS\\GameEngine\\res\\testSheet.png"));
	private SpriteAnimation anim;
//	private Screen debugScreen;
	
	private GameWindow mainWindow;
//	private GameWindow debugWindow;
	
//	private GameRenderingWindow mainWindow;
//	private GameRenderingWindow debugWindow;
	
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
		
		
		
		Sprite[] spr = {sheet.getSprite(0, 0, 32, 32), sheet.getSprite(1, 0, 32, 32), sheet.getSprite(2, 0, 32, 32), sheet.getSprite(1, 1, 32, 32)};
		
		anim = new SpriteAnimation(10,Animation.DELAY_FORMAT_TICKS,spr);
		
		test = new Sprite(50,50,pixels);
		
//		mainScreen = new Screen(500,500,Color.black);
//		debugScreen = new Screen(640,300,Color.BLUE);
		
//		mainWindow = new GameRenderingWindow("Game",mainScreen.getRenderLayer(),1000,1000);
//		debugWindow = new GameRenderingWindow("Debug",debugScreen.getRenderLayer(),640,300);
		
		mainWindow = new GameWindow(500,500,"Game",2.0);
//		debugWindow = new GameWindow(640,300,"Debug",Color.BLUE);
		
		mainScreen = mainWindow.getScreen();
		mainScreen.addScreenExpansion(r);
//		debugScreen = debugWindow.getScreen();
	}
	
	public void start(){
		new Thread(this,"game").start();
	}
	
	//Game loop
	@Override
	public void run() {
		init();
		anim.start(false);
		
		long lastTime = System.nanoTime();
		long lastTimeDebugOutput = System.currentTimeMillis();
		double delta = 0;
		double nanoSecPerTick = Math.pow(10, 9)/500;
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
			
			try {
				Thread.sleep(7);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			fps++;
			render();
				
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
	}
	
	/**
	 * Renders game content. 
	 */
	public void render(){
		mainScreen.prepare();
//		debugScreen.prepare();
		
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
		
		mainScreen.setColor(Color.CYAN);
		
		r.shadeItAll();
		
		anim.render(mainScreen, 100, 100);
		
//		debugScreen.setColor(Color.white);
//		debugScreen.renderString(render, 0, 15);
		
		mainScreen.disposeGraphics();
//		debugScreen.disposeGraphics();
		
//		mainWindow.render();
//		debugWindow.render();
		
		mainWindow.showRenderedContent();
//		debugWindow.showContent();
	}
}
