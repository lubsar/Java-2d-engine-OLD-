/*
 *	Copyright 2015 ¼ubomír Hlavko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package svk.sglubos.engine.gfx.animation;

import svk.sglubos.engine.gfx.Screen;
import svk.sglubos.engine.utils.debug.DebugStringBuilder;
import svk.sglubos.engine.utils.debug.MessageHandler;
import svk.sglubos.engine.utils.timer.LoopTimerTask;
import svk.sglubos.engine.utils.timer.Timer;
import svk.sglubos.engine.utils.timer.TimerCallback;
import svk.sglubos.engine.utils.timer.TimerTask;

//TODO document - new Timer
/**
 * Provides abilities to create basic animations.
 * <p>
 * This class handles timing, starting and stopping, and also provides reverse, but not specific rendering. 
 * Rendering should be created manually using {@link #render(Screen, int, int)} method.
 * <p>
 * Timing is based on {@link svk.sglubos.engine.utils.Timer Timer} class.
 * Every time when {@link #timer timer} finishes loop after specified {@link #frameDelay time} (delay), 
 * {@link #switchFrame()} method is called.
 * The {@link #switchFrame()} method switches to next frame of animation. Switching to next frame means to increase,
 * or decrease (if reverse decrease) value of {@link #currentFrame} by one.
 * {@link #switchFrame() Update frame} method also keeps {@link #currentFrame} value in bounds of {@link #startFrame} and {@link #endFrame}.
 * <strong>But keep in mind, that {@link #startFrame} < {@link #endFrame}.</strong>
 * <p>
 * To play animation from {@link #startFrame} to {@link #endFrame} use {@link #start(boolean)} method 
 * and to play from {@link #endFrame} to {@link #startFrame} use {@link #startReverse(boolean)} method. A boolean argument in these methods determines, 
 * if animation will be played once, or repeatedly played until {@link #stop()} method is called. If called {@link #start(boolean)} with argument <code>false</code>,
 * animation will stop when the value {@link #currentFrame} reaches value of {@link #endFrame}, and if called with argument <code>true</code>, 
 * {@link #currentFrame} will be set to {@link #startFrame} when {@link #currentFrame} reaches {@link #endFrame}. 
 * This causes that animation will not stop until {@link #stop()} method is manually called.
 * In case of {@link #startReverse(boolean)} method it is the same, but when the argument is <code>true</code>, 
 * {@link #currentFrame} will be set to {@link #endFrame} when {@link #currentFrame} reaches {@link #startFrame}.
 * 
 * @see #Animation(long, byte, int) basic constructor
 * @see #Animation(long, int, int, int, byte) constructor with ability to set startFrame and endFrame
 * @see #start(boolean) 
 * @see #stop() 
 * @see svk.sglubos.engine.utils.Timer
 */
public abstract class Animation {
	/**
	 * Delay between frame switches of animation.
	 * <p>
	 * This variable is initialized in {@link #Animation(long, byte, int) constructor}.
	 * The delay can be set by {@link #setFrameDelay(long)} method.
	 * A {@link #delayFormat delay format} (unit) is also initialized in {@link #Animation(long, byte, int) constructor}. Avaible units are all units in {@link svk.sglubos.engine.utils.Timer Timer}: 
	 * {@link svk.sglubos.engine.utils.Timer#DELAY_FORMAT_MILLISECS milliseconds}, {@link svk.sglubos.engine.utils.Timer#DELAY_FORMAT_SECS seconds} 
	 * and {@link svk.sglubos.engine.utils.Timer#DELAY_FORMAT_TICKS ticks}.
	 * The delay format can be also set by {@link #setFrameDelay(long)} method, but the frame delay will not be converted to that specific unit, 
	 * because the conversion is not implemented.
	 * 
	 * @see #Animation(long, byte, int) constructor
	 * @see #svk.sglubos.engine.utils.Timer
	 * @see #setFrameDelay(long)
	 * @see #getFrameDelay()
	 * @see #getDelayFormat()
	 * @see #setDelayFormat(byte)
	 */
	protected double frameDelay;
	
