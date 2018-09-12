package nl.knokko.model.type;

import java.util.ArrayList;
import java.util.List;

import nl.knokko.tiles.Tile;

public abstract class TileModel extends AbstractModel {
	
	private int index;
	
	private final List<Tile> tiles;
	
	public TileModel(int vaoID, int vertexCount) {
		super(vaoID, vertexCount);
		this.tiles = new ArrayList<Tile>();
	}
	
	@Override
	public String toString(){
		return "TileModel(" + getVaoID() + ", " + getVertexCount() + ", " + tiles.size() + " tiles)";
	}
	
	public int getIndex(){
		return index;
	}
	
	public List<Tile> getTiles(){
		return tiles;
	}
	
	public void addTile(Tile tile){
		tiles.add(tile);
	}
	
	public void setIndex(int index){
		this.index = index;
	}
}