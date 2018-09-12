package nl.knokko.model.body.wing;

public interface BirdWing {
	
	float wingPartMaxLength();
	
	float wingPartHeight();
	
	float wingPartDepth();
	
	/**
	 * @return a float[] with a value between 0 and 1 for every part of this wing
	 */
	float[] wingPartLengths();
}
