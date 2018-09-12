package nl.knokko.texture.pattern.equipment;

import nl.knokko.texture.pattern.TexturePattern;
import nl.knokko.util.color.Color;
import nl.knokko.util.resources.Resources.TextureBuilder;

public class PatternHelmet extends TexturePattern {
	
	protected final TexturePattern pattern;

	public PatternHelmet(TexturePattern underPattern) {
		pattern = underPattern;
	}

	@Override
	public void paintBetween(TextureBuilder texture, int minX, int minY, int maxX, int maxY) {
		pattern.paintBetween(texture, minX, minY, maxX, maxY);
		int deltaX = maxX - minX;
		int deltaY = maxY - minY;
		int faceX = ((minX + maxX) / 2 + maxX) / 2;
		int eyeY = maxY - deltaY / 2;
		texture.fillOval(faceX, eyeY, deltaX / 12 + deltaX / 16, deltaY / 12, Color.TRANSPARENT);
	}
}
