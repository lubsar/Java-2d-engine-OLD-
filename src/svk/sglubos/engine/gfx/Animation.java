package svk.sglubos.engine.gfx;

import svk.sglubos.engine.utils.MessageHandler;
import svk.sglubos.engine.utils.Timer;
import svk.sglubos.engine.utils.TimerTask;

//TODO Documentation

public abstract class Animation {
	protected long frameDelay;
	protected byte timeFormat;
	protected int frames;
	
	protected boolean loop;
	protected boolean running;
	protected boolean reverse;
	
	protected int currentFrame;
	protected int startFrame;
	protected int endFrame;
	
	protected Timer timer;
	protected TimerTask t = () -> updateFrame();
	
	public Animation(long frameDelay, byte timeFormat, int frames) {
		this(frameDelay, 0, frames - 1, frames, timeFormat);
	}
	
	public Animation(long frameDelay, int startFrame, int endFrame, int frames, byte timeFormat) {
		this.frameDelay = frameDelay;
		this.timeFormat = timeFormat;
		this.frames = frames;
		
		setStartFrame(startFrame);
		setEndFrame(endFrame);
		timer = new Timer(t, timeFormat, frameDelay);
	}
	
	public void startReverse(boolean loop){
		if(loop) {
			timer.startInfiniteLoop();
		} else {
			timer.start();
		}
		
		currentFrame = endFrame;
		running = true;
		reverse = true;
		this.loop = loop;
	}
	
	public void start(boolean loop) {
		if(loop) {
			timer.startInfiniteLoop();
		} else {
			timer.startLoop(endFrame - startFrame);
		}
		
		currentFrame = startFrame;
		running = true;
		reverse = false;
		this.loop = loop;
	}
	
	public void stop() {
		timer.stop();
		running = false;
	}
	
	public void tick() {
		if(running){
			timer.update();
		}
	}
	
	protected void updateFrame() {
		if(reverse){
			currentFrame--;
		}else{
			currentFrame++;			
		}
		
		if(currentFrame < startFrame) {
			if(loop){
				currentFrame = endFrame;							
			}else {
				currentFrame = endFrame;
				stop();
			}
		} else if (currentFrame == endFrame) {
			if(loop){
				currentFrame = startFrame;							
			}else {
				currentFrame = startFrame;
				stop();
			}
		}
	}
	
	public void setFrameDelay(long frameDelay) {
		this.frameDelay = frameDelay;
		timer.setDelay(frameDelay);
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
	
	public void setStartFrame(int start) {
		if(start < 0 || start >= frames) {
			MessageHandler.printMessage("ANIMATION", MessageHandler.ERROR, "Illegal starting frame, frame cannot be less than zero and more than frames -1: " + start);
			throw new IllegalArgumentException("Illegal starting frame: " + start);
		}
		
		this.startFrame = start;
	}
	
	public void setEndFrame(int end) {
		if(end < 0 || end >= frames) {
			MessageHandler.printMessage("ANIMATION", MessageHandler.ERROR, "Illegal ending frame, frame cannot be less than zero and more than frames -1: " + end);
			throw new IllegalArgumentException("Illegal ending frame: " + end);
		}
		
		this.endFrame = end;
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
