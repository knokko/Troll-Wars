package nl.knokko.shaders.battle;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import nl.knokko.main.Game;
import nl.knokko.shaders.ShaderProgram;

public class SimpleEffectShader extends ShaderProgram {
	
	public static final String VERTEX_FILE = "nl/knokko/shaders/battle/simple.vshad";
	public static final String FRAGMENT_FILE = "nl/knokko/shaders/battle/simple.fshad";
	
	public static final SimpleEffectShader SIMPLE_EFFECT_SHADER = new SimpleEffectShader();
	
	private int locationPosition;
	private int locationViewMatrix;
	private int locationColor;

	private SimpleEffectShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		locationPosition = getUniformLocation("position");
		locationViewMatrix = getUniformLocation("viewMatrix");
		locationColor = getUniformLocation("color");
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "modelPosition");
	}
	
	public void loadPosition(Vector3f position){
		loadVector(locationPosition, position);
	}
	
	public void loadViewMatrix(Matrix4f view){
		loadMatrix(locationViewMatrix, Matrix4f.mul(Game.getProjectionMatrix(), view, null));
	}
	
	public void loadColor(Vector3f color){
		loadVector(locationColor, color);
	}
}