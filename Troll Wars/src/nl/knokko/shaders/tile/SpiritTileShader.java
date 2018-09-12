package nl.knokko.shaders.tile;

import nl.knokko.main.Game;
import nl.knokko.shaders.ShaderProgram;
import nl.knokko.util.Maths;
import nl.knokko.view.camera.Camera;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class SpiritTileShader extends ShaderProgram {
	
	public static final String VERTEX_FILE = "nl/knokko/shaders/tile/spirit.vshad";
	public static final String FRAGMENT_FILE = "nl/knokko/shaders/tile/spirit.fshad";
	
	public static final SpiritTileShader SPIRIT_TILE_SHADER = new SpiritTileShader();
	
	private int locationTilePosition;
	private int locationViewMatrix;
	
	private int locationRandom;
	
	private float randomizer;

	private SpiritTileShader() {
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
		randomizer += 0.01f;
		if(randomizer > 1f)
			randomizer -= 1f;
		loadFloat(locationRandom, randomizer);
	}
}
