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
package nl.knokko.util.resources;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

import javax.imageio.ImageIO;

import nl.knokko.area.Area;
import nl.knokko.area.TileMap;
import nl.knokko.inventory.Inventory;
import nl.knokko.items.Item;
import nl.knokko.story.dialogue.Dialogue;
import nl.knokko.story.dialogue.DialogueFactory;
import nl.knokko.story.dialogue.DialoguePart;
import nl.knokko.story.dialogue.DialogueText;
import nl.knokko.texture.ImageTexture;
import nl.knokko.texture.ModelTexture;
import nl.knokko.texture.SizedTexture;
import nl.knokko.texture.Texture;
import nl.knokko.texture.area.TextureArea;
import nl.knokko.texture.builder.TextureBuilder;
import nl.knokko.texture.factory.MyTextureLoader;
import nl.knokko.texture.marker.TextureMarker;
import nl.knokko.texture.pattern.TexturePattern;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitInputStream;
import nl.knokko.util.color.Color;
import nl.knokko.util.color.ColorAlpha;

public final class Resources {
	
	private static final java.awt.Color INVENTORY_LABEL_COLOR = new java.awt.Color(200, 200, 50);
	
	public static BufferedImage loadImage(String fileName){
		try {
			return ImageIO.read(Resources.class.getClassLoader().getResource(("textures/" + fileName + ".png")).openStream());
		} catch(Exception ex){
			throw new RuntimeException("Failed to load image " + fileName, ex);
		}
	}
	
	public static BufferedImage loadPortrait(String portraitName){
		return loadImage("portraits/" + portraitName);
	}
	
	public static Dialogue loadDialogue(String dialogueName, int id){
		try {
			BitInput input = new BitInputStream(Resources.class.getClassLoader().getResourceAsStream("dialogues/" + dialogueName + ".dal"));
			Dialogue dialogue = DialogueFactory.loadFromBits(input, id);
			input.terminate();
			return dialogue;
		} catch (NullPointerException e) {
			throw new IllegalArgumentException("There is no dialogue with name " + dialogueName, e);
		}
	}
	
	public static Area loadArea(String name){
		InputStream input = Resources.class.getResourceAsStream("areas/" + name + ".area");
		ArrayList<Byte> data = readStream(input);
		return new Area(TileMap.load(data, 0, true));
	}
	
	public static TileMap loadTileMap(String name, boolean allowRemove){
		InputStream input = Resources.class.getClassLoader().getResourceAsStream("areas/" + name + ".area");
		byte[] data = Compressor.decompress(readByteArray(input));
		ArrayList<Byte> list = new ArrayList<Byte>(data.length);
		for(byte b : data)
			list.add(b);
		return TileMap.load(list, 0, allowRemove);
	}
	
	public static ArrayList<Byte> readStream(InputStream input){
		try {
			ArrayList<Byte> bytes = new ArrayList<Byte>();
			while(true){
				int next = input.read();
				if(next == -1)
					break;
				bytes.add((byte) next);
			}
			input.close();
			return bytes;
		} catch(Exception ex){
			throw new RuntimeException("Failed to read stream from " + input, ex);
		}
	}
	
	public static byte[] readByteArray(InputStream input){
		try {
			int index = 0;
			byte[] bytes = new byte[input.available()];
			while(true){
				int next = input.read();
				if(next == -1)
					break;
				if(index >= bytes.length){
					byte[] newBytes = new byte[bytes.length + 1 + input.available()];
					System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
					bytes = newBytes;
				}
				bytes[index] = (byte) next;
				index++;
			}
			return bytes;
		} catch(Exception ex){
			throw new RuntimeException("Failed to read stream from " + input, ex);
		}
	}
	
	public static String deepToString(Object object){
		return deepToString(object, new ArrayList<Object>());
	}
	
