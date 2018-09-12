package nl.knokko.view.light;

import org.lwjgl.util.vector.Vector3f;

public class DefaultLight implements Light {
	
	public static final Vector3f POSITION = new Vector3f(0, 1000000, 0);
	public static final Vector3f COLOR = new Vector3f(1, 1, 1);

	@Override
	public Vector3f getPosition() {
		return POSITION;
	}

	@Override
	public Vector3f getColor() {
		return COLOR;
	}

}
