package nl.knokko.texture.pattern;

import nl.knokko.util.color.Color;
import nl.knokko.util.resources.Resources.TextureBuilder;

public class PatternFill extends TexturePattern {
	
	private final Color color;

	public PatternFill(Color color) {
		this.color = color;
	}

	@Override
	public void paintBetween(TextureBuilder texture, int minX, int minY, int maxX, int maxY) {
		for(int x = minX; x <= maxX; x++)
			for(int y = minY; y <= maxY; y++)
				texture.setPixel(x, y, color);
	}

}
