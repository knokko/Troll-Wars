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

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class CastingShader extends ShaderProgram {
	
	public static final String VERTEX_FILE = "nl/knokko/shaders/casting.vshad";
	public static final String FRAGMENT_FILE = "nl/knokko/shaders/casting.fshad";
	
	public static final CastingShader CASTING_SHADER = new CastingShader();
	
	private int locationHandPosition;
	private int locationColor;
	private int locationProjectionMatrix;

	private CastingShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		locationHandPosition = getUniformLocation("handPosition");
		locationColor = getUniformLocation("color");
		locationProjectionMatrix = getUniformLocation("projectionMatrix");
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
	}
	
	public void loadHandPosition(Vector3f handPosition){
		loadVector(locationHandPosition, handPosition);
	}
	
	public void loadColor(Vector3f color){
		loadVector(locationColor, color);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix){
		loadMatrix(locationProjectionMatrix, matrix);
	}
}