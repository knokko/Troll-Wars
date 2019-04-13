package nl.knokko.texture.factory.modifier;

/**
 * Instances of FloatModifier assign values to any 2-dimensional point that is in their range.
 * They are basically functions with 2 inputs. They will be used to help with generating random
 * textures.
 * @author knokko
 *
 */
public interface FloatModifier {
	
	/**
	 * Gets the value that this float modifier will assign to the given texture coordinates.
	 * @param x The x-coordinate of the point
	 * @param y The y-coordinate of the point
	 * @return The value of this modifier for the given texture coordinates
	 */
	float getValue(int x, int y);
	
	/**
	 * Determines the minimum x-coordinate where this modifier will have effect. For all points that have a
	 * smaller x-coordinate, the getValue method of this modifier will return 0.
	 * @return The minimum x-coordinate where this modifier will have effect
	 */
	int getMinX();
	
	/**
	 * Determines the minimum y-coordinate where this modifier will have effect. For all points that have a
	 * smaller y-coordinate, the getValue method of this modifier will return 0.
	 * @return The minimum y-coordinate where this modifier will have effect
	 */
	int getMinY();
	
	/**
	 * Determines the maximum x-coordinate where this modifier will have effect. For all points that have a
	 * larger x-coordinate, the getValue method of this modifier will return 0.
	 * @return The maximum x-coordinate where this modifier will have effect
	 */
	int getMaxX();
	
	/**
	 * Determines the maximum y-coordinate where this modifier will have effect. For all points that have a
	 * larger y-coordinate, the getValue method of this modifier will return 0.
	 * @return The maximum y-coordinate where this modifier will have effect
	 */
	int getMaxY();
}