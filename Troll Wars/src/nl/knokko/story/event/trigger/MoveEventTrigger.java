package nl.knokko.story.event.trigger;

import nl.knokko.area.Area;

public interface MoveEventTrigger extends EventTrigger {
	
	boolean onMove(int xFrom, int yFrom, int zFrom, int xTo, int yTo, int zTo, Class<? extends Area> area);
}