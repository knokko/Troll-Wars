package nl.knokko.shaders.battle;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import nl.knokko.main.Game;
import nl.knokko.shaders.ShaderProgram;

public class Battle2dShader extends ShaderProgram {
	
	public static final String VERTEX_FILE = "nl/knokko/shaders/battle/battle2d.vshad";
	public static final String FRAGMENT_FILE = "nl/knokko/shaders/battle/battle2d.fshad";
	
	public static final Battle2dShader BATTLE_2D_SHADER = new Battle2dShader();
	
	private int locationViewMatrix;
	private int locationScale;
	private int locationWorldPosition;

	private Battle2dShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		locationViewMatrix = getUniformLocation("projViewMatrix");
		locationWorldPosition = getUniformLocation("worldPosition");
		locationScale = getUniformLocation("scale");
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "modelPosition");
		bindAttribute(1, "textureCoords");
	}
	
	public void loadViewMatrix(Matrix4f viewMatrix){
		loadMatrix(locationViewMatrix, Matrix4f.mul(Game.getProjectionMatrix(), viewMatrix, null));
	}
	
	public void loadWorldPosition(Vector3f position){
		loadVector(locationWorldPosition, position);
	}
	
	public void laodScale(Vector2f scale){
		loadVector(locationScale, scale);
	}
}