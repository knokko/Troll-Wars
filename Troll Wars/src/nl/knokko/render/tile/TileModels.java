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
package nl.knokko.render.tile;

import java.lang.reflect.Field;

import nl.knokko.model.factory.ModelLoader;
import nl.knokko.model.type.BigTileModel;
import nl.knokko.model.type.DefaultTileModel;
import nl.knokko.model.type.LiquidTileModel;
import nl.knokko.model.type.TileModel;
import nl.knokko.tiles.Tile;
import nl.knokko.tiles.Tiles;
import nl.knokko.util.Facing;
import nl.knokko.util.Maths;

public final class TileModels {
	
	private static LiquidTileModel loadLiq(float[] vertices, float[] textureCoords, int[] indices){
		return ModelLoader.loadLiquidTileModel(vertices, textureCoords, indices);
	}
	
	private static DefaultTileModel loadDef(float[] vertices, float[] textureCoords, float[] normals, int[] indices){
		return ModelLoader.loadDefaultTileModel(vertices, textureCoords, normals, indices);
	}
	
	private static BigTileModel loadBig(float[] vertices, float[] normals, int[] indices) {
		return ModelLoader.loadBigTileModel(vertices, normals, indices);
	}
	
	private static DefaultTileModel loadLadder(Facing facing){
		float[] textureCoords = {0,0, 1,0, 1,1, 0,1};
		int[] indices = {0,1,2, 0,3,2};
		if(facing == Facing.NORTH)
			return loadDef(new float[]{-1,0,-0.75f, 1,0,-0.75f, 1,4,-0.75f, -1,4,-0.75f}, textureCoords, new float[]{0,0,1, 0,0,1, 0,0,1, 0,0,1}, indices);
		if(facing == Facing.EAST)
			return loadDef(new float[]{0.75f,0,-1, 0.75f,0,1, 0.75f,4,1, 0.75f,4,-1}, textureCoords, new float[]{-1,0,0, -1,0,0, -1,0,0, -1,0,0}, indices);
		if(facing == Facing.SOUTH)
			return loadDef(new float[]{-1,0,0.75f, 1,0,0.75f, 1,4,0.75f, -1,4,0.75f}, textureCoords, new float[]{0,0,-1, 0,0,-1, 0,0,-1, 0,0,-1}, indices);
		return loadDef(new float[]{-0.75f,0,-1, -0.75f,0,1, -0.75f,4,1, -0.75f,4,-1}, textureCoords, new float[]{1,0,0, 1,0,0, -1,0,0, 1,0,0}, indices);
	}
	
	private static DefaultTileModel loadWall(Facing facing, float height){
		float nx = -facing.getDX();
		float nz = -facing.getDZ();
		float[] textureCoords = null;
		float[] normals = {nx,0,nz, nx,0,nz, nx,0,nz, nx,0,nz};
		int[] indices = {0,1,2, 2,3,0};
		float[] vertices = null;
		if(facing == Facing.NORTH){
			vertices = new float[]{-1,0,-1, 1,0,-1, 1,height,-1, -1,height,-1};
			textureCoords = new float[]{0,height / 4, 1,height / 4, 1,0, 0,0};
		}
		if(facing == Facing.EAST){
			vertices = new float[]{1,0,-1, 1,0,1, 1,height,1, 1,height,-1};
			textureCoords = new float[]{0,height / 4, 1,height / 4, 1,0, 0,0};
		}
		if(facing == Facing.SOUTH){
			vertices = new float[]{-1,0,1, 1,0,1, 1,height,1, -1,height,1};
			textureCoords = new float[]{1,height / 4, 0,height / 4, 0,0, 1,0};
		}
		if(facing == Facing.WEST){
			vertices = new float[]{-1,0,-1, -1,0,1, -1,height,1, -1,height,-1};
			textureCoords = new float[]{1,height / 4, 0,height / 4, 0,0, 1,0};
		}
		return loadDef(vertices, textureCoords, normals, indices);
	}
	
