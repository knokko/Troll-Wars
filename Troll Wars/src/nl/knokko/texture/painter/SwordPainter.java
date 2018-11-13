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

public class SwordPainter extends ModelPainter {
	
	public SwordPainter(TexturePattern handlePattern, TexturePattern bladePattern){
		this(handlePattern, handlePattern, bladePattern);
	}

	public SwordPainter(TexturePattern handlePattern, TexturePattern middlePattern, TexturePattern bladePattern) {
		super(new PatternCircle(handlePattern, Color.TRANSPARENT), handlePattern, middlePattern, bladePattern);
	}

	@Override
	public void save(BitOutput buffer) {
		throw new UnsupportedOperationException("A sword can't save itself!");
	}
	
	public TexturePattern getHandleBottom(){
		return patterns[0];
	}
	
	public TexturePattern getHandle(){
		return patterns[1];
	}
	
	public TexturePattern getMiddle(){
		return patterns[2];
	}
	
	public TexturePattern getBlade(){
		return patterns[3];
	}
}