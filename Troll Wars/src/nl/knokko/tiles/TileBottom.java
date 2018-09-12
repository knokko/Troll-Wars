package nl.knokko.tiles;

import nl.knokko.area.creature.AreaCreature;
import nl.knokko.model.type.TileModel;
import nl.knokko.render.tile.TileModels;
import nl.knokko.shaders.ShaderType;
import nl.knokko.texture.ModelTexture;
import nl.knokko.tiles.Tiles.RenderForm;
import nl.knokko.util.Facing;
import nl.knokko.util.Maths;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class TileBottom extends Tile {
	
	private final ModelTexture texture;

	TileBottom(ModelTexture texture) {
		this.texture = texture;
	}
	
	@Override
	public Matrix4f getMatrix(int tileX, int tileY, int tileZ){
		return Maths.createTransformationMatrix(new Vector3f(tileX * 64, tileY * 16, tileZ * 64), 32);
	}
	
	@Override
	public TileModel getModel(){
		return TileModels.BOTTOM;
	}

	@Override
	public int getHeight() {
		return 1;
	}

	@Override
	public ModelTexture getTexture() {
		return texture;
	}

	@Override
	public RenderForm getRenderForm() {
		return RenderForm.QUAD;
	}
	
	@Override
	public ShaderType getShaderType(){
		return ShaderType.NORMAL;
	}

	@Override
	public boolean canMoveTo(AreaCreature creature, int ownTileY, int moverTileY, Facing facing) {
		return ownTileY == moverTileY;
	}

	@Override
	public boolean preventMoveIfIn(int ownTileY, int moverTileYFrom, int moverTileYTo, Facing facing) {
		return false;
	}

	@Override
	public boolean preventMoveIfTo(int ownTileY, int moverTileYFrom, int moverTileYTo, Facing facing) {
		return false;
	}

	@Override
	public int getWalkHeight(int ownTileY, float relativeMoverX, float relativeMoverZ, AreaCreature creature) {
		return ownTileY * 8;
	}

	@Override
	public int getExitTileY(int ownTileY, Facing facing) {
		return ownTileY;
	}

	@Override
	public boolean canExitTile(int ownTileY, Facing facing) {
		return true;
	}
}
