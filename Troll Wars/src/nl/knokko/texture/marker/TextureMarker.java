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

import static java.lang.Math.PI;

import java.awt.Dimension;

import nl.knokko.texture.area.TextureArea;
import nl.knokko.util.Maths;

public class TextureMarker {
	
	public static Dimension setTextureAreas(TextureArea[] areas){
		int x = 0;
		int y = 0;
		int maxX = 0;
		int maxY = 0;
		for(TextureArea area : areas){
			area.setMinX(x);
			area.setMinY(y);
			maxX = area.getMaxX();
			if(area.getMaxY() > maxY)
				maxY = area.getMaxY();
			x = maxX + 1;
		}
		return new Dimension(Maths.next2Power(maxX + 1), Maths.next2Power(maxY + 1));
	}
	
	public static TextureArea createSphere(float height, float width, float depth, int ppm){
		return new TextureArea((width + depth) * 0.5 * PI * ppm, height * 0.5 * PI * ppm);
	}
	
	public static TextureArea createHalfSphere(float halfHeight, float width, float depth, int ppm){
		return new TextureArea((width + depth) * 0.5 * PI * ppm, halfHeight * 0.5 * PI * ppm);
	}
	
	public static TextureArea createCilinder(float height, float width, float depth, int ppm){
		return new TextureArea((width + depth) * 0.5 * PI * ppm, height * ppm);
	}
	
	public static TextureArea createApproachingCilinder(float radius1, float radius2, float length, int ppm){
		return new TextureArea((radius1 + radius2) * PI * ppm, length * ppm);
	}
	
	public static TextureArea createRectangle(float width, float height, int ppm){
		return new TextureArea(width * ppm, height * ppm);
	}
	
	public static TextureArea createCone(float radiusX, float radiusZ, float length, int ppm){
		float radius = (float) Math.sqrt(length * length + (radiusX + radiusZ) * (radiusX + radiusZ) / 4);
		return new TextureArea(2 * radius * ppm, 2 * radius * ppm);
	}
	
	protected TextureArea[] areas;
	
	protected int width;
	protected int height;

	public TextureMarker(TextureArea...areas) {
		this.areas = areas;
		Dimension dim = setTextureAreas(areas);
		this.width = dim.width;
		this.height = dim.height;
	}
	
	public TextureArea getArea(int index){
		return areas[index];
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getAmount(){
		return areas.length;
	}
}