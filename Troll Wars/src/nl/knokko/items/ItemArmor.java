package nl.knokko.items;

import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.battle.element.SimpleElementStats;

public abstract class ItemArmor extends ItemEquipment {
	
	protected int armor;
	protected int resistance;

	public ItemArmor(String name, ElementalStatistics elements, int armor, int resistance) {
		super(name, elements);
		this.armor = armor;
		this.resistance = resistance;
	}
	
	public ItemArmor(String name, int armor, int resistance){
		this(name, SimpleElementStats.NEUTRAL, armor, resistance);
	}
	
	public int getArmor(){
		return armor;
	}
	
	public int getResistance(){
		return resistance;
	}
}