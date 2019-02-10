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
package nl.knokko.model;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import nl.knokko.area.creature.AreaCreature;
import nl.knokko.model.type.AbstractModel;
import nl.knokko.texture.ModelTexture;
import nl.knokko.util.Maths;

public class ModelPart {
	
	private static final ModelPart[] EMPTY_ARRAY = new ModelPart[0];
	
	private AbstractModel model;
	private ModelTexture texture;
	
	private Vector3f position;
	
	protected float rotationX;
	protected float rotationY;
	protected float rotationZ;
	
	private ModelPart parent;

	public ModelPart(AbstractModel model, ModelTexture texture, Vector3f relativePosition, float pitch, float yaw, float roll){
		this.model = model;
		this.texture = texture;
		position = relativePosition;
		rotationX = pitch;
		rotationY = yaw;
		rotationZ = roll;
	}
	
	public ModelPart(AbstractModel model, ModelTexture texture, Vector3f relativePosition){
		this(model, texture, relativePosition, 0, 0, 0);
	}
	
	public AbstractModel getModel(){
		return model;
	}
	
	public ModelTexture getTexture(){
		return texture;
	}
	
	public Vector3f getRelativePosition(){
		return position;
	}
	
	public ModelPart[] getChildren(){
		return EMPTY_ARRAY;
	}
	
	public ModelPart getParent(){
		return parent;
	}
	
	public ModelPart setParent(ModelPart parent){
		this.parent = parent;
		return this;
	}
	
	public void setModel(AbstractModel model){
		this.model = model;
	}
	
	public void setPitch(float degree){
		rotationX = fixRotation(degree);
	}
	
	public void setYaw(float degree){
		rotationY = fixRotation(degree);
	}
	
	public void setRoll(float degree){
		rotationZ = fixRotation(degree);
	}
	
	private float fixRotation(float angle){
		while(angle < 0)
			angle += 360;
		while(angle >= 360)
			angle -= 360;
		return angle;
	}
	
	public float getPitch(){
		return rotationX;
	}
	
	public float getYaw(){
		return rotationY;
	}
	
	public float getRoll(){
		return rotationZ;
	}
	
	public boolean movePitchTowards(float goalRotation, float maxRotationSpeed){
		if(rotationX == goalRotation)
			return true;
		setPitch(moveTowards(getPitch(), goalRotation, maxRotationSpeed));
		return rotationX == goalRotation;
	}
	
	public boolean moveYawTowards(float goalRotation, float maxRotationSpeed){
		if(rotationY == goalRotation)
			return true;
		setYaw(moveTowards(getYaw(), goalRotation, maxRotationSpeed));
		return rotationY == goalRotation;
	}
	
	public boolean moveRollTowards(float goalRotation, float maxRotationSpeed){
		if(rotationZ == goalRotation)
			return true;
		setRoll(moveTowards(getRoll(), goalRotation, maxRotationSpeed));
		return rotationZ == goalRotation;
	}
	
	private float moveTowards(float current, float goalRotation, float maxRotationSpeed){
		if(goalRotation >= 360 || goalRotation < 0)
			throw new IllegalArgumentException("goal rotation must be between 0 (inclusive) and 360 (exclusive): " + goalRotation);
		if(current >= 360 || current < 0)
			throw new IllegalArgumentException("current rotation must be between 0 (inclusive) and 360 (exclusive): " + current);
		if(maxRotationSpeed <= 0)
			throw new IllegalArgumentException("maximum rotation speed can't be smaller than 0! (" + maxRotationSpeed + ")");
		if(Math.abs(goalRotation - current) <= maxRotationSpeed || (goalRotation > current && 360 - goalRotation + current <= maxRotationSpeed || (goalRotation < current && 360 - current + goalRotation <= maxRotationSpeed))){
			current = goalRotation;
		}
		else if(goalRotation > current){
			if(goalRotation - current <= 360 - goalRotation + current)
				current += maxRotationSpeed;
			else {
				current -= maxRotationSpeed;
				if(current < 0)
					current += 360f;
			}
		}
		else {
			if(current - goalRotation <= 360 - current + goalRotation)
				current -= maxRotationSpeed;
			else {
				current += maxRotationSpeed;
				if(current >= 360f)
					current -= 360f;
			}
		}
		return current;
	}
	
	public void addChild(ModelPart child){
		throw new IllegalStateException("ModelParts can't have children, only ModelPartParent can!");
	}
	
	public Matrix4f getMatrix(AreaCreature owner){
		return getMatrix(owner.getRenderMatrix());
	}
	
	public Matrix4f getMatrix(Matrix4f owner){
		Matrix4f own = Maths.createTransformationMatrix(position, rotationX, rotationY, rotationZ, 1);
		if(parent == null)
			return Matrix4f.mul(owner, own, null);
		return Matrix4f.mul(parent.getMatrix(owner), own, null);
	}
	
	@Override
	public String toString(){
		return "ModelPart[model = " + model + ", texture = " + texture + ", position = " + position + "]";
	}
	
	@Override
	public ModelPart clone(){
		return new ModelPart(model, texture, new Vector3f(position), rotationX, rotationY, rotationZ).setParent(parent);
	}
}
