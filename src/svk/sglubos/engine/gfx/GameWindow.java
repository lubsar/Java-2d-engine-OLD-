package svk.sglubos.engine.gfx;

import java.awt.Color;

import javax.swing.JFrame;

/**
 * <code>GameWindow</code> inherits from {@link javax.swing.JFrame JFrame} class. <br>
 * <code>GameWindow</code> class provides easy way to create {@link javax.swing.JFrame JFrame} which contains {@link svk.sglubos.engine.gfx.RenderCanvas RenderCanvas}.<br>
 * {@link svk.sglubos.engine.gfx.RenderCanvas RenderCanvas} provides ability to display content rendered by {@link svk.sglubos.engine.gfx.Screen Screen}.{@link svk.sglubos.engine.gfx.Screen Screen} is also created in this class. <br>
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
 *  screenFromGameWindow.disposesGraphics();<br>
 *  <br>
 *  //display content rendered on screenFromGameWindow in GameWindow<br>
 *  myGameWindow.showRenderedContent();<br>
 *	}	
 *</code>
 * <p>
 * @see svk.sglubos.engine.gfx.Screen
 * @see svk.sglubos.engine.gfx.RenderCanvas
 * @see {@link #GameWindow(int, int, String, double, Color) constructor}
 */

@SuppressWarnings("serial")
public class GameWindow extends JFrame {
	/**
	 * Screen object which provides ability do draw game content which will be displayed in this windows.<br>
	 * The Screen object object is initialized in {@link #GameWindow(int, int, String, double, Color) constructor}.
	 * Can be obtained by {@link #getScreen()} method. <br>
	 * <p>
	 * @see svk.sglubos.engine.gfx.Screen
	 * @see {@link #getScreen()}
	 * @see {@link #GameWindow(int, int, String, double, Color) constructor}
	 */
	protected Screen screen;
	
	/**
	 * {@link svk.sglubos.engine.gfx.RenderCanvas RenderCanvas object} which provides ability to display <code>renderLayer</code> in <code>JFrame</code>.
	 * The <code>canvas</code> is initialized in {@link #GameWindow(int, int, String, double, Color) constructor}. 
	 * <p>
	 * <h2>WARNING:</h2>
	 * Canvas is flickering while manually resizing JFrame ! <br>
	 * <p>
	 * 
	 * @see {@link #GameWindow(int, int, String, double, Color) constructor}.
	 * @see svk.sglubos.engine.gfx.RenderCanvas
	 */
	protected RenderCanvas canvas;
	
	/**
	 * Constructs new {@link GameWindow} object with specified width and height of screen, title and with canvas scale: 1.0 and default screen color: Color.black. <br>
	 * Uses {@link #GameWindow(int, int, String, double, Color) this(width, height, title, 1.0, Color.black)} constructor.
	 * 
	 * @param screenWidth width of Screen
	 * @param screenHeight height of Screen
	 * @param title title of Game Window
	 * <p>
	 * @see {@link #GameWindow(int, int, String, double, Color)}
	 */
	public GameWindow(int screenWidth, int screenHeight, String title) {
		this(screenWidth, screenHeight, title, 1.0, Color.black);
	}
	
	/**
	 * Constructs new {@link GameWindow} object with specified width and height of screen, title and scale and width default screen color: Color.black. <br>
	 * Uses {@link #GameWindow(int, int, String, double, Color) this(width, height, title, canvasScale, Color.black)} constructor.
	 * 
	 * @param screenWidth width of Screen
	 * @param screenHeight height of Screen
	 * @param title title of Game Window
	 * @param canvasScale value of which is canvas scaled of screen
	 * <p>
	 * @see {@link #GameWindow(int, int, String, double, Color)}
	 */
	public GameWindow(int screenWidth,int screenHeight, String title, double canvasScale){
		this(screenWidth, screenHeight, title, canvasScale, Color.black);
	}
	
	/**
	 * Constructs new {@link GameWindow} object with specified width and height of screen, title and color and canvas scale: 1.0. <br>
	 * Uses {@link #GameWindow(int, int, String, double, Color) this(width, height, title, 1.0, defaultScreenColor)} constructor.
	 * 
	 * @param screenWidth width of Screen
	 * @param screenHeight height of Screen
	 * @param title title of Game Window
	 * @param defaultScreenColor color with which is screen colored 
	 * 
	 * <p>
	 * @see {@link #GameWindow(int, int, String, double, Color)}
	 */
	public GameWindow(int screenWidth, int screenHeight, String title, Color defaultScreenColor){
		this(screenWidth, screenHeight, title, 1.0, defaultScreenColor);
	}
	/**
	 * Constructs new {@link GameWindow} object with specified width and height of screen, title, canvas scale and default screen color. <br>
	 *	
	 * <h1>Initializes</h1>
	 * {@link javax.swing.JFrame#JFrame(String) JFrame constructor} with parameter title, which sets window title to that text<br>
	 * Sets {@link javax.swing.JFrame#setDefaultCloseOperation(int) JFrame default close operation} with argument {@link javax.swing.JFrame#EXIT_ON_CLOSE}, which shuts down entire application. <br>
	 * Sets {@link javax.swing.JFrame#setResizable(boolean) JFrame resizability } with argument <code>false</code>, window is not resizable. <br>
	 *	
	 * <h2>WARNING:</h2>
	 * Canvas is flickering while manually resizing JFrame ! <br>
	 * <p>
	 * {@link #screen Screen object} with parameters: <code>screenWidth</code>,<code> screenHeight </code>and <code>defaultScrenColor</code>, creates screen of specified size and with specified defaul color.<br>
	 * {@link #canvas RenderCanvas object} with parameters: <code>screen</code>, <code>canvasScale</code>, creates canvas with dimensions of screen times scale.<br>
	 * Calls {@link javax.swing.JFrame#add(java.awt.Component)}JFrame add method with argument <code>canvas</code>. adds canvas onto the window. <br>
	 * Calls {@link javax.swing.JFrame#pack()} method,in this case sets size of window depended on size of canvas. <br>
	 * Sets {@link javax.swing.JFrame#setVisible(boolean)} with argument <code>true</code> sets window to visible state. <br>
	 * Sets {@link javax.swing.JFrame#setLocationRelativeTo(java.awt.Component)} with argument <code>null</code>, centers position of screen. <br>
	 * Calls {@link svk.sglubos.engine.gfx.RenderCanvas#init(int)} with argument <code>2</code>, initializes {@link java.awt.image.BufferStategy} with 2 buffers.<br>
	 * <p>
	 * @param screenWidth width of screen
	 * @param screenHeight height of screen
	 * @param title title of JFrame
	 * @param canvasScale scale of renderCanvas
	 * @param defaultScreenColor defaultScreenColor of screen
	 *<p>
	 *@see svk.sglubos.engine.gfx.Screen
	 *@see {@link #getScreen()}
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
	
	/**
	 * @return {@link #screen screen object} which can render content
	 * <p>
	 * @see svk.sglubos.engine.gfx.Screen
	 */
	public Screen getScreen() {
		return screen;
	}
	/**
	 * Displays rendered content on Canvas which is added to JFrame<code><br>
	 * Uses {@link svk.sglubos.engine.gfx.RenderCanvas#showRenderedContent() canvas.shownRenderedContent()} method.
	 * 
	 * @see {@link #canvas}
	 */
	public void showRenderedContent() {
		canvas.showRenderedContent();
	}
}
