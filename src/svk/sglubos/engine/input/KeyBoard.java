package svk.sglubos.engine.input;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import svk.sglubos.engine.utils.debug.MessageHandler;
//TODO Documentation
public class Keyboard extends KeyAdapter {
	private static final Keyboard INSTANCE = new Keyboard();
	private static Component bounding;
	private static List<Integer> pressed = new ArrayList<Integer>();
	
	private static String keySequence = "";
	private static boolean recordKeySequence;
	
	private static boolean bound;
	
	private static int modifiersEx;
	
	private Keyboard() {}
	
	public static void bind(Component compo) {
		if(bound) {
			MessageHandler.printMessage("KEYBOARD","INFO", "Mouse is already bound to: " + bounding.toString());
			return;
		}
		
		compo.addKeyListener(INSTANCE);
		bounding = compo;
		
		bound = true;
	}
	
	public static void unbind() {
		if(!bound) {
			MessageHandler.printMessage("KEYBOARD","INFO", "Mouse was not bound to any component");
			return;
		}
		
		bounding.addKeyListener(INSTANCE);
		
		bounding = null;
		bound = false;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		Integer code = e.getKeyCode();
		
		if(!pressed.contains(code)) {
			pressed.add(code);
		}
		
		if(recordKeySequence) { 
			keySequence += e.getKeyChar();
		}
		
		modifiersEx = e.getModifiersEx();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		Integer code = e.getKeyCode();
		pressed.remove(code);
		
		modifiersEx = e.getModifiersEx();
	}
	
	public static void recordKeyCharacters() {
		recordKeySequence = true;
	}
	
	public static boolean isPressed(int keyCode) {
		return pressed.contains(keyCode);
	}
	
	public static boolean isAnyKeyPressed() {
		return pressed.size() > 0;
	}
	
	public static String getRecordedKeySequence() {
		recordKeySequence = false;
		String temp = keySequence;
		keySequence = "";
		
		return temp;
	}
	
	public static int getModifiersEx() {
		return modifiersEx;
	}
	
}
