package nl.knokko.area;

import nl.knokko.main.Game;

public class AreaDoor {
	
	private final Location location;
	private final Location destination;
	
	private final float destinationYaw;
	
	private final short id;

	public AreaDoor(Location doorLocation, Location doorDestination, float destinationYaw, short destinationID) {
		location = doorLocation;
		destination = doorDestination;
		id = destinationID;
		this.destinationYaw = destinationYaw;
	}
	
	public Location getLocation(){
		return location;
	}
	
	public void transferPlayer(){
		Game.getArea().transferPlayer(id, destination, destinationYaw);
	}
	
	public static class Location implements Comparable<Location> {
		
		private final int x;
		private final byte y;
		private final int z;
		
		public Location(int x, int y, int z){
			this.x = x;
			this.y = (byte)(y - 128);
			this.z = z;
		}
		
		@Override
		public String toString(){
			return "AreaDoor Location(" + x + "," + y + "," + z + ")";
		}
		
		@Override
		public int hashCode(){
			return x + y * 1000 + z * 1000000;
		}
		
		@Override
		public int compareTo(Location l){
			if(l.x > x)
				return -1;
			if(l.x < x)
				return 1;
			if(l.y > y)
				return -1;
			if(l.y < y)
				return 1;
			if(l.z > z)
				return -1;
			if(l.z < z)
				return 1;
			return 0;
		}
		
		@Override
		public boolean equals(Object other){
			if(other instanceof Location){
				Location l = (Location) other;
				return l.x == x && l.y == y && l.z == z;
			}
			return false;
		}
		
		public int getTileX(){
			return x;
		}
		
		public int getTileY(){
			return y + 128;
		}
		
		public byte getY(){
			return y;
		}
		
		public int getTileZ(){
			return z;
		}
	}
}
