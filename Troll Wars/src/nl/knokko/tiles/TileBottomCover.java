package nl.knokko.tiles;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import nl.knokko.area.creature.AreaCreature;
import nl.knokko.model.type.TileModel;
import nl.knokko.render.tile.TileModels;
import nl.knokko.texture.ModelTexture;
import nl.knokko.util.Facing;
import nl.knokko.util.Maths;

public class TileBottomCover extends TileBottom {

	public TileBottomCover(ModelTexture topTexture) {
		super(topTexture);
	}
	
	@Override
	public Matrix4f getMatrix(int tileX, int tileY, int tileZ){
		return Maths.createTransformationMatrix(new Vector3f(tileX * 64, tileY * 16 + 0.2f, tileZ * 64), 32);
	}
	
	@Override
	public TileModel getModel(){
		return TileModels.BOTTOM_COVER;
	}
	
	@Override
	public boolean canMoveTo(AreaCreature creature, int ownTileY, int moverTileY, Facing facing) {
		return false;
	}
	
	@Override
	public int getExitTileY(int ownTileY, Facing facing) {
		throw new RuntimeException("Creatures can not move to another tile from this tile because they can not walk over this tile!");
	}
	
	@Override
	public boolean canExitTile(int ownTileY, Facing facing) {
		throw new RuntimeException("Creatures can not move to another tile from this tile because they can not walk over this tile!");
	}
}
