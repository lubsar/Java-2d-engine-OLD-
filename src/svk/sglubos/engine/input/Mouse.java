package svk.sglubos.engine.input;

import java.awt.Component;
import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.HashMap;
import java.util.Map;

public class Mouse extends MouseAdapter{
	private static final Mouse instance = new Mouse();
	private static Map<Integer, Boolean> buttons = new HashMap<Integer, Boolean>();
	
	public static int x;
	public static int y;
	
	public static int mouseEventModifiersEx;
	public static boolean mouseWheelRotated;
	public static boolean isInsideofComponent;
	
	private static int mouseWheelRotation;
	
	public static void bind(Component component) {
		component.addMouseListener(instance);
		component.addMouseMotionListener(instance);
		component.addMouseWheelListener(instance);
		
		int butts = MouseInfo.getNumberOfButtons();
		for(int i = 0; i < butts; i++) {
			buttons.put(1 + i, false);
		}
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
		isInsideofComponent = true;
		
		mouseEventModifiersEx = e.getModifiersEx();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		isInsideofComponent = false;
		
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
	
	public static int getRotation() {
		int rot = mouseWheelRotation;
		mouseWheelRotated = false;
		mouseWheelRotation = 0;
		return rot;
	}
	
	public static boolean isButtonPressed(int button) {
		return buttons.get(button);
	}
}
