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
package nl.knokko.tiles;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import nl.knokko.area.creature.AreaCreature;
import nl.knokko.model.type.TileModel;
import nl.knokko.render.tile.TileModels;
import nl.knokko.shaders.ShaderType;
import nl.knokko.texture.ModelTexture;
import nl.knokko.tiles.Tiles.RenderForm;
import nl.knokko.util.Facing;
import nl.knokko.util.Maths;

public class TileSlope extends Tile {
	
	private final float angle;
	private final float length;
	private final int height;
	
	private final Facing facing;
	private final ModelTexture texture;
	private final TileModel model;

	public TileSlope(ModelTexture texture, Facing facing, int height) {
		this.facing = facing;
		this.angle = (float) Math.toDegrees(Math.atan2(height * 0.25f, 1));
		this.length = (float) (height / Math.sin(Math.toRadians(angle)));
		this.texture = texture;
		this.height = height;
		try {
			model = (TileModel) TileModels.class.getField("SLOPE_" + facing + "_" + height).get(null);
		} catch(Exception ex){
			throw new Error("Can't get model for slope with facing " + facing + " and height " + height);
		}
	}
	
	public Matrix4f getMatrix(int tileX, int tileY, int tileZ){
		return Maths.createTransformationMatrix(new Vector3f(tileX * 64, tileY * 16 + height * 8, tileZ * 64), angle, -facing.getDegreeYaw(), 0, 32, 8 * height, 8 * length);
	}
	
	@Override
	public TileModel getModel(){
		return model;
	}

	@Override
	public int getHeight() {
		return height;
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
	public boolean canMoveTo(AreaCreature creature, int ownTileY, int moverTileY, Facing facing){
		if(height > 2)
			return false;
		if(facing == this.facing)
			return ownTileY == moverTileY;
		if(facing.getOpposite() == this.facing)
			return ownTileY == moverTileY - height;
		return ownTileY == moverTileY - height / 2;
	}

	@Override
	public boolean preventMoveIfIn(int ownTileY, int moverTileYFrom, int moverTileYTo, Facing facing) {
		return false;
	}
	
	@Override
	public boolean preventMoveIfTo(int ownTileY, int moverTileYFrom, int moverTileYTo, Facing facing) {
		return moverTileYTo >= ownTileY && moverTileYTo <= ownTileY + height;
	}

	@Override
	public int getWalkHeight(int ownTileY, float relativeMoverX, float relativeMoverZ, AreaCreature creature){
		float progress = 0;
		if(facing == Facing.NORTH)
			progress = relativeMoverZ / 1f;
		if(facing == Facing.EAST)
			progress = relativeMoverX / -1f;
		if(facing == Facing.SOUTH)
			progress = relativeMoverZ / -1f;
		if(facing == Facing.WEST)
			progress = relativeMoverX / 1f;
		return (int) (ownTileY * 8 + 4 * height + progress * height * 4);
	}

	@Override
	public int getExitTileY(int ownTileY, Facing facing){
		if(facing == this.facing)
			return ownTileY + height;
		if(facing.getOpposite() == this.facing)
			return ownTileY;
		return ownTileY + height / 2;
	}

	@Override
	public boolean canExitTile(int ownTileY, Facing facing) {
		return true;
	}
}
