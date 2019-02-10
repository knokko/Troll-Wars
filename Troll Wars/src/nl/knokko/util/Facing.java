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
package nl.knokko.util;

public enum Facing {
	
	NORTH(0),
	EAST(90),
	SOUTH(180),
	WEST(270);
	
	public static Facing fromYaw(float yaw){
		while(yaw > 360)
			yaw -= 360;
		while(yaw < 0)
			yaw += 360;
		if(yaw <= 45 || yaw >= 315)
			return NORTH;
		if(yaw >= 45 && yaw <= 135)
			return EAST;
		if(yaw >= 135 && yaw <= 225)
			return SOUTH;
		if(yaw >= 225 && yaw <= 315)
			return WEST;
		throw new IllegalArgumentException("Invalid rotation: " + yaw);
	}
	
	public static Facing fromXZ(int dx, int dz){
		if(dx > 0)
			return EAST;
		if(dx < 0)
			return WEST;
		if(dz > 0)
			return SOUTH;
		if(dz < 0)
			return NORTH;
		throw new IllegalArgumentException("No valid facing for (" + dx + "," + dz + ")");
	}
	
	private final float yaw;
	private final float radYaw;
	
	Facing(float yaw){
		this.yaw = yaw;
		this.radYaw = (float) Math.toRadians(yaw);
	}
	
	public Facing getOpposite(){
		if(this == NORTH)
			return SOUTH;
		if(this == EAST)
			return WEST;
		if(this == SOUTH)
			return NORTH;
		return EAST;
	}
	
	public float getDegreeYaw(){
		return yaw;
	}
	
	public float getRadianYaw(){
		return radYaw;
	}
	
	public boolean onXAxis(){
		return this == EAST || this == WEST;
	}
	
	public boolean onZAxis(){
		return this == SOUTH || this == NORTH;
	}
	
	public byte getDX(){
		if(this == EAST)
			return 1;
		if(this == WEST)
			return -1;
		return 0;
	}
	
	public byte getDZ(){
		if(this == SOUTH)
			return 1;
		if(this == NORTH)
			return -1;
		return 0;
	}
}
