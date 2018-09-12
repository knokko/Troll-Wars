package nl.knokko.story.dialogue;

import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public interface Dialogue {
	
	/**
	 * @return True if this dialogue doesn't need to be finished properly, false if the player can't walk away.
	 */
	public abstract boolean canLeave();
	
	public abstract DialoguePart current();
	
	public abstract void setCurrent(DialoguePart newPart);
	
	void save(BitOutput output);
	
	void load(BitInput input);
	
	int getID();
}