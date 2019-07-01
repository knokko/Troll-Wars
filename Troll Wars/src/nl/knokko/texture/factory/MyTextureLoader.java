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

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGB8;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import nl.knokko.main.Game;
import nl.knokko.texture.Texture;
import nl.knokko.texture.builder.ByteArrayTextureBuilder;
import nl.knokko.texture.builder.TextureBuilder;

public class MyTextureLoader {
	
	private static ArrayList<Integer> textures = new ArrayList<Integer>();
	
	public static Texture[] loadGuiMoveTexture(String moveName){
		Texture[] textures = new Texture[3];
		String pre = "gui/moves/";
		textures[0] = new Texture(loadTexture(pre + "learned" + File.separator + moveName));
		textures[1] = new Texture(loadTexture(pre + "notlearned" + File.separator + moveName));
		textures[2] = new Texture(loadTexture(pre + "cantlearn" + File.separator + moveName));
		return textures;
	}
	
	public static int loadTexture(String fileName){
		/*
		org.newdawn.slick.opengl.Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", Resources.class.getClassLoader().getResource("textures/" + fileName + ".png").openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		int id = Game.getWindow().getTextureLoader().loadTexture("textures/" + fileName + ".png").getTextureID();
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
	
	public static int loadTexture(TextureBuilder textureBuilder) {
		return loadTexture(textureBuilder.createArrrayRGBA(), textureBuilder.width(), textureBuilder.height(), textureBuilder.useAlpha());
	}
	
	public static TextureBuilder createTextureBuilder(int width, int height, boolean useAlpha) {
		return new ByteArrayTextureBuilder(width, height, useAlpha);
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
		for(int texture : textures)
			GL11.glDeleteTextures(texture);
	}
}