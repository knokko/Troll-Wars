package nl.knokko.battle.move;

import nl.knokko.battle.Battle;
import nl.knokko.battle.creature.BattleCreature;

public interface FightMoveOption {
	
	boolean canCast(BattleCreature caster, Battle battle);
	
	boolean canCast(BattleCreature caster, BattleCreature target, Battle battle);
	
	boolean isPositive();
	
	BattleMove createMove(BattleCreature caster, BattleCreature target, Battle battle);
	
	Category getCategory();
	
	String getName();
	
	public static enum Category {
		
		BASIC,
		HEALING,
		MAGIC,
		PHYSICAL;
		
		public String getDisplayName(){
			return name().charAt(0) + name().substring(1);
		}
	}
}
