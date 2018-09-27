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
package nl.knokko.equipment;

import nl.knokko.inventory.InventoryType;
import nl.knokko.items.Item;
import nl.knokko.items.Items;
import nl.knokko.util.bits.BitInput;

public class EquipmentFull extends EquipmentBase {
	
	public static EquipmentFull createFullIron(Item weapon){
		EquipmentFull equipment = new EquipmentFull();
		equipment.equipHelmet(Items.IRON_HELMET);
		equipment.equipChestplate(Items.IRON_CHESTPLATE);
		equipment.equipPants(Items.IRON_LEGGINGS);
		equipment.equipLeftShoe(Items.IRON_SHOE);
		equipment.equipRightShoe(Items.IRON_SHOE);
		equipment.equipRightWeapon(weapon);
		return equipment;
	}
	
	public static EquipmentFull createFullBlessedIron(Item weapon){
		EquipmentFull equipment = new EquipmentFull();
		equipment.equipHelmet(Items.BLESSED_IRON_HELMET);
		equipment.equipChestplate(Items.BLESSED_IRON_CHESTPLATE);
		equipment.equipPants(Items.BLESSED_IRON_LEGGINGS);
		equipment.equipLeftShoe(Items.BLESSED_IRON_SHOE);
		equipment.equipRightShoe(Items.BLESSED_IRON_SHOE);
		equipment.equipRightWeapon(weapon);
		return equipment;
	}

	public EquipmentFull() {}

	public EquipmentFull(BitInput buffer) {
		super(buffer);
	}

	@Override
	protected boolean canEquip(InventoryType type) {
		return true;
	}

	@Override
	protected boolean canEquip(Item item) {
		return true;
	}
}
