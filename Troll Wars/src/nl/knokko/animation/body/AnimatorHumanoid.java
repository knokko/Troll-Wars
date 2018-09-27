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
package nl.knokko.animation.body;

import nl.knokko.model.ModelPart;

public class AnimatorHumanoid implements BodyAnimator {
	
	public static final float LEG_ANGLE = 45;
	public static final float ARM_ANGLE = 35;
	
	//private static final float FACTOR = (float) (2 * Math.PI);
	
	protected ModelPart leftArm;
	protected ModelPart rightArm;
	
	protected ModelPart leftLeg;
	protected ModelPart rightLeg;
	
	protected ModelPart leftUnderLeg;
	protected ModelPart rightUnderLeg;

	public AnimatorHumanoid(ModelPart leftArm, ModelPart rightArm, ModelPart leftLeg, ModelPart rightLeg, ModelPart leftUnderLeg, ModelPart rightUnderLeg) {
		this.leftArm = leftArm;
		this.rightArm = rightArm;
		this.leftLeg = leftLeg;
		this.rightLeg = rightLeg;
		this.leftUnderLeg = leftUnderLeg;
		this.rightUnderLeg = rightUnderLeg;
	}

	@Override
	public void update(float moveProgress, boolean updateArms, boolean updateLegs) {
		//moveProgress = (float) Math.sin(moveProgress * FACTOR);
		float angleFactor = angleFactor(moveProgress);
		if(updateArms){
			leftArm.setPitch(angleFactor * ARM_ANGLE - 90);
			rightArm.setPitch(-angleFactor * ARM_ANGLE - 90);
		}
		if(updateLegs){
			leftLeg.setPitch(angleFactor * LEG_ANGLE);
			rightLeg.setPitch(-angleFactor * LEG_ANGLE);
			leftUnderLeg.setPitch(-Math.abs(angleFactor * LEG_ANGLE));
			rightUnderLeg.setPitch(-Math.abs(-angleFactor * LEG_ANGLE));
		}
	}
	
	private float angleFactor(float moveProgress){
		if(moveProgress <= 0.25f)
			return moveProgress * 4;
		if(moveProgress <= 0.75f)
			return 2f - moveProgress * 4;
		return moveProgress * 4 - 4f;
	}
}
