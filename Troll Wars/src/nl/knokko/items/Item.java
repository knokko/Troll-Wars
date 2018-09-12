package nl.knokko.items;

import nl.knokko.inventory.InventoryType;
import nl.knokko.texture.ImageTexture;
import nl.knokko.util.resources.Resources;

public class Item implements Comparable<Item> {
	
	private static short nextID = Short.MIN_VALUE;
	
	protected final String name;
	protected ImageTexture texture;
	
	private final short id;
	private final InventoryType type;

	Item(String name, InventoryType type) {
		id = nextID;
		nextID++;
		Items.ITEMS[id - Short.MIN_VALUE] = this;
		this.name = name;
		this.type = type;
		this.texture = loadTexture();
	}
	
	@Override
	public final int hashCode(){
		return id;
	}
	
	@Override
	public final String toString(){
		return getName();
	}
	
	@Override
	public final int compareTo(Item item) {
		return id - item.id;
	}
	
	final short getID(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public InventoryType getType(){
		return type;
	}
	
	public ImageTexture getTexture(){
		return texture;
	}
	
	protected ImageTexture loadTexture(){
		return new ImageTexture(Resources.loadImage("items/" + name));
	}
}
