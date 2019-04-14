package nl.knokko.texture.factory.modifier;

public class MaxFloatModifiers implements FloatModifier {
	
	private static int findMinX(FloatModifier...modifiers) {
		int minX = modifiers[0].getMinX();
		for (int index = 1; index < modifiers.length; index++) {
			int localMinX = modifiers[index].getMinX();
			if (localMinX < minX) {
				minX = localMinX;
			}
		}
		return minX;
	}
	
	private static int findMinY(FloatModifier...modifiers) {
		int minY = modifiers[0].getMinY();
		for (int index = 1; index < modifiers.length; index++) {
			int localMinY = modifiers[index].getMinY();
			if (localMinY < minY) {
				minY = localMinY;
			}
		}
		return minY;
	}
	
	private static int findMaxX(FloatModifier...modifiers) {
		int maxX = modifiers[0].getMaxX();
		for (int index = 1; index < modifiers.length; index++) {
			int localMaxX = modifiers[index].getMaxX();
			if (localMaxX > maxX) {
				maxX = localMaxX;
			}
		}
		return maxX;
	}
	
	private static int findMaxY(FloatModifier...modifiers) {
		int maxY = modifiers[0].getMaxY();
		for (int index = 1; index < modifiers.length; index++) {
			int localMaxY = modifiers[index].getMaxY();
			if (localMaxY > maxY) {
				maxY = localMaxY;
			}
		}
		return maxY;
	}
	
	private final FloatModifier[] modifiers;
	
	private final int minX;
	private final int minY;
	private final int maxX;
	private final int maxY;
	
	public MaxFloatModifiers(int minX, int minY, int maxX, int maxY, FloatModifier...modifiers) {
		this.modifiers = modifiers;
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}
	
	public MaxFloatModifiers(FloatModifier...modifiers) {
		this(findMinX(modifiers), findMinY(modifiers), findMaxX(modifiers), findMaxY(modifiers), modifiers);
	}

	@Override
	public float getValue(int x, int y) {
		float value = modifiers[0].getValue(x, y);
		for (int index = 1; index < modifiers.length; index++) {
			float nextValue = modifiers[index].getValue(x, y);
			if (nextValue > value) {
				value = nextValue;
			}
		}
		return value;
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