	private static DefaultTileModel loadSlope(Facing facing, int height){
		float[] vertices = null;
		float[] textureCoords = null;
		float nx = -1;
		float nz = -1;
		float pitch = (float) Math.toDegrees(Math.atan2(height * 0.25f, 1));
		float ny = Maths.cos(pitch);
		float nxz = Maths.sin(pitch);
		if(facing == Facing.NORTH){
			vertices = new float[]{-1,height,-1, 1,height,-1, 1,0,1, -1,0,1};
			textureCoords = new float[]{0,0, 1,0, 1,1, 0,1};//(0,0) and (1,0) where y = height
			nx = 0;
			nz = -nxz;
		}
		if(facing == Facing.EAST){
			vertices = new float[]{-1,0,-1, 1,height,-1, 1,height,1, -1,0,1};
			textureCoords = new float[]{0,1, 0,0, 1,0, 1,1};
			nx = nxz;
			nz = 0;
		}
		if(facing == Facing.SOUTH){
			vertices = new float[]{-1,0,-1, 1,0,-1, 1,height,1, -1,height,1};
			textureCoords = new float[]{1,1, 0,1, 0,0, 1,0};
			nx = 0;
			nz = nxz;
		}
		if(facing == Facing.WEST){
			vertices = new float[]{-1,height,-1, 1,0,-1, 1,0,1, -1,height,1};
			nx = -nxz;
			nz = 0;
			textureCoords = new float[]{1,0, 1,1, 0,1, 0,0};
		}
		float[] normals = {nx,ny,nz, nx,ny,nz, nx,ny,nz, nx,ny,nz, nx,ny,nz};
		int[] indices = new int[]{0,1,2, 0,3,2};
		return loadDef(vertices, textureCoords, normals, indices);
	}
	
	private static DefaultTileModel loadHalfSlope(Facing facing, int height, boolean side, boolean up){
		float[] vertices = null;
		float[] textureCoords = null;
		float nx = -1;
		float nz = -1;
		float pitch = (float) Math.toDegrees(Math.atan2(height * 0.25f, 1));
		float ny = Maths.cos(pitch);
		float nxz = Maths.sin(pitch);
		if(facing == Facing.NORTH){
			if(side){
				if(up){
					vertices = new float[]{-1,height,-1, 1,height,-1, -1,0,1};
					textureCoords = new float[]{0,0, 1,0, 0,1};
				}
				else {
					vertices = new float[]{-1,0,1, 1,0,1, -1,height,-1};
					textureCoords = new float[]{0,1, 1,1, 0,0};
				}
			}
			else {
				if(up){
					vertices = new float[]{-1,height,-1, 1,height,-1, 1,0,1};
					textureCoords = new float[]{0,0, 1,0, 1,1};
				}
				else {
					vertices = new float[]{1,0,1, -1,0,1, 1,height,-1};
					textureCoords = new float[]{1,1, 0,1, 1,0};
				}
			}
			nx = 0;
			nz = -nxz;
		}
		if(facing == Facing.EAST){
			if(!side){
				if(up){
					vertices = new float[]{1,height,-1, 1,height,1, -1,0,1};
					textureCoords = new float[]{0,1, 1,1, 1,0};
				}
				else {
					vertices = new float[]{-1,0,-1, -1,0,1, 1,height,1};
					textureCoords = new float[]{0,0, 1,0, 1,1};
				}
			}
			else {
				if(up){
					vertices = new float[]{1,height,-1, 1,height,1, -1,0,-1};
					textureCoords = new float[]{0,1, 1,1, 0,0};
				}
				else {
					vertices = new float[]{-1,0,-1, -1,0,1, 1,height,-1};
					textureCoords = new float[]{0,0, 1,0, 0,1};
				}
			}
			nx = nxz;
			nz = 0;
		}
		if(facing == Facing.SOUTH){
			if(side){
				if(up){
					vertices = new float[]{-1,height,1, 1,height,1, 1,0,-1};
					textureCoords = new float[]{0,0, 1,0, 0,1};
				}
				else {
					vertices = new float[]{1,0,-1, -1,0,-1, 1,height,1};
					textureCoords = new float[]{0,1, 1,1, 0,0};
				}
			}
			else {
				if(up){
					vertices = new float[]{-1,height,1, 1,height,1, -1,0,-1};
					textureCoords = new float[]{0,0, 1,0, 1,1};
				}
				else {
					vertices = new float[]{-1,0,-1, 1,0,-1, -1,height,1};
					textureCoords = new float[]{1,1, 0,1, 1,0};
				}
			}
			nx = 0;
			nz = nxz;
		}
		if(facing == Facing.WEST){
			if(side){
				if(up){
					vertices = new float[]{-1,height,-1, -1,height,1, 1,0,1};
					textureCoords = new float[]{0,1, 1,1, 1,0};
				}
				else {
					vertices = new float[]{1,0,-1, 1,0,1, -1,height,1};
					textureCoords = new float[]{0,0, 1,0, 1,1};
				}
			}
			else {
				if(up){
					vertices = new float[]{-1,height,-1, -1,height,1, 1,0,-1};
					textureCoords = new float[]{1,1, 0,1, 0,0};
				}
				else {
					vertices = new float[]{1,0,-1, 1,0,1, -1,height,-1};
					textureCoords = new float[]{0,0, 1,0, 0,1};
				}
			}
			nx = -nxz;
			nz = 0;
		}
		float[] normals = {nx,ny,nz, nx,ny,nz, nx,ny,nz, nx,ny,nz};
		int[] indices = new int[]{0,1,2};
		return loadDef(vertices, textureCoords, normals, indices);
	}
	
