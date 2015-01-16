package svk.sglubos.engine.gfx;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;

import javax.swing.JFrame;

//TODO Documentation 
/**
 * <code>GameWindow</code> class provides easy way to create {@link javax.swing.JFrame JFrame} which contains {@link svk.sglubos.engine.gfx.RenderCanvas RenderCanvas}.<br>
 * <code>GameWindow</code> inherits from {@link javax.swing.JFrame JFrame} class. <br>
 * {@link svk.sglubos.engine.gfx.RenderCanvas RenderCanvas} provides ability to display content rendered by {@link svk.sglubos.engine.gfx.Screen Screen}.<br>
 * {@link svk.sglubos.engine.gfx.Screen Screen} object which can will be displayed is also created in this class. <br>
 * The {@link svk.sglubos.engine.gfx.Screen Screen object} is returned by {@link #getScreen()} method.
 * <p>
 * <code>GameWindow</code> does not handle {@link svk.sglubos.engine.gfx.Screen#prepare() screen prepare method} so you have to do it manually.
 * To display rendered content in {@link svk.sglubos.engine.gfx.Screen#renderLayer screen renderLayer} call method {@link #showRenderedContent()}.
 * Method update does not disposes {@link svk.sglubos.engine.gfx.Screen#g screen Graphics object} so you have to do it manually.
 * 
 * <h1>example: render method called every frame</h1>
 * 
 * <code>
 * void render(){<br>
 * 	//prepare screen obtained from GameWindow<br>
 * 	screenFromGameWindow.prepare();<br>
 * <br>
 *	//render content to screen<br>	
 * 	screenFromGameWindow.renderRectangle(0,0,50,50);<br>
 * 	...<br>
 * 	<br>
 *	//dispose screen Graphics<br>
 *  screenFromGameWindow.disposeGraphics();<br>
 *  <br>
 *  //display content rendered on screenFromGameWindow in GameWindow<br>
 *  myGameWindow.showRenderedContent();<br>
 *	}	
 *</code>
 * <p>
 * @see svk.sglubos.engine.gfx.Screen
 * @see svk.sglubos.engine.gfx.RenderCanvas
 * @see #GameWindow(int, int, String, double, Color) constructor
 */

@SuppressWarnings("serial")
public class GameWindow extends JFrame {
	/**
	 * Screen object which provides ability do draw game content which will be displayed in this windows.<br>
	 * The Screen object object is initialized in {@link #GameWindow(int, int, String, double, Color) constructor}.
	 * Can be obtained by {@link #getScreen()} method. <br>
	 * <p>
	 * @see svk.sglubos.engine.gfx.Screen
	 * @see #getScreen()
	 * @see #GameWindow(int, int, String, double, Color) constructor
	 */
	protected Screen screen;
	
	/**
	 * {@link svk.sglubos.engine.gfx.RenderCanvas RenderCanvas object} which provides ability to display <code>renderLayer</code> in <code>JFrame</code>.
	 * The <code>canvas</code> is initialized in {@link #GameWindow(int, int, String, double, Color) constructor}. 
	 * 
	 * <h2>WARNING:</h2>
	 * Canvas is flickering while manually resizing JFrame ! <br>
	 * <p>
	 * 
	 * @see #GameWindow(int, int, String, double, Color) constructor
	 * @see svk.sglubos.engine.gfx.RenderCanvas
	 */
	protected RenderCanvas canvas;
	
	protected GraphicsDevice device;
	
	/**
	 * Constructs new {@link GameWindow} object with specified width and height of screen, title, with canvas scale: 1.0 and with default screen color Color.black. <br>
	 * Uses {@link #GameWindow(int, int, String, double, Color) this(width, height, title, 1.0, Color.black)} constructor.
	 * 
	 * @param screenWidth width of Screen
	 * @param screenHeight height of Screen
	 * @param title title of Game Window
	 * 
	 * @see #GameWindow(int, int, String, double, Color)
	 */
	public GameWindow(int screenWidth, int screenHeight, String title) {
		this(screenWidth, screenHeight, title, 1.0, Color.black);
	}
	
