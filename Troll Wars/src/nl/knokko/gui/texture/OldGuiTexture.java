package nl.knokko.gui.texture;

import nl.knokko.texture.Texture;

import org.lwjgl.util.vector.Vector2f;

public class OldGuiTexture {
	
	private final Vector2f centre;
	private final Vector2f size;
	
	private final Texture texture;

	public OldGuiTexture(Vector2f centre, Vector2f size, Texture texture) {
		this.texture = texture;
		this.centre = centre;
		this.size = size;
	}
	
	public Vector2f getPosition(){
		return centre;
	}
	
	public Vector2f getScale(){
		return size;
	}
	
	public Texture[] getTextures(){
		return new Texture[]{texture};
	}
}
