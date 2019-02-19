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
package nl.knokko.util;

import nl.knokko.util.position.AreaPosition;
import nl.knokko.view.camera.Camera;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class Maths {
	
	private static final int[] POWERS = new int[30];
	
	public static final float PI = (float) Math.PI;
	
	static {
		int power = 1;
		for(int i = 0; i < POWERS.length; i++){
			POWERS[i] = power;
			power *= 2;
		}
	}
	
	public static final boolean powerOf2(int number){
		for(int power : POWERS)
			if(number == power)
				return true;
		return false;
	}
	
	public static final byte log2Up(int number){
		if(number < 1)
			throw new IllegalArgumentException("Number (" + number + ") is too small!");
		for(byte i = 0; i < POWERS.length; i++){
			if(number <= POWERS[i])
				return i;
		}
		throw new IllegalArgumentException("Number (" + number + ") is greater than 2^30 !");
	}
	
	public static final int next2Power(int number){
		if(number < 1)
			throw new IllegalArgumentException("Number (" + number + ") is too small!");
		for(int i = 0; i < POWERS.length; i++){
			if(number <= POWERS[i])
				return POWERS[i];
		}
		throw new IllegalArgumentException("Number (" + number + ") is greater than 2^30 !");
	}
	
	public static final Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale){
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
		return matrix;
	}

	public static final Matrix4f createTransformationMatrix(Vector3f position, float rx, float ry, float rz, float size){
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(position, matrix, matrix);
		Matrix4f.rotate((float) toRadians(rz), new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.rotate((float) toRadians(ry), new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate((float) toRadians(rx), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.scale(new Vector3f(size, size, size), matrix, matrix);
		return matrix;
	}
	
	public static final Matrix4f createTransformationMatrix(AreaPosition position, float rotX, float rotY, float rotZ, float size){
		Matrix4f matrix = createTransformationMatrix(new Vector3f(position.getX() * 2, position.getY() * 2, position.getZ() * 2), rotX, rotY, rotZ, size);
		return matrix;
	}
	
	public static final Matrix4f createTransformationMatrix(AreaPosition position, Camera camera, float rotX, float rotY, float rotZ, float size){
		Vector3f c = camera.getPosition();
		Matrix4f matrix = createTransformationMatrix(new Vector3f(position.getX() * 2 - c.x, position.getY() * 2 - c.y, position.getZ() * 2 - c.z), rotX, rotY, rotZ, size);
		return matrix;
	}
	
	public static final Matrix4f createTransformationMatrix(Vector3f position, float rx, float ry, float rz, float sx, float sy, float sz){
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(position, matrix, matrix);
		Matrix4f.rotate((float) toRadians(rz), new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.rotate((float) toRadians(ry), new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate((float) toRadians(rx), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.scale(new Vector3f(sx, sy, sz), matrix, matrix);
		return matrix;
	}
	
	public static final Matrix4f createTransformationMatrix(Vector3f position, float size){
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(position, matrix, matrix);
		Matrix4f.scale(new Vector3f(size, size, size), matrix, matrix);
		return matrix;
	}
	
	public static final Matrix4f createTransformationMatrix(Vector3f position, float scaleX, float scaleY, float scaleZ){
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(position, matrix, matrix);
		Matrix4f.scale(new Vector3f(scaleX, scaleY, scaleZ), matrix, matrix);
		return matrix;
	}
	
	/*
	public static final Matrix4f createTransformationMatrix(Entity entity){
		return createTransformationMatrix(Vector3f.add(entity.getPosition(), entity.getBaseModelPosition(), null), entity.getRotationX(), entity.getRotationY(), entity.getRotationZ(), entity.getSize());
	}
	
	public static final Matrix4f createTransformationMatrix(SubModel model){
		return createTransformationMatrix(model.getRelativePosition(), model.rotationX, model.rotationY, model.rotationZ, 1);
	}
	*/
	
	public static final Matrix4f createViewMatrix(Camera camera){
		Matrix4f view = new Matrix4f();
		view.setIdentity();
		Matrix4f.rotate(camera.getRadPitch(), new Vector3f(1, 0, 0), view, view);
		Matrix4f.rotate(camera.getRadYaw(), new Vector3f(0, 1, 0), view, view);
		Matrix4f.rotate(camera.getRadRoll(), new Vector3f(0, 0, 1), view, view);
		Vector3f c = camera.getPosition();
		Vector3f invert = new Vector3f(-c.x, -c.y, -c.z);
		Matrix4f.translate(invert, view, view);
		return view;
	}
	
	public static final Matrix4f createOriginViewMatrix(Camera camera) {
		Matrix4f view = new Matrix4f();
		view.setIdentity();
		Matrix4f.rotate(camera.getRadPitch(), new Vector3f(1, 0, 0), view, view);
		Matrix4f.rotate(camera.getRadYaw(), new Vector3f(0, 1, 0), view, view);
		Matrix4f.rotate(camera.getRadRoll(), new Vector3f(0, 0, 1), view, view);
		return view;
	}
	
	public static final Vector3f getRotationVector(float pitch, float yaw, float roll){
		float r = (float) toRadians(roll);
		Matrix4f mat = createTransformationMatrix(new Vector3f(), pitch, yaw, 0, 1);
		Vector3f vector = new Vector3f(mat.m20, mat.m21, -mat.m22);
		return new Vector3f((float)(vector.x * cos(r) + vector.y * sin(r)), (float)(vector.y * cos(r) - vector.x * sin(r)), vector.z);
	}
	
	public static final double getDistance(Vector3f v1, Vector3f v2){
		return Math.sqrt((v1.x - v2.x) * (v1.x - v2.x) + (v1.y - v2.y) * (v1.y - v2.y) + (v1.z - v2.z) * (v1.z - v2.z));
	}
	
	public static final double getYaw(Vector3f vector){
		Vector3f vec = new Vector3f(vector);
		vec.y = 0;
		if(vec.length() == 0)
			return 0;
		vec.normalise();
		double angle = Math.toDegrees(-Math.acos(-vec.z));
		if(vec.x < 0)
			angle = -angle;
		if(angle < 0)
			angle += 360;
		if(angle > 360)
			angle -= 360;
		return angle;
	}

	public static final double getPitch(Vector3f normalized) {
		return toDegrees(-asin(normalized.y));
	}
	
	public static final float max(float... floats){
		float max = floats[0];
		for(float f : floats)
			if(f > max)
				max = f;
		return max;
	}
	
	public static final float min(float... floats){
		float min = floats[0];
		for(float f : floats)
			if(f < min)
				min = f;
		return min;
	}
	
	public static final float sinRad(float angle){
		return (float) Math.sin(angle);
	}
	
	public static final float cosRad(float angle){
		return (float) Math.cos(angle);
	}
	
	public static final float sin(float angle){
		return (float) Math.sin(toRadians(angle));
	}
	
	public static final float cos(float angle){
		return (float) Math.cos(toRadians(angle));
	}
	
	public static final float tan(float angle){
		return (float) Math.tan(toRadians(angle));
	}
	
	public static final float asin(float value){
		return (float) Math.toDegrees(Math.asin(value));
	}
	
	public static final float atan(float value){
		return (float) Math.toDegrees(Math.atan(value));
	}
	
	public static final float toRadians(float degrees){
		return (float) Math.toRadians(degrees);
	}
	
	public static final float toDegrees(float radians){
		return (float) Math.toDegrees(radians);
	}
	
	public static final float sqrt(float value){
		return (float) Math.sqrt(value);
	}
	
	public static final boolean AND(boolean condition1, boolean condition2){
		return condition1 && condition2;
	}
	
	public static final boolean AND(boolean condition1, boolean condition2, boolean condition3){
		return condition1 && condition2 && condition3;
	}
	
	public static Vector3f multiply(Matrix4f matrix, Vector3f vector){
		return new Vector3f(
				matrix.m00 * vector.x + matrix.m10 * vector.y + matrix.m20 * vector.z + matrix.m30,
				matrix.m01 * vector.x + matrix.m11 * vector.y + matrix.m21 * vector.z + matrix.m31,
				matrix.m02 * vector.x + matrix.m12 * vector.y + matrix.m22 * vector.z + matrix.m32
			);
	}
	
	public static Vector4f multiply(Matrix4f matrix, Vector4f vector){
		return new Vector4f(
				matrix.m00 * vector.x + matrix.m10 * vector.y + matrix.m20 * vector.z + matrix.m30 * vector.w,
				matrix.m01 * vector.x + matrix.m11 * vector.y + matrix.m21 * vector.z + matrix.m31 * vector.w,
				matrix.m02 * vector.x + matrix.m12 * vector.y + matrix.m22 * vector.z + matrix.m32 * vector.w,
				matrix.m03 * vector.x + matrix.m13 * vector.y + matrix.m23 * vector.z + matrix.m33 * vector.w
			);
	}
}
