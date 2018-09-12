package nl.knokko.items;

import nl.knokko.inventory.InventoryType;

public abstract class ItemDrink extends ItemConsumable {

	ItemDrink(String name) {
		super(name);
	}

	@Override
	public InventoryType getType() {
		return InventoryType.DRINK;
	}

}