package nl.knokko.render.tile;

import nl.knokko.model.type.TileModel;
import nl.knokko.tiles.Tile;
import nl.knokko.tiles.Tiles;

import org.lwjgl.util.vector.Vector3f;

public class TileRenderMap {
	
	private Vector3f[][] data;
	
	private int[] counts;

	public TileRenderMap() {
		data = new Vector3f[Tiles.getAmount()][0];
		counts = new int[TileModels.AMOUNT];
	}
	
	public void addTile(Tile tile, int tileX, int tileY, int tileZ){
		Vector3f[] positions = data[tile.getRuntimeID() - Short.MIN_VALUE];
		Vector3f[] newPositions = new Vector3f[positions.length + 1];
		System.arraycopy(positions, 0, newPositions, 0, positions.length);
		newPositions[positions.length] = new Vector3f(tileX * 64, tileY * 16, tileZ * 64);
		data[tile.getRuntimeID() - Short.MIN_VALUE] = newPositions;
		counts[tile.getModel().getIndex()]++;
	}
	
	public void addTile(short runtimeID, int tileX, int tileY, int tileZ){
		addTile(Tiles.fromRuntimeID(runtimeID), tileX, tileY, tileZ);
	}
	
	public void removeTiles(int tileX, int tileY, int tileZ){
		float x = tileX * 64;
		float y = tileY * 16;
		float z = tileZ * 64;
		for(int i = 0; i < data.length; i++){
			Vector3f[] current = data[i];
			for(int j = 0; j < current.length; j++){
				Vector3f vec = current[j];
				if(vec.x == x && vec.y == y && vec.z == z){
					Vector3f[] newCurrent = new Vector3f[current.length - 1];
					System.arraycopy(current, 0, newCurrent, 0, j);
					System.arraycopy(current, j + 1, newCurrent, j, newCurrent.length - j);
					current = newCurrent;
					counts[Tiles.fromRuntimeID((short) (i + Short.MIN_VALUE)).getModel().getIndex()]--;
					j--;
				}
			}
			data[i] = current;
		}
	}
	
	public boolean hasModel(TileModel model){
		return counts[model.getIndex()] > 0;
	}
	
	public Vector3f[] getPositions(Tile tile){
		return data[tile.getRuntimeID() - Short.MIN_VALUE];
	}
}