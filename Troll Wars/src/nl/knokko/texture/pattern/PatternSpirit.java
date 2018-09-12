package nl.knokko.texture.pattern;

import java.util.Random;

import nl.knokko.util.color.Color;
import nl.knokko.util.color.ColorAlpha;
import nl.knokko.util.resources.Resources.TextureBuilder;

public class PatternSpirit extends TexturePattern {
	
	protected final Color color;
	protected final float maxDifference;
	protected final long seed;

	public PatternSpirit(Color color, float maxDifference, long seed) {
		this.color = color;
		this.maxDifference = maxDifference;
		this.seed = seed;
	}

	@Override
	public void paintBetween(TextureBuilder texture, int minX, int minY, int maxX, int maxY) {
		Random random = new Random(seed);
		for(int x = minX; x <= maxX; x++){
			for(int y = minY; y <= maxY; y++){
				texture.setPixel(x, y, new ColorAlpha(dif(random, color.getRedI()), dif(random, color.getGreenI()), dif(random, color.getBlueI()), dif(random, color.getAlphaI())));
			}
		}
	}
	
	private int dif(Random random, int original){
		return Math.min(Math.max((int) (original * (1 - maxDifference) + random.nextFloat() * maxDifference * 2), 0), 255);
	}
}