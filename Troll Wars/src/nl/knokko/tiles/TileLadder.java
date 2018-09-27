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

import nl.knokko.area.Area;
import nl.knokko.area.Block;
import nl.knokko.area.creature.AreaCreature;
import nl.knokko.model.type.TileModel;
import nl.knokko.render.tile.TileModels;
import nl.knokko.shaders.ShaderType;
import nl.knokko.texture.ModelTexture;
import nl.knokko.tiles.Tiles.RenderForm;
import nl.knokko.util.Facing;
import nl.knokko.util.Maths;

public class TileLadder extends Tile {
	
	private final ModelTexture texture;
	private final Facing facing;
	private final TileModel model;

	public TileLadder(ModelTexture texture, Facing facing) {
		super();
		this.texture = texture;
		this.facing = facing;
		if(facing == Facing.NORTH)
			model = TileModels.LADDER_NORTH;
		else if(facing == Facing.EAST)
			model = TileModels.LADDER_EAST;
		else if(facing == Facing.SOUTH)
			model = TileModels.LADDER_SOUTH;
		else
			model = TileModels.LADDER_WEST;
	}
	
	@Override
	public void onInteract(Area area, int tileX, int tileY, int tileZ, Facing facing, boolean inside){
		if((!inside && facing == this.facing.getOpposite()) || (inside && facing == this.facing)){
			Block[] blocks = area.getTiles().getBlocks(tileX, tileZ);
			int y = tileY + 4;
			while(true){
				int prevY = y;
				for(Block block : blocks){
					if(block.getTileY() == y - 128 && block.getTile() instanceof TileLadder){
						y += 4;
						break;
					}
				}
				if(prevY == y)
					break;
			}
			Block[] sideBlocks = area.getTiles().getBlocks(tileX + this.facing.getDX(), tileZ + this.facing.getDZ());
			for(Block block : sideBlocks){
				if(block.getTile().canMoveTo(area.getPlayer(true), block.getTileY() + 128, y, this.facing)){
					area.getPlayer(true).getPosition().setPosition(tileX * 32 + this.facing.getDX() * 32, y * 8, tileZ * 32 + this.facing.getDZ() * 32);
					area.getPlayer(true).refreshPosition();
					return;
				}
			}
			System.out.println("Couldn't find destination!");
		}
	}
	
	@Override
	public void onWeakInteract(Area area, int tileX, int tileY, int tileZ, Facing facing, boolean inside){
		onInteract(area, tileX, tileY, tileZ, facing, inside);
	}
	
	@Override
	public boolean canMoveTo(AreaCreature creature, int ownTileY, int moverTileY, Facing facing) {
		if(facing == null || ownTileY + 4 != moverTileY)
			return false;
		//TODO wall glitch?
		int y = ownTileY + 128;
		int tileX = creature.getPosition().getTileX() - this.facing.getDX();
		int tileZ = creature.getPosition().getTileZ() - this.facing.getDZ();
		Block[] blocks = creature.getArea().getTiles().getBlocks(tileX, tileZ);
		while(true){
			int prevY = y;
			for(Block block : blocks){
				if(block.getTile() instanceof TileLadder && block.getTileY() + 128 == y - 4){
					y -= 4;
					break;
				}
			}
			if(y == prevY)
				break;
		}
		if(y != ownTileY + 128){
			creature.getPosition().setPosition(tileX * 32, y * 8, tileZ * 32);
			creature.refreshPosition();
		}
		return false;
	}
	
	@Override
	public Matrix4f getMatrix(int tileX, int tileY, int tileZ) {
		return Maths.createTransformationMatrix(new Vector3f((float) (tileX * 64 - 24 * Math.sin(-facing.getRadianYaw())), tileY * 16 + 32, (float) (tileZ * 64 - 24 * Math.cos(-facing.getRadianYaw()))), 90, -facing.getDegreeYaw(), 0, 32);
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
		return RenderForm.QUAD;
	}
	
	@Override
	public ShaderType getShaderType(){
		return ShaderType.NORMAL;
	}

	@Override
	public int getHeight() {
		return 4;
	}

	@Override
	public boolean preventMoveIfIn(int ownTileY, int moverTileYFrom, int moverTileYTo, Facing facing) {
		return false;
	}

	@Override
	public boolean preventMoveIfTo(int ownTileY, int moverTileYFrom, int moverTileYTo, Facing facing) {
		return ownTileY + 4 == moverTileYFrom;
	}

	@Override
	public int getWalkHeight(int ownTileY, float relativeMoverX, float relativeMoverZ, AreaCreature creature) {
		
		return ownTileY;
	}

	@Override
	public int getExitTileY(int ownTileY, Facing facing) {
		return ownTileY + 4;
	}

	@Override
	public boolean canExitTile(int ownTileY, Facing facing) {
		return false;
	}
}
