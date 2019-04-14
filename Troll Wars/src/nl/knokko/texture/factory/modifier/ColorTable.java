package nl.knokko.texture.factory.modifier;

import java.util.Collection;

import nl.knokko.texture.factory.TextureBuilder;

public class ColorTable {
	
	public static void sumModifiers(TextureBuilder builder, Collection<ColorModifier> modifiers, float min, float max) {
		int width = builder.width();
		int height = builder.height();
		float delta = max - min;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				float weight = 0;
				float red = 0;
				float green = 0;
				float blue = 0;
				for (ColorModifier mod : modifiers) {
					if (mod.isInRange(x, y)) {
						float nextWeight = mod.getWeight(x, y);
						red += mod.getRed(x, y) * nextWeight;
						green += mod.getGreen(x, y) * nextWeight;
						blue += mod.getBlue(x, y) * nextWeight;
						weight += nextWeight;
					}
				}
				if (weight != 0) {
					red /= weight;
					green /= weight;
					blue /= weight;
				}
				if (red < min) red = min;
				if (red > max) red = max;
				if (green < min) green = min;
				if (green > max) green = max;
				if (blue < min) blue = min;
				if (blue > max) blue = max;
				builder.setPixel(x, y, (byte) (255 * (red - min) / delta), (byte) (255 * (green - min) / delta), (byte) (255 * (blue - min) / delta));
			}
		}
	}
}