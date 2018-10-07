/* 
 * The MIT License
 *
 * Copyright 2018 knokko.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
	
	/**
	 * @param filter The filter to use
	 * @return all items in this inventory that pass the filter
	 */
	List<Item> getItems(ItemFilter filter);
	
	public static interface ItemFilter {
		
		boolean filter(Item item);
	}
	
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
