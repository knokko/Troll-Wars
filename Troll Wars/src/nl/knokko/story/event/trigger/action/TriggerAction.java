package nl.knokko.story.event.trigger.action;

import nl.knokko.area.Area;

public interface TriggerAction {
	
	/**
	 * @param xFrom tileX the player comes from
	 * @param yFrom tileY the player comes from
	 * @param zFrom tileZ the player comes from
	 * @param xTo tileX destination
	 * @param yTo tileY destination
	 * @param zTo tileZ destination
	 * @param area the class of the current area
	 * @return true if the movement of the player should be prevented, false if the player can finish his step
	 */
	boolean execute(int xFrom, int yFrom, int zFrom, int xTo, int yTo, int zTo, Class<? extends Area> area);
}
