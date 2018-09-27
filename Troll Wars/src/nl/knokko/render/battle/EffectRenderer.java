/* 
 * The MIT License
 *
 * Copyright 2018 knokko.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
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
 */
package nl.knokko.render.battle;

import java.util.HashMap;
import java.util.Map;

import nl.knokko.model.type.SimpleEffectModel;
import nl.knokko.util.Maths;
import nl.knokko.util.resources.Resources;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import static nl.knokko.shaders.battle.SimpleEffectShader.SIMPLE_EFFECT_SHADER;

public class EffectRenderer {
	
	//always 120 degree angle
	private static final float[] PARTICLE_POSITIONS = {0,1,0,  0,-0.5f,Maths.sqrt(3)/2, Maths.sqrt(3)/2,-0.5f,-0.5f, -Maths.sqrt(3)/2,-0.5f,-0.5f};
	private static final int[] PARTICLE_INDICES = {0,1,2, 0,2,3, 0,3,1, 1,2,3};
	private static final int[] SPHERE_INDICES = createSphereIndices();
	
	private static final int SPHERE_ANGLE_PARTS = 180;
	private static final int SPHERE_HEIGHT_PARTS = 90;
	
	private static float[] createSpherePositions(float radius){
		float[] positions = new float[SPHERE_ANGLE_PARTS * SPHERE_HEIGHT_PARTS * 3];
		int index = 0;
		for(int h = 0; h < SPHERE_HEIGHT_PARTS; h++){
			float height = ((float) h / (SPHERE_HEIGHT_PARTS - 1)) * 2 * radius - radius;
			for(int a = 0; a < SPHERE_ANGLE_PARTS; a++){
				float angle = ((float)a / (SPHERE_ANGLE_PARTS - 1)) * 360;
				float length = Maths.sqrt(radius * radius - height * height);
				positions[index++] = Maths.cos(angle) * length;
				positions[index++] = height;
				positions[index++] = Maths.sin(angle) * length;
			}
		}
		return positions;
	}
	
	private static int[] createSphereIndices(){
		int[] indices = new int[6 * (SPHERE_HEIGHT_PARTS - 1) * (SPHERE_ANGLE_PARTS - 1)];
		int index = 0;
		for(int h = 0; h < SPHERE_HEIGHT_PARTS - 1; h++){
			for(int a = 0; a < SPHERE_ANGLE_PARTS - 1; a++){
				indices[index++] = a + h * SPHERE_ANGLE_PARTS;
				indices[index++] = a + 1 + h * SPHERE_ANGLE_PARTS;
				indices[index++] = a + 1 + (h + 1) * SPHERE_ANGLE_PARTS;
				indices[index++] = a + 1 + (h + 1) * SPHERE_ANGLE_PARTS;
				indices[index++] = a + (h + 1) * SPHERE_ANGLE_PARTS;
				indices[index++] = a + h * SPHERE_ANGLE_PARTS;
			}
		}
		return indices;
	}
	
	private static SimpleEffectModel createSphereModel(float radius){
		return Resources.loadSimpleEffectModel(createSpherePositions(radius), SPHERE_INDICES);
	}
	
	private final SimpleEffectModel simpleModel = Resources.loadSimpleEffectModel(PARTICLE_POSITIONS, PARTICLE_INDICES);
	
	private final Map<Float,SimpleEffectModel> sphereModels = new HashMap<Float,SimpleEffectModel>();
	
	public void startSimpleShader(Matrix4f viewMatrix){
		SIMPLE_EFFECT_SHADER.start();
		SIMPLE_EFFECT_SHADER.loadViewMatrix(viewMatrix);
	}
	
	public void stopSimpleShader(){
		SIMPLE_EFFECT_SHADER.stop();
	}
	
	public void renderParticles(Vector3f[] positions, Vector3f color){
		prepareSimpleModel(simpleModel);
		SIMPLE_EFFECT_SHADER.loadColor(color);
		for(Vector3f position : positions){
			SIMPLE_EFFECT_SHADER.loadPosition(position);
			GL11.glDrawElements(GL11.GL_TRIANGLES, simpleModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		}
		unbindSimpleModel();
	}
	
	private SimpleEffectModel getSphereModel(float radius){
		SimpleEffectModel model = sphereModels.get(new Float(radius));
		if(model == null){
			model = createSphereModel(radius);
			sphereModels.put(new Float(radius), model);
		}
		return model;
	}
	
	public void renderSphere(Vector3f centre, float radius, Vector3f color){
		prepareSimpleModel(getSphereModel(radius));
		SIMPLE_EFFECT_SHADER.loadColor(color);
		SIMPLE_EFFECT_SHADER.loadPosition(centre);
		GL11.glDrawElements(GL11.GL_TRIANGLES, SPHERE_INDICES.length, GL11.GL_UNSIGNED_INT, 0);
		unbindSimpleModel();
	}
	
	private void prepareSimpleModel(SimpleEffectModel model){
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
	}
	
	private void unbindSimpleModel(){
		GL30.glBindVertexArray(0);
		GL20.glDisableVertexAttribArray(0);
	}
}