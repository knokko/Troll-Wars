package nl.knokko.items;

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.element.BattleElement;
import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.battle.element.SimpleElementStats;
import nl.knokko.inventory.InventoryType;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.hand.HumanoidHandProperties;

public abstract class ItemWeapon extends ItemEquipment {
	
	public ItemWeapon(String name){
		this(name, SimpleElementStats.NEUTRAL);
	}

	public ItemWeapon(String name, ElementalStatistics elementStats) {
		super(name, elementStats);
	}

	@Override
	public final InventoryType getType() {
		return InventoryType.WEAPON;
	}
	
	public abstract ModelPart createModel(HumanoidHandProperties hand, boolean left);
	
	public abstract float getRange(BattleCreature user, HumanoidHandProperties hand);
	
	public abstract BattleElement getElement();
	
	public abstract int getShootDamage();
	public abstract int getStabDamage();
	public abstract int getPrickDamage();
	public abstract int getSwingDamage();
	public abstract int getSlashDamage();
	public abstract int getSmashDamage();
	
	public abstract int getPunchDamage();
	public abstract int getSpellDamage();
}
