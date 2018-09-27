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

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.BodyMyrre;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class MyrreRenderProperties extends SimpleRenderProperties {
	
	protected final ModelPart leftHand;
	protected final ModelPart rightHand;

	public MyrreRenderProperties(BattleCreature creature, BodyMyrre body) {
		super(creature, 0, 0, 0, Math.max(body.bellyWidth(), body.bellyDepth()), body.legLength() + body.bellyHeight());
		leftHand = BodyMyrre.Helper.getLeftHand(creature);
		rightHand = BodyMyrre.Helper.getRightHand(creature);
	}

	@Override
	public Vector3f[] getCastHands() {
		Matrix4f matrix = creature.getMatrix();
		Matrix4f left = leftHand.getMatrix(matrix);
		Matrix4f right = rightHand.getMatrix(matrix);
		return new Vector3f[]{new Vector3f(left.m20, left.m21, -left.m22), new Vector3f(right.m20, right.m21, -right.m22)};
	}
}