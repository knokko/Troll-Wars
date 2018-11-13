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