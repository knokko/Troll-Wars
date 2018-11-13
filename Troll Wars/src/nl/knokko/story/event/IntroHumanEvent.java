/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2018 knokko
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

public class IntroHumanEvent implements StoryEvent {
	
	public static final byte STATE_NOT_MET = 0;
	public static final byte STATE_LEFT = 1;
	public static final byte STATE_FOUGHT_ALONE = 2;
	public static final byte STATE_FOUGHT_WITH_TROLLS = 3;
	public static final byte STATE_FOUGHT_WITH_DEMON = 4;
	public static final byte STATE_SHOW_MYRMORA = 5;
	public static final byte STATE_LEFT_HAS_TROLLS = 6;
	
	private static final byte BITCOUNT = 3;
	
	private byte state;

	public IntroHumanEvent() {}

	@Override
	public void save(BitOutput bits) {
		bits.addNumber(state, BITCOUNT, false);
	}

	@Override
	public void load(BitInput bits) {
		state = (byte) bits.readNumber(BITCOUNT, false);
	}

	@Override
	public void initNewGame() {
		state = STATE_NOT_MET;
	}
	
	public byte getState(){
		return state;
	}
	
	public void setState(byte newState){
		state = newState;
	}
	
	public boolean isOver(){
		return state == STATE_FOUGHT_ALONE || state == STATE_FOUGHT_WITH_TROLLS || state == STATE_FOUGHT_WITH_DEMON;
	}
	
	public boolean wasHelpedByTrolls(){
		return state == STATE_FOUGHT_WITH_TROLLS;
	}
	
	public boolean wasHelpedByDemon(){
		return state == STATE_FOUGHT_WITH_DEMON;
	}
}