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

import org.lwjgl.util.vector.Matrix4f;

import nl.knokko.area.Area;
import nl.knokko.area.creature.AreaCreature;
import nl.knokko.model.type.TileModel;
import nl.knokko.shaders.ShaderType;
import nl.knokko.texture.ModelTexture;
import nl.knokko.util.Facing;

public abstract class Tile {
	
	private final short runtimeID;
	private short absoluteID;

	Tile() {
		runtimeID = (short) (Tiles.getAmount() + Short.MIN_VALUE);
		Tiles.tiles.add(this);
	}
	
	void setAbsoluteID(short id){
		this.absoluteID = id;
	}
	
	public final short getRuntimeID(){
		return runtimeID;
	}
	
	public final short getAbsoluteID(){
		return absoluteID;
	}
	
	public abstract Matrix4f getMatrix(int tileX, int tileY, int tileZ);
	
	public abstract TileModel getModel();
	
	public abstract ModelTexture getTexture();
	
	public abstract Tiles.RenderForm getRenderForm();
	
	public abstract ShaderType getShaderType();
	
	public abstract int getHeight();
	
	/**
	 * This method determines whether a creature can walk on this tile that is coming from (moverTileX,moverTileY,moverTileZ).
	 * If the mover can walk on this tile, this method returns true, otherwise this method returns false.
	 * @param ownTileY The tile Y coordinate of this tile
	 * @param moverTileY The tile Y coordinate of the creature that tries to walk on this Tile
	 * @param facing The facing the creature is moving towards to reach this Tile
	 */
	public abstract boolean canMoveTo(AreaCreature creature, int ownTileY, int moverTileY, Facing facing);
	
	/**
	 * This method will be called if a creature tries to walk to another tile and is on the same XZ as this tile.
	 * If this method returns true, the move of the creature that tries to walk to a new position will be prevented,
	 * regardless what the other tiles returned.
	 * @param ownTileY The tile Y coordinate of this tile
	 * @param moverTileYFrom The tile Y coordinate the creature comes from
	 * @param moverTileYTo The tile Y coordinate the creature is trying to walk to
	 * @param facing The facing the creature is moving towards
	 */
	public abstract boolean preventMoveIfIn(int ownTileY, int moverTileYFrom, int moverTileYTo, Facing facing);
	
	
	/**
	 * This method will be called if a creature tries to walk to another tile and the destination tile is on the same XZ as this tile.
	 * If this method returns true, the move of the creature that tries to walk to a new position will be prevented,
	 * regardless what the other tiles returned.
	 * @param ownTileY The tile Y coordinate of this tile
	 * @param moverTileYFrom The tile Y coordinate the creature comes from
	 * @param moverTileYTo The tile Y coordinate the creature is trying to walk to
	 * @param facing The facing the creature is moving towards
	 */
	public abstract boolean preventMoveIfTo(int ownTileY, int moverTileYFrom, int moverTileYTo, Facing facing);

	/**
	 * This method determines on what Y coordinate the creature that is walking on this tile should be.
	 * @param ownTileY The tile Y coordinate of this tile.
	 * @param relativeMoverX A number between -16 and 16 that describes the relative X position of the creature on this tile.
	 * @param relativeMoverZ A number between -16 and 16 that describes the relative Z position of the creature on this tile.
	 * @param creature TODO
	 * @return The absolute Y coordinate the creature on this tile should be walking on
	 */
	public abstract int getWalkHeight(int ownTileY, float relativeMoverX, float relativeMoverZ, AreaCreature creature);
	
	/**
	 * This method determines what Y coordinate the creature that is walking on this tile will move to when it moves to another tile.
	 * @param ownTileY The tile Y coordinate of this tile.
	 * @param facing The facing the creature is moving towards.
	 */
	public abstract int getExitTileY(int ownTileY, Facing facing);
	
	/**
	 * This method determines whether the creature that is walking on this tile can walk to another tile, or not.
	 * @param ownTileY The tile Y coordinate of this tile.
	 * @param facing The facing the creature tries to move towards.
	 * @return true if the creature can leave this tile, false if not
	 */
	public abstract boolean canExitTile(int ownTileY, Facing facing);
	
	/**
	 * This method will be called when the player presses the interact key while facing this tile.
	 * @param area The area this tile is located in.
	 * @param tileX The tile X coordinate of this tile.
	 * @param tileY The tile Y coordinate of this tile.
	 * @param tileZ The tile Z coordinate of this tile.
	 */
	public void onInteract(Area area, int tileX, int tileY, int tileZ, Facing playerFacing, boolean playerInside){}
	
	public void onWeakInteract(Area area, int tileX, int tileY, int tileZ, Facing playerFacing, boolean playerInside){}
}
