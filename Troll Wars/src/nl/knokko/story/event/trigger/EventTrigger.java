package nl.knokko.story.event.trigger;

public interface EventTrigger {
	
	/**
	 * @return An array containing all areas where this event can be triggered
	 */
	Class<?>[] getAreaClasses();
}
