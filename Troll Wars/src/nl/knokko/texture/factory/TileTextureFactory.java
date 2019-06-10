/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2019 knokko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *  
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *******************************************************************************/
package nl.knokko.texture.factory;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import javax.imageio.ImageIO;

import nl.knokko.texture.Texture;
import nl.knokko.texture.factory.modifier.CircleFunctionFloatMod;
import nl.knokko.texture.factory.modifier.ColorModifier;
import nl.knokko.texture.factory.modifier.ColorTable;
import nl.knokko.texture.factory.modifier.ConstantColorModifier;
import nl.knokko.texture.factory.modifier.FloatModifier;
import nl.knokko.texture.factory.modifier.SumFloatModifiers;
import nl.knokko.texture.factory.modifier.UniformColorModifier;
import nl.knokko.util.Maths;
import nl.knokko.util.color.Color;

public class TileTextureFactory {

	private static int[] spreadCircles(Random random, int minX, int minY, int maxX, int maxY, int amount,
			int attemptsPerCircle) {
		int[] coords = new int[amount * 2];
		int width = maxX - minX + 1;
		int height = maxY - minY + 1;
		coords[0] = minX + random.nextInt(width);
		coords[1] = minY + random.nextInt(height);
		for (int index = 1; index < amount; index++) {

			// Low score is better, so this score should be overwritten at the first attempt
			double bestScore = Double.POSITIVE_INFINITY;
			int bestX = -1;
			int bestY = -1;

			for (int counter = 0; counter < attemptsPerCircle; counter++) {
				int maybeX = minX + random.nextInt(width);
				int maybeY = minY + random.nextInt(height);
				double maybeScore = 0;
				for (int scoreIndex = 0; scoreIndex < index; scoreIndex++) {
					int difX1 = coords[2 * scoreIndex] - maybeX;
					int difY1 = coords[2 * scoreIndex + 1] - maybeY;

					int difX2 = Math.abs(difX1 - width);
					int difY2 = Math.abs(difY1 - height);

					int difX3 = Math.abs(difX1 + width);
					int difY3 = Math.abs(difY1 + height);

					int difX = Maths.min(Math.abs(difX1), difX2, difX3);
					int difY = Maths.min(Math.abs(difY1), difY2, difY3);
					double distanceSQ = difX * difX + difY * difY;
					maybeScore += 1.0 / Math.pow(distanceSQ, 3);
				}
				if (maybeScore < bestScore) {
					bestScore = maybeScore;
					bestX = maybeX;
					bestY = maybeY;
				}
			}

			coords[index * 2] = bestX;
			coords[index * 2 + 1] = bestY;
		}
		return coords;
	}
	
