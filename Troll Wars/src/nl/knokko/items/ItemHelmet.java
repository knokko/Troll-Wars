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

import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.inventory.InventoryType;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.head.HeadProperties;
import nl.knokko.model.equipment.helmet.ModelArmorHead;
import nl.knokko.texture.equipment.ArmorTexture;
import nl.knokko.util.resources.Resources;

public class ItemHelmet extends ItemArmor {
	
	protected ArmorTexture armorTexture;
	
	public ItemHelmet(String name, ElementalStatistics elementStats, int armor, int resistance, ArmorTexture texture) {
		super(name, elementStats, armor, resistance);
		armorTexture = texture;
	}

	public ItemHelmet(String name, int armor, int resistance, ArmorTexture texture) {
		super(name, armor, resistance);
		armorTexture = texture;
	}

	@Override
	public final InventoryType getType() {
		return InventoryType.HELMET;
	}
	
	public ModelPart createModel(HeadProperties head){
		return Resources.createModelArmorHelmet(ModelArmorHead.Factory.createInstance(1.3f), head, armorTexture);
	}
}