	public static final DefaultTileModel BOTTOM = loadDef(new float[]{-1,0,-1, 1,0,-1, 1,0,1, -1,0,1}, new float[]{0,0, 1,0, 1,1, 0,1}, new float[]{0,1,0, 0,1,0, 0,1,0, 0,1,0}, new int[]{0,1,2, 0,3,2});
	public static final BigTileModel BOTTOM_BIG = loadBig(new float[]{-1,0,-1, 1,0,-1, 1,0,1, -1,0,1}, new float[]{0,1,0, 0,1,0, 0,1,0, 0,1,0}, new int[]{0,1,2, 0,3,2});
	public static final DefaultTileModel BOTTOM_COVER = loadDef(new float[]{-1,0.1f,-1, 1,0.1f,-1, 1,0.1f,1, -1,0.1f,1}, new float[]{0,0, 1,0, 1,1, 0,1}, new float[]{0,1,0, 0,1,0, 0,1,0, 0,1,0}, new int[]{0,1,2, 0,3,2});
	public static final LiquidTileModel BOTTOM_LIQUID = loadLiq(new float[]{-1,0,-1, 1,0,-1, 1,0,1, -1,0,1}, new float[]{0,0, 1,0, 1,1, 0,1}, new int[]{0,1,2, 0,3,2});
	
	public static final DefaultTileModel WALL_NORTH = loadWall(Facing.NORTH, 4);
	public static final DefaultTileModel WALL_EAST = loadWall(Facing.EAST, 4);
	public static final DefaultTileModel WALL_SOUTH = loadWall(Facing.SOUTH, 4);
	public static final DefaultTileModel WALL_WEST = loadWall(Facing.WEST, 4);
	
	public static final DefaultTileModel WALL_HALF_NORTH = loadWall(Facing.NORTH, 2);
	public static final DefaultTileModel WALL_HALF_EAST = loadWall(Facing.EAST, 2);
	public static final DefaultTileModel WALL_HALF_SOUTH = loadWall(Facing.SOUTH, 2);
	public static final DefaultTileModel WALL_HALF_WEST = loadWall(Facing.WEST, 2);
	
