package svk.sglubos.engine.utils.timer;
//TODO toString, document
public class BasicTimerTask extends TimerTask {
	public BasicTimerTask(byte timeFormat, double delay, TimerCallback callback) {
		super(timeFormat, delay, callback);
		nextCallback = delay;
	}
	
	@Override
	public void update(double time) {
		nextCallback -= time;
		if(nextCallback <= 0) {
			callback.callback();
			done = true;
		}
	}
}
