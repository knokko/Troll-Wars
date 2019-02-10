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

import nl.knokko.input.KeyInput;
import nl.knokko.main.Game;
import nl.knokko.util.Maths;

import org.lwjgl.util.vector.Vector3f;

public class CameraBattle implements Camera {
	
	private float yaw;
	
	private float y;

	public CameraBattle() {
		yaw = 0f;
		y = 100f;
	}

	@Override
	public float getRadPitch() {
		return 0;
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
		return 0;
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
		Vector3f vec = Maths.getRotationVector(0, yaw, 0);
		vec.scale(-400);
		vec.y = y;
		return vec;
	}

	@Override
	public float getMinX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getMinZ() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getMaxX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getMaxZ() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update() {
		if(KeyInput.isKeyDown(Game.getOptions().keyEast)){
			yaw++;
			if(yaw >= 360)
				yaw -= 360;
		}
		if(KeyInput.isKeyDown(Game.getOptions().keyWest)){
			yaw--;
			if(yaw < 0)
				yaw += 360;
		}
		if(KeyInput.isKeyDown(Game.getOptions().keyNorth))
			y++;
		if(KeyInput.isKeyDown(Game.getOptions().keySouth))
			y--;
	}
}
