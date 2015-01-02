package svk.sglubos.engine.gfx;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * Render Canvas inherits from {@link java.awt.Canvas Canvas} class. <br>
 * RenderCanvas provides easy way to display rendered game content
 * from {@link svk.sglubos.engine.gfx.Screen Screen} object passed in {@link #RenderCanvas(Screen) constructor}. 
 *<p>
 * If you want to start displaying game content, you need to create {@link java.awt.image.BufferStrategy BufferStrategy} object. <br>
 * To initialize {@link java.awt.image.BufferStrategy BufferStrategy} object call method {@link #init(int) init(numBuffers)}. <br>
 * The canvas must be visible when you create {@link java.awt.image.BufferStrategy BufferStrategy}!
 * <p>
 * Every frame after all is rendered call method {@link #showRenderedContent()}. <br>
 * This method fills entire canvas with {@link java.awt.image.BufferedImage BufferedImage} renderLayer.
 * 
 * @see svk.sglubos.engine.gfx.Screen
 * @see {@link svk.sglubos.engine.gfx.Screen#renderLayer renderLayer}
 */


@SuppressWarnings("serial")
public class RenderCanvas extends Canvas {
	private BufferedImage renderContent;
	private double scale = 1.0;
	
	private BufferStrategy bs;
	private Graphics g;
	
	public RenderCanvas(Screen screen,double scale){
		renderContent = screen.getRenderLayer();
		setSize((int)(screen.getWidth()*scale), (int)(screen.getHeight()*scale));
		
		this.scale = scale;
	}
	
	public void init(int buffers){
		createBufferStrategy(buffers);
		bs = getBufferStrategy();
	}
	
	public void showRenderedContent(){
		g = bs.getDrawGraphics();
		
		g.clearRect(0, 0, getWidth(), getHeight());
		g.drawImage(renderContent, 0, 0,getWidth(), getHeight(), null);
		
		g.dispose();
		bs.show();
	}
}
