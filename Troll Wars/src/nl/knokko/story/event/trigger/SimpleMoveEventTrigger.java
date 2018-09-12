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