package nl.knokko.view.camera;

import nl.knokko.input.KeyInput;
import nl.knokko.main.Game;
import nl.knokko.util.Maths;

import org.lwjgl.util.vector.Vector3f;

public class CameraBattle implements Camera {
	
	private float yaw;
	
	private float y;

	public CameraBattle() {
		yaw = 0f;
		y = 100f;
	}

	@Override
	public float getRadPitch() {
		return 0;
	}

	@Override
	public float getRadYaw() {
		return Maths.toRadians(yaw);
	}

	@Override
	public float getRadRoll() {
		return 0;
	}

	@Override
	public float getDegPitch() {
		return 0;
	}

	@Override
	public float getDegYaw() {
		return yaw;
	}

	@Override
	public float getDegRoll() {
		return 0;
	}

	@Override
	public Vector3f getPosition() {
		Vector3f vec = Maths.getRotationVector(0, yaw, 0);
		vec.scale(-400);
		vec.y = y;
		return vec;
	}

	@Override
	public float getMinX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getMinZ() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getMaxX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getMaxZ() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update() {
		if(KeyInput.isKeydown(Game.getOptions().keyEast)){
			yaw++;
			if(yaw >= 360)
				yaw -= 360;
		}
		if(KeyInput.isKeydown(Game.getOptions().keyWest)){
			yaw--;
			if(yaw < 0)
				yaw += 360;
		}
		if(KeyInput.isKeydown(Game.getOptions().keyNorth))
			y++;
		if(KeyInput.isKeydown(Game.getOptions().keySouth))
			y--;
	}
}
