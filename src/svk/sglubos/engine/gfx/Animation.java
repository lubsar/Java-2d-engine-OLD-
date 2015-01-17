package svk.sglubos.engine.gfx;

import svk.sglubos.engine.utils.MessageHandler;
import svk.sglubos.engine.utils.Timer;
import svk.sglubos.engine.utils.TimerTask;

//TODO documentation

/**
 * Provides abilities to create basic animations.<br>
 * This class handles timing, starting and stopping, and also provides reverse, but not rendering. 
 * Rendering have to be created manually.<br>
 * <p>
 * Timing is based on {@link svk.sglubos.engine.utils.Timer Timer} class. Every time when {@link #timer timer} finishes loop after specified {@link #frameDelay time} (delay), 
 * {@link #updateFrame()} method is called.
 * The {@link #updateFrame()} method switches to next frame of animation. Switching to next frame means to increase,
 * or decrease (if reverse decrease) value of {@link #currentFrame} by one.
 * {@link #updateFrame() Update frame} method also keeps {@link #currentFrame} value in bounds of {@link #startFrame} and {@link #endFrame}.
 * <strong>But keep in mind, that {@link #startFrame} < {@link #endFrame}.</strong>
 * <p>
 * To play animation from {@link #startFrame} to {@link #endFrame} use {@link #start(boolean)} method 
 * and to play from {@link #endFrame} to {@link #startFrame} use {@link #startReverse(boolean)} method. A boolean argument in these methods determines, 
 * if animation will be played once, or repeatedly played until {@link #stop()} method is called. If called {@link #start(boolean)} with argument <code>false</code>,
 * animation will stop when the value {@link #currentFrame} reaches value of {@link #endFrame}, and if called with argument <code>true</code>, 
 * {@link #currentFrame} will be set to {@link #startFrame} when {@link #currentFrame} reaches {@link #endFrame}. 
 * This causes that animation will not stop until {@link #stop()} method is manually called.
 * In case of {@link #startReverse(boolean)} method it is the same, but when the argument is <code>true</code>, 
 * {@link #currentFrame} will be set to {@link #endFrame} when {@link #currentFrame} reaches {@link #startFrame}.<br>
 * 
 * @see #Animation(long, byte, int) basic constructor
 * @see #Animation(long, int, int, int, byte) constructor with ability to set startFrame and endFrame
 * @see #start(boolean) 
 * @see #stop() 
 * @see svk.sglubos.engine.utils.Timer
 */
public abstract class Animation {
	/**
	 * Delay between frame switch of animation.<br>
	 * This variable is initialized in {@link}
	 * The delay can be set by {@link #setFrameDelay(long)} method.
	 * A {@link #delayFormat delay format} (unit) is also initialized in {@link #Animation(long, byte, int) constructor}. Avaible units are all units in {@link svk.sglubos.engine.utils.Timer Timer}: {@link svk.sglubos.engine.utils.Timer#DELAY_FORMAT_MILISECS milliseconds}, {@link svk.sglubos.engine.utils.Timer#DELAY_FORMAT_SECS seconds} and {@link svk.sglubos.engine.utils.Timer#DELAY_FORMAT_TICKS ticks}.
	 * The delay format can be also set by {@link #setFrameDelay(long)} method, but the frame delay will not be converted to that specific unit, because the conversion is not implemented.
	 * 
	 * @see #Animation(long, byte, int) constructor
	 * @see #svk.sglubos.engine.utils.Timer
	 * @see #setFrameDelay(long)
	 * @see #getFrameDelay()
	 * @see #getTimeFormat()
	 * @see #setTimeFormat(byte)
	 */
	protected long frameDelay;
	
	
	protected byte delayFormat;
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
		this.delayFormat = timeFormat;
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
		
		if(currentFrame == startFrame) {
			if(loop){
				currentFrame = endFrame;							
			}else {
				stop();
			}
		} else if (currentFrame == endFrame) {
			if(loop){
				currentFrame = startFrame;							
			}else {
				stop();
			}
		}
	}
	
	public void setFrameDelay(long frameDelay) {
		if(frameDelay < 0) {
			MessageHandler.printMessage("ANIMATION", MessageHandler.ERROR, "Invalind animation frame delay, delay can not be less than zero " + frameDelay);
			throw new IllegalArgumentException("Frame delay can not be less than 0: " + frameDelay);
		}
		this.frameDelay = frameDelay;
		timer.setDelay(frameDelay);
	}
	
	public long getFrameDelay() {
		return frameDelay;
	}
	
	public void setTimeFormat(byte timeFormat) {
		this.delayFormat = timeFormat;
	}
	
	public byte getTimeFormat() {
		return delayFormat;
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
