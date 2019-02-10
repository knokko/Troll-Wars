/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2019 knokko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *  
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *******************************************************************************/
package nl.knokko.story.event;

import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public class TempEvent implements StoryEvent {
	
	private boolean started;
	private boolean stopped;

	public TempEvent() {}

	@Override
	public void save(BitOutput bits) {
		bits.addBoolean(started);
		bits.addBoolean(stopped);
	}

	@Override
	public void load(BitInput bits) {
		started = bits.readBoolean();
		stopped = bits.readBoolean();
	}

	@Override
	public void initNewGame() {
		started = false;
		stopped = false;
	}
	
	public boolean isStarted(){
		return started;
	}
	
	public boolean isActive(){
		return started && !stopped;
	}
	
	public boolean isStopped(){
		return stopped;
	}
	
	public boolean notStarted(){
		return !started;
	}
	
	public boolean notActive(){
		return !started || stopped;
	}
	
	public boolean notStopped(){
		return !stopped;
	}
}
