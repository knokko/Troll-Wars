package nl.knokko.inventory.trading;

import nl.knokko.items.Item;

public class ItemStack {
	
	private final Item item;
	
	private final int amount;
	
	public ItemStack(Item item){
		this(item, 1);
	}

	public ItemStack(Item item, int amount) {
		this.item = item;
		this.amount = amount;
	}
	
	public Item getItem(){
		return item;
	}
	
	public int getAmount(){
		return amount;
	}
}
