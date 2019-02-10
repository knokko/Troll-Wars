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
package nl.knokko.battle.decoration;

import org.lwjgl.util.vector.Matrix4f;

import nl.knokko.main.Game;
import nl.knokko.model.ModelPart;
import nl.knokko.shaders.ShaderType;
import nl.knokko.util.Maths;
import nl.knokko.util.color.Color;
import nl.knokko.view.camera.Camera;
import nl.knokko.view.light.Light;

public abstract class SimpleBattleDecoration extends BattleDecoration {
	
	private final byte id;
	
	private final Color backgroundColor;
	private final Light light;
	
	private ModelPart[] model;
	
	public SimpleBattleDecoration(byte id, Color backgroundColor, Light light) {
		this.id = id;
		this.backgroundColor = backgroundColor;
		this.light = light;
	}

	@Override
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	@Override
	public Light getLight() {
		return light;
	}
	
	@Override
	public void render(Camera camera) {
		Game.getCreatureRenderer().prepare(false);
		Game.getCreatureRenderer().prepareWorldShader(camera, getLight());
		Matrix4f parentMatrix = Maths.createTransformationMatrix(camera.getPosition().negate(null), 1);
		for (ModelPart model : this.model) {
			Game.getCreatureRenderer().prepareModel(model.getModel(), ShaderType.NORMAL);
			Game.getCreatureRenderer().prepareTexture(model.getTexture(), ShaderType.NORMAL);
			Game.getCreatureRenderer().renderInstance(model.getMatrix(parentMatrix), ShaderType.NORMAL, model.getModel().getVertexCount());
			// TODO maybe render child parts as well
		}
	}
	
	@Override
	public void refreshModel() {
		model = createModel();
	}
	
	protected abstract ModelPart[] createModel();
	
	@Override
	public byte getID() {
		return id;
	}
}