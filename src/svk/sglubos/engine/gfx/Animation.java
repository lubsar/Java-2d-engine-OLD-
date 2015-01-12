package svk.sglubos.engine.gfx;

import svk.sglubos.engine.gfx.Screen;
import svk.sglubos.engine.utils.MessageHandler;

//TODO Documentation create separate timer class

public abstract class Animation {
	public static final byte DELAY_FORMAT_MILISECS = 0x0;
	public static final byte DELAY_FORMAT_TICKS = 0x1;
	public static final byte DELAY_FORMAT_SECS = 0x2;
	
	protected long frameDelay;
	protected byte timeFormat;
	protected int frames;
	
	protected boolean loop;
	protected boolean running;
	protected boolean reverse;
	
	protected int currentFrame;	
	protected long nextFrameSwitch;
	protected long currentTime;
	
	public Animation(long frameDelayInMilisecs, byte timeFormat, int frames) {
		this.frameDelay = frameDelayInMilisecs;
		this.timeFormat = timeFormat;
		this.frames = frames;
	}

	public void startReverse(boolean loop){
		prepareTiming();
		currentFrame = --frames;
		running = true;
		reverse = true;
		this.loop = loop;
	}
	
	public void start(boolean loop) {
		prepareTiming();
		currentFrame = 0;
		running = true;
		reverse = false;
		this.loop = loop;
	}
	
	public void stop() {
		running = false;
	}
	
	public void tick() {
		if(!running){
			return;
		}
		checkTime();
	}
	
	protected void checkTime() {
		currentTime = getCurrentTime();
		if(currentTime >= nextFrameSwitch) {
			updateTime();
			updateFrame();
		}
	}
	
	protected void prepareTiming() {
		switch(timeFormat){
		case DELAY_FORMAT_MILISECS:
			nextFrameSwitch = System.currentTimeMillis() + frameDelay;
		break;	
		case DELAY_FORMAT_TICKS:
			nextFrameSwitch = frameDelay;
		break;
		case DELAY_FORMAT_SECS:
			nextFrameSwitch = System.currentTimeMillis() / 1000 + frameDelay;
		break;	
		default:
			MessageHandler.printMessage("ANIMATION", MessageHandler.ERROR, "Unknown animation delay time format ! ");
			throw new RuntimeException("Unknown time format: " + timeFormat);
		}
	}
	
	protected long getCurrentTime(){
		switch(timeFormat){
			case DELAY_FORMAT_MILISECS:
				return System.currentTimeMillis();
			case DELAY_FORMAT_TICKS:
				return ++currentTime;
			case DELAY_FORMAT_SECS:
				return (System.currentTimeMillis() / 1000);
			default:
				MessageHandler.printMessage("ANIMATION", MessageHandler.ERROR, "Unknown animation delay time format ! ");
				throw new RuntimeException("Unknown time format: " + timeFormat);
		}
	}
	
	protected void updateTime() {
		switch(timeFormat){
		case DELAY_FORMAT_MILISECS:
			nextFrameSwitch = currentTime + frameDelay;
		break;	
		case DELAY_FORMAT_TICKS:
			nextFrameSwitch = frameDelay;
			currentTime = 0;
		break;
		case DELAY_FORMAT_SECS:
			nextFrameSwitch = currentTime + frameDelay;
		break;
		default:
			MessageHandler.printMessage("ANIMATION", MessageHandler.ERROR, "Unknown animation delay time format ! ");
			throw new RuntimeException("Unknown time format" + timeFormat);
		}
	}
	
	protected void updateFrame() {
		if(reverse){
			currentFrame--;
		}else{
			currentFrame++;			
		}
		if(currentFrame < 0) {
			if(loop){
				currentFrame = frames;							
			}else {
				currentFrame = frames;
				stop();
			}
		} else if (currentFrame == frames) {
			if(loop){
				currentFrame = 0;							
			}else {
				currentFrame = 0;
				stop();
			}
		}
	}
	
	public void setFrameDelay(long frameDelay) {
		this.frameDelay = frameDelay;
		prepareTiming();
	}
	
	public long getFrameDelay() {
		return frameDelay;
	}
	
	public void setTimeFormat(byte timeFormat) {
		this.timeFormat = timeFormat;
	}
	
	public byte getTimeFormat() {
		return timeFormat;
	}
	
	public void setCurrentFrame(int frame){
		if(frame < 0 || frame > this.frames) {
			throw new IllegalArgumentException("Frame can not be set to value less than zero and more than the acutual animation frame array length: " + frame);
		}
		this.currentFrame = frame;
	}
	
	public int getCurrentFrame(){
		return currentFrame;
	}
	
	public abstract void render(Screen screen, int x, int y);
}
