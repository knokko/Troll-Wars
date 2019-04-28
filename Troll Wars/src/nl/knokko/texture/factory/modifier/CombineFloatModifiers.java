package nl.knokko.texture.factory.modifier;

public abstract class CombineFloatModifiers implements FloatModifier {
	
	public static int findMinX(int textureWidth, int textureHeight, FloatModifier...modifiers) {
		if (textureWidth != -1 || textureHeight != -1) return 0;
		int minX = modifiers[0].getMinX();
		for (int index = 1; index < modifiers.length; index++) {
			int localMinX = modifiers[index].getMinX();
			if (localMinX < minX) {
				minX = localMinX;
			}
		}
		return minX;
	}
	
	public static int findMinY(int textureWidth, int textureHeight, FloatModifier...modifiers) {
		if (textureWidth != -1 || textureHeight != -1) return 0;
		int minY = modifiers[0].getMinY();
		for (int index = 1; index < modifiers.length; index++) {
			int localMinY = modifiers[index].getMinY();
			if (localMinY < minY) {
				minY = localMinY;
			}
		}
		return minY;
	}
	
	public static int findMaxX(int textureWidth, int textureHeight, FloatModifier...modifiers) {
		if (textureWidth != -1 || textureHeight != -1) return textureWidth - 1;
		int maxX = modifiers[0].getMaxX();
		for (int index = 1; index < modifiers.length; index++) {
			int localMaxX = modifiers[index].getMaxX();
			if (localMaxX > maxX) {
				maxX = localMaxX;
			}
		}
		return maxX;
	}
	
	public static int findMaxY(int textureWidth, int textureHeight, FloatModifier...modifiers) {
		if (textureWidth != -1 || textureHeight != -1) return textureHeight - 1;
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
		this(findMinX(textureWidth, textureHeight, modifiers), findMinY(textureWidth, textureHeight, modifiers), 
				findMaxX(textureWidth, textureHeight, modifiers), 
				findMaxY(textureWidth, textureHeight, modifiers), textureWidth, textureHeight, modifiers);
	}

	@Override
	public float getValue(int x, int y) {
		float value = valueAt(modifiers[0], x, y);
		for (int index = 1; index < modifiers.length; index++) {
			value = combine(value, valueAt(modifiers[index], x, y));
		}
		return value;
	}
	
	private boolean doWrapping() {
		return width != -1 && height != -1;
	}
	
	private final float valueAt(FloatModifier mod, int x, int y) {
		float result = tryValueAt(mod, x, y);
		
		if (!doWrapping()) return result;
		
		result = maybeCombine(result, tryValueAt(mod, x + width, y));
		result = maybeCombine(result, tryValueAt(mod, x - width, y));
		
		result = maybeCombine(result, tryValueAt(mod, x, y + height));
		result = maybeCombine(result, tryValueAt(mod, x + width, y + height));
		result = maybeCombine(result, tryValueAt(mod, x - width, y + height));
		
		result = maybeCombine(result, tryValueAt(mod, x, y - height));
		result = maybeCombine(result, tryValueAt(mod, x + width, y - height));
		result = maybeCombine(result, tryValueAt(mod, x - width, y - height));
		
		return result;
	}
	
	private final float maybeCombine(float f1, float f2) {
		if (f2 == 0) {
			return f1;
		} else {
			return combine(f1, f2);
		}
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