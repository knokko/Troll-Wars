/* 
 * The MIT License
 *
 * Copyright 2018 knokko.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
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
 */
package nl.knokko.area;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;

import nl.knokko.render.tile.TileRenderMap;
import nl.knokko.tiles.Tile;
import nl.knokko.tiles.Tiles;

public class TileMap {
	
	/**
	 * The first byte is -128
	 * The next 4 bytes are used to store the width
	 * The next 4 bytes are used to store the depth
	 * Loops through [0,0] --> [1,0] --> [2,0] ... [width - 1,0] --> [0,1] --> [1,1] --> [2,1] ... [width - 1,1] ... [0, depth - 1] ... [width - 1, depth - 1]
	 * For every [x,z], the first byte is the amount of tiles, thereafter, 3 * amount bytes are stored for the actual data
	 */
	private static final byte ENCODING_WD_ARRAYS = -128;
	
	private final int width;
	private final int depth;
	
	private byte[][] tiles;
	
	//private Matrix4f[][] renderMap;
	private TileRenderMap renderMap2;
	//private Map<Point,Matrix4f[]> renderMapPoints;

	public TileMap(int width, int depth, boolean allowRemove) {
		this.width = width;
		this.depth = depth;
		tiles = new byte[width * depth][];
		//renderMap = new Matrix4f[Tiles.getAmount()][];
		renderMap2 = new TileRenderMap();
		//if(allowRemove)
			//renderMapPoints = new HashMap<Point,Matrix4f[]>();
	}
	
	private int getIndex(int tileX, int tileZ){
		return tileX + tileZ * width;
	}
	
	private byte[] getRawTiles(int tileX, int tileZ){
		int index = getIndex(tileX, tileZ);
		return index < tiles.length && index >= 0 ? tiles[index] : null;
	}
	
	public Block[] getBlocks(int tileX, int tileZ){
		byte[] bytes = getRawTiles(tileX, tileZ);
		if(bytes == null)
			return new Block[0];
		Block[] blocks = new Block[bytes.length / 3];
		for(int i = 0; i < blocks.length; i++)
			blocks[i] = new Block(bytes[i * 3], bytes[i * 3 + 1], bytes[i * 3 + 2]);
		return blocks;
	}
	
	/*
	public Matrix4f[][] getRenderMap(){
		return renderMap;
	}
	*/
	
	public TileRenderMap getNewRenderMap(){
		return renderMap2;
	}
	
	public void placeTile(Tile tile, int tileX, int tileY, int tileZ){
		int index = getIndex(tileX, tileZ);
		byte[] data = tiles[index];
		byte[] newData = data == null ? new byte[3] : new byte[data.length + 3];
		if(data != null)
			for(int i = 0; i < data.length; i++)
				newData[i] = data[i];
		byte[] id = ByteBuffer.allocate(2).putShort(tile.getRuntimeID()).array();
		newData[newData.length - 3] = id[0];
		newData[newData.length - 2] = id[1];
		newData[newData.length - 1] = (byte) (tileY - 128);
		tiles[index] = newData;
		addToRenderMap(tile.getRuntimeID(), tileX, tileY, tileZ);
	}
	
	public void fillTiles(Tile tile, int tileX1, int tileY1, int tileZ1, int tileX2, int tileY2, int tileZ2){
		int minX = Math.min(tileX1, tileX2);
		int minY = Math.min(tileY1, tileY2);
		int minZ = Math.min(tileZ1, tileZ2);
		int maxX = Math.max(tileX1, tileX2);
		int maxY = Math.max(tileY1, tileY2);
		int maxZ = Math.max(tileZ1, tileZ2);
		for(int x = minX; x <= maxX; x++)
			for(int y = minY; y <= maxY; y += tile.getHeight())
				for(int z = minZ; z <= maxZ; z++)
					placeTile(tile, x, y, z);
	}
	
	public void removeTiles(int tileX, int tileY, int tileZ){
		removeFromRenderMap(tileX, tileY, tileZ);
		int index = getIndex(tileX, tileZ);
		byte[] data = tiles[index];
		if(data == null)
			return;
		for(int i = 0; i < data.length; i += 3){
			int y = data[i + 2] + 128;
			if(y == tileY){
				if(data.length == 3){
					tiles[index] = null;
					return;
				}
				else {
					byte[] newData = new byte[data.length - 3];
					System.arraycopy(data, 0, newData, 0, i);
					System.arraycopy(data, i + 3, newData, i, newData.length - i);
					i -= 3;
					data = newData;
				}
			}
		}
		tiles[index] = data;
	}
	
