/* 
 * The MIT License
 *
 * Copyright 2018 knokko.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
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
 */
package nl.knokko.battle.damage;

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.element.BattleElement;
import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.items.ItemWeapon;

public class DamageCalculator {
	
	public static long calculateFinalDamage(BattleCreature target, DamageCause cause, long damage){
		damage *= getElementResistanceFactor(cause, target.getElementStats());
		damage = applyArmorCalculation(getActiveArmor(target, cause), damage);
		return damage;
	}
	
	private static long applyArmorCalculation(double activeResistance, long damage){
		return (long) (damage / Math.pow(2, activeResistance / 10));
	}
	
	private static double getElementResistanceFactor(DamageCause cause, ElementalStatistics es){
		return 1 - es.getResistance(cause.getElement());
	}
	
	private static double getActiveArmor(BattleCreature target, DamageCause cause){
		return target.getArmor() * (double) cause.getPhysicalFactor() + target.getResistance() * (double) cause.getMagicalFactor();
	}
	
	public static long calculatePhysicalMonsterDamage(BattleCreature attacker, AttackType type, int factor){
		float power = attacker.getElementStats().getPower(attacker.getAttackElement());
		System.out.println("strengh is " + attacker.getStrength() + " and power is " + power);
		return (long) (attacker.getStrength() * factor * power);
	}
	
	public static long calculatePhysicalDamageOutput(BattleCreature attacker, ItemWeapon weapon, AttackType type){
		BattleElement element = weapon != null && type != AttackType.PUNCH ? weapon.getElement() : attacker.getAttackElement();
		float e = attacker.getElementStats().getPower(element);
		if(weapon != null)
			e *= weapon.getElementStats().getPower(element);
		if(weapon == null)
			return (long) (attacker.getStrength() * type.getBaseDamage() * e);
		if(type == AttackType.PRICK)
			return (long) (attacker.getStrength() * weapon.getPrickDamage() * e);
		if(type == AttackType.PUNCH)
			return (long) (attacker.getStrength() * weapon.getPunchDamage() * e);
		if(type == AttackType.SHOOT)
			return (long) (weapon.getShootDamage() * weapon.getElementStats().getPower(element));
		if(type == AttackType.SLASH)
			return (long) (attacker.getStrength() * weapon.getSlashDamage() * e);
		if(type == AttackType.SMASH)
			return (long) (attacker.getStrength() * weapon.getSmashDamage() * e);
		if(type == AttackType.SPELL)
			throw new IllegalArgumentException("Spells can't deal physical damage!");
		if(type == AttackType.STAB)
			return (long) (attacker.getStrength() * weapon.getStabDamage() * e);
		if(type == AttackType.SWING)
			return (long) (attacker.getStrength() * weapon.getSwingDamage() * e);
		throw new IllegalArgumentException("Unknown attack type: " + type.name());
	}
	
	public static DamageCause getPhysicalDamageCause(BattleCreature attacker, ItemWeapon weapon, AttackType type){
		return DamageCause.createPhysicalCause(weapon != null ? weapon.getElement() : attacker.getAttackElement(), type);
	}
}
