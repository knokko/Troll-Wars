package nl.knokko.items;

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.move.ItemMoveOption;
import nl.knokko.inventory.InventoryType;

public abstract class ItemConsumable extends Item {

	ItemConsumable(String name) {
		super(name, null);
	}
	
	public abstract boolean canConsume(BattleCreature target);
	
	public abstract void consume(BattleCreature target);
	
	public abstract ItemMoveOption getMove();
	
	@Override
	public abstract InventoryType getType();
}