	/**
	 * Format of {@link #frameDelay delay} between frame switches.
	 * <p>
	 * The <code>delayFormat</code> represents the unit of {@link #frameDelay delay} between frame switches. Supported time formats are all formats from {@link svk.sglubos.engine.utils.Timer Timer}.
	 * <p>
	 * <h1>Supported time formats:</h1>
	 * {@link svk.sglubos.engine.utils.Timer#DELAY_FORMAT_SECS DELAY_FORMAT_SECS}<br>
	 * {@link svk.sglubos.engine.utils.Timer#DELAY_FORMAT_TICKS DELAY_FORMAT_TICKS}<br>
	 * {@link svk.sglubos.engine.utils.Timer#DELAY_FORMAT_MILLISECS DELAY_FORMAT_MILLISECS}
	 * <p> 
	 * This variable is initialized in {@link #Animation(long, byte, int) constructor} and can be set by {@link #setDelayFormat(byte)} method.
	 * <strong>Changing of time format does not convert delay value to that specific format.</strong><br>
	 * To obtain value of this variable use {@link #getDelayFormat()} method.
	 * 
	 * @see #frameDelay
	 * @see #setFrameDelay(long)
	 * @see #getFrameDelay()
	 * @see svk.sglubos.engine.utils.Timer
	 */
	protected byte delayFormat;
	
	/**
	 * Determines number of frames, first frame is <code>0</code> and last frame is <code>frames - 1</code>.
	 * <p>
	 * This variable is initialized in {@link #Animation(long, byte, int) constructor}.
	 * 
	 * @see #Animation(long, byte, int)
	 * @see #setStartFrame(int)
	 * @see #setEndFrame(int)
	 */
	protected int frames;
	
	/**
	 * Determines if animation will be played once or played continuously until {@link #stop()} method will be called.
	 * <p>
	 * If <code>true</code> animation will be player continuously and if <code>false</code>, animation will be played only once.<br>
	 * This variable is set in {@link #start(boolean)} and {@link #startReverse(boolean)} method to value passed as an argument in these methods.
	 * 
	 * @see #start(boolean)
	 * @see #st(boolean)
	 * @see #startReverse(boolean)
	 * @see #stop()
	 */
	protected boolean loop;
	
	/**
	 * Determines if animation is currently running (that frames are being updated).
	 * <p>
	 * This variable is set in {@link #start(boolean)}, {@link #startReverse(boolean)} and {@link #stop()} methods.<br>
	 * The {@link #start(boolean)} and {@link #startReverse(boolean)} methods set this variable to <code>true</code>,
	 * what causes that the {@link #timer} is updated every time {@link #tick()} method is called.<br>
	 * The {@link #stop()} method sets this variable to <code>false</code>, 
	 * {@link #timer} will not be updated event if {@link #tick()} method is called.
	 * 
	 * @see #tick()
	 * @see #timer()
	 * @see #start(boolean)
	 * @see #startReverse(boolean)
	 * @see #stop()
	 */
	protected boolean running;
	
	/**
	 * Determines if animation frames are played from {@link #startFrame} to {@link #endFrame} or from {@link #endFrame} to {@link #startFrame}.
	 * <p>
	 * This variable is set in {@link #start(boolean)} and {@link #startReverse(boolean)} methods.<br>
	 * <p>
	 * The {@link #start(boolean)} method sets this variable to <code>false</code>,
	 * what causes that the frames are played from {@link #startFrame} to {@link #endFrame}, 
	 * so the {@link #currentFrame} is increased by 1 every time when {@link #switchFrame()} method is called.<br>
	 * The {@link #startReverse(boolean)} method sets this variable to <code>true</code>,
	 * what causes that the frames are played from {@link #endFrame} to {@link #startFrame}, 
	 * so the {@link #currentFrame} is decreased by 1 every time when {@link #switchFrame()} method is called.
	 * 
	 * @see #currentFrame
	 * @see #startFrame
	 * @see #endFrame
	 * @see #start(boolean)
	 * @see #startReverse(boolean)
	 * @see #switchFrame()
	 */
	protected boolean reverse;
	
