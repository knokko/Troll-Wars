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
