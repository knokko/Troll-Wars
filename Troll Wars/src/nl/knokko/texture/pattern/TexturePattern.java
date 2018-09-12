package nl.knokko.texture.pattern;

import nl.knokko.texture.area.TextureArea;
import nl.knokko.util.resources.Resources.TextureBuilder;

public abstract class TexturePattern {
	
	public void paint(TextureBuilder texture, int x, int y, int width, int height){
		paintBetween(texture, x, y, x + width, y + height);
	}
	
	public void paint(TextureBuilder texture, TextureArea area){
		paintBetween(texture, area.getMinX(), area.getMinY(), area.getMaxX(), area.getMaxY());
	}
	
	public abstract void paintBetween(TextureBuilder texture, int minX, int minY, int maxX, int maxY);
}
