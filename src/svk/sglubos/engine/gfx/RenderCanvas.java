/*
 *	Copyright 2015 ¼ubomír Hlavko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package svk.sglubos.engine.gfx;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import svk.sglubos.engine.utils.debug.DebugStringBuilder;
import svk.sglubos.engine.utils.debug.MessageHandler;

/**
 * RenderCanvas provides easy way to display rendered game content
 * from {@link svk.sglubos.engine.gfx.Screen Screen} object passed in {@link #RenderCanvas(Screen, double) constructor}.
 * <p>
 * Render Canvas inherits from {@link java.awt.Canvas Canvas} class.
 * <p>
 * If you want to start displaying game content, you need to create {@link java.awt.image.BufferStrategy BufferStrategy} object. <br>
 * To initialize {@link java.awt.image.BufferStrategy BufferStrategy} object call method {@link #init(int) init(numBuffers)}<br>
 * This method creates <code>BufferStrategy with specified number of buffers</code>.<br>
 * <strong>The canvas must be visible when you create {@link java.awt.image.BufferStrategy BufferStrategy}!</strong>
 * <p>
 * Every frame after all is rendered call method {@link #showRenderedContent()}. <br>
 * This method fills entire canvas with {@link java.awt.image.BufferedImage BufferedImage} renderLayer, <br>
 *  which contains all graphics rendered by {@link svk.sglubos.engine.gfx.Screen screen}.
 * 
 * @see svk.sglubos.engine.gfx.Screen
 * @see svk.sglubos.engine.gfx.Screen#renderLayer renderLayer
 */
@SuppressWarnings("serial")
public class RenderCanvas extends Canvas {
	/**
	 * {@link java.awt.image.BufferedImage BufferedImage} which contains all graphics rendered by {@link svk.sglubos.engine.gfx.Screen screen}.
	 * <p>
	 * This image is used in {@link #showRenderedContent()}  method to display all of that rendered graphics and is initialized in {@link #RenderCanvas(Screen, double) constructor}.<br>
	 * 
	 * @see #showRenderedContent()  
	 * @see svk.sglubos.engine.gfx.Screen
	 */
	protected BufferedImage renderLayer;
	
	/**
	 * Canvas size is screen size times scale.
	 * <p>
	 * This variable is initialized in {@link #RenderCanvas(Screen, double) constructor}.
	 * 
	 * @see #RenderCanvas(Screen, double)
	 */
	protected double scale = 1.0;
	
	/** 
	 * {@link java.awt.image.BufferStrategy BufferStrategy} removes flickering of fast rendering by drawing renderLayer to back buffer and displaying it after renderLayer is drawn.
	 * <p>
	 * This object is initialized in {@link #init(int) init(int numBuffers)} method.
	 * 
	 * @see java.awt.image.BufferStrategy
	 * @see  #showRenderedContent()
	 */
	protected BufferStrategy bs;
	
	/**
	 * Constructs new {@link svk.sglubos.engine.gfx.RenderCanvas RenderCanvas}.
	 * 
	 * <h1>Initializes: </h1>
	 * {@link java.awt.image.BufferedImage BufferedImage} {@link #renderLayer} is initialized by {@link svk.sglubos.engine.gfx.Screen Screen} <code>getRenderLayer()</code> method. <br>  
	 * Preferred size of canvas set to width: screen.getWidth()*scale and height: screen.getHeight()*scale.
	 * {@link #scale} is initialized to value of passed parameter scale. <br>
	 * <p>
	 * @param screen screen object which is used to initialize sizes and renderLayer
	 * @param scale value which scales up canvas 
	 * <p>
	 * @see svk.sglubos.engine.gfx.Screen
	 * @see java.awt.image.BufferedImage
	 * @see java.awt.image.BufferStrategy
	 */
	public RenderCanvas(Screen screen,double scale){
		renderLayer = screen.getRenderLayer();
		setPreferredSize(new Dimension((int)(screen.getWidth() + 10 * scale), (int)(screen.getHeight() + 10 * scale)));
		
		this.scale = scale;
	}
	
	/**
	 * Initializes {@link #bs BufferStrategy object} with specified number of buffers.
	 * <p>
	 * <strong>This method must be called before started rendering content, but the RenderCanvas must be visible !</strong>
	 * <p>
	 * @param numBuffers number of buffers
	 * <p>
	 * @see java.awt.image.BufferStrategy
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
	
	//TODO Exception
	/**
	 * Shows content rendered by {@link svk.sglubos.engine.gfx.Screen Screen} object passed in {@link #RenderCanvas(Screen, double) constructor}.
	 * <p>
	 * <strong>{@link java.awt.image.BufferStrategy BufferStrategy BufferStrategy} {@link #bs} have to be initialized ! If not, error message is printed and return is called.</strong>
	 * <p>
	 * Rendered content is displayed by filling entire canvas with {@link #renderLayer}.
	 * Handles showing of {@link java.awt.image.BufferStrategy BufferStrategy}.
	 * 
	 * @see svk.sglubos.engine.gfx.Screen
	 * @see java.awt.image.BufferStrategy
	 */
	public void showRenderedContent(){
		if(bs == null){
			MessageHandler.printMessage("RENDER_CANVAS", MessageHandler.ERROR, "BufferStrategy is not initialized !");
			return;
		}
		
		Graphics g = null;
		
		do {
		    try{
		    	g = bs.getDrawGraphics();
		    	g.drawImage(renderLayer, 0, 0, getWidth(),getHeight(), null);
		    } finally {
		    	if(g != null)
		    		g.dispose();
		    }
		    
		    bs.show();
		} while (bs.contentsLost());
	}
	
	//TODO document
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		
		ret.append(getClass(), hashCode());
		ret.addLayer();
		ret.appendln(super.toString());
		ret.append("scale", scale);
		ret.append(renderLayer, "renderlayer");
		ret.append(bs, "bs");
		ret.removeLayer();
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}
