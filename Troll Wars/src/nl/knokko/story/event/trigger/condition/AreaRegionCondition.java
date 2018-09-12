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
