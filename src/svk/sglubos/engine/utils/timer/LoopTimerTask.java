/*
 *	Copyright 2015 Ľubomír Hlavko
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

public class LoopTimerTask extends TimerTask {
	public static final int INFINITE = -1;
	private int cycles;
	
	public LoopTimerTask(byte timeFormat, double delay, int cycles, TimerCallback callback) {
		super(timeFormat, delay, callback);
		this.nextCallback = delay;
		this.cycles = cycles;
	}

	@Override
	public void update(double time) {
		nextCallback -= time;
		if(nextCallback <= 0) {
			callback.callback();
			
			if(cycles == INFINITE) {
				nextCallback = delay;
				return;
			} else if (cycles == 0) {
				done = true;
			} else {
				cycles--;
				nextCallback = delay;
			}
		}
	}
}
