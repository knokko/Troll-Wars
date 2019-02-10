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
package nl.knokko.texture.pattern;

import nl.knokko.texture.factory.TextureBuilder;
import nl.knokko.util.color.Color;

public class PatternCircle extends TexturePattern {
	
	private final TexturePattern pattern;
	
	private final Color color;
	
	private final double radius;

	public PatternCircle(TexturePattern pattern, Color outOfCircleColor, double radius) {
		this.pattern = pattern;
		this.color = outOfCircleColor;
		this.radius = radius;
	}
	
	public PatternCircle(TexturePattern pattern, Color outOfCircleColor){
		this(pattern, outOfCircleColor, Double.NaN);
	}

	@Override
	public void paintBetween(TextureBuilder texture, int minX, int minY, int maxX, int maxY) {
		pattern.paintBetween(texture, minX, minY, maxX, maxY);
		double midX = (maxX + minX) / 2.0;
		double midY = (maxY + minY) / 2.0;
		double r = radius == radius ? radius : Math.min((maxX - minX) / 2, (maxY - minY) / 2);
		for(int x = minX; x <= maxX; x++){
			for(int y = minY; y <= maxY; y++){
				double dx = x - midX;
				double dy = y - midY;
				if(Math.sqrt(dx * dx + dy * dy) > r)
					texture.setPixel(x, y, color);
			}
		}
	}
}
