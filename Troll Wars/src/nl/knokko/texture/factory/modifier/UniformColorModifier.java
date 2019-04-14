package nl.knokko.texture.factory.modifier;

/**
 * Uniform color modifiers use a single FloatModifier to determine its weight for given texture
 * coordinates. The result of the getRed, getGreen and getBlue for given coordinates are simply
 * the values that are passed via the constructor. The getWeight will return the values returned
 * by the weight parameter of the constructor.
 * @author knokko
 *
 */
public class UniformColorModifier implements ColorModifier {
	
	private final FloatModifier weight;
	
	private final float red;
	private final float green;
	private final float blue;
	
	public UniformColorModifier(FloatModifier weight, float red, float green, float blue) {
		this.weight = weight;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	@Override
	public float getRed(int x, int y) {
		return red;
	}

	@Override
	public float getGreen(int x, int y) {
		return green;
	}

	@Override
	public float getBlue(int x, int y) {
		return blue;
	}
	
	@Override
	public float getWeight(int x, int y) {
		return weight.getValue(x, y);
	}

	@Override
	public int getMinX() {
		return weight.getMinX();
	}

	@Override
	public int getMinY() {
		return weight.getMinY();
	}

	@Override
	public int getMaxX() {
		return weight.getMaxX();
	}

	@Override
	public int getMaxY() {
		return weight.getMaxY();
	}
}