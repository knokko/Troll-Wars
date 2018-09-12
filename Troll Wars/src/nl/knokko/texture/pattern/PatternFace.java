package nl.knokko.texture.pattern;

import java.util.Random;

import nl.knokko.util.Maths;
import nl.knokko.util.color.Color;
import nl.knokko.util.resources.Resources.TextureBuilder;

public class PatternFace extends PatternAverage {
	
	protected Color hairColor;

	public PatternFace(Color color, Color hairColor, float maxDifference, long seed) {
		super(color, maxDifference, seed);
		this.hairColor = hairColor;
	}
	
	@Override
	public void paintBetween(TextureBuilder tb, int minX, int minY, int maxX, int maxY){
		super.paintBetween(tb, minX, minY, maxX, maxY);
		Random random = new Random(seed);
		int deltaX = maxX - minX;
		int deltaY = maxY - minY;
		for(int x = minX; x <= maxX; x++)
			tb.drawVerticalLine(maxY - random.nextInt(deltaY / 5) - deltaY / 6, maxY, x, hairColor);
		int faceX = ((minX + maxX) / 2 + maxX) / 2;
		int eyeY = (minY + maxY) / 2;
		tb.fillOval(faceX - deltaX / 12, eyeY, deltaX / 16, deltaY / 12, Color.WHITE);
		tb.fillOval(faceX + deltaX / 12, eyeY, deltaX / 16, deltaY / 12, Color.WHITE);
		tb.fillOval(faceX - deltaX / 12, eyeY, deltaX / 24, deltaY / 18, Color.GREEN);
		tb.fillOval(faceX + deltaX / 12, eyeY, deltaX / 24, deltaY / 18, Color.GREEN);
		tb.fillOval(faceX - deltaX / 12, eyeY, deltaX / 48, deltaY / 36, Color.BLACK);
		tb.fillOval(faceX + deltaX / 12, eyeY, deltaX / 48, deltaY / 36, Color.BLACK);
		int ms = deltaY / 24;
		for(int x = faceX - deltaX / 8; x <= faceX + deltaX / 8; x++){
			int y = (int) (minY + deltaY / 4 - Maths.cos((x - faceX) * 90 / (deltaX / 8)) * deltaY / 16);
			boolean flag = x == faceX - deltaX / 8 || x == faceX + deltaX / 8;
			tb.setPixel(x, y - ms, Color.RED);
			tb.drawVerticalLine(y - ms + 1, y + ms - 1, x, flag ? Color.RED : Color.BLACK);
			tb.setPixel(x, y + ms, Color.RED);
		}
	}
}
