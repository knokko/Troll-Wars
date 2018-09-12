package nl.knokko.inventory.trading;

import nl.knokko.inventory.Inventory;
import nl.knokko.items.Item;

public class TradeOffer {
	
	private ItemStack[] give;
	private ItemStack[] get;

	public TradeOffer(ItemStack[] itemsToGive, ItemStack... itemsToGet) {
		give = itemsToGive;
		get = itemsToGet;
	}
	
	@Override
	public String toString(){
		String string = "offer:(give{";
		for(ItemStack is : give)
			string += "(" + is.getAmount() + " x " + is.getItem().getName() + "),";
		string = string.substring(0, string.length() - 1) + "},get{";
		for(ItemStack is : get)
			string += "(" + is.getAmount() + " x " + is.getItem().getName() + "),";
		string = string.substring(0, string.length() - 1) + "}";
		return string + ")";
	}
	
	public ItemStack[] getItemsToGive(){
		return give;
	}
	
	public ItemStack[] getItemsToGet(){
		return get;
	}
	
	public boolean trade(Inventory inventory){
		if(!canPay(inventory))
			return false;
		for(ItemStack is : give)
			inventory.removeItem(is.getItem(), is.getAmount());
		for(ItemStack is : get)
			inventory.addItems(is.getItem(), is.getAmount());
		return true;
	}
	
	public boolean canPay(Inventory inventory){
		for(ItemStack is : give){
			if(inventory.getAmount(is.getItem()) < is.getAmount())
				return false;
		}
		return true;
	}
	
	public boolean needItem(Item item){
		for(ItemStack is : give)
			if(is.getItem() == item)
				return true;
		for(ItemStack is : get)
			if(is.getItem() == item)
				return true;
		return false;
	}
}
