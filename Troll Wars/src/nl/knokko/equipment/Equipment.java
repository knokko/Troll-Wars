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
package nl.knokko.equipment;

import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.items.Item;
import nl.knokko.items.ItemShoe;
import nl.knokko.items.ItemChestplate;
import nl.knokko.items.ItemGlobe;
import nl.knokko.items.ItemHelmet;
import nl.knokko.items.ItemPants;
import nl.knokko.items.ItemWeapon;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public interface Equipment {
	
	int getArmor();
	
	int getResistance();
	
	BitOutput save(BitOutput buffer);
	
	void load(BitInput buffer);
	
	public void equipLeftShoe(Item boots);
	
	public void equipRightShoe(Item boots);
	
	public void equipPants(Item pants);
	
	public void equipChestplate(Item plate);
	
	public void equipLeftGlobe(Item globe);
	
	public void equipRightGlobe(Item globe);
	
	public void equipLeftWeapon(Item weapon);
	
	public void equipRightWeapon(Item weapon);
	
	public void equipHelmet(Item helmet);
	
	public ItemShoe getLeftShoe();
	
	public ItemShoe getRightShoe();
	
	public ItemPants getPants();
	
	public ItemChestplate getChestplate();
	
	public ItemGlobe getLeftGlobe();
	
	public ItemGlobe getRightGlobe();
	
	public ItemWeapon getLeftWeapon();
	
	public ItemWeapon getRightWeapon();
	
	public ItemHelmet getHelmet();
	
	ElementalStatistics getElementStats();
	
	boolean canEquipLeftShoe(Item shoe);
	
	boolean canEquipRightShoe(Item shoe);
	
	boolean canEquipPants(Item pants);
	
	boolean canEquipChestplate(Item plate);
	
	boolean canEquipLeftGlobe(Item globe);
	
	boolean canEquipRightGlobe(Item globe);
	
	boolean canEquipLeftWeapon(Item weapon);
	
	boolean canEquipRightWeapon(Item weapon);
	
	boolean canEquipHelmet(Item helmet);
	
	boolean canEquipLeftShoe();
	
	boolean canEquipRightShoe();
	
	boolean canEquipPants();
	
	boolean canEquipChestplate();
	
	boolean canEquipLeftGlobe();
	
	boolean canEquipRightGlobe();
	
	boolean canEquipLeftWeapon();
	
	boolean canEquipRightWeapon();
	
	boolean canEquipHelmet();
}
