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

public class TileWall extends Tile {
	
	private final ModelTexture texture;
	final Facing facing;
	private final TileModel model;

	public TileWall(ModelTexture texture, Facing facing) {
		this.texture = texture;
		this.facing = facing;
		this.model = createModel();
	}
	
	protected TileModel createModel(){
		if(facing == Facing.NORTH)
			return TileModels.WALL_NORTH;
		if(facing == Facing.EAST)
			return TileModels.WALL_EAST;
		if(facing == Facing.SOUTH)
			return TileModels.WALL_SOUTH;
		return TileModels.WALL_WEST;
	}

	@Override
	public Matrix4f getMatrix(int tileX, int tileY, int tileZ) {
		return Maths.createTransformationMatrix(new Vector3f((float) (tileX * 64 - 32 * Math.sin(-facing.getRadianYaw())), tileY * 16 + 32, (float) (tileZ * 64 - 32 * Math.cos(-facing.getRadianYaw()))), 90, -facing.getDegreeYaw(), 0, 32);
	}
	
	@Override
	public TileModel getModel(){
		return model;
	}

	@Override
	public int getHeight() {
		return 4;
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
		return false;
	}

	@Override
	public boolean preventMoveIfIn(int ownTileY, int moverTileYFrom, int moverTileYTo, Facing facing) {
		return facing == this.facing && ownTileY <= moverTileYFrom && ownTileY + getHeight() > moverTileYFrom;
	}

	@Override
	public boolean preventMoveIfTo(int ownTileY, int moverTileYFrom, int moverTileYTo, Facing facing) {
		return facing.getOpposite() == this.facing && ownTileY <= moverTileYTo && ownTileY + getHeight() > moverTileYTo;
	}

	@Override
	public int getWalkHeight(int ownTileY, float relativeMoverX, float relativeMoverZ, AreaCreature creature) {
		throw new RuntimeException("No creature can walk on a TileWall!");
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