	/**
	 * This variable stores the current frame of animation.
	 * <p>
	 * This variable is updated in {@link #switchFrame()} method and can be obtained by {@link #getCurrentFrame()}.
	 * <p>
	 * The value of this variable is kept between {@link #startFrame} and {@link #endFrame}, 
	 * so the first frame of animation is {@link #startFrame} and the last frame is {@link #endFrame}.<br>
	 * This variable is increased or decreased by <code>1</code>, depended on {@link #reverse}.
	 * If reverse is <code>true</code> the value is decreased and if <code>false</code> the value is increased.
	 * The reverse is set in {@link #start(boolean)} to false and in {@link #startReverse(boolean)} to true.
	 * 
	 * @see #reverse
	 * @see #startFrame
	 * @see #endFrame
	 * @see #switchFrame()
	 * @see #getCurrentFrame()
	 * @see #start(boolean)
	 * @see #startReverse(boolean)
	 */
	protected int currentFrame;
	
	/**
	 * The first frame of animation.
	 * <p>
	 * This variable is initialized in {@link #Animation(long, int, int, int, byte) constructor} 
	 * and if used {@link #Animation(long, byte, int)} the value is set to <code>0</code>.
	 * This variable can be set by {@link #setStartFrame(int)} method, but the value have to be positive,
	 * lower than {@link #endFrame} and not bigger than {@link #frames}-1.
	 * <p>
	 * This is the lowest value of {@link #currentFrame}, so if {@link #start(boolean)} method called with argument <code>false</code>,
	 * the animation starts at this frame and if {@link #currentFrame} reaches value of {@link #endFrame} the animation will stop,
	 * but if called with argument <code>true</code>, after {@link #currentFrame} reaches the {@link #endFrame},
	 * the animation than starts again from this frame.
	 * <p>
	 * If called {@link #startReverse(boolean)} with argument <code>false</code>, the animation starts at {@link #endFrame}
	 * and if {@link #currentFrame} reaches this value the animation will stop, but if called with argument<code>true</code>, 
	 * after {@link #currentFrame} reaches this value the animation than starts again from {@link #endFrame}.
	 * 
	 * @see #Animation(long, byte, int) constructor (sets to 0)
	 * @see #Animation(long, int, int, int, byte) constuctor (sets to specified value)
	 * @see #switchFrame()
	 * @see #setStartFrame(int)
	 * @see #endFrame
	 * @see #currentFrame
	 */
	protected int startFrame;
	
	/**
	 * The last frame of animation.
	 * <p>
	 * This variable is initialized in {@link #Animation(long, int, int, int, byte) constructor} 
	 * and if used {@link #Animation(long, byte, int)} the value is set to <code>{@link #frames}-1</code>.
	 * This variable can be set by {@link #setEndFrame(int)} method, but the value have to be positive,
	 * bigger than {@link #startFrame} but not bigger than {@link #frames}-1.
	 * <p>
	 * This is the highest value of {@link #currentFrame}, so if {@link #start(boolean)} method called with argument <code>false</code>,
	 * the animation starts at {@link #startFrame} frame and if {@link #currentFrame} reaches this value the animation will stop,
	 * but if called with argument <code>true</code>, after {@link #currentFrame} reaches this value,
	 * the animation than starts again from {@link #startFrame}.
	 * <P>
	 * If called {@link #startReverse(boolean)} with argument <code>false</code>, the animation starts at this frame
	 * and if {@link #currentFrame} reaches the {@link #startFrame} the animation will stop, but if called with argument<code>true</code>, 
	 * after {@link #currentFrame} reaches {@link #startFrame} value the animation than starts again from this frame.
	 * 
	 * @see #Animation(long, byte, int) constructor (sets to frames-1)
	 * @see #Animation(long, int, int, int, byte) constuctor (sets to specified value)
	 * @see #switchFrame()
	 * @see #setEndFrame(int)
	 * @see #startFrame
	 * @see #currentFrame
	 */
	protected int endFrame;
	
	/**
	 * {@link svk.sglubos.engine.utils.TimerTask TimerTask} which calls {@link #switchFrame()} method every time {@link #timer} finishes cycle.
	 * 
	 * @see #timer
	 * @see #switchFrame()
	 * @see svk.sglubos.engine.utils.TimerTask
	 */
	protected TimerCallback frameSwitch  = () -> switchFrame();
	
