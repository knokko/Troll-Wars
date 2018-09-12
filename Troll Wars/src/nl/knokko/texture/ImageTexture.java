package nl.knokko.texture;

import java.awt.image.BufferedImage;

import nl.knokko.gui.texture.GuiTexture;
import nl.knokko.main.Game;
import nl.knokko.util.resources.Resources;

public class ImageTexture extends Texture {
	
	private final BufferedImage image;
	
	private GuiTexture guiTexture;

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
	
	public GuiTexture getGuiTexture(){
		if(guiTexture == null)
			guiTexture = Game.getWindow().getTextureLoader().loadTexture(image);
		return guiTexture;
	}
}