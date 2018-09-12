package nl.knokko.animation.body;

public interface BodyAnimator {
	
	/**
	 * Updates the BodyAnimator. This should be called after every taken step.
	 * @param moveProgress A value between 0 and 1 that indicates how far the step is. 0 is begin and 1 is end.
	 */
	void update(float moveProgress, boolean updateArms, boolean updateLegs);
}
