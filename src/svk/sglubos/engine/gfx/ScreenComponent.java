package svk.sglubos.engine.gfx;

import java.awt.Graphics;

import svk.sglubos.engine.utils.debug.DebugStringBuilder;
/**
 * This class provides ability to implement own rendering to {@link svk.sglubos.engine.gfx.Screen Screen}.<br>
 * This class provides abilities such as: changing {@link svk.sglubos.engine.gfx.Screen#pixels Screen pixels} and using {@link svk.sglubos.engine.gfx.Screen#g Screen graphics object}.<br>
 * 
 * <h1>Implementation:</h1>
 * To implement own rendering to specific {@link svk.sglubos.engine.gfx.Screen Screen} object, pass object of own class extended by this class
 * in {@link svk.sglubos.engine.gfx.Screen#addScreenComponent(ScreenComponent) addScreenComponent(screenComponent)} method.
 * Which binds that component to Screen by components {@link #bind(Screen, Graphics, int[])} method which gives that object abilities specified above.
 * And if you want to remove specific screen component from specific {@link svk.sglubos.engine.gfx.Screen Screen} object, 
 * use that objects {@link svk.sglubos.engine.gfx.Screen#removeScreenComponent(ScreenComponent) removeScreenComponent(screenComponent)} method.
 * If component was removed from screen , that component looses abilities to draw to screen from which was removed. This two methods are deprecated because they should not be called outside of Screen.<br><br>
 *
 * @see svk.sglubos.engine.gfx.Screen
 * @see svk.sglubos.engine.gfx.Screen#pixels Screen pixels
 * @see svk.sglubos.engine.gfx.Screen#g Screen Graphics object
 * @see svk.sglubos.engine.gfx.Screen#addScreenComponent(ScreenComponent) add components to Screen
 * @see svk.sglubos.engine.gfx.Screen#removeScreenComponent(ScreenComponent) remove components from Screen
 * @see java.awt.Graphics
 */
public abstract class ScreenComponent {
	/**
	 * Graphics object which has abilities to draw to {@link svk.sglubos.engine.gfx.Screen Screen} object, to which is this component bound.<br>
	 * This object is initialized in {@link #bind(Screen, Graphics, int[])} method, where obtains its ability to draw into the {@link svk.sglubos.engine.gfx.Screen Screen} object 
	 * and this abilities can be removed by {@link #unbind()} method.<br><br>
	 * 
	 * @see svk.sglubos.engine.gfx.Screen
	 * @see #bind(Screen, Graphics, int[])
	 * @see #unbind()
	 */
	protected Graphics g;
	
	/**
	 * {@link svk.sglubos.engine.gfx.Screen Screen} object to which is this component bound.<br>
	 * This object is initialized in {@link #bind(Screen, Graphics, int[])} method.<br>
	 * 
	 * @see svk.sglubos.engine.gfx.Screen
	 * @see #bind(Screen, Graphics, int[])
	 * @see #unbind()
	 */
	protected Screen screen;
	
	/**
	 * Integer array which contains {@link svk.sglubos.engine.gfx.Screen#pixels pixels} of {@link svk.sglubos.engine.gfx.Screen Screen} object to which is this component bound.<br>
	 * Changing values in this array causes to change pixels of actual {@link svk.sglubos.engine.gfx.Screen Screen object}.
	 * This object is initialized in {@link #bind(Screen, Graphics, int[])} method where obtains its abilities.<br>
	 * 
	 * @see svk.sglubos.engine.gfx.Screen
	 * @see svk.sglubos.engine.gfx.Screen#pixels
	 * @see #bind(Screen, Graphics, int[])
	 * @see #unbind()
	 */
	protected int[] pixels;
	
	/**
	 * Boolean which determines if this component is bound to any {@link svk.sglubos.engine.gfx.Screen Screen} object.<br>
	 * 
	 * @see svk.sglubos.engine.gfx.Screen
	 * @see #bind(Screen, Graphics, int[])
	 * @see #unbind()
	 */
	protected boolean bound;
	
	/**
	 * This method makes component able to render into the specific {@link svk.sglubos.engine.gfx.Screen Screen} object.<br>
	 * <strong>This method should not be called manually, to add component to specific {@link svk.sglubos.engine.gfx.Screen Screen} object </stong> 
	 * use its {@link svk.sglubos.engine.gfx.Screen#addScreenComponent(ScreenComponent) addScreenComponent(component)} method.
	 *
	 * <h1>Initializes:</h1>
	 * {@link #g Graphics object} with argument passed in this method, gets abilities to draw into the specific screen object.<br>
	 * {@link #screen Screen object} with argument passed in this method, gets abilities to get info about screen which is this object bound to.<br>
	 * {@link #pixels Pixels array} with argument passed in this method, gets abilities to change screen pixels.<br>
	 * {@link #bound Boolean bound} with true, determines that this object is bound to specific screen object.<br><br>
	 * 
	 * @param screen screen object providing all information about itself
	 * @param g {@link java.awt.Graphics} object which can draw into the screen
	 * @param pixels pixels of screen<br><br>
	 * 
	 * @see svk.sglubos.engine.gfx.Screen
	 * @see svk.sglubos.engine.gfx.Screen#addScreenComponent(ScreenComponent)
	 * @see svk.sglubos.engine.gfx.Screen#removeScreenComponent(ScreenComponent)
	 */
	public void bind(Screen screen, Graphics g, int[] pixels) {
		this.bound = true;
		this.g = g;
		this.pixels = pixels;
		this.screen = screen;
	}
	/**
	 * This method removes components abilities to render into the specific {@link svk.sglubos.engine.gfx.Screen Screen} object.<br>
	 * <strong>This method should not be called manually, to remove component from specific {@link svk.sglubos.engine.gfx.Screen Screen} object</strong>
	 * use its {@link svk.sglubos.engine.gfx.Screen#removeScreenComponent(ScreenComponent) removeScreenComponent(component)} method.
	 *
	 * @see svk.sglubos.engine.gfx.Screen
	 * @see svk.sglubos.engine.gfx.Screen#addScreenComponent(ScreenComponent)
	 * @see svk.sglubos.engine.gfx.Screen#removeScreenComponent(ScreenComponent)
	 */
	public void unbind() {
		bound = false;
		g = null;
		pixels = null;
		screen = null;
	}
	
	//TODO document
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		
		ret.appendClassDataBracket(getClass(), hashCode());
		ret.appendTabln("bound = " + bound);
		ret.appendTabln("g" + g.toString());
		ret.appendTabln("screen = " + screen.toString());
		ret.appendTabln("pixels = " + pixels.toString());
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}
