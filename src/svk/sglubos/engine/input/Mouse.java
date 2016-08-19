/*
 *	Copyright 2015 Ľubomír Hlavko
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

package svk.sglubos.engine.input;

import java.awt.Component;
import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import svk.sglubos.engine.utils.debug.DebugStringBuilder;
import svk.sglubos.engine.utils.debug.MessageHandler;

public class Mouse extends MouseAdapter{
	private static final Mouse INSTANCE = new Mouse();
	
	private static Map<Integer, MouseEvent> pressedButtons = new HashMap<Integer, MouseEvent>((int) (MouseInfo.getNumberOfButtons() / 0.75) + 1);
	
	private static List<Integer> clickedButtons = new ArrayList<Integer>();
	
	private static int x;
	
	private static int y;
	
	private static double scaleX;
	
	private static double scaleY;
	
	private static Component inside;
	
	private static boolean mouseWheelRotated;
	
	private static int mouseWheelRotation;
	
	private static byte boundTo;
	public static final byte MAX_BOUND_COMPONENTS = Byte.MAX_VALUE;
	
	private Mouse() {}
	
	public static void initClickBufferSize(int size) {
		clickedButtons = new ArrayList<Integer>(size);
	}
	
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
	
	public static boolean isButtonClicked(int button) {
		if(pressedButtons.containsKey(button) && !clickedButtons.contains((Integer) button)) {
			return clickedButtons.add((Integer) button);
		} else {
			return false;
		}
	}
	
	public static MouseEvent getButtonClickEvent(int button) {
		if(clickedButtons.contains((Integer) button)) {
			return pressedButtons.get(button);			
		} else {
			return null;
		}
	}
	
	public static int getRotation() {
		int rot = mouseWheelRotation;
		mouseWheelRotation = 0;
		mouseWheelRotated = false;
		return rot;
	}
	
	public static void setScaleX(double scaleX) {
		Mouse.scaleX = scaleX;
	}
	
	public static void setScaleY(double scaleY) {
		Mouse.scaleY = scaleY;
	}
	
	public static void setScale(double scaleX, double scaleY) {
		Mouse.scaleX = scaleX;
		Mouse.scaleY = scaleY;
	}
	
	public static int getScaledX() {
		return (int) (x / scaleX);
	}
	
	public static int getScaledY() {
		return (int) (y / scaleY);
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
	
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		ret.append(getClass(), hashCode());
		ret.increaseLayer();
		ret.append(pressedButtons, "pressedButtons");
		ret.append("x", x);
		ret.append("y", y);
		ret.append("inside", inside);
		ret.append("mouseWheelRotated", mouseWheelRotated);
		ret.append("mouseWheelRotation", mouseWheelRotation);
		ret.append("boundTo", boundTo);
		ret.decreaseLayer();
		ret.appendCloseBracket();
		
		return ret.getString();
	}
	
	public static String toDebug() {
		return INSTANCE.toString();
	}
}