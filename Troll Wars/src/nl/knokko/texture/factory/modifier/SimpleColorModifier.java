package nl.knokko.texture.factory.modifier;

import nl.knokko.util.Maths;

/**
 * Simple color modifiers are color modifiers that have a separate FloatModifier for red, green and blue.
 * The getRed, getGreen and getBlue simply returns the getValue of the corresponding float modifier.
 * @author knokko
 *
 */
public class SimpleColorModifier implements ColorModifier {
	
	private final int minX;
	private final int minY;
	private final int maxX;
	private final int maxY;
	
	private final FloatModifier red;
	private final FloatModifier green;
	private final FloatModifier blue;
	
	public SimpleColorModifier(FloatModifier red, FloatModifier green, FloatModifier blue) {
		this.minX = Maths.min(red.getMinX(), green.getMinX(), blue.getMinX());
		this.minY = Maths.min(red.getMinY(), green.getMinY(), blue.getMinY());
		this.maxX = Maths.max(red.getMaxX(), green.getMaxX(), blue.getMaxX());
		this.maxY = Maths.max(red.getMaxY(), green.getMaxY(), blue.getMaxY());
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	@Override
	public float getRed(int x, int y) {
		return red.getValue(x, y);
	}

	@Override
	public float getGreen(int x, int y) {
		return green.getValue(x, y);
	}

	@Override
	public float getBlue(int x, int y) {
		return blue.getValue(x, y);
	}

	@Override
	public int getMinX() {
		return minX;
	}

	@Override
	public int getMinY() {
		return minY;
	}

	@Override
	public int getMaxX() {
		return maxX;
	}

	@Override
	public int getMaxY() {
		return maxY;
	}
}