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
package nl.knokko.texture.pattern;

import java.util.Random;

import nl.knokko.texture.factory.TextureBuilder;
import nl.knokko.util.color.Color;
import nl.knokko.util.color.ColorAlpha;

public class PatternSpirit extends TexturePattern {
	
	protected final Color color;
	protected final float maxDifference;
	protected final long seed;

	public PatternSpirit(Color color, float maxDifference, long seed) {
		this.color = color;
		this.maxDifference = maxDifference;
		this.seed = seed;
	}

	@Override
	public void paintBetween(TextureBuilder texture, int minX, int minY, int maxX, int maxY) {
		Random random = new Random(seed);
		for(int x = minX; x <= maxX; x++){
			for(int y = minY; y <= maxY; y++){
				texture.setPixel(x, y, new ColorAlpha(dif(random, color.getRedI()), dif(random, color.getGreenI()), dif(random, color.getBlueI()), dif(random, color.getAlphaI())));
			}
		}
	}
	
	private int dif(Random random, int original){
		return Math.min(Math.max((int) (original * (1 - maxDifference) + random.nextFloat() * maxDifference * 2), 0), 255);
	}
}