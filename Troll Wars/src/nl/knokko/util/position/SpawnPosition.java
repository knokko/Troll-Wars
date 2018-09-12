package nl.knokko.util.position;

public class SpawnPosition {
	
	private final int tileX;
	private final int tileY;
	private final int tileZ;
	
	/**
	 * @param x the tileX
	 * @param y the tileY
	 * @param z the tileZ
	 */
	public SpawnPosition(int x, int y, int z){
		tileX = x;
		tileY = y;
		tileZ = z;
	}
	
	public int getTileX(){
		return tileX;
	}
	
	public int getTileY(){
		return tileY;
	}
	
	public int getTileZ(){
		return tileZ;
	}
}
