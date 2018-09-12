package nl.knokko.area;

import java.nio.ByteBuffer;

import nl.knokko.tiles.Tile;
import nl.knokko.tiles.Tiles;

/**
 * The Block class holds a Tile at a Y-coordinate.
 * Unlike the Tile class, many Blocks can be instantiated for the same tile.
 * @author knokko
 *
 */
public class Block {
	
	private short tile;
	private byte y;

	public Block(byte[] tile, byte tileY) {
		this.y = tileY;
		this.tile = ByteBuffer.wrap(tile).getShort();
	}
	
	public Block(byte tileID1, byte tileID2, byte tileY){
		this(new byte[]{tileID1, tileID2}, tileY);
	}
	
	public short getTileID(){
		return tile;
	}
	
	public byte getTileY(){
		return y;
	}
	
	public Tile getTile(){
		return Tiles.fromRuntimeID(tile);
	}
	
	@Override
	public String toString(){
		return "Block[tile = " + getTile().getAbsoluteID() + ", y = " + y + "]";
	}
}
