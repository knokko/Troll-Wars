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
package nl.knokko.util.color;


public class ColorAlpha extends Color {
	
	public static final ColorAlpha TRANSPARENT = new ColorAlpha(0, 0, 0, 0);
	
	private final byte alpha;
	
	public ColorAlpha(byte red, byte green, byte blue, byte alpha){
		super(red, green, blue);
		this.alpha = alpha;
	}

	public ColorAlpha(int red, int green, int blue, int alpha) {
		super(red, green, blue);
		this.alpha = (byte) alpha;
	}
	
	@Override
	public byte getAlpha(){
		return alpha;
	}
	
	@Override
	public int getAlphaI(){
		return alpha & (0xff);
	}
	
	@Override
	public float getAlphaF(){
		return getAlphaI() / 255f;
	}
	
	@Override
	public boolean equals(Object other){
		if(other.getClass() == Color.class && alpha == -1){
			Color c = (Color) other;
			return c.getRed() == getRed() && c.getGreen() == getGreen() && c.getBlue() == getBlue();
		}
		if(other.getClass() == ColorAlpha.class){
			ColorAlpha c = (ColorAlpha) other;
			return c.getRed() == getRed() && c.getGreen() == getGreen() && c.getBlue() == getBlue() && c.alpha == alpha;
		}
		return false;
	}
}
