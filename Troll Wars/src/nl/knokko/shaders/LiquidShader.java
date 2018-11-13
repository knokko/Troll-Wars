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
package nl.knokko.shaders;

import nl.knokko.main.Game;
import nl.knokko.util.Maths;
import nl.knokko.view.camera.Camera;

import org.lwjgl.util.vector.Matrix4f;

public class LiquidShader extends ShaderProgram {
	
	public static final String VERTEX_FILE = "nl/knokko/shaders/liquid.vshad";
	public static final String FRAGMENT_FILE = "nl/knokko/shaders/liquid.fshad";
	
	public static final LiquidShader LIQUID_SHADER = new LiquidShader();
	
	private int locationTransformationMatrix;
	private int locationViewMatrix;
	
	private int locationRandom;
	
	private float randomizer;

	private LiquidShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		locationTransformationMatrix = getUniformLocation("transformationMatrix");
		locationViewMatrix = getUniformLocation("viewMatrix");
		locationRandom = getUniformLocation("rand");
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
		bindAttribute(1, "textureCoords");
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		loadMatrix(locationTransformationMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera camera){
		loadMatrix(locationViewMatrix, Matrix4f.mul(Game.getProjectionMatrix(), Maths.createViewMatrix(camera), null));
	}
	
	public void loadRandomizer(){
		randomizer += 0.001f;
		loadFloat(locationRandom, randomizer);
	}
}
