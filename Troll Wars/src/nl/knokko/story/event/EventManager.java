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
import nl.knokko.util.resources.Saver;

public class EventManager {
	
	private final IntroHumanEvent introHuman = new IntroHumanEvent();
	
	private final StoryEvent[] events = {introHuman};

	public EventManager() {}
	
	public void initNewGame(){
		for(StoryEvent event : events)
			event.initNewGame();
	}
	
	public void save(){
		BitOutput buffer = Saver.save("events.data", 32 + events.length * 2);
		//BitBuffer buffer = new BitBuffer(32 + events.length * 2);
		buffer.addInt(events.length);//make older save files compatible as well
		for(StoryEvent event : events)
			event.save(buffer);
		//Saver.save(buffer, "events.data");
	}
	
	public void load(){
		BitInput bits = Saver.load("events.data");
		int amount = bits.readInt();
		if(amount != events.length)
			System.out.println("The loaded save file has less events.");
		int index;
		for(index = 0; index < amount; index++)
			events[index].load(bits);
		for(;index < events.length; index++)
			events[index].initNewGame();
	}
	
	public IntroHumanEvent introHuman(){
		return introHuman;
	}
}
