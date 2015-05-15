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
import java.awt.GraphicsEnvironment;

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
 * To update the <code>GameWindow</code> have to call {@link #showRenderedContent()} method.
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
 * @see #GameWindow(GraphicsDevice, int, int, String, double, Color) constructor
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
	 * {@link svk.sglubos.engine.gfx.Screen Screen} object which provides ability to render game content.
	 * <p> 
	 * The content rendered by this {@link svk.sglubos.engine.gfx.Screen Screen} object is displayed {@link svk.sglubos.engine.gfx.RenderCanvas RenderCanvas} {@link #canvas}.
	 * The {@link #canvas} is displayed in the <code>GameWindow</code>.
	 * <p>
	 * The <code>screen</code> is used to initialize {@link svk.sglubos.engine.gfx.RenderCanvas RenderCanvas} {@link #canvas} in {@link #GameWindow(GraphicsDevice, int, int, String, double, Color) constructor}.
	 * Reference to the <code>screen</code> can be obtained by {@link #getScreen()} method.
	 * <p>
	 * The <code>screen</code> is initialized in {@link #GameWindow(GraphicsDevice, int, int, String, double, Color) constructor}.
	 * The {@link svk.sglubos.engine.gfx.Screen Screen} object is initialized with {@link svk.sglubos.engine.gfx.Screen#Screen(int, int, Color) constructor}
	 * with arguments: <code>width</code>(integer), <code>height</code>(integer), and <code>color</code>({@link #java.awt.Color Color object}), which is used to clear the screen. 
	 * <p>
	 * @see #GameWindow(GraphicsDevice, int, int, String, double, Color) constructor
	 * @see #getScreen()
	 * @see #canvas
	 * @see svk.sglubos.engine.gfx.Screen
	 * @see svk.sglubos.engine.gfx.RenderCanvas
	 * @see svk.sglubos.engine.gfx.Screen#Screen(int, int, Color) constructor of {@link svk.sglubos.engine.gfx.Screen Screen class} 
	 * @see java.awt.Color
	 */
	protected Screen screen;
	
	/**
	 * {@link svk.sglubos.engine.gfx.RenderCanvas RenderCanvas} object which provides ability to display content rendered by {@link #screen} in this {@link javax.swing.JFrame JFrame}.
	 * <p>
	 * The content rendered by {@link #screen} can be displayed by {@link #showRenderedContent()} which calls the {@link svk.sglubos.engine.gfx.RenderCanvas#showRenderedContent() RenderCanvas.showRenderedContent()} method,
	 * which repaints the content rendered by {@link #screen}. That method must be called every frame after the content is rendered. 
	 * The reference to <code>canvas</code> can be obtained by {@link #getRenderCanvas()} method.
	 * <p>
	 * The <code>canvas</code> is initialized in {@link #GameWindow(GraphicsDevice, int, int, String, double, Color) constructor} with {@link svk.sglubos.engine.gfx.RenderCanvas#RenderCanvas(Screen, double)constructor} of {@link svk.sglubos.engine.gfx.RenderCanvas RenderCanvas} class
	 * with arguments: <code>screen</code>({@link svk.sglubos.engine.gfx.Screen screen} {@link #screen} object), which renders content which is displayed in this canvas and <code>scale</code>(double),
	 * which represents how many times the content rendered by {@link #screen} should be scaled inside of this {@link svk.sglubos.engine.gfx.RenderCanvas RenderCanvas}. 
	 * 
	 * <h1>WARNING:</h1>
	 * Canvas is flickering while manually resizing JFrame !
	 * 
	 * @see #GameWindow(GraphicsDevice, int, int, String, double, Color) constructor
	 * @see #showRenderedContent()
	 * @see #getRenderCanvas()
	 * @see #screen
	 * @see svk.sglubos.engine.gfx.RenderCanvas
	 * @see svk.sglubos.engine.gfx.RenderCanvas#RenderCanvas(Screen, double) constructor of {@link svk.sglubos.engine.gfx.RenderCanvas RenderCavnas class}
	 */
	protected RenderCanvas canvas;
	
	/**
	 * {@link java.awt.GraphicsDevice GraphicsDevice} which is used to set full screen mode and {@link java.awt.DisplayMode display mode}.
	 * <p>
	 * The <code>device</code> is used for setting display mode in {@link #setDisplayMode(DisplayMode)} method which has one {@link java.awt.DisplayMode DisplayMode} parameter,
	 * which represent the display mode which will be set.
	 * The display mode is set by {@link java.awt.GraphicsDevice#setDisplayMode(DisplayMode) GraphicsDevice.setDisplayMode(DisplayMode)} method.
	 * The full screen mode can be set by {@link #setFullScreenMode(boolean)} method which has one boolean parameter,
	 * which determines if the <code>GameWindow</code> will be set to full screen mode or to window mode.
	 * The full screen mode is set by {@link java.awt.GraphicsDevice#setFullScreenWindow(java.awt.Window) GraphicsDevice.setFullScreenWindow(Window)} method.
	 * <p>
	 * The <code>device</code> is initialized in {@link #GameWindow(GraphicsDevice, int, int, String, double, Color) constructor}
	 * 
	 * @see #GameWindow(GraphicsDevice, int, int, String, double, Color) constructor
	 * @see #setDisplayMode(DisplayMode)
	 * @see #setFullScreenMode(boolean)
	 * @see java.awt.GraphicsDevice
	 * @see java.awt.DisplayMode
	 * @see java.awt.GraphicsDevice#setDisplayMode(DisplayMode) GraphicsDevice.setDisplayMode(DisplayMode)
	 * @see java.awt.GraphicsEnvironment
	 * @see java.awt.GraphicsDevice#setFullScreenWindow(java.awt.Window) GraphicsDevice.setFullScreenWindow(Window)
	 */
	protected GraphicsDevice device;
	
	public GameWindow(int screenWidth, int screenHeight, String title) {
		this(screenWidth, screenHeight, title, 1.0, Color.black, GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
	}
	
	public GameWindow(int screenWidth,int screenHeight, String title, double canvasScale){
		this(screenWidth, screenHeight, title, 1.0, Color.black,GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
	}
	
	public GameWindow(int screenWidth, int screenHeight, String title, Color defaultScreenColor){
		this(screenWidth, screenHeight, title, 1.0, defaultScreenColor, GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
	}
	
	public GameWindow(int screenWidth, int screenHeight, String title, double canvasScale, Color defaultScreenColor) {
		this(screenWidth, screenHeight, title, canvasScale, defaultScreenColor, GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
	}
	
	/**
	 * Constructs new <code>GameWindow</code> object with specific parameters: width and height of screen, title of JFrame, scale of screen in RenderCanvas, default color of screen and GraphicsDevice.
	 * 
	 * @param device
	 * @param screenWidth
	 * @param screenHeight
	 * @param title
	 * @param canvasScale
	 * @param defaultScreenColor
	 */
	
	public GameWindow(int screenWidth, int screenHeight, String title, double canvasScale, Color defaultScreenColor, GraphicsDevice device) {
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
		if(device == null || !device.isFullScreenSupported()) {
			simulateFullScreen(fullScreen);
		} else {
			if(fullScreen){
				setWindowDecoration(true);
				device.setFullScreenWindow(this);
			} else {
				setWindowDecoration(false);
				device.setFullScreenWindow(null);
			}
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
