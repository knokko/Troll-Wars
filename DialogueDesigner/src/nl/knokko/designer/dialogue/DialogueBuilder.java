package nl.knokko.designer.dialogue;

import java.util.ArrayList;
import java.util.List;

import nl.knokko.story.dialogue.PortraitMap;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public class DialogueBuilder {
	
	private static PartBuilder loadPartBuilder(BitInput data, PortraitMap portraits, int index){
		int x = data.readInt();
		int y = data.readInt();
		if(data.readBoolean())
			return new ChoisePartBuilder(data, portraits, index, x, y);
		else
			return new SimplePartBuilder(data, portraits, index, x, y);
	}
	
	private static void savePartBuilder(BitOutput data, PartBuilder part, PortraitMap portraits){
		data.addInt(part.designerX);
		data.addInt(part.designerY);
		part.save(data, portraits);
	}
	
	public boolean canLeave;
	
	public List<PartBuilder> parts;
	
	public PortraitMap portraits;

	public DialogueBuilder() {
		canLeave = true;
		parts = new ArrayList<PartBuilder>();
		portraits = new PortraitMap();
		portraits.add("EMPTY");
	}
	
	public DialogueBuilder(BitInput data){
		canLeave = data.readBoolean();
		portraits = new PortraitMap(data);
		int size = data.readInt();
		parts = new ArrayList<PartBuilder>(size);
		for(int i = 0; i < size; i++)
			parts.add(loadPartBuilder(data, portraits, i));
	}

	public void save(BitOutput data) {
		data.addBoolean(canLeave);
		portraits.save(data);
		data.addInt(parts.size());
		for(PartBuilder part : parts)
			savePartBuilder(data, part, portraits);
	}
}