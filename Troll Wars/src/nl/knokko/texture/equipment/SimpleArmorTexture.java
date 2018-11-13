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

import nl.knokko.texture.pattern.TexturePattern;
import nl.knokko.texture.pattern.equipment.*;

public class SimpleArmorTexture implements ArmorTexture {
	
	protected final float shineDamper;
	protected final float reflectivity;
	
	protected final PatternHelmet helmet;
	protected final PatternChestplateTop chestplateTop;
	protected final PatternChestplate chestplate;
	protected final PatternUpperArm upperArm;
	protected final PatternElbow elbow;
	protected final PatternUnderArm underArm;
	protected final PatternGlobe globe;
	protected final PatternUpperLeg upperLeg;
	protected final PatternKnee knee;
	protected final PatternUnderLeg underLeg;
	protected final PatternShoe shoe;

	public SimpleArmorTexture(TexturePattern basePattern, float shineDamper, float reflectivity) {
		this.shineDamper = shineDamper;
		this.reflectivity = reflectivity;
		helmet = new PatternHelmet(basePattern);
		chestplateTop = new PatternChestplateTop(basePattern);
		chestplate = new PatternChestplate(basePattern);
		upperArm = new PatternUpperArm(basePattern);
		elbow = new PatternElbow(basePattern);
		underArm = new PatternUnderArm(basePattern);
		globe = new PatternGlobe(basePattern);
		upperLeg = new PatternUpperLeg(basePattern);
		knee = new PatternKnee(basePattern);
		underLeg = new PatternUnderLeg(basePattern);
		shoe = new PatternShoe(basePattern);
	}
	
	public float getShineDamper(){
		return shineDamper;
	}
	
	public float getReflectivity(){
		return reflectivity;
	}
	
	public PatternHelmet getHelmet(){
		return helmet;
	}
	
	public PatternChestplateTop getChestplateTop(){
		return chestplateTop;
	}
	
	public PatternChestplate getChestplate(){
		return chestplate;
	}
	
	public PatternUpperArm getUpperArm(){
		return upperArm;
	}
	
	public PatternElbow getElbow(){
		return elbow;
	}
	
	public PatternUnderArm getUnderArm(){
		return underArm;
	}
	
	public PatternGlobe getGlobe(){
		return globe;
	}
	
	public PatternUpperLeg getUpperLeg(){
		return upperLeg;
	}
	
	public PatternKnee getKnee(){
		return knee;
	}
	
	public PatternUnderLeg getUnderLeg(){
		return underLeg;
	}
	
	public PatternShoe getShoe(){
		return shoe;
	}
}