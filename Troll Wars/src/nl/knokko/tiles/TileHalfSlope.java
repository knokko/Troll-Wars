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

public class TileHalfSlope extends Tile {
	
	private final float angle;
	private final float length;
	private final int height;
	
	private final boolean side;
	private final boolean up;
	
	private final Facing facing;
	private final ModelTexture texture;
	private final TileModel model;

	public TileHalfSlope(ModelTexture texture, Facing facing, int height, boolean side, boolean up) {
		this.facing = facing;
		this.angle = (float) Math.toDegrees(Math.atan2(height * 0.25f, 1));
		this.length = (float) (height / Math.sin(Math.toRadians(angle)));
		this.texture = texture;
		this.height = height;
		this.side = side;
		this.up = up;
		try {
			model = (TileModel) TileModels.class.getField("SLOPE_HALF_" + facing.name() + "_" + height + "_" + (side ? "1" : "0") + "_" + (up ? "1" : "0")).get(null);
			//model = null;
		} catch(Exception ex){
			throw new Error(ex);
		}
	}
	
	@Override
	public Matrix4f getMatrix(int tileX, int tileY, int tileZ){
		//TODO the normals are incorrect with this method
		return Maths.createTransformationMatrix(new Vector3f(tileX * 64, tileY * 16 + height * 8, tileZ * 64), up ? angle : angle + 0, -facing.getDegreeYaw(), 0, side ? -32 : 32, (side ? -8 : 8) * height, (up ? 8 : -8) * length);
	}
	
	@Override
	public TileModel getModel(){
		return model;
	}

	@Override
	public ModelTexture getTexture() {
		return texture;
	}

	@Override
	public RenderForm getRenderForm() {
		return RenderForm.TRIANGLE;
	}
	
	@Override
	public ShaderType getShaderType(){
		return ShaderType.NORMAL;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public boolean canMoveTo(AreaCreature creature, int ownTileY, int moverTileY, Facing facing) {
		return false;
	}

	@Override
	public boolean preventMoveIfIn(int ownTileY, int moverTileYFrom, int moverTileYTo, Facing facing){
		return false;
	}

	@Override
	public boolean preventMoveIfTo(int ownTileY, int moverTileYFrom, int moverTileYTo, Facing facing) {
		return moverTileYTo >= ownTileY && moverTileYTo <= ownTileY + height;
	}

	@Override
	public int getWalkHeight(int ownTileY, float relativeMoverX, float relativeMoverZ, AreaCreature creature) {
		throw new RuntimeException("No creature can walk on a TileHalfSlope!");
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
