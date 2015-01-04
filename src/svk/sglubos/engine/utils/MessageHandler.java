package svk.sglubos.engine.utils;
/**
 * Handles printing of messages to console. <br>
 * Message has specified tag, which indicates if message is {@link #WARNING warning}, {@link #INFO information}, {@link #ERROR error} or you can specify own tag.<br>
 * Tag is displayed in square brackets. <br>
 * Messages start with prefix. Default tag is "ENGINE", but you can also specify prefix.<br>
 * Prefix is on start of printed message. 
 * 
 * @see {@link #printMessage(String, String) printMessage(tag ,message)}
 * @see {@link #printMessage(String,String, String) printMessage(prefix, tag, message)}
 */
public class MessageHandler {
	/**
	 * Message tag with value: "WARNING"
	 * 
	 * @see {@link #printMessage(String, String)}
	 */
	public static final String WARNING = "WARNING";
	
	/**
	 * Message tag with value: "INFO"
	 * 
	 * @see {@link #printMessage(String, String)}
	 */
	public static final String INFO = "INFO";
	
	/**
	 * Message tag with value: "ERROR" if used, message is printed as an error.
	 * 
	 * @see {@link #printMessage(String, String)}
	 */
	public static final String ERROR = "ERROR";
	
	/**
	 * Prints specified message with default prefix: "ENGINE" and specified tag. <br>
	 * Uses {@link #printMessage(String, String, String) printMessage("ENGINE", tag, message)} method.
	 *  
	 * @param tag message tag
	 * @param message message text
	 * <p>
	 * @see {@link #printMessage(String, String, String)}
	 */
	public static void printMessage(String tag, String message){
		printMessage("ENGINE", tag, message);
	}
	
	/**
	 * Prints specified message with specified prefix and tag. <br>
	 * Uses {@link System#out}'s println(String) method and if you use {@link #ERROR} tag, message is printed by: {@link System#err}`s println(String)} <br>
	 * 
	 * <h1> Message structure:</h1>
	 * prefix: [tag] message
	 * <p>
	 * @param prefix message prefix
	 * @param tag message tag
	 * @param message message text
	 * <p>
	 * @see {@link #printMessage(String, String, String)}
	 */
	public static void printMessage(String prefix, String tag, String message){
		if(tag.equals(ERROR)){
			System.err.println(prefix + ": [" +tag + "] " + message );
			return;
		}
		System.out.println(prefix + ": [" +tag + "] " + message );
	}
}