	//TODO document
	protected TimerTask task;
	
	/**
	 * Constructs new animation with specified {@link #frameDelay delay between frame switches}, {@link #delayFormat format of delay} and {@link #frames number of frames}.
	 * <p>
	 * The {@link #startFrame} of animation is set to <code>0</code> and the {@link #endFrame} of animation is set to <code>frames - 1 </code>.<br>
	 * Uses this {@link #Animation(long, int, int, int, byte) constructor}.
	 * 
	 * @param frameDelay delay between frame switches
	 * @param timeFormat format (time unit) of delay between frame switches
	 * @param frames number of frames in this animation
	 * <p>
	 * @see #Animation(long, int, int, int, byte) constructor
	 * @see #frameDelay
	 * @see #delayFormat
	 * @see #frames
	 * @see #startFrame
	 * @see #endFrame
	 */
	public Animation(double frameDelay, byte timeFormat, int frames) {
		this(frameDelay, 0, frames - 1, frames, timeFormat);
	}
	//TODO document
	/**
	 * Constructs new animation with specified {@link #frameDelay delay between frame switches}, 
	 * {@link #startFrame start frame of animation}, {@link #endFrame end frame of animation},  
	 * {@link #frames number of frames} and {@link #delayFormat format of delay}.
	 * 
	 * <h1>Initializes:</h1>
	 * {@link #frameDelay FrameDelay} to value passed as a parameter.<br>
	 * {@link #startFrame StartFrame} and {@link #endFrame} using method {@link #initStartAndEnd(int, int)} called with arguments passed as parameters. 
	 * This method validates the values, keeps them in bounds of <code>0</code> and <code>frames -1</code>, but also if {@link #startFrame} is lower than {@link #endFrame}.
	 * <p>
	 * The {@link #frames Frames} to value passed as a parameter.<br>
	 * The {@link #delayFormat DelayFormat} to value passed as a parameter, the avaible formats (units) are formats from {@link svk.sglubos.engine.utils.Timer}:
	 * {@link svk.sglubos.engine.utils.Timer#DELAY_FORMAT_MILLISECS DELAY_FORMAT_MILLISECS}, {@link svk.sglubos.engine.utils.Timer#DELAY_FORMAT_SECS DELAY_FORMAT_SECS}
	 * and {@link svk.sglubos.engine.utils.Timer#DELAY_FORMAT_TICKS DELAY_FORMAT_TICKS}.<br>
	 * {@link #timer Timer} which handles timing between frame switches of animation. 
	 * The {@link #timer} is initialized with {@link svk.sglubos.engine.utils.TimerTask TimerTask} {@link #frameSwitch frame switch}, 
	 * which calls {@link #switchFrame()} method every time the {@link #timer} completes cycle after specified {@link #frameDelay delay}, 
	 * the delay value passed as a parameter and the format passed as a parameter.
	 * 
	 * @param frameDelay delay between frame switches
	 * @param startFrame start frame of animation
	 * @param endFrame end frame of animation
	 * @param frames number of animation frames
	 * @param timeFormat format of delay (time unit)
	 */
	public Animation(double frameDelay, int startFrame, int endFrame, int frames, byte timeFormat) {
		this.frameDelay = frameDelay;
		this.delayFormat = timeFormat;
		this.frames = frames;
		
		initStartAndEnd(startFrame, endFrame);
	}
	
