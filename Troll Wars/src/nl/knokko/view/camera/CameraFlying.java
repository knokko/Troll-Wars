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
package nl.knokko.view.camera;

import java.util.ArrayList;

import nl.knokko.input.KeyInput;
import nl.knokko.input.MouseInput;
import nl.knokko.input.MouseMoveEvent;
import nl.knokko.util.Maths;
import nl.knokko.util.position.SpawnPosition;
import nl.knokko.gui.keycode.KeyCode;

import org.lwjgl.util.vector.Vector3f;

public class CameraFlying implements Camera {
	
	private float yaw;
	private float pitch;
	
	private Vector3f position;

	public CameraFlying() {
		this(new Vector3f(32, 100, 32), 90, 0);
	}
	
	public CameraFlying(Vector3f position, float pitch, float yaw){
		this.pitch = pitch;
		this.yaw = yaw;
		this.position = position;
	}

	public CameraFlying(SpawnPosition playerSpawn) {
		this(new Vector3f(playerSpawn.getTileX() * 64, playerSpawn.getTileY() * 16, playerSpawn.getTileZ() * 64), 0, 0);
	}

	@Override
	public float getRadPitch() {
		return (float) Math.toRadians(pitch);
	}

	@Override
	public float getRadYaw() {
		return (float) Math.toRadians(yaw);
	}

	@Override
	public float getRadRoll() {
		return 0;
	}
	
	@Override
	public float getDegPitch(){
		return pitch;
	}
	
	@Override
	public float getDegYaw(){
		return yaw;
	}
	
	@Override
	public float getDegRoll(){
		return 0;
	}

	@Override
	public Vector3f getPosition() {
		return position;
	}

	@Override
	public float getMinX() {
		return position.x / 2 - 2000;
	}

	@Override
	public float getMinZ() {
		return position.z / 2 - 2000;
	}

	@Override
	public float getMaxX() {
		return position.x / 2 + 2000;
	}

	@Override
	public float getMaxZ() {
		return position.z / 2 + 2000;
	}
	
	@Override
	public void update(){
		ArrayList<MouseMoveEvent> moves = MouseInput.getMouseMoves();
		for(MouseMoveEvent event : moves){
			yaw += event.getDeltaX() * 500;
			yaw = yaw % 360;
			pitch -= event.getDeltaY() * 500;
			pitch = pitch % 360;
		}
		float speed = KeyInput.isKeyDown(KeyCode.KEY_T) ? 15 : 5;
		if(KeyInput.isKeyDown(KeyCode.KEY_CAPSLOCK))
			speed = 0.5f;
		Vector3f forward = Maths.getRotationVector(pitch, yaw, 0);
		if(KeyInput.isKeyDown(KeyCode.KEY_W)){
			position.x += forward.x * speed;
			position.y += forward.y * speed;
			position.z += forward.z * speed;
		}
		if(KeyInput.isKeyDown(KeyCode.KEY_D)){
			position.x -= forward.z * speed;
			position.z += forward.x * speed;
		}
		if(KeyInput.isKeyDown(KeyCode.KEY_S)){
			position.x -= forward.x * speed;
			position.y -= forward.y * speed;
			position.z -= forward.z * speed;
		}
		if(KeyInput.isKeyDown(KeyCode.KEY_A)){
			position.x += forward.z * speed;
			position.z -= forward.x * speed;
		}
		if(KeyInput.isKeyDown(KeyCode.KEY_SPACE))
			position.y += speed;
		if(KeyInput.isKeyDown(KeyCode.KEY_SHIFT))
			position.y -= speed;
	}
}
