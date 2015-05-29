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
 * <code>GameWindow</code> inherits from <code>JFrame</code> class.
 * <p>
 * This <code>JFrame</code> contains {@link svk.sglubos.engine.gfx.RenderCanvas RenderCanvas} 
 * which can display content rendered by {@link #screen this} {@link svk.sglubos.engine.gfx.Screen Screen} object {@link #screen}.  
 * The reference to the {@link #canvas RenderCanvas object} can be obtained by {@link #getRenderCanvas()} method.
 * The reference to the <code>screen</code>, which can draw content which is displayed, can be obtained by {@link #getScreen()} method.
 * <p>
 * The <code>GameWindow</code> contains {@link java.awt.GraphicsDevice GraphicsDevice} 
 * which is used for setting {@link java.awt.DisplayMode DisplayMode} and for setting full screen mode.
 * The <code>DisplayMode</code> can be set by {@link #setDisplayMode(DisplayMode)} method and
 * the full screen mode can be set by {@link #setFullScreenMode(boolean)} method.
 * <p>
 * The <code>GameWindow</code> is updated by {@link #showRenderedContent()} method which displays the content rendered by <code>screen</code>.
 * 
 * <h1>Example: Render method called every frame: </h1>
 * <pre>{@code
 * //clears the screen with default color (optional)
 * screen.clear();
 * 
 * //render content to screen
 * screenFromGameWindow.renderRectangle(0,0,50,50);
 * 	
 * //display content rendered on screenFromGameWindow in GameWindow
 * myGameWindow.showRenderedContent();
 * }</pre>
 * <p>
 * @see #GameWindow(int, int, String, double, Color,GraphicsDevice) constructor
 * @see #getRenderCanvas()
 * @see #getScreen()
 * @see #setDisplayMode(DisplayMode)
 * @see #setFullScreenMode(boolean)
 * @see <br>
 * @see svk.sglubos.engine.gfx.RenderCanvas
 * @see svk.sglubos.engine.gfx.Screen
 * @see javax.swing.JFrame
 * @see <br>
 * @see java.awt.GraphicsDevice
 * @see java.awt.DisplayMode
 */
@SuppressWarnings("serial")
public class GameWindow extends JFrame {
	/**
	 * {@link svk.sglubos.engine.gfx.Screen Screen} object which provides ability to render game content.
	 * <p> 
	 * The content rendered by this <code>Screen</code> object is displayed {@link svk.sglubos.engine.gfx.RenderCanvas RenderCanvas} object {@link #canvas}.
	 * The <code>canvas</code> is displayed in this <code>GameWindow</code>.
	 * <p>
	 * The <code>screen</code> is used in initialization of <code>canvas</code> in {@link #GameWindow(int, int, String, double, Color,GraphicsDevice)  constructor}.
	 * Reference to the <code>screen</code> can be obtained by {@link #getScreen()} method.
	 * <p>
	 * The <code>screen</code> is initialized in {@link #GameWindow(int, int, String, double, Color,GraphicsDevice)  constructor}.
	 * This <code>Screen</code> object is initialized with {@link svk.sglubos.engine.gfx.Screen#Screen(int, int, Color) constructor}
	 * with arguments: <code>width</code>(integer), <code>height</code>(integer), and <code>color</code>({@link #java.awt.Color Color object}), which is used to clear the screen. 
	 * <p>
	 * @see #GameWindow(int, int, String, double, Color,GraphicsDevice) 	constructor
	 * @see #getScreen()
	 * @see #canvas
	 * @see <br>
	 * @see svk.sglubos.engine.gfx.Screen
	 * @see svk.sglubos.engine.gfx.RenderCanvas
	 * @see svk.sglubos.engine.gfx.Screen#Screen(int, int, Color)			constructor of {@link svk.sglubos.engine.gfx.Screen Screen class}
	 * @see <br> 
	 * @see java.awt.Color
	 */
	protected Screen screen;
	
	/**
	 * {@link svk.sglubos.engine.gfx.RenderCanvas RenderCanvas} object which provides ability to display content rendered by {@link svk.sglubos.engine.gfx.Screen Screen} {@link #screen} in this {@link javax.swing.JFrame JFrame}.
	 * <p>
	 * The content rendered by <code>screen</code> can be displayed by {@link #showRenderedContent()} method which calls the {@link svk.sglubos.engine.gfx.RenderCanvas#showRenderedContent() RenderCanvas.showRenderedContent()} method,
	 * which paints the content rendered by <code>screen</code>}. That method must be called every frame after the content is rendered. 
	 * The reference to <code>canvas</code> can be obtained by {@link #getRenderCanvas()} method.
	 * <p>
	 * The <code>canvas</code> is initialized in {@link #GameWindow(int, int, String, double, Color,GraphicsDevice) constructor} with {@link svk.sglubos.engine.gfx.RenderCanvas#RenderCanvas(Screen, double)constructor} of {@link svk.sglubos.engine.gfx.RenderCanvas RenderCanvas} class
	 * with arguments: <code>screen</code>, which renders content which is displayed in this canvas and <code>scale</code>(double),
	 * which represents how many times the content rendered by <code>screen</code> should be scaled inside of this {@link svk.sglubos.engine.gfx.RenderCanvas RenderCanvas}. 
	 * 
	 * <h1>WARNING:</h1>
	 * Canvas is flickering while manually resizing JFrame !
	 * 
	 * @see #GameWindow(int, int, String, double, Color,GraphicsDevice) 		constructor
	 * @see #showRenderedContent()
	 * @see #getRenderCanvas()
	 * @see #screen
	 * @see <br>
	 * @see svk.sglubos.engine.gfx.RenderCanvas
	 * @see svk.sglubos.engine.gfx.RenderCanvas#RenderCanvas(Screen, double) 	constructor of {@link svk.sglubos.engine.gfx.RenderCanvas RenderCavnas class}
	 */
	protected RenderCanvas canvas;
	
	/**
	 * {@link java.awt.GraphicsDevice GraphicsDevice} which is used to set full screen mode and {@link java.awt.DisplayMode display mode}.
	 * <p>
	 * The <code>device</code> is used for setting display mode in {@link #setDisplayMode(DisplayMode)} method which has one <code>DisplayMode</code> parameter,
	 * which represent the display mode which will be set, if that <code>DisplayMode</code> is supported by the <code>GraphicsDevice</code> device.
	 * The display mode is set by {@link java.awt.GraphicsDevice#setDisplayMode(DisplayMode) GraphicsDevice.setDisplayMode(DisplayMode)} method.
	 * The full screen mode can be set by {@link #setFullScreenMode(boolean)} method which has one boolean parameter,
	 * which determines if the <code>GameWindow</code> will be set to full screen mode or to window mode.
	 * The full screen mode is set by {@link java.awt.GraphicsDevice#setFullScreenWindow(java.awt.Window) GraphicsDevice.setFullScreenWindow(Window)} method.
	 * <p>
	 * The <code>device</code> is initialized in {@link #GameWindow(int, int, String, double, Color,GraphicsDevice) constructor}
	 * 
	 * @see #GameWindow(int, int, String, double, Color,GraphicsDevice) constructor
	 * @see #setDisplayMode(DisplayMode)
	 * @see #setFullScreenMode(boolean)
	 * @see <br>
	 * @see java.awt.GraphicsDevice
	 * @see java.awt.DisplayMode
	 * @see java.awt.GraphicsDevice#setDisplayMode(DisplayMode) 			GraphicsDevice.setDisplayMode(DisplayMode)
	 * @see java.awt.GraphicsEnvironment
	 * @see java.awt.GraphicsDevice#setFullScreenWindow(java.awt.Window) 	GraphicsDevice.setFullScreenWindow(Window)
	 */
	protected GraphicsDevice device;
	
	/**
	 * Constructs new <code>GameWindow</code> object with specific parameters: width and height of <code>screen</code> and title of <code>JFrame</code>.
	 * 
	 * <h1>Construction:</h1>
	 * Uses {@link #GameWindow(int, int, String, double, Color, GraphicsDevice)} constructor.
	 * The <code>constructor</code> is called with arguments: <code>screenWidth</code>, <code>screenHeight</code> 
	 * and <code>title</code> parameters of this constructor and with default values <code>screenScale: 1.0</code>, <code>defaultScreenColor: Color.black</code> 
	 * and with default <code>graphicsDevice</code> obtained from {@link java.awt.GraphicsEnvironment GraphicsEnvironmet} by {@link java.awt.GraphicsEnvironment#getDefaultScreenDevice() GraphicsEnvrinonment.getDefaultScreenDevice()} method.
	 * <p> 
	 * @param screenWidth	width of the {@link #screen}
	 * @param screenHeight	height of the <code>screen</code>
	 * @param title			title of the <code>JFrame</code>
	 * @see <br>
	 * @see #GameWindow(int, int, String, double, Color, GraphicsDevice) main constructor
	 * @see #screen
	 * @see <br>
	 * @see java.awt.GraphicsEnvironment
	 * @see java.awt.GraphicsEnvironment#getDefaultScreenDevice() GraphicsEnvironment.getDefaultScreenDevice()
	 */
	public GameWindow(int screenWidth, int screenHeight, String title) {
		this(screenWidth, screenHeight, title, 1.0, Color.black, GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
	}
	
	/**
	 * Constructs new <code>GameWindow</code> object with specific parameters: width and height of <code>screen</code>, title of <code>JFrame</code> and scale of screen in render canvas.
	 * 
	 * <h1>Construction:</h1>
	 * Uses {@link #GameWindow(int, int, String, double, Color, GraphicsDevice)} constructor.
	 * The <code>constructor</code> is called with arguments: <code>screenWidth</code>, <code>screenHeight</code>, <code>title</code> and <code>screenScale</code>  parameters of this constructor and with default values: <code>defaultScreenColor: Color.black</code> 
	 * and with default <code>graphicsDevice</code> obtained from {@link java.awt.GraphicsEnvironment GraphicsEnvironmet} by {@link java.awt.GraphicsEnvironment#getDefaultScreenDevice() GraphicsEnvrinonment.getDefaultScreenDevice()} method.
	 * <p> 
	 * @param screenWidth	width of the {@link #screen}
	 * @param screenHeight	height of the <code>screen</code>
	 * @param title			title of the <code>JFrame</code>
	 * @param screenScale	scale of the <code>screen</code> in {@link #canvas}
	 * <p>
	 * @see #GameWindow(int, int, String, double, Color, GraphicsDevice) main constructor
	 * @see #screen
	 * @see #canvas
	 * @see <br>
	 * @see java.awt.GraphicsEnvironment
	 * @see java.awt.GraphicsEnvironment#getDefaultScreenDevice() GraphicsEnvironment.getDefaultScreenDevice()
	 */
	public GameWindow(int screenWidth,int screenHeight, String title, double screenScale){
		this(screenWidth, screenHeight, title, 1.0, Color.black, GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
	}
	
	/**
	 * Constructs new <code>GameWindow</code> object with specific parameters: width and height of <code>screen</code>, title of <code>JFrame</code> and defaultScreenColor of <code>screen</code>.
	 * 
	 * <h1>Construction:</h1>
	 * Uses {@link #GameWindow(int, int, String, double, Color, GraphicsDevice)} constructor.
	 * The <code>constructor</code> is called with arguments: <code>screenWidth</code>, <code>screenHeight</code>, <code>title</code> and <code>defaultScreenColor</code>  parameters of this constructor and with default values: <code>screenScale: 1.0</code> 
	 * and with default <code>graphicsDevice</code> obtained from {@link java.awt.GraphicsEnvironment GraphicsEnvironmet} by {@link java.awt.GraphicsEnvironment#getDefaultScreenDevice() GraphicsEnvrinonment.getDefaultScreenDevice()} method.
	 * <p> 
	 * @param screenWidth			width of the {@link #screen}
	 * @param screenHeight			height of the <code>screen</code>
	 * @param title					title of the <code>JFrame</code>
	 * @param defaultScreenColor	default color of the <code>screen</code>
	 * <p>
	 * @see #GameWindow(int, int, String, double, Color, GraphicsDevice) main constructor
	 * @see #screen
	 * @see <br>
	 * @see java.awt.GraphicsEnvironment
	 * @see java.awt.GraphicsEnvironment#getDefaultScreenDevice() GraphicsEnvironment.getDefaultScreenDevice()
	 */
	public GameWindow(int screenWidth, int screenHeight, String title, Color defaultScreenColor){
		this(screenWidth, screenHeight, title, 1.0, defaultScreenColor, GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
	}
	
	/**
	 * Constructs new <code>GameWindow</code> object with specific parameters: width and height of <code>screen</code>, title of <code>JFrame</code>, scale of the <code>screen</code> anddefaultScreenColor of the <code>screen</code>.
	 * 
	 * <h1>Construction:</h1>
	 * Uses {@link #GameWindow(int, int, String, double, Color, GraphicsDevice)} constructor.
	 * The <code>constructor</code> is called with arguments: <code>screenWidth</code>, <code>screenHeight</code>, <code>title</code> <code>screenScale</code> and <code>defaultScreenColor</code> parameters of this constructor and with default values: 
	 *  default <code>graphicsDevice</code> obtained from {@link java.awt.GraphicsEnvironment GraphicsEnvironmet} by {@link java.awt.GraphicsEnvironment#getDefaultScreenDevice() GraphicsEnvrinonment.getDefaultScreenDevice()} method.
	 * <p> 
	 * @param screenWidth			width of the {@link #screen}
	 * @param screenHeight			height of the <code>screen</code>
	 * @param title					title of the <code>JFrame</code>
	 * @param defaultScreenColor	default color of the <code>screen</code>
	 * <p>
	 * @see #GameWindow(int, int, String, double, Color, GraphicsDevice) main constructor
	 * @see #screen
	 * @see #canvas
	 * @see <br>
	 * @see java.awt.GraphicsEnvironment
	 * @see java.awt.GraphicsEnvironment#getDefaultScreenDevice() GraphicsEnvironment.getDefaultScreenDevice()
	 */
	public GameWindow(int screenWidth, int screenHeight, String title, double canvasScale, Color defaultScreenColor) {
		this(screenWidth, screenHeight, title, canvasScale, defaultScreenColor, GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
	}
	
	/**
	 * Constructs new <code>GameWindow</code> object with specific parameters: width and height of <code>screen</code>, title of <code>JFrame</code>, scale of <code>screen</code> in <code>RenderCanvas</code>, default color of <code>screen</code> and <code>GraphicsDevice</code>.
	 * 
	 * <h1>Construction:</h1>
	 * Super class is initialized {@link javax.swing.JFrame#JFrame(String) JFrame(String)} constructor with <code>String</code> argument <code>title</code>, which represents the title of <code>JFrame</code>.
	 * Also sets the <code>JFrame</code> default close operation to {@link JFrame#EXIT_ON_CLOSE} 
	 * and makes the <code>JFrame</code> non resizable by calling {@link javax.swing.JFrame#setResizable(boolean) JFrame.setResizable(boolean)} method with argument <code>false</code>.
	 * Then {@link #canvas} is added to the <code>JFrame</code> by {@link javax.swing.JFrame#add(java.awt.Component)Jframe.add(Component)} method.
	 * After the <code>canvas</code> is added, the <code>JFrame</code> is packed by {@link javax.swing.JFrame#pack() JFrame.pack()},
	 * then the frame is centered by {@link javax.swing.JFrame#setLocationRelativeTo(java.awt.Component) Jframe.setLocationRelativeTo(Component)} method called with argument <code>null</code>.
	 * Lastly the <code>JFrame</code> is set to visible state by calling {@link javax.swing.JFrame#setVisible(boolean) JFrame.setVisible(boolean)} method with argument <code>true</code>.<br>
	 * Initializes {@link svk.sglubos.engine.gfx.Screen Screen} object {@link #screen} with arguments <code>screenWidth</code>, <code>screenHeight</code> and <code>defaultScreenColor</code> passed as a parameters of this constructor.<br>
	 * Initializes {@link svk.sglubos.engine.gfx.RenderCanvas RenderCanvas} object {@link #canvas} with arguments <code>screen</code> and with <code>scale</code> passed as a parameter of this constructor.
	 * The <code>canvas</code> is initialized by {@link svk.sglubos.engine.gfx.RenderCanvas#init(int) RenderCanvas.init(int)} method called wihth argument <code>2</code>
	 * {@link java.awt.GraphicsDevice GraphicsDevice} object {@link #device} with reference <code>GraphicsDevice>passed as a parameter of this constructor.
	 * <p>
	 * @param graphicsDevice		{@link java.awt.GraphicsDevice GraphicsDevice} used by this <code>GameWindow</code>
	 * @param screenWidth			width of the {@link #screen}
	 * @param screenHeight			height of the <code>screen</code>
	 * @param title					title of the {@link javax.swing.JFrame JFrame}
	 * @param screenScale			scale of the <code>screen</code>
	 * @param defaultScreenColor	default {@link java.awt.Color Color} of <code>screen</code>
	 * <p>
	 * @see #GameWindow(int, int, String) 					constructor which constructs <code>GameWindow</code> with default <code>screenScale</code>, <code>screenColor</code> and <code>graphicsDevice</code>
	 * @see #GameWindow(int, int, String, Color) 			constructor which constructs <code>GameWindow</code> with default <code>screenScale</code> and <code>graphicsDevice</code>
	 * @see #GameWindow(int, int, String, double) 			constructor which constructs <code>GameWindow</code> with default <code>screenColor</code> and <code>graphicsDevice</code>
	 * @see #GameWindow(int, int, String, double, Color) 	constructor which constructs <code>GameWindow</code> with default <code>graphicsDevice</code>
	 * @see #screen
	 * @see #canvas
	 * @see #device
	 * @see <br>
	 * @see svk.sglubos.engine.gfx.Screen#Screen(int, int, Color) 				constructor of the Screen class
	 * @see svk.sglubos.engine.gfx.RenderCanvas#RenderCanvas(Screen, double) 	constructor of the RenderCanvas class
	 * @see svk.sglubos.engine.gfx.RenderCanvas#init(int) 						RenderCanvas.init()
	 * @see <br>
	 * @see javax.swing.JFrame
	 * @see java.awt.GraphicsDevice
	 * @see javax.swing.JFrame#JFrame(String)constructor of JFrame class
	 */
	public GameWindow(int screenWidth, int screenHeight, String title, double screenScale, Color defaultScreenColor, GraphicsDevice graphicsDevice) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		screen = new Screen(screenWidth, screenHeight, defaultScreenColor);
		canvas = new RenderCanvas(screen, screenScale);
		
		add(canvas);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		canvas.init(2);
		this.device = graphicsDevice;
	}
	
	/**
	 * Displays content rendered by {@link #screen} in {@link #canvas}.
	 * <p>
	 * This method should be called every frame after the content is rendered. (see {@link GameWindow} example section)
	 * Uses {@link svk.sglubos.engine.gfx.RenderCanvas#showRenderedContent() Canvas.shownRenderedContent()} method.
	 * 
	 * @see #screen
	 * @see #canvas
	 * @see <br>
	 * @see svk.sglubos.engine.gfx.RenderCanvas#showRenderedContent()
	 */
	public void showRenderedContent() {
		canvas.showRenderedContent();
	}
	
	/**
	 * Sets the
	 * 
	 * @param fullScreen
	 */
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
	
	/**
	 * @return {@link #screen screen object} which can render content displayed in this window
	 * @return <br>
	 *
	 * @see svk.sglubos.engine.gfx.Screen
	 */
	public Screen getScreen() {
		return screen;
	}
	
	//TODO Documentation
	public RenderCanvas getRenderCanvas() {
		return canvas;
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
