package nl.knokko.equipment;

import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.battle.element.SimpleElementStats;
import nl.knokko.items.Item;
import nl.knokko.items.ItemChestplate;
import nl.knokko.items.ItemGlobe;
import nl.knokko.items.ItemHelmet;
import nl.knokko.items.ItemPants;
import nl.knokko.items.ItemShoe;
import nl.knokko.items.ItemWeapon;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public class EquipmentNone implements Equipment {

	public EquipmentNone() {}

	@Override
	public int getArmor() {
		return 0;
	}

	@Override
	public int getResistance() {
		return 0;
	}

	@Override
	public BitOutput save(BitOutput buffer) {
		return buffer;
	}

	@Override
	public void load(BitInput buffer) {}

	@Override
	public void equipLeftShoe(Item boots) {}
	
	@Override
	public void equipRightShoe(Item boots){}

	@Override
	public void equipPants(Item pants) {}

	@Override
	public void equipChestplate(Item plate) {}

	@Override
	public void equipLeftGlobe(Item globe) {}

	@Override
	public void equipRightGlobe(Item globe) {}

	@Override
	public void equipLeftWeapon(Item weapon) {}

	@Override
	public void equipRightWeapon(Item weapon) {}

	@Override
	public void equipHelmet(Item helmet) {}

	@Override
	public ItemShoe getLeftShoe() {
		return null;
	}
	
	@Override
	public ItemShoe getRightShoe(){
		return null;
	}

	@Override
	public ItemPants getPants() {
		return null;
	}

	@Override
	public ItemChestplate getChestplate() {
		return null;
	}

	@Override
	public ItemGlobe getLeftGlobe() {
		return null;
	}

	@Override
	public ItemGlobe getRightGlobe() {
		return null;
	}

	@Override
	public ItemWeapon getLeftWeapon() {
		return null;
	}

	@Override
	public ItemWeapon getRightWeapon() {
		return null;
	}

	@Override
	public ItemHelmet getHelmet() {
		return null;
	}

	@Override
	public ElementalStatistics getElementStats() {
		return SimpleElementStats.NEUTRAL;
	}
}
