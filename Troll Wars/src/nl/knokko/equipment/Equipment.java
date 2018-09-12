package nl.knokko.equipment;

import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.items.Item;
import nl.knokko.items.ItemShoe;
import nl.knokko.items.ItemChestplate;
import nl.knokko.items.ItemGlobe;
import nl.knokko.items.ItemHelmet;
import nl.knokko.items.ItemPants;
import nl.knokko.items.ItemWeapon;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public interface Equipment {
	
	int getArmor();
	
	int getResistance();
	
	BitOutput save(BitOutput buffer);
	
	void load(BitInput buffer);
	
	public void equipLeftShoe(Item boots);
	
	public void equipRightShoe(Item boots);
	
	public void equipPants(Item pants);
	
	public void equipChestplate(Item plate);
	
	public void equipLeftGlobe(Item globe);
	
	public void equipRightGlobe(Item globe);
	
	public void equipLeftWeapon(Item weapon);
	
	public void equipRightWeapon(Item weapon);
	
	public void equipHelmet(Item helmet);
	
	public ItemShoe getLeftShoe();
	
	public ItemShoe getRightShoe();
	
	public ItemPants getPants();
	
	public ItemChestplate getChestplate();
	
	public ItemGlobe getLeftGlobe();
	
	public ItemGlobe getRightGlobe();
	
	public ItemWeapon getLeftWeapon();
	
	public ItemWeapon getRightWeapon();
	
	public ItemHelmet getHelmet();
	
	ElementalStatistics getElementStats();
}
