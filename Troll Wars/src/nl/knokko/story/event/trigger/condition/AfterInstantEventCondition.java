package nl.knokko.story.event.trigger.condition;

import nl.knokko.area.Area;
import nl.knokko.story.event.InstantEvent;

public class AfterInstantEventCondition implements TriggerCondition {
	
	private final InstantEvent event;

	public AfterInstantEventCondition(InstantEvent event) {
		this.event = event;
	}

	@Override
	public boolean trigger(int xFrom, int yFrom, int zFrom, int xTo, int yTo, int zTo, Class<? extends Area> area) {
		return event.hasHappened();
	}
}
