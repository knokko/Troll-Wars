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

import nl.knokko.model.equipment.weapon.ModelSword;
import nl.knokko.texture.area.TextureArea;

public class TextureMarkerSword extends TextureMarker {

	public TextureMarkerSword(ModelSword sword, float scale, int ppm) {
		super(
				createRectangle(sword.handleRadius() * 2 * scale, sword.handleRadius() * 2 * scale, ppm),
				createCilinder(sword.handleLength() * scale, sword.handleRadius() * scale, sword.handleRadius() * scale, ppm),
				createRectangle(sword.middleLength() * scale, sword.middleWidth() * scale, ppm),
				createRectangle(sword.middleLength() * scale, sword.middleWidth() * scale, ppm),
				createRectangle(sword.middleWidth() * scale, sword.middleLength() * scale, ppm),
				createRectangle(sword.middleWidth() * scale, sword.middleLength() * scale, ppm),
				createRectangle(sword.middleWidth() * scale, sword.middleWidth() * scale, ppm),
				createRectangle(sword.middleWidth() * scale, sword.middleWidth() * scale, ppm),
				createRectangle(sword.bladeLength() * scale, sword.bladeWidth() * scale, ppm),
				createRectangle(sword.bladeLength() * scale, sword.bladeWidth() * scale, ppm)
			);
	}
	
	public TextureArea getHandleBottom(){
		return areas[0];
	}
	
	public TextureArea getHandle(){
		return areas[1];
	}
	
	public TextureArea getMiddleFront(){
		return areas[2];
	}
	
	public TextureArea getMiddleBack(){
		return areas[3];
	}
	
	public TextureArea getMiddleTop(){
		return areas[4];
	}
	
	public TextureArea getMiddleBottom(){
		return areas[5];
	}
	
	public TextureArea getMiddleLeft(){
		return areas[6];
	}
	
	public TextureArea getMiddleRight(){
		return areas[7];
	}
	
	public TextureArea getBladeFront(){
		return areas[8];
	}
	
	public TextureArea getBladeBack(){
		return areas[9];
	}
}