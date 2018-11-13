/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2018 knokko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
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
 *******************************************************************************/
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
