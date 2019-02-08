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
package nl.knokko.battle.render.properties;

import org.lwjgl.util.vector.Vector3f;

import nl.knokko.view.camera.Camera;

public interface BattleRenderProperties {
	
	default float getMinX(Camera camera) {
		return getRealMinX() - camera.getPosition().x;
	}
	
	float getRealMinX();
	
	default float getMinY(Camera camera) {
		return getRealMinY() - camera.getPosition().y;
	}
	
	float getRealMinY();
	
	default float getMinZ(Camera camera) {
		return getRealMinZ() - camera.getPosition().z;
	}
	
	float getRealMinZ();
	
	default float getMaxX(Camera camera) {
		return getRealMaxX() - camera.getPosition().x;
	}
	
	float getRealMaxX();
	
	default float getMaxY(Camera camera) {
		return getRealMaxY() - camera.getPosition().y;
	}
	
	float getRealMaxY();
	
	default float getMaxZ(Camera camera) {
		return getRealMaxZ() - camera.getPosition().z;
	}
	
	float getRealMaxZ();
	
	float getWidth();
	
	float getHeight();
	
	float getDepth();
	
	default float getCentreX(Camera camera) {
		return getRealCenterX() - camera.getPosition().x;
	}
	
	float getRealCenterX();
	
	default float getCentreY(Camera camera) {
		return getRealCenterY() - camera.getPosition().y;
	}
	
	float getRealCenterY();
	
	default float getCentreZ(Camera camera) {
		return getRealCenterZ() - camera.getPosition().z;
	}
	
	float getRealCenterZ();
	
	float getCilinderRadius();
	
	default float getHealthX(Camera camera) {
		return getRealHealthX() - camera.getPosition().x;
	}
	
	float getRealHealthX();
	
	default float getHealthY(Camera camera) {
		return getRealHealthY() - camera.getPosition().y;
	}
	
	float getRealHealthY();
	
	default float getHealthZ(Camera camera) {
		return getRealHealthZ() - camera.getPosition().z;
	}
	
	float getRealHealthZ();
	
	float getHealthWidth();
	
	float getHealthHeight();
	
	default float getManaX(Camera camera) {
		return getRealManaX() - camera.getPosition().x;
	}
	
	float getRealManaX();
	
	default float getManaY(Camera camera) {
		return getRealManaY() - camera.getPosition().y;
	}
	
	float getRealManaY();
	
	default float getManaZ(Camera camera) {
		return getRealManaZ() - camera.getPosition().z;
	}
	
	float getRealManaZ();
	
	float getManaWidth();
	
	float getManaHeight();
	
	boolean isInside(float x, float y, float z);
	
	Vector3f[] getCastHands(Camera camera);
}