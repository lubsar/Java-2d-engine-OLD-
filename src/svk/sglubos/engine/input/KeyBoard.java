package svk.sglubos.engine.input;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import svk.sglubos.engine.utils.debug.MessageHandler;
//TODO focus ? 
/**
 * This class handles keyboard input and provides static access to data. This class is extended by {@link java.awt.event.KeyAdapter KeyAdapter} 
 * which is used to listen for {@link java.awt.event.KeyEvent KeyEvents}.<br>
 * 
 * <h1>Functions</h1>
 * <code>KeyBoard</code> keeps track of pressed keyboard keys, when the {@link #bounding component} to which is {@link #INSTANCE} of }the <code>Keyboard</code> bound is focused. 
 * To bind <code>Keyboard</code> to specific {@link java.awt.Component Component} use method {@link #bind(Component)} 
 * and to unbind from component to which mouse was bound use method {@link #unbind()}.
 * The currently pressed keys are stored in {@link java.util.ArrayList ArrayList} {@link #pressedKeys} 
 * which contains <code>keyCodes</code> of these keys.
 * You can check if a specific key is pressed by method {@link #isKeyPressed(int)}, where the parameter is <code>keyCode</code> of keyboard key.
 * The <code>keyCodes</code> for keys can be obtained from {@link java.awt.event.KeyEvent KeyEvent}.
 * Also you can check if any key is pressed by {@link #isAnyKeyPressed()} method.<br>
 * The <code>Keyboard</code> can also record the pressed key characters and store them inside of String {@link #keySequence}. 
 * The recording can be started with {@link #recordKeyCharacters()} method and to obtain the recorded value use {@link #getRecordedKeySequence()} method.
 * This method returns the {@link #keySequence} and stops the recording of characters.<br><br>
 * 
 * @see #bind(Component)
 * @see #unbind()
 * @see #isKeyPressed(int)
 * @see #isAnyKeyPressed()
 * @see #recordKeySequence
 * @see #getRecordedKeySequence()
 * 
 * @see java.awt.event.KeyAdapter
 * @see java.awt.event.KeyEvent
 */
public class Keyboard extends KeyAdapter {
	private static final Keyboard INSTANCE = new Keyboard();
	private static Component bounding;
	private static List<Integer> pressedKeys = new ArrayList<Integer>();
	
	private static String keySequence = "";
	private static boolean recordKeySequence;
	
	private static boolean bound;
	
	private static int modifiersEx;
	
	private Keyboard() {}
	
	public static void bind(Component compo) {
		if(bound) {
			MessageHandler.printMessage("KEYBOARD","INFO", "Keyboard is already bound to: " + bounding.toString());
			return;
		}
		
		compo.addKeyListener(INSTANCE);
		bounding = compo;
		
		bound = true;
	}
	
	public static void unbind() {
		if(!bound) {
			MessageHandler.printMessage("KEYBOARD","INFO", "Keyboard was not bound to any component");
			return;
		}
		
		bounding.addKeyListener(INSTANCE);
		
		bounding = null;
		bound = false;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		Integer code = e.getKeyCode();
		
		if(!pressedKeys.contains(code)) {
			pressedKeys.add(code);
		}
		
		if(recordKeySequence) { 
			keySequence += e.getKeyChar();
		}
		
		modifiersEx = e.getModifiersEx();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		pressedKeys.remove(e.getKeyCode());
		
		modifiersEx = e.getModifiersEx();
	}
	
	public static void recordKeyCharacters() {
		recordKeySequence = true;
	}
	
	public static boolean isKeyPressed(int keyCode) {
		return pressedKeys.contains(keyCode);
	}
	
	public static boolean isAnyKeyPressed() {
		return pressedKeys.size() > 0;
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
