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
package nl.knokko.area.creature;

import java.util.ArrayList;

import org.lwjgl.util.vector.Matrix4f;

import nl.knokko.animation.body.BodyAnimator;
import nl.knokko.area.Area;
import nl.knokko.area.Block;
import nl.knokko.model.ModelOwner;
import nl.knokko.model.ModelPart;
import nl.knokko.shaders.ShaderType;
import nl.knokko.util.Facing;
import nl.knokko.util.Maths;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.position.AreaPosition;
import nl.knokko.util.position.SpawnPosition;
import nl.knokko.view.camera.Camera;

public abstract class AreaCreature implements ModelOwner {
	
	protected final ArrayList<ModelPart> models;
	protected BodyAnimator animator;
	
	protected final AreaPosition position;
	
	protected Area area;
	
	protected Block currentBlock;
	protected Block destinationBlock;
	
	protected float rotationY;
	
	protected byte moveX;
	protected byte moveZ;
	
	private Matrix4f renderMatrix;

	public AreaCreature(SpawnPosition spawn){
		models = new ArrayList<ModelPart>();
		position = new AreaPosition(spawn);
	}
	
	public void setArea(Area area){
		this.area = area;
		if(position.getTileX() != -1)
			currentBlock = findBlock();
		createBody();
		animator = createAnimator();
	}
	
	public Area getArea(){
		return area;
	}
	
	public void update(){
		if(moveX > 0){
			position.move(1, 0, 0);
			if(moveX == 16){
				currentBlock = destinationBlock;
				destinationBlock = null;
			}
			moveX--;
			animator.update(moveX / 32f, true, true);
		}
		if(moveX < 0){
			position.move(-1, 0, 0);
			if(moveX == -16){
				currentBlock = destinationBlock;
				destinationBlock = null;
			}
			moveX++;
			animator.update(-moveX / 32f, true, true);
		}
		if(moveZ > 0){
			position.move(0, 0, 1);
			if(moveZ == 16){
				currentBlock = destinationBlock;
				destinationBlock = null;
			}
			moveZ--;
			animator.update(moveZ / 32f, true, true);
		}
		if(moveZ < 0){
			position.move(0, 0, -1);
			if(moveZ == -16){
				currentBlock = destinationBlock;
				destinationBlock = null;
			}
			moveZ++;
			animator.update(-moveZ / 32f, true, true);
		}
		byte dz = moveZ;
		if(dz < 0)
			dz = (byte) -dz;
		if(dz > 15)
			dz = (byte) (32 - dz);
		else
			dz = (byte) -dz;
		byte dx = moveX;
		if(dx < 0)
			dx = (byte) -dx;
		if(dx > 15)
			dx = (byte)(32 - dx);
		else
			dx = (byte) -dx;
		position.setY(currentBlock.getTile().getWalkHeight(currentBlock.getTileY() + 128, moveX > 0 ? (-dx / 16f) : (dx / 16f), moveZ > 0 ? (-dz / 16f) : (dz / 16f), this));
		updateCreature();
	}
	
	protected Block findBlock(){
		Block[] blocks = area.getTiles().getBlocks(position.getTileX(), position.getTileZ());
		for(Block block : blocks){
			if(block.getTile().canMoveTo(this, block.getTileY(), block.getTileY(), null) && block.getTileY() + 128 == position.getTileY())
				return block;
		}
		throw new RuntimeException("Invalid spawn point: " + position + " (blocks.length = " + blocks.length + ")");
	}
	
	protected Block findBlock(short blockID, byte tileY){
		Block[] blocks = area.getTiles().getBlocks(position.getTileX(), position.getTileZ());
		for(Block block : blocks){
			if(block.getTileY() == tileY && block.getTile().getAbsoluteID() == blockID)
				return block;
		}
		//for(Block block : blocks)
			//System.out.println("block = " + block);
		throw new RuntimeException("Can't find block with id " + blockID + " and y " + tileY + " within " + blocks.length + " blocks!");
	}
	
