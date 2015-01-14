package svk.sglubos.engine.gfx;

import svk.sglubos.engine.utils.Timer;
import svk.sglubos.engine.utils.TimerTask;

//TODO Documentation create separate timer class

public abstract class Animation {
	protected long frameDelay;
	protected byte timeFormat;
	protected int frames;
	
	protected boolean loop;
	protected boolean running;
	protected boolean reverse;
	
	protected int currentFrame;
	
	protected Timer timer;
	protected TimerTask t = () -> updateFrame();
	
	public Animation(long frameDelay, byte timeFormat, int frames) {
		this.frameDelay = frameDelay;
		this.timeFormat = timeFormat;
		this.frames = frames;
		timer = new Timer(t, timeFormat, frameDelay);
	}

	public void startReverse(boolean loop){
		if(loop) {
			timer.startInfiniteLoop();
		} else {
			timer.start();
		}
		currentFrame = --frames;
		running = true;
		reverse = true;
		this.loop = loop;
	}
	
	public void start(boolean loop) {
		if(loop) {
			timer.startInfiniteLoop();
		} else {
			timer.startLoop(frames);
		}
		currentFrame = 0;
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
