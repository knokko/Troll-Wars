package nl.knokko.battle.effect.icon;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import nl.knokko.util.resources.Resources;

public class SimpleEffectIcon implements EffectIcon {
	
	protected BufferedImage image;
	protected int textureID;

	public SimpleEffectIcon(String iconName) {
		image = Resources.loadImage("effects/" + iconName);
		textureID = Resources.loadTexture(image, true);
	}

	@Override
	public void draw(Graphics2D graphics, int x, int y, int width, int height) {
		graphics.drawImage(image, x, y, width, height, null);
	}

	@Override
	public int getTextureID() {
		return textureID;
	}
	
	public BufferedImage getImage(){
		return image;
	}
	
	public void setImage(BufferedImage newImage, int newTextureID){
		image = newImage;
		textureID = newTextureID;
	}
	
	public void setImage(BufferedImage newImage){
		setImage(newImage, Resources.loadTexture(newImage, true));
	}
}