	/**
	 * Starts playing animation from {@link #startFrame} to {@link #endFrame}, so the {@link #currentFrame} will be increased by one every frame switch.
	 * <p>
	 * The argument <code>loop</code> determines if the animation will be played once (if <code>false</code>) 
	 * or it will be continuously played until the {@link #stop()} method is called (if <code>true</code>).<br>
	 * Starts the {@link #timer}.
	 * Sets the {@link #running} to <code>true</code>, which makes {@link #tick()} method update the timer, and {@link #reverse} to <code>false</code> which determines, 
	 * that the animation is player from {@link #startFrame} to {@link #endFrame}.
	 * 
	 * @param loop determines if animation will be played once or continuously played until {@link #stop()} method is called
	 * <p>
	 * @see #startReverse(boolean)
	 * @see #currentFrame
	 * @see #startFrame
	 * @see #endFrame
	 */
	public void start(boolean loop) {
		if(running) {
			MessageHandler.printMessage("ANIMATION", MessageHandler.WARNING, "Animation is still running !");
			return;
		}
		
		if(startFrame == endFrame) {
			currentFrame = startFrame;
			return;
		}
		
		if(!Timer.isInitialized()) {
			MessageHandler.printMessage("ANIMATION", MessageHandler.ERROR, "Timer is not initialized !");
			throw new IllegalStateException("Timer is not initialized !");
		}
		
		if(loop) {
			task = new LoopTimerTask(delayFormat, frameDelay, LoopTimerTask.INFINITE, frameSwitch);
			Timer.addTask(task);
		} else {
			task = new LoopTimerTask(delayFormat, frameDelay, endFrame - startFrame, frameSwitch);
			Timer.addTask(task);
		}
		
		currentFrame = startFrame;
		running = true;
		reverse = false;
		this.loop = loop;
	}
	
	/**
	 * Starts playing animation from {@link #endFrame} to {@link #startFrame}, so the {@link #currentFrame} will be decreased by one every frame switch.
	 * <p>
	 * The argument <code>loop</code> determines if the animation will be played once (if <code>false</code>) 
	 * or it will be continuously played until the {@link #stop()} method is called (if <code>true</code>).<br>
	 * Starts the {@link #timer}.
	 * Sets the {@link #running} to <code>true</code>, which makes {@link #tick()} method update the timer, and {@link #reverse} to <code>true</code> which determines, 
	 * that the animation is player from {@link #endFrame} to {@link #startFrame}.
	 * 
	 * @param loop determines if animation will be played once or continuously played until {@link #stop()} method is called
	 * <p>
	 * @see #start(boolean)
	 * @see #currentFrame
	 * @see #startFrame
	 * @see #endFrame
	 */
	public void startReverse(boolean loop){
		if(running) {
			MessageHandler.printMessage("ANIMATION", MessageHandler.WARNING, "Animation is still running !");
			return;
		}
		
		if(startFrame == endFrame) {
			currentFrame = endFrame;
			return;
		}
		
		if(!Timer.isInitialized()) {
			MessageHandler.printMessage("ANIMATION", MessageHandler.ERROR, "Timer is not initialized !");
			throw new IllegalStateException("Timer is not initialized !");
		}
		
		if(loop) {
			task = new LoopTimerTask(delayFormat, frameDelay, LoopTimerTask.INFINITE, frameSwitch);
			Timer.addTask(task);
		} else {
			task = new LoopTimerTask(delayFormat, frameDelay, endFrame - startFrame, frameSwitch);
			Timer.addTask(task);
		}
		
		currentFrame = endFrame;
		running = true;
		reverse = true;
		this.loop = loop;
	}
	
	/**
	 * Stops the animation.
	 * <p>
	 * Stops the {@link #timer} and sets the {@link #running} to <code>false</code>.
	 * The {@link #currentFrame} will stay the same after this method is called, 
	 * however if called {@link #start(boolean)} or {@link #startReverse(boolean)} methods, the {@link #currentFrame} will reset.
	 * 
	 * @see #start(boolean)
	 * @see #startReverse(boolean)
	 * @see #currentFrame
	 * @see #timer
	 * @see #timer
	 */
	public void stop() {
		Timer.removeTask(task);
		running = false;
	}
	
