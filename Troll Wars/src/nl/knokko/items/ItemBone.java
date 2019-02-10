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
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.hand.HumanoidHandProperties;
import nl.knokko.model.equipment.weapon.ModelBone;
import nl.knokko.model.factory.equipment.WeaponFactory;
import nl.knokko.texture.painter.BonePainter;
import nl.knokko.util.color.Color;

public class ItemBone extends ItemWeapon {
	
	protected final ModelBone model;
	protected final Color color;
	
	protected final int stabDamage;
	protected final int swingDamage;
	protected final int slashDamage;
	protected final int smashDamage;
	protected final int punchDamage;
	protected final int spellDamage;

	public ItemBone(String name, ModelBone model, Color color, int stabDamage, int swingDamage, int slashDamage, int smashDamage, int punchDamage, int spellDamage) {
		super(name);
		this.model = model;
		this.color = color;
		this.stabDamage = stabDamage;
		this.swingDamage = swingDamage;
		this.slashDamage = slashDamage;
		this.smashDamage = smashDamage;
		this.punchDamage = punchDamage;
		this.spellDamage = spellDamage;
	}
	
	@Override
	public ModelPart createModel(HumanoidHandProperties hand, boolean left){
		return WeaponFactory.createModelBone(model, new BonePainter(color), hand, left);
	}

	@Override
	public float getRange(BattleCreature user, HumanoidHandProperties hand) {//TODO test this one and that of ItemSpear
		return (hand.handCoreLength() / Math.max(model.boneRadiusX(), model.boneRadiusZ()) * 0.5f) * (model.boneLength() + model.boneRadiusTopY());
	}

	@Override
	public int getShootDamage() {
		return 0;
	}

	@Override
	public int getStabDamage() {
		return stabDamage;
	}

	@Override
	public int getPrickDamage() {
		return 0;
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

	@Override
	public BattleElement getElement() {
		return BattleElement.PHYSICAL;
	}
}
