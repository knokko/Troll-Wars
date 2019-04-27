package nl.knokko.texture.factory.modifier;

public abstract class CombineFloatModifiers implements FloatModifier {
	
	public static int findMinX(FloatModifier...modifiers) {
		int minX = modifiers[0].getMinX();
		for (int index = 1; index < modifiers.length; index++) {
			int localMinX = modifiers[index].getMinX();
			if (localMinX < minX) {
				minX = localMinX;
			}
		}
		return minX;
	}
	
	public static int findMinY(FloatModifier...modifiers) {
		int minY = modifiers[0].getMinY();
		for (int index = 1; index < modifiers.length; index++) {
			int localMinY = modifiers[index].getMinY();
			if (localMinY < minY) {
				minY = localMinY;
			}
		}
		return minY;
	}
	
	public static int findMaxX(FloatModifier...modifiers) {
		int maxX = modifiers[0].getMaxX();
		for (int index = 1; index < modifiers.length; index++) {
			int localMaxX = modifiers[index].getMaxX();
			if (localMaxX > maxX) {
				maxX = localMaxX;
			}
		}
		return maxX;
	}
	
	public static int findMaxY(FloatModifier...modifiers) {
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
	
	private final int width;
	private final int height;
	
	public CombineFloatModifiers(int minX, int minY, int maxX, int maxY, int textureWidth, int textureHeight, FloatModifier...modifiers) {
		this.modifiers = modifiers;
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
		
		this.width = textureWidth;
		this.height = textureHeight;
	}
	
	public CombineFloatModifiers(int textureWidth, int textureHeight, FloatModifier...modifiers) {
		this(findMinX(modifiers), findMinY(modifiers), findMaxX(modifiers), findMaxY(modifiers), textureWidth, textureHeight, modifiers);
	}

	@Override
	public float getValue(int x, int y) {
		float value = valueAt(modifiers[0], x, y);
		for (int index = 1; index < modifiers.length; index++) {
			value = combine(value, valueAt(modifiers[index], x, y));
		}
		return value;
	}
	
	private final float valueAt(FloatModifier mod, int x, int y) {
		float result = tryValueAt(mod, x, y);
		
		if (width == -1 || height == -1) return result;
		
		result = combine(result, tryValueAt(mod, x + width, y));
		result = combine(result, tryValueAt(mod, x - width, y));
		
		result = combine(result, tryValueAt(mod, x, y + height));
		result = combine(result, tryValueAt(mod, x + width, y + height));
		result = combine(result, tryValueAt(mod, x - width, y + height));
		
		result = combine(result, tryValueAt(mod, x, y - height));
		result = combine(result, tryValueAt(mod, x + width, y - height));
		result = combine(result, tryValueAt(mod, x - width, y - height));
		
		return result;
	}
	
	private final float tryValueAt(FloatModifier mod, int x, int y) {
		if (x >= mod.getMinX() && x <= mod.getMaxX() && y >= mod.getMinY() && y <= mod.getMaxY()) {
			return mod.getValue(x, y);
		} else {
			return 0;
		}
	}
	
	protected abstract float combine(float f1, float f2);

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