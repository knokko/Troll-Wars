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
