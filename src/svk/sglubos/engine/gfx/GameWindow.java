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

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;

import javax.swing.JFrame;

import svk.sglubos.engine.utils.debug.DebugStringBuilder;

//TODO Documentation 
/**
 * <code>GameWindow</code> class provides easy way to create {@link javax.swing.JFrame JFrame} which contains {@link svk.sglubos.engine.gfx.RenderCanvas RenderCanvas},
 * which is used to display content rendered trough {@link svk.sglubos.engine.gfx.Screen Screen}.
 * <p>
 * <code>GameWindow</code> inherits from {@link javax.swing.JFrame JFrame} class.
 * <p>
 * This {@link javax.swing.JFrame JFrame} contains {@link svk.sglubos.engine.gfx.RenderCanvas RenderCanvas} 
 * which can display content rendered by {@link #screen this} {@link svk.sglubos.engine.gfx.Screen Screen} {@link #screen object}.  
 * The reference to the {@link #canvas RenderCanvas object} can be obtained by {@link #getRenderCanvas()} method.
 * The reference to the {@link #screen Screen object}, which can draw content which is displayed, can be obtained by {@link #getScreen()} method.
 * <p>
 * The <code>GameWindow</code> contains {@link java.awt.GraphicsDevice GraphicsDevice} 
 * which is used for setting {@link java.awt.DisplayMode DisplayMode} and for setting full screen mode.
 * The {@link java.awt.DisplayMode DisplayMode} can be set by {@link #setDisplayMode(DisplayMode)} method and
 * the full screen mode can be set by {@link #setFullScreenMode(boolean)} method.
 * <p>
 * <code>GameWindow</code> does not handle {@link svk.sglubos.engine.gfx.Screen#prepare() screen prepare method} and {@link svk.sglubos.engine.gfx.Screen#disposeGraphics() screen dispose graphics method}, so you have to do it manually.
 * To update the <code>GameWindow</code> every frame you have to call {@link #showRenderedContent()} method.
 * 
 * <h1>Example: Render method called every frame: </h1>
 * <pre>{@code
 * //prepare screen obtained from GameWindow
 * screenFromGameWindow.prepare();
 * 
 * //render content to screen	
 * screenFromGameWindow.renderRectangle(0,0,50,50);
 * 	
 * //dispose screen Graphics<br>
 * screenFromGameWindow.disposeGraphics();
 *  
 * //display content rendered on screenFromGameWindow in GameWindow
 * myGameWindow.showRenderedContent();
 * }</pre>
 * <p>
 * @see #GameWindow(int, int, String, double, Color) constructor
 * @see #getRenderCanvas()
 * @see #getScreen()
 * @see #setDisplayMode(DisplayMode)
 * @see #setFullScreenMode(boolean)
 * @see svk.sglubos.engine.gfx.RenderCanvas
 * @see svk.sglubos.engine.gfx.Screen
 * @see javax.swing.JFrame
 * @see java.awt.GraphicsDevice
 * @see java.awt.DisplayMode
 */
@SuppressWarnings("serial")
public class GameWindow extends JFrame {
	/**
	 * {@link svk.sglubos.engine.gfx.Screen Screen} object which provides ability to draw game content, 
	 * which is displayed in this {@link javax.swing.JFrame JFrame} through {@link svk.sglubos.engine.gfx.RenderCanvas RenderCanvas} {@link #canvas}.
	 * <p>
	 * This Screen object is initialized in {@link #GameWindow(int, int, String, double, Color) constructor}.
	 * This Screen object can  be obtained by {@link #getScreen()} method.
	 * <p>
	 * @see svk.sglubos.engine.gfx.Screen
	 * @see #getScreen()
	 * @see #GameWindow(int, int, String, double, Color) constructor
	 */
	protected Screen screen;
	
	/**
	 * {@link svk.sglubos.engine.gfx.RenderCanvas RenderCanvas object} which provides ability to display content rendered by {@link #screen} in this {@link javax.swing.JFrame JFrame}.
	 * <p>
	 * The <code>canvas</code> is initialized in {@link #GameWindow(int, int, String, double, Color) constructor}. 
	 * 
	 * <h1>WARNING:</h1>
	 * Canvas is flickering while manually resizing JFrame !
	 * 
	 * @see svk.sglubos.engine.gfx.RenderCanvas
	 * @see #GameWindow(int, int, String, double, Color) constructor
	 * @see #getRenderCanvas()
	 * @see #screen
	 */
	protected RenderCanvas canvas;
	
	protected GraphicsDevice device;
	
	/**
	 * Constructs new {@link GameWindow} object with specified width and height of screen, title, with canvas scale: 1.0 and with default screen color Color.black.
	 * <p>
	 * Uses {@link #GameWindow(int, int, String, double, Color) this(width, height, title, 1.0, Color.black)} constructor.
	 * 
	 * @param screenWidth width of Screen
	 * @param screenHeight height of Screen
	 * @param title title of Game Window
	 * <p>
	 * @see #GameWindow(int, int, String, double, Color)
	 */
	public GameWindow(int screenWidth, int screenHeight, String title) {
		this(screenWidth, screenHeight, title, 1.0, Color.black);
	}
	
	/**
	 * Constructs new {@link GameWindow} object with specified width and height of screen, title and scale and with default screen color Color.black.
	 * <p>
	 * Uses {@link #GameWindow(int, int, String, double, Color) this(width, height, title, canvasScale, Color.black)} constructor.
	 * 
	 * @param screenWidth width of Screen
	 * @param screenHeight height of Screen
	 * @param title title of Game Window
	 * @param canvasScale value of which is canvas scaled of screen
	 * <p>
	 * @see #GameWindow(int, int, String, double, Color)
	 */
	public GameWindow(int screenWidth,int screenHeight, String title, double canvasScale){
		this(screenWidth, screenHeight, title, canvasScale, Color.black);
	}
	
