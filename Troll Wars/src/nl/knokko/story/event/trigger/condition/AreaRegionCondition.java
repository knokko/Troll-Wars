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
package nl.knokko.story.event.trigger.condition;

import nl.knokko.area.Area;

public class AreaRegionCondition implements TriggerCondition {
	
	private final Class<? extends Area> area;
	private final Region region;

	public AreaRegionCondition(Class<? extends Area> area, Region region) {
		this.area = area;
		this.region = region;
	}

	@Override
	public boolean trigger(int xFrom, int yFrom, int zFrom, int xTo, int yTo, int zTo, Class<? extends Area> area) {
		return area == this.area && region.isInside(xTo, yTo, zTo);
	}
	
	public static interface Region {
		
		boolean isInside(int x, int y, int z);
		
		public static class LineX implements Region {
			
			private final int minX;
			private final int maxX;
			
			private final int y;
			private final int z;
			
			public LineX(int minX, int maxX, int y, int z){
				this.minX = minX;
				this.maxX = maxX;
				this.y = y;
				this.z = z;
			}

			@Override
			public boolean isInside(int x, int y, int z) {
				return x >= minX && x <= maxX && y == this.y && z == this.z;
			}
		}
		
		public static class LineY implements Region {
			
			private final int minY;
			private final int maxY;
			
			private final int x;
			private final int z;
			
			public LineY(int minY, int maxY, int x, int z){
				this.minY = minY;
				this.maxY = maxY;
				this.x = x;
				this.z = z;
			}

			@Override
			public boolean isInside(int x, int y, int z) {
				return x == this.x && y >= minY && y <= maxY && z == this.z;
			}
		}
		
		public static class LineZ implements Region {
			
			private final int minZ;
			private final int maxZ;
			
			private final int x;
			private final int y;
			
			public LineZ(int minZ, int maxZ, int x, int y){
				this.minZ = minZ;
				this.maxZ = maxZ;
				this.x = x;
				this.y = y;
			}

			@Override
			public boolean isInside(int x, int y, int z) {
				return z >= minZ && z <= maxZ && y == this.y && x == this.x;
			}
		}
	}
}
