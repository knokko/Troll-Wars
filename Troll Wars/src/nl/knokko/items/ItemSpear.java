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

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.element.BattleElement;
import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.battle.element.SimpleElementStats;
import nl.knokko.battle.move.physical.MovePunch;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.hand.HumanoidHandProperties;
import nl.knokko.texture.equipment.SpearTexture;
import nl.knokko.util.resources.Resources;

public class ItemSpear extends ItemSimpleWeapon {
	
	protected SpearTexture texture;

	public ItemSpear(String name, SpearTexture texture, BattleElement element, int prickDamage, int swingDamage, int slashDamage, int smashDamage, int spellDamage) {
		this(name, texture, BattleElement.PHYSICAL, SimpleElementStats.NEUTRAL, prickDamage, swingDamage, slashDamage, smashDamage, spellDamage);
	}
	
	public ItemSpear(String name, SpearTexture texture, BattleElement element, int prickDamage, int spellDamage){
		this(name, texture, element, SimpleElementStats.NEUTRAL, prickDamage, prickDamage / 3, prickDamage / 3, prickDamage / 3, spellDamage);
	}

	public ItemSpear(String name, SpearTexture texture, BattleElement element, ElementalStatistics elementStats, int prickDamage, int swingDamage, int slashDamage, int smashDamage, int spellDamage) {
		super(name, elementStats, element, 0, 0, prickDamage, swingDamage, slashDamage, smashDamage, MovePunch.BASE_DAMAGE, spellDamage);
		this.texture = texture;
	}

	@Override
	public ModelPart createModel(HumanoidHandProperties hand, boolean left) {
		return Resources.createModelSpear(texture, hand);
	}

	@Override
	public float getRange(BattleCreature user, HumanoidHandProperties hand) {
		return (hand.handCoreLength() / texture.getSpearModel().stickRadius() / 2) * (texture.getSpearModel().stickLength() + texture.getSpearModel().pointLength()) - hand.handHeight();
	}
}