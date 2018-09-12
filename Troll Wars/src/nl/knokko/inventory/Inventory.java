package nl.knokko.inventory;

import java.util.ArrayList;
import java.util.List;

import nl.knokko.battle.move.ItemMoveOption;
import nl.knokko.items.Item;
import nl.knokko.items.ItemConsumable;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public interface Inventory {
	
	void addItem(Item item);
	
	void addItems(Item item, int amount);
	
	int getAmount(Item item);
	
	BitOutput save(BitOutput output);
	
	void load(BitInput input);
	
	boolean removeItem(Item item, int amount);
	
	boolean removeItem(Item item);
	
	/**
	 * @return all items that this inventory contains
	 */
	List<Item> getItems();
	
	/**
	 * @return all items of the specified type that this inventory contains
	 */
	List<Item> getItems(InventoryTypeBase type);
	
	/**
	 * @return all items of the specified type that this inventory contains
	 */
	List<Item> getItems(InventoryType type);
	
	public static class Helper {
		
		public static ItemMoveOption[] getItemMoves(Inventory inventory){
			return getItemMoves(inventory.getItems(InventoryTypeBase.CONSUMABLE), null);
		}
		
		public static ItemMoveOption[] getItemMoves(Inventory inventory, ItemMoveOption.Category category){
			return getItemMoves(inventory.getItems(InventoryTypeBase.CONSUMABLE), category);
		}
		
		private static ItemMoveOption[] getItemMoves(List<Item> items, ItemMoveOption.Category category){
			List<ItemMoveOption> moves = new ArrayList<ItemMoveOption>(items.size());
			for(Item item : items){
				ItemConsumable ic = (ItemConsumable) item;
				ItemMoveOption move = ic.getMove();
				if(category == null || move.getCategory() == category)
					moves.add(move);
			}
			return moves.toArray(new ItemMoveOption[moves.size()]);
		}
	}
}
