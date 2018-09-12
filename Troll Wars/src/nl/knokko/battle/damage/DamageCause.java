package nl.knokko.battle.damage;

import nl.knokko.battle.element.BattleElement;

public class DamageCause {
	
	public static DamageCause createPhysicalCause(BattleElement element, AttackType attackType){
		return new DamageCause(element, attackType.getArmorBase());
	}
	
	public static DamageCause createMagicCause(BattleElement element){
		return new DamageCause(element, AttackType.SPELL.getArmorBase());
	}
	
	private final BattleElement element;
	
	private final float armorBase;

	private DamageCause(BattleElement element, float armorBase) {
		this.element = element;
		this.armorBase = armorBase;
	}
	
	public BattleElement getElement(){
		return element;
	}
	
	public float getPhysicalFactor(){
		return element.getPhysicalProtection();
	}
	
	public float getMagicalFactor(){
		return element.getMagicProtection();
	}
	
	public float getResistanceBase(){
		return armorBase;
	}
}
