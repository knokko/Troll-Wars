package nl.knokko.texture.factory.modifier;

/**
 * Uniform color modifiers use a single FloatModifier to determine its 'power' for given texture
 * coordinates. The result of the getRed, getGreen and getBlue for given coordinates are the
 * product of the power for the coordinates and the red, green and blue values that were
 * given to the constructor.
 * @author knokko
 *
 */
public class UniformColorModifier implements ColorModifier {
	
	private final FloatModifier modifier;
	
	private final float red;
	private final float green;
	private final float blue;
	
	public UniformColorModifier(FloatModifier floatModifier, float red, float green, float blue) {
		modifier = floatModifier;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	@Override
	public float getRed(int x, int y) {
		return red * modifier.getValue(x, y);
	}

	@Override
	public float getGreen(int x, int y) {
		return green * modifier.getValue(x, y);
	}

	@Override
	public float getBlue(int x, int y) {
		return blue * modifier.getValue(x, y);
	}

	@Override
	public int getMinX() {
		return modifier.getMinX();
	}

	@Override
	public int getMinY() {
		return modifier.getMinY();
	}

	@Override
	public int getMaxX() {
		return modifier.getMaxX();
	}

	@Override
	public int getMaxY() {
		return modifier.getMaxY();
	}
}