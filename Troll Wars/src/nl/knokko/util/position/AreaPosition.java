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
package nl.knokko.util.position;

public class AreaPosition {
	
	private int x;
	private int y;
	private int z;

	public AreaPosition(int x, int y, int z) {
		setPosition(x, y, z);
	}
	
	public AreaPosition(SpawnPosition position){
		setPosition(position);
	}
	
	public void setPosition(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void setPosition(SpawnPosition position){
		x = position.getTileX() * 32;
		y = position.getTileY() * 8;
		z = position.getTileZ() * 32;
	}
	
	public void move(int dx, int dy, int dz){
		x += dx;
		y += dy;
		z += dz;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getZ(){
		return z;
	}
	
	public int getTileX(){
		return x / 32;
	}
	
	public int getTileY(){
		return y / 8;
	}
	
	public int getTileZ(){
		return z / 32;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public void setZ(int z){
		this.z = z;
	}
	
	@Override
	public String toString(){
		return "AreaPosition[" + x + "," + y + "," + z + "]";
	}
}