	public static TileMap load(ArrayList<Byte> bytes, int index, boolean allowRemove){
		byte encoding = bytes.get(index);
		if(encoding == ENCODING_WD_ARRAYS)
			return loadWDArrays(bytes, index + 1, allowRemove);
		throw new IllegalArgumentException("Unknown encoding: " + encoding);
	}
	
	private static TileMap loadWDArrays(ArrayList<Byte> bytes, int index, boolean allowRemove){
		ByteBuffer buffer = ByteBuffer.allocate(8);
		for(int i = 0; i < 8; i++)
			buffer.put(bytes.get(i + index));
		buffer.flip();
		index += 8;
		TileMap map = new TileMap(buffer.getInt(), buffer.getInt(), allowRemove);
		for(int i = 0; i < map.tiles.length; i++){
			int amount = bytes.get(index) + 128;
			index++;
			if(amount > 0){
				byte[] current = new byte[amount * 3];
				ArrayList<Byte> ys = new ArrayList<Byte>();
				ArrayList<Short> aIDs = new ArrayList<Short>();
				for(int j = 0; j < current.length; j += 3){
					short absoluteID = ByteBuffer.wrap(new byte[]{bytes.get(j + index), bytes.get(j + index + 1)}).getShort();
					short runtimeIDS = Tiles.randomFromAbsoluteID(absoluteID).getRuntimeID();
					byte[] runtimeID = ByteBuffer.allocate(2).putShort(runtimeIDS).array();
					boolean flag = true;
					for(int k = 0; k < ys.size(); k++){
						if(bytes.get(index + j + 2) == ys.get(k) && absoluteID == aIDs.get(k))
							flag = false;
					}
					if(flag){
						map.addToRenderMap(runtimeIDS, bytes.get(index + j + 2) + 128, i);
						current[j] = runtimeID[0];
						current[j + 1] = runtimeID[1];
						current[j + 2] = bytes.get(index + j + 2);
						ys.add(bytes.get(index + j + 2));
						aIDs.add(absoluteID);
					}
					else {
						j -= 3;
						index += 3;
						current = Arrays.copyOf(current, current.length - 3);
					}
				}
				index += current.length;
				map.tiles[i] = current;
			}
			//if amount is 0, the tiles[i] will stay null
		}
		return map;
	}
	
	public void save(ArrayList<Byte> bytes){
		saveWDArrays(bytes);
	}
	