	/**
	 * Constructs new {@link GameWindow} object with specified width and height of screen, title and color and with canvas scale 1.0.
	 * <p>
	 * Uses {@link #GameWindow(int, int, String, double, Color) this(width, height, title, 1.0, defaultScreenColor)} constructor.
	 * 
	 * @param screenWidth width of Screen
	 * @param screenHeight height of Screen
	 * @param title title of Game Window
	 * @param defaultScreenColor color with which is screen colored 
	 * <p>
	 * @see #GameWindow(int, int, String, double, Color)
	 */
	public GameWindow(int screenWidth, int screenHeight, String title, Color defaultScreenColor){
		this(screenWidth, screenHeight, title, 1.0, defaultScreenColor);
	}
	/**
	 * Constructs new {@link GameWindow} object with specified width and height of screen, title, canvas scale and with specified default screen color.
	 *	
	 * <h1>Initializes: </h1>
	 * {@link javax.swing.JFrame#JFrame(String) JFrame constructor} with parameter title which sets window title to title passed by this constructor<br>
	 * Sets {@link javax.swing.JFrame#setDefaultCloseOperation(int) JFrame default close operation} to {@link javax.swing.JFrame#EXIT_ON_CLOSE} which shuts down entire application. <br>
	 * Makes JFrame not resizable.
	 *	
	 * <h2>WARNING:</h2>
	 * Canvas is flickering while manually resizing JFrame !
	 * <p>
	 * {@link #screen Screen object} with parameters: <code>screenWidth</code>,<code> screenHeight </code>and <code>defaultScreenColor</code>, creates screen of specified size and with specified default color.<br>
	 * {@link #canvas RenderCanvas object} with parameters: <code>screen</code>, <code>canvasScale</code>, creates canvas with dimensions of screen times scale.<br>
	 * Adds canvas onto the window. <br>
	 * Packs the JFrame which in this case sets size of window depended on size of canvas.
	 * Sets JFame to visible state. <br>
	 * Sets JFrame location relative to <code>null </code> which centers JFrame.<br>
	 * Calls {@link svk.sglubos.engine.gfx.RenderCanvas#init(int)} with argument <code>2</code> which initializes {@link svk.sglubos.engine.gfx.RenderCanvas#bs render canvas BufferStrategy} with 2 buffers.
	 * <p>
	 * @param screenWidth width of screen
	 * @param screenHeight height of screen
	 * @param title title of JFrame
	 * @param canvasScale scale of canvas which displays screen
	 * @param defaultScreenColor defaultScreenColor of screen
	 * <p>
	 * @see svk.sglubos.engine.gfx.Screen
	 * @see #getScreen()
	 * @see javax.swing.JFrame
	 * @see svk.sglubos.engine.gfx.RenderCanvas
	 */
	public GameWindow(int screenWidth, int screenHeight, String title, double canvasScale, Color defaultScreenColor) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		screen = new Screen(screenWidth, screenHeight, defaultScreenColor);
		canvas = new RenderCanvas(screen, canvasScale);
		
		add(canvas);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		canvas.init(2);
	}
	
	public GameWindow(GraphicsDevice device, int screenWidth, int screenHeight, String title, double scale, Color defaultColor) {
		this(screenWidth, screenHeight, title, scale, defaultColor);
		this.device = device;
	}
	
	/**
	 * @return {@link #screen screen object} which can render content displayed in this window
	 * <p>
	 * @see svk.sglubos.engine.gfx.Screen
	 */
	public Screen getScreen() {
		return screen;
	}
	
	//TODO Documentation
	public RenderCanvas getRenderCanvas() {
		return canvas;
	}
	
	/**
	 * Displays rendered content on Canvas which is added to <code>JFrame</code>
	 * <p>
	 * Uses {@link svk.sglubos.engine.gfx.RenderCanvas#showRenderedContent() canvas.shownRenderedContent()} method.
	 * 
	 * @see #canvas
	 */
	public void showRenderedContent() {
		canvas.showRenderedContent();
	}
	
	public void setFullScreenMode(boolean fullScreen) {
		if(device == null) {
			return;
		}
		
		if(!device.isFullScreenSupported()) {
			simulateFullScreen(fullScreen);
			return;
		}
		
		if(fullScreen){
			setWindowDecoration(true);
			device.setFullScreenWindow(this);
		} else {
			setWindowDecoration(false);
			device.setFullScreenWindow(null);
		}
	}
	
	public void setDisplayMode(DisplayMode mode) {
		DisplayMode[] modes = device.getDisplayModes();
		for(DisplayMode md : modes) {
			if(md.equals(mode)) {
				device.setDisplayMode(mode);
				break;
			}
		}
	}
	
	protected void setWindowDecoration(boolean decorated) {
		dispose();
		setUndecorated(decorated);
		pack();
		setVisible(true);
		canvas.init(2);
	}
	
	protected void simulateFullScreen(boolean fullScreen) {
		if(getExtendedState() == JFrame.MAXIMIZED_BOTH && !fullScreen) {
			setWindowDecoration(true);
			setExtendedState(JFrame.NORMAL);
		} else if (fullScreen){
			setWindowDecoration(false);
			setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
	}
	
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		
		ret.appendClassDataBracket(this.getClass(), hashCode());
		ret.appendTabln(super.toString());
		ret.appendObjectToStringTabln("screen = ", screen);
		ret.appendObjectToStringTabln("canvas = ", canvas);
		ret.appendObjectToStringTabln("device = ", device);
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}
