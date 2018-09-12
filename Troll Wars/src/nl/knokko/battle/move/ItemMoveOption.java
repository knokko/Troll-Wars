package nl.knokko.battle.move;

import nl.knokko.battle.Battle;
import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.inventory.Inventory;

public interface ItemMoveOption {
	
	boolean canUse(Inventory inventory, BattleCreature user, Battle battle);
	
	boolean canUse(Inventory inventory, BattleCreature user, BattleCreature target, Battle battle);
	
	boolean isPositive();
	
	BattleMove createMove(Inventory inventory, BattleCreature user, BattleCreature target, Battle battle);
	
	Category getCategory();
	
	String getName();
	
	public static enum Category {
		
		HEALING,
		ENERGY,
		EMPOWERING,
		DAMAGE;
		
		public String getDisplayName(){
			return name().charAt(0) + name().substring(1);
		}
	}
}
