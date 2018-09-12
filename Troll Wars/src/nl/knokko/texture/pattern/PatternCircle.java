package nl.knokko.texture.pattern;

import nl.knokko.util.color.Color;
import nl.knokko.util.resources.Resources.TextureBuilder;

public class PatternCircle extends TexturePattern {
	
	private final TexturePattern pattern;
	
	private final Color color;
	
	private final double radius;

	public PatternCircle(TexturePattern pattern, Color outOfCircleColor, double radius) {
		this.pattern = pattern;
		this.color = outOfCircleColor;
		this.radius = radius;
	}
	
	public PatternCircle(TexturePattern pattern, Color outOfCircleColor){
		this(pattern, outOfCircleColor, Double.NaN);
	}

	@Override
	public void paintBetween(TextureBuilder texture, int minX, int minY, int maxX, int maxY) {
		pattern.paintBetween(texture, minX, minY, maxX, maxY);
		double midX = (maxX + minX) / 2.0;
		double midY = (maxY + minY) / 2.0;
		double r = radius == radius ? radius : Math.min((maxX - minX) / 2, (maxY - minY) / 2);
		for(int x = minX; x <= maxX; x++){
			for(int y = minY; y <= maxY; y++){
				double dx = x - midX;
				double dy = y - midY;
				if(Math.sqrt(dx * dx + dy * dy) > r)
					texture.setPixel(x, y, color);
			}
		}
	}
}
