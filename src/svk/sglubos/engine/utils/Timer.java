package svk.sglubos.engine.utils;
//TODO Documentation
public class Timer {
	public static final byte DELAY_FORMAT_MILISECS = 0x0;
	public static final byte DELAY_FORMAT_TICKS = 0x1;
	public static final byte DELAY_FORMAT_SECS = 0x2;
	
	private boolean running;
	private long switchTime;
	private long currentTime;
	private long delay;
	private byte timeFormat;
	
	private boolean loop;
	private int numCycles;
	
	private TimerTask task;
	
	public Timer(TimerTask task, byte timeFormat) {
		this.task = task;
		this.timeFormat = timeFormat;
	}
	
	public Timer(TimerTask task, byte timeFormat, long delay) {
		this(task, timeFormat);
		this.delay = delay;
	}
	
	public void update() {
		if(!running)
			return;
					
		currentTime = getCurrentTime();
		if(currentTime >= switchTime) {
			task.timeSwitch();
			
			if(numCycles > -1 ){
				numCycles--;				
			}
			
			if(loop && numCycles != 0 || numCycles != -1) {
				updateSwitchTime();
				return;
			}

			stop();
		}
	}
	
	public void stop() {
		running = false;
	}
	
	public void start() {
		prepareTiming();
		numCycles = 1;
		running = true;
	}
	
	public void startInfiniteLoop() {
		this.loop = true;
		this.numCycles = -1;
		start();
	}
	
	public void startLoop(int cycles) {
		if(cycles < 1) {
			MessageHandler.printMessage("TIMER", "ERROR", "Number of cycles can not be less than one, invalid number: " + cycles);
			throw new IllegalArgumentException("Number of cycles can not be less than one");
		}
		this.numCycles = cycles;
		this.loop = true;
		start();
	}
	
	protected void prepareTiming() {
		switch(timeFormat){
		case DELAY_FORMAT_MILISECS:
			switchTime = System.currentTimeMillis() + delay;
		break;	
		case DELAY_FORMAT_TICKS:
			switchTime = delay;
		break;
		case DELAY_FORMAT_SECS:
			switchTime = System.currentTimeMillis() / 1000 + delay;
		break;	
		default:
			MessageHandler.printMessage("TIMER", MessageHandler.ERROR, "Unknown delay time format ! ");
			throw new RuntimeException("Unknown time format: " + timeFormat);
		}
	}
	
	private long getCurrentTime(){
		switch(timeFormat){
			case DELAY_FORMAT_MILISECS:
				return System.currentTimeMillis();
			case DELAY_FORMAT_TICKS:
				return ++currentTime;
			case DELAY_FORMAT_SECS:
				return (System.currentTimeMillis() / 1000);
			default:
				MessageHandler.printMessage("TIMER", MessageHandler.ERROR, "Unknown delay time format ! ");
				throw new RuntimeException("Unknown time format: " + timeFormat);
		}
	}
	
	private void updateSwitchTime() {
		switch(timeFormat){
		case DELAY_FORMAT_MILISECS:
			switchTime = currentTime + delay;
		break;	
		case DELAY_FORMAT_TICKS:
			switchTime = delay;
			currentTime = 0;
		break;
		case DELAY_FORMAT_SECS:
			switchTime = currentTime + delay;
		break;
		default:
			MessageHandler.printMessage("TIMER", MessageHandler.ERROR, "Unknown delay time format ! ");
			throw new RuntimeException("Unknown time format" + timeFormat);
		}
	}
	
	public void setDelay(long delay) {
		this.delay = delay;
		if(running) {
			prepareTiming();
		}
	}
	
	public long getDelay() {
		return delay;
	}
	
	public void setTimeFormat(byte timeFormat) {
		this.timeFormat = timeFormat;
	}
	
	public byte getTimeFormat() {
		return timeFormat;
	}
	
	public boolean isRunning() {
		return running;
	}
}
