package nl.knokko.story.event;

import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public class TempEvent implements StoryEvent {
	
	private boolean started;
	private boolean stopped;

	public TempEvent() {}

	@Override
	public void save(BitOutput bits) {
		bits.addBoolean(started);
		bits.addBoolean(stopped);
	}

	@Override
	public void load(BitInput bits) {
		started = bits.readBoolean();
		stopped = bits.readBoolean();
	}

	@Override
	public void initNewGame() {
		started = false;
		stopped = false;
	}
	
	public boolean isStarted(){
		return started;
	}
	
	public boolean isActive(){
		return started && !stopped;
	}
	
	public boolean isStopped(){
		return stopped;
	}
	
	public boolean notStarted(){
		return !started;
	}
	
	public boolean notActive(){
		return !started || stopped;
	}
	
	public boolean notStopped(){
		return !stopped;
	}
}
