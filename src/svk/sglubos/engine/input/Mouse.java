package svk.sglubos.engine.input;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.util.HashMap;
import java.util.Map;

import svk.sglubos.engine.utils.debug.MessageHandler;
/**
 * <code>Mouse</code> class is extended by {@link java.awt.event.MouseAdapter}, 
 * which gives the <code>Mouse</code> abilities to handle mouse events such as {@link java.awt.event.MouseEvent MouseEvent} and {@link java.awt.event.MouseWheelEvent MouseWheelEvent}
 * which are performed on {@link java.awt.Component Components} to which is <code>Mouse</code> bound.<br><br>
 * 
 * The <code>Mouse</code> keeps track of pressed mouse buttons and press events of those buttons.
 * The mouse button press {@link java.awt.event.MouseEvent MouseEvents} are stored in {@link java.util.HashMap HashMap} {@link #pressedButtons} with the keys, 
 * which represent the pressed button. These keys (buttons) are obtained by {@link java.awt.event.MouseEvent#getButton() MouseEvent.getButton()} from the event which will be stored.<br>
 * The <code>Mouse</code> also tracks the position of cursor, if the cursor is inside of {@link java.awt.Component Component} to which is <code>Mouse</code> bound
 * and this {@link java.awt.Component Component} is also stored by <code>Mouse</code>.<br>
 * The <code>Mouse</code> tracks rotation of mouse wheel.
 * Boolean {@link #mouseWheelRotated} determines if the mouse wheel was moved. Rotation of the mouse wheel is cumulated in {@link #mouseWheelRotation} until the {@link #getRotation()} is called.
 * After calling the {@link #getRotation()} method the rotation is returned and then set to <code>0</code> and the {@link #mouseWheelRotated} is set to <code>false</code>.<br>
 * 
 * <h1>Binding</h1>
 * 
 * @see java.awt.event.MouseAdapter
 * @see java.awt.event.MouseEvent
 * @see java.awt.event.MouseWheelEvent
 * @see java.awt.event.Component
 * @see java.util.HashMap
 * @see #isButtonPressed(int)
 * @see #getButtonPressEvent(int)
 * @see #getX()
 * @see #getY()
 * @see #isCursorInsideOfBoundComponent()
 * @see #isCursorInsideOfComponent(Component)
 * @see #wasMousewheelRotated()
 * @see #getRotation()
 * @see #bind(Component)
 * @see #unbind(Component)
 * @see #isBound()
 */
public class Mouse extends MouseAdapter{
	private static final Mouse INSTANCE = new Mouse();
	
	private static Map<Integer, MouseEvent> pressedButtons = new HashMap<Integer, MouseEvent>();
	
	private static int x;
	
	private static int y;
	
	private static Component inside;
	
	private static boolean mouseWheelRotated;
	
	private static int mouseWheelRotation;
	
	private static byte boundTo;
	public static final byte MAX_BOUND_COMPONENTS = Byte.MAX_VALUE;
	
	public static void bind(Component component) {
		if(boundTo < MAX_BOUND_COMPONENTS) {
			MouseListener[] listeners = component.getMouseListeners();
			for(MouseListener list : listeners ) {
				if(list.equals(INSTANCE)){
					MessageHandler.printMessage("MOUSE", MessageHandler.INFO, "The mouse is already bound to this component " + component.toString());
					return;
				}
			}
			
			component.addMouseListener(INSTANCE);
			component.addMouseMotionListener(INSTANCE);
			component.addMouseWheelListener(INSTANCE);
			
			boundTo++;
		} else {
			MessageHandler.printMessage("MOUSE", MessageHandler.ERROR, "Maximum number of bound components was reached !");
		}
	}
	
	public static void unbind(Component component) {
		if(boundTo > 0) {
			MouseListener[] listeners = component.getMouseListeners();
			for(MouseListener list : listeners ) {
				if(list.equals(INSTANCE)){
					component.removeMouseListener(INSTANCE);
					component.removeMouseMotionListener(INSTANCE);
					component.removeMouseWheelListener(INSTANCE);
					boundTo--;
					return;
				}
			}
			MessageHandler.printMessage("MOUSE", MessageHandler.INFO, "MOUSE was not bound to this component" + component.toString());
			
		} else {
			MessageHandler.printMessage("MOUSE", MessageHandler.INFO, "MOUSE was not bound to any component.");
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(!pressedButtons.containsKey(e.getButton()))
			pressedButtons.put(e.getButton(), e);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(pressedButtons.containsKey(e.getButton()))
			pressedButtons.remove(e.getButton());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		inside = (Component) e.getSource();
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		inside = null;
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		mouseWheelRotated = true;
		mouseWheelRotation += e.getWheelRotation();
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}
	
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}
	
	public static void resetMouseWheelRotation() {
		mouseWheelRotated = false;
		mouseWheelRotation = 0;
	}
	
	public static boolean isButtonPressed(int button) {
		return pressedButtons.containsKey(button);
	}
	
	public static MouseEvent getButtonPressEvent(int button) {
		return pressedButtons.get(button); 
	}
	
	public static int getRotation() {
		int rot = mouseWheelRotation;
		mouseWheelRotation = 0;
		mouseWheelRotated = false;
		return rot;
	}
	
	public static int getX() {
		return x;
	}
	
	public static int getY() {
		return y;
	}
	
	public static boolean isCursorInsideOfBoundComponent() {
		return inside != null;
	}
	
	public static boolean isCursorInsideOfComponent(Component component) {
		if(inside == null) {
			return false;
		}
		return Mouse.inside.equals(component);
	}
	
	public static boolean wasMousewheelRotated() {
		return mouseWheelRotated;
	}
	
	public static boolean isBound() {
		return boundTo > 0;
	}
}
