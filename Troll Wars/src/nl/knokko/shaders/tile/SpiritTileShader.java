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
package nl.knokko.shaders.tile;

import nl.knokko.main.Game;
import nl.knokko.util.Maths;
import nl.knokko.view.camera.Camera;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class SpiritTileShader extends TileShader {
	
	public static final String VERTEX_FILE = "nl/knokko/shaders/tile/spirit.vshad";
	public static final String FRAGMENT_FILE = "nl/knokko/shaders/tile/spirit.fshad";
	
	public static final SpiritTileShader SPIRIT_TILE_SHADER = new SpiritTileShader();
	
	private int locationTilePosition;
	private int locationViewMatrix;
	
	private int locationRandom;
	
	private float randomizer;

	private SpiritTileShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		locationTilePosition = getUniformLocation("tilePosition");
		locationViewMatrix = getUniformLocation("viewMatrix");
		locationRandom = getUniformLocation("rand");
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "modelPosition");
		bindAttribute(1, "textureCoords");
	}
	
	@Override
	public void loadTilePosition(Vector3f position){
		loadVector(locationTilePosition, position);
	}
	
	public void loadViewMatrix(Camera camera){
		loadMatrix(locationViewMatrix, Matrix4f.mul(Game.getProjectionMatrix(), Maths.createOriginViewMatrix(camera), null));
	}
	
	public void loadRandomizer(){
		randomizer += 0.01f;
		if(randomizer > 1f)
			randomizer -= 1f;
		loadFloat(locationRandom, randomizer);
	}
}
