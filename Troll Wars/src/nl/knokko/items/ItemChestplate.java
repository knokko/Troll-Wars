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
import nl.knokko.model.body.arm.HumanoidArm;
import nl.knokko.model.body.belly.HumanoidBelly;
import nl.knokko.model.equipment.chestplate.ModelArmorArm;
import nl.knokko.model.equipment.chestplate.ModelArmorBelly;
import nl.knokko.model.factory.equipment.ArmorFactory;
import nl.knokko.texture.equipment.ArmorTexture;

public class ItemChestplate extends ItemArmor {
	
	protected ArmorTexture armorTexture;
	
	public ItemChestplate(String name, ElementalStatistics elementStats, int armor, int resistance, ArmorTexture armorTexture) {
		super(name, elementStats, armor, resistance);
		this.armorTexture = armorTexture;
	}

	public ItemChestplate(String name, int armor, int resistance, ArmorTexture armorTexture) {
		super(name, armor, resistance);
		this.armorTexture = armorTexture;
	}

	@Override
	public final InventoryType getType() {
		return InventoryType.CHESTPLATE;
	}
	
	public ModelPart createModelBelly(HumanoidBelly belly){
		return ArmorFactory.createModelArmorBelly(ModelArmorBelly.Factory.createInstance(1.3f), belly, armorTexture);
	}
	
	public ModelPart createModelUpperArm(HumanoidArm arm){
		return ArmorFactory.createModelArmorUpperArm(ModelArmorArm.Factory.createInstance(1.3f, 1.3f), arm, armorTexture);
	}
	
	public ModelPart createModelUnderArm(HumanoidArm arm){
		return ArmorFactory.createModelArmorUnderArm(ModelArmorArm.Factory.createInstance(1.3f, 1.3f), arm, armorTexture);
	}
}