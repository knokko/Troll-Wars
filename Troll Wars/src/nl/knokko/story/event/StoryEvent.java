package nl.knokko.story.event;

import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public interface StoryEvent {
	
	void save(BitOutput bits);
	
	void load(BitInput bits);
	
	void initNewGame();
}
