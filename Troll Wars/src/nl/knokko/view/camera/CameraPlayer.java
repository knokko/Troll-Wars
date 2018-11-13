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
package nl.knokko.view.camera;

import nl.knokko.input.KeyInput;
import nl.knokko.main.Game;
import nl.knokko.render.main.WorldRenderer;
import nl.knokko.util.Maths;
import nl.knokko.util.position.AreaPosition;

import org.lwjgl.util.vector.Vector3f;

public class CameraPlayer implements Camera {
	
	private AreaPosition target;
	
	private float yaw;
	private float pitch;
	
	private float distance = 500;

	public CameraPlayer(AreaPosition target) {
		this.target = target;
		this.pitch = 90;
	}

	@Override
	public float getRadPitch() {
		return Maths.toRadians(pitch);
	}

	@Override
	public float getRadYaw() {
		return Maths.toRadians(yaw);
	}

	@Override
	public float getRadRoll() {
		return 0;
	}

	@Override
	public float getDegPitch() {
		return pitch;
	}

	@Override
	public float getDegYaw() {
		return yaw;
	}

	@Override
	public float getDegRoll() {
		return 0;
	}

	@Override
	public Vector3f getPosition() {
		return new Vector3f(target.getX() * 2 - distance * Maths.sinRad(getRadYaw()) * Maths.cosRad(getRadPitch()), (target.getY() + 25) * 2 + distance * Maths.sinRad(getRadPitch()), target.getZ() * 2 + distance * Maths.cosRad(getRadYaw()) * Maths.cosRad(getRadPitch()));
	}

	@Override
	public float getMinX() {
		return target.getX() - extra();
	}

	@Override
	public float getMinZ() {
		return target.getZ() - extra();
	}

	@Override
	public float getMaxX() {
		return target.getX() + extra();
	}

	@Override
	public float getMaxZ() {
		return target.getZ() + extra();
	}
	
	private float extra(){
		return (float) (Math.tan(Math.toRadians(WorldRenderer.FOV / 2)) * Math.sqrt(2) * getPosition().y);
	}

	@Override
	public void update() {
		if(KeyInput.isKeyDown(Game.getOptions().keyCameraRight))
			yaw++;
		if(KeyInput.isKeyDown(Game.getOptions().keyCameraLeft))
			yaw--;
		if(KeyInput.isKeyDown(Game.getOptions().keyCameraUp) && pitch < 90)
			pitch++;
		if(KeyInput.isKeyDown(Game.getOptions().keyCameraDown) && pitch > 20)
			pitch--;
		if(KeyInput.isKeyDown(Game.getOptions().keyZoomOut))
			distance += 2;
		if(KeyInput.isKeyDown(Game.getOptions().keyZoomIn) && distance > 20)
			distance -= 2;
		//TODO make sure the camera doesn't move outside the area/cave
	}
}