	public AreaPosition getPosition(){
		return position;
	}
	
	public ArrayList<ModelPart> getModels(){
		return models;
	}
	
	public ShaderType getShaderType(){
		return ShaderType.NORMAL;
	}
	
	/**
	 * 
	 * @param targetX The tile X of the target position
	 * @param targetZ The tile Z of the target position
	 * @return True if this creature should face this side, false otherwise
	 */
	public boolean tryMove(int targetX, int targetZ){
		if(moveX != 0 || moveZ != 0)
			return false;
		Block[] currentBlocks = area.getTiles().getBlocks(position.getTileX(), position.getTileZ());
		Block[] targetBlocks = area.getTiles().getBlocks(targetX, targetZ);
		Facing facing = Facing.fromXZ(targetX - position.getTileX(), targetZ - position.getTileZ());
		if(!currentBlock.getTile().canExitTile(currentBlock.getTileY(), facing))
			return false;
		int exitHeight = currentBlock.getTile().getExitTileY(currentBlock.getTileY(), facing);
		Block destination = null;
		for(Block block : targetBlocks){
			if(block.getTile().canMoveTo(this, block.getTileY(), exitHeight, facing))
				destination = block;
		}
		if(destination == null)
			return true;
		for(Block block : currentBlocks)
			if(block.getTile().preventMoveIfIn(block.getTileY(), exitHeight, destination.getTileY(), facing))
				return true;
		for(Block block : targetBlocks)
			if(block != destination && block.getTile().preventMoveIfTo(block.getTileY(), exitHeight, destination.getTileY(), facing))
				return true;
		destinationBlock = destination;
		moveX = (byte) (facing.getDX() * 32);
		moveZ = (byte) (facing.getDZ() * 32);
		return true;
	}
	
	public void tryMoveNorth(){
		if(tryMove(position.getTileX(), position.getTileZ() - 1))
			rotationY = 0;
	}
	
	public void tryMoveEast(){
		if(tryMove(position.getTileX() + 1, position.getTileZ()))
			rotationY = 90;
	}
	
	public void tryMoveSouth(){
		if(tryMove(position.getTileX(), position.getTileZ() + 1))
			rotationY = 180;
	}
	
	public void tryMoveWest(){
		if(tryMove(position.getTileX() - 1, position.getTileZ()))
			rotationY = 270;
	}
	
	protected void addModel(ModelPart model){
		models.add(model);
	}
	
	protected abstract void updateCreature();
	
	protected abstract void createBody();
	
	protected abstract BodyAnimator createAnimator();
	
	public abstract void interact();

	public Matrix4f getRealMatrix(){
		return Maths.createTransformationMatrix(position, 0, -rotationY, 0, 1);
	}
	
	public void setRenderMatrix(Camera camera) {
		if (camera != null)
			renderMatrix = Maths.createTransformationMatrix(position, camera, 0, -rotationY, 0, 1);
		else
			renderMatrix = null;
	}
	
	public Matrix4f getRenderMatrix() {
		return renderMatrix;
	}
	
	public void refreshPosition(){
		currentBlock = findBlock();
	}
	
	public void setYaw(float yaw){
		rotationY = yaw;
	}
	
	public float getYaw(){
		return rotationY;
	}
	
	public void save(BitOutput buffer){
		buffer.addInt(position.getTileX());
		buffer.addInt(position.getTileZ());
		buffer.addByte(currentBlock.getTileY());
		buffer.addShort(currentBlock.getTile().getAbsoluteID());
		buffer.addFloat(rotationY);
	}
	
	public void load(BitInput buffer){
		position.setX(32 * buffer.readInt());
		position.setZ(32 * buffer.readInt());
		byte tileY = buffer.readByte();
		short tileID = buffer.readShort();
		rotationY = buffer.readFloat();
		currentBlock = findBlock(tileID, tileY);
	}
}
