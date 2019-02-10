package nl.knokko.texture.factory;

import java.util.Random;

import nl.knokko.util.color.Color;
import nl.knokko.util.color.ColorAlpha;

public class TextureBuilder {
	
	public static Color getDifColor(Random random, Color basic, float maxDifference){
    	//float factor = 1 - maxDifference + random.nextFloat() * maxDifference * 2;
    	return getMultipliedColor(basic, 1 - maxDifference + random.nextFloat() * maxDifference * 2);
		//return new GuiColor(Math.max(Math.min((int)(basic.getRedI() * factor), 255), 0), Math.max(Math.min((int)(basic.getGreenI() * factor), 255), 0), Math.max(Math.min((int)(basic.getBlueI() * factor), 255), 0));
    }
	
	public static Color getMultipliedColor(Color basic, float factor){
		return new Color(Math.max(Math.min((int)(basic.getRedI() * factor), 255), 0), Math.max(Math.min((int)(basic.getGreenI() * factor), 255), 0), Math.max(Math.min((int)(basic.getBlueI() * factor), 255), 0));
	}
	
	private byte[] data;
	
	private int width;
	private int height;
	
	private boolean useAlpha;
	
	public TextureBuilder(int width, int height, boolean useAlpha){
		data = new byte[width * height * (useAlpha ? 4 : 3)];
		this.width = width;
		this.height = height;
		this.useAlpha = useAlpha;
	}
	
	public void setPixel(int x, int y, byte red, byte green, byte blue, byte alpha){
		int index = (y * width + x) * (useAlpha ? 4 : 3);
		data[index] = red;
		data[index + 1] = green;
		data[index + 2] = blue;
		if(useAlpha)
			data[index + 3] = alpha;
	}
	
	public void setPixel(int x, int y, byte red, byte green, byte blue){
		setPixel(x, y, red, green, blue, (byte) 0);
	}
	
