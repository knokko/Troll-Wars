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
package nl.knokko.shaders.battle;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import nl.knokko.main.Game;
import nl.knokko.shaders.ShaderProgram;

public class Battle2dShader extends ShaderProgram {
	
	public static final String VERTEX_FILE = "nl/knokko/shaders/battle/battle2d.vshad";
	public static final String FRAGMENT_FILE = "nl/knokko/shaders/battle/battle2d.fshad";
	
	public static final Battle2dShader BATTLE_2D_SHADER = new Battle2dShader();
	
	private int locationViewMatrix;
	private int locationScale;
	private int locationWorldPosition;

	private Battle2dShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		locationViewMatrix = getUniformLocation("projViewMatrix");
		locationWorldPosition = getUniformLocation("worldPosition");
		locationScale = getUniformLocation("scale");
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "modelPosition");
		bindAttribute(1, "textureCoords");
	}
	
	public void loadViewMatrix(Matrix4f viewMatrix){
		loadMatrix(locationViewMatrix, Matrix4f.mul(Game.getProjectionMatrix(), viewMatrix, null));
	}
	
	public void loadWorldPosition(Vector3f position){
		loadVector(locationWorldPosition, position);
	}
	
	public void laodScale(Vector2f scale){
		loadVector(locationScale, scale);
	}
}