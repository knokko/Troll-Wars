package nl.knokko.texture.pattern.equipment;

import nl.knokko.texture.pattern.TexturePattern;
import nl.knokko.util.color.Color;
import nl.knokko.util.resources.Resources.TextureBuilder;

public class PatternElbow extends TexturePattern {
	
	protected final TexturePattern armorPattern;

	public PatternElbow(TexturePattern armorPattern) {
		this.armorPattern = armorPattern;
	}

	@Override
	public void paintBetween(TextureBuilder texture, int minX, int minY, int maxX, int maxY) {
		armorPattern.paintBetween(texture, minX, minY, maxX, maxY);
		for(int x = minX; x <= maxX; x++){
			for(int y = minY; y <= maxY; y++){
				Color pixel = texture.getPixel(x, y);
				texture.setPixel(x, y, new Color(pixel.getRedI() / 2, pixel.getGreenI() / 2, pixel.getBlueI() / 2));
			}
		}
	}
}