	public static final DefaultTileModel LADDER_NORTH = loadLadder(Facing.NORTH);
	public static final DefaultTileModel LADDER_EAST = loadLadder(Facing.EAST);
	public static final DefaultTileModel LADDER_SOUTH = loadLadder(Facing.SOUTH);
	public static final DefaultTileModel LADDER_WEST = loadLadder(Facing.WEST);
	
	public static final DefaultTileModel SLOPE_NORTH_1 = loadSlope(Facing.NORTH, 1);
	public static final DefaultTileModel SLOPE_EAST_1 = loadSlope(Facing.EAST, 1);
	public static final DefaultTileModel SLOPE_SOUTH_1 = loadSlope(Facing.SOUTH, 1);
	public static final DefaultTileModel SLOPE_WEST_1 = loadSlope(Facing.WEST, 1);
	public static final DefaultTileModel SLOPE_NORTH_2 = loadSlope(Facing.NORTH, 2);
	public static final DefaultTileModel SLOPE_EAST_2 = loadSlope(Facing.EAST, 2);
	public static final DefaultTileModel SLOPE_SOUTH_2 = loadSlope(Facing.SOUTH, 2);
	public static final DefaultTileModel SLOPE_WEST_2 = loadSlope(Facing.WEST, 2);
	public static final DefaultTileModel SLOPE_NORTH_3 = loadSlope(Facing.NORTH, 3);
	public static final DefaultTileModel SLOPE_EAST_3 = loadSlope(Facing.EAST, 3);
	public static final DefaultTileModel SLOPE_SOUTH_3 = loadSlope(Facing.SOUTH, 3);
	public static final DefaultTileModel SLOPE_WEST_3 = loadSlope(Facing.WEST, 3);
	public static final DefaultTileModel SLOPE_NORTH_4 = loadSlope(Facing.NORTH, 4);
	public static final DefaultTileModel SLOPE_EAST_4 = loadSlope(Facing.EAST, 4);
	public static final DefaultTileModel SLOPE_SOUTH_4 = loadSlope(Facing.SOUTH, 4);
	public static final DefaultTileModel SLOPE_WEST_4 = loadSlope(Facing.WEST, 4);
	
	public static final DefaultTileModel SLOPE_HALF_NORTH_1_0_0 = loadHalfSlope(Facing.NORTH, 1, false, false);
	public static final DefaultTileModel SLOPE_HALF_NORTH_1_1_0 = loadHalfSlope(Facing.NORTH, 1, true, false);
	public static final DefaultTileModel SLOPE_HALF_NORTH_1_1_1 = loadHalfSlope(Facing.NORTH, 1, true, true);
	public static final DefaultTileModel SLOPE_HALF_NORTH_1_0_1 = loadHalfSlope(Facing.NORTH, 1, false, true);
	public static final DefaultTileModel SLOPE_HALF_EAST_1_0_0 = loadHalfSlope(Facing.EAST, 1, false, false);
	public static final DefaultTileModel SLOPE_HALF_EAST_1_1_0 = loadHalfSlope(Facing.EAST, 1, true, false);
	public static final DefaultTileModel SLOPE_HALF_EAST_1_1_1 = loadHalfSlope(Facing.EAST, 1, true, true);
	public static final DefaultTileModel SLOPE_HALF_EAST_1_0_1 = loadHalfSlope(Facing.EAST, 1, false, true);
	public static final DefaultTileModel SLOPE_HALF_SOUTH_1_0_0 = loadHalfSlope(Facing.SOUTH, 1, false, false);
	public static final DefaultTileModel SLOPE_HALF_SOUTH_1_1_0 = loadHalfSlope(Facing.SOUTH, 1, true, false);
	public static final DefaultTileModel SLOPE_HALF_SOUTH_1_1_1 = loadHalfSlope(Facing.SOUTH, 1, true, true);
	public static final DefaultTileModel SLOPE_HALF_SOUTH_1_0_1 = loadHalfSlope(Facing.SOUTH, 1, false, true);
	public static final DefaultTileModel SLOPE_HALF_WEST_1_0_0 = loadHalfSlope(Facing.WEST, 1, false, false);
	public static final DefaultTileModel SLOPE_HALF_WEST_1_1_0 = loadHalfSlope(Facing.WEST, 1, true, false);
	public static final DefaultTileModel SLOPE_HALF_WEST_1_1_1 = loadHalfSlope(Facing.WEST, 1, true, true);
	public static final DefaultTileModel SLOPE_HALF_WEST_1_0_1 = loadHalfSlope(Facing.WEST, 1, false, true);
	
