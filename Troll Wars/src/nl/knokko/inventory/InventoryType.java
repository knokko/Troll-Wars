package nl.knokko.inventory;

import static nl.knokko.inventory.InventoryTypeBase.*;

public enum InventoryType {
	
	WEAPON(EQUIPMENT),
	BOOTS(EQUIPMENT),
	PANTS(EQUIPMENT),
	CHESTPLATE(EQUIPMENT),
	GLOBE(EQUIPMENT),
	HELMET(EQUIPMENT),
	MONEY(MATERIAL),
	METAL(MATERIAL),
	GEM(MATERIAL),
	ORGANIC(MATERIAL),
	DRINK(CONSUMABLE),
	FOOD(CONSUMABLE);
	
	private final InventoryTypeBase parent;
	
	private InventoryType(InventoryTypeBase parent){
		this.parent = parent;
	}
	
	public InventoryTypeBase getParent(){
		return parent;
	}
}
