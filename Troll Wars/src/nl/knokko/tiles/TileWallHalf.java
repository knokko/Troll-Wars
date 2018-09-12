package nl.knokko.tiles;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import nl.knokko.model.type.TileModel;
import nl.knokko.render.tile.TileModels;
import nl.knokko.texture.ModelTexture;
import nl.knokko.util.Facing;
import nl.knokko.util.Maths;

public class TileWallHalf extends TileWall {

	public TileWallHalf(ModelTexture texture, Facing facing) {
		super(texture, facing);
	}
	
	@Override
	public Matrix4f getMatrix(int tileX, int tileY, int tileZ) {
		return Maths.createTransformationMatrix(new Vector3f((float) (tileX * 64 + 32 * Math.sin(facing.getRadianYaw())), tileY * 16 + 16, (float) (tileZ * 64 - 32 * Math.cos(facing.getRadianYaw()))), 90, facing.getDegreeYaw(), 0, 32, 32, 16);
	}
	
	@Override
	public int getHeight(){
		return 2;
	}
	
	@Override
	protected TileModel createModel(){
		if(facing == Facing.NORTH)
			return TileModels.WALL_HALF_NORTH;
		if(facing == Facing.EAST)
			return TileModels.WALL_HALF_EAST;
		if(facing == Facing.SOUTH)
			return TileModels.WALL_HALF_SOUTH;
		return TileModels.WALL_HALF_WEST;
	}
}