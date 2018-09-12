package nl.knokko.story.event.trigger.condition;

import nl.knokko.area.Area;

public interface TriggerCondition {
	
	/**
	 * @param xFrom tileX the player comes from
	 * @param yFrom tileY the player comes from
	 * @param zFrom tileZ the player comes from
	 * @param xTo tileX of destination
	 * @param yTo tileY of destination
	 * @param zTo tileZ of destination
	 * @param area The class of the current area
	 * @return true if the action of the trigger should be executed, false otherwise
	 */
	boolean trigger(int xFrom, int yFrom, int zFrom, int xTo, int yTo, int zTo, Class<? extends Area> area);
}