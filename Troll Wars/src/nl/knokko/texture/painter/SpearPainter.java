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
package nl.knokko.texture.painter;

import nl.knokko.texture.pattern.PatternCircle;
import nl.knokko.texture.pattern.TexturePattern;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.color.Color;

public class SpearPainter extends ModelPainter {

	public SpearPainter(TexturePattern stickPattern, TexturePattern pointPattern) {
		super(stickPattern, new PatternCircle(stickPattern, Color.TRANSPARENT), pointPattern);
	}

	@Override
	public void save(BitOutput buffer) {
		throw new UnsupportedOperationException("A spear painter can't save itself!");
	}
	
	public TexturePattern getStick(){
		return patterns[0];
	}
	
	public TexturePattern getStickBottom(){
		return patterns[1];
	}
	
	public TexturePattern getPoint(){
		return patterns[2];
	}
}