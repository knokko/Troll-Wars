package nl.knokko.inventory;

import java.util.ArrayList;
import java.util.List;

import nl.knokko.items.Item;
import nl.knokko.items.Items;
import nl.knokko.util.Maths;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public class ArrayInventory implements Inventory {
	
	private final int[] amounts = new int[Items.amount()];
	
	private int greatestAmount;

	public ArrayInventory() {}

	@Override
	public void addItem(Item item) {
		addItems(item, 1);
	}

	@Override
	public void addItems(Item item, int amount) {
		amounts[Items.getID(item) - Short.MIN_VALUE] += amount;
		if(getAmount(item) > greatestAmount)
			greatestAmount = getAmount(item);
	}

	@Override
	public int getAmount(Item item) {
		return amounts[Items.getID(item) - Short.MIN_VALUE];
	}

	@Override
	public BitOutput save(BitOutput output) {
		output.addInt(amounts.length);
		if(greatestAmount >= 1){
			byte bitCount = Maths.log2Up(greatestAmount + 1);
			output.addNumber(bitCount, (byte) 6, false);
			for(int amount : amounts)
				output.addNumber(amount, bitCount, false);
		}
		else
			output.addNumber(0, (byte)6, false);
		return output;
	}

	@Override
	public void load(BitInput input) {
		int itemCount = input.readInt();
		byte bitCount = (byte) input.readNumber((byte) 6, false);
		if(bitCount >= 1){
			for(int i = 0; i < itemCount; i++){
				amounts[i] = (int) input.readNumber(bitCount, false);
				if(amounts[i] > greatestAmount)
					greatestAmount = amounts[i];
			}
		}
	}

	@Override
	public boolean removeItem(Item item, int amount) {
		if(getAmount(item) >= amount){
			amounts[Items.getID(item) - Short.MIN_VALUE] -= amount;
			return true;
		}
		amounts[Items.getID(item) - Short.MIN_VALUE] = 0;
		return false;
	}

	@Override
	public boolean removeItem(Item item) {
		return removeItem(item, 1);
	}

	@Override
	public List<Item> getItems() {
		List<Item> list = new ArrayList<Item>();
		for(int i = 0; i < amounts.length; i++)
			if(amounts[i] > 0)
				list.add(Items.fromID((short) (i + Short.MIN_VALUE)));
		return list;
	}

	@Override
	public List<Item> getItems(InventoryTypeBase type) {
		List<Item> list = new ArrayList<Item>();
		for(int i = 0; i < amounts.length; i++)
			if(amounts[i] > 0){
				Item item = Items.fromID((short) (i + Short.MIN_VALUE));
				if(item.getType().getParent() == type)
					list.add(item);
			}
		return list;
	}

	@Override
	public List<Item> getItems(InventoryType type) {
		List<Item> list = new ArrayList<Item>();
		for(int i = 0; i < amounts.length; i++)
			if(amounts[i] > 0){
				Item item = Items.fromID((short) (i + Short.MIN_VALUE));
				if(item.getType() == type)
					list.add(item);
			}
		return list;
	}

}
