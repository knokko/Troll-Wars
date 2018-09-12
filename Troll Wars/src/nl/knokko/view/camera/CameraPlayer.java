package nl.knokko.view.camera;

import nl.knokko.input.KeyInput;
import nl.knokko.main.Game;
import nl.knokko.render.main.WorldRenderer;
import nl.knokko.util.Maths;
import nl.knokko.util.position.AreaPosition;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class CameraPlayer implements Camera {
	
	private AreaPosition target;
	
	private float yaw;
	private float pitch;
	
	private float distance = 500;

	public CameraPlayer(AreaPosition target) {
		this.target = target;
		this.pitch = 90;
	}

	@Override
	public float getRadPitch() {
		return Maths.toRadians(pitch);
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
		return pitch;
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
		return new Vector3f(target.getX() * 2 - distance * Maths.sinRad(getRadYaw()) * Maths.cosRad(getRadPitch()), (target.getY() + 25) * 2 + distance * Maths.sinRad(getRadPitch()), target.getZ() * 2 + distance * Maths.cosRad(getRadYaw()) * Maths.cosRad(getRadPitch()));
	}

	@Override
	public float getMinX() {
		return target.getX() - extra();
	}

	@Override
	public float getMinZ() {
		return target.getZ() - extra();
	}

	@Override
	public float getMaxX() {
		return target.getX() + extra();
	}

	@Override
	public float getMaxZ() {
		return target.getZ() + extra();
	}
	
	private float extra(){
		return (float) (Math.tan(Math.toRadians(WorldRenderer.FOV / 2)) * Math.sqrt(2) * getPosition().y);
	}

	@Override
	public void update() {
		if(KeyInput.isKeydown(Game.getOptions().keyCameraRight))
			yaw++;
		if(KeyInput.isKeydown(Game.getOptions().keyCameraLeft))
			yaw--;
		if(KeyInput.isKeydown(Game.getOptions().keyCameraUp) && pitch < 90)
			pitch++;
		if(KeyInput.isKeydown(Game.getOptions().keyCameraDown) && pitch > 20)
			pitch--;
		if(KeyInput.isKeydown(Keyboard.KEY_MINUS))
			distance += 2;
		if(KeyInput.isKeydown(Keyboard.KEY_EQUALS) && distance > 20)
			distance -= 2;
		//TODO make sure the camera doesn't move outside the area/cave
	}
}
