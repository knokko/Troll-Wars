package nl.knokko.texture.factory.modifier;

import nl.knokko.util.Maths;

public class CircleFunctionFloatMod implements FloatModifier {
	
	private final int centerX;
	private final int centerY;
	
	private final int minX;
	private final int minY;
	private final int maxX;
	private final int maxY;
	
	private final float[] radiuses;
	private final ValueFunction valueFunction;
	
	public CircleFunctionFloatMod(int centerX, int centerY, RadiusFunction radiusFunction, ValueFunction valueFunction) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.valueFunction = valueFunction;
		this.radiuses = new float[360];
		
		float maxValue = 0;
		for (int index = 0; index < 360; index++) {
			float value = radiusFunction.getValue(index);
			this.radiuses[index] = value;
			if (value > maxValue) {
				maxValue = value;
			}
		}
		
		int radius = Maths.ceil(maxValue);
		this.minX = centerX - radius;
		this.minY = centerY - radius;
		this.maxX = centerX + radius;
		this.maxY = centerY + radius;
	}

	@Override
	public float getValue(int x, int y) {
		// Use Math.max and Math.min to prevent array index issues when floating point inaccuracy occurs
		float radius = radiuses[Math.max(180 + Math.min((int) (180 * Math.atan2(y - centerY, x - centerX) / Math.PI), 179), 0)];
		float distance = Maths.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
		return valueFunction.getValue(radius, distance);
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
	
	public static interface RadiusFunction {
		
		float getValue(float angle);
	}
	
	public static interface ValueFunction {
		
		float getValue(float radius, float distance);
	}
}