	/**
	 * Switches the animation frame to next frame (updates the {@link #currentFrame}).<br>
	 * This method is called by {@link #frameSwitch frame switch timer task} which is used in {@link #timer}.
	 * 
	 * <h1>Switching to next frame</h1>
	 * The value of {@link #currentFrame} is kept in bounds of {@link #startFrame} and {@link #endFrame}.
	 * <p>
	 * If {@link #reverse} is <code>false</code> the {@link #currentFrame} is increased by one every time this method is called.
	 * When the {@link #currentFrame} reaches the value {@link #endFrame} and the {@link #loop} is <code>false</code>, the animation will stop,
	 * otherwise if the {@link #loop} is <code>true</code>, the {@link #currentFrame} will be set to {@link #startFrame} 
	 * and the animation will continuously cycle until {@link #stop()} method is called.
	 * <p>
	 * If {@link #reverse} is <code>true</code> the {@link #currentFrame} is decreased by one every time this method is called.
	 * When the {@link #currentFrame} reaches the value {@link #startFrame} and the {@link #loop} is <code>false</code>, the animation will stop,
	 * otherwise if the {@link #loop} is <code>true</code>, the {@link #currentFrame} will be set to {@link #endFrame}
	 * and the animation will continuously cycle until {@link #stop()} method is called.
	 * 
	 * @see #start(boolean)
	 * @see #startReverse(boolean)
	 * @see #stop()
	 * @see #timer
	 * @see #currentFrame
	 * @see #startFrame
	 * @see #endFrame
	 * @see #loop
	 * @see #reverse
	 */
	protected void switchFrame() {
		if(reverse){
			currentFrame--;
		}else{
			currentFrame++;			
		}
		
		if(currentFrame < startFrame) {
			if(loop){
				currentFrame = endFrame;							
			}else {
				stop();
			}
		} else if (currentFrame > endFrame) {
			if(loop){
				currentFrame = startFrame;							
			}else {
				stop();
			}
		}
	}

	/**
	 * Method used by {@link #Animation(long, int, int, int, byte) constructor}, initializes the start and end frame of animation.
	 * 
	 * <h1>Conditions for frames: </h1>
	 * {@link #startFrame StartFrame} have to be positive, but lower than {@link #endFrame} and also lower than {@link #frames}.<br>
	 * {@link #endFrame EndFrame} have to be positive, lower than {@link #frames} and higher than {@link #startFrame}.
	 * 
	 * @param startFrame start frame of animation
	 * @param endFrame end frame of animation
	 */
	protected void initStartAndEnd(int startFrame, int endFrame) {
		if(startFrame > endFrame || startFrame < 0 || endFrame < 0 || startFrame > frames - 1 || endFrame > frames -1) {
			MessageHandler.printMessage("ANIMATION", MessageHandler.ERROR, "Invalind animation starting or ending frame. Starting frame can not be higher than end frame: start:" + startFrame + " end: " + endFrame);
			throw new IllegalArgumentException("Invalid starting or ending frame: start: " + startFrame + " end:" +endFrame);
		}
		
		this.startFrame = startFrame;
		this.endFrame = endFrame;
	}
	
	/**
	 * Sets the {@link #frameDelay delay between frames} to specified value.
	 * <p>
	 * 
	 * @param frameDelay delay between frame
	 * <p>
	 * @throws java.lang.IllegalArgumentException IllegalArgumentException if frame delay is bellow 0
	 * <p>
	 * @see #getFrameDelay()
	 * @see #frameDelay
	 * @see #delayFormat
	 * @see #setDelayFormat(byte)
	 * @see #getDelayFormat()
	 */
	public void setFrameDelay(double frameDelay) {
		if(frameDelay < 0) {
			MessageHandler.printMessage("ANIMATION", MessageHandler.ERROR, "Invalind animation frame delay, delay can not be less than zero " + frameDelay);
			throw new IllegalArgumentException("Frame delay can not be less than 0: " + frameDelay);
		}
		this.frameDelay = frameDelay;
		task.setDelay(frameDelay);
	}
	
	/**
	 * @return delay between frames
	 * <p>
	 * @see #frameDelay
	 * @see #setFrameDelay(long)
	 * @see #delayFormat
	 * @see #getDelayFormat()
	 * @see #setDelayFormat(byte)
	 */
	public double getFrameDelay() {
		return frameDelay;
	}
	//TODO redocument
	/**
	 * Sets the {@link #delayFormat format of delay} to specified format.
	 * <p>
	 * Avaible formats are formats from {@link svk.sglubos.engine.utils.Timer Timner} class.
	 * 
	 * <h1>Avaible formats: </h1>
	 * {@link svk.sglubos.engine.utils.Timer#DELAY_FORMAT_MILLISECS DELAY_FORMAT_MILLISECS} delay in milliseconds<br>
	 * {@link svk.sglubos.engine.utils.Timer#DELAY_FORMAT_TICKS DELAY_FORMAT_TICKS} delay in ticks<br>
	 * {@link svk.sglubos.engine.utils.Timer#DELAY_FORMAT_SECS DELAY_FORMAT_SECS} delay in seconds<br>
	 * 
	 * @param delayFormat format of delay
	 * 
	 * @see #delayFormat
	 * @see #frameDelay
	 * @see #getDelayFormat()
	 * @see svk.sglubos.engine.utils.Timer
	 */
	public void setDelayFormat(byte delayFormat) {
		this.delayFormat = delayFormat;
	}
	
