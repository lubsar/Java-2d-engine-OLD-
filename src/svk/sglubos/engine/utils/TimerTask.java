package svk.sglubos.engine.utils;
/**
 * TimerTask is an function interface which is used in creation {@link svk.sglubos.engine.utils.Timer Timer}.<br>
 * This class contains only one method, {@link #timeSwitch()} which is called when {@link svk.sglubos.engine.utils.Timer Timer} finishes its cycle.<br>
 * 
 * @see svk.sglubos.engine.utils.Timer
 * @see #timeSwitch()
 */
@FunctionalInterface
public interface TimerTask {
	
	
	/**
	 * This method is called when {@link svk.sglubos.engine.utils.Timer} completes its cycle.<br>
	 * 
	 * @see svk.sglubos.engine.utils.Timer
	 */
	public void timeSwitch();
}
