package svk.sglubos.engine.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

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
public class Game extends BasicCore implements ImageObserver {
	private Screen mainScreen;
	private GameWindow window;
	private SpriteSheet sheet = new SpriteSheet(ImagePort.getImageAsResource("/AnimationTestingNumberSheet.png"), 20, 20);
	private Sprite[] sprites = sheet.getSprites();
	private Animation animation = new SpriteAnimation(sprites, 10, Timer.TIME_FORMAT_TICKS);
//	private Sprite tile = new SpriteSheet(ImagePort.getImageAsResource("/isometric tile.bmp")).getSprite(0, 0, 45, 37);
	private BufferedImage tile = toCompatibleImage(ImagePort.getImageAsResource("/isometric tile.png"));
	
	//Constructor
	public Game() {
		super(20, BasicCore.FPS_UNLIMITED, true);
		start();
	}
	
	/**
	 * Initializes game content before starting game loop 
	 */
	public void init(){
		window = new GameWindow(1600, 900, "game", Color.white);
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
		if(Keyboard.isKeyPressed(KeyEvent.VK_W)) {
			mainScreen.setOffset(mainScreen.getXOffset(), mainScreen.getYOffset() -10);
		}
		if(Keyboard.isKeyPressed(KeyEvent.VK_S)) {
			mainScreen.setOffset(mainScreen.getXOffset(), mainScreen.getYOffset() +10);
		}
		if(Keyboard.isKeyPressed(KeyEvent.VK_A)) {
			mainScreen.setOffset(mainScreen.getXOffset() - 10, mainScreen.getYOffset());
		}
		if(Keyboard.isKeyPressed(KeyEvent.VK_D)) {
			mainScreen.setOffset(mainScreen.getXOffset() + 10, mainScreen.getYOffset());
		}
		
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
	int x;
	int y;
	public void render(){
		mainScreen.clear();
		
//		mainScreen.setColor(Color.white);
//		mainScreen.renderRectangle(0, 0, 10, 10);
		for(int a = 0; a < 1000; a++) {
			y = a * 24;
			x = a * -24;
			for(int b = 0; b < 100; b++) {
				y += 13;
				x += 22;
				mainScreen.renderImage(tile, x, y);
//				mainScreen.renderSprite(tile, x, y);
//				mainScreen.getGraphics().drawImage(tile, x, y, this);
			}
		}
		
		window.showRenderedContent();
	}

	@Override
	protected void stopped() {
		System.exit(0);
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		System.out.println(width);
		return true;
	}

	private BufferedImage toCompatibleImage(BufferedImage image) {
		GraphicsConfiguration gfx_config = GraphicsEnvironment.
				getLocalGraphicsEnvironment().getDefaultScreenDevice().
				getDefaultConfiguration();

		/*
		 * if image is already compatible and optimized for current system 
		 * settings, simply return it
		 */
		if (image.getColorModel().equals(gfx_config.getColorModel()))
			return image;
		
		// image is not optimized, so create a new image that is
		BufferedImage new_image = gfx_config.createCompatibleImage(
				image.getWidth(), image.getHeight(), BufferedImage.BITMASK);

		// get the graphics context of the new image to draw the old image on
		Graphics2D g2d = (Graphics2D) new_image.getGraphics();

		// actually draw the image and dispose of context no longer needed
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();

		// return the new optimized image
		return new_image; 
	}
	
}
