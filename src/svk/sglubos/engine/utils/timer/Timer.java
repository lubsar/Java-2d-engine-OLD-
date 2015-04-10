package svk.sglubos.engine.utils.timer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import svk.sglubos.engine.utils.debug.MessageHandler;
//TODO toString, document
public class Timer {
	public static final byte TIME_FORMAT_MILLISECONDS = 1;
	public static final byte TIME_FORMAT_TICKS = 2;
	
	private static boolean initialized = false;
	
	private static double msPTick;
	private static List<TimerTask> tasks = new ArrayList<TimerTask>();
	
	public static void init(int updatesPerSecond) {
		Timer.msPTick = 1000 / updatesPerSecond;
		initialized = true;
	}
	
	public static void addTask(TimerTask task) {
		 tasks.add(task);
	}
	
	public static void removeTask(TimerTask task) {
		tasks.remove(task);
	}
	
	public static void update() {
		if(!initialized) {
			MessageHandler.printMessage("TIMER", MessageHandler.ERROR, "Timer is not initialized !");
			throw new IllegalStateException("Timer is not initialized !");
		}
		
		Iterator<TimerTask> iter = new ArrayList<TimerTask>(tasks).iterator();
		
		while(iter.hasNext()) {
			TimerTask task = iter.next();
			
			if(task.isDone()) {
				tasks.remove(task);
			} else {
				switch(task.getTimeFormat()){
				case TIME_FORMAT_MILLISECONDS:
					task.update(msPTick);
					break;	
				case TIME_FORMAT_TICKS: 
					task.update(1);
					break;	
				default:
					MessageHandler.printMessage("TIMER", MessageHandler.ERROR, "Unknown time format:" + task.getTimeFormat() + "of task: " + task.toString());
					throw new RuntimeException("Unknown time format:" + task.getTimeFormat() + "of task: " + task.toString());
				}
			}
		}
	}
}