	public void setPixel(int x, int y, Color color){
		setPixel(x, y, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}
	
	public Color getPixel(int x, int y){
		int index = (y * width + x) * (useAlpha ? 4 : 3);
		return useAlpha ? new ColorAlpha(data[index], data[index + 1], data[index + 2], data[index + 3]) : new Color(data[index], data[index + 1], data[index + 2]);
	}
	
	public void drawHorizontalLine(int minX, int maxX, int y, byte red, byte green, byte blue, byte alpha){
		for(int x = minX; x <= maxX; x++)
			setPixel(x, y, red, green, blue, alpha);
	}
	
	public void drawHorizontalLine(int minX, int maxX, int y, Color color){
		drawHorizontalLine(minX, maxX, y, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}
	
	public void drawVerticalLine(int minY, int maxY, int x, byte red, byte green, byte blue, byte alpha){
		for(int y = minY; y <= maxY; y++)
			setPixel(x, y, red, green, blue, alpha);
	}
	
	public void drawVerticalLine(int minY, int maxY, int x, Color color){
		drawVerticalLine(minY, maxY, x, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}
	
	public void fillRect(int minX, int minY, int maxX, int maxY, byte red, byte green, byte blue, byte alpha){
		for(int x = minX; x <= maxX; x++)
			for(int y = minY; y <= maxY; y++)
				setPixel(x, y, red, green, blue, alpha);
	}
	
	public void fillRect(int minX, int minY, int maxX, int maxY, Color color){
		fillRect(minX, minY, maxX, maxY, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}
	
	public void fillCircle(int centreX, int centreY, double radius, Color color){
		int minX = (int)(centreX - radius);
		int minY = (int)(centreY - radius);
		int maxX = (int)(centreX + radius + 1);
		int maxY = (int)(centreY + radius + 1);
		for(int x = minX; x <= maxX; x++){
			for(int y = minY; y <= maxY; y++){
				double distance = Math.hypot(x - centreX, y - centreY);
				if(distance <= radius)
					setPixel(x, y, color);
			}
		}
	}
	
	public void fillOval(int centreX, int centreY, double radiusX, double radiusY, Color color){
		int minX = (int)(centreX - radiusX);
		int minY = (int)(centreY - radiusY);
		int maxX = (int)(centreX + radiusX + 1);
		int maxY = (int)(centreY + radiusY + 1);
		for(int x = minX; x <= maxX; x++){
			for(int y = minY; y <= maxY; y++){
				double distance = Math.hypot((x - centreX) / radiusX, (y - centreY) / radiusY);
				if(distance <= 1)
					setPixel(x, y, color);
			}
		}
	}
	
	public void fillAverage(int minX, int minY, int maxX, int maxY, Color color, float maxDifference, Random random){
		for(int x = minX; x <= maxX; x++)
			for(int y = minY; y <= maxY; y++)
				setPixel(x, y, getDifColor(random, color, maxDifference));
	}
	
	public void fillAverageChance(int minX, int minY, int maxX, int maxY, Color color, float maxDifference, Random random, int chance){
		for(int x = minX; x <= maxX; x++)
			for(int y = minY; y <= maxY; y++)
				if(random.nextInt(chance) == 0)
					setPixel(x, y, getDifColor(random, color, maxDifference));
	}
	
	public void fillAverageChance(int minX, int minY, int maxX, int maxY, Color color, float maxDifference, Random random, int partChance, int wholeChance){
		for(int x = minX; x <= maxX; x++)
			for(int y = minY; y <= maxY; y++)
				if(random.nextInt(wholeChance) < partChance)
					setPixel(x, y, getDifColor(random, color, maxDifference));
	}
	
	public void fillBrickPattern(int minX, int minY, int maxX, int maxY, int brickLength, int brickHeight, Color brickColor, Color edgeColor, float maxDifference, Random random){
		fillAverage(minX, minY, maxX, maxY, brickColor, maxDifference, random);
		for(int y = minY; y <= maxY; y += brickHeight)
			fillAverage(minX, y, maxX, y, edgeColor, maxDifference, random);
		boolean flipper = false;
		for(int y = minY; y <= maxY; y += brickHeight){
			for(int x = flipper ? minX : minX + brickLength / 2; x <= maxX; x += brickLength)
				fillAverage(x, y, x, Math.min(y + brickHeight, maxY), edgeColor, maxDifference, random);
			flipper = !flipper;
		}
	}
	
	public void fillWoodPlanksPattern(int minX, int minY, int maxX, int maxY, int plankLength, int plankHeight, int plankShift, Color plankColor, Color edgeColor, float maxDifference, Random random){
		//fillWoodPattern(minX, minY, maxX, maxY, plankColor, random);
		int shift = plankShift;
		for(int y = minY; y <= maxY; y += plankHeight){
			while(shift > plankLength)
				shift -= plankLength;
			fillWoodPattern(minX, y, minX + shift, Math.min(y + plankHeight, maxY), plankColor, random);
			for(int x = minX + shift; x <= maxX; x += plankLength)
				fillWoodPattern(x, y, Math.min(x + plankLength, maxX), Math.min(y + plankHeight, maxY), plankColor, random);
			shift += plankShift;
		}
		shift = plankShift;
		for(int y = minY; y <= maxY; y += plankHeight){
			fillAverage(minX, y, maxX, y, edgeColor, maxDifference, random);
			for(int x = minX + shift; x <= maxX; x += plankLength)
				fillAverage(x, y, x, Math.min(y + plankHeight, maxY), edgeColor, maxDifference, random);
			shift += plankShift;
			while(shift > plankLength)
				shift -= plankLength;
		}
		drawHorizontalLine(minX, maxX, minY, edgeColor);
		drawHorizontalLine(minX, maxX, maxY, edgeColor);
		drawVerticalLine(minY, maxY, minX, edgeColor);
		drawVerticalLine(minY, maxY, maxX, edgeColor);
	}
	
	public void fillWoodPattern(int minX, int minY, int maxX, int maxY, Color averageColor, Random random){
		Color color = getDifColor(random, averageColor, 0.3f);
		fillRect(minX, minY, maxX, maxY, color);
		for(int i = 0; i < 10; i++){
			Color lineColor = getDifColor(random, color, 0.3f);
			int y = minY + random.nextInt(maxY - minY + 1);
			for(int x = minX; x <= maxX; x++){
				setPixel(x, y, lineColor);
				if(y < maxY && random.nextInt(4) == 0)
					y++;
				if(y > minY && random.nextInt(4) == 0)
					y--;
			}
		}
	}
	
	public int width(){
		return width;
	}
	
	public int height(){
		return height;
	}
	
	public boolean useAlpha(){
		return useAlpha;
	}
	
	public int loadNormal(){
		return MyTextureLoader.loadTexture(data, width, height, useAlpha);
	}
}