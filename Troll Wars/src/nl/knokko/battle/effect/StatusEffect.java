/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2018 knokko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *  
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *******************************************************************************/
package nl.knokko.battle.effect;

import java.util.Collection;

import org.lwjgl.util.vector.Vector4f;

import nl.knokko.battle.Battle;
import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.damage.DamageCause;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public interface StatusEffect {
	
	void onTurnStart(BattleCreature target);
	
	long beforeAttack(BattleCreature creature, BattleCreature target, DamageCause cause, long damage);
	
	long beforeHurt(BattleCreature host, BattleCreature attacker, DamageCause cause, long damage);
	
	/**
	 * The update priority determines when the method onTurnStart will be called compared to the other status effects.
	 * The effect with the highest priority will be called as last.
	 * The minimum priority is -200 and the maximum priority is 200
	 * The monitor priority is 201
	 * @return The update priority
	 */
	int updatePriority();
	
	/**
	 * The attack priority determines when the method beforeAttack will be called compared to the other status effects.
	 * The effect with the highest priority will be called as last.
	 * The minimum priority is -200 and the maximum priority is 200
	 * The monitor priority is 201
	 * @return The attack priority
	 */
	int attackPriority();
	
	int hurtPriority();
	
	boolean isExpired();
	
	boolean isPositive();
	
	void combine(StatusEffect other);
	
	Vector4f getEffectColor();
	
	short getID();
	
	void save(Battle battle, BitOutput output);
	
	public static class Registry {
		
		private static final Class<?>[] ID_MAP = {EffectGuardianDemon.class};
		
		public static StatusEffect load(BitInput input){
			short id = input.readShort();
			try {
				return (StatusEffect) ID_MAP[id - Short.MIN_VALUE].getConstructor(BitInput.class).newInstance(input);
			} catch(Exception ex){
				throw new IllegalArgumentException("Failed to load status effect with id " + id, ex);
			}
		}
	}
	
	public static class AttackPriority {
		
		public static final int MONITOR = 201;
		public static final int MAX = 200;
		public static final int NORMAL = 0;
		public static final int MIN = -200;
		
		public static long beforeAttack(BattleCreature creature, BattleCreature target, DamageCause cause, long damage, Collection<StatusEffect> effects){
			for(int priority = MIN; priority <= MAX; priority++){
				for(StatusEffect effect : effects){
					if(effect.attackPriority() == priority){
						damage = effect.beforeAttack(creature, target, cause, damage);
					}
				}
			}
			for(StatusEffect effect : effects){
				if(effect.attackPriority() == MONITOR){
					effect.beforeAttack(creature, target, cause, damage);
				}
			}
			return damage;
		}
	}
	
	public static class HurtPriority {
		
		public static final int MONITOR = 201;
		public static final int MAX = 200;
		public static final int NORMAL = 0;
		public static final int MIN = -200;
		
		public static final int GUARDIAN_DEMON = 200;
		
		public static long beforeHurt(BattleCreature creature, BattleCreature target, DamageCause cause, long damage, Collection<StatusEffect> effects){
			for(int priority = MIN; priority <= MAX; priority++){
				for(StatusEffect effect : effects){
					if(effect.attackPriority() == priority){
						damage = effect.beforeAttack(creature, target, cause, damage);
					}
				}
			}
			for(StatusEffect effect : effects){
				if(effect.attackPriority() == MONITOR){
					effect.beforeAttack(creature, target, cause, damage);
				}
			}
			return damage;
		}
	}
	
	public static class UpdatePriority {
		
		public static final int MAX = 200;
		public static final int NORMAL = 0;
		public static final int MIN = -200;
		
		public static void beginTurn(BattleCreature creature, Collection<StatusEffect> effects){
			for(int priority = MIN; priority <= MAX; priority++){
				for(StatusEffect effect : effects){
					if(effect.updatePriority() == priority){
						effect.onTurnStart(creature);
					}
				}
			}
		}
	}
}