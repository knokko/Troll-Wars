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
package nl.knokko.texture.area;

public class TextureArea {
	
	private int minX;
	private int minY;
	
	private final int width;
	private final int height;

	public TextureArea(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	@Override
	public String toString() {
		return "TextureArea(" + minX + "," + minY + "," + width + "," + height + ")";
	}
	
	public TextureArea(float width, float height){
		this((int) Math.ceil(width), (int) Math.ceil(height));
	}
	
	public TextureArea(double width, double height){
		this((int) Math.ceil(width), (int) Math.ceil(height));
	}
	
	public int getMinX(){
		return minX;
	}
	
	public int getMinY(){
		return minY;
	}
	
	public int getMaxX(){
		return minX + width - 1;
	}
	
	public int getMaxY(){
		return minY + height - 1;
	}
	
	public float getMinU(int textureWidth){
		return (float) minX / textureWidth;
	}
	
	public float getMinV(int textureHeight){
		return (float) minY / textureHeight;
	}
	
	public float getMaxU(int textureWidth){
		return (float) getMaxX() / textureWidth;
	}
	
	public float getMaxV(int textureHeight){
		return (float) getMaxY() / textureHeight;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void setMinX(int x){
		minX = x;
	}
	
	public void setMinY(int y){
		minY = y;
	}
}
