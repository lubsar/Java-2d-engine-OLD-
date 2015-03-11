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
 * which is used to listen for various mouse events such as {@link java.awt.event.MouseEvent MouseEvent} and {@link java.awt.event.MouseWheelEvent MouseWheelEvent}.<br>
 * 
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
 * 
 * @see java.awt.event.MouseAdapter
 * @see java.awt.event.MouseEvent
 * @see java.awt.event.MouseWheelEvent
 */
public class Mouse extends MouseAdapter{
	/**
	 * Instance of this class is used to add and remove listeners to component in {@link #bind(Component)} and {@link #unbind()} methods.
	 * 
	 * @see #bind(Component)
	 * @see #unbind()
	 */
	private static final Mouse INSTANCE = new Mouse();
	
	/**
	 * {@link java.awt.Component Component} to which can be <code>Mouse</code> bound by {@link #bind(Component)} method 
	 *  and to unbind <code>Mouse</code> use {@link #unbind()} method. 
	 *  The binding gives the <code>Mouse</code> ability to listen to mouse events such as {@link java.awt.event.MouseEvent MouseEvent} 
	 *  and {@link java.awt.event.MouseWheelEvent MouseWheelEvent}. The <code> Mouse can be bound to only one {@link java.awt.Component Component} at time.<br>
	 *  
	 *  @see java.awt.Component
	 *  @see java.awt.event.MouseEvent
	 *  @see java.awt.event.MouseWheelEvent
	 *  @see #bind(Component)
	 *  @see #unbind()
	 */
	private static Component component;
	
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
	 * The reference of {@link java.awt.Component Component} to which is <code>Mouse</code> bound is stored in {@link #component} object.<br>
	 * To bind <code>Mouse</code> to a {@link java.awt.Component Component} use method {@link #bind(Component)}.
	 * If the <code>Mouse</code> is already bound, the message is printed by the {@link svk.sglubos.engine.utils.debug.MessageHandler MessageHandler} and nothing changes.<br>
	 * To unbind the <code>Mouse</code> from a {@link java.awt.Component Component} use method{@link #unbind()}.
	 * If the <code>Mouse</code> was not bound, the Message is printed and nothing changes.<br>
	 * 
	 * @see #bind(Component)
	 * @see #unbind()
	 * @see #component
	 */
	private static boolean bound;
	
	/**
	 * Contains the x coordinate of cursor relative to {@link #component component} to which is <code>Mouse</code> bound.<br>
	 * To obtain value of this variable use {@link #getX()} method.
	 * 
	 * @see #getX()
	 */
	private static int x;
	
	/**
	 * Contains the x coordinate of cursor relative to {@link #component component} to which is <code>Mouse</code> bound.<br>
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
	 * Determines if cursor is inside of {@link #component component} to which is <code>Mouse</code> bound.<br>
	 * If the cursor is inside of {@link #component that component} the value of this variable is <code>true</code> else the value is <code>false</code>.<br>
	 * To obtain the value of this variable use {@link #isCursorInsideOfComponent()} method.
	 * 
	 *  @see #isCursorInsideOfComponent()
	 *  @see #component
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
	
	/**
	 * Binds <code>Mouse</code> to the specified {@link java.awt.Component Component} which gives <code>Mouse</code> ability to listen to various mouse events 
	 * such as {@link java.awt.event.MouseEvent MouseEvent} and {@link #java.awt.event.MouseWheelEvent MouseWheelEvent}.<br> 
	 * To unbind <code>Mouse</code> use {@link #unbind()} method.
	 * 
	 * <h1>Binding</h1>
	 * if the <code>Mouse</code> is already bound info message is printed by {@link svk.sglubos.engine.utils.debug.MessageHandler MessageHandler} 
	 *  and return statement is called. And if bound is <code>false</code> the binding is processed.
	 * To the specified {@link java.awt.Component Component} is added {@link #INSTANCE instance of this class} as a {@link java.awt.event.MouseListener MouseListener}, 
	 * {@link java.awt.event.MouseWheelListener MouseWheelListener} and {@link java.awt.event.MouseMotionListener MouseMotionListener}. 
	 * Reference to the specified {@link java.awt.Component Component} is stored to {@link #component}. The {@link #buttons } map is initialized with keys from <code>1</code> to
	 * value obtained by {@link java.awt.MouseInfo#getNumberOfButtons() MouseInfo.getNumberOfButtons()} <code>+ 1</code>. 
	 * And the {@link #bound} is set to <code>true</code><br><br>
	 * 
	 * @param component component to which will be <code>Mouse</code> bound to<br><br>
	 * 
	 * @see #unbind()		
	 * @see #buttons
	 * 
	 * @see java.awt.Component
	 * @see java.awt.event.MouseAdapter
	 */
	public static void bind(Component component) {
		if(bound) {
			MessageHandler.printMessage("MOUSE", MessageHandler.INFO, "Mouse is already bound to: " + Mouse.component.toString());
			return;
		}
		
		component.addMouseListener(INSTANCE);
		component.addMouseMotionListener(INSTANCE);
		component.addMouseWheelListener(INSTANCE);
		
		int butts = MouseInfo.getNumberOfButtons();
		for(int i = 0; i < butts; i++) {
			buttons.put(i, false);
		}
		
		Mouse.component = component;
		
		bound = true;
	}
	
	/**
	 * Unbinds <code>Mouse</code> from {@link #component component} to which the <code>Mouse</code> was bound by {@link #bind(Component)} method 
	 * which removes <code>Mouse's</code> ability to listen to various mouse events such as {@link java.awt.event.MouseEvent MouseEvent} and {@link java.awt.event.MouseWheelEvent MouseWheelEvent}.<br>
	 * <h1>Unbinding</h1>
	 * If the <code>Mouse</code> was not bound (the {@link #bound} is <code>false</code>), the message is printed through {@link svk.sglubos.engine.utils.debug.MessageHandler MessageHandler} and return statement is called.
	 * And if the {@link #bound} is <code>true</code> the unbinding is processed. From the {@link #component Component } to which <code>Mouse</code> was bound is {@link #INSTANCE} removed as the {@link java.awt.event.MouseListener MouseListener}, 
	 * as the {@link java.awt.event.MouseWheelListener MouseWheelListener} and as the {@link java.awt.event.MouseMotionListener MouseMotionListener}. 
	 * The {@link #component reference} to the {@link java.awt.Component Component} to which the <code>Mouse</code> was bound is set to <code>null</code>.
	 * And the {@link #bound} is set to <code>false</code>.<br><br> 
	 * 
	 * @see #bind()		
	 * @see #bound
	 * 
	 * @see java.awt.Component
	 * @see java.awt.event.MouseAdapter
	 */
	public static void unbind() {
		if(!bound) {
			MessageHandler.printMessage("MOUSE", MessageHandler.INFO, "Mouse is not bound to any component");
			return;
		}
		
		component.removeMouseListener(INSTANCE);
		component.removeMouseMotionListener(INSTANCE);
		component.removeMouseWheelListener(INSTANCE);
				
		component = null;
		bound = false;
	}
	
	/**
	 * Overridden {@link java.awt.event.MouseAdapter#mousePressed(MouseEvent) MouseAdapter.mousePressed(MouseEvent) method} from {@link java.awt.event.MouseAdapter MouseAdapter} which is used to handle presses of the mouse buttons.<br>
	 * <h1>Handling</h1>
	 * The pressed button's state is stored in {@link #buttons} {@link java.util.HashMap HashMap} with the key {@link java.awt.event.MouseEvent#getButton() MouseEvent.getButton()} 
	 * and with the value <code>true</code>. 
	 * And the {@link #mouseEventModifiersEx}  is set to {@link java.awt.event.MouseEvent#getModifiersEx() MouseEvent.getModifiersEx()} from this listened event.<br>
	 * 
	 * @see #buttons
	 * @see #isButtonPressed(int)
	 * @see #mouseEventModifiersEx
	 * @see java.awt.event.MouseEvent
	 *  
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		buttons.put(e.getButton(), true);
		
		mouseEventModifiersEx = e.getModifiersEx();
	}
	
	/**
	 * Overridden {@link java.awt.event.MouseAdapter#mouseReleased(MouseEvent) MouseAdapter.mouseReleased(MouseEvent) method} from {@link java.awt.event.MouseAdapter MouseAdapter} 
	 * which is used to handle releases of the mouse buttons.<br>
	 * <h1>Handling</h1>
	 * The released button's state is stored in {@link #buttons} {@link java.util.HashMap HashMap} with the key {@link java.awt.event.MouseEvent#getButton() MouseEvent.getButton()} 
	 * and with the value <code>false</code>. 
	 * And the {@link #mouseEventModifiersEx}  is set to {@link java.awt.event.MouseEvent#getModifiersEx() MouseEvent.getModifiersEx()} from this listened event.<br>
	 * 
	 * @see #buttons
	 * @see #isButtonPressed(int)
	 * @see #mouseEventModifiersEx
	 * @see java.awt.event.MouseEvent
	 *  
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		buttons.put(e.getButton(), false);
		
		mouseEventModifiersEx = e.getModifiersEx();
	}

	/**
	 * Overridden {@link java.awt.event.MouseAdapter#mouseEntered(MouseEvent) MouseAdapter.mouseEntered(MouseEvent) method} from {@link java.awt.event.MouseAdapter MouseAdapter} 
	 * which is used to handle if the cursor enters the {@link #component Component} to which is <code>Mouse</code> bound.<br>
	 * <h1>Handling</h1>
	 * The {@link #insideofComponent} is set to <code>true</code>.
	 * And the {@link #mouseEventModifiersEx}  is set to {@link java.awt.event.MouseEvent#getModifiersEx() MouseEvent.getModifiersEx()} from this listened event.<br>
	 * 
	 * @see #insideofComponent
	 * @see #isCursorInsideOfComponent()
	 * @see #mouseEventModifiersEx
	 * @see #component
	 * @see java.awt.event.MouseEvent
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		insideofComponent = true;
		
		mouseEventModifiersEx = e.getModifiersEx();
	}
	
	/**
	 * Overridden {@link java.awt.event.MouseAdapter#mouseExited(MouseEvent) MouseAdapter.mouseExited(MouseEvent) method} from {@link java.awt.event.MouseAdapter MouseAdapter} 
	 * which is used to handle if the cursor exits the {@link #component Component} to which is <code>Mouse</code> bound.<br>
	 * <h1>Handling</h1>
	 * The {@link #insideofComponent} is set to <code>false</code>.
	 * And the {@link #mouseEventModifiersEx}  is set to {@link java.awt.event.MouseEvent#getModifiersEx() MouseEvent.getModifiersEx()} from this listened event.<br>
	 * 
	 * @see #insideofComponent
	 * @see #isCursorInsideOfComponent()
	 * @see #mouseEventModifiersEx
	 * @see #component
	 * @see java.awt.event.MouseEvent
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		insideofComponent = false;
		
		mouseEventModifiersEx = e.getModifiersEx();
	}
	
	/**
	 * Overridden {@link java.awt.event.MouseAdapter#mouseWheelMoved(MouseWheelEvent) MouseAdapter.mouseWheelMoved(MouseEvent) method} from {@link java.awt.event.MouseAdapter MouseAdapter} 
	 * which is used to handle if the mouse wheel is rotated.<br>
	 * <h1>Handling</h1>
	 * The {@link #mouseWheelRotated} is set to <code>true</code>. 
	 * The value obtained by {@link java.awt.event.MouseWheelEvent#getWheelRotation() MouseWheelEvent.getWheelRotation()} method is added to {@link #mouseWheelRotation} variable.<br>
	 * And the {@link #mouseEventModifiersEx}  is set to {@link java.awt.event.MouseEvent#getModifiersEx() MouseEvent.getModifiersEx()} from this listened event.<br>
	 * 
	 * @see #getRotation()
	 * @see #resetMouseWheelRotation()
	 * @see #isCursorInsideOfComponent()
	 * @see #mouseEventModifiersEx
	 * @see java.awt.event.MouseWheelEvent
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		mouseWheelRotated = true;
		mouseWheelRotation += e.getWheelRotation();
		
		mouseEventModifiersEx = e.getModifiersEx();
	}
	
	/**
	 * Overridden {@link java.awt.event.MouseAdapter#mouseMoved(MouseEvent) MouseAdapter.mouseMoved(MouseEvent) method} from {@link java.awt.event.MouseAdapter MouseAdapter} 
	 * which is used to handle if the cursor is moved inside of {@link #component Component} to which is <code>Mouse</code> bound.<br>
	 * <h1>Handling</h1>
	 * Sets the value of {@link #x} to value obtained by {@link java.awt.event.MouseEvent#getX() MouseEvent.getX()}, 
	 * sets the value of {@link #y} to value obtained by {@link java.awt.event.MouseEvent#getY() MouseEvent.getY()}.
	 * And the {@link #mouseEventModifiersEx}  is set to {@link java.awt.event.MouseEvent#getModifiersEx() MouseEvent.getModifiersEx()} from this listened event.<br>
	 * 
	 * @see #x
	 * @see #getX()
	 * @see #y
	 * @see #getY()
	 * @see #mouseEventModifiersEx
	 * @see java.awt.event.MouseEvent
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		
		mouseEventModifiersEx = e.getModifiersEx();
	}
	
	/**
	 * Sets the {@link #mouseWheelRotation} to <code>0</code> and {@link #mouseWheelRotated} to <code>false</code>.<br>
	 * The {@link #mouseWheelRotation} can be obtained by {@link #getRotation()} method, 
	 * which also sets the {@link #mouseWheelRotation} to <code>0</code> and {@link #mouseWheelRotated} to <code>false</code>.
	 * 
	 * @see #getRotation()
	 * @see #mouseWheelRotation
	 * @see #mouseWheelRotated
	 */
	public static void resetMouseWheelRotation() {
		mouseWheelRotated = false;
		mouseWheelRotation = 0;
	}
	
	/**
	 * Determines if specific mouse button is pressed.<br>
	 * Returns the boolean value stored in {@link java.util.HashMap HashMap} {@link #buttons}, 
	 * where the integer (parameter of this method) is the key for obtaining the value stored in this {@link java.util.HashMap HashMap}.
	 * If the mouse button with the specified key is pressed, the returned value is <code>true</code>, otherwise the returned value is <code>false</code>
	 * The key can be simply the number of mouse button. see {@link java.awt.event.MouseEvent#getButton() MouseEvent.getButton()}
	 *   
	 * @param button specified key for obtaining state of mouse button with this key<br><br>
	 * @return state of button with specified key<br><br>
	 * 
	 * @see #buttons
	 * @see java.awt.event.MouseEvent#getButton()
	 * @see java.awt.event.MouseEvent
	 */
	public static boolean isButtonPressed(int button) {
		return buttons.get(button);
	}
	
	/**
	 * Returns the value of {@link #mouseWheelRotation} which contains the rotation of mouse wheel.<br>
	 * The value of {@link #mouseWheelRotation} contains the rotation accumulated from the last reset of the rotation or last call of this method.
	 * Also sets the {@link #mouseWheelRotation} to <code>0</code> and sets the {@link #mouseWheelRotated} to <code>false</code>.
	 * The value of {@link #mouseWheelRotation} and {@link #mouseWheelRotated} can be also reset by {@link #resetMouseWheelRotation()} method.<br>
	 * 
	 * @return the rotation of mouse wheel<br><br>
	 * 
	 * @see #mouseWheelRotation
	 * @see #mouseWheelRotated
	 * @see #resetMouseWheelRotation()
	 */
	public static int getRotation() {
		int rot = mouseWheelRotation;
		mouseWheelRotation = 0;
		mouseWheelRotated = false;
		return rot;
	}
	
	/**
	 * @return the horizontal coordinate of mouse cursor relative to component to which is <code>Mouse</code> bound<br><br>
	 * 
	 * @see #bind(Component)
	 * @see #unbind()
	 * @see #component
	 */
	public static int getX() {
		return x;
	}
	
	/**
	 * @return the vertical coordinate of mouse cursor relative to {@link #component component} to which is <code>Mouse</code> bound<br><br>
	 * 
	 * @see #bind(Component)
	 * @see #unbind()
	 * @see #component
	 */
	public static int getY() {
		return y;
	}
	
	/**
	 * @return the value of {@link #bound}, which determines if the <code>Mouse</code> is bound to a {@link #java.awt.Component Component}, 
	 * if is bound returns <code>true</code> otherwise return <code>false</code><br><br>
	 * 
	 * @see #bound
	 * @see #bind(Component)
	 * @see #unbind()
	 */
	public static boolean isBound() {
		return bound;
	}
	
	/**
	 * @return the extended modifiers from the latest {@link java.awt.event.MouseEvent MouseEvent} ({@link java.awt.event.MouseEvent#getModifiersEx() MouseEvent.getModifiersEx()})
	 * or from the latest {@link java.awt.event.MouseWheelEvent MouseWheelEvent} ({@link java.awt.event.MouseWheelEvent#getModifiersEx() MouseWheelEvent.getModifiersEx()}).<br><br>
	 * 
	 * @see #mouseEventModifiersEx
	 * @see java.awt.event.MouseEvent#getModifiersEx()
	 * @see java.awt.event.MouseWheelEvent#getModifiersEx()
	 */
	public static int getModifiersEx() {
		return mouseEventModifiersEx;
	}
	
	/**
	 * @return the value of {@link #insideofComponent}, which determines if the mouse cursor is inside of a {@link #component} to which is mouse bound.<br><br>
	 * 
	 * @see #bound
	 * @see #component
	 * @see #bind(Component)
	 * @see #unbind()
	 */
	public static boolean isCursorInsideOfComponent() {
		return insideofComponent;
	}
	
	/**
	 * @return the value of {@link #mouseWheelRoted}, which determines if the mouse wheel was rotated
	 * 
	 * @see #mouseWheelRotated
	 * @see #mouseWheelRotation
	 * @see #wasMousewheelRotated()
	 * @see #getRotation()
	 */
	public static boolean wasMousewheelRotated() {
		return mouseWheelRotated;
	}
}
