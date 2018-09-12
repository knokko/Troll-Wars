package nl.knokko.view.camera;

import org.lwjgl.util.vector.Vector3f;

public interface Camera {

	float getRadPitch();

	float getRadYaw();

	float getRadRoll();
	
	float getDegPitch();
	
	float getDegYaw();
	
	float getDegRoll();

	Vector3f getPosition();
	
	/**
	 * @return The minimum X-Coordinate that can be seen if the minimum Y-coordinate is 0.
	 */
	float getMinX();
	
	/**
	 * @return The minimum Z-Coordinate that can be seen if the minimum Y-coordinate is 0.
	 */
	float getMinZ();
	
	float getMaxX();
	
	float getMaxZ();
	
	void update();
}
