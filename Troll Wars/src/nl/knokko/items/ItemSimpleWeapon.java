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
package nl.knokko.items;

import nl.knokko.battle.element.BattleElement;
import nl.knokko.battle.element.ElementalStatistics;

public abstract class ItemSimpleWeapon extends ItemWeapon {
	
	protected BattleElement element;
	
	protected int shootDamage;
	protected int stabDamage;
	protected int prickDamage;
	protected int swingDamage;
	protected int slashDamage;
	protected int smashDamage;
	protected int punchDamage;
	protected int spellDamage;

	public ItemSimpleWeapon(String name, BattleElement element, int shootDamage, int stabDamage, int prickDamage,
			int swingDamage, int slashDamage, int smashDamage, int punchDamage, int spellDamage) {
		super(name);
		this.element = element;
		this.shootDamage = shootDamage;
		this.stabDamage = stabDamage;
		this.prickDamage = prickDamage;
		this.swingDamage = swingDamage;
		this.slashDamage = slashDamage;
		this.smashDamage = smashDamage;
		this.punchDamage = punchDamage;
		this.spellDamage = spellDamage;
	}

	public ItemSimpleWeapon(String name, ElementalStatistics elementStats, BattleElement element, int shootDamage,
			int stabDamage, int prickDamage, int swingDamage, int slashDamage, int smashDamage, int punchDamage, int spellDamage) {
		super(name, elementStats);
		this.element = element;
		this.shootDamage = shootDamage;
		this.stabDamage = stabDamage;
		this.prickDamage = prickDamage;
		this.swingDamage = swingDamage;
		this.slashDamage = slashDamage;
		this.smashDamage = smashDamage;
		this.punchDamage = punchDamage;
		this.spellDamage = spellDamage;
	}

	@Override
	public BattleElement getElement() {
		return element;
	}

	@Override
	public int getShootDamage() {
		return shootDamage;
	}

	@Override
	public int getStabDamage() {
		return stabDamage;
	}

	@Override
	public int getPrickDamage() {
		return prickDamage;
	}

	@Override
	public int getSwingDamage() {
		return swingDamage;
	}

	@Override
	public int getSlashDamage() {
		return slashDamage;
	}

	@Override
	public int getSmashDamage() {
		return smashDamage;
	}

	@Override
	public int getPunchDamage() {
		return punchDamage;
	}

	@Override
	public int getSpellDamage() {
		return spellDamage;
	}
}