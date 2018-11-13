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

import org.lwjgl.util.vector.Vector3f;

public interface Camera {

	float getRadPitch();

	float getRadYaw();

	float getRadRoll();
	
	float getDegPitch();
	
	float getDegYaw();
	
	float getDegRoll();

	Vector3f getPosition();
	
	/**
	 * @return The minimum X-Coordinate that can be seen if the minimum Y-coordinate is 0.
	 */
	float getMinX();
	
	/**
	 * @return The minimum Z-Coordinate that can be seen if the minimum Y-coordinate is 0.
	 */
	float getMinZ();
	
	float getMaxX();
	
	float getMaxZ();
	
	void update();
}
