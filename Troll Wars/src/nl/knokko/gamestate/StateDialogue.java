package nl.knokko.gamestate;

import nl.knokko.gui.Gui;
import nl.knokko.main.Game;
import nl.knokko.story.dialogue.Dialogue;
import nl.knokko.story.dialogues.Dialogues;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.resources.Saver;

public class StateDialogue implements GameState {
	
	private Dialogue current;

	public StateDialogue() {}

	@Override
	public void update() {
		if(current == null)
			Game.removeState();
		else {
			//TODO use user input
		}
	}

	@Override
	public void render() {
		if(current != null){
			
		}
	}

	@Override
	public void open() {
		if(current == null)
			throw new IllegalStateException("There is no current dialogue");
	}

	@Override
	public void close() {}

	@Override
	public void enable() {}

	@Override
	public void disable() {}

	@Override
	public void save() {
		if(current != null) {
			//BitBuffer data = new BitBuffer();
			BitOutput data = Saver.save("dialogue.data", 100);
			data.addInt(current.getID());
			current.save(data);
			//Saver.save(data, "dialogue.data");
		} 
	}
	
	public void load(BitInput data){
		current = Dialogues.fromID(data.readInt());
		current.load(data);
	}

	@Override
	public boolean renderTransparent() {
		return true;
	}

	@Override
	public boolean updateTransparent() {
		return false;
	}

	@Override
	public void setCurrentGui(Gui gui) {}
	
	public void setCurrentDialogue(Dialogue dialogue){
		current = dialogue;
	}
}
