package svk.sglubos.engine.utils;
//TODO Documentation and redesign
public class Timer {
	public static final byte DELAY_FORMAT_MILLISECS = 0x0;
	public static final byte DELAY_FORMAT_TICKS = 0x1;
	public static final byte DELAY_FORMAT_SECS = 0x2;
	
	private boolean running;
	private long timeSwitch;
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
		if(currentTime >= timeSwitch) {
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
		running = true;
	}
	
	public void startCycle() {
		numCycles = 1;
		start();
	}
	
	public void startInfiniteCycle() {
		loop = true;
		numCycles = -1;
		start();
	}
	
	public void startLoop(int cycles) {
		if(cycles < 1) {
			MessageHandler.printMessage("TIMER", "ERROR", "Number of cycles can not be less than one, invalid number: " + cycles);
			throw new IllegalArgumentException("Number of cycles can not be less than one");
		}
		this.numCycles = cycles;
		this.loop = true;
		startCycle();
	}
	
	protected void prepareTiming() {
		switch(timeFormat){
		case DELAY_FORMAT_MILLISECS:
			timeSwitch = System.currentTimeMillis() + delay;
		break;	
		case DELAY_FORMAT_TICKS:
			timeSwitch = delay;
		break;
		case DELAY_FORMAT_SECS:
			timeSwitch = System.currentTimeMillis() / 1000 + delay;
		break;	
		default:
			MessageHandler.printMessage("TIMER", MessageHandler.ERROR, "Unknown delay time format ! ");
			throw new RuntimeException("Unknown time format: " + timeFormat);
		}
	}
	
	private long getCurrentTime(){
		switch(timeFormat){
			case DELAY_FORMAT_MILLISECS:
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
		case DELAY_FORMAT_MILLISECS:
			timeSwitch = currentTime + delay;
		break;	
		case DELAY_FORMAT_TICKS:
			timeSwitch = delay;
			currentTime = 0;
		break;
		case DELAY_FORMAT_SECS:
			timeSwitch = currentTime + delay;
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
	
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		
		ret.appendClassDataBracket(getClass(), hashCode());
		ret.appendTab();
		ret.append("running =" + running, " timeSwitch = " + timeSwitch, " currentTime = " + currentTime, " delay = " + delay, " timeformat = " + timeFormat,
				" loop = " + loop, " numCycles = " + numCycles);
		ret.appendLineSeparator();
		ret.appendObjectToStringTabln("task = ", task);
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}
