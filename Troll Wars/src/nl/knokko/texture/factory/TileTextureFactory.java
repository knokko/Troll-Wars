package nl.knokko.texture.factory;

import java.util.Random;

import nl.knokko.texture.Texture;
import nl.knokko.util.color.Color;

public class TileTextureFactory {
	
	private static final Color GREEN_GRASS_COLOR = new Color(100, 200, 50);
	
	public static Texture[] createGrassTextures(int amount){
		return createGrassTextures(amount, GREEN_GRASS_COLOR);
	}
	
	public static Texture[] createGrassTextures(int amount, Color color){
		Texture[] textures = new Texture[amount];
		for(int i = 0; i < textures.length; i++)
			textures[i] = createGrassTexture(i * 28376583, color);
		return textures;
	}
	
	private static Texture createGrassTexture(long seed, Color color){
		TextureBuilder builder = new TextureBuilder(32, 32, false);
		builder.fillAverage(0, 0, builder.width() - 1, builder.height() - 1, color, 0.3f, new Random(seed));
		return new Texture(builder.loadNormal());
	}
	
	public static Texture[] createRockTextures(int amount, Color baseColor, Color[] extraColors, int[] extraChances){
		Texture[] textures = new Texture[amount];
		for(int i = 0; i < textures.length; i++)
			textures[i] = createRockTexture(baseColor, extraColors, extraChances, i * 98236);
		return textures;
	}
	
	private static Texture createRockTexture(Color baseColor, Color[] extraColors, int[] extraChances, long seed){
		TextureBuilder builder = new TextureBuilder(32, 32, false);
		Random random = new Random(seed);
		builder.fillAverage(0, 0, builder.width() - 1, builder.height() - 1, baseColor, 0.2f, random);
		for(int i = 0; i < extraColors.length; i++)
			builder.fillAverageChance(0, 0, builder.width() - 1, builder.height() - 1, extraColors[i], 0.2f, random, extraChances[i]);
		return new Texture(builder.loadNormal());
	}
	
	public static Texture[] createBrickTriLeftTextures(int amount, Color brickColor, Color edgeColor, int brickLength, int brickHeight){
		Texture[] textures = new Texture[amount];
		for(int i = 0; i < textures.length; i++)
			textures[i] = createBrickTriLeftTexture(brickColor, edgeColor, brickLength, brickHeight, i * 73);
		return textures;
	}
	
	private static Texture createBrickTriLeftTexture(Color brickColor, Color edgeColor, int brickLength, int brickHeight, long seed){
		TextureBuilder builder = new TextureBuilder(32, 32, true);
		builder.fillBrickPattern(0, 0, builder.width() - 1, builder.height() - 1, brickLength, brickHeight, brickColor, edgeColor, 0.2f, new Random(seed));
		for(int y = 0; y < 32; y++)
			builder.fillRect(Math.max(0, y * 2 - 32), y, 31, y, (byte) 0, (byte) 0, (byte) 0, (byte) 0);
		return new Texture(builder.loadNormal());
	}
	
	public static Texture[] createBrickTriRightTextures(int amount, Color brickColor, Color edgeColor, int brickLength, int brickHeight){
		Texture[] textures = new Texture[amount];
		for(int i = 0; i < textures.length; i++)
			textures[i] = createBrickTriRightTexture(brickColor, edgeColor, brickLength, brickHeight, i * 73);
		return textures;
	}
	
	private static Texture createBrickTriRightTexture(Color brickColor, Color edgeColor, int brickLength, int brickHeight, long seed){
		TextureBuilder builder = new TextureBuilder(32, 32, true);
		builder.fillBrickPattern(0, 0, builder.width() - 1, builder.height() - 1, brickLength, brickHeight, brickColor, edgeColor, 0.2f, new Random(seed));
		for(int y = 0; y < 32; y++)
			builder.fillRect(0, y, 31 - Math.max(0, y * 2 - 32), y, (byte) 0, (byte) 0, (byte) 0, (byte) 0);
		return new Texture(builder.loadNormal());
	}
	
	public static Texture[] createBrickTextures(int amount, Color brickColor, Color edgeColor, int brickLength, int brickHeight){
		Texture[] textures = new Texture[amount];
		for(int i = 0; i < textures.length; i++)
			textures[i] = createBrickTexture(brickColor, edgeColor, brickLength, brickHeight, i * 73);
		return textures;
	}
	
	private static Texture createBrickTexture(Color brickColor, Color edgeColor, int brickLength, int brickHeight, long seed){
		TextureBuilder builder = new TextureBuilder(32, 32, false);
		builder.fillBrickPattern(0, 0, builder.width() - 1, builder.height() - 1, brickLength, brickHeight, brickColor, edgeColor, 0.2f, new Random(seed));
		return new Texture(builder.loadNormal());
	}
	
	public static Texture[] createPlanksTextures(int amount, Color plankColor, Color edgeColor, int plankLength, int plankHeight, int plankShift){
		Texture[] textures = new Texture[amount];
		for(int i = 0; i < textures.length; i++)
			textures[i] = createPlanksTexture(plankColor, edgeColor, plankLength, plankHeight, plankShift, i * 274);
		return textures;
	}
	
