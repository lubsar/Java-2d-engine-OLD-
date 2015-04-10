package svk.sglubos.engine.utils.timer;

public class LoopTimerTask extends TimerTask {
	private int cycles;
	
	public LoopTimerTask(byte timeFormat, double delay, int cycles, TimerCallback callback) {
		super(timeFormat, delay, callback);
		this.nextCallback = delay;
		this.cycles = cycles;
	}

	@Override
	public void update(double time) {
		nextCallback -= time;
		if(nextCallback <= 0) {
			callback.callback();
			
			if(cycles == -1) {
				nextCallback = delay;
				return;
			} else if (cycles == 0) {
				done = true;
			} else {
				cycles--;
				nextCallback = delay;
			}
		}
	}
}
