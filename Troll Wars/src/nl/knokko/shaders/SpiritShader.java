package nl.knokko.shaders;

import nl.knokko.main.Game;
import nl.knokko.util.Maths;
import nl.knokko.view.camera.Camera;

import org.lwjgl.util.vector.Matrix4f;

public class SpiritShader extends ShaderProgram {
	
	public static final String VERTEX_FILE = "nl/knokko/shaders/spirit.vshad";
	public static final String FRAGMENT_FILE = "nl/knokko/shaders/spirit.fshad";
	
	public static final SpiritShader SPIRIT_SHADER = new SpiritShader();
	
	private int locationTransformationMatrix;
	private int locationViewMatrix;
	
	private int locationRandom;
	
	private float randomizer;

	private SpiritShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
		locationViewMatrix = super.getUniformLocation("viewMatrix");
		locationRandom = super.getUniformLocation("rand");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(locationTransformationMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera camera){
		super.loadMatrix(locationViewMatrix, Matrix4f.mul(Game.getProjectionMatrix(), Maths.createViewMatrix(camera), null));
	}
	
	public void loadRandomizer(){
		randomizer += 0.01f;
		if(randomizer > 1f)
			randomizer -= 1f;
		super.loadFloat(locationRandom, randomizer);
	}
}
