package svk.sglubos.engine.utils.timer;
//TODO toString, document
public abstract class TimerTask {
	protected boolean done;
	protected double nextCallback;
	protected byte timeFormat;
	protected double delay;
	protected TimerCallback callback;
	
	public TimerTask(byte timeFormat, double delay, TimerCallback callback) {
		this.timeFormat = timeFormat;
		this.delay = delay;
		this.callback = callback;
	}
	
	public abstract void update(double time); 
	
	public byte getTimeFormat() {
		return timeFormat;
	}
	
	public boolean isDone() {
		return done;
	}
}
