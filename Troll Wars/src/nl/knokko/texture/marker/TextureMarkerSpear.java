/* 
 * The MIT License
 *
 * Copyright 2018 knokko.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
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
 */
package nl.knokko.texture.marker;

import nl.knokko.model.equipment.weapon.ModelSpear;
import nl.knokko.texture.area.TextureArea;

public class TextureMarkerSpear extends TextureMarker {

	public TextureMarkerSpear(ModelSpear spear, float scale, int ppm) {
		super(
				createCilinder(spear.stickLength() * scale, spear.stickRadius() * scale * 2, spear.stickRadius() * scale * 2, ppm),
				createRectangle(spear.stickRadius() * scale * 2, spear.stickRadius() * scale * 2, ppm),
				createRectangle((float) (Math.sqrt(2 * spear.pointMaxRadius() * spear.pointMaxRadius()) * scale), (float) (Math.sqrt(2 * spear.pointMaxRadius() * spear.pointMaxRadius()) * scale), ppm),
				createRectangle((float) (Math.sqrt(2 * spear.pointMaxRadius() * spear.pointMaxRadius()) * scale), spear.pointLength() * scale, ppm),
				createRectangle((float) (Math.sqrt(2 * spear.pointMaxRadius() * spear.pointMaxRadius()) * scale), spear.pointLength() * scale, ppm)
				);
	}
	
	public TextureArea getStick(){
		return areas[0];
	}
	
	public TextureArea getStickBottom(){
		return areas[1];
	}
	
	public TextureArea getPointBottom(){
		return areas[2];
	}
	
	public TextureArea getPointNES(){
		return areas[3];
	}
	
	public TextureArea getPointSWN(){
		return areas[4];
	}
}
