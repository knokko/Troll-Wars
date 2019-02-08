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

import nl.knokko.battle.creature.BattleCreature;

public abstract class SimpleRenderProperties implements BattleRenderProperties {
	
	protected final BattleCreature creature;
	
	protected final float offsetX;
	protected final float offsetY;
	protected final float offsetZ;
	
	protected final float cilinderRadius;
	protected final float height;

	public SimpleRenderProperties(BattleCreature creature, float offsetX, float offsetY, float offsetZ, float cilinderRadius, float height) {
		this.creature = creature;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.cilinderRadius = cilinderRadius;
		this.height = height;
	}
	
	@Override
	public float getRealMinX(){
		return creature.getX() + offsetX - cilinderRadius;
	}

	@Override
	public float getRealMinY() {
		return creature.getY() + offsetY;
	}
	
	@Override
	public float getRealMinZ(){
		return creature.getZ() + offsetZ - cilinderRadius;
	}
	
	@Override
	public float getRealMaxX(){
		return creature.getX() + offsetX + cilinderRadius;
	}

	@Override
	public float getRealMaxY() {
		return creature.getY() + offsetY + height;
	}
	
	@Override
	public float getRealMaxZ(){
		return creature.getZ() + offsetZ + cilinderRadius;
	}
	
	@Override
	public float getWidth(){
		return 2 * cilinderRadius;
	}

	@Override
	public float getHeight() {
		return height;
	}
	
	@Override
	public float getDepth(){
		return 2 * cilinderRadius;
	}

	@Override
	public float getRealCenterX() {
		return creature.getX() + offsetX;
	}
	
	@Override
	public float getRealCenterY(){
		return creature.getY() + offsetY + height / 2;
	}

	@Override
	public float getRealCenterZ() {
		return creature.getZ() + offsetZ;
	}

	@Override
	public float getCilinderRadius() {
		return cilinderRadius;
	}
	
	@Override
	public float getRealHealthX(){
		return creature.getX() + offsetX;
	}
	
	@Override
	public float getRealHealthY(){
		return creature.getY() + offsetY + 1.4f * height;
	}
	
	@Override
	public float getRealHealthZ(){
		return creature.getZ() + offsetZ;
	}
	
	@Override
	public float getHealthWidth(){
		return 4 * cilinderRadius;
	}
	
	@Override
	public float getHealthHeight(){
		return height * 0.3f;
	}
	
	@Override
	public float getRealManaX(){
		return creature.getX() + offsetX;
	}
	
	@Override
	public float getRealManaY(){
		return creature.getY() + offsetY + 1.15f * height;
	}
	
	@Override
	public float getRealManaZ(){
		return creature.getZ() + offsetZ;
	}
	
	@Override
	public float getManaWidth(){
		return 4 * cilinderRadius;
	}
	
	@Override
	public float getManaHeight(){
		return height * 0.2f;
	}
	
	@Override
	public boolean isInside(float x, float y, float z){
		if(y >= getRealMinY() && y <= getRealMaxY()){
			float dx = x - getRealCenterX();
			float dz = z - getRealCenterZ();
			float radius = getCilinderRadius();
			return dx * dx + dz * dz <= radius * radius;
		}
		return false;
	}
}