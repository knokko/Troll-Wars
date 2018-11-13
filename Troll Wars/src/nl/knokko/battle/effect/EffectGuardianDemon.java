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

import nl.knokko.battle.Battle;
import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.damage.DamageCause;
import nl.knokko.main.Game;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

import org.lwjgl.util.vector.Vector4f;

public class EffectGuardianDemon implements StatusEffect {
	
	public static final Vector4f EFFECT_COLOR = new Vector4f(0.2f, 0f, 0.2f, 0.5f);
	
	protected BattleCreature[] casters;
	protected int[] casterIndexes;

	public EffectGuardianDemon(BattleCreature caster) {
		casters = new BattleCreature[]{caster};
	}
	
	public EffectGuardianDemon(BitInput input){
		casterIndexes = new int[input.readInt()];
		for(int index = 0; index < casterIndexes.length; index++)
			casterIndexes[index] = input.readInt();
	}
	
	protected void getCasters(){
		if(casters == null){
			casters = new BattleCreature[casterIndexes.length];
			for(int index = 0; index < casterIndexes.length; index++)
				casters[index] = Battle.Helper.getByIndex(Game.getBattle().getBattle(), casterIndexes[index]);
			casterIndexes = null;
		}
		for(int index = 0; index < casters.length; index++){
			if(casters[index].isFainted()){
				BattleCreature[] newCasters = new BattleCreature[casters.length - 1];
				System.arraycopy(casters, 0, newCasters, 0, index);
				System.arraycopy(casters, index + 1, newCasters, index, newCasters.length - index);
				casters = newCasters;
				index--;
			}
		}
	}

	@Override
	public void onTurnStart(BattleCreature target) {}

	@Override
	public long beforeAttack(BattleCreature creature, BattleCreature target, DamageCause cause, long damage) {
		return damage;
	}
	
	@Override
	public long beforeHurt(BattleCreature host, BattleCreature attacker, DamageCause cause, long damage){
		long left = host.getHealth() - damage;
		if(left <= 0){
			long reflected = -left + 1;
			for(BattleCreature caster : casters){
				if(caster.getHealth() >= reflected){
					caster.inflictTrueDamage(reflected);//the caster has enough health to absorb all damage
					return host.getHealth() - 1;
				}
				else {
					reflected -= caster.getHealth();
					caster.inflictTrueDamage(caster.getHealth());//the caster dies
				}
			}
			return reflected + host.getHealth() - 1;//the caster dies as well as the host
		}
		else
			return damage;//the host has enough health to survive the damage
	}

	@Override
	public int updatePriority() {
		return StatusEffect.UpdatePriority.NORMAL;
	}

	@Override
	public int attackPriority() {
		return StatusEffect.AttackPriority.NORMAL;
	}
	
	@Override
	public int hurtPriority(){
		return StatusEffect.HurtPriority.GUARDIAN_DEMON;
	}

	@Override
	public boolean isExpired() {
		return casters.length == 0;
	}

	@Override
	public boolean isPositive() {
		return true;
	}

	@Override
	public void combine(StatusEffect other) {
		EffectGuardianDemon gd = (EffectGuardianDemon) other;
		int length = casters.length;
		for(BattleCreature bc : gd.casters){
			for(BattleCreature caster : casters){
				if(caster == bc){
					length--;
					break;
				}
			}
			length++;
		}
		BattleCreature[] combined = new BattleCreature[length];
		System.arraycopy(casters, 0, combined, 0, casters.length);
		int index = casters.length;
		for(BattleCreature bc : gd.casters){
			boolean add = true;
			for(BattleCreature caster : casters){
				if(caster == bc){
					add = false;
					break;
				}
			}
			if(add)
				combined[index++] = bc;
		}
		casters = combined;
	}

	@Override
	public Vector4f getEffectColor() {
		return EFFECT_COLOR;
	}

	@Override
	public short getID() {
		return Short.MIN_VALUE;
	}

	@Override
	public void save(Battle battle, BitOutput output) {
		output.addInt(casters.length);
		for(BattleCreature caster : casters)
			output.addInt(Battle.Helper.getIndexFor(battle, caster));
	}
}