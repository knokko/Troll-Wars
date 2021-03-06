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

import nl.knokko.texture.builder.TextureBuilder;
import nl.knokko.util.color.Color;

public class PatternBirdFace extends PatternAverage {

	public PatternBirdFace(Color color, float maxDifference, long seed) {
		super(color, maxDifference, seed);
	}
	
	@Override
	public void paintBetween(TextureBuilder tb, int minX, int minY, int maxX, int maxY){
		super.paintBetween(tb, minX, minY, maxX, maxY);
		int deltaX = maxX - minX;
		int deltaY = maxY - minY;
		int faceX = ((minX + maxX) / 2 + maxX) / 2;
		int eyeY = maxY - deltaY / 4;
		tb.geometry().fillOval(faceX - deltaX / 12, eyeY, deltaX / 16, deltaY / 12, Color.WHITE);
		tb.geometry().fillOval(faceX + deltaX / 12, eyeY, deltaX / 16, deltaY / 12, Color.WHITE);
		tb.geometry().fillOval(faceX - deltaX / 12, eyeY, deltaX / 24, deltaY / 18, Color.GREEN);
		tb.geometry().fillOval(faceX + deltaX / 12, eyeY, deltaX / 24, deltaY / 18, Color.GREEN);
		tb.geometry().fillOval(faceX - deltaX / 12, eyeY, deltaX / 48, deltaY / 36, Color.BLACK);
		tb.geometry().fillOval(faceX + deltaX / 12, eyeY, deltaX / 48, deltaY / 36, Color.BLACK);
	}
}