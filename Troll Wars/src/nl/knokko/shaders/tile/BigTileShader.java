package nl.knokko.shaders.tile;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import nl.knokko.main.Game;
import nl.knokko.util.Maths;
import nl.knokko.view.camera.Camera;

public class BigTileShader extends TileShader {
	
	public static final BigTileShader BIG_TILE_SHADER = new BigTileShader();
	
	private static final String VERTEX_FILE = "nl/knokko/shaders/tile/big.vshad";
	private static final String FRAGMENT_FILE = "nl/knokko/shaders/tile/big.fshad";
	
	private int locationTilePosition;
	private int locationRealTilePosition;
	private int locationViewMatrix;
	private int locationShineDamper;
	private int locationReflectivity;

	private BigTileShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		locationTilePosition = getUniformLocation("tilePosition");
		locationRealTilePosition = getUniformLocation("realTilePosition");
		locationViewMatrix = getUniformLocation("viewMatrix");
		locationShineDamper = getUniformLocation("shineDamper");
		locationReflectivity = getUniformLocation("reflectivity");
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "modelPosition");
		bindAttribute(1, "reflectedLightDirection");
		bindAttribute(2, "brightness");
	}
	
	@Override
	public void loadTilePosition(Vector3f position){
		loadVector(locationTilePosition, position);
	}
	
	public void loadRealTilePosition(Vector3f position) {
		loadVector(locationRealTilePosition, position);
	}
	
	public void loadViewMatrix(Camera camera){
		loadMatrix(locationViewMatrix, Matrix4f.mul(Game.getProjectionMatrix(), Maths.createOriginViewMatrix(camera), null));
	}
	
	public void loadShine(float shineDamper, float reflectivity){
		loadFloat(locationShineDamper, shineDamper);
		loadFloat(locationReflectivity, reflectivity);
	}
}