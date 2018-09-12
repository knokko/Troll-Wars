package nl.knokko.shaders.battle;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import nl.knokko.main.Game;
import nl.knokko.shaders.ShaderProgram;
import nl.knokko.util.color.Color;

public class ResourceShader extends ShaderProgram {
	
	public static final String VERTEX_FILE = "nl/knokko/shaders/battle/resource.vshad";
	public static final String FRAGMENT_FILE = "nl/knokko/shaders/battle/resource.fshad";
	
	public static final ResourceShader RESOURCE_SHADER = new ResourceShader();
	
	private static final Vector3f HEALTH_FULL = Color.HEALTH_FULL.toVector();
	private static final Vector3f HEALTH_EMPTY = Color.HEALTH_EMPTY.toVector();
	private static final Vector3f MANA_FULL = Color.MANA_FULL.toVector();
	private static final Vector3f MANA_EMPTY = Color.MANA_EMPTY.toVector();
	private static final Vector3f FOCUS_FULL = Color.FOCUS_FULL.toVector();
	private static final Vector3f FOCUS_EMPTY = Color.FOCUS_EMPTY.toVector();
	private static final Vector3f ENERGY_FULL = Color.ENERGY_FULL.toVector();
	private static final Vector3f ENERGY_EMPTY = Color.ENERGY_EMPTY.toVector();
	private static final Vector3f RAGE_FULL = Color.RAGE_FULL.toVector();
	private static final Vector3f RAGE_EMPTY = Color.RAGE_EMPTY.toVector();
	
	private int locationScale;
	private int locationWorldPosition;
	private int locationViewMatrix;
	
	private int locationProgress;
	private int locationFullColor;
	private int locationEmptyColor;

	private ResourceShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		locationScale = getUniformLocation("scale");
		locationWorldPosition = getUniformLocation("worldPosition");
		locationViewMatrix = getUniformLocation("projViewMatrix");
		locationProgress = getUniformLocation("progress");
		locationFullColor = getUniformLocation("fullColor");
		locationEmptyColor = getUniformLocation("emptyColor");
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "modelPosition");
	}
	
	public void loadScale(Vector2f scale){
		loadVector(locationScale, scale);
		//System.out.println("ResourceShader.loadScale() scale is " + scale);
	}
	
	public void loadWorldPosition(Vector3f position){
		loadVector(locationWorldPosition, position);
		//System.out.println("ResourceShader.loadWorldPosition() position is " + position);
	}
	
	public void loadViewMatrix(Matrix4f viewMatrix){
		loadMatrix(locationViewMatrix, Matrix4f.mul(Game.getProjectionMatrix(), viewMatrix, null));
		//System.out.println();
		//System.out.println("ResourceShader.loadViewMatrix()");
	}
	
	public void loadProgress(float progress){
		loadFloat(locationProgress, progress);
		//System.out.println("ResourceShader.loadProgress() progress is " + progress);
	}
	
	public void setHealthColor(){
		loadVector(locationFullColor, HEALTH_FULL);
		loadVector(locationEmptyColor, HEALTH_EMPTY);
		//System.out.println("ResourceShader.setHealthColor() full is " + HEALTH_FULL);
	}
	
	public void setManaColor(){
		loadVector(locationFullColor, MANA_FULL);
		loadVector(locationEmptyColor, MANA_EMPTY);
	}
	
	public void setFocusColor(){
		loadVector(locationFullColor, FOCUS_FULL);
		loadVector(locationEmptyColor, FOCUS_EMPTY);
	}
	
	public void setEnergyColor(){
		loadVector(locationFullColor, ENERGY_FULL);
		loadVector(locationEmptyColor, ENERGY_EMPTY);
	}
	
	public void setRageColor(){
		loadVector(locationFullColor, RAGE_FULL);
		loadVector(locationEmptyColor, RAGE_EMPTY);
	}
}