	public static void createBigRockTexture2(Color darkColor, Color lightColor, Color...gemColors) {
		long startTime = System.currentTimeMillis();
		int AMOUNT = 20;
		int[][] coordsX = new int[AMOUNT][AMOUNT];
		int[][] coordsY = new int[AMOUNT][AMOUNT];
		int SIZE = 1024;
		Random random = new Random();
		TextureBuilder texture = new TextureBuilder(SIZE, SIZE, false);
		for (int x = 0; x < AMOUNT; x++) {
			int baseX = x * SIZE / AMOUNT;
			for (int y = 0; y < AMOUNT; y++) {
				int baseY = y * SIZE / AMOUNT;
				coordsX[x][y] = baseX + 50 - random.nextInt(100);
				coordsY[x][y] = baseY + 50 - random.nextInt(100);
			}
		}
		
		for (int indexX = 0; indexX < AMOUNT; indexX++) {
			for (int indexY = 0; indexY < AMOUNT; indexY++) {
				texture.fillCircle(coordsX[indexX][indexY], coordsY[indexX][indexY], 20, Color.GREEN);
			}
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("Creating big rock texture 2 took " + (endTime - startTime) + " ms");
		
		try {
			ImageIO.write(texture.createBufferedImage(), "PNG", new File("rocktest.png"));
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}

	public static Texture createBigRockTexture(Color darkColor, Color lightColor, Color... gemColors) {

		long startTime = System.currentTimeMillis();

		int textureWidth = 1024;
		int textureHeight = 1024;
		TextureBuilder texture = new TextureBuilder(textureWidth, textureHeight, false);
		
		Collection<ColorModifier> modifiers = new ArrayList<ColorModifier>(2);

		modifiers.add(new ConstantColorModifier(1f, darkColor.getRedF(), darkColor.getGreenF(), darkColor.getBlueF(), 0,
				0, textureWidth, textureHeight));

		Random random = new Random();

		{
			int amount = 15;
			int[] lightCircleCoords = spreadCircles(random, 0, 0, textureWidth, textureHeight, amount, 3);
			FloatModifier[] lightModifiers = new FloatModifier[amount];
			for (int index = 0; index < amount; index++) {
				float offset1 = 360 * random.nextFloat();
				float offset2 = 360 * random.nextFloat();
				float offset3 = 360 * random.nextFloat();
				float offset4 = 360 * random.nextFloat();
				float offset5 = 360 * random.nextFloat();
				lightModifiers[index] = new CircleFunctionFloatMod(lightCircleCoords[index * 2],
						lightCircleCoords[index * 2 + 1], (float angle) -> {
							return 200 + 10f * (Maths.sin(angle - offset1) + Maths.sin((angle - offset2) * 2)
									+ Maths.sin((angle - offset3) * 3) + Maths.sin((angle - offset4) * 4)
									+ Maths.sin((angle - offset5) * 5));
						}, (float radius, float distance) -> {
							return Math.max(0.2f - 0.2f * distance / radius, 0);
						});
			}
			modifiers.add(new UniformColorModifier(new SumFloatModifiers(textureWidth, textureHeight, lightModifiers),
					lightColor.getRedF(), lightColor.getGreenF(), lightColor.getBlueF()));
		}

		{
			int amount = 20 / gemColors.length;
			int edgeCount = 7;
			for (Color gemColor : gemColors) {
				FloatModifier[] gemModifiers = new FloatModifier[amount];
				int[] gemCoords = spreadCircles(random, 0, 0, textureWidth, textureHeight, amount, 5);
				for (int index = 0; index < amount; index++) {
					float[] edges = new float[edgeCount + 1];
					for (int edgeIndex = 0; edgeIndex < edgeCount; edgeIndex++) {
						edges[edgeIndex] = 50f + 30f * random.nextFloat();
					}
					edges[edgeCount] = edges[0];
					gemModifiers[index] = new CircleFunctionFloatMod(gemCoords[index * 2], gemCoords[index * 2 + 1],
							(float angle) -> {
								
								float currentIndex = edgeCount * angle / 360f;
								int indexLow = (int) currentIndex;
								if (currentIndex == indexLow) {
									return edges[indexLow];
								} else {
									int indexHigh = indexLow + 1;
									float highFactor = currentIndex - indexLow;
									float lowFactor = indexHigh - currentIndex;
									float lowEdge = edges[indexLow];
									float highEdge = edges[indexHigh];
									float radius = lowFactor * lowEdge + highFactor * highEdge;
									return radius;
								}
							}, (float radius, float distance) -> {
								return Math.max(0.3f - 0.3f * distance / radius, 0);
							});
				}
				modifiers.add(new UniformColorModifier(new SumFloatModifiers(textureWidth, textureHeight, gemModifiers),
						gemColor.getRedF(), gemColor.getGreenF(), gemColor.getBlueF()));
			}
		}

		ColorTable.sumModifiers(texture, modifiers, 0, 1);

		long endTime = System.currentTimeMillis();
		System.out.println("Creating big rock texture took " + (endTime - startTime) + " ms");

		/*
		try {
			BufferedImage image = texture.createBufferedImage();
			BufferedImage multiple = new BufferedImage(3 * textureWidth, 3 * textureHeight, image.getType());
			Graphics2D g = multiple.createGraphics();
			g.drawImage(image, 0, 0, null);
			g.drawImage(image, 0, textureHeight, null);
			g.drawImage(image, 0, 2 * textureHeight, null);
			g.drawImage(image, textureWidth, 0, null);
			g.drawImage(image, textureWidth, textureHeight, null);
			g.drawImage(image, textureWidth, 2 * textureHeight, null);
			g.drawImage(image, 2 * textureWidth, 0, null);
			g.drawImage(image, 2 * textureWidth, textureHeight, null);
			g.drawImage(image, 2 * textureWidth, 2 * textureHeight, null);
			g.dispose();
			ImageIO.write(multiple, "PNG", new File("testrock.png"));
		} catch (IOException io) {
			io.printStackTrace();
		}
		
		long endTime2 = System.currentTimeMillis();
		System.out.println("Saving big rock image took " + (endTime2 - endTime) + " ms");

		*/
		return new Texture(texture.loadNormal());
	}

	private static final Color GREEN_GRASS_COLOR = new Color(100, 200, 50);

	public static Texture[] createGrassTextures(int amount) {
		return createGrassTextures(amount, GREEN_GRASS_COLOR);
	}

	public static Texture[] createGrassTextures(int amount, Color color) {
		Texture[] textures = new Texture[amount];
		for (int i = 0; i < textures.length; i++)
			textures[i] = createGrassTexture(i * 28376583, color);
		return textures;
	}

	public static Texture createBigGrassTexture(Color grassColor, Color brightGrassColor, Color groundColor) {
		
		long startTime = System.currentTimeMillis();

		// Calculate and define the most used variables before starting the actual stuff
		int textureWidth = 1024;
		int textureHeight = 1024;

		int redBase = grassColor.getRedI();
		int greenBase = grassColor.getGreenI();
		int blueBase = grassColor.getBlueI();

		int redLeft = brightGrassColor.getRedI() - redBase;
		int greenLeft = brightGrassColor.getGreenI() - greenBase;
		int blueLeft = brightGrassColor.getBlueI() - blueBase;

		byte redGround = groundColor.getRed();
		byte greenGround = groundColor.getGreen();
		byte blueGround = groundColor.getBlue();

		TextureBuilder builder = new TextureBuilder(textureWidth, textureHeight, false);

		// First color the ground
		for (int x = 0; x < textureWidth; x++) {
			for (int y = 0; y < textureHeight; y++) {
				builder.setPixel(x, y, redGround, greenGround, blueGround);
			}
		}

		// Use the height map to make sure the highest grass is always shown
		byte[][] heightMap = new byte[textureWidth][textureHeight];
		Random random = new Random();

		// Draw that many grass 'lines'
		for (int counter = 0; counter < 15000; counter++) {
			int startX = random.nextInt(textureWidth);
			int startY = random.nextInt(textureHeight);
			float angle = random.nextFloat() * 360f;

			// The angle between vertical and the angle at the end of the grass
			float vertAngle = random.nextFloat() * 70f;
			int length = 50 + random.nextInt(30);

			// A dirty trick that should work
			length *= Maths.sin(vertAngle);

			int width = 4 + random.nextInt(3);
			int endX = startX + (int) (Maths.cos(angle) * length);
			int endY = startY + (int) (Maths.sin(angle) * length);

			// The line through these coordinates will be perpendicular to angle and go
			// through (startX,startY)
			int startX1 = startX - (int) (Maths.sin(angle) * width);
			int startY1 = startY + (int) (Maths.cos(angle) * width);
			int startX2 = startX + (int) (Maths.sin(angle) * width);
			int startY2 = startY - (int) (Maths.cos(angle) * width);

			// revertA and revertB will be used to transform the effective region such that
			// it becomes vertical
			float revertA = Maths.cos(-angle + 90);
			float revertB = Maths.sin(-angle + 90);

			// The next variables will make it easier to loop
			int minX = Maths.min(startX1, startX2, endX);
			int minY = Maths.min(startY1, startY2, endY);
			int maxX = Maths.max(startX1, startX2, endX);
			int maxY = Maths.max(startY1, startY2, endY);
			int effectiveWidth = maxX - minX + 1;
			int effectiveHeight = maxY - minY + 1;
			int fictiveStartX = startX - minX;
			int fictiveStartY = startY - minY;

			// Loop over all relevant coordinates
			for (int x = 0; x < effectiveWidth; x++) {
				for (int y = 0; y < effectiveHeight; y++) {

					// Rotate (x - fictiveStartX, y - fictiveStartY)
					float transformedX = revertA * (x - fictiveStartX) - revertB * (y - fictiveStartY);
					float transformedY = revertA * (y - fictiveStartY) + revertB * (x - fictiveStartX);

					// Check if the pixel at this location should be affected
					if (transformedX > -width && transformedX < width && transformedY >= 0 && transformedY < length) {
						float progress = transformedY / length;
						if (Math.abs(transformedX) <= grassWidth(progress, width)) {

							// Let's now get back to the texture coordinates
							int realX = minX + x;
							int realY = minY + y;

							// If we get a little outside of the texture range, we continue on the other
							// side
							if (realX >= textureWidth) {
								realX -= textureWidth;
							}
							if (realX < 0) {
								realX += textureWidth;
							}
							if (realY >= textureHeight) {
								realY -= textureHeight;
							}
							if (realY < 0) {
								realY += textureHeight;
							}

							// Finally test if we are not 'below' some other grass 'line'
							byte realHeight = (byte) grassHeight(progress, length, vertAngle);
							if (realHeight >= heightMap[realX][realY]) {
								heightMap[realX][realY] = realHeight;

								float extraColor = extraGrassColor(progress, vertAngle);
								builder.setPixel(realX, realY, (byte) (redBase + extraColor * redLeft),
										(byte) (greenBase + extraColor * greenLeft),
										(byte) (blueBase + extraColor * blueLeft));
							}
						}
					}
				}
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Creating big grass texture took " + (endTime - startTime) + " ms");
		return new Texture(builder.loadNormal());
	}

	private static float grassWidth(float progress, int maxWidth) {
		return Maths.sqrt(1 - progress) * maxWidth;
	}

	private static float grassHeight(float progress, int maxHeight, float vertAngle) {
		return Maths.cos(vertAngle) * progress * maxHeight;
	}

	private static float extraGrassColor(float progress, float vertAngle) {
		return Maths.sin(vertAngle) * progress * progress;
	}

	private static Texture createGrassTexture(long seed, Color color) {
		TextureBuilder builder = new TextureBuilder(32, 32, false);
		builder.fillAverage(0, 0, builder.width() - 1, builder.height() - 1, color, 0.3f, new Random(seed));
		return new Texture(builder.loadNormal());
	}

	public static Texture[] createRockTextures(int amount, Color baseColor, Color[] extraColors, int[] extraChances) {
		Texture[] textures = new Texture[amount];
		for (int i = 0; i < textures.length; i++)
			textures[i] = createRockTexture(baseColor, extraColors, extraChances, i * 98236);
		return textures;
	}

	private static Texture createRockTexture(Color baseColor, Color[] extraColors, int[] extraChances, long seed) {
		TextureBuilder builder = new TextureBuilder(32, 32, false);
		Random random = new Random(seed);
		builder.fillAverage(0, 0, builder.width() - 1, builder.height() - 1, baseColor, 0.2f, random);
		for (int i = 0; i < extraColors.length; i++)
			builder.fillAverageChance(0, 0, builder.width() - 1, builder.height() - 1, extraColors[i], 0.2f, random,
					extraChances[i]);
		return new Texture(builder.loadNormal());
	}

	public static Texture[] createBrickTriLeftTextures(int amount, Color brickColor, Color edgeColor, int brickLength,
			int brickHeight) {
		Texture[] textures = new Texture[amount];
		for (int i = 0; i < textures.length; i++)
			textures[i] = createBrickTriLeftTexture(brickColor, edgeColor, brickLength, brickHeight, i * 73);
		return textures;
	}

	private static Texture createBrickTriLeftTexture(Color brickColor, Color edgeColor, int brickLength,
			int brickHeight, long seed) {
		TextureBuilder builder = new TextureBuilder(32, 32, true);
		builder.fillBrickPattern(0, 0, builder.width() - 1, builder.height() - 1, brickLength, brickHeight, brickColor,
				edgeColor, 0.2f, new Random(seed));
		for (int y = 0; y < 32; y++)
			builder.fillRect(Math.max(0, y * 2 - 32), y, 31, y, (byte) 0, (byte) 0, (byte) 0, (byte) 0);
		return new Texture(builder.loadNormal());
	}

	public static Texture[] createBrickTriRightTextures(int amount, Color brickColor, Color edgeColor, int brickLength,
			int brickHeight) {
		Texture[] textures = new Texture[amount];
		for (int i = 0; i < textures.length; i++)
			textures[i] = createBrickTriRightTexture(brickColor, edgeColor, brickLength, brickHeight, i * 73);
		return textures;
	}

	private static Texture createBrickTriRightTexture(Color brickColor, Color edgeColor, int brickLength,
			int brickHeight, long seed) {
		TextureBuilder builder = new TextureBuilder(32, 32, true);
		builder.fillBrickPattern(0, 0, builder.width() - 1, builder.height() - 1, brickLength, brickHeight, brickColor,
				edgeColor, 0.2f, new Random(seed));
		for (int y = 0; y < 32; y++)
			builder.fillRect(0, y, 31 - Math.max(0, y * 2 - 32), y, (byte) 0, (byte) 0, (byte) 0, (byte) 0);
		return new Texture(builder.loadNormal());
	}

	public static Texture[] createBrickTextures(int amount, Color brickColor, Color edgeColor, int brickLength,
			int brickHeight) {
		Texture[] textures = new Texture[amount];
		for (int i = 0; i < textures.length; i++)
			textures[i] = createBrickTexture(brickColor, edgeColor, brickLength, brickHeight, i * 73);
		return textures;
	}

	private static Texture createBrickTexture(Color brickColor, Color edgeColor, int brickLength, int brickHeight,
			long seed) {
		TextureBuilder builder = new TextureBuilder(32, 32, false);
		builder.fillBrickPattern(0, 0, builder.width() - 1, builder.height() - 1, brickLength, brickHeight, brickColor,
				edgeColor, 0.2f, new Random(seed));
		return new Texture(builder.loadNormal());
	}

	public static Texture[] createPlanksTextures(int amount, Color plankColor, Color edgeColor, int plankLength,
			int plankHeight, int plankShift) {
		Texture[] textures = new Texture[amount];
		for (int i = 0; i < textures.length; i++)
			textures[i] = createPlanksTexture(plankColor, edgeColor, plankLength, plankHeight, plankShift, i * 274);
		return textures;
	}

	private static Texture createPlanksTexture(Color plankColor, Color edgeColor, int plankLength, int plankHeight,
			int plankShift, long seed) {
		TextureBuilder builder = new TextureBuilder(32, 32, false);
		builder.fillWoodPlanksPattern(0, 0, builder.width() - 1, builder.height() - 1, plankLength, plankHeight,
				plankShift, plankColor, edgeColor, 0.3f, new Random(seed));
		return new Texture(builder.loadNormal());
	}

	public static Texture[] createFenceTextures(int amount, Color plankColor, Color edgeColor, int plankLength,
			int plankHeight, int plankShift) {
		Texture[] textures = new Texture[amount];
		for (int i = 0; i < textures.length; i++)
			textures[i] = createFenceTexture(plankColor, edgeColor, plankLength, plankHeight, plankShift, i * 2376723);
		return textures;
	}

	private static Texture createFenceTexture(Color plankColor, Color edgeColor, int plankLength, int plankHeight,
			int plankShift, long seed) {
		TextureBuilder builder = new TextureBuilder(32, 32, true);
		builder.fillWoodPlanksPattern(0, 0, builder.width() - 1, builder.height() - 1, plankLength, plankHeight,
				plankShift, plankColor, edgeColor, 0.3f, new Random(seed));
		builder.fillRect(0, 0, 31, 3, (byte) 0, (byte) 0, (byte) 0, (byte) 0);
		for (int x = 1; x < 32; x += 4) {
			builder.fillRect(x, 4, x + 1, 6, (byte) 0, (byte) 0, (byte) 0, (byte) 0);
			builder.fillRect(x, 10, x + 1, 17, (byte) 0, (byte) 0, (byte) 0, (byte) 0);
			builder.fillRect(x, 20, x + 1, 27, (byte) 0, (byte) 0, (byte) 0, (byte) 0);
		}
		return new Texture(builder.loadNormal());
	}

	public static Texture[] createHoleTextures(int amount, Color baseColor, Color[] extraColors, int[] extraChances) {
		Texture[] textures = new Texture[amount];
		for (int i = 0; i < textures.length; i++)
			textures[i] = createHoleTexture(baseColor, extraColors, extraChances, i * 873619);
		return textures;
	}

	private static Texture createHoleTexture(Color baseColor, Color[] extraColors, int[] extraChances, long seed) {
		TextureBuilder builder = new TextureBuilder(32, 32, true);
		Random random = new Random(seed);
		builder.fillAverage(0, 0, builder.width() - 1, builder.height() - 1, baseColor, 0.2f, random);
		for (int i = 0; i < extraColors.length; i++)
			builder.fillAverageChance(0, 0, builder.width() - 1, builder.height() - 1, extraColors[i], 0.2f, random,
					extraChances[i]);
		for (int y = 0; y < builder.width(); y++) {
			// builder.drawHorizontalLine(0, random.nextInt(5), y, Color.TRANSPARENT);
			// builder.drawHorizontalLine(31 - random.nextInt(5), builder.width - 1, y,
			// Color.TRANSPARENT);
			builder.drawHorizontalLine(random.nextInt(5), 31 - random.nextInt(5), y, Color.TRANSPARENT);
		}
		return new Texture(builder.loadNormal());
	}

	public static Texture[] createDoorTextures(int amount, Color plankColor, Color edgeColor, Color holderColor,
			int plankLength, int plankHeight, int plankShift) {
		Texture[] textures = new Texture[amount];
		for (int i = 0; i < textures.length; i++)
			textures[i] = createDoorTexture(plankColor, edgeColor, holderColor, plankLength, plankHeight, plankShift,
					i * 374583);
		return textures;
	}

	private static Texture createDoorTexture(Color plankColor, Color edgeColor, Color holderColor, int plankLength,
			int plankHeight, int plankShift, long seed) {
		Random random = new Random(seed);
		TextureBuilder builder = new TextureBuilder(32, 32, false);
		builder.fillWoodPlanksPattern(0, 0, builder.width() - 1, builder.height() - 1, plankLength, plankHeight,
				plankShift, plankColor, edgeColor, 0.3f, random);
		builder.fillCircle(25, 25, 5, holderColor);
		return new Texture(builder.loadNormal());
	}

	public static Texture[] createLadderTextures(int amount, Color verticalColor, Color horizontalColor) {
		Texture[] textures = new Texture[amount];
		for (int i = 0; i < textures.length; i++)
			textures[i] = createLadderTexture(verticalColor, horizontalColor);
		return textures;
	}

	private static Texture createLadderTexture(Color verticalColor, Color horizontalColor) {
		TextureBuilder builder = new TextureBuilder(32, 32, true);
		builder.fillRect(0, 0, 3, 31, verticalColor);
		builder.fillRect(28, 0, 31, 31, verticalColor);
		for (int i = 0; i < 32; i += 4)
			builder.drawHorizontalLine(0, 31, i, horizontalColor);
		return new Texture(builder.loadNormal());
	}
}