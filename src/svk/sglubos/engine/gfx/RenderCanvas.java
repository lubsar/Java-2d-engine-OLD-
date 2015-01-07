package svk.sglubos.engine.gfx;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import svk.sglubos.engine.utils.MessageHandler;

/**
 * Render Canvas inherits from {@link java.awt.Canvas Canvas} class. <br>
 * RenderCanvas provides easy way to display rendered game content
 * from {@link svk.sglubos.engine.gfx.Screen Screen} object passed in {@link #RenderCanvas(Screen) constructor}. 
 *<p>
 * If you want to start displaying game content, you need to create {@link java.awt.image.BufferStrategy BufferStrategy} object. <br>
 * To initialize {@link java.awt.image.BufferStrategy BufferStrategy} object call method {@link #init(int) init(numBuffers)}<br>
 * This method creates <code>BufferStrategy with specified number of buffers</code>. <br>
 * <strong>The canvas must be visible when you create {@link java.awt.image.BufferStrategy BufferStrategy}!</strong>
 * <p>
 * Every frame after all is rendered call method {@link #showRenderedContent()}. <br>
 * This method fills entire canvas with {@link java.awt.image.BufferedImage BufferedImage} renderLayer, <br>
 *  which contains all graphics rendered by {@link svk.sglubos.engine.gfx.Screen screen}.
 * 
 * @see svk.sglubos.engine.gfx.Screen
 * @see {@link svk.sglubos.engine.gfx.Screen#renderLayer renderLayer}
 */
@SuppressWarnings("serial")
public class RenderCanvas extends Canvas {
	/**
	 * {@link java.awt.image.BufferedImage BufferedImage} which contains all graphics rendered by {@link svk.sglubos.engine.gfx.Screen screen}. <br>
	 * This image is used in {@link #showRenderedContent()}  method to display all of that rendered graphics and is initialized in {@link #RenderCanvas(Screen, double) constructor}.<br>
	 * 
	 * @see {@link #showRenderedContent()}  
	 * @see svk.sglubos.engine.gfx.Screen
	 */
	protected BufferedImage renderLayer;
	
	/**
	 * Canvas size is screen size times scale.<br>
	 * This variable is initialized in {@link #RenderCanvas(Screen, double) constructor}.
	 */
	protected double scale = 1.0;
	/** 
	 * {@link java.awt.image.BufferStrategy BufferStrategy} removes flickering of fast rendering by drawing renderLayer to back buffer and displaying it after renderLayer is drawn.<br>
	 * This object is initialized in {@link #init(int) init(int numBuffers)} method.
	 * <p>
	 * @see java.awt.image.BufferStrategy
	 * @see {@link #showRenderedContent()} 
	 */
	protected BufferStrategy bs;
	
	/**
	 * Constructs new {@link svk.sglubos.engine.gfx.RenderCanvas RenderCanvas}.<br>
	 * <h1>initializes</h1>
	 * <p>
	 * {@link java.awt.image.BufferedImage BufferedImage} {@link #renderLayer} is initialized by {@link svk.sglubos.engine.gfx.Screen Screen} <code>getRenderLayer()</code> method. <br>  
	 * Preferred size of canvas set to width: screen.getWidth()*scale and height: screen.getHeight()*scale. <br>
	 * {@link #scale} is initialized to value of passed parameter scale. <br>
	 * <p>
	 * @param screen screen object which is used to initialize sizes and renderLayer
	 * @param scale value which scales up canvas 
	 * 
	 * @see svk.sglubos.engine.gfx.Screen
	 * @see java.awt.image.BufferedImage
	 * @see java.awt.image.BufferStrategy
	 */
	public RenderCanvas(Screen screen,double scale){
		renderLayer = screen.getRenderLayer();
		setPreferredSize(new Dimension((int)(screen.getWidth()*scale), (int)(screen.getHeight()*scale)));
		
		this.scale = scale;
	}
	
	/**
	 * Initializes buffer strategy with specified number of buffers. <br>
	 * This method must be called before started rendering content, but the RenderCanvas must be visible !
	 * <p>
	 * @param numBuffers number of buffers
	 * <p>
	 * @see java.awt.image.BufferStrategy
	 * 
	 */
	public void init(int numBuffers){
		try{
			createBufferStrategy(numBuffers);			
		}catch(Exception e){
			MessageHandler.printMessage("RENDER_CANVAS", MessageHandler.ERROR, "Exception while creating BufferStrategy ! printing stack trace\n");
			e.printStackTrace();
		}
		bs = getBufferStrategy();
	}
	
	/**
	 * Shows content rendered by {@link svk.sglubos.engine.gfx.Screen Screen} passed in {@link #RenderCanvas(Screen, double) constructor}.<br>
	 * {@link java.awt.image.BufferStrategy BufferStrategy BufferStrategy} {@link #bs} have to be initialized ! If not, error message is printed and return is called. <br>
	 * 
	 * Rendered content is displayer by filling entire canvas with {@link #renderLayer}.
	 * Handles showing of {@link java.awt.image.BufferStrategy BufferStrategy};
	 * 
	 * @see svk.sglubos.engine.gfx.Screen
	 * @see java.awt.image.BufferStrategy
	 */
	public void showRenderedContent(){
		if(bs == null){
			MessageHandler.printMessage("RENDER_CANVAS", MessageHandler.ERROR, "BufferStrategy is not initialized !");
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(renderLayer, 0, 0,getWidth(),getHeight(), null);
		g.dispose();
		bs.show();
	}
}