	/**
	 * @return format of delay
	 * <p>
	 * @see #setDelayFormat(byte)
	 * @see #delayFormat
	 * @see #frameDelay
	 */
	public byte getDelayFormat() {
		return delayFormat;
	}
	
	/**
	 * Sets the {@link #startFrame start frame} o animation to specified frame.
	 * <p>
	 * The value of start frame have to by higher than <code>0</code> and lower than {@link #endFrame}.
	 * 
	 * @param startFrame start frame of animation
	 * <p>
	 * @throws java.lang.IllegalArgumentException IllegalArgumentException if startFrame is lower than 0 or higher than {@link #endFrame}, or even higher than <code>{@link #frames} -1. </code><br><br>
	 * <p>
	 * @see #startFrame
	 * @see #endFrame
	 * @see #frames
	 */
	public void setStartFrame(int startFrame) {
		if(startFrame < 0 || startFrame >= frames || startFrame > endFrame) {
			MessageHandler.printMessage("ANIMATION", MessageHandler.ERROR, "Illegal starting frame, frame cannot be less than zero and more than frames -1: " + startFrame);
			throw new IllegalArgumentException("Illegal starting frame: " + startFrame);
		}
		
		this.startFrame = startFrame;
	}
	
	/**
	 * Sets the {@link #endFrame end frame} of animation to specified frame.
	 * <p>
	 * The value of end frame have to by higher than <code>0</code> and also higher than {@link #startFrame}.
	 * The maximum value is {@link #frames} - 1.
	 * 
	 * @param endFrame end frame of animation
	 * <p>
	 * @throws java.lang.IllegalArgumentException IllegalArgumentException if end is lower than <code>0</code> or lower than {@link #startFrame}, or even higher than <code>{@link #frames} -1 </code><br><br>
	 * <p>
	 * @see #startFrame
	 * @see #endFrame
	 * @see #frames
	 */
	public void setEndFrame(int endFrame) {
		if(endFrame < 0 || endFrame >= frames || endFrame < startFrame) {
			MessageHandler.printMessage("ANIMATION", MessageHandler.ERROR, "Illegal ending frame, frame cannot be less than zero and more than frames -1: " + endFrame);
			throw new IllegalArgumentException("Illegal ending frame: " + endFrame);
		}
		
		this.endFrame = endFrame;
	}
	
	/**
	 * @return current frame of animation
	 * <p>
	 * @see #currentFrame
	 * @see #frames
	 */
	public int getCurrentFrame(){
		return currentFrame;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	//TODO document
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		
		ret.append(getClass(), hashCode());
		ret.setLayer(1);
		ret.append("frameDelay", frameDelay);
		ret.append("delayFormat", delayFormat);
		ret.append("frames", frames);
		ret.append("loop", loop);
		ret.append("running", running);
		ret.append("reverse", reverse);
		ret.append("currentFrame", currentFrame);
		ret.append("startFrame", startFrame);
		ret.append("endFrame", endFrame);
		
		ret.append(task, "task");
		ret.append(frameSwitch, "frameSwitch");
		ret.setLayer(0);
		ret.appendCloseBracket();
		
		return ret.getString();
	}
	
	/**
	 * Abstract method which is prepared to handle rendering of animation.
     *
	 * @param screen screen object used to render currentFrame of animation
	 * @param x horizontal position of animation
	 * @param y vertical position of animation<br><br>
	 * <p> 
	 * @see svk.sglubos.engine.gfx.Screen
	 */
	public abstract void render(Screen screen, int x, int y);
}
