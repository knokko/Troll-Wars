package nl.knokko.util.color;

import java.util.Random;

public class ColorHelper {
	
	private static Random random = new Random();
	
	public static Color r(Color original, float maxDifference){
		return new Color(r(original.getRedI(), maxDifference), r(original.getGreenI(), maxDifference), r(original.getBlueI(), maxDifference));
	}
	
	public static int r(int original, float maxDifference){
		return Math.min(255, Math.max(0, (int) (original * (1 - maxDifference + 2 * maxDifference * random.nextFloat()))));
	}
}
