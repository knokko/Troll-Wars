package nl.knokko.items;

import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.inventory.InventoryType;

public abstract class ItemEquipment extends Item {
	
	protected ElementalStatistics elementStats;

	public ItemEquipment(String name, ElementalStatistics elementStats) {
		super(name, null);
		this.elementStats = elementStats;
	}
	
	public ElementalStatistics getElementStats(){
		return elementStats;
	}
	
	@Override
	public abstract InventoryType getType();
}
