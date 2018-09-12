package nl.knokko.texture.pattern;

import java.util.Random;

import nl.knokko.util.color.Color;
import nl.knokko.util.resources.Resources.TextureBuilder;

public class PatternMetal extends TexturePattern {
	
	protected final Color color;
	
	protected final long seed;

	public PatternMetal(Color metalColor, long randomSeed) {
		color = metalColor;
		seed = randomSeed;
	}

	@Override
	public void paintBetween(TextureBuilder texture, int minX, int minY, int maxX, int maxY) {
		Random random = new Random(seed);
		for(int x = minX; x <= maxX; x++){
			for(int y = minY; y <= maxY; y++){
				if(random.nextInt(5) == 0)
					texture.setPixel(x, y, new Color((int) (color.getRed() * 0.8f), (int) (color.getRed() * 0.8f), (int) (color.getBlue() * 0.8f)));
				else
					texture.setPixel(x, y, color);
			}
		}
	}

}
