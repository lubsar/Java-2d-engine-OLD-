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
 * <code>KeyBoard</code> keeps track of pressed keyboard keys, when the {@link #component component} to which is {@link #INSTANCE} of }the <code>Keyboard</code> bound is focused.<br>
 * <strong>The Keyboard can not be bound to more than one {@link java.awt.Component Component} at the same time !</strong><br>
 * To bind <code>Keyboard</code> to specific {@link java.awt.Component Component} use method {@link #bind(Component)} 
 * and to unbind from component to which mouse was bound use method {@link #unbind()}.
 * The currently pressed keys are stored in {@link java.util.ArrayList ArrayList} {@link #pressedKeys} 
 * which contains <code>keyCodes</code> of these keys.
 * You can check if a specific key is pressed by method {@link #isKeyPressed(int)}, where the parameter is <code>keyCode</code> of keyboard key.
 * The <code>keyCodes</code> for keys can be obtained from {@link java.awt.event.KeyEvent KeyEvent}.
 * Also you can check if any key is pressed by {@link #isAnyKeyPressed()} method.<br>
 * The <code>Keyboard</code> can also record the pressed key characters and store them inside of String {@link #recordedKeyChars}. 
 * The recording can be started with {@link #recordKeyCharacters()} method and to obtain the recorded value use {@link #getRecordedKeySequence()} method.
 * This method returns the {@link #recordedKeyChars} and stops the recording of characters.<br><br>
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
	/**
	 * Instance of this class used in binding and unbinding process. 
	 * Binding is simply adding {@link java.awt.event.KeyListener KeyListener} to {@link java.awt.Component Component} 
	 * which gives the <code>Keyboard</code> ability to listen for {@link java.awt.event.KeyEvent KeyEvents}.
	 * To add {@link java.awt.event.KeyListener KeyListener} to specific {@link java.awt.Component Component} use method {@link #bind(Component)}.<br>
	 * Unbinding is removing the {@link java.awt.event.KeyListener KeyListener} from the {@link java.awt.Component Component} 
	 * to which the <code>Keyboard</code> was bound before.<br>
	 * To remove {@link java.awt.event.KeyListener} use method {@link #unbind()}<br><br>
	 * 
	 * @see #bind(Component)
	 * @see #unbind()
	 * @see #component
	 */
	private static final Keyboard INSTANCE = new Keyboard();
	
	/**
	 * Reference to {@link java.awt.Component Component} to which is <code>Keyboard</code> bound.<br>
	 * To bind <code>Keyboard</code> use {@link #bind(Component)} method.
	 * The parameter of {@link #bind(Component)} method is the reference which will be stored in this field.<br>
	 * <strong>Keyboard can not be bound to more than one component at the same time !</strong>
	 * If the <code>Keyboard</code> is already bound and the method {@link #bind(Component)} is called, 
	 * the message is printed through {@link svk.sglubos.engine.utils.debug.MessageHandler MessageHandler} and nothing changes.<br>
	 * To unbind use {@link #unbind()} method which sets this reference to <code>null</code>.
	 * If the <code>Keyboard</code> was not bound and the {@link #unbind()} method is called,
	 * the message is printed through {@link svk.sglubos.engine.utils.debug.MessageHandler MessageHandler} and nothing changes.<br>
	 * 
	 * @see #bind(Component)
	 * @see #unbind()
	 */
	private static Component component;
	
	/**
	 * {@link java.util.ArrayList ArrayList} object which stores keyCodes of currently pressed keys.<br>
	 * Method {@link #isKeyPressed(int)} returns <code>true</code> if this {@link java.util.ArrayList ArrayList} contains keycode which is passed as a parameter
	 * of the {@link #isAnyKeyPressed()} method otherwise return <code>false</code>.
	 * If this {@link java.util.ArrayList ArrayList} contains some keycodes, method {@link #isAnyKeyPressed()} returns <code>true</code> 
	 * and if this {@link java.util.ArrayList ArrayList} is empty the method returns <code>false</code>.<br><br> 
	 *  
	 *  @see #isKeyPressed(int)
	 *  @see #isAnyKeyPressed()
	 */
	private static List<Integer> pressedKeys = new ArrayList<Integer>();
	
	/**
	 * <code>String</code> which contains {@link java.awt.event.KeyEvent#getKeyChar() key chars} which are being recorded when the {@link #recordKeySequence} is <code>true</code>
	 * <h1>Recording</h1>
	 * When the keyboard key is pressed, the {@link java.awt.event.KeyEvent#getKeyChar() key char} is added to this <code>String</code>.
	 * (Every character is recorded, recording of the "invisible" characters will be fixed)<br><br>
	 * To start recording characters use {@link #recordKeyCharacters()} method which sets the {@link #recordKeySequence} to <code>true</code> what
	 * causes that the recording of key chars is started.
	 * To stop recording of key chars and to obtain the recorded chars use {@link #getRecordedKeySequence()} method.<br><br>
	 *
	 * @see #recordKeyCharacters()
	 * @see #getRecordedKeySequence()
	 */
	private static String recordedKeyChars = "";
	
	/**
	 * Determines if key chars are being recorded or not.<br>
	 * If <code>true</code> the characters  of pressed key are being recorded, otherwise the characters are not being recorded.
	 * To start recording use {@link #recordKeyCharacters()}, to stop and obtain the recorded characters use {@link #getRecordedKeySequence()}.<br>
	 * 
	 * @see #recordKeyCharacters()
	 * @see #recordedKeyChars
	 * @see #getRecordedKeySequence()
	 */
	private static boolean recordKeySequence;
	
	/**
	 * Determines if <code>Keyboard</code> is bound to a {@link java.awt.Component Component}.<br>
	 * The reference of {@link java.awt.Component Component} to which is <code>Keyboard</code> bound is stored in {@link #component} object.<br>
	 * To bind <code>Keyboard</code> to a {@link java.awt.Component Component} use method {@link #bind(Component)}.
	 * If the <code>Keyboard</code> is already bound, the message is printed by the {@link svk.sglubos.engine.utils.debug.MessageHandler MessageHandler} and nothing changes.<br>
	 * To unbind the <code>Keyboard</code> from a {@link java.awt.Component Component} use method{@link #unbind()}.
	 * If the <code>Keyboard</code> was not bound, the Message is printed and nothing changes.<br>
	 * 
	 * @see #bind(Component)
	 * @see #unbind()
	 * @see #component
	 */
	private static boolean bound;
	
	private static int modifiersEx;
	
	/**
	 * Binds <code>Keyboard</code> to the specified {@link java.awt.Component Component} which gives <code>Keyboard</code> ability to listen to key events -  
	 *{@link java.awt.event.KeyEvent KeyEvent}}.<br> 
	 * To unbind <code>Keyboard</code> use {@link #unbind()} method.
	 * 
	 * <h1>Binding</h1>
	 * if the <code>Keyboard</code> is already bound info message is printed by {@link svk.sglubos.engine.utils.debug.MessageHandler MessageHandler} 
	 *  and return statement is called. And if bound is <code>false</code> the binding is processed.
	 * To the specified {@link java.awt.Component Component} is added {@link #INSTANCE instance of this class} as a {@link java.awt.event.KeyListener KeyListener}, 
	 * Reference to the specified {@link java.awt.Component Component} is stored to {@link #component}.
	 * And the {@link #bound} is set to <code>true</code><br><br>
	 * 
	 * @param component component to which will be <code>Mouse</code> bound to<br><br>
	 * 
	 * @see #unbind()		
	 * @see #buttons
	 * 
	 * @see java.awt.Component
	 * @see java.awt.event.KeyListener
	 */
	public static void bind(Component compo) {
		if(bound) {
			MessageHandler.printMessage("KEYBOARD","INFO", "Keyboard is already bound to: " + component.toString());
			return;
		}
		
		compo.addKeyListener(INSTANCE);
		component = compo;
		
		bound = true;
	}
	
	/**
	 * Unbinds <code>Keyboard</code> from {@link #component component} to which the <code>Keyboard</code> was bound by {@link #bind(Component)} method 
	 * which removes <code>Keyboard</code> ability to listen to key events such as {@link java.awt.event.KeyEvent KeyEvent}.<br>
	 * <h1>Unbinding</h1>
	 * If the <code>Keyboard</code> was not bound (the {@link #bound} is <code>false</code>), the message is printed through {@link svk.sglubos.engine.utils.debug.MessageHandler MessageHandler} and return statement is called.
	 * And if the {@link #bound} is <code>true</code> the unbinding is processed. From the {@link #component Component } to which <code>Keyboard</code> was bound is {@link #INSTANCE} removed as the {@link java.awt.event.KeyListener KeyListener}, 
	 * The {@link #component reference} to the {@link java.awt.Component Component} to which the <code>Keyboard</code> was bound is set to <code>null</code>.
	 * And the {@link #bound} is set to <code>false</code>.<br><br> 
	 * 
	 * @see #bind()		
	 * @see #bound
	 * 
	 * @see java.awt.Component
	 * @see java.awt.event.KeyListener
	 */
	public static void unbind() {
		if(!bound) {
			MessageHandler.printMessage("KEYBOARD","INFO", "Keyboard was not bound to any component");
			return;
		}
		
		component.addKeyListener(INSTANCE);
		
		component = null;
		bound = false;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		Integer code = e.getKeyCode();
		
		if(!pressedKeys.contains(code)) {
			pressedKeys.add(code);
		}
		
		if(recordKeySequence) { 
			recordedKeyChars += e.getKeyChar();
		}
		
		modifiersEx = e.getModifiersEx();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		pressedKeys.remove((Integer)e.getKeyCode());
		
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
		String temp = recordedKeyChars;
		recordedKeyChars = "";
		
		return temp;
	}
	
	public static int getModifiersEx() {
		return modifiersEx;
	}
}