	/**
	 * Constructs new {@link GameWindow} object with specified width and height of screen, title and scale and with default screen color Color.black. <br>
	 * Uses {@link #GameWindow(int, int, String, double, Color) this(width, height, title, canvasScale, Color.black)} constructor.
	 * 
	 * @param screenWidth width of Screen
	 * @param screenHeight height of Screen
	 * @param title title of Game Window
	 * @param canvasScale value of which is canvas scaled of screen
	 * 
	 * @see #GameWindow(int, int, String, double, Color)
	 */
	public GameWindow(int screenWidth,int screenHeight, String title, double canvasScale){
		this(screenWidth, screenHeight, title, canvasScale, Color.black);
	}
	
	/**
	 * Constructs new {@link GameWindow} object with specified width and height of screen, title and color and with canvas scale 1.0. <br>
	 * Uses {@link #GameWindow(int, int, String, double, Color) this(width, height, title, 1.0, defaultScreenColor)} constructor.
	 * 
	 * @param screenWidth width of Screen
	 * @param screenHeight height of Screen
	 * @param title title of Game Window
	 * @param defaultScreenColor color with which is screen colored 
	 * 
	 * @see #GameWindow(int, int, String, double, Color)
	 */
	public GameWindow(int screenWidth, int screenHeight, String title, Color defaultScreenColor){
		this(screenWidth, screenHeight, title, 1.0, defaultScreenColor);
	}
	/**
	 * Constructs new {@link GameWindow} object with specified width and height of screen, title, canvas scale and with specified default screen color. <br>
	 *	
	 * <h1>Initializes: </h1>
	 * {@link javax.swing.JFrame#JFrame(String) JFrame constructor} with parameter title, which sets window title to title passed by this constructor<br>
	 * Sets {@link javax.swing.JFrame#setDefaultCloseOperation(int) JFrame default close operation} to {@link javax.swing.JFrame#EXIT_ON_CLOSE}, which shuts down entire application. <br>
	 * Makes JFrame not resizable.<br>
	 *	
	 * <h2>WARNING:</h2>
	 * Canvas is flickering while manually resizing JFrame ! <br>
	 * <p>
	 * {@link #screen Screen object} with parameters: <code>screenWidth</code>,<code> screenHeight </code>and <code>defaultScreenColor</code>, creates screen of specified size and with specified default color.<br>
	 * {@link #canvas RenderCanvas object} with parameters: <code>screen</code>, <code>canvasScale</code>, creates canvas with dimensions of screen times scale.<br>
	 * Adds canvas onto the window. <br>
	 * Packs the JFrame, which in this case sets size of window depended on size of canvas.
	 * Sets JFame to visible state. <br>
	 * Sets JFrame location relative to <code>null </code>, which centers JFrame.<br>
	 * Calls {@link svk.sglubos.engine.gfx.RenderCanvas#init(int)} with argument <code>2</code>, which initializes {@link svk.sglubos.engine.gfx.RenderCanvas#bs render canvas BufferStrategy} with 2 buffers.<br>
	 * <p>
	 * @param screenWidth width of screen
	 * @param screenHeight height of screen
	 * @param title title of JFrame
	 * @param canvasScale scale of canvas which displays screen
	 * @param defaultScreenColor defaultScreenColor of screen
	 *
	 *@see svk.sglubos.engine.gfx.Screen
	 *@see #getScreen()
	 *@see javax.swing.JFrame
	 *@see svk.sglubos.engine.gfx.RenderCanvas
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
	 *
	 * @see svk.sglubos.engine.gfx.Screen
	 */
	public Screen getScreen() {
		return screen;
	}
	/**
	 * Displays rendered content on Canvas which is added to <code>JFrame</code><br>
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
		} else {
			setWindowDecoration(false);
			setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
	}
}
