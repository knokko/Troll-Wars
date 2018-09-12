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
