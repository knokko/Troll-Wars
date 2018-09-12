package nl.knokko.battle.effect.management;

import java.util.Collection;

import org.lwjgl.util.vector.Vector4f;

import nl.knokko.battle.Battle;
import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.damage.DamageCause;
import nl.knokko.battle.effect.StatusEffect;
import nl.knokko.util.bits.BitOutput;

public interface StatusEffects {
	
	Collection<StatusEffect> getEffects();
	
	Vector4f getEffectColors();
	
	long beforeAttack(BattleCreature host, BattleCreature target, DamageCause cause, long damage);
	
	void beginTurn(BattleCreature host);
	
	void add(StatusEffect effect);
	
	void clear();
	
	void remove(short ID);
	
	void remove(Selector selector);
	
	void save(Battle battle, BitOutput output);
	
	public static interface Selector {
		
		public static final Selector POSITIVE = new Selector(){

			@Override
			public boolean shouldRemove(StatusEffect effect) {
				return effect.isPositive();
			}
		};
		
		public static final Selector NEGATIVE = new Selector(){

			@Override
			public boolean shouldRemove(StatusEffect effect) {
				return !effect.isPositive();
			}
		};
		
		boolean shouldRemove(StatusEffect effect);
	}
}