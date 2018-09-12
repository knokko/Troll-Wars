package nl.knokko.animation.body;

import nl.knokko.model.ModelOwner;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.BodyMyrre;

public class AnimatorMyrre implements BodyAnimator {
	
	public static final double FACTOR = 2 * Math.PI;
	
	public static final float UPPER_ARM_ANGLE = 30;
	public static final float ARM_ANGLE = 40;
	
	public static final float LEG_ANGLE = 40;
	
	protected final ModelPart[] leftArmParts;
	protected final ModelPart[] rightArmParts;
	
	protected final ModelPart[] leftLegParts;
	protected final ModelPart[] rightLegParts;

	public AnimatorMyrre(ModelOwner myrre) {
		leftArmParts = BodyMyrre.Helper.getLeftArmParts(myrre);
		rightArmParts = BodyMyrre.Helper.getRightArmParts(myrre);
		leftLegParts = BodyMyrre.Helper.getLeftLegParts(myrre);
		rightLegParts = BodyMyrre.Helper.getRightLegParts(myrre);
	}

	@Override
	public void update(float moveProgress, boolean updateArms, boolean updateLegs) {
		float angleFactor = angleFactor(moveProgress);
		if(updateArms){
			leftArmParts[0].setPitch(angleFactor * UPPER_ARM_ANGLE);
			rightArmParts[0].setPitch(-angleFactor * UPPER_ARM_ANGLE);
			float partPitch = angleFactor * ARM_ANGLE / (leftArmParts.length - 1);
			for(int index = 1; index < leftArmParts.length; index++){
				leftArmParts[index].setPitch(partPitch);
				rightArmParts[index].setPitch(partPitch);
			}
		}
		if(updateLegs){
			leftArmParts[0].setPitch(angleFactor * LEG_ANGLE);
			rightArmParts[0].setPitch(-angleFactor * LEG_ANGLE);
			float partPitch = angleFactor * LEG_ANGLE / (leftLegParts.length - 1);
			for(int index = 1; index < leftLegParts.length; index++){
				leftLegParts[index].setPitch(-partPitch);
				rightLegParts[index].setPitch(partPitch);
			}
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