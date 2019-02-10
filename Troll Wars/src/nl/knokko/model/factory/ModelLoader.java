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
package nl.knokko.model.factory;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

import nl.knokko.model.type.DefaultModel;
import nl.knokko.model.type.DefaultTileModel;
import nl.knokko.model.type.GuiModel;
import nl.knokko.model.type.LiquidModel;
import nl.knokko.model.type.LiquidTileModel;
import nl.knokko.model.type.SimpleEffectModel;
import nl.knokko.model.type.SpiritModel;
import nl.knokko.util.collection.floating.FloatList;

public class ModelLoader {
	
	private static ArrayList<Integer> vaos = new ArrayList<Integer>();
	private static ArrayList<Integer> vbos = new ArrayList<Integer>();
	
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
	
	public static void cleanUp(){
		for(int vao : vaos)
			GL30.glDeleteVertexArrays(vao);
		for(int vbo : vbos)
			GL15.glDeleteBuffers(vbo);
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
}