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
package svk.sglubos.engine.utils.timer;

public abstract class TimerTask {
	protected boolean done;
	protected double nextCallback;
	protected byte timeFormat;
	protected double delay;
	protected TimerCallback callback;
	
	public TimerTask(byte timeFormat, double delay, TimerCallback callback) {
		this.timeFormat = timeFormat;
		this.delay = delay;
		this.callback = callback;
	}
	
	public abstract void update(double time); 
	
	public byte getTimeFormat() {
		return timeFormat;
	}
	
	public boolean isDone() {
		return done;
	}

	public double getDelay() {
		return delay;
	}

	public void setDelay(double delay) {
		this.delay = delay;
	}

	public TimerCallback getCallback() {
		return callback;
	}

	public void setCallback(TimerCallback callback) {
		this.callback = callback;
	}

	public void setTimeFormat(byte timeFormat) {
		this.timeFormat = timeFormat;
	}
}
