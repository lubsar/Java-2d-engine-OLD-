package svk.sglubos.engine.input;

import java.awt.Component;
import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.util.HashMap;
import java.util.Map;

import svk.sglubos.engine.utils.MessageHandler;

/**
 * This class handles mouse input and data can be statically accessed. This class is extended by {@link java.awt.event.MouseAdapter MouseAdapter} 
 * which is used to listen for mouse events.<br>
 * <h1>Functions</h1>
 * Mouse keeps track of coordinates of cursor relative to component to which is 
 * {@link #INSTANCE instance} of this class bound using {@link #bind(Component)} method and if the cursor is inside this component too.
 * Mouse button press state is also tracked by Mouse. The mouse button's states are stored in {@link java.util.HashMap HashMap object}.
 * To obtain state for specific mouse button use method {@link #isButtonPressed(int)} where parameter is mouse button.<br> 
 * You can obtain value of modifiersEx of the latest {@link java.awt.event.MouseEvent MouseEvent} by statically accessing {@link #mouseEventModifiersEx} variable.<br>
 *  MouseWheel rotation is also tracked. If mouse wheel was rotated, 
 * the {@link Mouse#mouseWheelRotated} is <code>true</code> and the rotation can be obtained by {@link #getRotation()} method.<br>
 * The {@link #getRotation()} methods resets the value of {@link #mouseWheelRotation} and also sets the {@link #mouseWheelRotated} to <code>false</code>.<br>
 * The {@link #mouseWheelRotation} and {@link #mouseWheelRotated} can be reset also by {@link #resetMouseWheelRotation()} method.<br><br>
 * 
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
	private static final Mouse INSTANCE = new Mouse();
	private static Map<Integer, Boolean> buttons = new HashMap<Integer, Boolean>();
	
	private static boolean bound;
	
	private static int x;
	private static int y;
	
	private static int mouseEventModifiersEx;
	private static boolean mouseWheelRotated;
	private static boolean insideofComponent;
	
	private static int mouseWheelRotation;
	
	public static void bind(Component component) {
		if(bound) {
			MessageHandler.printMessage("MOUSE", MessageHandler.INFO, "Mouse is bound to a component");
			return;
		}
		
		component.addMouseListener(INSTANCE);
		component.addMouseMotionListener(INSTANCE);
		component.addMouseWheelListener(INSTANCE);
		
		int butts = MouseInfo.getNumberOfButtons();
		for(int i = 0; i < butts; i++) {
			buttons.put(1 + i, false);
		}
		
		bound = true;
	}
	
	public static void unbind(Component component) {
		if(!bound) {
			MessageHandler.printMessage("MOUSE", MessageHandler.INFO, "Mouse is not bound to any component");
			return;
		}
		
		MouseListener[] listeners = component.getMouseListeners();
		
		for(MouseListener mouse : listeners) {
			if(mouse.equals(INSTANCE)) {
				component.removeMouseListener(INSTANCE);
				component.removeMouseMotionListener(INSTANCE);
				component.removeMouseWheelListener(INSTANCE);
				
				bound = false;
				return;
			}
		}
		
		MessageHandler.printMessage("MOUSE", MessageHandler.INFO, "Mouse was not bound to component: " + component.toString());
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
	
	public static int getModifiersX() {
		return mouseEventModifiersEx;
	}
	
	public static boolean isCursorInsideOfComponent() {
		return insideofComponent;
	}
	
	public static boolean wasMousewheelRotated() {
		return mouseWheelRotated;
	}
}
