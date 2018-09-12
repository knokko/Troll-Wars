package nl.knokko.shaders.battle;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import nl.knokko.main.Game;
import nl.knokko.shaders.ShaderProgram;

public class MatrixEffectShader extends ShaderProgram {
	
	public static final String VERTEX_FILE = "nl/knokko/shaders/battle/matrix.vshad";
	public static final String FRAGMENT_FILE = "nl/knokko/shaders/battle/matrix.fshad";
	
	public static final MatrixEffectShader MATRIX_EFFECT_SHADER = new MatrixEffectShader();
	
	private int locationTransformationMatrix;
	private int locationViewMatrix;
	private int locationColor;

	private MatrixEffectShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		locationTransformationMatrix = getUniformLocation("transformationMatrix");
		locationViewMatrix = getUniformLocation("viewMatrix");
		locationColor = getUniformLocation("color");
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "modelPosition");
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		loadMatrix(locationTransformationMatrix, matrix);
	}
	
	public void loadViewMatrix(Matrix4f view){
		loadMatrix(locationViewMatrix, Matrix4f.mul(Game.getProjectionMatrix(), view, null));
	}
	
	public void loadColor(Vector3f color){
		loadVector(locationColor, color);
	}
}