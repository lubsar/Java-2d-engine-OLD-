package svk.sglubos.engine.input;

import java.awt.Component;
import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.HashMap;
import java.util.Map;

import svk.sglubos.engine.utils.debug.MessageHandler;

/**
 * This class handles mouse input and provides static access to data. This class is extended by {@link java.awt.event.MouseAdapter MouseAdapter} 
 * which is used to listen for mouse events.<br>
 * <h1>Functions</h1>
 * Mouse keeps track of coordinates of cursor relative to component to which is 
 * {@link #INSTANCE instance} of this class bound using {@link #bind(Component)} method and if the cursor is inside this component too.
 * Mouse button press state is also tracked by Mouse. The mouse button's states are stored in {@link java.util.HashMap HashMap object}.
 * To obtain state for specific mouse button use method {@link #isButtonPressed(int)} where parameter is mouse button.<br> 
 * You can obtain value of modifiersEx of the latest {@link java.awt.event.MouseEvent MouseEvent} by {@link #getModifiersEx()} method.<br>
 *  MouseWheel rotation is also tracked. If mouse wheel was rotated, 
 * the {@link #wasMousewheelRotated() } is <code>true</code> and the rotation can be obtained by {@link #getRotation()} method.<br>
 * The {@link #getRotation()} methods resets the value of {@link #mouseWheelRotation} and also sets the {@link #mouseWheelRotated} to <code>false</code>.<br>
 * The {@link #mouseWheelRotation} and {@link #mouseWheelRotated} can be reset also by {@link #resetMouseWheelRotation()} method.<br><br>
 * 
 * @see #bind(Component)
 * @see #unbind()
 * @see #getX()
 * @see #getY()
 * @see #getModifiersEx()
 * @see #isMouseWheelRotated() 
 * @see #isCursorInsideOfComponent()
 * @see #getRotation()
 * @see #resetMouseWheelRotation()
 * @see #isButtonPressed(int)
 */
public class Mouse extends MouseAdapter{
	/**
	 * Instance of this class is used to add and remove listeners to component in {@link #bind(Component)} and {@link #unbind()} methods.
	 * 
	 * @see #bind(Component)
	 * @see #unbind()
	 */
	private static final Mouse INSTANCE = new Mouse();
	
	private static Component bouding;
	
	/**
	 * This {@link java.util.Map Map} keeps track of state of mouse buttons.<br>
	 * Keys are <code>integers</code>, which represent mouse buttons and values for the keys are <code>booleans</code>.<br>
	 * If a button is pressed, the value for that specific button key is <code>true</code> and if is not pressed the value is <code>false</code>.<br> 
	 * To access data use {@link #isButtonPressed(int)} method with specific mouse button key.<br>
	 * 
	 * @see #isButtonPressed(int)
	 */
	private static Map<Integer, Boolean> buttons = new HashMap<Integer, Boolean>();
	
	/**
	 * Determines if <code>Mouse</code> is bound to a {@link java.awt.Component Component}.<br>
	 * The reference of {@link java.awt.Component Component} to which is <code>Mouse</code> bound is stored in {@link #bouding} object.<br>
	 * To bind <code>Mouse</code> to a {@link java.awt.Component Component} use method {@link #bind(Component)}.
	 * If the <code>Mouse</code> is already bound, the message is printed by the {@link svk.sglubos.engine.utils.debug.MessageHandler MessageHandler} and nothing changes.<br>
	 * To unbind the <code>Mouse</code> from a {@link java.awt.Component Component} use method{@link #unbind()}.
	 * If the <code>Mouse</code> was not bound, the Message is printed and nothing changes.<br>
	 * 
	 * @see #bind(Component)
	 * @see #unbind()
	 * @see #bouding
	 */
	private static boolean bound;
	
	/**
	 * Contains the x coordinate of cursor relative to {@link #bouding component} to which is <code>Mouse</code> bound.<br>
	 * To obtain value of this variable use {@link #getX()} method.
	 * 
	 * @see #getX()
	 */
	private static int x;
	
	/**
	 * Contains the x coordinate of cursor relative to {@link #bouding component} to which is <code>Mouse</code> bound.<br>
	 * To obtain value of this variable use {@link #getY()} method.
	 * 
	 * @see #getY()
	 */
	private static int y;
	
	/**
	 * Contains the {@link java.awt.event.MouseEvent#getModifiersEx() modifiersEx} from last {@link java.awt.event.MouseEvent MouseEvent} listened by <code>Mouse</code>.<br>
	 * To obtain value of this variable use {@link #getModifiersEx()} method.
	 * 
	 * @see #getModifiersEx()
	 * @see java.awt.event.MouseEvent
	 * @see java.awt.event.MouseEvent#getModifiersEx()
	 */
	private static int mouseEventModifiersEx;
	
