package svk.sglubos.engine.test;

import java.awt.Color;
import java.awt.GraphicsEnvironment;

import svk.sglubos.engine.IO.ImagePort;
import svk.sglubos.engine.gfx.Animation;
import svk.sglubos.engine.gfx.GameWindow;
import svk.sglubos.engine.gfx.Screen;
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
	
	private Screen mainScreen;
	private SpriteSheet sheet = new SpriteSheet(ImagePort.loadImage("G:\\Dokumenty\\eclipseWS\\GameEngine\\res\\testSheet.png"),32,32);
	private SpriteAnimation anim;
//	private Screen debugScreen;
	
	private GameWindow mainWindow;
//	private GameWindow debugWindow;
	
//	private GameFullScreenWindow debugWindow;
	
	//Constructor
	public Game(){
		start();
	}
	
	/**
	 * Initializes game content before starting game loop; 
	 */
	Sprite[] spr = {sheet.getSprite(0, 0, 32, 32), sheet.getSprite(1, 0, 32, 32), sheet.getSprite(2, 0, 32, 32), sheet.getSprite(1, 1, 32, 32)};
	public void init(){
		int[] pixels = new int[10*10];
		for(int i = 0; i < pixels.length;i++){
			pixels[i] = 0xFF00FF;
		}
		
		
		anim = new SpriteAnimation(sheet, 300, 0, 2, Timer.DELAY_FORMAT_MILLISECS);
		
		test = new Sprite(10,10,pixels);
		
//		mainScreen = new Screen(1920, 1080,Color.black);
//		debugScreen = new Screen(640,300,Color.BLUE);
		
//		mainWindow = new GameRenderingWindow("Game",mainScreen.getRenderLayer(),1000,1000);
//		debugWindow = new GameFullScreenWindow("Debug",debugScreen.getRenderLayer(),640,300);
		
//		mainWindow = new GameFullScreenWindow(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(),mainScreen.getRenderLayer(),500,500);
//		debugWindow = new GameWindow(640,300,"Debug",Color.BLUE);
		mainWindow = new GameWindow(GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0],500, 500,"game",2,Color.BLACK);
		
		mainScreen = mainWindow.getScreen();
		mainScreen.addScreenComponent(r);
//		debugScreen = debugWindow.getScreen();
	}
	
	public void start(){
		new Thread(this,"game").start();
	}
	
	Timer t = new Timer(new TimerTask(){
		
		@Override
		public void timeSwitch() {
			r.changeLight(++r.ambientAlpha);
		}
		
	}, Timer.DELAY_FORMAT_MILLISECS, 60);
	
	//Game loop
	@Override
	public void run() {
		init();
		anim.startReverse(false);
		t.startInfiniteLoop();
		
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
				Thread.sleep(7);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
				
			if((System.currentTimeMillis() - lastTimeDebugOutput) >= 1000){
				render = "[DEBUG] ticks: " + ticks + "fps: " + fps;
//				anim.start(false);
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
	}
	
	/**
	 * Renders game content. 
	 */
	int a = 2;
	public void render(){
		mainScreen.prepare();
//		debugScreen.prepare();
		
		mainScreen.setColor(Color.RED);
		
		mainScreen.renderArc(0, 0, 50, 50, 90, 180);
		mainScreen.renderFilledArc(50, 0, 50, 50, 90, 180);
		
		mainScreen.renderOval(100, 0, 50, 50);
		mainScreen.renderFilledOval(150, 0, 50, 50);
		
		mainScreen.renderRectangle(200, 0, 50, 50);
		mainScreen.renderFilledRectangle(250, 0, 50, 50);
		
		mainScreen.renderLine(300, 0, 349, 0);
		
		mainScreen.renderSprite(spr[0], 0, 0, a);
		mainScreen.renderString("auto", 400, 10);
		
		mainScreen.setColor(Color.CYAN);
		anim.render(mainScreen, 100, 100);
		
		r.shadeItAll();
		
//		debugScreen.setColor(Color.white);
//		debugScreen.renderString(render, 0, 90);
		
		mainScreen.disposeGraphics();
//		debugScreen.disposeGraphics();
		
//		mainWindow.render();
//		debugWindow.render();
		
		mainWindow.showRenderedContent();
//		debugWindow.showContent();
	}
}
