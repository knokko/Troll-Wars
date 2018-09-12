package nl.knokko.story.event;

import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public class InstantEvent implements StoryEvent {
	
	private boolean happened;

	public InstantEvent() {}

	@Override
	public void save(BitOutput bits) {
		bits.addBoolean(happened);
	}

	@Override
	public void load(BitInput bits) {
		happened = bits.readBoolean();
	}

	@Override
	public void initNewGame() {
		happened = false;
	}
	
	public boolean hasHappened(){
		return happened;
	}
	
	public boolean notHappened(){
		return !happened;
	}
	
	public void happen(){
		happened = true;
	}
}
