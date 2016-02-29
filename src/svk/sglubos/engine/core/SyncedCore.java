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
package svk.sglubos.engine.core;

import svk.sglubos.engine.utils.debug.DebugStringBuilder;

public abstract class SyncedCore extends Core implements Runnable {
	protected Thread thread;
	
	private boolean debug;
	private long sleep;
	private int ticksPerSecond;
	private int fpsLimit;
	
	public SyncedCore (int ticksPerSecond, boolean debug) {
		this.ticksPerSecond = ticksPerSecond;
		this.fpsLimit = ticksPerSecond;
		this.debug = debug;
		this.sleep = (long) (1000 / fpsLimit);
	}
	
	@Override
	public void run() {
		init();
		
		long lastTime = System.nanoTime();
		long lastTimeDebugOutput = System.currentTimeMillis();
		double delta = 0;
		double nanoSecPerTick = Math.pow(10, 9) / ticksPerSecond;
		int fps = 0;
		int ticks = 0;
		
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) /nanoSecPerTick;
			lastTime = now;
				
			while(delta >= 1){
				delta--;
				tick();
				render();
				
				if (debug) {
					ticks++;
					fps++;
				}
			}
			
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
				
			if(debug && (System.currentTimeMillis() - lastTimeDebugOutput) >= 1000){
				System.out.println("[DEBUG] ticks: " + ticks + " fps: " + fps);
				lastTimeDebugOutput += 1000;
				fps = 0;
				ticks = 0;
			}
		}
		
		stopped();	
	}

	protected int getFPSLimit() {
		return fpsLimit;
	}
	
	protected int getTPSLimit() {
		return ticksPerSecond;
	}
	
	protected boolean isDebug() {
		return debug;
	}
	
	protected void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	@Override
	protected void start() {
		running = true;
		thread = new Thread(this,"core");
		thread.start();
	}

	@Override
	protected void stop() {
	 running = false;
	}
	
	@Override
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		ret.append(getClass(), hashCode());
		ret.increaseLayer();
		ret.appendln(super.toString());
		ret.append("debug", debug);
		ret.append("sleep", sleep);
		ret.append("ticksPerSecond", ticksPerSecond);
		ret.append("fpsLimit", fpsLimit);
		ret.decreaseLayer();
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}

