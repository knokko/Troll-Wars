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
