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
package nl.knokko.story.event.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import nl.knokko.area.Area;
import nl.knokko.areas.AreaSorgCave;
import nl.knokko.main.Game;
import nl.knokko.story.dialogues.Dialogues;
import nl.knokko.story.event.IntroHumanEvent;
import nl.knokko.story.event.trigger.MoveEventTrigger;
import nl.knokko.story.event.trigger.SimpleMoveEventTrigger;
import nl.knokko.story.event.trigger.action.TriggerAction;
import nl.knokko.story.event.trigger.condition.AreaRegionCondition;
import nl.knokko.story.event.trigger.condition.AreaRegionCondition.Region;

public class EventHandler {
	
	private static final Map<Class<?>, Collection<MoveEventTrigger>> MOVE_EVENTS = new HashMap<Class<?>, Collection<MoveEventTrigger>>();
	
	static {
		put(new SimpleMoveEventTrigger(new AreaRegionCondition(AreaSorgCave.A1.class, new Region.LineX(47, 50, 13, 50)), new IntroHumanTriggerAction(), AreaSorgCave.A1.class));
	}
	
	private static class IntroHumanTriggerAction implements TriggerAction {
		
		@Override
		public boolean execute(int xFrom, int yFrom, int zFrom, int xTo, int yTo, int zTo, Class<? extends Area> area){
			byte state = Game.getEventManager().introHuman().getState();
			if(state == IntroHumanEvent.STATE_NOT_MET){
				Game.startDialogue(Dialogues.INTRO_HUMANS);
				return true;
			}
			if(state == IntroHumanEvent.STATE_LEFT){
				Game.startDialogue(Dialogues.INTRO_HUMANS_WITHOUT_TROLLS);
				return true;
			}
			if(state == IntroHumanEvent.STATE_LEFT_HAS_TROLLS){
				Game.startDialogue(Dialogues.INTRO_HUMANS_WITH_TROLLS);
			}
			return false;
		}
	}
	
	private static void put(MoveEventTrigger trigger){
		Class<?>[] classes = trigger.getAreaClasses();
		for(Class<?> areaClass : classes){
			Collection<MoveEventTrigger> collection = MOVE_EVENTS.get(areaClass);
			if(collection == null){
				collection = new ArrayList<MoveEventTrigger>();
				MOVE_EVENTS.put(areaClass, collection);
			}
			collection.add(trigger);
		}
	}

	public EventHandler() {}
	
	/**
	 * @param tileXFrom The current tile X coord
	 * @param tileYFrom The current tile Y coord
	 * @param tileZFrom The current tile Z coord
	 * @param tileXTo The destination tile X coord
	 * @param tileYTo The destination tile Y coord
	 * @param tileZTo The destination tile Z coord
	 * @param areaClass The class of the current area
	 * @return true if the movement has to be blocked, false if not
	 */
	public boolean onPlayerMove(int tileXFrom, int tileYFrom, int tileZFrom, int tileXTo, int tileYTo, int tileZTo, Class<? extends Area> areaClass){
		Collection<MoveEventTrigger> events = MOVE_EVENTS.get(areaClass);
		if(events == null)
			return false;
		for(MoveEventTrigger event : events)
			if(event.onMove(tileXFrom, tileYFrom, tileZFrom, tileXTo, tileYTo, tileZTo, areaClass))
				return true;
		return false;
	}
	
	public void onPlotBattleVictory(int plotBattleID){
		Game.getStoryBattleManager().onStoryBattleVictory(plotBattleID);
	}
	
	public void onPlotBattleDefeat(int plotBattleID){
		Game.getStoryBattleManager().onStoryBattleDefeat(plotBattleID);
	}
}
