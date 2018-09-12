package nl.knokko.model.body.claw;

public interface BirdClaw {
	
	float legLength();
	
	float legRadius();
	
	/**
	 * @return The angle between the most left and most right nail, in degrees.
	 */
	float nailSpreadAngle();
	
	int nailAmount();
	
	float nailLength();
	
	float nailRadius();
}
