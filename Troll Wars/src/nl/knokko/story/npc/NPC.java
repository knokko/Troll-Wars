package nl.knokko.story.npc;

import nl.knokko.area.Area;
import nl.knokko.area.creature.AreaCreature;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

/**
 * A Non-Player Character
 */
public interface NPC {
	
	/**
	 * @return An array of the classes of all areas where this NPC could be.
	 */
	Class<?>[] getPossibleAreas();
	
	/**
	 * @return An AreaCreature that will represent this NPC in the specified area, or null if this NPC is not in this area.
	 */
	AreaCreature createRepresentation(Area area);
	
	/**
	 * This method must save the same amount of bits as the load method will load.
	 */
	void save(BitOutput bits);
	
	/**
	 * This method must load the same amount of bits as it saved.
	 */
	void load(BitInput bits);
	
	void initFirstGame();
}