	/**
	 * Determines if mouse wheel was rotated (if {@link java.awt.event.MouseWheelEvent MouseWheelEvent} was listened by <code>Mouse</code>).<br>
	 * If the {@link java.awt.event.MouseWheelEvent MouseWheelEvent} was occurred, the value of this variable is <code>true</code>.
	 * To obtain the value of {@link #mouseWheelRotation} use method {@link #getRotation()}, which returns the value {@link #mouseWheelRotation},
	 * and then sets it to <code>0</code> and sets this variable to <code>false</code> by method {@link #resetMouseWheelRotation()}.<br>
	 * To obtain value of this variable use {@link #wasMousewheelRotated()} method.
	 * 
	 * @see #wasMousewheelRotated()
	 * @see #mouseWheelRotation
	 * @see #getRotation()
	 * @see #resetMouseWheelRotation()
	 * @see java.awt.event.MouseWheelEvent
	 */
	private static boolean mouseWheelRotated;
	
	/**
	 * Determines if cursor is inside of {@link #bouding component} to which is <code>Mouse</code> bound.<br>
	 * If the cursor is inside of {@link #bouding that component} the value of this variable is <code>true</code> else the value is <code>false</code>.<br>
	 * To obtain the value of this variable use {@link #isCursorInsideOfComponent()} method.
	 * 
	 *  @see #isCursorInsideOfComponent()
	 *  @see #bouding
	 */
	private static boolean insideofComponent;
	
	/**
	 * Contains a accumulated value of mouse wheel rotation from {@link java.awt.event.MouseWheelEvent MouseWheelEvent} which is listened by <code>Mouse</code>.<br>
	 * Every time the {@link java.awt.event.MouseWheelEvent MouseWheelEvent} occurs, 
	 * the value of {@link java.awt.event.MouseWheelEvent#getWheelRotation() MouseEvent.getWheelRotation()} is added to the value of this variable.<br>
	 * To obtain value of this variable use {@link #getRotation()} method. This method returns the value of this variable, 
	 * sets value of this variable to <code>0</code> and sets the {@link #mouseWheelRotated} to <code>false</code> by {@link #resetMouseWheelRotation()}.<br>
	 * This value can be reset by {@link #resetMouseWheelRotation()} method.
	 * 
	 * @see #getRotation()
	 * @see #resetMouseWheelRotation()
	 * @see #mouseWheelRotated
	 * @see #wasMousewheelRotated()
	 * @see java.awt.event.MouseWheelEvent
	 */
	private static int mouseWheelRotation;
	
	
	public static void bind(Component component) {
		if(bound) {
			MessageHandler.printMessage("MOUSE", MessageHandler.INFO, "Mouse is already bound to: " + bouding.toString());
			return;
		}
		
		component.addMouseListener(INSTANCE);
		component.addMouseMotionListener(INSTANCE);
		component.addMouseWheelListener(INSTANCE);
		
		int butts = MouseInfo.getNumberOfButtons();
		for(int i = 0; i < butts; i++) {
			buttons.put(1 + i, false);
		}
		
		bouding = component;
		
		bound = true;
	}
	
	public static void unbind() {
		if(!bound) {
			MessageHandler.printMessage("MOUSE", MessageHandler.INFO, "Mouse is not bound to any component");
			return;
		}
		
		bouding.removeMouseListener(INSTANCE);
		bouding.removeMouseMotionListener(INSTANCE);
		bouding.removeMouseWheelListener(INSTANCE);
				
		bouding = null;
		bound = false;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		buttons.put(e.getButton(), true);
		
		mouseEventModifiersEx = e.getModifiersEx();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		buttons.put(e.getButton(), false);
		
		mouseEventModifiersEx = e.getModifiersEx();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		insideofComponent = true;
		
		mouseEventModifiersEx = e.getModifiersEx();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		insideofComponent = false;
		
		mouseEventModifiersEx = e.getModifiersEx();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		mouseWheelRotated = true;
		mouseWheelRotation += e.getWheelRotation();
		
		mouseEventModifiersEx = e.getModifiersEx();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		
		mouseEventModifiersEx = e.getModifiersEx();
	}
	
	public static void resetMouseWheelRotation() {
		mouseWheelRotated = false;
		mouseWheelRotation = 0;
	}
	
	public static boolean isButtonPressed(int button) {
		return buttons.get(button);
	}
	
	public static int getRotation() {
		int rot = mouseWheelRotation;
		resetMouseWheelRotation();
		return rot;
	}
	
	public static int getX() {
		return x;
	}
	
	public static int getY() {
		return y;
	}
	
	public static boolean isBound() {
		return bound;
	}
	
	public static int getModifiersEx() {
		return mouseEventModifiersEx;
	}
	
	public static boolean isCursorInsideOfComponent() {
		return insideofComponent;
	}
	
	public static boolean wasMousewheelRotated() {
		return mouseWheelRotated;
	}
}
