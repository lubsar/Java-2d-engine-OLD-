package svk.sglubos.engine.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;

import svk.sglubos.engine.gfx.GameWindow;
import svk.sglubos.engine.gfx.Screen;
import svk.sglubos.engine.gfx.animation.Animation;
import svk.sglubos.engine.gfx.animation.SpriteAnimation;
import svk.sglubos.engine.gfx.sprite.Sprite;
import svk.sglubos.engine.gfx.sprite.SpriteSheet;
import svk.sglubos.engine.input.Keyboard;
import svk.sglubos.engine.input.Mouse;
import svk.sglubos.engine.io.ImagePort;
import svk.sglubos.engine.utils.timer.Timer;

/**
 * Temporary class.
 * Game loop & stuff for testing
 */
public class Game implements Runnable {
	private Screen mainScreen;
	private GameWindow window;
	private SpriteSheet sheet = new SpriteSheet(ImagePort.getImageAsResource("/AnimationTestingNumberSheet.png"), 20, 20);
	private Sprite[] sprites = sheet.getSprites();
	private Animation animation = new SpriteAnimation(sprites, 10, Timer.TIME_FORMAT_TICKS);
	
	//Constructor
	public Game(){
		start();
	}
	
	/**
	 * Initializes game content before starting game loop 
	 */
	public void init(){
		window = new GameWindow(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(), 500, 500, "game", 1.0, Color.black);
		mainScreen = window.getScreen();
		mainScreen.setFont(new Font("null", Font.BOLD, 15));
		
		Mouse.bind(window.getRenderCanvas());
		Keyboard.bind(window.getRenderCanvas());
		Timer.init(20);
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
				Thread.sleep(14);
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
		svk.sglubos.engine.utils.timer.Timer.update();
		
		if(Keyboard.isKeyPressed(KeyEvent.VK_ENTER)) {
			animation.startReverse(true);
		}
		
		if(Keyboard.isKeyPressed(KeyEvent.VK_BACK_SPACE)) {
			animation.stop();
		}
	}
	
	/**
	 * Renders game content. 
	 */
	public void render(){
		mainScreen.prepare();
		
		animation.render(mainScreen, 0, 0);
		
		mainScreen.disposeGraphics();
		window.showRenderedContent();
	}

}
