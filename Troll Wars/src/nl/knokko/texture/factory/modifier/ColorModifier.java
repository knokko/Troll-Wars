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
	 * Determines the weight of this color modifier at the given coordinates. The higher the weight,
	 * the more influence the values of getRed, getGreen and getBlue will have on the pixel at (x,y).
	 * When the weight is 0, it won't have any effect on that coordinate. The value of the weight
	 * is only meaningful in comparison with the weights of the other color modifiers.
	 * @param x The x-coordinate of the pixel
	 * @param y The y-coordinate of the pixel
	 * @return The weight of this color modifier for the given texture coordinates
	 */
	float getWeight(int x, int y);
	
	/**
	 * Determines the minimum x-coordinate where this color modifier will have effect. The getWeight method
	 * will return 0 for all points with a smaller x-coordinate.
	 * @return The minimum x-coordinate where this color modifier has effect
	 */
	int getMinX();
	
	/**
	 * Determines the minimum y-coordinate where this color modifier will have effect. The getWeight method
	 * will return 0 for all points with a smaller y-coordinate.
	 * @return The minimum y-coordinate where this color modifier has effect
	 */
	int getMinY();
	
	/**
	 * Determines the maximum x-coordinate where this color modifier will have effect. The getWeight method
	 * will return 0 for all points with a larger x-coordinate.
	 * @return The maximum x-coordinate where this color modifier has effect
	 */
	int getMaxX();
	
	/**
	 * Determines the maximum y-coordinate where this color modifier will have effect. The getWeight method
	 * will return 0 for all points with a larger y-coordinate.
	 * @return The maximum y-coordinate where this color modifier has effect
	 */
	int getMaxY();
	
	/**
	 * Checks if the point with the given coordinates is within the range of this color modifier. When this
	 * method returns false, the getRed, getGreen and getBlue method of this color modifier will return 0
	 * for the given coordinates.
	 * @param x The x-coordinate
	 * @param y The y-coordinate
	 * @return true if the point with the given coordinates is in the range of this modifier, false if not
	 */
	default boolean isInRange(int x, int y) {
		return x >= getMinX() && x <= getMaxX() && y >= getMinY() && y <= getMaxY();
	}
}