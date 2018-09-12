package nl.knokko.texture.pattern.equipment;

import nl.knokko.texture.pattern.TexturePattern;
import nl.knokko.util.resources.Resources.TextureBuilder;

public class PatternUnderLeg extends TexturePattern {
	
	protected final TexturePattern armorPattern;

	public PatternUnderLeg(TexturePattern armorPattern) {
		this.armorPattern = armorPattern;
	}

	@Override
	public void paintBetween(TextureBuilder texture, int minX, int minY, int maxX, int maxY) {
		armorPattern.paintBetween(texture, minX, minY, maxX, maxY);
	}
}