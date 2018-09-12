package nl.knokko.texture.pattern;

import nl.knokko.util.color.Color;
import nl.knokko.util.resources.Resources.TextureBuilder;

public class PatternBirdFace extends PatternAverage {

	public PatternBirdFace(Color color, float maxDifference, long seed) {
		super(color, maxDifference, seed);
	}
	
	@Override
	public void paintBetween(TextureBuilder tb, int minX, int minY, int maxX, int maxY){
		super.paintBetween(tb, minX, minY, maxX, maxY);
		int deltaX = maxX - minX;
		int deltaY = maxY - minY;
		int faceX = ((minX + maxX) / 2 + maxX) / 2;
		int eyeY = maxY - deltaY / 4;
		tb.fillOval(faceX - deltaX / 12, eyeY, deltaX / 16, deltaY / 12, Color.WHITE);
		tb.fillOval(faceX + deltaX / 12, eyeY, deltaX / 16, deltaY / 12, Color.WHITE);
		tb.fillOval(faceX - deltaX / 12, eyeY, deltaX / 24, deltaY / 18, Color.GREEN);
		tb.fillOval(faceX + deltaX / 12, eyeY, deltaX / 24, deltaY / 18, Color.GREEN);
		tb.fillOval(faceX - deltaX / 12, eyeY, deltaX / 48, deltaY / 36, Color.BLACK);
		tb.fillOval(faceX + deltaX / 12, eyeY, deltaX / 48, deltaY / 36, Color.BLACK);
	}
}