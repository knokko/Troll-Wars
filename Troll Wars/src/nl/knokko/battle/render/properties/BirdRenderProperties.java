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
package nl.knokko.battle.render.properties;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.BodyBird;
import nl.knokko.util.Maths;
import nl.knokko.view.camera.Camera;

public class BirdRenderProperties implements BattleRenderProperties {
	
	protected final BattleCreature bird;
	
	protected final ModelPart leftLeg;
	protected final ModelPart rightLeg;
	
	protected final float legLength;
	protected final float radius;
	
	protected float flyHeight;
	
	public BirdRenderProperties(BattleCreature bird, BodyBird body){
		this.bird = bird;
		this.radius = Math.max(Math.max(body.bellyDepth(), body.bellyHeight()), body.bellyWidth()) / 2 + body.legLength();
		this.flyHeight = body.bellyHeight() * 3;
		this.legLength = body.legLength();
		ModelPart belly = bird.getModels().get(0);
		this.leftLeg = BodyBird.Helper.getLeftLeg(belly);
		this.rightLeg = BodyBird.Helper.getRightLeg(belly);
	}

	@Override
	public float getRealMinX() {
		return bird.getX() - radius;
	}

	@Override
	public float getRealMinY() {
		return bird.getY() + flyHeight;
	}

	@Override
	public float getRealMinZ() {
		return bird.getZ() - radius;
	}

	@Override
	public float getRealMaxX() {
		return bird.getX() + radius;
	}

	@Override
	public float getRealMaxY() {
		return bird.getY() + flyHeight + 2 * radius;
	}

	@Override
	public float getRealMaxZ() {
		return bird.getZ() + radius;
	}

	@Override
	public float getWidth() {
		return 2 * radius;
	}

	@Override
	public float getHeight() {
		return 2 * radius;
	}

	@Override
	public float getDepth() {
		return 2 * radius;
	}

	@Override
	public float getRealCenterX() {
		return bird.getX();
	}

	@Override
	public float getRealCenterY() {
		return bird.getY() + flyHeight + radius;
	}

	@Override
	public float getRealCenterZ() {
		return bird.getZ();
	}

	@Override
	public float getCilinderRadius() {
		return radius;
	}

	@Override
	public boolean isInside(float x, float y, float z) {
		float dx = x - getRealCenterX();
		float dy = y - getRealCenterY();
		float dz = z - getRealCenterZ();
		return dx * dx + dy * dy + dz * dz <= radius * radius;
	}

	@Override
	public Vector3f[] getCastHands(Camera camera) {
		//A little more difficult since birds don't have claws as extra ModelPart
		Matrix4f birdMatrix = bird.getMatrix(camera);
		Matrix4f leftMatrix = leftLeg.getMatrix(birdMatrix);
		Matrix4f rightMatrix = rightLeg.getMatrix(birdMatrix);
		Vector3f vector = new Vector3f(0, -legLength, 0);
		return new Vector3f[]{Maths.multiply(leftMatrix, vector), Maths.multiply(rightMatrix, vector)};
	}
	
	public float getFlyHeight(){
		return flyHeight;
	}
	
	public void setFlyHeight(float newHeight){
		flyHeight = newHeight;
	}

	@Override
	public float getRealHealthX() {
		return bird.getX();
	}

	@Override
	public float getRealHealthY() {
		return bird.getY() + flyHeight + 2.9f * radius;
	}

	@Override
	public float getRealHealthZ() {
		return bird.getZ();
	}

	@Override
	public float getHealthWidth() {
		return 2 * radius;
	}

	@Override
	public float getHealthHeight() {
		return radius * 0.6f;
	}

	@Override
	public float getRealManaX() {
		return bird.getX();
	}

	@Override
	public float getRealManaY() {
		return bird.getY() + flyHeight + 2.4f * radius;
	}

	@Override
	public float getRealManaZ() {
		return bird.getZ();
	}

	@Override
	public float getManaWidth() {
		return 2 * radius;
	}

	@Override
	public float getManaHeight() {
		return 0.4f * radius;
	}
}