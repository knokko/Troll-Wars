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

import nl.knokko.main.Game;
import nl.knokko.model.body.BodyBird;
import nl.knokko.texture.area.TextureArea;

public class TextureMarkerBird extends TextureMarker {

	private TextureMarkerBird(BodyBird model, int ppm) {
		super(
				createSphere(model.headHeight(), model.headWidth(), model.headDepth(), ppm),
				createApproachingCilinder(model.snailRadius(), 0, model.snailLength(), ppm),
				createSphere(model.bellyHeight(), model.bellyWidth(), model.bellyDepth(), ppm),
				createRectangle(model.wingPartMaxLength(), model.wingPartHeight() * model.wingPartLengths().length, ppm),
				createRectangle(model.wingPartMaxLength(), model.wingPartHeight() * model.wingPartLengths().length, ppm),
				createCilinder(model.legLength(), model.legRadius(), model.legRadius(), ppm),
				createCilinder(model.legLength(), model.legRadius(), model.legRadius(), ppm),
				createCilinder(model.nailLength(), model.nailAmount() * model.nailRadius(), model.nailAmount() * model.nailRadius(), ppm),
				createCilinder(model.nailLength(), model.nailAmount() * model.nailRadius(), model.nailAmount() * model.nailRadius(), ppm)
				);
	}
	
	public TextureMarkerBird(BodyBird model){
		this(model, Game.getOptions().pixelsPerMeter);
	}
	
	public TextureArea getHead(){
		return areas[0];
	}
	
	public TextureArea getSnail(){
		return areas[1];
	}
	
	public TextureArea getBelly(){
		return areas[2];
	}
	
	public TextureArea getLeftWing(){
		return areas[3];
	}
	
	public TextureArea getRightWing(){
		return areas[4];
	}
	
	public TextureArea getLeftLeg(){
		return areas[5];
	}
	
	public TextureArea getRightLeg(){
		return areas[6];
	}
	
	public TextureArea getLeftClaw(){
		return areas[7];
	}
	
	public TextureArea getRightClaw(){
		return areas[8];
	}
}
