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

import nl.knokko.texture.builder.TextureBuilder;
import nl.knokko.texture.builder.drawing.GeometryDrawer;
import nl.knokko.util.Maths;
import nl.knokko.util.color.Color;

public class PatternFace extends PatternAverage {
	
	protected Color hairColor;

	public PatternFace(Color color, Color hairColor, float maxDifference, long seed) {
		super(color, maxDifference, seed);
		this.hairColor = hairColor;
	}
	
	@Override
	public void paintBetween(TextureBuilder tb, int minX, int minY, int maxX, int maxY){
		super.paintBetween(tb, minX, minY, maxX, maxY);
		Random random = new Random(seed);
		int deltaX = maxX - minX;
		int deltaY = maxY - minY;
		GeometryDrawer g = tb.geometry();
		for(int x = minX; x <= maxX; x++)
			g.drawVerticalLine(maxY - random.nextInt(deltaY / 5) - deltaY / 6, maxY, x, hairColor);
		int faceX = ((minX + maxX) / 2 + maxX) / 2;
		int eyeY = (minY + maxY) / 2;
		g.fillOval(faceX - deltaX / 12, eyeY, deltaX / 16, deltaY / 12, Color.WHITE);
		g.fillOval(faceX + deltaX / 12, eyeY, deltaX / 16, deltaY / 12, Color.WHITE);
		g.fillOval(faceX - deltaX / 12, eyeY, deltaX / 24, deltaY / 18, Color.GREEN);
		g.fillOval(faceX + deltaX / 12, eyeY, deltaX / 24, deltaY / 18, Color.GREEN);
		g.fillOval(faceX - deltaX / 12, eyeY, deltaX / 48, deltaY / 36, Color.BLACK);
		g.fillOval(faceX + deltaX / 12, eyeY, deltaX / 48, deltaY / 36, Color.BLACK);
		int ms = deltaY / 24;
		for(int x = faceX - deltaX / 8; x <= faceX + deltaX / 8; x++){
			int y = (int) (minY + deltaY / 4 - Maths.cos((x - faceX) * 90 / (deltaX / 8)) * deltaY / 16);
			boolean flag = x == faceX - deltaX / 8 || x == faceX + deltaX / 8;
			tb.setPixel(x, y - ms, Color.RED);
			g.drawVerticalLine(y - ms + 1, y + ms - 1, x, flag ? Color.RED : Color.BLACK);
			tb.setPixel(x, y + ms, Color.RED);
		}
	}
}
