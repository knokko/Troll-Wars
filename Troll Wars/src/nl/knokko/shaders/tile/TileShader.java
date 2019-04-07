package nl.knokko.shaders.tile;

import org.lwjgl.util.vector.Vector3f;

import nl.knokko.shaders.ShaderProgram;

public abstract class TileShader extends ShaderProgram {

	public TileShader(String vertexFile, String fragmentFile) {
		super(vertexFile, fragmentFile);
	}
	
	public abstract void loadTilePosition(Vector3f position);
}