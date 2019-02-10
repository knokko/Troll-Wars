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

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.element.BattleElement;
import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.battle.move.physical.MovePunch;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.hand.HumanoidHandProperties;
import nl.knokko.model.factory.equipment.WeaponFactory;
import nl.knokko.texture.equipment.SwordTexture;

public class ItemSword extends ItemSimpleWeapon {
	
	protected SwordTexture texture;
	
	public ItemSword(String name, SwordTexture texture, BattleElement element, int slashDamage, int spellDamage) {
		super(name, element, 0, 0, slashDamage * 4 / 3, slashDamage, slashDamage, slashDamage, MovePunch.BASE_DAMAGE, spellDamage);
		this.texture = texture;
	}

	public ItemSword(String name, SwordTexture texture, ElementalStatistics elementStats,
			BattleElement element, int slashDamage, int spellDamage) {
		super(name, elementStats, element, 0, 0, slashDamage * 4 / 3, slashDamage, slashDamage, slashDamage, MovePunch.BASE_DAMAGE, spellDamage);
		this.texture = texture;
	}

	@Override
	public ModelPart createModel(HumanoidHandProperties hand, boolean left) {
		return WeaponFactory.createModelSword(texture, hand);
	}

	@Override
	public float getRange(BattleCreature user, HumanoidHandProperties hand) {
		return (hand.handCoreLength() / texture.getSwordModel().handleRadius() / 2) * (texture.getSwordModel().handleLength() + texture.getSwordModel().middleWidth() + texture.getSwordModel().bladeLength()) - hand.handHeight();
	}
}