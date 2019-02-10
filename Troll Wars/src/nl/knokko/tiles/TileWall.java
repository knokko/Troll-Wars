/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2019 knokko
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
