package nl.knokko.shaders.tile;

import nl.knokko.main.Game;
import nl.knokko.shaders.ShaderProgram;
import nl.knokko.util.Maths;
import nl.knokko.view.camera.Camera;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class DefaultTileShader extends ShaderProgram {
	
	public static final String VERTEX_FILE = "nl/knokko/shaders/tile/default.vshad";
	public static final String FRAGMENT_FILE = "nl/knokko/shaders/tile/default.fshad";
	
	public static final DefaultTileShader DEFAULT_TILE_SHADER = new DefaultTileShader();
	
	private int locationTilePosition;
	private int locationViewMatrix;
	private int locationShineDamper;
	private int locationReflectivity;

	public DefaultTileShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		locationTilePosition = getUniformLocation("tilePosition");
		locationViewMatrix = getUniformLocation("viewMatrix");
		locationShineDamper = getUniformLocation("shineDamper");
		locationReflectivity = getUniformLocation("reflectivity");
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "modelPosition");
		bindAttribute(1, "textureCoords");
		bindAttribute(2, "reflectedLightDirection");
		bindAttribute(3, "brightness");
	}
	
	public void loadTilePosition(Vector3f position){
		loadVector(locationTilePosition, position);
	}
	
	public void loadViewMatrix(Camera camera){
		loadMatrix(locationViewMatrix, Matrix4f.mul(Game.getProjectionMatrix(), Maths.createViewMatrix(camera), null));
	}
	
	public void loadShine(float shineDamper, float reflectivity){
		loadFloat(locationShineDamper, shineDamper);
		loadFloat(locationReflectivity, reflectivity);
	}
}