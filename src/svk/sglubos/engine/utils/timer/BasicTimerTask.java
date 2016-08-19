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

public class BasicTimerTask extends TimerTask {
	public BasicTimerTask(byte timeFormat, double delay, TimerCallback callback) {
		super(timeFormat, delay, callback);
		nextCallback = delay;
	}
	
	@Override
	public void update(double time) {
		nextCallback -= time;
		if(nextCallback <= 0) {
			callback.callback();
			done = true;
		}
	}
}
