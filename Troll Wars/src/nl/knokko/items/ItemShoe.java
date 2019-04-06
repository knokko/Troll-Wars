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

import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.inventory.InventoryType;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.foot.HumanoidFootProperties;
import nl.knokko.model.body.leg.HumanoidLeg;
import nl.knokko.model.equipment.boots.ModelArmorFoot;
import nl.knokko.model.factory.equipment.ArmorFactory;
import nl.knokko.texture.equipment.ArmorTexture;

public class ItemShoe extends ItemArmor {
	
	protected ArmorTexture armorTexture;

	public ItemShoe(String name, ElementalStatistics elementStats, int armor, int resistance, ArmorTexture texture) {
		super(name, elementStats, armor, resistance);
		armorTexture = texture;
	}

	public ItemShoe(String name, int armor, int resistance, ArmorTexture texture) {
		super(name, armor, resistance);
		armorTexture = texture;
	}

	@Override
	public final InventoryType getType() {
		return InventoryType.BOOTS;
	}
	
	public ModelPart createModelLeft(HumanoidFootProperties foot, HumanoidLeg leg){
		return ArmorFactory.createModelArmorFoot(ModelArmorFoot.Factory.createInstance(1.15f), foot, leg, armorTexture, false);
	}
	
	public ModelPart createModelRight(HumanoidFootProperties foot, HumanoidLeg leg){
		return ArmorFactory.createModelArmorFoot(ModelArmorFoot.Factory.createInstance(1.15f), foot, leg, armorTexture, true);
	}
}
