/*
 *	Copyright 2015 Ă„ËťubomÄ‚Â­r Hlavko
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
import svk.sglubos.engine.utils.debug.MessageHandler;
import svk.sglubos.engine.utils.timer.LoopTimerTask;
import svk.sglubos.engine.utils.timer.Timer;
import svk.sglubos.engine.utils.timer.TimerCallback;
import svk.sglubos.engine.utils.timer.TimerTask;

public abstract class Animation {
	protected double frameDelay;
	protected byte delayFormat;
	protected int frames;
	protected boolean loop;
	protected boolean running;
	protected boolean reverse;
	protected int currentFrame;
	protected int startFrame;
	protected int endFrame;

	protected TimerCallback frameSwitch  = () -> switchFrame();
	protected TimerTask task;
	
	public Animation(double frameDelay, byte timeFormat, int frames) {
		this(frameDelay, 0, frames - 1, frames, timeFormat);
	}

	public Animation(double frameDelay, int startFrame, int endFrame, int frames, byte timeFormat) {
		this.frameDelay = frameDelay;
		this.delayFormat = timeFormat;
		this.frames = frames;
		
		initStartAndEnd(startFrame, endFrame);
	}
	
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
	
	public void stop() {
		Timer.removeTask(task);
		running = false;
	}
	
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

	protected void initStartAndEnd(int startFrame, int endFrame) {
		if(startFrame > endFrame || startFrame < 0 || endFrame < 0 || startFrame > frames - 1 || endFrame > frames -1) {
			MessageHandler.printMessage("ANIMATION", MessageHandler.ERROR, "Invalind animation starting or ending frame. Starting frame can not be higher than end frame: start:" + startFrame + " end: " + endFrame);
			throw new IllegalArgumentException("Invalid starting or ending frame: start: " + startFrame + " end:" +endFrame);
		}
		
		this.startFrame = startFrame;
		this.endFrame = endFrame;
	}
	
	public void setFrameDelay(double frameDelay) {
		if(frameDelay < 0) {
			MessageHandler.printMessage("ANIMATION", MessageHandler.ERROR, "Invalind animation frame delay, delay can not be less than zero " + frameDelay);
			throw new IllegalArgumentException("Frame delay can not be less than 0: " + frameDelay);
		}
		this.frameDelay = frameDelay;
		task.setDelay(frameDelay);
	}
	
	public double getFrameDelay() {
		return frameDelay;
	}

	public void setDelayFormat(byte delayFormat) {
		this.delayFormat = delayFormat;
	}
	
	public byte getDelayFormat() {
		return delayFormat;
	}
	
	public void setStartFrame(int startFrame) {
		if(startFrame < 0 || startFrame >= frames || startFrame > endFrame) {
			MessageHandler.printMessage("ANIMATION", MessageHandler.ERROR, "Illegal starting frame, frame cannot be less than zero and more than frames -1: " + startFrame);
			throw new IllegalArgumentException("Illegal starting frame: " + startFrame);
		}
		
		this.startFrame = startFrame;
	}
	
	public void setEndFrame(int endFrame) {
		if(endFrame < 0 || endFrame >= frames || endFrame < startFrame) {
			MessageHandler.printMessage("ANIMATION", MessageHandler.ERROR, "Illegal ending frame, frame cannot be less than zero and more than frames -1: " + endFrame);
			throw new IllegalArgumentException("Illegal ending frame: " + endFrame);
		}
		
		this.endFrame = endFrame;
	}
	
	public int getCurrentFrame(){
		return currentFrame;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public abstract void render(Screen screen, int x, int y);
}
