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

public class TileBottom extends Tile {
	
	private final ModelTexture texture;
	private final ModelTexture bigTexture;

	TileBottom(ModelTexture texture) {
		this(texture, null);
	}
	
	TileBottom(ModelTexture texture, ModelTexture bigTexture){
		this.texture = texture;
		this.bigTexture = bigTexture;
	}
	
	@Override
	public Matrix4f getMatrix(int tileX, int tileY, int tileZ){
		return Maths.createTransformationMatrix(new Vector3f(tileX * 64, tileY * 16, tileZ * 64), 32);
	}
	
	@Override
	public TileModel getModel(){
		return bigTexture == null ? TileModels.BOTTOM : TileModels.BOTTOM_BIG;
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
	public ModelTexture getBigTexture() {
		return bigTexture;
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
