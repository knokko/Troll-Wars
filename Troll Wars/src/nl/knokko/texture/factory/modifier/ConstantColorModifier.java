package nl.knokko.texture.factory.modifier;

public class ConstantColorModifier implements ColorModifier {
	
	private final float red;
	private final float green;
	private final float blue;
	private final float weight;
	
	private final int minX;
	private final int minY;
	private final int maxX;
	private final int maxY;
	
	public ConstantColorModifier(float weight, float red, float green, float blue, int minX, int minY, int maxX, int maxY) {
		this.weight = weight;
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
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
		return weight;
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