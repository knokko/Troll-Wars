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
package nl.knokko.items;

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.element.BattleElement;
import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.battle.element.SimpleElementStats;
import nl.knokko.inventory.InventoryType;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.hand.HumanoidHandProperties;

public abstract class ItemWeapon extends ItemEquipment {
	
	public ItemWeapon(String name){
		this(name, SimpleElementStats.NEUTRAL);
	}

	public ItemWeapon(String name, ElementalStatistics elementStats) {
		super(name, elementStats);
	}

	@Override
	public final InventoryType getType() {
		return InventoryType.WEAPON;
	}
	
	public abstract ModelPart createModel(HumanoidHandProperties hand, boolean left);
	
	public abstract float getRange(BattleCreature user, HumanoidHandProperties hand);
	
	public abstract BattleElement getElement();
	
	public abstract int getShootDamage();
	public abstract int getStabDamage();
	public abstract int getPrickDamage();
	public abstract int getSwingDamage();
	public abstract int getSlashDamage();
	public abstract int getSmashDamage();
	
	public abstract int getPunchDamage();
	public abstract int getSpellDamage();
}
