package nl.knokko.view.camera;

import java.util.ArrayList;

import nl.knokko.input.KeyInput;
import nl.knokko.input.MouseInput;
import nl.knokko.input.MouseMoveEvent;
import nl.knokko.util.Maths;
import nl.knokko.util.position.SpawnPosition;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class CameraFlying implements Camera {
	
	private float yaw;
	private float pitch;
	
	private Vector3f position;

	public CameraFlying() {
		this(new Vector3f(32, 100, 32), 90, 0);
	}
	
	public CameraFlying(Vector3f position, float pitch, float yaw){
		this.pitch = pitch;
		this.yaw = yaw;
		this.position = position;
	}

	public CameraFlying(SpawnPosition playerSpawn) {
		this(new Vector3f(playerSpawn.getTileX() * 64, playerSpawn.getTileY() * 16, playerSpawn.getTileZ() * 64), 0, 0);
	}

	@Override
	public float getRadPitch() {
		return (float) Math.toRadians(pitch);
	}

	@Override
	public float getRadYaw() {
		return (float) Math.toRadians(yaw);
	}

	@Override
	public float getRadRoll() {
		return 0;
	}
	
	@Override
	public float getDegPitch(){
		return pitch;
	}
	
	@Override
	public float getDegYaw(){
		return yaw;
	}
	
	@Override
	public float getDegRoll(){
		return 0;
	}

	@Override
	public Vector3f getPosition() {
		return position;
	}

	@Override
	public float getMinX() {
		return position.x / 2 - 2000;
	}

	@Override
	public float getMinZ() {
		return position.z / 2 - 2000;
	}

	@Override
	public float getMaxX() {
		return position.x / 2 + 2000;
	}

	@Override
	public float getMaxZ() {
		return position.z / 2 + 2000;
	}
	
	@Override
	public void update(){
		ArrayList<MouseMoveEvent> moves = MouseInput.getMouseMoves();
		for(MouseMoveEvent event : moves){
			yaw += event.getDeltaX();
			while(yaw >= 360)
				yaw -= 360;
			while(yaw < 0)
				yaw += 360;
			pitch -= event.getDeltaY();
			while(pitch >= 360)
				pitch -= 360;
			while(pitch < 0)
				pitch += 360;
		}
		float speed = KeyInput.isKeydown(Keyboard.KEY_T) ? 15 : 5;
		if(KeyInput.isKeydown(Keyboard.KEY_RSHIFT))
			speed = 0.5f;
		Vector3f forward = Maths.getRotationVector(pitch, yaw, 0);
		if(KeyInput.isKeydown(Keyboard.KEY_W)){
			position.x += forward.x * speed;
			position.y += forward.y * speed;
			position.z += forward.z * speed;
		}
		if(KeyInput.isKeydown(Keyboard.KEY_D)){
			position.x -= forward.z * speed;
			position.z += forward.x * speed;
		}
		if(KeyInput.isKeydown(Keyboard.KEY_S)){
			position.x -= forward.x * speed;
			position.y -= forward.y * speed;
			position.z -= forward.z * speed;
		}
		if(KeyInput.isKeydown(Keyboard.KEY_A)){
			position.x += forward.z * speed;
			position.z -= forward.x * speed;
		}
		if(KeyInput.isKeydown(Keyboard.KEY_SPACE))
			position.y += speed;
		if(KeyInput.isKeydown(Keyboard.KEY_LSHIFT))
			position.y -= speed;
	}
}
