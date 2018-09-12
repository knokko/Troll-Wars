package nl.knokko.texture;

import java.awt.image.BufferedImage;

import nl.knokko.util.resources.Resources;

public class ImageTexture extends Texture {
	
	private final BufferedImage image;

	public ImageTexture(BufferedImage image) {
		this(image, true);
	}
	
	public ImageTexture(BufferedImage image, boolean createTexture){
		super(createTexture ? Resources.loadTexture(image, true) : -1);
		this.image = image;
	}
	
	public BufferedImage getImage(){
		return image;
	}
}