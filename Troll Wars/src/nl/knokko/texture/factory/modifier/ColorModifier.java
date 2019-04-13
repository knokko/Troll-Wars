package nl.knokko.texture.factory.modifier;

/**
 * Instances of ColorModifier assign values for red, green and blue to every point in their range.
 * They will typically use instances of FloatModifier for the actual function, but not necessarily.
 * 
 * They will be used to help with programmatically generate big textures that look more or less smooth.
 * @author knokko
 *
 */
public interface ColorModifier {
	
	/**
	 * Calculates the red value of this color modifier for the given texture coordinates.
	 * @param x The x-coordinate
	 * @param y The y-coordinate
	 * @return The red value of this color modifier for the given coordinates
	 */
	float getRed(int x, int y);
	
	/**
	 * Calculates the green value of this color modifier for the given texture coordinates.
	 * @param x The x-coordinate
	 * @param y The y-coordinate
	 * @return The green value of this color modifier for the given coordinates
	 */
	float getGreen(int x, int y);
	
	/**
	 * Calculates the blue value of this color modifier for the given texture coordinates.
	 * @param x The x-coordinate
	 * @param y The y-coordinate
	 * @return The blue value of this color modifier for the given coordinates
	 */
	float getBlue(int x, int y);
	
	/**
	 * Determines the minimum x-coordinate where this color modifier will have effect. The methods getRed,
	 * getGreen and getBlue will return 0 for all points with a smaller x-coordinate.
	 * @return The minimum x-coordinate where this color modifier has effect
	 */
	int getMinX();
	
	/**
	 * Determines the minimum y-coordinate where this color modifier will have effect. The methods getRed,
	 * getGreen and getBlue will return 0 for all points with a smaller y-coordinate.
	 * @return The minimum y-coordinate where this color modifier has effect
	 */
	int getMinY();
	
	/**
	 * Determines the maximum x-coordinate where this color modifier will have effect. The methods getRed,
	 * getGreen and getBlue will return 0 for all points with a larger x-coordinate.
	 * @return The maximum x-coordinate where this color modifier has effect
	 */
	int getMaxX();
	
	/**
	 * Determines the maximum y-coordinate where this color modifier will have effect. The methods getRed,
	 * getGreen and getBlue will return 0 for all points with a larger y-coordinate.
	 * @return The maximum y-coordinate where this color modifier has effect
	 */
	int getMaxY();
}