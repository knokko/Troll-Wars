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
package nl.knokko.battle.effect.management;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import nl.knokko.battle.Battle;
import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.damage.DamageCause;
import nl.knokko.battle.effect.StatusEffect;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

import org.lwjgl.util.vector.Vector4f;

public class SimpleStatusEffects implements StatusEffects {
	
	protected final Collection<StatusEffect> effects;

	public SimpleStatusEffects() {
		effects = new ArrayList<StatusEffect>();
	}
	
	public SimpleStatusEffects(BitInput input){
		int size = input.readInt();
		effects = new ArrayList<StatusEffect>(size);
		for(int i = 0; i < size; i++){
			effects.add(StatusEffect.Registry.load(input));
		}
	}
	
	@Override
	public void save(Battle battle, BitOutput output){
		output.addInt(effects.size());
		for(StatusEffect effect : effects){
			output.addShort(effect.getID());
			effect.save(battle, output);
		}
	}

	@Override
	public Collection<StatusEffect> getEffects() {
		return effects;
	}

	@Override
	public Vector4f getEffectColors() {
		Vector4f color = new Vector4f();
		for(StatusEffect effect : effects)
			Vector4f.add(color, effect.getEffectColor(), color);
		return color;
	}

	@Override
	public long beforeAttack(BattleCreature host, BattleCreature target, DamageCause cause, long damage) {
		return StatusEffect.AttackPriority.beforeAttack(host, target, cause, damage, effects);
	}

	@Override
	public void beginTurn(BattleCreature host) {
		StatusEffect.UpdatePriority.beginTurn(host, effects);
	}

	@Override
	public void add(StatusEffect effect) {
		for(StatusEffect current : effects){
			if(current.getID() == effect.getID()){
				current.combine(effect);
				return;
			}
		}
		effects.add(effect);
	}

	@Override
	public void clear() {
		effects.clear();
	}

	@Override
	public void remove(short ID) {
		Iterator<StatusEffect> iterator = effects.iterator();
		while(iterator.hasNext()){
			if(iterator.next().getID() == ID){
				iterator.remove();
				return;
			}
		}
	}

	@Override
	public void remove(Selector selector) {
		Iterator<StatusEffect> iterator = effects.iterator();
		while(iterator.hasNext()){
			if(selector.shouldRemove(iterator.next())){
				iterator.remove();
			}
		}
	}
}