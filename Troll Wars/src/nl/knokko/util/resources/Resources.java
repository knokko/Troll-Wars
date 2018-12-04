/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2018 knokko
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

import static java.lang.Math.PI;
import static java.lang.Math.min;
import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;

import javax.imageio.ImageIO;

import nl.knokko.area.Area;
import nl.knokko.area.TileMap;
import nl.knokko.inventory.Inventory;
import nl.knokko.inventory.trading.ItemStack;
import nl.knokko.inventory.trading.TradeOffer;
import nl.knokko.items.Item;
import nl.knokko.main.Game;
import nl.knokko.main.GameScreen;
import nl.knokko.model.ModelPart;
import nl.knokko.model.ModelPartParent;
import nl.knokko.model.body.BodyBird;
import nl.knokko.model.body.BodyHuman;
import nl.knokko.model.body.BodyMyrre;
import nl.knokko.model.body.BodyTroll;
import nl.knokko.model.body.arm.HumanoidArm;
import nl.knokko.model.body.arm.MyrreArm;
import nl.knokko.model.body.belly.HumanoidBelly;
import nl.knokko.model.body.belly.MyrreBelly;
import nl.knokko.model.body.claw.BirdClaw;
import nl.knokko.model.body.claw.MyrreFootClaw;
import nl.knokko.model.body.claw.MyrreHandClaw;
import nl.knokko.model.body.foot.HumanoidFootProperties;
import nl.knokko.model.body.hand.HumanoidHandProperties;
import nl.knokko.model.body.head.BirdHead;
import nl.knokko.model.body.head.HeadProperties;
import nl.knokko.model.body.head.TrollHeadProperties;
import nl.knokko.model.body.leg.HumanoidLeg;
import nl.knokko.model.body.leg.MyrreLeg;
import nl.knokko.model.body.wing.BirdWing;
import nl.knokko.model.equipment.boots.ModelArmorFoot;
import nl.knokko.model.equipment.chestplate.ModelArmorBelly;
import nl.knokko.model.equipment.chestplate.ModelArmorUnderArm;
import nl.knokko.model.equipment.chestplate.ModelArmorUpperArm;
import nl.knokko.model.equipment.hands.ModelArmorGlobe;
import nl.knokko.model.equipment.helmet.ModelArmorHead;
import nl.knokko.model.equipment.pants.ModelArmorUnderLeg;
import nl.knokko.model.equipment.pants.ModelArmorUpperLeg;
import nl.knokko.model.equipment.weapon.ModelBone;
import nl.knokko.model.equipment.weapon.ModelSpear;
import nl.knokko.model.equipment.weapon.ModelSword;
import nl.knokko.model.type.AbstractModel;
import nl.knokko.model.type.DefaultModel;
import nl.knokko.model.type.DefaultTileModel;
import nl.knokko.model.type.GuiModel;
import nl.knokko.model.type.LiquidModel;
import nl.knokko.model.type.LiquidTileModel;
import nl.knokko.model.type.SimpleEffectModel;
import nl.knokko.model.type.SpiritModel;
import nl.knokko.story.dialogue.Dialogue;
import nl.knokko.story.dialogue.DialogueFactory;
import nl.knokko.story.dialogue.DialoguePart;
import nl.knokko.story.dialogue.DialogueText;
import nl.knokko.texture.ImageTexture;
import nl.knokko.texture.ModelTexture;
import nl.knokko.texture.SizedTexture;
import nl.knokko.texture.Texture;
import nl.knokko.texture.area.TextureArea;
import nl.knokko.texture.equipment.ArmorTexture;
import nl.knokko.texture.equipment.SpearTexture;
import nl.knokko.texture.equipment.SwordTexture;
import nl.knokko.texture.marker.TextureMarker;
import nl.knokko.texture.marker.TextureMarkerBird;
import nl.knokko.texture.marker.TextureMarkerBone;
import nl.knokko.texture.marker.TextureMarkerHuman;
import nl.knokko.texture.marker.TextureMarkerMyrre;
import nl.knokko.texture.marker.TextureMarkerSpear;
import nl.knokko.texture.marker.TextureMarkerSword;
import nl.knokko.texture.marker.TextureMarkerTroll;
import nl.knokko.texture.painter.BirdPainter;
import nl.knokko.texture.painter.BonePainter;
import nl.knokko.texture.painter.HumanPainter;
import nl.knokko.texture.painter.MyrrePainter;
import nl.knokko.texture.painter.SpearPainter;
import nl.knokko.texture.painter.SwordPainter;
import nl.knokko.texture.painter.TrollPainter;
import nl.knokko.texture.pattern.TexturePattern;
import nl.knokko.util.Maths;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitInputStream;
import nl.knokko.util.collection.floating.ArrayFloatList;
import nl.knokko.util.collection.floating.FloatList;
import nl.knokko.util.color.Color;
import nl.knokko.util.color.ColorAlpha;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.TextureLoader;

public final class Resources {
	
	private static final java.awt.Color INVENTORY_LABEL_COLOR = new java.awt.Color(200, 200, 50);
	private static final java.awt.Color TRADE_OFFER_COLOR = new java.awt.Color(200, 150, 150);
	private static final Font TRADE_OFFER_FONT = new Font("TimesRoman", Font.ITALIC, 40);
	
	private static final float BUTTON_BORDER_FACTOR = 0.05f;
	private static final Color GREEN_GRASS_COLOR = new Color(100, 200, 50);
	//private static final float CHAR_SIZE = 0.1f;
	
	private static ArrayList<Integer> vaos = new ArrayList<Integer>();
	private static ArrayList<Integer> vbos = new ArrayList<Integer>();
	private static ArrayList<Integer> textures = new ArrayList<Integer>();
	
	private static Map<TextTexture,Texture> textTextureMap = new TreeMap<TextTexture,Texture>();
	
	private static ModelTexture WHITE;
	private static final BufferedImage TRADE_ARROW = createTradeArrow();
	
	public static GuiModel loadGuiModel(float[] positions){
		int vaoID = createVAO();
		storeDataInAttributeList(0, 2, positions);
		unbindVAO();
		return new GuiModel(vaoID, positions.length / 2);
	}
	
