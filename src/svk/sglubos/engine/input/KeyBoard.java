package svk.sglubos.engine.input;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import svk.sglubos.engine.utils.debug.MessageHandler;

//TODO document
public class Keyboard extends KeyAdapter {
	private static final Keyboard INSTANCE = new Keyboard();
	public static final byte MAX_BOUND_COMPONENTS = Byte.MAX_VALUE;
	
	private static Map<Integer,KeyEvent> pressedKeys = new HashMap<Integer,KeyEvent>();
	private static String recordedKeyChars = "";
	private static boolean recordKeySequence;
	
	private static byte boundTo;
	
	public static void bind(Component component) {
		if(boundTo < MAX_BOUND_COMPONENTS) {
			KeyListener[] listeners = component.getKeyListeners();
			for(KeyListener list : listeners ) {
				if(list.equals(INSTANCE)){
					MessageHandler.printMessage("KEYBOARD", MessageHandler.INFO, "The mouse is already bound to this component " + component.toString());
					return;
				}
			}
			
			component.addKeyListener(INSTANCE);
			component.requestFocusInWindow();
			
			boundTo++;
		} else {
			MessageHandler.printMessage("KEYBOARD", MessageHandler.ERROR, "Maximum number of bound components was reached !");
		}
	}
	
	public static void unbind(Component component) {
		if(boundTo > 0) {
			KeyListener[] listeners = component.getKeyListeners();
			for(KeyListener list : listeners ) {
				if(list.equals(INSTANCE)){
					component.removeKeyListener(INSTANCE);
					boundTo--;
					return;
				}
			}
			MessageHandler.printMessage("KEYBOARD", MessageHandler.INFO, "MOUSE was not bound to this component" + component.toString());
			
		} else {
			MessageHandler.printMessage("KEYBOARD", MessageHandler.INFO, "MOUSE was not bound to any component.");
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		Integer code = e.getKeyCode();
		
		if(!pressedKeys.containsKey(code)) {
			pressedKeys.put(code, e);
		}
		
		if(recordKeySequence) { 
			recordedKeyChars += e.getKeyChar();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		pressedKeys.remove((Integer)e.getKeyCode());
	}
	
	public static void recordKeyCharacters() {
		recordKeySequence = true;
	}
	
	public static boolean isKeyPressed(int keyCode) {
		return pressedKeys.containsKey(keyCode);
	}
	
	public static KeyEvent getPressEvent(int keyCode) {
		return pressedKeys.get(keyCode);
	}
	
	public static boolean isAnyKeyPressed() {
		return pressedKeys.size() > 0;
	}
	
	public static boolean isBoundToAnyComponent() {
		return boundTo > 0;
	}
	
	public static String getRecordedKeySequence() {
		recordKeySequence = false;
		String temp = recordedKeyChars;
		recordedKeyChars = "";
		
		return temp;
	}
}