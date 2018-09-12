package nl.knokko.story.event.trigger.action;

import nl.knokko.area.Area;

public class TriggerActionDebug implements TriggerAction {
	
	private final String message;
	private final boolean cancel;

	public TriggerActionDebug(String message, boolean cancelStep) {
		this.message = message;
		cancel = cancelStep;
	}

	@Override
	public boolean execute(int xFrom, int yFrom, int zFrom, int xTo, int yTo, int zTo, Class<? extends Area> area) {
		System.out.println(message);
		return cancel;
	}
}
