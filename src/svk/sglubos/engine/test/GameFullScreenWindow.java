package svk.sglubos.engine.test;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

//TODO FIX decorated mode
/**
 * Provides ability to display {@link svk.sglubos.engine.gfx.Screen#renderLayer BufferedImage renderlayer},  which contains all rendered graphics by {@link svk.sglubos.engine.gfx.Screen Screen} directly in {@link javax.swing.JFame JFrame} in full screen mode <br>
 * Inherits from {@link javax.swing.JFame JFrame} class. <br>
 * <p>
 * Fills JFrame with {@link java.awt.image.BufferedImage BufferedImage} passed in {@link #GameFullScreenWindow(String, BufferedImage, int, int) constructor}.
 * To display that {@link #renderLayer BufferedImage} you need to call {@link #render()} method every frame after all content is rendered.<br>
 * <p>
 * <h1>Example: render method called every frame</h1>
 * <code> void render() {<br>
 * 	//prepare screen object to render<br>
 * 	screen.prepare();<br><br>
 *	
 *	//render content through Screen object<br>
 *	screen.renderRectangle(0,0,50,50);<br>
 *	...<br><br>

 *	//dispose graphics of Screen object<br>	
 *	screendisposeGraphics();<br><br>
 *
 *	//display rendered content in GameFullScreenWindow<br>
 *	gameRenderingWindow.render();<br> 
 * }<code><br><br>
 * 
 *	@see #render()
 *	@see #GameFullScreenWindow(String, BufferedImage, int, int) constructor
 *	@see svk.sglubos.engine.gfx.Screen
 */
@SuppressWarnings("serial")
public class GameFullScreenWindow extends JFrame {
	/**
	 * {@link java.awt.image.BufferedImage BufferedImage} which contains all graphics rendered by specified {@link svk.sglubos.engine.gfx.Screen Screen} object, from which was this {@link java.awt.image.BufferedImage BufferedImage} obtained.<br>
	 * This object is initialized in {@link #GameFullScreenWindow(String, BufferedImage, int, int) constructor}.
	 * 
	 * @see svk.sglubos.engine.gfx.Screen
	 * @see svk.sglubos.engine.gfx.Screen#getRenderLayer()
	 * @see #GameFullScreenWindow(String, BufferedImage, int, int) constructor
	 */
	protected BufferedImage renderLayer;
	
	/**
	 * {@link java.awt.image.BufferStrategy BufferStrategy} used for double buffering to prevent flickering, this object is used in {@link #render()} method.<br>
	 * This object is initialized in {@link #GameFullScreenWindow(String, BufferedImage, int, int) constructor}.
	 * 
	 * @see java.awt.image.BufferStrategy
	 * @see #GameFullScreenWindow(String, BufferedImage, int, int) constructor
	 * @see #render()
	 */
	protected BufferStrategy bs;
	
	/**
	 * Constructs new {@link svk.sglubos.engine.test.GameFullScreenWindow GameFullScreenWindow} with specified <code>title </code>, <code>width</code>, <code>height</code> and {@link java.awt.image.BufferedImage renderlayer} which will be displayed.<br>
	 * <p>
	 * <h1>Initializes: </h1><br>
	 * {@link javax.swing.JFrame JFrame} with parameter passed in this constructor: <code>title</code><br>
	 * Sets {@link javax.swing.JFrame JFrame} size to parameters passed in this constructor: <code>with</code> and <code>height</code>.<br>
	 * Sets {@link javax.swing.JFrame JFrame} location relative to: <code>null</code>.<br>
	 * Sets {@link javax.swing.JFrame JFrame} <code>DefaultCloseOperation</code> to {@link JFrame#EXIT_ON_CLOSE EXIT_ON_CLOSE}, which shuts down entire application.<br>
	 * Sets {@link javax.swing.JFrame JFrame} to visible state.<br>
	 * Creates {@link javax.swing.JFrame JFrame} {@link java.awt.image.BufferStrategy BufferStrategy} with two buffers. <br>
	 * <p>
	 * {@link #bs BufferStrategy object} with object obtained from {@link javax.swing.JFrame JFrame}, which was created before.<br>
	 * {@link #renderLayer BufferedImage renderLayer  object} with object passed as parameter in this constructor: <code>renderLayer</code>. To display this image call {@link #render()} method.<br> 
	 * 
	 * @param title title of JFrame
	 * @param renderLayer BufferedImage which will be displayed
	 * @param width width of JFrame
	 * @param height height of JFrame<br><br>
	 * 
	 * @see javax.swing.JFrame
	 * @see svk.sglubos.engine.gfx.Screen
	 * @see #render()
	 */
	public GameFullScreenWindow(GraphicsDevice graphicsDevice, BufferedImage renderLayer, int width,int height){
		super();
//		setUndecorated(true);
		getContentPane().setPreferredSize(new Dimension(width,height));
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		graphicsDevice.setFullScreenWindow(this);
		setVisible(true);
		
		createBufferStrategy(2);
		bs = getBufferStrategy(); 
		
		this.renderLayer = renderLayer;
	}
	
	//TODO redo
	/**
	 * Displays content rendered in {@link #renderLayer}. <br>
	 */
	public void render(){
		if(bs == null) {
			return;
		}
		Graphics g = null;
		try{
			g = bs.getDrawGraphics();
		}catch(IllegalStateException e){
			createBufferStrategy(2);
			bs = getBufferStrategy();
			g = bs.getDrawGraphics();
		}
		g.drawImage(renderLayer, 0, 0,getWidth(),getHeight(),null);
		g.dispose();
		bs.show();
	}
}
