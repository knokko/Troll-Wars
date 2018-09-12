package nl.knokko.story.event.trigger.condition;

import nl.knokko.area.Area;

public class MultiCondition implements TriggerCondition {
	
	private final TriggerCondition[] conditions;

	public MultiCondition(TriggerCondition... conditions) {
		this.conditions = conditions;
	}

	@Override
	public boolean trigger(int xFrom, int yFrom, int zFrom, int xTo, int yTo, int zTo, Class<? extends Area> area) {
		for(TriggerCondition condition : conditions)
			if(condition.trigger(xFrom, yFrom, zFrom, xTo, yTo, zTo, area))
				return true;
		return false;
	}
}
