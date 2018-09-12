package nl.knokko.texture;

import nl.knokko.main.GameScreen;

public class SizedTexture extends Texture {
	
	private final int width;
	private final int height;

	public SizedTexture(int textureID, int width, int height) {
		super(textureID);
		this.width = width;
		this.height = height;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public float getRelativeWidth(){
		return (float) width / GameScreen.getWidth();
	}
	
	public float getRelativeHeight(){
		return (float) height / GameScreen.getHeight();
	}
}
