package nl.knokko.story.event;

import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.resources.Saver;

public class EventManager {
	
	private final IntroHumanEvent introHuman = new IntroHumanEvent();
	
	private final StoryEvent[] events = {introHuman};

	public EventManager() {}
	
	public void initNewGame(){
		for(StoryEvent event : events)
			event.initNewGame();
	}
	
	public void save(){
		BitOutput buffer = Saver.save("events.data", 32 + events.length * 2);
		//BitBuffer buffer = new BitBuffer(32 + events.length * 2);
		buffer.addInt(events.length);//make older save files compatible as well
		for(StoryEvent event : events)
			event.save(buffer);
		//Saver.save(buffer, "events.data");
	}
	
	public void load(){
		BitInput bits = Saver.load("events.data");
		int amount = bits.readInt();
		if(amount != events.length)
			System.out.println("The loaded save file has less events.");
		int index;
		for(index = 0; index < amount; index++)
			events[index].load(bits);
		for(;index < events.length; index++)
			events[index].initNewGame();
	}
	
	public IntroHumanEvent introHuman(){
		return introHuman;
	}
}