	public static String deepToString(Object object, List<Object> parents){
		if(object == null)
			return "null";
		if(object instanceof Boolean || object instanceof Number || object instanceof Character)
			return object.toString();
		if(object instanceof boolean[]){
			boolean[] array = (boolean[]) object;
			StringBuilder builder = new StringBuilder(2 + array.length);
			builder.append('[');
			for(boolean bool : array){
				if(bool)
					builder.append('1');
				else
					builder.append('0');
			}
			builder.append(']');
			return builder.toString();
		}
		if(object instanceof byte[])
			return Arrays.toString((byte[]) object);
		if(object instanceof short[])
			return Arrays.toString((short[]) object);
		if(object instanceof char[])
			return Arrays.toString((char[]) object);
		if(object instanceof int[])
			return Arrays.toString((int[]) object);
		if(object instanceof float[])
			return Arrays.toString((float[]) object);
		if(object instanceof long[])
			return Arrays.toString((long[]) object);
		if(object instanceof double[])
			return Arrays.toString((double[]) object);
		parents.add(object);
		if(object instanceof Object[]){
			StringBuilder builder = new StringBuilder(200);
			builder.append('[');
			Object[] objects = (Object[]) object;
			int index = 0;
			for(Object element : objects){
				String result = null;
				if(element == object)
					result = "*this";
				else {
					for(Object parent : parents)
						if(element == parent)
							result = "*parent";
					if(result == null)
						result = deepToString(element, parents);
				}
				builder.append(result);
				if(index != objects.length - 1)
					builder.append(", ");
				index++;
			}
			builder.append(']');
			return builder.toString();
		}
		try {
			StringBuilder builder = new StringBuilder(200);
			builder.append(object.getClass().getName() + "{");
			Field[] fields = getAllFields(object);
			int index = 0;
			for(Field field : fields){
				if(!Modifier.isStatic(field.getModifiers())){
					field.setAccessible(true);
					Object value = field.get(object);
					String result = null;
					if(value == object)
						result = "*this";
					else {
						for(Object parent : parents)
							if(value == parent)
								result = "*parent";
						if(result == null)
							result = deepToString(value, parents);
					}
					builder.append(field.getName() + ": " + result);
					if(index != fields.length - 1)
						builder.append(", ");
				}
				index++;
			}
			builder.append('}');
			return builder.toString();
		} catch(Exception ex){
			return "?: " + ex.getLocalizedMessage();
		}
	}
	
	private static Field[] getAllFields(Object object){
		List<Field> list = new ArrayList<Field>(100);
		getAllFields(object.getClass(), list);
		Field[] array = new Field[list.size()];
		list.toArray(array);
		return array;
	}
	
	private static void getAllFields(Class<?> type, List<Field> list){
		Field[] fields = type.getDeclaredFields();
		for(Field field : fields)
			list.add(field);
		Class<?> sup = type.getSuperclass();
		if(sup != null)
			getAllFields(sup, list);
	}
	
	/**
	 * @param textHeights If this is not null, the y coords of the text per index will be put in this array
	 */
	public static BufferedImage createDialogueImage(DialoguePart part, int[] textHeights, boolean addDummySpace){
		return createDialogueImage(part.getBackGround(), part.getPortrait(), part.getTitle(), 
				part.getText(), textHeights, addDummySpace);
	}
	
	public static void getTextHeights(DialogueText[] texts, int[] textHeights){
		int y = 540;
		int textIndex = 0;
		BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		for(DialogueText text : texts){
			g.setFont(text.getFont());
			textHeights[textIndex] = y;
			String textLine = text.getText();
			int lineIndex = 0;
			for(int length = 0; length + lineIndex < textLine.length(); length++){
				double width = g.getFontMetrics().getStringBounds(textLine.substring(lineIndex, lineIndex + length), g).getWidth();
				if(width > 700){
					//dont break words, that's ugly
					for(int i = length + lineIndex; i > 0; i--){
						if(textLine.charAt(i) == ' '){
							length = i + 1 - lineIndex;
							break;
						}
					}
					lineIndex += length;
					length = 0;
					y += text.getFont().getSize();
				}
			}
			y += text.getFont().getSize() * 1.4;
			textIndex++;
		}
		g.dispose();
	}
	
