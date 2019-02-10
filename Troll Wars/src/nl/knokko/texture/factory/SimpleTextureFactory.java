package nl.knokko.texture.factory;

import nl.knokko.texture.ModelTexture;
import nl.knokko.texture.Texture;
import nl.knokko.util.color.Color;
import nl.knokko.util.color.ColorAlpha;

public class SimpleTextureFactory {
	
	private static ModelTexture WHITE;
	
	private static Texture createWhiteTexture(){
		return createFilledTexture(Color.WHITE);
	}
	
	public static ModelTexture white(){
		if(WHITE == null)
			WHITE = new ModelTexture(createWhiteTexture(), 0.1f, 0.1f);
		return WHITE;
	}
	
	public static Texture createFilledTexture(Color color){
		TextureBuilder builder = new TextureBuilder(8, 8, color instanceof ColorAlpha);
		builder.fillRect(0, 0, 7, 7, color);
		return new Texture(builder.loadNormal());
	}
}