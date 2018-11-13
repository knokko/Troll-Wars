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
package nl.knokko.render.main;

import nl.knokko.model.type.AbstractModel;
import nl.knokko.shaders.LiquidShader;
import nl.knokko.shaders.ShaderType;
import nl.knokko.shaders.SpiritShader;
import nl.knokko.shaders.WorldShader;
import nl.knokko.texture.ModelTexture;
import nl.knokko.view.camera.Camera;
import nl.knokko.view.light.Light;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector4f;

import static nl.knokko.shaders.WorldShader.WORLD_SHADER;
import static nl.knokko.shaders.LiquidShader.LIQUID_SHADER;
import static nl.knokko.shaders.SpiritShader.SPIRIT_SHADER;

public abstract class WorldRenderer {

	public static final float FOV = 70;
	//private static final float NEAR_PLANE = 0.1f;
	//private static final float FAR_PLANE = 10000;
	public static final Vector4f NO_EFFECT_COLOR = new Vector4f(0, 0, 0, 0);
	
	//private Matrix4f projectionMatrix;
	
	public WorldRenderer(){
		/*
		createProjectionMatrix();
		worldShader.start();
		worldShader.loadProjectionMatrix(projectionMatrix);
		worldShader.stop();
		liquidShader.start();
		liquidShader.loadProjectionMatrix(projectionMatrix);
		liquidShader.stop();
		spiritShader.start();
		spiritShader.loadProjectionMatrix(projectionMatrix);
		spiritShader.stop();
		*/
	}
	
	public WorldShader getWorldShader(){
		return WORLD_SHADER;
	}
	
	public LiquidShader getLiquidShader(){
		return LIQUID_SHADER;
	}
	
	public SpiritShader getSpiritShader(){
		return SPIRIT_SHADER;
	}
	
	public void prepareShader(Camera camera, Light light, ShaderType shader){
		if(shader == ShaderType.NORMAL)
			prepareWorldShader(camera, light);
		else if(shader == ShaderType.LIQUID)
			prepareLiquidShader(camera, light);
		else
			prepareSpiritShader(camera, light);
	}
	
	public void prepareWorldShader(Camera camera, Light light){
		WORLD_SHADER.start();
		WORLD_SHADER.loadViewMatrix(camera);
		WORLD_SHADER.loadEffectColor(NO_EFFECT_COLOR);
	}
	
	public void prepareLiquidShader(Camera camera, Light light){
		LIQUID_SHADER.start();
		LIQUID_SHADER.loadViewMatrix(camera);
		LIQUID_SHADER.loadRandomizer();
	}
	
	public void prepareSpiritShader(Camera camera, Light light){
		SPIRIT_SHADER.start();
		SPIRIT_SHADER.loadViewMatrix(camera);
		SPIRIT_SHADER.loadRandomizer();
	}
	
	public void prepare(boolean clear){
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		if(clear){
			GL11.glClearColor(0, 0, 0, 1);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		}
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public void prepareModel(AbstractModel model, ShaderType shaderType){
		if(shaderType == ShaderType.NORMAL)
			prepareNormalModel(model);
		else if(shaderType == ShaderType.LIQUID)
			prepareLiquidModel(model);
		else
			prepareSpiritModel(model);
	}
	
	protected void prepareNormalModel(AbstractModel model){
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
	}
	
	protected void prepareLiquidModel(AbstractModel model){
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	protected void prepareSpiritModel(AbstractModel model){
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	public void prepareTexture(ModelTexture texture, ShaderType type){
		if(type == ShaderType.NORMAL)
			prepareNormalTexture(texture);
		else if(type == ShaderType.LIQUID)
			prepareLiquidTexture(texture);
		else
			prepareSpiritTexture(texture);
	}
	
	protected void prepareNormalTexture(ModelTexture texture){
		WORLD_SHADER.loadShine(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
	}
	
	protected void prepareLiquidTexture(ModelTexture texture){
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
	}
	
	protected void prepareSpiritTexture(ModelTexture texture){
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
	}
	
	public void unbindModel(ShaderType type){
		if(type == ShaderType.NORMAL)
			unbindNormalModel();
		else if(type == ShaderType.LIQUID)
			unbindLiquidModel();
		else
			unbindSpiritModel();
	}
	
	protected void unbindNormalModel(){
		GL30.glBindVertexArray(0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
	}
	
	protected void unbindLiquidModel(){
		GL30.glBindVertexArray(0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	protected void unbindSpiritModel(){
		GL30.glBindVertexArray(0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	/*
	private void createProjectionMatrix(){
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }
    */
}
