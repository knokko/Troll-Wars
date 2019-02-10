/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2019 knokko
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
import nl.knokko.battle.element.SimpleElementStats;
import nl.knokko.items.Item;
import nl.knokko.items.ItemChestplate;
import nl.knokko.items.ItemGlobe;
import nl.knokko.items.ItemHelmet;
import nl.knokko.items.ItemPants;
import nl.knokko.items.ItemShoe;
import nl.knokko.items.ItemWeapon;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public class EquipmentNone implements Equipment {

	public EquipmentNone() {}

	@Override
	public int getArmor() {
		return 0;
	}

	@Override
	public int getResistance() {
		return 0;
	}

	@Override
	public BitOutput save(BitOutput buffer) {
		return buffer;
	}

	@Override
	public void load(BitInput buffer) {}

	@Override
	public void equipLeftShoe(Item boots) {}
	
	@Override
	public void equipRightShoe(Item boots){}

	@Override
	public void equipPants(Item pants) {}

	@Override
	public void equipChestplate(Item plate) {}

	@Override
	public void equipLeftGlobe(Item globe) {}

	@Override
	public void equipRightGlobe(Item globe) {}

	@Override
	public void equipLeftWeapon(Item weapon) {}

	@Override
	public void equipRightWeapon(Item weapon) {}

	@Override
	public void equipHelmet(Item helmet) {}

	@Override
	public ItemShoe getLeftShoe() {
		return null;
	}
	
	@Override
	public ItemShoe getRightShoe(){
		return null;
	}

	@Override
	public ItemPants getPants() {
		return null;
	}

	@Override
	public ItemChestplate getChestplate() {
		return null;
	}

	@Override
	public ItemGlobe getLeftGlobe() {
		return null;
	}

	@Override
	public ItemGlobe getRightGlobe() {
		return null;
	}

	@Override
	public ItemWeapon getLeftWeapon() {
		return null;
	}

	@Override
	public ItemWeapon getRightWeapon() {
		return null;
	}

	@Override
	public ItemHelmet getHelmet() {
		return null;
	}

	@Override
	public ElementalStatistics getElementStats() {
		return SimpleElementStats.NEUTRAL;
	}

	@Override
	public boolean canEquipLeftShoe(Item shoe) {
		return false;
	}

	@Override
	public boolean canEquipRightShoe(Item shoe) {
		return false;
	}

	@Override
	public boolean canEquipPants(Item pants) {
		return false;
	}

	@Override
	public boolean canEquipChestplate(Item plate) {
		return false;
	}

	@Override
	public boolean canEquipLeftGlobe(Item globe) {
		return false;
	}

	@Override
	public boolean canEquipRightGlobe(Item globe) {
		return false;
	}

	@Override
	public boolean canEquipLeftWeapon(Item weapon) {
		return false;
	}

	@Override
	public boolean canEquipRightWeapon(Item weapon) {
		return false;
	}

	@Override
	public boolean canEquipHelmet(Item helmet) {
		return false;
	}

	@Override
	public boolean canEquipLeftShoe() {
		return false;
	}

	@Override
	public boolean canEquipRightShoe() {
		return false;
	}

	@Override
	public boolean canEquipPants() {
		return false;
	}

	@Override
	public boolean canEquipChestplate() {
		return false;
	}

	@Override
	public boolean canEquipLeftGlobe() {
		return false;
	}

	@Override
	public boolean canEquipRightGlobe() {
		return false;
	}

	@Override
	public boolean canEquipLeftWeapon() {
		return false;
	}

	@Override
	public boolean canEquipRightWeapon() {
		return false;
	}

	@Override
	public boolean canEquipHelmet() {
		return false;
	}
}