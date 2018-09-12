package nl.knokko.shaders;

import nl.knokko.main.Game;
import nl.knokko.util.Maths;
import nl.knokko.view.camera.Camera;

import org.lwjgl.util.vector.Matrix4f;

public class LiquidShader extends ShaderProgram {
	
	public static final String VERTEX_FILE = "nl/knokko/shaders/liquid.vshad";
	public static final String FRAGMENT_FILE = "nl/knokko/shaders/liquid.fshad";
	
	public static final LiquidShader LIQUID_SHADER = new LiquidShader();
	
	private int locationTransformationMatrix;
	private int locationViewMatrix;
	
	private int locationRandom;
	
	private float randomizer;

	private LiquidShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		locationTransformationMatrix = getUniformLocation("transformationMatrix");
		locationViewMatrix = getUniformLocation("viewMatrix");
		locationRandom = getUniformLocation("rand");
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
		bindAttribute(1, "textureCoords");
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		loadMatrix(locationTransformationMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera camera){
		loadMatrix(locationViewMatrix, Matrix4f.mul(Game.getProjectionMatrix(), Maths.createViewMatrix(camera), null));
	}
	
	public void loadRandomizer(){
		randomizer += 0.001f;
		loadFloat(locationRandom, randomizer);
	}
}
