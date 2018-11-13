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
package nl.knokko.texture.equipment;

import nl.knokko.model.equipment.weapon.ModelSpear;
import nl.knokko.model.equipment.weapon.ModelSword;
import nl.knokko.texture.painter.SpearPainter;
import nl.knokko.texture.painter.SwordPainter;
import nl.knokko.texture.pattern.PatternAverage;
import nl.knokko.texture.pattern.PatternMetal;
import nl.knokko.texture.pattern.PatternWood;
import nl.knokko.util.color.Color;

public class SimpleWeaponTextures implements WeaponTextures {
	
	protected float shineDamper;
	protected float reflectivity;
	
	protected ModelSword swordModel;
	protected SwordPainter swordPainter;
	
	protected ModelSpear spearModel;
	protected SpearPainter spearPainter;
	
	public static SimpleWeaponTextures createRockSet(Color handleColor, Color rockColor){
		return new SimpleWeaponTextures(1f, 0.2f, 
				ModelSword.Factory.createSimple(), new SwordPainter(new PatternWood(handleColor, 0.1f, 23891436), new PatternAverage(rockColor, 0.2f, 17465)),
				ModelSpear.Factory.createSimple(), new SpearPainter(new PatternWood(handleColor, 0.1f, -5476328), new PatternAverage(rockColor, 0.2f, 1115429))
		);
	}
	
	public static SimpleWeaponTextures createMetalSet(Color handleColor, Color metalColor){
		return new SimpleWeaponTextures(1f, 0.2f, 
				ModelSword.Factory.createSimple(), new SwordPainter(new PatternWood(handleColor, 0.1f, 23891436), new PatternMetal(metalColor, 17465)),
				ModelSpear.Factory.createSimple(), new SpearPainter(new PatternWood(handleColor, 0.1f, -5476328), new PatternMetal(metalColor, 1115429))
		);
	}

	public SimpleWeaponTextures(float shineDamper, float reflectivity, ModelSword swordModel, SwordPainter swordPainter,
			ModelSpear spearModel, SpearPainter spearPainter) {
		this.shineDamper = shineDamper;
		this.reflectivity = reflectivity;
		this.swordModel = swordModel;
		this.swordPainter = swordPainter;
		this.spearModel = spearModel;
		this.spearPainter = spearPainter;
	}

	@Override
	public float getShineDamper() {
		return shineDamper;
	}

	@Override
	public float getReflectivity() {
		return reflectivity;
	}

	@Override
	public ModelSword getSwordModel() {
		return swordModel;
	}

	@Override
	public SwordPainter getSwordPainter() {
		return swordPainter;
	}

	@Override
	public ModelSpear getSpearModel() {
		return spearModel;
	}

	@Override
	public SpearPainter getSpearPainter() {
		return spearPainter;
	}
}