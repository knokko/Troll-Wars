package nl.knokko.items;

import nl.knokko.inventory.InventoryType;

public abstract class ItemFood extends ItemConsumable {

	ItemFood(String name) {
		super(name);
	}

	@Override
	public InventoryType getType() {
		return InventoryType.FOOD;
	}

}