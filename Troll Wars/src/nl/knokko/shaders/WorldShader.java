package nl.knokko.shaders;

import nl.knokko.main.Game;
import nl.knokko.util.Maths;
import nl.knokko.view.camera.Camera;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

public class WorldShader extends ShaderProgram {
	
	public static final String VERTEX_FILE = "nl/knokko/shaders/default.vshad";
	public static final String FRAGMENT_FILE = "nl/knokko/shaders/default.fshad";
	
	public static final WorldShader WORLD_SHADER = new WorldShader();
	
	private int locationTransformationMatrix;
	private int locationViewMatrix;
	private int locationReflectivity;
	private int locationShineDamper;
	private int locationEffectColor;

	private WorldShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
		bindAttribute(1, "textureCoords");
		bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		locationTransformationMatrix = getUniformLocation("transformationMatrix");
		locationViewMatrix = getUniformLocation("viewMatrix");
		locationReflectivity = getUniformLocation("reflectivity");
		locationShineDamper = getUniformLocation("shineDamper");
		locationEffectColor = getUniformLocation("effectColor");
	}
	
	public void loadShine(float damper, float reflectivity){
		loadFloat(locationShineDamper, damper);
		loadFloat(locationReflectivity, reflectivity);
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		loadMatrix(locationTransformationMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera camera){
		loadMatrix(locationViewMatrix, Matrix4f.mul(Game.getProjectionMatrix(), Maths.createViewMatrix(camera), null));
	}
	
	public void loadEffectColor(Vector4f color){
		loadVector(locationEffectColor, color);
	}
}