	/**
	 * @param textHeights If this is not null, the y coords of the text per index will be put in this array
	 */
	public static BufferedImage createDialogueImage(Color backGround, ImageTexture portrait, 
			DialogueText title, DialogueText[] texts, int[] textHeights, boolean addDummySpace){
		// Quick and dirty solution for something I shouldn't have done
		final int dummyHeight = 466;
		BufferedImage image = new BufferedImage(800, 800 - dummyHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setColor(backGround.toAWTColor());
		g.fillRect(0, 500 - dummyHeight, 800, 300);
		g.fillRect(34, 466 - dummyHeight, 766, 34);
		g.setColor(java.awt.Color.BLACK);
		g.drawRect(0, 500 - dummyHeight, 800, 300);
		g.drawRect(33, 466 - dummyHeight, 766, 34);
		g.drawRect(0, 466 - dummyHeight, 33, 33);
		g.drawImage(portrait.getImage(), 1, 467 - dummyHeight, 32, 32, null);
		g.setFont(title.getFont());
		g.setColor(title.getColor().toAWTColor());
		g.drawString(title.getText(), 40, 494 - dummyHeight);
		int textIndex = 0;
		int y = 540 - dummyHeight;
		for(DialogueText text : texts){
			g.setColor(text.getColor().toAWTColor());
			g.setFont(text.getFont());
			if(textHeights != null)
				textHeights[textIndex] = y;
			String textLine = text.getText();
			int lineIndex = 0;
			for(int length = 0; length + lineIndex < textLine.length(); length++){
				double width = g.getFontMetrics().getStringBounds(textLine.substring(lineIndex, lineIndex + length), g).getWidth();
				if(width > 700){
					//dont break words, that's ugly
					for(int i = length + lineIndex; i > 0; i--){
						if(textLine.charAt(i) == ' '){
							length = i + 1 - lineIndex;
							break;
						}
					}
					g.drawString(textLine.substring(lineIndex, lineIndex + length), 50, y);
					lineIndex += length;
					length = 0;
					y += text.getFont().getSize();
				}
			}
			g.drawString(textLine.substring(lineIndex), 50, y);
			y += text.getFont().getSize() * 1.4;
			textIndex++;
		}
		g.dispose();
		if (addDummySpace) {
			BufferedImage result = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
			g = result.createGraphics();
			g.drawImage(image, 0, dummyHeight, null);
			g.dispose();
			if (textHeights != null) {
				for (int index = 0; index < textHeights.length; index++) {
					textHeights[index] += dummyHeight;
				}
			}
			return result;
		}
		return image;
	}
	
	public static SizedTexture createInventoryTexture(Inventory inventory, List<Item> items){
		if(items.isEmpty()){
			/*
			BufferedImage image = new BufferedImage(160, 64, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.setFont(new Font("TimesRoman", Font.ITALIC, 25));
			g.setColor(INVENTORY_LABEL_COLOR);
			g.fillRect(0, 0, image.getWidth(), image.getHeight());
			g.setColor(Color.BLACK.toAWTColor());
			g.drawLine(0, 0, image.getWidth() - 1, 0);
			g.drawLine(0, image.getHeight() - 1, image.getWidth() - 1, image.getHeight() - 1);
			g.drawLine(0, 0, 0, image.getHeight() - 1);
			g.drawLine(image.getWidth() - 1, 0, image.getWidth() - 1, image.getHeight() - 1);
			g.drawLine(0, image.getHeight() - 1, image.getWidth() - 1, image.getHeight() - 1);
			g.drawString("There are no items", 1, 20);
			g.drawString("of this type.", 1, 45);
			g.dispose();
			return new SizedTexture(loadTexture(image, false), image.getWidth(), image.getHeight());
			*/
			return null;
		}
		BufferedImage image = new BufferedImage(160, items.size() * 64, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.setColor(INVENTORY_LABEL_COLOR);
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		g.setColor(Color.BLACK.toAWTColor());
		g.setFont(new Font("TimesRoman", Font.ITALIC, 40));
		g.drawLine(0, 0, image.getWidth() - 1, 0);
		g.drawLine(0, 0, 0, image.getHeight() - 1);
		g.drawLine(image.getWidth() - 1, 0, image.getWidth() - 1, image.getHeight() - 1);
		g.drawLine(0, image.getHeight() - 1, image.getWidth() - 1, image.getHeight() - 1);
		int i = 1;
		for(Item item : items){
			g.drawLine(0, i * 64, image.getWidth() - 1, i * 64);
			g.drawString(inventory.getAmount(item) + "", 10, i * 64 - 24);
			g.drawImage(item.getTexture().getImage(), 80, i * 64 - 64, 64, 64, null);
			i++;
		}
		g.dispose();
		return new SizedTexture(MyTextureLoader.loadTexture(image, false), image.getWidth(), image.getHeight());
	}
	
	public static Texture[] createThinPathTextures(int amount, Color color, float maxDifference){
		Texture[] textures = new Texture[amount];
		for(int i = 0; i < textures.length; i++)
			textures[i] = createThinPathTexture(color, maxDifference, i * 139429);
		return textures;
	}
	
	private static Texture createThinPathTexture(Color color, float maxDifference, long seed){
		TextureBuilder builder = MyTextureLoader.createTextureBuilder(32, 32, true);
		builder.geometry().fillRect(0, 0, builder.width() - 1, builder.height() - 1, ColorAlpha.TRANSPARENT);
		builder.average().fillAverageChance(0, 0, builder.width() - 1, builder.height() - 1, color, maxDifference, new Random(seed), 0.167);
		return new Texture(MyTextureLoader.loadTexture(builder));
	}
	
	public static Texture createLiquidTexture(Color color){
		TextureBuilder builder = MyTextureLoader.createTextureBuilder(8, 8, false);
		builder.geometry().fillRect(0, 0, builder.width() - 1, builder.height() - 1, color);
		return new Texture(MyTextureLoader.loadTexture(builder));
	}
	
	public static void createBluePrintImage(TextureMarker marker){
		BufferedImage image = new BufferedImage(marker.getWidth(), marker.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.setColor(java.awt.Color.RED);
		for(int i = 0; i < marker.getAmount(); i++){
			TextureArea ta = marker.getArea(i);
			g.drawRect(ta.getMinX(), ta.getMinY(), ta.getWidth(), ta.getHeight());
		}
		try {
			ImageIO.write(image, "PNG", new File("texture blue print.png"));
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static ModelTexture getTextureFromPattern(TexturePattern pattern, int width, int height, boolean alpha, float shineDamper, float reflectivity){
		TextureBuilder builder = MyTextureLoader.createTextureBuilder(width, height, alpha);
		pattern.paint(builder, 0, 0, width, height);
		return new ModelTexture(new Texture(MyTextureLoader.loadTexture(builder)), shineDamper, reflectivity);
	}
}