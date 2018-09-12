package nl.knokko.shaders.tile;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import nl.knokko.main.Game;
import nl.knokko.shaders.ShaderProgram;
import nl.knokko.util.Maths;
import nl.knokko.view.camera.Camera;

public class LiquidTileShader extends ShaderProgram {
	
	public static final String VERTEX_FILE = "nl/knokko/shaders/tile/liquid.vshad";
	public static final String FRAGMENT_FILE = "nl/knokko/shaders/tile/liquid.fshad";
	
	public static final LiquidTileShader LIQUID_TILE_SHADER = new LiquidTileShader();
	
	private int locationViewMatrix;
	private int locationTilePosition;
	
	private int locationRandom;
	
	private float randomizer;

	private LiquidTileShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		locationTilePosition = getUniformLocation("tilePosition");
		locationViewMatrix = getUniformLocation("viewMatrix");
		locationRandom = getUniformLocation("rand");
	}
	
	@Override
	protected void bindAttributes() {
		bindAttribute(0, "modelPosition");
		bindAttribute(1, "textureCoords");
	}
	
	public void loadTilePosition(Vector3f position){
		loadVector(locationTilePosition, position);
	}
	
	public void loadViewMatrix(Camera camera){
		loadMatrix(locationViewMatrix, Matrix4f.mul(Game.getProjectionMatrix(), Maths.createViewMatrix(camera), null));
	}
	
	public void loadRandomizer(){
		randomizer += 0.001f;
		loadFloat(locationRandom, randomizer);
	}
}