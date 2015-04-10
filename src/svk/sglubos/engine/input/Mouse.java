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
 * If a {@link java.awt.Component Component is bound to the <code>Mouse</code>,
 * the <code>Mouse</code> gets ability to listen for the various mouse events ({@link java.awt.event.MouseEvent MouseEvent}, {@link java.awt.event.MouseWheelEvent MouseWheelEvent}) performed on that {@link java.awt.Component Component} 
 * and if the {@link java.awt.Component Component} is unbind from the <code>Mouse</code>, the <code>Mouse</code> loses ability to listen for those events.<br><br>
 * 
 * To bind {@link java.awt.Component Component} to the <code>Mouse</code> use {@link #bind(Component)} method.
 * The parameter is {@link java.awt.Component Component} which will be bound to <code>Mouse</code>.
 * To this {@link java.awt.Component Component} is {@link #INSTANCE instance of this class} added as a {@link java.awt.event.MouseListener MouseListener}, {@link java.awt.event.MouseWheelListener MouseWheelListener} and as a {@link java.awt.event.MouseMotionListener MouseMotionListener}.
 * To adding {@link #INSTANCE instance} as the {@link java.awt.event.MouseListener MouseListener} is used {@link java.awt.Component#addMouseListener(MouseListener) Component.addMouseListener(mouseListener)},
 * to adding {@link #INSTANCE instance} as the {@link java.awt.event.MouseWheelListener MouseWheelListener} is used {@link java.awt.Component#addMouseWheelListener(MouseWheelListener) Component.addMouseWheelListener(mouseWheelListener)} and
 * to adding {@link #INSTANCE instance} as the {@link java.awt.event.MouseMotionListener MouseMotionListener} is used {@link java.awt.Component#addMouseMotionListener(MouseMotionListener) Component.addMouseMotionListener(mouseMotionListener)} method.<br>
 * To unbind {@link java.awt.Component Component} from the <code>Mouse</code> use {@link #unbind(Component)} method.
 * The parameter is {@link java.awt.Component Component} which will be unbound from the <code>Mouse</code>.
 * If this {@link java.awt.Component Component} was bound to <code>Mouse</code>,  
 * the {@link #INSTANCE instance of this class} removed as the {@link java.awt.event.MouseListener MouseListener}, {@link java.awt.event.MouseWheelListener MouseWheelListener} and as the {@link java.awt.event.MouseMotionListener MouseMotionListener}.
 * To removing {@link #INSTANCE instance} as the {@link java.awt.event.MouseListener MouseListener} is used {@link java.awt.Component#removeMouseListener(MouseListener) Component.removeMouseListener(mouseListener)},
 * to removing {@link #INSTANCE instance} as the {@link java.awt.event.MouseWheelListener MouseWheelListener} is used {@link java.awt.Component#removeMouseWheelListener(MouseWheelListener) Component.removeMouseWheelListener(mouseWheelListener)} and
 * to removing {@link #INSTANCE instance} as the {@link java.awt.event.MouseMotionListener MouseMotionListener} is used {@link java.awt.Component#removeMouseMotionListener(MouseMotionListener) Component.removeMouseMotionListener(mouseMotionListener)} method.<br><br>
 * 
 * The number of bound {@link java.awt.Component Components} is stored in {@link #boundTo} variable.
 * When the {@link java.awt.Component Component} is bound, the value of {@link #boundTo} is increased by <code>1</code> 
 * and if the {@link java.awt.Component Component} is removed, the value of that variable is decreased by <code>1</code>.
 * The maximum number of bound {@link java.awt.Component Components} at the same time is the value of {@link #MAX_BOUND_COMPONENTS}, 
 * which is <code>127</code>.<br>
 * To test if a {@link java.awt.Component Component} is bound to the <code>Mouse</code> use {@link #isBound()} method, 
 * which returns <code>true</code> if at least <code>1</code> {@link java.awt.Component Component} is bound to <code>Mouse</code>
 * and if there is not any {@link java.awt.Component Component} bound to <code>Mouse</code>, the {@link #isBound()} method returns <code>false</code>.<br>
 * The {@link #inside} stores the reference to the bound {@link java.awt.Component Component} inside of which is cursor. 
 * The {@link #isCursorInsideOfBoundComponent()} returns <code>true</code> if this reference does not equals to <code>null</code> 
 * and if this reference equals to <code>null</code> the returned value is <code>false</code>.
 * To test if the mouse cursor is inside of specific {@link java.awt.Component Component} which is bound to the <code>Mouse</code> use {@link #isCursorInsideOfComponent(Component)} method, 
 * which returns <code>true</code> if the cursor is inside of specified {@link java.awt.Component Component} (if this {@link java.awt.Component Component} equals {@link #inside}) otherwise it returns <code>false</code>.<br><br>  
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
