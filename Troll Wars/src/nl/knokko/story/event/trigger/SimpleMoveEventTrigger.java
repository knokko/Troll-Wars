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
package nl.knokko.story.event.trigger;

import nl.knokko.area.Area;
import nl.knokko.story.event.trigger.action.TriggerAction;
import nl.knokko.story.event.trigger.condition.TriggerCondition;

public final class SimpleMoveEventTrigger implements MoveEventTrigger {
	
	private TriggerCondition condition;
	private TriggerAction action;
	
	private Class<?>[] areas;
	
	public SimpleMoveEventTrigger(TriggerCondition condition, TriggerAction action, Class<?>... areas){
		this.condition = condition;
		this.action = action;
		this.areas = areas;
	}
	
	/**
	 * @param xFrom The current tile X coord
	 * @param yFrom The current tile Y coord
	 * @param zFrom The current tile Z coord
	 * @param xTo The destination tile X coord
	 * @param yTo The destination tile Y coord
	 * @param zTo The destination tile Z coord
	 * @param area The class of the current area
	 * @return true if the movement has to be blocked, false if not
	 */
	@Override
	public boolean onMove(int xFrom, int yFrom, int zFrom, int xTo, int yTo, int zTo, Class<? extends Area> area){
		if(condition.trigger(xFrom, yFrom, zFrom, xTo, yTo, zTo, area))
			return action.execute(xFrom, yFrom, zFrom, xTo, yTo, zTo, area);
		else
			return false;
	}

	@Override
	public Class<?>[] getAreaClasses() {
		return areas;
	}
}