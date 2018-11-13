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
package nl.knokko.texture.marker;

import nl.knokko.model.equipment.weapon.ModelBone;
import nl.knokko.texture.area.TextureArea;

public class TextureMarkerBone extends TextureMarker {

	public TextureMarkerBone(ModelBone bone, float scale, int ppm) {
		super(
				createSphere(bone.boneRadiusTopY() * scale * 2, bone.boneRadiusTopX() * scale * 2, bone.boneRadiusTopZ() * scale * 2, ppm),
				createSphere(bone.boneRadiusTopY() * scale * 2, bone.boneRadiusTopX() * scale * 2, bone.boneRadiusTopZ() * scale * 2, ppm),
				createSphere(bone.boneRadiusTopY() * scale * 2, bone.boneRadiusTopX() * scale * 2, bone.boneRadiusTopZ() * scale * 2, ppm),
				createSphere(bone.boneRadiusTopY() * scale * 2, bone.boneRadiusTopX() * scale * 2, bone.boneRadiusTopZ() * scale * 2, ppm),
				createCilinder(bone.boneLength() * scale * 2, bone.boneRadiusX() * scale * 2, bone.boneRadiusZ() * scale * 2, ppm)
				);
	}
	
	public TextureArea getSphere1(){
		return areas[0];
	}
	
	public TextureArea getSphere2(){
		return areas[1];
	}
	
	public TextureArea getSphere3(){
		return areas[2];
	}
	
	public TextureArea getSphere4(){
		return areas[3];
	}
	
	public TextureArea getCilinder(){
		return areas[4];
	}
}