	private static Texture createPlanksTexture(Color plankColor, Color edgeColor, int plankLength, int plankHeight, int plankShift, long seed){
		TextureBuilder builder = new TextureBuilder(32, 32, false);
		builder.fillWoodPlanksPattern(0, 0, builder.width() - 1, builder.height() - 1, plankLength, plankHeight, plankShift, plankColor, edgeColor, 0.3f, new Random(seed));
		return new Texture(builder.loadNormal());
	}
	
	public static Texture[] createFenceTextures(int amount, Color plankColor, Color edgeColor, int plankLength, int plankHeight, int plankShift){
		Texture[] textures = new Texture[amount];
		for(int i = 0; i < textures.length; i++)
			textures[i] = createFenceTexture(plankColor, edgeColor, plankLength, plankHeight, plankShift, i * 2376723);
		return textures;
	}
	
	private static Texture createFenceTexture(Color plankColor, Color edgeColor, int plankLength, int plankHeight, int plankShift, long seed){
		TextureBuilder builder = new TextureBuilder(32, 32, true);
		builder.fillWoodPlanksPattern(0, 0, builder.width() - 1, builder.height() - 1, plankLength, plankHeight, plankShift, plankColor, edgeColor, 0.3f, new Random(seed));
		builder.fillRect(0, 0, 31, 3, (byte) 0, (byte) 0, (byte) 0, (byte) 0);
		for(int x = 1; x < 32; x += 4){
			builder.fillRect(x, 4, x + 1, 6, (byte) 0, (byte) 0, (byte) 0, (byte) 0);
			builder.fillRect(x, 10, x + 1, 17, (byte) 0, (byte) 0, (byte) 0, (byte) 0);
			builder.fillRect(x, 20, x + 1, 27, (byte) 0, (byte) 0, (byte) 0, (byte) 0);
		}
		return new Texture(builder.loadNormal());
	}
	
	public static Texture[] createHoleTextures(int amount, Color baseColor, Color[] extraColors, int[] extraChances){
		Texture[] textures = new Texture[amount];
		for(int i = 0; i < textures.length; i++)
			textures[i] = createHoleTexture(baseColor, extraColors, extraChances, i * 873619);
		return textures;
	}
	
	private static Texture createHoleTexture(Color baseColor, Color[] extraColors, int[] extraChances, long seed){
		TextureBuilder builder = new TextureBuilder(32, 32, true);
		Random random = new Random(seed);
		builder.fillAverage(0, 0, builder.width() - 1, builder.height() - 1, baseColor, 0.2f, random);
		for(int i = 0; i < extraColors.length; i++)
			builder.fillAverageChance(0, 0, builder.width() - 1, builder.height() - 1, extraColors[i], 0.2f, random, extraChances[i]);
		for(int y = 0; y < builder.width(); y++){
			//builder.drawHorizontalLine(0, random.nextInt(5), y, Color.TRANSPARENT);
			//builder.drawHorizontalLine(31 - random.nextInt(5), builder.width - 1, y, Color.TRANSPARENT);
			builder.drawHorizontalLine(random.nextInt(5), 31 - random.nextInt(5), y, Color.TRANSPARENT);
		}
		return new Texture(builder.loadNormal());
	}
	
	public static Texture[] createDoorTextures(int amount, Color plankColor, Color edgeColor, Color holderColor, int plankLength, int plankHeight, int plankShift){
		Texture[] textures = new Texture[amount];
		for(int i = 0; i < textures.length; i++)
			textures[i] = createDoorTexture(plankColor, edgeColor, holderColor, plankLength, plankHeight, plankShift, i * 374583);
		return textures;
	}
	
	private static Texture createDoorTexture(Color plankColor, Color edgeColor, Color holderColor, int plankLength, int plankHeight, int plankShift, long seed){
		Random random = new Random(seed);
		TextureBuilder builder = new TextureBuilder(32, 32, false);
		builder.fillWoodPlanksPattern(0, 0, builder.width() - 1, builder.height() - 1, plankLength, plankHeight, plankShift, plankColor, edgeColor, 0.3f, random);
		builder.fillCircle(25, 25, 5, holderColor);
		return new Texture(builder.loadNormal());
	}
	
	public static Texture[] createLadderTextures(int amount, Color verticalColor, Color horizontalColor){
		Texture[] textures = new Texture[amount];
		for(int i = 0; i < textures.length; i++)
			textures[i] = createLadderTexture(verticalColor, horizontalColor);
		return textures;
	}
	
	private static Texture createLadderTexture(Color verticalColor, Color horizontalColor){
		TextureBuilder builder = new TextureBuilder(32, 32, true);
		builder.fillRect(0, 0, 3, 31, verticalColor);
		builder.fillRect(28, 0, 31, 31, verticalColor);
		for(int i = 0; i < 32; i += 4)
			builder.drawHorizontalLine(0, 31, i, horizontalColor);
		return new Texture(builder.loadNormal());
	}
}