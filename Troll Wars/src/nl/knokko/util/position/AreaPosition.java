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
