package nl.knokko.texture.pattern;

import java.util.Random;

import nl.knokko.util.color.Color;
import nl.knokko.util.resources.Resources;
import nl.knokko.util.resources.Resources.TextureBuilder;

public class PatternWood extends TexturePattern {

protected final Color color;
	
	protected final long seed;
	
	protected final float maxDifference;

	public PatternWood(Color color, float maxDifference, long seed) {
		this.color = color;
		this.maxDifference = maxDifference;
		this.seed = seed;
	}

	@Override
	public void paintBetween(TextureBuilder texture, int minX, int minY, int maxX, int maxY) {
		Random random = new Random(seed);
		for(int x = minX; x <= maxX; x++)
			for(int y = minY; y <= maxY; y++)
				texture.setPixel(x, y, Resources.getDifColor(random, color, maxDifference));
	}
}