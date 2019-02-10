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
package nl.knokko.texture.marker;

import nl.knokko.model.body.BodyMyrre;
import nl.knokko.texture.area.TextureArea;

public class TextureMarkerMyrre extends TextureMarker {

	public TextureMarkerMyrre(BodyMyrre body, int ppm) {
		super(
				createSphere(body.bellyHeight(), body.bellyWidth(), body.bellyDepth(), ppm),
				createCilinder(body.armLength(), body.armRadius() * 2, body.armRadius() * 2, ppm),
				createCilinder(body.armLength(), body.armRadius() * 2, body.armRadius() * 2, ppm),
				createRectangle(body.nailHandLength(), body.armRadius() * 2 / 3, ppm),
				createRectangle(body.nailHandLength(), body.armRadius() * 2 / 3, ppm),
				createCilinder(body.legLength(), body.legRadius() * 2, body.legRadius() * 2, ppm),
				createCilinder(body.legLength(), body.legRadius() * 2, body.legRadius() * 2, ppm),
				createRectangle(body.nailFootLength(), body.legRadius() * 2 / 3, ppm),
				createRectangle(body.nailFootLength(), body.legRadius() * 2 / 3, ppm)
		);
	}
	
	public TextureArea getBelly(){
		return areas[0];
	}
	
	public TextureArea getLeftArm(){
		return areas[1];
	}
	
	public TextureArea getRightArm(){
		return areas[2];
	}
	
	public TextureArea getLeftHandClaws(){
		return areas[3];
	}
	
	public TextureArea getRightHandClaws(){
		return areas[4];
	}
	
	public TextureArea getLeftLeg(){
		return areas[5];
	}
	
	public TextureArea getRightLeg(){
		return areas[6];
	}
	
	public TextureArea getLeftFootClaws(){
		return areas[7];
	}
	
	public TextureArea getRightFootClaws(){
		return areas[8];
	}
}