	public static final DefaultTileModel SLOPE_HALF_NORTH_4_0_0 = loadHalfSlope(Facing.NORTH, 4, false, false);
	public static final DefaultTileModel SLOPE_HALF_NORTH_4_1_0 = loadHalfSlope(Facing.NORTH, 4, true, false);
	public static final DefaultTileModel SLOPE_HALF_NORTH_4_1_1 = loadHalfSlope(Facing.NORTH, 4, true, true);
	public static final DefaultTileModel SLOPE_HALF_NORTH_4_0_1 = loadHalfSlope(Facing.NORTH, 4, false, true);
	public static final DefaultTileModel SLOPE_HALF_EAST_4_0_0 = loadHalfSlope(Facing.EAST, 4, false, false);
	public static final DefaultTileModel SLOPE_HALF_EAST_4_1_0 = loadHalfSlope(Facing.EAST, 4, true, false);
	public static final DefaultTileModel SLOPE_HALF_EAST_4_1_1 = loadHalfSlope(Facing.EAST, 4, true, true);
	public static final DefaultTileModel SLOPE_HALF_EAST_4_0_1 = loadHalfSlope(Facing.EAST, 4, false, true);
	public static final DefaultTileModel SLOPE_HALF_SOUTH_4_0_0 = loadHalfSlope(Facing.SOUTH, 4, false, false);
	public static final DefaultTileModel SLOPE_HALF_SOUTH_4_1_0 = loadHalfSlope(Facing.SOUTH, 4, true, false);
	public static final DefaultTileModel SLOPE_HALF_SOUTH_4_1_1 = loadHalfSlope(Facing.SOUTH, 4, true, true);
	public static final DefaultTileModel SLOPE_HALF_SOUTH_4_0_1 = loadHalfSlope(Facing.SOUTH, 4, false, true);
	public static final DefaultTileModel SLOPE_HALF_WEST_4_0_0 = loadHalfSlope(Facing.WEST, 4, false, false);
	public static final DefaultTileModel SLOPE_HALF_WEST_4_1_0 = loadHalfSlope(Facing.WEST, 4, true, false);
	public static final DefaultTileModel SLOPE_HALF_WEST_4_1_1 = loadHalfSlope(Facing.WEST, 4, true, true);
	public static final DefaultTileModel SLOPE_HALF_WEST_4_0_1 = loadHalfSlope(Facing.WEST, 4, false, true);
	
	static TileModel[] ALL;
	public static int AMOUNT;
	
	public static void init() {
		Field[] fields = TileModels.class.getFields();
		AMOUNT = fields.length - 1;//not fields.length - 2 because ALL is not public!
		ALL = new TileModel[AMOUNT];
		try {
			int allIndex = 0;
			for(int fieldIndex = 0; fieldIndex < fields.length; fieldIndex++){
				if(TileModel.class.isAssignableFrom(fields[fieldIndex].getType())){
					ALL[allIndex] = (TileModel) fields[fieldIndex].get(null);
					ALL[allIndex].setIndex(allIndex);
					for(int tileIndex = 0; tileIndex < Tiles.getAmount(); tileIndex++){
						Tile tile = Tiles.fromRuntimeID((short) (tileIndex + Short.MIN_VALUE));
						if(tile.getModel() == ALL[allIndex])
							ALL[allIndex].addTile(tile);
					}
					allIndex++;
				}
			}
		} catch(Exception ex){
			throw new Error(ex);
		}
	}
	
	public static TileModel get(int index){
		return ALL[index];
	}
}