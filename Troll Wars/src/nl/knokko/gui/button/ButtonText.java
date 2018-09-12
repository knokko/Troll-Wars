package nl.knokko.gui.button;

import org.lwjgl.util.vector.Vector2f;

import nl.knokko.texture.Texture;
import nl.knokko.util.color.Color;
import nl.knokko.util.resources.Resources;

public abstract class ButtonText extends ButtonBase {
	
	public static long totalTime;
	public static int instances;

	public ButtonText(Vector2f centre, Vector2f size, Color buttonColor, Color borderColor, Color textColor, String text) {
		super(centre, size, buttonColor, borderColor);
		long start = System.nanoTime();
		this.textures = new Texture[]{texture, Resources.getTextTexture(text, textColor)};
		instances++;
		totalTime += System.nanoTime() - start;
	}
}