	private void saveWDArrays(ArrayList<Byte> bytes){
		bytes.add(ENCODING_WD_ARRAYS);
		byte[] sizeArray = ByteBuffer.allocate(8).putInt(width).putInt(depth).array();
		for(byte b : sizeArray)
			bytes.add(b);
		for(byte[] data : tiles){
			if(data != null){
				bytes.add((byte) (data.length / 3 - 128));
				for(int i = 0; i < data.length; i += 3){
					short runtimeID = ByteBuffer.wrap(new byte[]{data[i], data[i + 1]}).getShort();
					short absoluteID = Tiles.fromRuntimeID(runtimeID).getAbsoluteID();
					byte[] id = ByteBuffer.allocate(2).putShort(absoluteID).array();
					bytes.add(id[0]);
					bytes.add(id[1]);
					bytes.add(data[i + 2]);
				}
			}
			else
				bytes.add((byte) -128);
		}
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getDepth(){
		return depth;
	}
	
	public int getMinX(){
		return 0;
	}
	
	public int getMinZ(){
		return 0;
	}
	
	public int getMaxX(){
		return width * 32 + 32;
	}
	
	public int getMaxZ(){
		return depth * 32 + 32;
	}
	
	public int getMinTileX(){
		return 0;
	}
	
	public int getMinTileZ(){
		return 0;
	}
	
	public int getMaxTileX(){
		return width - 1;
	}
	
	public int getMaxTileZ(){
		return depth - 1;
	}
	
	public void clearDuplicates(){
		int count = 0;
		int index = 0;
		for(byte[] entries : tiles){
			if(entries != null){
				List<ABEntry> list = new ArrayList<ABEntry>();
				boolean replace = false;
				for(int i = 0; i < entries.length; i += 3){
					ABEntry entry = new ABEntry(entries, i);
					if(list.contains(entry)){
						replace = true;
						count++;
					}
					else
						list.add(entry);
				}
				if(replace){
					tiles[index] = new byte[list.size() * 3];
					int j = 0;
					for(ABEntry entry : list){
						entry.put(tiles[index], j);
						j += 3;
					}
				}
			}
			index++;
		}
		System.out.println("Removed " + count + " duplicated tiles.");//seems to work
	}
	
	private static class ABEntry {
		
		private short absoluteID;
		private byte tileY;
		
		private ABEntry(byte[] entries, int index){
			absoluteID = Tiles.fromRuntimeID(ByteBuffer.wrap(entries, index, 2).getShort()).getAbsoluteID();
			tileY = entries[index + 2];
		}
		
		private ABEntry(short absoluteID, byte tileY){
			this.absoluteID = absoluteID;
			this.tileY = tileY;
		}
		
		private void put(byte[] entries, int index){
			ByteBuffer.wrap(entries, index, 2).putShort(absoluteID);
			entries[index + 2] = tileY;
		}
		
		@Override
		public boolean equals(Object other){
			if(other instanceof ABEntry){
				ABEntry entry = (ABEntry) other;
				return entry.absoluteID == absoluteID && entry.tileY == tileY;
			}
			return false;
		}
		
		@Override
		public int hashCode(){
			return absoluteID + 300 * tileY;
		}
	}
	
	private void addToRenderMap(short runtimeID, int tileX, int tileY, int tileZ){
		//Matrix4f matrix = Tiles.fromRuntimeID(runtimeID).getMatrix(tileX, tileY, tileZ);
		renderMap2.addTile(runtimeID, tileX, tileY, tileZ);
		/*
		int index = runtimeID - Short.MIN_VALUE;
		Matrix4f[] data = renderMap[index];
		if(data == null)
			renderMap[index] = new Matrix4f[]{matrix};
		else {
			data = Arrays.copyOf(data, data.length + 1);
			data[data.length - 1] = matrix;
			renderMap[index] = data;
		}
		if(renderMapPoints == null) return;
		Matrix4f[] matrices = renderMapPoints.get(new Point(tileX,tileY,tileZ));
		if(matrices == null)
			matrices = new Matrix4f[]{matrix};
		else {
			matrices = Arrays.copyOf(matrices, matrices.length + 1);
			matrices[matrices.length - 1] = matrix;
		}
		renderMapPoints.put(new Point(tileX,tileY,tileZ), matrices);
		*/
	}
	
	private void removeFromRenderMap(int tileX, int tileY, int tileZ){
		renderMap2.removeTiles(tileX, tileY, tileZ);
		/*
		Matrix4f[] boundMatrices = renderMapPoints.get(new Point(tileX,tileY,tileZ));
		if(boundMatrices == null)
			return;
		for(int i = 0; i < renderMap.length; i++){
			Matrix4f[] renderMatrices = renderMap[i];
			if(renderMatrices != null){
				for(int j = 0; j < renderMatrices.length; j++){
					for(Matrix4f matrix : boundMatrices){
						if(matrix == renderMatrices[j]){
							Matrix4f[] newRenderMatrices = new Matrix4f[renderMatrices.length - 1];
							System.arraycopy(renderMatrices, 0, newRenderMatrices, 0, j);
							System.arraycopy(renderMatrices, j + 1, newRenderMatrices, j, renderMatrices.length - j - 1);
							renderMatrices = newRenderMatrices;
							renderMap[i] = newRenderMatrices;
							j--;
						}
					}
				}
			}
		}
		*/
	}
	
	private void addToRenderMap(short runtimeID, int tileY, int index){
		int tileZ = index / width;
		int tileX = index - (tileZ * width);
		addToRenderMap(runtimeID, tileX, tileY, tileZ);
	}
	
	public static int getTileX(Matrix4f matrix){
		return (int) (matrix.m30 / 64);
	}
	
	public static int getTileY(Matrix4f matrix){
		return (int) (matrix.m31 / 16);
	}
	
	public static int getTileZ(Matrix4f matrix){
		return (int) (matrix.m32 / 64);
	}
	
	/*
	private static class Point implements Comparable<Point> {
		
		private int x;
		private int y;
		private int z;
		
		private Point(int x, int y, int z){
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		@Override
		public String toString(){
			return "Point(" + x + "," + y + "," + z + ")";
		}
		
		@Override
		public boolean equals(Object other){
			if(other instanceof Point){
				Point point = (Point) other;
				return point.x == x && point.y == y && point.z == z;
			}
			return false;
		}
		
		@Override
		public int hashCode(){
			return x + 100 * y + 10000 * z;
		}

		@Override
		public int compareTo(Point p) {
			if(x > p.x)
				return 1;
			if(x < p.x)
				return -1;
			if(y > p.y)
				return 1;
			if(y < p.y)
				return -1;
			if(z > p.z)
				return 1;
			if(z < p.z)
				return -1;
			return 0;
		}
	}*/
}
