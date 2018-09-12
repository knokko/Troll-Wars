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
