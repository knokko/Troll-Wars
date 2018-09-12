package nl.knokko.shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class CastingShader extends ShaderProgram {
	
	public static final String VERTEX_FILE = "nl/knokko/shaders/casting.vshad";
	public static final String FRAGMENT_FILE = "nl/knokko/shaders/casting.fshad";
	
	public static final CastingShader CASTING_SHADER = new CastingShader();
	
	private int locationHandPosition;
	private int locationColor;
	private int locationProjectionMatrix;

	private CastingShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		locationHandPosition = getUniformLocation("handPosition");
		locationColor = getUniformLocation("color");
		locationProjectionMatrix = getUniformLocation("projectionMatrix");
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
	}
	
	public void loadHandPosition(Vector3f handPosition){
		loadVector(locationHandPosition, handPosition);
	}
	
	public void loadColor(Vector3f color){
		loadVector(locationColor, color);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix){
		loadMatrix(locationProjectionMatrix, matrix);
	}
}