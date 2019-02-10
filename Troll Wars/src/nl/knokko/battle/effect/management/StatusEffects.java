/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2019 knokko
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