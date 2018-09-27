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
package nl.knokko.battle.render.properties;

import org.lwjgl.util.vector.Vector3f;

public interface BattleRenderProperties {
	
	float getMinX();
	
	float getMinY();
	
	float getMinZ();
	
	float getMaxX();
	
	float getMaxY();
	
	float getMaxZ();
	
	float getWidth();
	
	float getHeight();
	
	float getDepth();
	
	float getCentreX();
	
	float getCentreY();
	
	float getCentreZ();
	
	float getCilinderRadius();
	
	float getHealthX();
	
	float getHealthY();
	
	float getHealthZ();
	
	float getHealthWidth();
	
	float getHealthHeight();
	
	float getManaX();
	
	float getManaY();
	
	float getManaZ();
	
	float getManaWidth();
	
	float getManaHeight();
	
	boolean isInside(float x, float y, float z);
	
	Vector3f[] getCastHands();
}