	public static DefaultModel loadDefaultModel(float[] positions, float[] textureCoords, float[] normals, int[] indices){
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textureCoords);
		storeDataInAttributeList(2, 3, normals);
		unbindVAO();
		return new DefaultModel(vaoID, indices.length);
	}
	
	public static LiquidModel loadLiquidModel(float[] positions, float[] textureCoords, int[] indices){
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textureCoords);
		unbindVAO();
		return new LiquidModel(vaoID, indices.length);
	}
	
	public static SpiritModel loadSpiritModel(float[] positions, float[] textureCoords, int[] indices){
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textureCoords);
		unbindVAO();
		return new SpiritModel(vaoID, indices.length);
	}
	
	public static SimpleEffectModel loadSimpleEffectModel(float[] positions, int[] indices){
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		unbindVAO();
		return new SimpleEffectModel(vaoID, indices.length);
	}
	
	private static final Vector3f LD = new Vector3f(0, -1, 0);
	private static final Vector3f UP = new Vector3f(0, 1, 0);
	
	public static DefaultTileModel loadDefaultTileModel(float[] positions, float[] textureCoords, float[] normals, int[] indices){
		float[] reflectedLightDirections = new float[positions.length];
		float[] brightnesses = new float[positions.length / 3];
		for(int i = 0; i < positions.length; i += 3){
			positions[i] *= 32;
			positions[i + 1] *= 16;
			positions[i + 1] -= 8;
			positions[i + 2] *= 32;
		}
		for(int i = 0; i < brightnesses.length; i++){
			Vector3f normal = new Vector3f(normals[i * 3], normals[i * 3 + 1], normals[i * 3 + 2]);
			float dot = Vector3f.dot(normal, LD);
			reflectedLightDirections[i * 3] = LD.x - 2 * dot * normal.x;
			reflectedLightDirections[i * 3 + 1] = LD.y - 2 * dot * normal.y;
			reflectedLightDirections[i * 3 + 2] = LD.z - 2 * dot * normal.z;
			brightnesses[i] = Math.max(0.5f, Vector3f.dot(normal, UP));
		}
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textureCoords);
		storeDataInAttributeList(2, 3, reflectedLightDirections);
		storeDataInAttributeList(3, 1, brightnesses);
		unbindVAO();
		return new DefaultTileModel(vaoID, indices.length);
	}
	
	public static LiquidTileModel loadLiquidTileModel(float[] positions, float[] textureCoords, int[] indices){
		for(int i = 0; i < positions.length; i += 3){
			positions[i] *= 32;
			positions[i + 1] *= 16;
			positions[i + 1] -= 8;
			positions[i + 2] *= 32;
		}
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textureCoords);
		unbindVAO();
		return new LiquidTileModel(vaoID, indices.length);
	}
	
	public static DefaultModel loadDefaultModel(FloatList vertices, FloatList normals, FloatList textures, ArrayList<Integer> indices){
		int[] indiceArray = new int[indices.size()];
		for(int i = 0; i < indices.size(); i++)
			indiceArray[i] = indices.get(i);
		return loadDefaultModel(vertices.getArray(), textures.getArray(), normals.getArray(), indiceArray);
	}
	
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
	
	public static Texture[] loadGuiMoveTexture(String moveName){
		Texture[] textures = new Texture[3];
		String pre = "gui/moves/";
		textures[0] = new Texture(loadTexture(pre + "learned" + File.separator + moveName));
		textures[1] = new Texture(loadTexture(pre + "notlearned" + File.separator + moveName));
		textures[2] = new Texture(loadTexture(pre + "cantlearn" + File.separator + moveName));
		return textures;
	}
	
	public static int loadTexture(String fileName){
		org.newdawn.slick.opengl.Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", Resources.class.getClassLoader().getResource("textures/" + fileName + ".png").openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		int id = texture.getTextureID();
		textures.add(id);
		return id;
	}
	
	public static int loadTexture(byte[] data, int width, int height, boolean alpha){
		 int textureID = glGenTextures();
		 glBindTexture(GL_TEXTURE_2D, textureID);
		 glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		 glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		 glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);//TODO maybe reset to GL_NEAREST
		 glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		 glTexImage2D(GL_TEXTURE_2D, 0, alpha ? GL_RGBA8 : GL_RGB8, width, height, 0, alpha ? GL_RGBA : GL_RGB, GL_UNSIGNED_BYTE, (ByteBuffer)BufferUtils.createByteBuffer(data.length).put(data).flip());
		 textures.add(textureID);
		 return textureID;
	}
	
	public static int loadTexture(BufferedImage image, boolean allowAlpha){
		int height = image.getHeight();
		int width = image.getWidth();
	    ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * (allowAlpha ? 4 : 3)); //4 for RGBA, 3 for RGB
	    for(int y = 0; y < height; y++){
	        for(int x = 0; x < width; x++){
	        	int rgb = image.getRGB(x, y);
	        	buffer.put((byte) (rgb >> 16));
	        	buffer.put((byte) (rgb >> 8));
	        	buffer.put((byte) (rgb >> 0));
	        	if(allowAlpha)
	        		buffer.put((byte) (rgb >> 24));
	        }
	    }
	    buffer.flip();
	    int textureID = glGenTextures();
	    glBindTexture(GL_TEXTURE_2D, textureID);
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	    glTexImage2D(GL_TEXTURE_2D, 0, allowAlpha ? GL_RGBA8 : GL_RGB8, width, height, 0, allowAlpha ? GL_RGBA : GL_RGB, GL_UNSIGNED_BYTE, buffer);
	    textures.add(textureID);
	    return textureID;
	}
	
	public static void cleanUp(){
		for(int vao : vaos)
			GL30.glDeleteVertexArrays(vao);
		for(int vbo : vbos)
			GL15.glDeleteBuffers(vbo);
		for(int texture : textures)
			GL11.glDeleteTextures(texture);
	}
	
	public static Color getDifColor(Random random, Color basic, float maxDifference){
    	//float factor = 1 - maxDifference + random.nextFloat() * maxDifference * 2;
    	return getMultipliedColor(basic, 1 - maxDifference + random.nextFloat() * maxDifference * 2);
		//return new GuiColor(Math.max(Math.min((int)(basic.getRedI() * factor), 255), 0), Math.max(Math.min((int)(basic.getGreenI() * factor), 255), 0), Math.max(Math.min((int)(basic.getBlueI() * factor), 255), 0));
    }
	
	public static Color getMultipliedColor(Color basic, float factor){
		return new Color(Math.max(Math.min((int)(basic.getRedI() * factor), 255), 0), Math.max(Math.min((int)(basic.getGreenI() * factor), 255), 0), Math.max(Math.min((int)(basic.getBlueI() * factor), 255), 0));
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
	
	private static int createVAO(){
		int vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		vaos.add(vaoID);
		return vaoID;
	}
	
	private static void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data){
		int vboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		vbos.add(vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private static void unbindVAO(){
		GL30.glBindVertexArray(0);
	}
	
	private static void bindIndicesBuffer(int[] indices){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	private static IntBuffer storeDataInIntBuffer(int[] data){
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private static FloatBuffer storeDataInFloatBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private static int addVertex(ModelBuilder data, float x, float y, float z, float nx, float ny, float nz, float u, float v){
		data.vertices.addAll(x, y, z);
		data.normals.addAll(nx, ny, nz);
		data.textures.addAll(u, v);
		return data.vertices.size() / 3 - 1;
	}
	
	private static void addVerticalCilinder(ModelBuilder builder, int flatParts, float centreX, float centreZ, float radiusX, float radiusZ, float minY, float maxY, TextureArea area, int tw, int th){
		addVerticalCilinder(builder, flatParts, centreX, centreZ, radiusX, radiusZ, minY, maxY, area.getMinU(tw), area.getMinV(th), area.getMaxU(tw), area.getMaxV(th));
	}
	
	private static void addVerticalCilinder(ModelBuilder builder, int flatParts, float centreX, float centreZ, float radiusX, float radiusZ, float minY, float maxY, float minU, float minV, float maxU, float maxV){
		int index = builder.vertices.size() / 3;
		float deltaU = maxU - minU;
		for(int i = 0; i <= flatParts; i++){
			float angle = i * 360f / flatParts;
			float x = centreX + Maths.sin(angle) * radiusX;
			float z = centreZ - Maths.cos(angle) * radiusZ;
			addVertex(builder, x, minY, z, Maths.sin(angle), 0, Maths.cos(angle), minU + deltaU * i / flatParts, minV);
			addVertex(builder, x, maxY, z, Maths.sin(angle), 0, Maths.cos(angle), minU + deltaU * i / flatParts, maxV);
		}
		for(int i = 0; i < flatParts; i++)
			bindFourangle(builder, index + 2 * i, index + 2 * i + 1, index + 2 * i + 3, index + 2 * i + 2);
	}
	
	private static void addVerticalCilinder(ModelBuilder builder, int flatParts, float centreX, float centreZ, float radiusX1, float radiusZ1, float radiusX2, float radiusZ2, float y1, float y2, float minU, float v1, float maxU, float v2){
		int index = builder.vertices.size() / 3;
		float deltaU = maxU - minU;
		for(int i = 0; i <= flatParts; i++){
			float angle = i * 360f / flatParts;
			addVertex(builder, centreX + Maths.sin(angle) * radiusX1, y1, centreZ - Maths.cos(angle) * radiusZ1, Maths.sin(angle), 0, Maths.cos(angle), minU + deltaU * i / flatParts, v1);
			addVertex(builder, centreX + Maths.sin(angle) * radiusX2, y2, centreZ - Maths.cos(angle) * radiusZ2, Maths.sin(angle), 0, Maths.cos(angle), minU + deltaU * i / flatParts, v2);
		}
		for(int i = 0; i < flatParts; i++)
			bindFourangle(builder, index + 2 * i, index + 2 * i + 1, index + 2 * i + 3, index + 2 * i + 2);
	}
	
	private static void addHorizontalCilinder(ModelBuilder builder, int flatParts, float centreX, float centreY, float radiusX1, float radiusY1, float radiusX2, float radiusY2, float z1, float z2, float minU, float v1, float maxU, float v2){
		int index = builder.vertices.size() / 3;
		float deltaU = maxU - minU;
		for(int i = 0; i <= flatParts; i++){
			float angle = i * 360f / flatParts;
			addVertex(builder, centreX + Maths.cos(angle) * radiusX1, centreY + Maths.sin(angle) * radiusY1, z1, Maths.cos(angle), Maths.sin(angle), 0f, minU + deltaU * i / flatParts, v1);
			addVertex(builder, centreX + Maths.cos(angle) * radiusX2, centreY + Maths.sin(angle) * radiusY2, z2, Maths.cos(angle), Maths.sin(angle), 0f, minU + deltaU * i / flatParts, v2);
		}
		for(int i = 0; i < flatParts; i++)
			bindFourangle(builder, index + 2 * i, index + 2 * i + 1, index + 2 * i + 3, index + 2 * i + 2);
	}
	
	private static void addBoneSphere(ModelBuilder builder, ModelBone bone, Random random, float scale, float centreX, float centreY, float centreZ, float minU, float minV, float maxU, float maxV, int partsY, int partsR){
		float deltaU = maxU - minU;
		float deltaV = maxV - minV;
		int index = builder.vertices.size() / 3;
		for(int i = 0; i <= partsY; i++){
			float angleI = Maths.asin((float)i / partsY * 2 - 1);
			float c = Maths.cos(angleI);
			float s = Maths.sin(angleI);
			for(int j = 0; j <= partsR; j++){
				float angleJ = (float)j / partsR * 360f;
				addVertex(builder, centreX + Maths.cos(angleJ) * c * bone.boneRadiusTopX() * scale * (1 - bone.maxRandomFactor() * 0.5f + 1.0f * random.nextFloat() * bone.maxRandomFactor()), centreY + s * bone.boneRadiusTopY() * scale, centreZ + Maths.sin(angleJ) * c * bone.boneRadiusTopZ() * scale * (1 - bone.maxRandomFactor() * 0.5f + 1.0f * random.nextFloat() * bone.maxRandomFactor()), Maths.cos(angleJ) * c, s, Maths.sin(angleJ) * c, minU + (float)j / partsR * deltaU, minV + (float)i / partsR * deltaV);
			}
		}
		for(int i = 0; i <= partsY; i++)
			for(int j = 0; j <= partsR; j++)
				bindFourangle(builder, index + j * partsR + i, index + j * partsR + i + 1, index + (j + 1) * partsR + i + 1, index + (j + 1) * partsR + i);
	}
	
	private static void addSphere(ModelBuilder builder, int parts, float centreX, float centreY, float centreZ, float radiusX, float radiusY, float radiusZ, float minU, float minV, float maxU, float maxV){
		float deltaU = maxU - minU;
		float deltaV = maxV - minV;
		int index = builder.vertices.size() / 3;
		for(int i = 0; i <= parts; i++){
			float angleI = Maths.asin((float)i / parts * 2 - 1);
			float c = Maths.cos(angleI);
			float s = Maths.sin(angleI);
			for(int j = 0; j <= parts; j++){
				float angleJ = (float)j / parts * 360f;
				addVertex(builder, centreX + Maths.cos(angleJ) * c * radiusX, centreY + s * radiusY, centreZ + Maths.sin(angleJ) * c * radiusZ, Maths.cos(angleJ) * c, s, Maths.sin(angleJ) * c, minU + (float)j / parts * deltaU, minV + (float)i / parts * deltaV);
			}
		}
		for(int i = 0; i <= parts; i++)
			for(int j = 0; j <= parts; j++)
				bindFourangle(builder, index + j * parts + i, index + j * parts + i + 1, index + (j + 1) * parts + i + 1, index + (j + 1) * parts + i);
	}
	
	private static void addSphereTop(ModelBuilder builder, int parts, float centreX, float centreY, float centreZ, float radiusX, float radiusY, float radiusZ, float minU, float minV, float maxU, float maxV){
		float deltaU = maxU - minU;
		float deltaV = maxV - minV;
		int index = builder.vertices.size() / 3;
		for(int i = 0; i <= parts; i++){
			float angleI = Maths.asin((float)i / parts);
			float c = Maths.cos(angleI);
			float s = Maths.sin(angleI);
			for(int j = 0; j <= parts; j++){
				float angleJ = (float)j / parts * 360f;
				addVertex(builder, centreX + Maths.cos(angleJ) * c * radiusX, centreY + s * radiusY, centreZ + Maths.sin(angleJ) * c * radiusZ, Maths.cos(angleJ) * c, s, Maths.sin(angleJ) * c, minU + (float)j / parts * deltaU, minV + (float)i / parts * deltaV);
			}
		}
		for(int i = 0; i <= parts; i++)
			for(int j = 0; j <= parts; j++)
				bindFourangle(builder, index + j * parts + i, index + j * parts + i + 1, index + (j + 1) * parts + i + 1, index + (j + 1) * parts + i);
	}
	
	private static void addSphereSouth(ModelBuilder builder, int parts, float centreX, float centreY, float centreZ, float radiusX, float radiusY, float radiusZ, float minU, float minV, float maxU, float maxV){
		float deltaU = maxU - minU;
		float deltaV = maxV - minV;
		int index = builder.vertices.size() / 3;
		for(int i = 0; i <= parts; i++){
			float angleI = Maths.asin((float)i / parts * 2 - 1);
			float c = Maths.cos(angleI);
			float s = Maths.sin(angleI);
			for(int j = 0; j <= parts; j++){
				float angleJ = 0 + (float)j / parts * 180f;
				addVertex(builder, centreX + Maths.cos(angleJ) * c * radiusX, centreY + s * radiusY, centreZ + Maths.sin(angleJ) * c * radiusZ, Maths.cos(angleJ) * c, s, Maths.sin(angleJ) * c, minU + (float)j / parts * deltaU, minV + (float)i / parts * deltaV);
			}
		}
		for(int i = 0; i <= parts; i++)
			for(int j = 0; j <= parts; j++)
				bindFourangle(builder, index + j * parts + i, index + j * parts + i + 1, index + (j + 1) * parts + i + 1, index + (j + 1) * parts + i);
	}
	
	@SuppressWarnings("unused")
	private static void addSmoothTopsCilinder(ModelBuilder builder, int flatParts, float centreX, float centreZ, float radiusX, float radiusZ, float minY, float minCilY, float maxY, float maxCilY, float minU, float minV, float maxU, float maxV){
		int index = builder.vertices.size() / 3;
		float deltaV = (maxV - minV) * 0.5f;
		float midV = (minV + maxV) / 2;
		float midY = (minY + maxY) / 2;
		float midCilY = (minCilY + maxCilY) / 2;
		addVerticalCilinder(builder, flatParts, centreX, centreZ, radiusX, radiusZ, minCilY, maxCilY, minU, midV - deltaV * ((midCilY - minCilY) / (midY - minY)), maxU, midV + deltaV * ((maxCilY - midCilY) / (maxY - midY)));
		float midU = (minU + maxU) / 2;
		int indexTop = builder.vertices.size() / 3;
		addVertex(builder, centreX, maxY, centreZ, 0f, 1f, 0f, midU, maxV);
		addVertex(builder, centreX, minY, centreZ, 0f, -1f, 0f, midU, minV);
		for(int i = 0; i < flatParts; i++){
			bindTriangle(builder, index + 2 * i, index + 2 * i + 2, indexTop + 1);
			bindTriangle(builder, index + 2 * i + 1, index + 2 * i + 3, indexTop);
		}
	}
	
	private static void addBox(ModelBuilder builder, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, float minU, float minV, float maxU, float maxV){
		float deltaU = maxU - minU;
		float deltaV = maxV - minV;
		float deltaX = maxX - minX;
		float deltaY = maxY - minY;
		float deltaZ = maxZ - minZ;
		float sepU1 = deltaX / (2 * deltaX + 2 * deltaZ) * deltaU + minU;
		float sepU2 = (deltaX + deltaZ) / (2 * deltaX + 2 * deltaZ) * deltaU + minU;
		float sepU3 = (2 * deltaX + deltaZ) / (2 * deltaX + 2 * deltaZ) * deltaU + minU;
		float midU = minU + deltaU / 2;
		float sepV = deltaY / (deltaY + deltaZ) * deltaV + minV;
		addPlane(builder, 0, 0, -1, minX, minY, minZ, minU, minV, maxX, minY, minZ, sepU1, minV, maxX, maxY, minZ, sepU1, sepV, minX, maxY, minZ, minU, sepV);//north
		addPlane(builder, 1, 0, 0, maxX, minY, minZ, sepU1, minV, maxX, minY, maxZ, sepU2, minV, maxX, maxY, maxZ, sepU2, sepV, maxX, maxY, minZ, sepU1, sepV);//east
		addPlane(builder, 0, 0, 1, maxX, minY, maxZ, sepU2, minV, minX, minY, maxZ, sepU3, minV, minX, maxY, maxZ, sepU3, sepV, maxX, maxY, maxZ, sepU2, sepV);//south
		addPlane(builder, -1, 0, 0, minX, minY, maxZ, sepU3, minV, minX, minY, minZ, maxU, minV, minX, maxY, minZ, maxU, sepV, minX, maxY, maxZ, sepU3, sepV);//west
		addPlane(builder, 0, 1, 0, minX, maxY, maxZ, minU, sepV, maxX, maxY, maxZ, midU, sepV, maxX, maxY, minZ, midU, maxV, minX, maxY, minZ, minU, maxV);//up
		addPlane(builder, 0, -1, 0, minX, minY, maxZ, minU, sepV, maxX, minY, maxZ, midU, sepV, maxX, minY, minZ, midU, maxV, minX, minY, minZ, minU, maxV);//down
	}
	
	private static void addFourangle(ModelBuilder data, TextureArea area, int tw, int th, 
			float x1, float y1, float z1, float nx1, float ny1, float nz1,
			float x2, float y2, float z2, float nx2, float ny2, float nz2,
			float x3, float y3, float z3, float nx3, float ny3, float nz3,
			float x4, float y4, float z4, float nx4, float ny4, float nz4){
		addFourangle(data, x1, y1, z1, nx1, ny1, nz1, area.getMinU(tw), area.getMinV(th), x2, y2, z2, nx2, ny2, nz2, area.getMaxU(tw), area.getMinV(th),
				x3, y3, z3, nx3, ny3, nz3, area.getMaxU(tw), area.getMaxV(th), x4, y4, z4, nx4, ny4, nz4, area.getMinU(tw), area.getMaxV(th));
	}
	
	private static void addFourangle(ModelBuilder data, float x1, float y1, float z1, float nx1, float ny1, float nz1, float u1, float v1, float x2, float y2, float z2, float nx2, float ny2, float nz2, float u2, float v2, float x3, float y3, float z3, float nx3, float ny3, float nz3, float u3, float v3, float x4, float y4, float z4, float nx4, float ny4, float nz4, float u4, float v4){
		addVertex(data, x1, y1, z1, nx1, ny1, nz1, u1, v1);
		addVertex(data, x2, y2, z2, nx2, ny2, nz2, u2, v2);
		addVertex(data, x3, y3, z3, nx3, ny3, nz3, u3, v3);
		addVertex(data, x4, y4, z4, nx4, ny4, nz4, u4, v4);
		bindFourangle(data);
	}
	
	private static void addPlane(ModelBuilder data, TextureArea area, int tw, int th, float nx, float ny, float nz, float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4){
		addPlane(data, nx, ny, nz, x1, y1, z1, area.getMinU(tw), area.getMinV(th), x2, y2, z2, area.getMaxU(tw), area.getMinV(th), x3, y3, z3, area.getMaxU(tw), area.getMaxV(th), x4, y4, z4, area.getMinU(tw), area.getMaxV(th));
	}
	
	private static void addPlane(ModelBuilder data, float nx, float ny, float nz, float x1, float y1, float z1, float u1, float v1, float x2, float y2, float z2, float u2, float v2, float x3, float y3, float z3, float u3, float v3, float x4, float y4, float z4, float u4, float v4){
		addFourangle(data, x1, y1, z1, nx, ny, nz, u1, v1, x2, y2, z2, nx, ny, nz, u2, v2, x3, y3, z3, nx, ny, nz, u3, v3, x4, y4, z4, nx, ny, nz, u4, v4);
	}
	
	private static void addPlaneTriangle(ModelBuilder data, float nx, float ny, float nz,
			float x1, float y1, float z1, float u1, float v1,
			float x2, float y2, float z2, float u2, float v2,
			float x3, float y3, float z3, float u3, float v3){
		addTriangle(data, x1, y1, z1, nx, ny, nz, u1, v1, x2, y2, z2, nx, ny, nz, u2, v2, x3, y3, z3, nx, ny, nz, u3, v3);
	}
	
	private static void addTriangle(ModelBuilder data, float x1, float y1, float z1, float nx1, float ny1, float nz1, float u1, float v1, float x2, float y2, float z2, float nx2, float ny2, float nz2, float u2, float v2, float x3, float y3, float z3, float nx3, float ny3, float nz3, float u3, float v3){
		addVertex(data, x1, y1, z1, nx1, ny1, nz1, u1, v1);
		addVertex(data, x2, y2, z2, nx2, ny2, nz2, u2, v2);
		addVertex(data, x3, y3, z3, nx3, ny3, nz3, u3, v3);
		bindTriangle(data);
	}
	
	private static void bindTriangle(ModelBuilder data){
		int i = data.vertices.size() / 3;
		bindTriangle(data, i - 2, i - 1, i);
	}
	
	private static void bindTriangle(ModelBuilder data, int indice1, int indice2, int indice3){
		data.indices.add(indice1);
		data.indices.add(indice2);
		data.indices.add(indice3);
	}
	
	private static void bindFourangle(ModelBuilder builder, int indice1, int indice2, int indice3, int indice4){
		bindTriangle(builder, indice1, indice2, indice3);
		bindTriangle(builder, indice1, indice4, indice3);
	}
	
	private static void bindFourangle2(ModelBuilder builder, int indice1, int indice2, int indice3, int indice4){
		bindTriangle(builder, indice1, indice2, indice4);
		bindTriangle(builder, indice1, indice3, indice4);
	}
	
	private static void bindFourangle(ModelBuilder data){
		int i = data.vertices.size() / 3;
		bindFourangle(data, i - 4, i - 3, i - 2, i - 1);
	}
	
	private static void mirrorX(ModelBuilder data){
		for(int i = 0; i < data.vertices.size(); i += 3)
			data.vertices.set(i, -data.vertices.get(i));
		for(int i = 0; i < data.normals.size(); i+= 3)
			data.normals.set(i, -data.normals.get(i));
	}
	
	@SuppressWarnings("unused")
	private static void mirrorY(ModelBuilder data){
		for(int i = 1; i < data.vertices.size(); i += 3)
			data.vertices.set(i, -data.vertices.get(i));
		for(int i = 1; i < data.normals.size(); i+= 3)
			data.normals.set(i, -data.normals.get(i));
	}
	
	@SuppressWarnings("unused")
	private static void mirrorZ(ModelBuilder data){
		for(int i = 2; i < data.vertices.size(); i += 3)
			data.vertices.set(i, -data.vertices.get(i));
		for(int i = 2; i < data.normals.size(); i+= 3)
			data.normals.set(i, -data.normals.get(i));
	}
	
	public static ModelPart createModelArmorHelmet(ModelArmorHead armor, HeadProperties head, ArmorTexture at){
		float f = armor.distanceToHeadFactor();
		TextureMarker marker = new TextureMarker(TextureMarker.createSphere(head.headHeight() * f, head.headWidth() * f, head.headDepth() * f, Game.getOptions().pixelsPerMeter));
		TextureArea area = marker.getArea(0);
		ModelBuilder builder = new ModelBuilder();
		int tw = marker.getWidth();
		int th = marker.getHeight();
		TextureBuilder textureBuilder = new TextureBuilder(tw, th, true);
		at.getHelmet().paint(textureBuilder, area);
		float radiusX = head.headWidth() * armor.distanceToHeadFactor() / 2;
		float radiusY = head.headHeight() * armor.distanceToHeadFactor() / 2;
		float radiusZ = head.headDepth() * armor.distanceToHeadFactor() / 2;
		addSphere(builder, 20, 0, head.headHeight() / 2, 0, radiusX, radiusY, radiusZ, area.getMinU(tw), area.getMinV(th), area.getMaxU(tw), area.getMaxV(th));
		return new ModelPart(builder.load(), new ModelTexture(new Texture(textureBuilder.loadNormal()), at.getShineDamper(), at.getReflectivity()), new Vector3f());
	}
	
	public static ModelPart createModelArmorGlobe(ModelArmorGlobe armor, HumanoidHandProperties hand, ArmorTexture at, boolean left){
		float factor = armor.distanceToHandFactor();
		TextureMarker marker = new TextureMarker(new TextureArea(2 * factor * (hand.handHeight() + hand.handWidth()) * Game.getOptions().pixelsPerMeter, (hand.handCoreLength() + hand.fingerLength()) * factor * Game.getOptions().pixelsPerMeter));
		TextureArea ta = marker.getArea(0);
		ModelBuilder builder = new ModelBuilder();
		int tw = marker.getWidth();
		int th = marker.getHeight();
		TextureBuilder textureBuilder = new TextureBuilder(tw, th, true);
		at.getGlobe().paint(textureBuilder, ta);
		float minU = ta.getMinU(tw);
		float minV = ta.getMinV(th);
		float maxU = ta.getMaxU(tw);
		float maxV = ta.getMaxV(th);
		float deltaU = maxU - minU;
		float deltaV = maxV - minV;
		float vEdge = minV + deltaV * (hand.handCoreLength() / (hand.handCoreLength() + hand.fingerLength()));
		float uLeftTop = minU;
		float handCircle = hand.handWidth() * 2 + hand.handHeight() * 2;
		float uRightTop = minU + deltaU * (hand.handWidth() / handCircle);
		float uRightBottom = minU + deltaU * 0.5f;
		float uLeftBottom = minU + deltaU * ((2 * hand.handWidth() + hand.handHeight()) / handCircle);
		Vector3f lu = (Vector3f) new Vector3f(-1, 1, 0).normalise();
		float hw = factor * hand.handWidth() / 2;
		float hh = factor * hand.handHeight() / 2;
		float hcl = factor * hand.handCoreLength();
		int indexBack = 0;
		addVertex(builder, -hw, hh, 0, lu.x, lu.y, lu.z, uLeftTop, minV);//left top
		addVertex(builder, hw, hh, 0, -lu.x, lu.y, lu.z, uRightTop, minV);//right top
		addVertex(builder, hw, -hh, 0, -lu.x, -lu.y, lu.z, uRightBottom, minV);//right bottom
		addVertex(builder, -hw, -hh, 0, lu.x, -lu.y, lu.z, uLeftBottom, minV);//left bottom
		addVertex(builder, -hw, hh, 0, lu.x, lu.y, lu.z, maxU, minV);//left top 2
		int indexEdge = builder.vertices.size() / 3;
		addVertex(builder, -hw, hh, -hcl, lu.x, lu.y, lu.z, uLeftTop, vEdge);//left top
		addVertex(builder, hw, hh, -hcl, -lu.x, lu.y, lu.z, uRightTop, vEdge);//right top
		addVertex(builder, hw, -hh, -hcl, -lu.x, -lu.y, lu.z, uRightBottom, vEdge);//right bottom
		addVertex(builder, -hw, -hh, -hcl, lu.x, -lu.y, lu.z, uLeftBottom, vEdge);//left bottom
		addVertex(builder, -hw, hh, -hcl, lu.x, lu.y, lu.z, maxU, vEdge);//left top 2
		float angle = 30f;
		float fl = factor * hand.fingerLength() / 3;
		float sfl = Maths.sin(angle) * fl;
		float cfl = Maths.cos(angle) * fl;
		float sfl2 = Maths.sin(angle + angle) * fl;
		float cfl2 = Maths.cos(angle + angle) * fl;
		float sfl3 = Maths.sin(angle + angle + angle) * fl;
		float cfl3 = Maths.cos(angle + angle + angle) * fl;
		float vf1 = minV + deltaV * ((hcl + fl) / (hcl + hand.fingerLength()));
		float vf2 = minV + deltaV * ((hcl + fl + fl) / (hcl + hand.fingerLength()));
		float vf3 = maxV;
		int indexFinger1 = builder.vertices.size() / 3;
		addVertex(builder, -hw - sfl, hh, -hcl - cfl, lu.x, lu.y, lu.z, uLeftTop, vf1);//left top finger 1
		addVertex(builder, hw - sfl, hh, -hcl - cfl, -lu.x, lu.y, lu.z, uRightTop, vf1);//right top finger 1
		addVertex(builder, hw - sfl, -hh, -hcl - cfl, -lu.x, -lu.y, lu.z, uRightBottom, vf1);//right bottom finger 1
		addVertex(builder, -hw - sfl, -hh, -hcl - cfl, lu.x, -lu.y, lu.z, uLeftBottom, vf1);//left bottom finger 1
		addVertex(builder, -hw - sfl, hh, -hcl - cfl, lu.x, lu.y, lu.z, maxU, vf1);//left top finger 1 (2)
		int indexFinger2 = builder.vertices.size() / 3;
		addVertex(builder, -hw - sfl - sfl2, hh, -hcl - cfl - cfl2, lu.x, lu.y, lu.z, uLeftTop, vf2);//left top finger 2
		addVertex(builder, hw - sfl - sfl2, hh, -hcl - cfl - cfl2, -lu.x, lu.y, lu.z, uRightTop, vf2);//right top finger 2
		addVertex(builder, hw - sfl - sfl2, -hh, -hcl - cfl - cfl2, -lu.x, -lu.y, lu.z, uRightBottom, vf2);//right bottom finger 2
		addVertex(builder, -hw - sfl - sfl2, -hh, -hcl - cfl - cfl2, lu.x, -lu.y, lu.z, uLeftBottom, vf2);//left bottom finger 2
		addVertex(builder, -hw - sfl - sfl2, hh, -hcl - cfl - cfl2, lu.x, lu.y, lu.z, maxU, vf2);//left top finger 2 (2)
		int indexFinger3 = builder.vertices.size() / 3;
		addVertex(builder, -hw - sfl - sfl2 - sfl3, hh, -hcl - cfl - cfl2 - cfl3, lu.x, lu.y, lu.z, uLeftTop, vf3);//left top finger 3
		addVertex(builder, hw - sfl - sfl2 - sfl3, hh, -hcl - cfl - cfl2 - cfl3, -lu.x, lu.y, lu.z, uRightTop, vf3);//right top finger 3
		addVertex(builder, hw - sfl - sfl2 - sfl3, -hh, -hcl - cfl - cfl2 - cfl3, -lu.x, -lu.y, lu.z, uRightBottom, vf3);//right bottom finger 3
		addVertex(builder, -hw - sfl - sfl2 - sfl3, -hh, -hcl - cfl - cfl2 - cfl3, lu.x, -lu.y, lu.z, uLeftBottom, vf3);//left bottom finger 3
		addVertex(builder, -hw - sfl - sfl2 - sfl3, hh, -hcl - cfl - cfl2 - cfl3, lu.x, lu.y, lu.z, maxU, vf3);//left top finger 3 (2)
		if(left)
			mirrorX(builder);
		bindFourangle(builder, indexBack, indexBack + 1, indexEdge + 1, indexEdge);
		bindFourangle(builder, indexBack + 1, indexBack + 2, indexEdge + 2, indexEdge + 1);
		bindFourangle(builder, indexBack + 2, indexBack + 3, indexEdge + 3, indexEdge + 2);
		bindFourangle(builder, indexBack + 3, indexBack + 4, indexEdge + 4, indexEdge + 3);
		bindFourangle(builder, indexEdge, indexEdge + 1, indexFinger1 + 1, indexFinger1);
		bindFourangle(builder, indexEdge + 1, indexEdge + 2, indexFinger1 + 2, indexFinger1 + 1);
		bindFourangle(builder, indexEdge + 2, indexEdge + 3, indexFinger1 + 3, indexFinger1 + 2);
		bindFourangle(builder, indexEdge + 3, indexEdge + 4, indexFinger1 + 4, indexFinger1 + 3);
		bindFourangle(builder, indexFinger1, indexFinger1 + 1, indexFinger2 + 1, indexFinger2);
		bindFourangle(builder, indexFinger1 + 1, indexFinger1 + 2, indexFinger2 + 2, indexFinger2 + 1);
		bindFourangle(builder, indexFinger1 + 2, indexFinger1 + 3, indexFinger2 + 3, indexFinger2 + 2);
		bindFourangle(builder, indexFinger1 + 3, indexFinger1 + 4, indexFinger2 + 4, indexFinger2 + 3);
		bindFourangle(builder, indexFinger2, indexFinger2 + 1, indexFinger3 + 1, indexFinger3);
		bindFourangle(builder, indexFinger2 + 1, indexFinger2 + 2, indexFinger3 + 2, indexFinger3 + 1);
		bindFourangle(builder, indexFinger2 + 2, indexFinger2 + 3, indexFinger3 + 3, indexFinger3 + 2);
		bindFourangle(builder, indexFinger2 + 3, indexFinger2 + 4, indexFinger3 + 4, indexFinger3 + 3);
		bindFourangle(builder, indexFinger3, indexFinger3 + 1, indexFinger3 + 2, indexFinger3 + 3);
		return new ModelPart(builder.load(), new ModelTexture(new Texture(textureBuilder.loadNormal()), at.getShineDamper(), at.getReflectivity()), new Vector3f());
	}
	
	public static ModelPart createModelArmorBelly(ModelArmorBelly armor, HumanoidBelly belly, ArmorTexture texture){
		float f = armor.distanceToBellyFactor();
		TextureMarker marker = new TextureMarker(TextureMarker.createCilinder(belly.bellyHeight() * f, belly.bellyWidth() * f, belly.bellyWidth() * f, Game.getOptions().pixelsPerMeter), TextureMarker.createHalfSphere(belly.bellyDepth() * f, belly.bellyWidth() * f, belly.bellyDepth() * f, Game.getOptions().pixelsPerMeter));
		TextureArea areaCil = marker.getArea(0);
		TextureArea areaTop = marker.getArea(1);
		TextureBuilder textureBuilder = new TextureBuilder(marker.getWidth(), marker.getHeight(), true);
		texture.getChestplate().paint(textureBuilder, areaCil);
		texture.getChestplateTop().paint(textureBuilder, areaTop);
		ModelBuilder builder = new ModelBuilder();
		int tw = marker.getWidth();
		int th = marker.getHeight();
		addSphereTop(builder, 10, 0, belly.bellyHeight() / 2, 0, belly.bellyWidth() / 2 * f, belly.bellyDepth() / 2 * f, belly.bellyDepth() / 2 * f, areaTop.getMinU(tw), areaTop.getMinV(th), areaTop.getMaxU(tw), areaTop.getMaxV(th));
		addVerticalCilinder(builder, 10, 0, 0, belly.bellyWidth() / 2 * f, belly.bellyDepth() / 2 * f, -belly.bellyHeight() * 0.5f * f, belly.bellyHeight() * 0.5f * f, areaCil.getMinU(tw), areaCil.getMinV(th), areaCil.getMaxU(tw), areaCil.getMaxV(th));
		return new ModelPart(builder.load(), new ModelTexture(new Texture(textureBuilder.loadNormal()), texture.getShineDamper(), texture.getReflectivity()), new Vector3f());
	}
	
	public static ModelPart createModelArmorUpperArm(ModelArmorUpperArm armor, HumanoidArm arm, ArmorTexture at){
		float af = armor.distanceToArmFactor();
		float sf = armor.distanceToShoulderFactor();
		TextureMarker marker = new TextureMarker(TextureMarker.createHalfSphere(arm.shoulderRadius() * sf, arm.shoulderRadius() * sf * 2, arm.shoulderRadius() * sf * 2, Game.getOptions().pixelsPerMeter), TextureMarker.createCilinder(arm.underArmLength(), arm.shoulderRadius() * 2 * af, arm.shoulderRadius() * 2 * af, Game.getOptions().pixelsPerMeter));
		int tw = marker.getWidth();
		int th = marker.getHeight();
		TextureArea elbow = marker.getArea(0);
		TextureArea upperArm = marker.getArea(1);
		TextureBuilder textureBuilder = new TextureBuilder(tw, th, true);
		at.getElbow().paint(textureBuilder, elbow);
		at.getUpperArm().paint(textureBuilder, upperArm);
		ModelBuilder builder = new ModelBuilder();
		addSphereSouth(builder, 10, 0, 0, 0, arm.shoulderRadius() * sf, arm.shoulderRadius() * sf, arm.shoulderRadius() * sf, elbow.getMinU(tw), elbow.getMinV(th), elbow.getMaxU(tw), elbow.getMaxV(th));
		addHorizontalCilinder(builder, 10, 0f, 0f, arm.shoulderRadius() * af, arm.shoulderRadius() * af, arm.elbowRadius() * af, arm.elbowRadius() * af, 0, -arm.upperArmLength(), upperArm.getMinU(tw), upperArm.getMinV(th), upperArm.getMaxU(tw), upperArm.getMaxV(th));
		return new ModelPart(builder.load(), new ModelTexture(new Texture(textureBuilder.loadNormal()), at.getShineDamper(), at.getReflectivity()), new Vector3f());
	}
	
	public static ModelPart createModelArmorUnderArm(ModelArmorUnderArm armor, HumanoidArm arm, ArmorTexture at){
		float f = armor.distanceToArmFactor();
		TextureMarker marker = new TextureMarker(TextureMarker.createCilinder(arm.underArmLength(), arm.elbowRadius() * 2 * f, arm.elbowRadius() * 2 * f, Game.getOptions().pixelsPerMeter));
		int tw = marker.getWidth();
		int th = marker.getHeight();
		TextureBuilder textureBuilder = new TextureBuilder(tw, th, true);
		TextureArea ta = marker.getArea(0);
		at.getUnderArm().paint(textureBuilder, ta);
		ModelBuilder builder = new ModelBuilder();
		addHorizontalCilinder(builder, 10, 0f, 0f, arm.elbowRadius() * f, arm.elbowRadius() * f, arm.wristRadius() * f, arm.wristRadius() * f, 0, -arm.underArmLength(), ta.getMinU(tw), ta.getMinV(th), ta.getMaxU(tw), ta.getMaxV(th));
		return new ModelPart(builder.load(), new ModelTexture(new Texture(textureBuilder.loadNormal()), at.getShineDamper(), at.getReflectivity()), new Vector3f());
	}
	
	public static ModelPart createModelArmorUpperLeg(ModelArmorUpperLeg armor, HumanoidLeg leg, ArmorTexture at){
		ModelBuilder builder = new ModelBuilder();
		float f = armor.distanceToLegFactor();
		TextureMarker marker = new TextureMarker(TextureMarker.createCilinder(leg.upperLegLength(), leg.legUpperRadius() * 2 * f, leg.legUpperRadius() * 2 * f, Game.getOptions().pixelsPerMeter));
		int tw = marker.getWidth();
		int th = marker.getHeight();
		TextureArea ta = marker.getArea(0);
		TextureBuilder textureBuilder = new TextureBuilder(tw, th, true);
		at.getUpperLeg().paint(textureBuilder, ta);
		addVerticalCilinder(builder, 10, 0f, 0f, leg.legUpperRadius() * f, leg.legUpperRadius() * f, leg.kneeRadius() * f, leg.kneeRadius() * f, 0, -leg.upperLegLength(), ta.getMinU(tw), ta.getMinV(th), ta.getMaxU(tw), ta.getMaxV(th));
		return new ModelPart(builder.load(), new ModelTexture(new Texture(textureBuilder.loadNormal()), at.getShineDamper(), at.getReflectivity()), new Vector3f());
	}
	
	public static ModelPart createModelArmorUnderLeg(ModelArmorUnderLeg armor, HumanoidLeg leg, ArmorTexture at){
		ModelBuilder builder = new ModelBuilder();
		float f = armor.distanceToLegFactor();
		TextureMarker marker = new TextureMarker(TextureMarker.createCilinder(leg.underLegLength(), leg.kneeRadius() * 2 * f, leg.kneeRadius() * 2 * f, Game.getOptions().pixelsPerMeter));
		int tw = marker.getWidth();
		int th = marker.getHeight();
		TextureBuilder textureBuilder = new TextureBuilder(tw, th, true);
		TextureArea ta = marker.getArea(0);
		at.getUnderLeg().paint(textureBuilder, ta);
		addVerticalCilinder(builder, 10, 0f, 0f, leg.kneeRadius() * f, leg.kneeRadius() * f, leg.legUnderRadius() * f, leg.legUnderRadius() * f, 0, -leg.underLegLength(), ta.getMinU(tw), ta.getMinV(th), ta.getMaxU(tw), ta.getMaxV(th));
		return new ModelPart(builder.load(), new ModelTexture(new Texture(textureBuilder.loadNormal()), at.getShineDamper(), at.getReflectivity()), new Vector3f());
	}
	
	public static ModelPart createModelArmorFoot(ModelArmorFoot armor, HumanoidFootProperties foot, HumanoidLeg leg, ArmorTexture at, boolean right){
		ModelBuilder builder = new ModelBuilder();
		float f = armor.distanceToFootFactor();
		int ppm = Game.getOptions().pixelsPerMeter;
		TextureMarker marker = new TextureMarker(new TextureArea(2 * foot.footFrontLength() * f * ppm + foot.footWidth() * PI * f * ppm, foot.footWidth() * 2 * f * ppm));
		int tw = marker.getWidth();
		int th = marker.getHeight();
		TextureArea ta = marker.getArea(0);
		TextureBuilder textureBuilder = new TextureBuilder(tw, th, true);
		at.getShoe().paint(textureBuilder, ta);
		float minV = ta.getMinV(th);
		float minU = ta.getMinU(tw);
		float maxU = ta.getMaxU(tw);
		float maxV = ta.getMaxV(th);
		float deltaU = maxU - minU;
		float deltaV = maxV - minV;
		float lowV = minV + 0.2f * deltaV;
		float lowVFront = minV + 0.4f * deltaV;
		float highVFront = minV + 0.6f * deltaV;
		int midParts = 5;
		int indexUpperCircle = builder.vertices.size() / 3;
		for(int i = 0; i <= midParts; i++){
			float angle = i / (float) midParts * 180f;
			addVertex(builder, Maths.cos(angle) * leg.legUnderRadius() * f, foot.footMidHeight() * f, -Maths.sin(angle) * leg.legUnderRadius() * f, Maths.cos(angle), 0, Maths.sin(angle), minU + (i / (float) midParts) * deltaU, minV);
		}
		int indexLowerCircle = builder.vertices.size() / 3;
		for(int i = 0; i <= midParts; i++){
			float angle = i / (float) midParts * 180f;
			addVertex(builder, Maths.cos(angle) * leg.legUnderRadius() * f, 0, -Maths.sin(angle) * leg.legUnderRadius() * f, Maths.cos(angle), 0, Maths.sin(angle), minU + (i / (float) midParts) * deltaU, lowV);
		}
		int indexLowFront = builder.vertices.size() / 3;
		addVertex(builder, -foot.footWidth()  * f / 2, 0, 0, -1, 0, 0, minU, lowVFront);
		addVertex(builder, -foot.footWidth()  * f / 2, 0, foot.footFrontLength() * f, -1, 0, 0, minU + 0.35f * deltaU, lowVFront);
		addVertex(builder, foot.footWidth() * f / 2, 0, foot.footFrontLength() * f, 1, 0, 0, minU + 0.65f * deltaU, lowVFront);
		addVertex(builder, foot.footWidth() * f / 2, 0, 0, 1, 0, 0, maxU, lowVFront);
		int indexHighFront = builder.vertices.size() / 3;
		addVertex(builder, -foot.footWidth() * f / 2, foot.footMidHeight() * f, 0, 0, 1, 0, maxU, highVFront);
		addVertex(builder, -foot.footWidth() * f / 2, foot.footOutHeight() * f, foot.footFrontLength() * f, 0, 1, 0, minU + 0.5f * deltaU, highVFront);
		addVertex(builder, foot.footWidth() * f / 2, foot.footOutHeight() * f, foot.footFrontLength() * f, 0, 1, 0, minU + 0.5f * deltaU, maxV);
		addVertex(builder, foot.footWidth() * f / 2, foot.footMidHeight() * f, 0, 0, 1, 0, maxU, maxV);
		bindFourangle2(builder, indexLowFront, indexLowFront + 1, indexHighFront, indexHighFront + 1);
		bindFourangle2(builder, indexLowFront + 1, indexLowFront + 2, indexHighFront + 1, indexHighFront + 2);
		bindFourangle2(builder, indexLowFront + 2, indexLowFront + 3, indexHighFront + 2, indexHighFront + 3);
		bindFourangle(builder, indexHighFront, indexHighFront + 1, indexHighFront + 2, indexHighFront + 3);
		bindFourangle(builder, indexLowFront, indexLowFront + 1, indexLowFront + 2, indexLowFront + 3);
		for(int i = 0; i < midParts; i++)
			bindFourangle(builder, indexLowerCircle + i, indexLowerCircle + i + 1, indexUpperCircle + i + 1, indexUpperCircle + i);
		return new ModelPart(builder.load(), new ModelTexture(new Texture(textureBuilder.loadNormal()), at.getShineDamper(), at.getReflectivity()), new Vector3f());
	}
	
	public static ModelPart createModelBone(ModelBone bone, BonePainter painter, HumanoidHandProperties hand, boolean left){
		ModelBuilder builder = new ModelBuilder();
		float scale = hand.handCoreLength() / Math.max(bone.boneRadiusX(), bone.boneRadiusZ()) * 0.5f;
		TextureMarkerBone marker = new TextureMarkerBone(bone, scale, Game.getOptions().pixelsPerMeter);
		int tw = marker.getWidth();
		int th = marker.getHeight();
		Random r = new Random(bone.seed());
		float z = -hand.handCoreLength() / 2 - hand.fingerLength() * 0.4f + bone.maxRandomFactor() * bone.boneRadiusZ() * scale;
		float x = -bone.boneRadiusX() * scale - hand.handWidth() * 0.55f + bone.maxRandomFactor() * bone.boneRadiusX() * scale;
		int partsR = 10;
		int partsY = 10;
		TextureArea c = marker.getCilinder();
		for(int i = 0; i <= partsY; i++){
			float y = (float)i / partsY * bone.boneLength() * scale - hand.handHeight();
			float v = c.getMinV(th) + (c.getMaxV(th) - c.getMinV(th)) * ((float) i / partsY);
			for(int j = 0; j < partsR; j++){
				float angle = (float) ((float)j / partsR * 2 * Math.PI);
				addVertex(builder, x + (Maths.cosRad(angle) * bone.boneRadiusX() * scale) * (1 - bone.maxRandomFactor() + 2 * r.nextFloat() * bone.maxRandomFactor()), y, z + (Maths.sinRad(angle) * bone.boneRadiusZ() * scale) * (1 - bone.maxRandomFactor() + 2 * r.nextFloat() * bone.maxRandomFactor()), Maths.cosRad(angle), 0, Maths.sinRad(angle), c.getMinU(tw) + ((float)j / partsR) * (c.getMaxU(tw) - c.getMinU(tw)), v);
			}
			addVertex(builder, builder.vertices.get(i * 3 * (partsR + 1)), builder.vertices.get(i * 3 * (partsR + 1) + 1), builder.vertices.get(i * 3 * (partsR + 1) + 2), 1, 0, 0, c.getMaxU(tw), v);
		}
		if(!left)
			mirrorX(builder);
		for(int i = 0; i < partsY; i++)
			for(int j = 0; j < partsR; j++)
				bindFourangle(builder, i * (partsR + 1) + j, i * (partsR + 1) + j + 1, (i + 1) * (partsR + 1) + j + 1, (i + 1) * (partsR + 1) + j);
		TextureArea s1 = marker.getSphere1();
		TextureArea s2 = marker.getSphere2();
		TextureArea s3 = marker.getSphere3();
		TextureArea s4 = marker.getSphere4();
		addBoneSphere(builder, bone, r, scale, bone.boneRadiusX() * scale + x, (bone.boneLength()) * scale - hand.handHeight(), z, s1.getMinU(tw), s1.getMinV(th), s1.getMaxU(tw), s1.getMaxV(th), partsY, partsR);
		addBoneSphere(builder, bone, r, scale, -bone.boneRadiusX() * scale + x, (bone.boneLength()) * scale - hand.handHeight(), z, s2.getMinU(tw), s2.getMinV(th), s2.getMaxU(tw), s2.getMaxV(th), partsY, partsR);
		addBoneSphere(builder, bone, r, scale, bone.boneRadiusX() * scale + x, -hand.handHeight(), z, s3.getMinU(tw), s3.getMinV(th), s3.getMaxU(tw), s3.getMaxV(th), partsY, partsR);
		addBoneSphere(builder, bone, r, scale, -bone.boneRadiusX() * scale + x, -hand.handHeight(), z, s4.getMinU(tw), s4.getMinV(th), s4.getMaxU(tw), s4.getMaxV(th), partsY, partsR);
		ModelPart model = new ModelPart(builder.load(), new ModelTexture(createBoneTexture(marker, painter), 1f, 0f), new Vector3f());
		return model;
	}
	
	public static ModelPart createModelSpear(SpearTexture texture, HumanoidHandProperties hand){
		ModelBuilder builder = new ModelBuilder();
		ModelSpear spear = texture.getSpearModel();
		float scale = hand.handCoreLength() / spear.stickRadius() / 2;
		TextureMarkerSpear marker = new TextureMarkerSpear(spear, scale, Game.getOptions().pixelsPerMeter);
		int tw = marker.getWidth();
		int th = marker.getHeight();
		float x = -spear.stickRadius() * scale - hand.handWidth() / 2;
		float y = spear.stickLength() * scale - hand.handHeight();
		float z = -spear.stickRadius() * scale - hand.fingerLength() / 2;
		TextureArea st = marker.getStick();
		addVerticalCilinder(builder, 10, x, z, spear.stickRadius() * scale, spear.stickRadius() * scale, -hand.handHeight(), y, st.getMinU(tw), st.getMinV(th), st.getMaxU(tw), st.getMaxV(th));
		float r = spear.pointMaxRadius() * scale;
		float l = spear.pointLength() * scale;
		TextureArea pb = marker.getPointBottom();
		addFourangle(builder, x, y, z - r, 0, -1, 0, pb.getMinU(tw), pb.getMinV(th), x + r, y, z, 1, 0, 0, pb.getMaxU(tw), pb.getMinV(th), x, y, z + r, 0, 0, 1, pb.getMaxU(tw), pb.getMaxV(th), x - r, y, z, -1, 0, 0, pb.getMinU(tw), pb.getMaxV(th));
		TextureArea sb = marker.getStickBottom();
		float sr = spear.stickRadius() * scale;
		float minY = -hand.handHeight();
		addFourangle(builder, x - sr, minY, z - sr, 0, -1, 0, sb.getMinU(tw), sb.getMinV(th), x + sr, minY, z - sr, 0, -1, 0, sb.getMaxU(tw), sb.getMinV(th), x + sr, minY, z + sr, 0, -1, 0, sb.getMaxU(tw), sb.getMaxV(th), x - sr, minY, z + sr, 0, -1, 0, sb.getMinU(tw), sb.getMaxV(th));
		Vector3f ne = new Vector3f(r, l, -r);
		Vector3f es = new Vector3f(r, l, r);
		Vector3f sw = new Vector3f(-r, l, r);
		Vector3f wn = new Vector3f(-r, l, -r);
		ne.normalise();
		es.normalise();
		sw.normalise();
		wn.normalise();
		TextureArea nes = marker.getPointNES();
		addFourangle(builder, x, y + l, z, 1, 0, 0, nes.getMaxU(tw), nes.getMaxV(th), x, y, z - r, ne.x, ne.y, ne.z, nes.getMinU(tw), nes.getMinV(th), x + r, y, z, ne.x, ne.y, ne.z, nes.getMaxU(tw), nes.getMinV(th), x, y, z + r, es.x, es.y, es.z, nes.getMinU(tw), nes.getMaxV(th));
		TextureArea swn = marker.getPointSWN();
		addFourangle(builder, x, y + l, z, -1, 0, 0, swn.getMaxU(tw), swn.getMaxV(th), x, y, z + r, sw.x, sw.y, sw.z, swn.getMinU(tw), swn.getMinV(th), x - r, y, z, sw.x, sw.y, sw.z, swn.getMaxU(tw), swn.getMinV(th), x, y, z - r, wn.x, wn.y, wn.z, swn.getMinU(tw), swn.getMaxV(th));
		return new ModelPart(builder.load(), new ModelTexture(createSpearTexture(marker, texture.getSpearPainter()), texture.getShineDamper(), texture.getReflectivity()), new Vector3f());
	}
	
	public static ModelPart createModelSword(SwordTexture texture, HumanoidHandProperties hand){
		ModelSword sword = texture.getSwordModel();
		float scale = hand.handCoreLength() / sword.handleRadius() / 2;
		TextureMarkerSword marker = new TextureMarkerSword(sword, scale, Game.getOptions().pixelsPerMeter);
		int tw = marker.getWidth();
		int th = marker.getHeight();
		float x = -sword.handleRadius() * scale - hand.handWidth() / 2;
		float y = -hand.handHeight();
		float z = -sword.handleRadius() * scale - hand.fingerLength() / 2;
		TextureArea ah = marker.getHandle();
		ModelBuilder builder = new ModelBuilder();
		addVerticalCilinder(builder, 10, x, z, sword.handleRadius() * scale, sword.handleRadius() * scale, y, y + sword.handleLength() * scale, ah, tw, th);
		float hr = sword.handleRadius() * scale;
		addPlane(builder, marker.getHandleBottom(), tw, th, 0, -1, 0, x - hr, y, z - hr, x + hr, y, z - hr, x + hr, y, z + hr, x - hr, y, z + hr);
		float mw = sword.middleWidth() * scale / 2;
		float ml = sword.middleLength() * scale / 2;
		float my = y + sword.handleLength() * scale + mw;
		addPlane(builder, marker.getMiddleFront(), tw, th, 0, 0, 1, x - ml, my - mw, z + mw, x + ml, my - mw, z + mw, x + ml, my + mw, z + mw, x - ml, my + mw, z + mw);
		addPlane(builder, marker.getMiddleBack(), tw, th, 0, 0, -1, x - ml, my - mw, z - mw, x + ml, my - mw, z - mw, x + ml, my + mw, z - mw, x - ml, my + mw, z - mw);
		addPlane(builder, marker.getMiddleTop(), tw, th, 0, 1, 0, x - ml, my + mw, z - mw, x + ml, my + mw, z - mw, x + ml, my + mw, z + mw, x - ml, my + mw, z + mw);
		addPlane(builder, marker.getMiddleBottom(), tw, th, 0, -1, 0, x - ml, my - mw, z - mw, x + ml, my - mw, z - mw, x + ml, my - mw, z + mw, x - ml, my - mw, z + mw);
		addPlane(builder, marker.getMiddleLeft(), tw, th, -1, 0, 0, x - ml, my - mw, z - mw, x - ml, my + mw, z - mw, x - ml, my + mw, z + mw, x - ml, my - mw, z + mw);
		addPlane(builder, marker.getMiddleRight(), tw, th, 1, 0, 0, x + ml, my - mw, z - mw, x + ml, my + mw, z - mw, x + ml, my + mw, z + mw, x + ml, my - mw, z + mw);
		//TODO the blade texture coords might behave a bit strangely...
		float bty = y + (sword.handleLength() + sword.middleWidth() + sword.bladeLength()) * scale;
		float by = y + (sword.handleLength() + sword.middleWidth()) * scale;
		float bw = sword.bladeWidth() * scale / 2;
		float bd = sword.bladeDepth() * scale / 2;
		addFourangle(builder, marker.getBladeFront(), tw, th, x - bw, by, z, -1, 0, 0, x, by, z + bd, 0, 0, 1, x + bw, by, z, 1, 0, 0, x, bty, z, 0, 1, 0);
		addFourangle(builder, marker.getBladeBack(), tw, th, x - bw, by, z, -1, 0, 0, x, by, z - bd, 0, 0, -1, x + bw, by, z, 1, 0, 0, x, bty, z, 0, 1, 0);
		return new ModelPart(builder.load(), new ModelTexture(createSwordTexture(marker, texture.getSwordPainter()), texture.getShineDamper(), texture.getReflectivity()), new Vector3f());
	}
	
	public static ModelPart createModelMyrre(BodyMyrre body, MyrrePainter colors){
		TextureMarkerMyrre tm = new TextureMarkerMyrre(body, Game.getOptions().pixelsPerMeter);
		int tw = tm.getWidth();
		int th = tm.getHeight();
		ModelTexture texture = new ModelTexture(createMyrreTexture(tm, colors), 1f, 0f);
		ModelPart leftFoot = new ModelPart(createMyrreFootClaw(body, body, tm.getLeftFootClaws(), tw, th), texture, new Vector3f(0, 0, 0));
		ModelPart rightFoot = new ModelPart(createMyrreFootClaw(body, body, tm.getRightFootClaws(), tw, th), texture, new Vector3f(0, 0, 0));
		ModelPart leftLeg = createMyrreLeg(body, texture, tm.getLeftLeg(), new Vector3f(-body.bellyWidth() / 2, -body.bellyHeight() / 2, 0), leftFoot);
		ModelPart rightLeg = createMyrreLeg(body, texture, tm.getRightLeg(), new Vector3f(body.bellyWidth() / 2, -body.bellyHeight() / 2, 0), rightFoot);
		ModelPart leftHandClaw = new ModelPart(createMyrreHandClaw(body, body, tm.getLeftHandClaws(), tw, th, true), texture, new Vector3f());
		ModelPart rightHandClaw = new ModelPart(createMyrreHandClaw(body, body, tm.getRightHandClaws(), tw, th, false), texture, new Vector3f());
		ModelPart leftArm = createMyrreArm(body, texture, tm.getLeftArm(), new Vector3f(-body.bellyWidth() / 3, body.bellyHeight() / 4, 0), true, leftHandClaw);
		ModelPart rightArm = createMyrreArm(body, texture, tm.getRightArm(), new Vector3f(body.bellyWidth() / 3, body.bellyHeight() / 4, 0), false, rightHandClaw);
		return new ModelPartParent(createMyrreBelly(body, tm.getBelly(), tw, th), texture, new Vector3f(), leftArm, rightArm, leftLeg, rightLeg);
	}
	
	private static ModelPart createMyrreLeg(MyrreLeg leg, ModelTexture texture, TextureArea ta, Vector3f vector, ModelPart foot){
		int tw = ta.getWidth();
		int th = ta.getHeight();
		ModelPart lowPart = createMyrreLegPart(leg, texture, ta, tw, th, 0, foot);
		for(int i = 1; i < leg.legParts(); i++)
			lowPart = createMyrreLegPart(leg, texture, ta, tw, th, i, lowPart);
		lowPart.getRelativePosition().x = vector.x;
		lowPart.getRelativePosition().y = vector.y;
		lowPart.getRelativePosition().z = vector.z;
		return lowPart;
	}
	
	private static ModelPart createMyrreArm(MyrreArm arm, ModelTexture texture, TextureArea ta, Vector3f vector, boolean left, ModelPart hand){
		int tw = ta.getWidth();
		int th = ta.getHeight();
		ModelPart part = createMyrreHandPart(arm, texture, ta, tw, th, hand);
		for(int i = 1; i < arm.armParts(); i++)
			part = createMyrreArmPart(arm, texture, ta, tw, th, 1, part);
		part.getRelativePosition().x = vector.x;
		part.getRelativePosition().y = vector.y;
		part.getRelativePosition().z = vector.z;
		part.setPitch(BodyMyrre.Rotation.ARM_PITCH);
		part.setRoll(left ? -BodyMyrre.Rotation.ARM_ROLL : BodyMyrre.Rotation.ARM_ROLL);
		return part;
	}
	
	private static ModelPart createMyrreHandPart(MyrreArm arm, ModelTexture texture, TextureArea ta, int tw, int th, ModelPart hand){
		AbstractModel model = createMyrreArmPart(arm, ta, tw, th, 0);
		return new BodyMyrre.MyrreHandModelPart(model, texture, new Vector3f(), hand);
	}
	
	private static ModelPart createMyrreArmPart(MyrreArm arm, ModelTexture texture, TextureArea ta, int tw, int th, int index, ModelPart next){
		return new ModelPartParent(createMyrreArmPart(arm, ta, tw, th, index), texture, new Vector3f(0, -arm.armLength() * index / arm.armParts(), 0), next);
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
	
	public static ModelPart createModelTroll(BodyTroll body, TrollPainter colors){
		TextureMarkerTroll tt = new TextureMarkerTroll(body);
		int tw = tt.getWidth();
		int th = tt.getHeight();
		ModelTexture texture = new ModelTexture(createTrollTexture(tt, colors), 1.0f, 0.0f);
		float s = 1f;
		ModelPart leftFoot = new ModelPartParent(createHumanoidFoot(body, body, tt.getLeftFoot(), tw, th), texture, new Vector3f(0, -s * (body.upperLegLength() + body.footMidHeight()), 0), 0, 180, 0);
		ModelPart rightFoot = new ModelPartParent(createHumanoidFoot(body, body, tt.getRightFoot(), tw, th), texture, new Vector3f(0, -s * (body.upperLegLength() + body.footMidHeight()), 0), 0, 180, 0);
		ModelPartParent leftUnderLeg = new ModelPartParent(createCreatureUnderLeg(body, tt.getLeftUnderLeg(), tt.getLeftKnee(), tw, th), texture, new Vector3f(0f, -s * body.upperLegLength(), 0f), leftFoot);
		ModelPartParent rightUnderLeg = new ModelPartParent(createCreatureUnderLeg(body, tt.getRightUnderLeg(), tt.getRightKnee(), tw, th), texture, new Vector3f(0f, -s * body.upperLegLength(), 0f), rightFoot);
		ModelPartParent leftLeg = new ModelPartParent(createCreatureUpperLeg(body, tt.getLeftUpperLeg(), tw, th), texture, new Vector3f(s * -body.bellyWidth() / 2 + s * body.legUpperRadius(), -s * body.bellyHeight() / 2, 0f), leftUnderLeg);
		ModelPartParent rightLeg = new ModelPartParent(createCreatureUpperLeg(body, tt.getRightUpperLeg(), tw, th), texture, new Vector3f(s * body.bellyWidth() / 2 - s * body.legUpperRadius(), -s * body.bellyHeight() / 2, 0f), rightUnderLeg);
		ModelPart leftHand = new ModelPartParent(createHumanoidHand(body, tt.getLeftHand(), tw, th, true), texture, new Vector3f(0, 0, -s * body.underArmLength()));
		ModelPartParent leftUnderArm = new ModelPartParent(createCreatureUnderArm(body, tt.getLeftUnderArm(), tw, th), texture, new Vector3f(0, 0, -s * body.upperArmLength()), 0, 0, 0, leftHand);
		ModelPartParent leftArm = new ModelPartParent(createCreatureUpperArm(body, tt.getLeftUpperArm(), tt.getLeftShoulder(), tw, th), texture, new Vector3f(s * -body.bellyWidth() / 2, s * body.bellyHeight() / 2, 0f), BodyTroll.Rotation.ARM_PITCH, 0, -BodyTroll.Rotation.ARM_ROLL, leftUnderArm);
		ModelPart rightHand = new ModelPartParent(createHumanoidHand(body, tt.getRightHand(), tw, th, false), texture, new Vector3f(0, 0, -s * body.underArmLength()));
		ModelPartParent rightUnderArm = new ModelPartParent(createCreatureUnderArm(body, tt.getRightUnderArm(), tw, th), texture, new Vector3f(0, 0, -s * body.upperArmLength()), 0, 0, 0, rightHand);
		ModelPartParent rightArm = new ModelPartParent(createCreatureUpperArm(body, tt.getRightUpperArm(), tt.getRightShoulder(), tw, th), texture, new Vector3f(s * body.bellyWidth() / 2, s * body.bellyHeight() / 2, 0f), BodyTroll.Rotation.ARM_PITCH, 0, BodyTroll.Rotation.ARM_ROLL, rightUnderArm);
		ModelPart head = new ModelPartParent(createTrollHead(body, tt.getHead(), tt.getNose(), tw, th), texture, new Vector3f(0, s * body.bellyHeight() / 2 + s * body.bellyDepth() / 4, 0));
		ModelPartParent belly = new ModelPartParent(createCreatureBelly(body, tt.getBelly(), tt.getBellyTop(), tw, th), texture, new Vector3f(0f, s * (body.footMidHeight() + body.underLegLength() + body.upperLegLength() + body.bellyHeight() / 2), 0f), 0, 0, 0, head, leftArm, rightArm, leftLeg, rightLeg);
		return belly;
	}
	
	public static ModelPart createModelHuman(BodyHuman body, HumanPainter colors){
		TextureMarkerHuman tt = new TextureMarkerHuman(body);
		int tw = tt.getWidth();
		int th = tt.getHeight();
		ModelTexture texture = new ModelTexture(createHumanTexture(tt, colors), 1.0f, 0.0f);
		float s = 1f;
		ModelPart leftFoot = new ModelPartParent(createHumanoidFoot(body, body, tt.getLeftFoot(), tw, th), texture, new Vector3f(0, -s * (body.upperLegLength() + body.footMidHeight()), 0), 0, 180, 0);
		ModelPart rightFoot = new ModelPartParent(createHumanoidFoot(body, body, tt.getRightFoot(), tw, th), texture, new Vector3f(0, -s * (body.upperLegLength() + body.footMidHeight()), 0), 0, 180, 0);
		ModelPartParent leftUnderLeg = new ModelPartParent(createCreatureUnderLeg(body, tt.getLeftUnderLeg(), tt.getLeftKnee(), tw, th), texture, new Vector3f(0f, -s * body.upperLegLength(), 0f), leftFoot);
		ModelPartParent rightUnderLeg = new ModelPartParent(createCreatureUnderLeg(body, tt.getRightUnderLeg(), tt.getRightKnee(), tw, th), texture, new Vector3f(0f, -s * body.upperLegLength(), 0f), rightFoot);
		ModelPartParent leftLeg = new ModelPartParent(createCreatureUpperLeg(body, tt.getLeftUpperLeg(), tw, th), texture, new Vector3f(s * -body.bellyWidth() / 2 + s * body.legUpperRadius(), -s * body.bellyHeight() / 2, 0f), leftUnderLeg);
		ModelPartParent rightLeg = new ModelPartParent(createCreatureUpperLeg(body, tt.getRightUpperLeg(), tw, th), texture, new Vector3f(s * body.bellyWidth() / 2 - s * body.legUpperRadius(), -s * body.bellyHeight() / 2, 0f), rightUnderLeg);
		ModelPart leftHand = new ModelPartParent(createHumanoidHand(body, tt.getLeftHand(), tw, th, true), texture, new Vector3f(0, 0, -s * body.underArmLength()));
		ModelPartParent leftUnderArm = new ModelPartParent(createCreatureUnderArm(body, tt.getLeftUnderArm(), tw, th), texture, new Vector3f(0, 0, -s * body.upperArmLength()), 0, 0, 0, leftHand);
		ModelPartParent leftArm = new ModelPartParent(createCreatureUpperArm(body, tt.getLeftUpperArm(), tt.getLeftShoulder(), tw, th), texture, new Vector3f(s * -body.bellyWidth() / 2, s * body.bellyHeight() / 2, 0f), BodyHuman.Rotation.ARM_PITCH, 0, -BodyHuman.Rotation.ARM_ROLL, leftUnderArm);
		ModelPart rightHand = new ModelPartParent(createHumanoidHand(body, tt.getRightHand(), tw, th, false), texture, new Vector3f(0, 0, -s * body.underArmLength()));
		ModelPartParent rightUnderArm = new ModelPartParent(createCreatureUnderArm(body, tt.getRightUnderArm(), tw, th), texture, new Vector3f(0, 0, -s * body.upperArmLength()), 0, 0, 0, rightHand);
		ModelPartParent rightArm = new ModelPartParent(createCreatureUpperArm(body, tt.getRightUpperArm(), tt.getRightShoulder(), tw, th), texture, new Vector3f(s * body.bellyWidth() / 2, s * body.bellyHeight() / 2, 0f), BodyHuman.Rotation.ARM_PITCH, 0, BodyHuman.Rotation.ARM_ROLL, rightUnderArm);
		ModelPart head = new ModelPartParent(createCreatureHead(body, tt.getHead(), tw, th), texture, new Vector3f(0, s * body.bellyHeight() / 2 + s * body.bellyDepth() / 4, 0));
		ModelPartParent belly = new ModelPartParent(createCreatureBelly(body, tt.getBelly(), tt.getBellyTop(), tw, th), texture, new Vector3f(0f, s * (body.footMidHeight() + body.underLegLength() + body.upperLegLength() + body.bellyHeight() / 2), 0f), 0, 0, 0, head, leftArm, rightArm, leftLeg, rightLeg);
		return belly;
	}
	
	public static ModelPart createModelBird(BodyBird body, BirdPainter painter){
		TextureMarkerBird tb = new TextureMarkerBird(body);
		createBluePrintImage(tb);
		int tw = tb.getWidth();
		int th = tb.getHeight();
		ModelTexture texture = new ModelTexture(createBirdTexture(tb, painter), 1.0f, 0.0f);
		ModelPart leftWing = new ModelPart(createBirdWing(body, body, tb.getLeftWing(), tw, th), texture, new Vector3f(-body.bellyWidth() / 3f, body.bellyHeight() / 2, 0), 90, 180, 0);
		ModelPart rightWing = new ModelPart(createBirdWing(body, body, tb.getRightWing(), tw, th), texture, new Vector3f(body.bellyWidth() / 3f, body.bellyHeight() / 2, 0), 90, 0, 0);
		ModelPart leftLeg = new ModelPart(createBirdLeg(body, tb.getLeftLeg(), tw, th), texture, new Vector3f(-body.bellyWidth() / 3f, -body.bellyHeight() / 3f, 0f), 0, 0, 330);
		ModelPart rightLeg = new ModelPart(createBirdLeg(body, tb.getRightLeg(), tw, th), texture, new Vector3f(body.bellyWidth() / 3f, -body.bellyHeight() / 3f, 0f), 0, 0, 30);
		ModelPart head = new ModelPart(createBirdHead(body, tb.getHead(), tb.getSnail(), tw, th), texture, new Vector3f(0, body.bellyHeight() / 2, 0), 30, 0, 0);
		ModelPartParent model = new ModelPartParent(createCreatureSphere(body, tb.getBelly(), tw, th), texture, new Vector3f(0, body.legLength() + body.bellyHeight(), 0), 310, 0, 0, head, leftWing, rightWing, leftLeg, rightLeg);
		return model;
	}
	
	private static Texture createBoneTexture(TextureMarkerBone marker, BonePainter painter){
		TextureBuilder builder = new TextureBuilder(marker.getWidth(), marker.getHeight(), false);
		painter.getPattern().paint(builder, marker.getCilinder());
		painter.getPattern().paint(builder, marker.getSphere1());
		painter.getPattern().paint(builder, marker.getSphere2());
		painter.getPattern().paint(builder, marker.getSphere3());
		painter.getPattern().paint(builder, marker.getSphere4());
		return new Texture(builder.loadNormal());
	}
	
	private static Texture createSpearTexture(TextureMarkerSpear marker, SpearPainter painter){
		TextureBuilder builder = new TextureBuilder(marker.getWidth(), marker.getHeight(), true);
		painter.getStick().paint(builder, marker.getStick());
		painter.getStickBottom().paint(builder, marker.getStickBottom());
		painter.getPoint().paint(builder, marker.getPointBottom());
		painter.getPoint().paint(builder, marker.getPointNES());
		painter.getPoint().paint(builder, marker.getPointSWN());
		return new Texture(builder.loadNormal());
	}
	
	private static Texture createSwordTexture(TextureMarkerSword marker, SwordPainter painter){
		TextureBuilder builder = new TextureBuilder(marker.getWidth(), marker.getHeight(), true);
		painter.getHandleBottom().paint(builder, marker.getHandleBottom());
		painter.getHandle().paint(builder, marker.getHandle());
		painter.getMiddle().paint(builder, marker.getMiddleFront());
		painter.getMiddle().paint(builder, marker.getMiddleBack());
		painter.getMiddle().paint(builder, marker.getMiddleTop());
		painter.getMiddle().paint(builder, marker.getMiddleBottom());
		painter.getMiddle().paint(builder, marker.getMiddleLeft());
		painter.getMiddle().paint(builder, marker.getMiddleRight());
		painter.getBlade().paint(builder, marker.getBladeFront());
		painter.getBlade().paint(builder, marker.getBladeBack());
		return new Texture(builder.loadNormal());
	}
	
	private static Texture createMyrreTexture(TextureMarkerMyrre tm, MyrrePainter p){
		TextureBuilder builder = new TextureBuilder(tm.getWidth(), tm.getHeight(), true);
		p.getBelly().paint(builder, tm.getBelly());
		p.getLeftArm().paint(builder, tm.getLeftArm());
		p.getRightArm().paint(builder, tm.getRightArm());
		p.getLeftHandClaw().paint(builder, tm.getLeftHandClaws());
		p.getRightHandClaw().paint(builder, tm.getRightHandClaws());
		p.getLeftLeg().paint(builder, tm.getLeftLeg());
		p.getRightLeg().paint(builder, tm.getRightLeg());
		p.getLeftFootClaw().paint(builder, tm.getLeftFootClaws());
		p.getRightFootClaw().paint(builder, tm.getRightFootClaws());
		return new Texture(builder.loadNormal());
	}
	
	private static Texture createTrollTexture(TextureMarkerTroll tt, TrollPainter painter){
		TextureBuilder builder = new TextureBuilder(tt.getWidth(), tt.getHeight(), false);
		painter.getHead().paint(builder, tt.getHead());
		painter.getBelly().paint(builder, tt.getBelly());
		painter.getLeftUpperArm().paint(builder, tt.getLeftUpperArm());
		painter.getRightUpperArm().paint(builder, tt.getRightUpperArm());
		painter.getLeftUnderArm().paint(builder, tt.getLeftUnderArm());
		painter.getRightUnderArm().paint(builder, tt.getRightUnderArm());
		painter.getLeftUpperLeg().paint(builder, tt.getLeftUpperLeg());
		painter.getRightUpperLeg().paint(builder, tt.getRightUpperLeg());
		painter.getLeftKnee().paint(builder, tt.getLeftKnee());
		painter.getRightKnee().paint(builder, tt.getRightKnee());
		painter.getLeftUnderLeg().paint(builder, tt.getLeftUnderLeg());
		painter.getRightUnderLeg().paint(builder, tt.getRightUnderLeg());
		painter.getLeftFoot().paint(builder, tt.getLeftFoot());
		painter.getRightFoot().paint(builder, tt.getRightFoot());
		painter.getLeftShoulder().paint(builder, tt.getLeftShoulder());
		painter.getRightShoulder().paint(builder, tt.getRightShoulder());
		painter.getBellyTop().paint(builder, tt.getBellyTop());
		painter.getNose().paint(builder, tt.getNose());
		painter.getLeftHand().paint(builder, tt.getLeftHand());
		painter.getRightHand().paint(builder, tt.getRightHand());
		return new Texture(builder.loadNormal());
	}
	
	private static Texture createHumanTexture(TextureMarkerHuman t, HumanPainter p){
		TextureBuilder b = new TextureBuilder(t.getWidth(), t.getHeight(), false);
		p.getHead().paint(b, t.getHead());
		p.getBelly().paint(b, t.getBelly());
		p.getLeftUpperArm().paint(b, t.getLeftUpperArm());
		p.getRightUpperArm().paint(b, t.getRightUpperArm());
		p.getLeftUnderArm().paint(b, t.getLeftUnderArm());
		p.getRightUnderArm().paint(b, t.getRightUnderArm());
		p.getLeftUpperLeg().paint(b, t.getLeftUpperLeg());
		p.getRightUpperLeg().paint(b, t.getRightUpperLeg());
		p.getLeftKnee().paint(b, t.getLeftKnee());
		p.getRightKnee().paint(b, t.getRightKnee());
		p.getLeftUnderLeg().paint(b, t.getLeftUnderLeg());
		p.getRightUnderLeg().paint(b, t.getRightUnderLeg());
		p.getLeftFoot().paint(b, t.getLeftFoot());
		p.getRightFoot().paint(b, t.getRightFoot());
		p.getLeftShoulder().paint(b, t.getLeftShoulder());
		p.getRightShoulder().paint(b, t.getRightShoulder());
		p.getBellyTop().paint(b, t.getBellyTop());
		p.getLeftHand().paint(b, t.getLeftHand());
		p.getRightHand().paint(b, t.getRightHand());
		return new Texture(b.loadNormal());
	}
	
	private static Texture createBirdTexture(TextureMarkerBird tb, BirdPainter painter){
		TextureBuilder builder = new TextureBuilder(tb.getWidth(), tb.getHeight(), true);
		painter.getHead().paint(builder, tb.getHead());
		painter.getSnail().paint(builder, tb.getSnail());
		painter.getBelly().paint(builder, tb.getBelly());
		painter.getLeftWing().paint(builder, tb.getLeftWing());
		painter.getRightWing().paint(builder, tb.getRightWing());
		painter.getLeftLeg().paint(builder, tb.getLeftLeg());
		painter.getRightLeg().paint(builder, tb.getRightLeg());
		painter.getLeftClaw().paint(builder, tb.getLeftClaw());
		painter.getRightClaw().paint(builder, tb.getRightClaw());
		return new Texture(builder.loadNormal());
	}
	
	private static AbstractModel createBirdHead(BirdHead head, TextureArea areaHead, TextureArea areaSnail, int textureWidth, int textureHeight){
		ModelBuilder builder = new ModelBuilder();
		addSphere(builder, 20, 0, head.headHeight() / 2, 0, head.headWidth() / 2, head.headHeight() / 2, head.headDepth() / 2, areaHead.getMinU(textureWidth), areaHead.getMinV(textureHeight), areaHead.getMaxU(textureWidth), areaHead.getMaxV(textureHeight));
		addHorizontalCilinder(builder, 10, 0, head.headHeight() / 2, head.snailRadius(), head.snailRadius(), 0, 0, 0, -head.snailLength(), areaSnail.getMinU(textureWidth), areaSnail.getMinV(textureHeight), areaSnail.getMaxU(textureWidth), areaSnail.getMaxV(textureHeight));
		return builder.load();
	}
	
	private static AbstractModel createCreatureHead(HeadProperties head, TextureArea area, int textureWidth, int textureHeight){
		ModelBuilder builder = new ModelBuilder();
		addSphere(builder, 20, 0, head.headHeight() / 2, 0, head.headWidth() / 2, head.headHeight() / 2, head.headDepth() / 2, area.getMinU(textureWidth), area.getMinV(textureHeight), area.getMaxU(textureWidth), area.getMaxV(textureHeight));
		return builder.load();
	}
	
	private static AbstractModel createTrollHead(TrollHeadProperties head, TextureArea area, TextureArea areaNose, int textureWidth, int textureHeight){
		ModelBuilder builder = new ModelBuilder();
		addSphere(builder, 20, 0, head.headHeight() / 2, 0, head.headWidth() / 2, head.headHeight() / 2, head.headDepth() / 2, area.getMinU(textureWidth), area.getMinV(textureHeight), area.getMaxU(textureWidth), area.getMaxV(textureHeight));
		int indexBottomNose = builder.vertices.size() / 3;
		float midU = (areaNose.getMinU(textureWidth) + areaNose.getMaxU(textureWidth)) / 2;
		float lowV = (areaNose.getMinV(textureHeight) + areaNose.getMaxV(textureHeight)) / 2;
		addVertex(builder, -head.noseWidth() / 2, head.headHeight() / 3, -head.headDepth() / 2, 0, 0, 1, areaNose.getMinU(textureWidth), lowV);
		addVertex(builder, 0, head.headHeight() / 3, -head.headDepth() / 2 - head.noseLengthFront() - head.noseLengthBack(), 0, 0, 1, midU, areaNose.getMinV(textureHeight));
		addVertex(builder, head.noseWidth() / 2, head.headHeight() / 3, -head.headDepth() / 2, 0, 0, 1, areaNose.getMaxU(textureWidth), lowV);
		int indexTopNose = builder.vertices.size() / 3;
		Vector3f v = new Vector3f(0, 0.3f, 0.6f);
		v.normalise();
		addVertex(builder, 0, head.headHeight() / 3 + head.noseHeight(), -head.headDepth() / 2 - head.noseLengthBack(), v.x, v.y, v.z, midU, lowV);
		addVertex(builder, 0, head.headHeight() / 3 + head.noseHeight(), -head.headDepth() / 2, 0, 0, 1, midU, areaNose.getMaxV(textureHeight));
		bindTriangle(builder, indexBottomNose, indexBottomNose + 1, indexTopNose);
		bindTriangle(builder, indexBottomNose + 1, indexBottomNose + 2, indexTopNose);
		bindTriangle(builder, indexBottomNose, indexTopNose, indexTopNose + 1);
		bindTriangle(builder, indexBottomNose + 2, indexTopNose, indexTopNose + 1);
		bindFourangle(builder, indexBottomNose, indexBottomNose + 1, indexBottomNose + 2, indexTopNose + 1);
		return builder.load();
	}
	
	private static AbstractModel createMyrreBelly(MyrreBelly belly, TextureArea ta, int tw, int th){
		ModelBuilder builder = new ModelBuilder();
		addSphere(builder, 10, 0, 0, 0, belly.bellyWidth() / 2, belly.bellyHeight() / 2, belly.bellyDepth() / 2, ta.getMinU(tw), ta.getMinV(th), ta.getMaxU(tw), ta.getMaxV(th));
		return builder.load();
	}
	
	private static AbstractModel createCreatureBelly(HumanoidBelly belly, TextureArea areaBelly, TextureArea areaTop, int textureWidth, int textureHeight){
		ModelBuilder builder = new ModelBuilder();
		//addSmoothTopsCilinder(builder, 10, 0, 0, belly.bellyWidth() / 2, belly.bellyDepth() / 2, -belly.bellyHeight() * 0.5f, -belly.bellyHeight() * 0.5f + belly.shoulderRadius(), belly.bellyHeight() * 0.5f, belly.bellyHeight() * 0.5f - belly.shoulderRadius(), 0, 0, 1, 1);
		addSphereTop(builder, 10, 0, belly.bellyHeight() / 2, 0, belly.bellyWidth() / 2, belly.bellyDepth() / 2, belly.bellyDepth() / 2, areaTop.getMinU(textureWidth), areaTop.getMinV(textureHeight), areaTop.getMaxU(textureWidth), areaTop.getMaxV(textureHeight));
		addVerticalCilinder(builder, 10, 0, 0, belly.bellyWidth() / 2, belly.bellyDepth() / 2, -belly.bellyHeight() * 0.5f, belly.bellyHeight() * 0.5f, areaBelly.getMinU(textureWidth), areaBelly.getMinV(textureHeight), areaBelly.getMaxU(textureWidth), areaBelly.getMaxV(textureHeight));
		return builder.load();
	}
	
	private static AbstractModel createCreatureSphere(HumanoidBelly belly, TextureArea areaBelly, int textureWidth, int textureHeight){
		ModelBuilder builder = new ModelBuilder();
		addSphere(builder, 10, 0, 0, 0, belly.bellyWidth() / 2, belly.bellyHeight() / 2, belly.bellyDepth() / 2, areaBelly.getMinU(textureWidth), areaBelly.getMinV(textureHeight), areaBelly.getMaxU(textureWidth), areaBelly.getMaxV(textureHeight));
		return builder.load();
	}
	
	private static AbstractModel createBirdWing(HumanoidBelly belly, BirdWing wing, TextureArea area, int textureWidth, int textureHeight){
		ModelBuilder builder = new ModelBuilder();
		float mv = area.getMinV(textureHeight);
		float dv = area.getMaxV(textureHeight) - mv;
		int i = 0;
		float[] parts = wing.wingPartLengths();
		for(float length : parts){
			addBox(builder, 0, 0, i * wing.wingPartHeight(), length * wing.wingPartMaxLength(), wing.wingPartDepth(), (i + 1) * wing.wingPartHeight(), area.getMinU(textureWidth), mv + i * dv / parts.length, area.getMaxU(textureWidth), mv + (i + 1) * dv / parts.length);
			i++;
		}
		return builder.load();
	}
	
	private static AbstractModel createMyrreArmPart(MyrreArm arm, TextureArea ta, int tw, int th, int index){
		ModelBuilder builder = new ModelBuilder();
		float minU = ta.getMinU(tw);
		float deltaU = ta.getMaxU(tw) - minU;
		addHorizontalCilinder(builder, 10, 0f, 0f, arm.armRadius(), arm.armRadius(), arm.armRadius(), arm.armRadius(), 0, -arm.armLength() / arm.armParts(), minU + deltaU * index / arm.armParts(), ta.getMinV(th), minU + (index + 1) * deltaU, ta.getMaxV(th));
		return builder.load();
	}
	
	private static AbstractModel createCreatureUpperArm(HumanoidArm arm, TextureArea areaArm, TextureArea areaShoulder, int textureWidth, int textureHeight){
		ModelBuilder builder = new ModelBuilder();
		addSphereSouth(builder, 10, 0, 0, 0, arm.shoulderRadius(), arm.shoulderRadius(), arm.shoulderRadius(), areaShoulder.getMinU(textureWidth), areaShoulder.getMinV(textureHeight), areaShoulder.getMaxU(textureWidth), areaShoulder.getMaxV(textureHeight));
		addHorizontalCilinder(builder, 10, 0f, 0f, arm.shoulderRadius(), arm.shoulderRadius(), arm.elbowRadius(), arm.elbowRadius(), 0, -arm.upperArmLength(), areaArm.getMinU(textureWidth), areaArm.getMinV(textureHeight), areaArm.getMaxU(textureWidth), areaArm.getMaxV(textureHeight));
		return builder.load();
	}
	
	private static AbstractModel createCreatureUnderArm(HumanoidArm arm, TextureArea area, int textureWidth, int textureHeight){
		ModelBuilder builder = new ModelBuilder();
		addHorizontalCilinder(builder, 10, 0f, 0f, arm.elbowRadius(), arm.elbowRadius(), arm.wristRadius(), arm.wristRadius(), 0, -arm.underArmLength(), area.getMinU(textureWidth), area.getMinV(textureHeight), area.getMaxU(textureWidth), area.getMaxV(textureHeight));
		return builder.load();
	}
	
	private static AbstractModel createMyrreHandClaw(MyrreHandClaw claw, MyrreArm arm, TextureArea area, int tw, int th, boolean left){
		ModelBuilder builder = new ModelBuilder();
		float minV = area.getMinV(th);
		float maxV = area.getMaxV(th);
		float midV = (maxV + minV) / 2;
		float minU = area.getMinU(tw);
		float maxU = area.getMaxU(tw);
		addPlaneTriangle(builder, 1, 0, 0, arm.armRadius(), -arm.armRadius(), 0, minU, minV, arm.armRadius(), -arm.armRadius() / 3, 0, minU, maxV, arm.armRadius(), -arm.armRadius() * 2 / 3, -claw.nailHandLength(), maxU, midV);
		addPlaneTriangle(builder, 1, 0, 0, arm.armRadius(), arm.armRadius(), 0, minU, minV, arm.armRadius(), arm.armRadius() / 3, 0, minU, maxV, arm.armRadius(), arm.armRadius() * 2 / 3, -claw.nailHandLength(), maxU, midV);
		if(left)
			mirrorX(builder);
		return builder.load();
	}
	
	private static AbstractModel createHumanoidHand(HumanoidHandProperties hand, TextureArea area, int textureWidth, int textureHeight, boolean left){
		ModelBuilder builder = new ModelBuilder();
		float minU = area.getMinU(textureWidth);
		float minV = area.getMinV(textureHeight);
		float maxU = area.getMaxU(textureWidth);
		float maxV = area.getMaxV(textureHeight);
		float deltaU = maxU - minU;
		float deltaV = maxV - minV;
		float vEdge = minV + deltaV * (hand.handCoreLength() / (hand.handCoreLength() + hand.fingerLength()));
		float uLeftTop = minU;
		float handCircle = hand.handWidth() * 2 + hand.handHeight() * 2;
		float uRightTop = minU + deltaU * (hand.handWidth() / handCircle);
		float uRightBottom = minU + deltaU * 0.5f;
		float uLeftBottom = minU + deltaU * ((2 * hand.handWidth() + hand.handHeight()) / handCircle);
		Vector3f lu = (Vector3f) new Vector3f(-1, 1, 0).normalise();
		float hw = hand.handWidth() / 2;
		float hh = hand.handHeight() / 2;
		float hcl = hand.handCoreLength();
		int indexBack = 0;
		addVertex(builder, -hw, hh, 0, lu.x, lu.y, lu.z, uLeftTop, minV);//left top
		addVertex(builder, hw, hh, 0, -lu.x, lu.y, lu.z, uRightTop, minV);//right top
		addVertex(builder, hw, -hh, 0, -lu.x, -lu.y, lu.z, uRightBottom, minV);//right bottom
		addVertex(builder, -hw, -hh, 0, lu.x, -lu.y, lu.z, uLeftBottom, minV);//left bottom
		addVertex(builder, -hw, hh, 0, lu.x, lu.y, lu.z, maxU, minV);//left top 2
		int indexEdge = builder.vertices.size() / 3;
		addVertex(builder, -hw, hh, -hcl, lu.x, lu.y, lu.z, uLeftTop, vEdge);//left top
		addVertex(builder, hw, hh, -hcl, -lu.x, lu.y, lu.z, uRightTop, vEdge);//right top
		addVertex(builder, hw, -hh, -hcl, -lu.x, -lu.y, lu.z, uRightBottom, vEdge);//right bottom
		addVertex(builder, -hw, -hh, -hcl, lu.x, -lu.y, lu.z, uLeftBottom, vEdge);//left bottom
		addVertex(builder, -hw, hh, -hcl, lu.x, lu.y, lu.z, maxU, vEdge);//left top 2
		float angle = 30f;
		float fl = hand.fingerLength() / 3;
		float sfl = Maths.sin(angle) * fl;
		float cfl = Maths.cos(angle) * fl;
		float sfl2 = Maths.sin(angle + angle) * fl;
		float cfl2 = Maths.cos(angle + angle) * fl;
		float sfl3 = Maths.sin(angle + angle + angle) * fl;
		float cfl3 = Maths.cos(angle + angle + angle) * fl;
		float vf1 = minV + deltaV * ((hcl + fl) / (hcl + hand.fingerLength()));
		float vf2 = minV + deltaV * ((hcl + fl + fl) / (hcl + hand.fingerLength()));
		float vf3 = maxV;
		int indexFinger1 = builder.vertices.size() / 3;
		addVertex(builder, -hw - sfl, hh, -hcl - cfl, lu.x, lu.y, lu.z, uLeftTop, vf1);//left top finger 1
		addVertex(builder, hw - sfl, hh, -hcl - cfl, -lu.x, lu.y, lu.z, uRightTop, vf1);//right top finger 1
		addVertex(builder, hw - sfl, -hh, -hcl - cfl, -lu.x, -lu.y, lu.z, uRightBottom, vf1);//right bottom finger 1
		addVertex(builder, -hw - sfl, -hh, -hcl - cfl, lu.x, -lu.y, lu.z, uLeftBottom, vf1);//left bottom finger 1
		addVertex(builder, -hw - sfl, hh, -hcl - cfl, lu.x, lu.y, lu.z, maxU, vf1);//left top finger 1 (2)
		int indexFinger2 = builder.vertices.size() / 3;
		addVertex(builder, -hw - sfl - sfl2, hh, -hcl - cfl - cfl2, lu.x, lu.y, lu.z, uLeftTop, vf2);//left top finger 2
		addVertex(builder, hw - sfl - sfl2, hh, -hcl - cfl - cfl2, -lu.x, lu.y, lu.z, uRightTop, vf2);//right top finger 2
		addVertex(builder, hw - sfl - sfl2, -hh, -hcl - cfl - cfl2, -lu.x, -lu.y, lu.z, uRightBottom, vf2);//right bottom finger 2
		addVertex(builder, -hw - sfl - sfl2, -hh, -hcl - cfl - cfl2, lu.x, -lu.y, lu.z, uLeftBottom, vf2);//left bottom finger 2
		addVertex(builder, -hw - sfl - sfl2, hh, -hcl - cfl - cfl2, lu.x, lu.y, lu.z, maxU, vf2);//left top finger 2 (2)
		int indexFinger3 = builder.vertices.size() / 3;
		addVertex(builder, -hw - sfl - sfl2 - sfl3, hh, -hcl - cfl - cfl2 - cfl3, lu.x, lu.y, lu.z, uLeftTop, vf3);//left top finger 3
		addVertex(builder, hw - sfl - sfl2 - sfl3, hh, -hcl - cfl - cfl2 - cfl3, -lu.x, lu.y, lu.z, uRightTop, vf3);//right top finger 3
		addVertex(builder, hw - sfl - sfl2 - sfl3, -hh, -hcl - cfl - cfl2 - cfl3, -lu.x, -lu.y, lu.z, uRightBottom, vf3);//right bottom finger 3
		addVertex(builder, -hw - sfl - sfl2 - sfl3, -hh, -hcl - cfl - cfl2 - cfl3, lu.x, -lu.y, lu.z, uLeftBottom, vf3);//left bottom finger 3
		addVertex(builder, -hw - sfl - sfl2 - sfl3, hh, -hcl - cfl - cfl2 - cfl3, lu.x, lu.y, lu.z, maxU, vf3);//left top finger 3 (2)
		if(left)
			mirrorX(builder);
		bindFourangle(builder, indexBack, indexBack + 1, indexEdge + 1, indexEdge);
		bindFourangle(builder, indexBack + 1, indexBack + 2, indexEdge + 2, indexEdge + 1);
		bindFourangle(builder, indexBack + 2, indexBack + 3, indexEdge + 3, indexEdge + 2);
		bindFourangle(builder, indexBack + 3, indexBack + 4, indexEdge + 4, indexEdge + 3);
		bindFourangle(builder, indexEdge, indexEdge + 1, indexFinger1 + 1, indexFinger1);
		bindFourangle(builder, indexEdge + 1, indexEdge + 2, indexFinger1 + 2, indexFinger1 + 1);
		bindFourangle(builder, indexEdge + 2, indexEdge + 3, indexFinger1 + 3, indexFinger1 + 2);
		bindFourangle(builder, indexEdge + 3, indexEdge + 4, indexFinger1 + 4, indexFinger1 + 3);
		bindFourangle(builder, indexFinger1, indexFinger1 + 1, indexFinger2 + 1, indexFinger2);
		bindFourangle(builder, indexFinger1 + 1, indexFinger1 + 2, indexFinger2 + 2, indexFinger2 + 1);
		bindFourangle(builder, indexFinger1 + 2, indexFinger1 + 3, indexFinger2 + 3, indexFinger2 + 2);
		bindFourangle(builder, indexFinger1 + 3, indexFinger1 + 4, indexFinger2 + 4, indexFinger2 + 3);
		bindFourangle(builder, indexFinger2, indexFinger2 + 1, indexFinger3 + 1, indexFinger3);
		bindFourangle(builder, indexFinger2 + 1, indexFinger2 + 2, indexFinger3 + 2, indexFinger3 + 1);
		bindFourangle(builder, indexFinger2 + 2, indexFinger2 + 3, indexFinger3 + 3, indexFinger3 + 2);
		bindFourangle(builder, indexFinger2 + 3, indexFinger2 + 4, indexFinger3 + 4, indexFinger3 + 3);
		bindFourangle(builder, indexFinger3, indexFinger3 + 1, indexFinger3 + 2, indexFinger3 + 3);
		return builder.load();
	}
	
	private static AbstractModel createBirdLeg(BirdClaw leg, TextureArea area, int textureWidth, int textureHeight){
		ModelBuilder builder = new ModelBuilder();
		float minU = area.getMinU(textureWidth);
		float minV = area.getMinV(textureHeight);
		float maxU = area.getMaxU(textureWidth);
		float maxV = area.getMaxV(textureHeight);
		addVerticalCilinder(builder, 10, 0f, 0f, leg.legRadius(), leg.legRadius(), leg.legRadius(), leg.legRadius(), 0, -leg.legLength(), minU, minV, maxU, maxV);
		int[][] indices = new int[leg.nailAmount()][12];
		float deltaAngle = leg.nailSpreadAngle() / leg.nailAmount();
		for(int i = 0; i < leg.nailAmount(); i++){
			float angleXZ = -leg.nailSpreadAngle() / 2 + i * deltaAngle;
			for(int j = 0; j <= 10; j++){
				float partY = (float)j / 10;
				float angle2 = partY * 360;
				indices[i][j] = addVertex(builder, leg.nailRadius() * Maths.cos(angle2) * Maths.cos(angleXZ), -leg.legLength() + Maths.sin(angle2) * leg.nailRadius(), leg.nailRadius() * Maths.cos(angle2) * Maths.sin(angleXZ), Maths.cos(angle2) * Maths.cos(angleXZ), Maths.sin(angle2), Maths.cos(angle2) * Maths.sin(angleXZ), minU, minV);//TODO fix the uv coords
			}
			indices[i][11] = addVertex(builder, (leg.legRadius() + leg.nailLength()) * Maths.sin(angleXZ), -leg.legLength(), -(leg.legRadius() + leg.nailLength()) * Maths.cos(angleXZ), Maths.sin(angleXZ), 0, -Maths.cos(angleXZ), maxU, maxV);
		}
		for(int i = 0; i < leg.nailAmount(); i++)
			for(int j = 0; j < 10; j++)
				bindTriangle(builder, indices[i][j], indices[i][j + 1], indices[i][11]);
		return builder.load();
	}
	
	private static ModelPart createMyrreLegPart(MyrreLeg leg, ModelTexture texture, TextureArea area, int tw, int th, int index, ModelPart next){
		ModelBuilder builder = new ModelBuilder();
		float minU = area.getMinU(tw);
		float deltaU = area.getMaxU(tw) - minU;
		addVerticalCilinder(builder, 10, 0, 0, leg.legRadius(), leg.legRadius(), -leg.legLength() / leg.legParts(), 0, minU + index * deltaU / leg.legParts(), area.getMinV(th), minU + (index + 1) * deltaU / leg.legParts(), area.getMaxV(th));
		return new ModelPartParent(builder.load(), texture, new Vector3f(0, -leg.legLength() / leg.legParts(), 0), next);
	}
	
	private static AbstractModel createCreatureUpperLeg(HumanoidLeg leg, TextureArea area, int textureWidth, int textureHeight){
		ModelBuilder builder = new ModelBuilder();
		addVerticalCilinder(builder, 10, 0f, 0f, leg.legUpperRadius(), leg.legUpperRadius(), leg.kneeRadius(), leg.kneeRadius(), 0, -leg.upperLegLength(), area.getMinU(textureWidth), area.getMinV(textureHeight), area.getMaxU(textureWidth), area.getMaxV(textureHeight));
		return builder.load();
	}
	
	private static AbstractModel createCreatureUnderLeg(HumanoidLeg leg, TextureArea area, TextureArea areaKnee, int textureWidth, int textureHeight){
		ModelBuilder builder = new ModelBuilder();
		addSphere(builder, 10, 0f, 0f, 0f, leg.kneeRadius(), leg.kneeRadius(), leg.kneeRadius(), areaKnee.getMinU(textureWidth), areaKnee.getMinV(textureHeight), areaKnee.getMaxU(textureWidth), areaKnee.getMaxV(textureHeight));
		addVerticalCilinder(builder, 10, 0f, 0f, leg.kneeRadius(), leg.kneeRadius(), leg.legUnderRadius(), leg.legUnderRadius(), 0, -leg.underLegLength(), area.getMinU(textureWidth), area.getMinV(textureHeight), area.getMaxU(textureWidth), area.getMaxV(textureHeight));
		return builder.load();
	}
	
	private static AbstractModel createMyrreFootClaw(MyrreFootClaw claw, MyrreLeg leg, TextureArea ta, int tw, int th){
		ModelBuilder builder = new ModelBuilder();
		float minU = ta.getMinU(tw);
		float minV = ta.getMinV(th);
		float maxU = ta.getMaxU(tw);
		float maxV = ta.getMaxV(th);
		float midV = (minV + maxV) / 2;
		addPlaneTriangle(builder, 0, 1, 0, -leg.legRadius(), 0, -leg.legRadius(), minU, minV, -leg.legRadius() / 3, 0, -leg.legRadius(), minU, maxV, -leg.legRadius() * 2 / 3, 0, -leg.legRadius() - claw.nailFootLength(), maxU, midV);
		addPlaneTriangle(builder, 0, 1, 0, -leg.legRadius() / 3, 0, -leg.legRadius(), minU, minV, leg.legRadius() / 3, 0, -leg.legRadius(), minU, maxV, 0, 0, -leg.legRadius() - claw.nailFootLength(), maxU, midV);
		addPlaneTriangle(builder, 0, 1, 0, leg.legRadius() / 3, 0, -leg.legRadius(), minU, minV, leg.legRadius(), 0, -leg.legRadius(), minU, maxV, leg.legRadius() * 2 / 3, 0, -leg.legRadius() - claw.nailFootLength(), maxU, midV);
		return builder.load();
	}
	
	private static AbstractModel createHumanoidFoot(HumanoidFootProperties foot, HumanoidLeg leg, TextureArea area, int textureWidth, int textureHeight){
		ModelBuilder builder = new ModelBuilder();
		float minV = area.getMinV(textureHeight);
		float minU = area.getMinU(textureWidth);
		float maxV = area.getMaxV(textureHeight);
		float maxU = area.getMaxU(textureWidth);
		float deltaU = maxU - minU;
		float deltaV = maxV - minV;
		float lowV = minV + 0.2f * deltaV;
		float lowVFront = minV + 0.4f * deltaV;
		float highVFront = minV + 0.6f * deltaV;
		int midParts = 5;
		int indexUpperCircle = builder.vertices.size() / 3;
		for(int i = 0; i <= midParts; i++){
			float angle = i / (float) midParts * 180f;
			addVertex(builder, Maths.cos(angle) * leg.legUnderRadius(), foot.footMidHeight(), -Maths.sin(angle) * leg.legUnderRadius(), Maths.cos(angle), 0, Maths.sin(angle), minU + (i / (float) midParts) * deltaU, minV);
		}
		int indexLowerCircle = builder.vertices.size() / 3;
		for(int i = 0; i <= midParts; i++){
			float angle = i / (float) midParts * 180f;
			addVertex(builder, Maths.cos(angle) * leg.legUnderRadius(), 0, -Maths.sin(angle) * leg.legUnderRadius(), Maths.cos(angle), 0, Maths.sin(angle), minU + (i / (float) midParts) * deltaU, lowV);
		}
		int indexLowFront = builder.vertices.size() / 3;
		addVertex(builder, -foot.footWidth() / 2, 0, 0, -1, 0, 0, minU, lowVFront);
		addVertex(builder, -foot.footWidth() / 2, 0, foot.footFrontLength(), -1, 0, 0, minU + 0.35f * deltaU, lowVFront);
		addVertex(builder, foot.footWidth() / 2, 0, foot.footFrontLength(), 1, 0, 0, minU + 0.65f * deltaU, lowVFront);
		addVertex(builder, foot.footWidth() / 2, 0, 0, 1, 0, 0, maxU, lowVFront);
		int indexHighFront = builder.vertices.size() / 3;
		addVertex(builder, -foot.footWidth() / 2, foot.footMidHeight(), 0, 0, 1, 0, maxU, highVFront);
		addVertex(builder, -foot.footWidth() / 2, foot.footOutHeight(), foot.footFrontLength(), 0, 1, 0, minU + 0.5f * deltaU, highVFront);
		addVertex(builder, foot.footWidth() / 2, foot.footOutHeight(), foot.footFrontLength(), 0, 1, 0, minU + 0.5f * deltaU, maxV);
		addVertex(builder, foot.footWidth() / 2, foot.footMidHeight(), 0, 0, 1, 0, maxU, maxV);
		bindFourangle2(builder, indexLowFront, indexLowFront + 1, indexHighFront, indexHighFront + 1);
		bindFourangle2(builder, indexLowFront + 1, indexLowFront + 2, indexHighFront + 1, indexHighFront + 2);
		bindFourangle2(builder, indexLowFront + 2, indexLowFront + 3, indexHighFront + 2, indexHighFront + 3);
		bindFourangle(builder, indexHighFront, indexHighFront + 1, indexHighFront + 2, indexHighFront + 3);
		bindFourangle(builder, indexLowFront, indexLowFront + 1, indexLowFront + 2, indexLowFront + 3);
		for(int i = 0; i < midParts; i++)
			bindFourangle(builder, indexLowerCircle + i, indexLowerCircle + i + 1, indexUpperCircle + i + 1, indexUpperCircle + i);
		return builder.load();
	}
	
	public static Texture createButtonTexture(Vector2f size, Color buttonColor, Color borderColor){
		int width = (int) (size.x * GameScreen.getWidth());
		int height = (int) (size.y * GameScreen.getHeight());
		TextureBuilder tb = new TextureBuilder(width, height, false);
		int bw = Math.max((int) (width * BUTTON_BORDER_FACTOR), 1);
		int bh = Math.max((int) (height * BUTTON_BORDER_FACTOR), 1);
		tb.fillRect(bw + 1, bh + 1, width - bw - 1, height - bh - 1, buttonColor);
		tb.fillRect(0, 0, bw, height - 1, borderColor);
		tb.fillRect(width - bw - 1, 0, width - 1, height - 1, borderColor);
		tb.fillRect(0, 0, width - 1, bh, borderColor);
		tb.fillRect(0, height - bh - 1, width - 1, height - 1, borderColor);
		return new Texture(tb.loadNormal());
	}
	
	public static Texture getTextTexture(String text, Color color){
		Texture texture = textTextureMap.get(new TextTexture(text, color));
		if(texture == null){
			texture = new Texture(loadTexture(createTextImage(text, color), true));
			textTextureMap.put(new TextTexture(text, color), texture);
		}
		return texture;
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
		return new SizedTexture(loadTexture(image, false), image.getWidth(), image.getHeight());
	}
	
	private static BufferedImage createTradeArrow(){
		BufferedImage image = new BufferedImage(100, 64, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setColor(java.awt.Color.YELLOW);
		int w = 67;
		g.fillRect(0, 20, w, 26);
		for(int i = 0; i < image.getHeight(); i++){
			g.drawLine(w, i, w + image.getHeight() / 2 - Math.abs(image.getHeight() / 2 - i), i);
		}
		g.dispose();
		return image;
	}
	
	public static SizedTexture creatureTradeOffersTexture(TradeOffer[] offers){
		if(offers.length == 0){
			BufferedImage image = new BufferedImage(600, 64, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.setColor(TRADE_OFFER_COLOR);
			g.fillRect(0, 0, image.getWidth(), image.getHeight());
			g.setColor(java.awt.Color.BLACK);
			g.drawRect(0, 0, image.getWidth() - 1, image.getHeight() - 1);
			g.setFont(TRADE_OFFER_FONT);
			g.drawString("There are no available trade offers.", 1, 40);
			g.dispose();
			return new SizedTexture(loadTexture(image, false), image.getWidth(), image.getHeight());
		}
		BufferedImage image = new BufferedImage(600, 64 * offers.length, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.setColor(TRADE_OFFER_COLOR);
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		g.setColor(java.awt.Color.BLACK);
		g.drawRect(0, 0, image.getWidth() - 1, image.getHeight() - 1);
		g.setFont(TRADE_OFFER_FONT);
		for(int i = 0; i < offers.length; i++){
			g.drawImage(TRADE_ARROW, 300, i * 64, null);
			g.drawLine(0, i * 64, image.getWidth(), i * 64);
			TradeOffer to = offers[i];
			ItemStack[] give = to.getItemsToGive();
			ItemStack[] get = to.getItemsToGet();
			for(int j = 0; j < give.length && j < 4; j++){
				g.drawString(give[j].getAmount() + "", 5 + j * 100, i * 64 + 40);
				g.drawImage(give[j].getItem().getTexture().getImage(), 50 + j * 100, i * 64, 64, 64, null);
			}
			for(int j = 0; j < get.length && j < 4; j++){
				g.drawString(get[j].getAmount() + "", 305 + TRADE_ARROW.getWidth() + j * 100, i * 64 + 40);
				g.drawImage(get[j].getItem().getTexture().getImage(), 336 + TRADE_ARROW.getWidth() + j * 100, i * 64, 64, 64, null);
			}
		}
		g.dispose();
		return new SizedTexture(loadTexture(image, false), image.getWidth(), image.getHeight());
	}
	
	public static SizedTexture createTradeOfferTexture(TradeOffer offer){
		BufferedImage image = new BufferedImage(600, 500, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.setColor(new java.awt.Color(0, 150, 150));
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		g.setColor(new java.awt.Color(0, 50, 50));
		g.fillRect(0, 0, image.getWidth() / 10, image.getHeight());
		g.fillRect(0, 0, image.getWidth(), image.getHeight() / 10);
		g.fillRect(0, image.getHeight() / 10 * 9, image.getWidth(), image.getHeight() / 10);
		g.fillRect(image.getWidth() / 10 * 9, 0, image.getWidth() / 10, image.getHeight());
		g.setFont(TRADE_OFFER_FONT);
		g.setColor(java.awt.Color.BLACK);
		g.drawString("Trade Offer", 190, 90);
		g.drawString("You are going to pay:", 75, 150);
		g.drawString("You are going to receive:", 75, 250);
		ItemStack[] give = offer.getItemsToGive();
		for(int j = 0; j < give.length && j < 4; j++){
			g.drawString(give[j].getAmount() + "", 75 + j * 100, 155 + 40);
			g.drawImage(give[j].getItem().getTexture().getImage(), 105 + j * 100, 155, 64, 64, null);
		}
		ItemStack[] get = offer.getItemsToGet();
		for(int j = 0; j < get.length && j < 4; j++){
			g.drawString(get[j].getAmount() + "", 75 + j * 100, 255 + 40);
			g.drawImage(get[j].getItem().getTexture().getImage(), 105 + j * 100, 255, 64, 64, null);
		}
		g.setColor(java.awt.Color.GREEN);
		g.fillRect(image.getWidth() / 6, image.getHeight() / 10 * 7, image.getWidth() / 4, image.getHeight() / 20 * 3);
		g.setColor(java.awt.Color.RED);
		g.fillRect(image.getWidth() * 7 / 12, image.getHeight() / 10 * 7, image.getWidth() / 4, image.getHeight() / 20 * 3);
		g.setColor(java.awt.Color.BLACK);
		g.drawRect(image.getWidth() / 6, image.getHeight() / 10 * 7, image.getWidth() / 4, image.getHeight() / 20 * 3);
		g.drawRect(image.getWidth() * 7 / 12, image.getHeight() / 10 * 7, image.getWidth() / 4, image.getHeight() / 20 * 3);
		g.drawString("Buy", image.getWidth() / 6 + image.getWidth() / 13, image.getHeight() / 10 * 8);
		g.drawString("Cancel", image.getWidth() * 7 / 12 + image.getWidth() / 30, image.getHeight() / 10 * 8);
		return new SizedTexture(loadTexture(image, false), image.getWidth(), image.getHeight());
	}
	
	public static Texture createBorderTexture(Color color, float width, float height, int lineWidth){
		int w = (int) (width * GameScreen.getWidth());
		int h = (int) (height * GameScreen.getHeight());
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setColor(color.toAWTColor());
		g.fillRect(0, 0, w, lineWidth);
		g.fillRect(0, h - lineWidth, w, lineWidth);
		g.fillRect(0, 0, lineWidth, h);
		g.fillRect(w - lineWidth, 0, lineWidth, h);
		return new Texture(loadTexture(image, true));
	}
	
	private static BufferedImage createTextImage(String text, Color color){
		BufferedImage image = new BufferedImage(256, 64, BufferedImage.TYPE_INT_ARGB);
		if(text.trim().isEmpty())
			return image;
		Graphics2D g2 = image.createGraphics();
		g2.setFont(new Font("TimesRoman", 0, 40));
		Rectangle2D bounds = g2.getFontMetrics().getStringBounds(text, g2);
		double preferredWidth = image.getWidth() * 0.9;
		double preferredHeight = image.getHeight() * 0.9;
		double factor = min(preferredWidth / bounds.getWidth(), preferredHeight / bounds.getHeight());
		g2.setColor(color.toAWTColor());
		g2.setFont(new Font("TimesRoman", 0, (int) (40 * factor)));
		Rectangle2D newBounds = g2.getFontMetrics().getStringBounds(text, g2);
		g2.drawString(text, (int) ((image.getWidth() - newBounds.getWidth()) / 2), (int) ((image.getHeight() - newBounds.getCenterY()) / 2));
		g2.setColor(java.awt.Color.BLACK);
		g2.drawLine(0, 0, image.getWidth() - 1, 0);
		g2.drawLine(0, 0, 0, image.getHeight() - 1);
		g2.drawLine(0, image.getHeight() - 1, image.getWidth() - 1, image.getHeight() - 1);
		g2.drawLine(image.getWidth() - 1, 0, image.getWidth() - 1, image.getHeight() - 1);
		return image;
	}
	
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
		builder.fillAverage(0, 0, builder.width - 1, builder.height - 1, color, 0.3f, new Random(seed));
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
		builder.fillAverage(0, 0, builder.width - 1, builder.height - 1, baseColor, 0.2f, random);
		for(int i = 0; i < extraColors.length; i++)
			builder.fillAverageChance(0, 0, builder.width - 1, builder.height - 1, extraColors[i], 0.2f, random, extraChances[i]);
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
		builder.fillBrickPattern(0, 0, builder.width - 1, builder.height - 1, brickLength, brickHeight, brickColor, edgeColor, 0.2f, new Random(seed));
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
		builder.fillBrickPattern(0, 0, builder.width - 1, builder.height - 1, brickLength, brickHeight, brickColor, edgeColor, 0.2f, new Random(seed));
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
		builder.fillBrickPattern(0, 0, builder.width - 1, builder.height - 1, brickLength, brickHeight, brickColor, edgeColor, 0.2f, new Random(seed));
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
		builder.fillWoodPlanksPattern(0, 0, builder.width - 1, builder.height - 1, plankLength, plankHeight, plankShift, plankColor, edgeColor, 0.3f, new Random(seed));
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
		builder.fillWoodPlanksPattern(0, 0, builder.width - 1, builder.height - 1, plankLength, plankHeight, plankShift, plankColor, edgeColor, 0.3f, new Random(seed));
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
		builder.fillAverage(0, 0, builder.width - 1, builder.height - 1, baseColor, 0.2f, random);
		for(int i = 0; i < extraColors.length; i++)
			builder.fillAverageChance(0, 0, builder.width - 1, builder.height - 1, extraColors[i], 0.2f, random, extraChances[i]);
		for(int y = 0; y < builder.width; y++){
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
		builder.fillWoodPlanksPattern(0, 0, builder.width - 1, builder.height - 1, plankLength, plankHeight, plankShift, plankColor, edgeColor, 0.3f, random);
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
	
	public static Texture[] createThinPathTextures(int amount, Color color, float maxDifference){
		Texture[] textures = new Texture[amount];
		for(int i = 0; i < textures.length; i++)
			textures[i] = createThinPathTexture(color, maxDifference, i * 139429);
		return textures;
	}
	
	private static Texture createThinPathTexture(Color color, float maxDifference, long seed){
		TextureBuilder builder = new TextureBuilder(32, 32, true);
		builder.fillRect(0, 0, builder.width - 1, builder.height - 1, ColorAlpha.TRANSPARENT);
		builder.fillAverageChance(0, 0, builder.width - 1, builder.height - 1, color, maxDifference, new Random(seed), 1, 6);
		return new Texture(builder.loadNormal());
	}
	
	public static Texture createLiquidTexture(Color color){
		TextureBuilder builder = new TextureBuilder(8, 8, false);
		builder.fillRect(0, 0, builder.width - 1, builder.height - 1, color);
		return new Texture(builder.loadNormal());
	}
	
	private static void createBluePrintImage(TextureMarker marker){
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
		TextureBuilder builder = new TextureBuilder(width, height, alpha);
		pattern.paint(builder, 0, 0, width, height);
		return new ModelTexture(new Texture(builder.loadNormal()), shineDamper, reflectivity);
	}
	
	/*
	private static void createA(int imageSize, GuiColor color){
		//a
		int size = (int) (CHAR_SIZE * imageSize);
		int width = size;
		int height = size + size / 2;
		TextureBuilder tb = new TextureBuilder(width, height, true);
		tb.fillRect(0, 0, width - 1, height - 1, (byte) 128, (byte) -128, (byte) -128, (byte) -128);
		tb.fillRect(minX, minY, maxX, maxY, color);
	}
	*/
	
	private static class ModelBuilder {
		
		private FloatList vertices = new ArrayFloatList(20);
		private FloatList normals = new ArrayFloatList(20);
		private FloatList textures = new ArrayFloatList(20);
		private ArrayList<Integer> indices = new ArrayList<Integer>();
		
		private AbstractModel load(){
			return loadDefaultModel(vertices, normals, textures, indices);
		}
	}
	
	public static class TextureBuilder {
		
		private byte[] data;
		
		private int width;
		private int height;
		
		private boolean useAlpha;
		
		private TextureBuilder(int width, int height, boolean useAlpha){
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
		
		private int loadNormal(){
			return loadTexture(data, width, height, useAlpha);
		}
	}
	
	private static class TextTexture implements Comparable<TextTexture> {
		
		private final String text;
		private final Color color;
		
		private TextTexture(String text, Color color){
			this.text = text;
			this.color = color;
		}
		
		@Override
		public int hashCode(){
			return text.hashCode() + color.hashCode();
		}
		
		@Override
		public boolean equals(Object other){
			if(other instanceof TextTexture){
				TextTexture tt = (TextTexture) other;
				return tt.text.equals(text) && tt.color.equals(color);
			}
			return false;
		}
		
		@Override
		public int compareTo(TextTexture other){
			int c = color.compareTo(other.color);
			if(c != 0)
				return c;
			return text.compareTo(other.text);
		}
	}
}
