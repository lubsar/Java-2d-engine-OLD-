package svk.sglubos.engine.input;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
//TODO Documentation
public class KeyBoard extends KeyAdapter{
	private static final KeyBoard instance = new KeyBoard();
	private static Map<Integer, Boolean> keys = new HashMap<Integer, Boolean>();
	
	private static String charSequence = "";
	private static boolean recordKeyChars;
	private static boolean listenToPressed;
	private static int pressedKeyCode;
	
	public static int modifiersEx;
	public static boolean keyPressed;
	
	public static void bind(Component c) {
		c.addKeyListener(instance);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		modifiersEx = e.getModifiersEx();
		
		if(keys.containsKey(code)){
			keys.put(code, true);
		}
		
		if(recordKeyChars){
			charSequence += e.getKeyChar();
		}
		
		if(listenToPressed){
			pressedKeyCode = code;
		}
		
		keyPressed = true;
		e.consume();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		modifiersEx = e.getModifiersEx();
		
		if(keys.containsKey(code)){
			keys.put(code, false);
		}
		
		keyPressed = false;
		e.consume();
	}
	
	public static void recordKeyChars() {
		recordKeyChars = true;
	}
	
	public static String getCharSequence() {
		String s = charSequence;
		charSequence = "";
		return s;
	}
	
	public static void register(int keyCode) {
		keys.put(keyCode, false);
	}
	
	public static boolean isPressed(int keyCode) {
		if(keys.containsKey(keyCode)) {
			return keys.get(keyCode);			
		} else {
			throw new RuntimeException("Keycode " + keyCode + "not registered !");
		}
	}
	
	public static void listenToPressed() {
		listenToPressed = true;
	}
	
	public static int getPressedKeyCode() {
		int pressed = pressedKeyCode;
		listenToPressed = false;
		return pressed;
	}
}
