package svk.sglubos.engine.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import svk.sglubos.engine.core.BasicCore;
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
public class Game extends BasicCore {
	private Screen mainScreen;
	private GameWindow window;
	private SpriteSheet sheet = new SpriteSheet(ImagePort.getImageAsResource("/AnimationTestingNumberSheet.png"), 20, 20);
	private Sprite[] sprites = sheet.getSprites();
	private Animation animation = new SpriteAnimation(sprites, 10, Timer.TIME_FORMAT_TICKS);
	
	//Constructor
	public Game() {
		super(20, FPS_UNLIMITED, true);
		start();
	}
	
	/**
	 * Initializes game content before starting game loop 
	 */
	public void init(){
		window = new GameWindow(100, 100, "game", 2.0, Color.black);
		window.setResizable(true);
		mainScreen = window.getScreen();
		mainScreen.setFont(new Font("Arial", Font.BOLD, 15));
		
		Mouse.bind(window.getRenderCanvas());
		Keyboard.bind(window.getRenderCanvas());
		Timer.init(20);
	}
	
	/**
	 * Updates game content.
	 */
	
	public void tick(){
//		d++;
		Timer.update();
		
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
//		mainScreen.clear();
		
//		mainScreen.setColor(Color.white);
//		mainScreen.renderRectangle(0, 0, 10, 10);
		
		window.showRenderedContent();
	}

	@Override
	protected void stopped() {
		System.exit(0);
	}

}
