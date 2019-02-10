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
package nl.knokko.render.battle;

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.render.properties.BattleRenderProperties;
import nl.knokko.model.factory.ModelLoader;
import nl.knokko.model.type.AbstractModel;
import nl.knokko.model.type.GuiModel;
import nl.knokko.view.camera.Camera;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import static nl.knokko.shaders.battle.ResourceShader.RESOURCE_SHADER;

public class Battle2dRenderer {

	public static final GuiModel QUAD = ModelLoader.loadGuiModel(new float[] { -1, 1, -1, -1, 1, 1, 1, -1 });

	public void startResourceShader(Matrix4f viewMatrix) {
		RESOURCE_SHADER.start();
		RESOURCE_SHADER.loadViewMatrix(viewMatrix);
		prepareModel(QUAD);
	}

	public void renderResources(Camera camera, BattleCreature creature) {
		BattleRenderProperties props = creature.getRenderProperties();
		renderHealth(camera, props, creature.getHealth(), creature.getMaxHealth());
		renderMana(camera, props, creature.getMana(), creature.getMaxMana());
		renderFocus(props, creature.getFocus(), creature.getMaxFocus());
	}

	private void prepareModel(AbstractModel model) {
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
	}

	private void unbindModel() {
		GL30.glBindVertexArray(0);
		GL20.glDisableVertexAttribArray(0);
	}

	private void renderHealth(Camera camera, BattleRenderProperties props, long health, long maxHealth) {
		RESOURCE_SHADER.setHealthColor();
		renderResource(props.getHealthX(camera), props.getHealthY(camera), props.getHealthZ(camera),
				props.getHealthWidth() / 2, props.getHealthHeight() / 2, health, maxHealth);
	}

	private void renderMana(Camera camera, BattleRenderProperties props, long mana, long maxMana) {
		if (maxMana > 0) {
			RESOURCE_SHADER.setManaColor();
			renderResource(props.getManaX(camera), props.getManaY(camera), props.getManaZ(camera),
					props.getManaWidth() / 2, props.getManaHeight() / 2, mana, maxMana);
		}
	}

	private void renderFocus(BattleRenderProperties props, long focus, long maxFocus) {
		if (maxFocus > 0) {
			RESOURCE_SHADER.setFocusColor();
		}
	}

	private void renderResource(float midX, float midY, float midZ, float scaleX, float scaleY, long current,
			long max) {
		RESOURCE_SHADER.loadProgress((float) current / max);
		RESOURCE_SHADER.loadRenderPosition(new Vector3f(midX, midY, midZ));
		RESOURCE_SHADER.loadScale(new Vector2f(scaleX, scaleY));
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, QUAD.getVertexCount());
	}

	public void stopResourceShader() {
		RESOURCE_SHADER.stop();
		unbindModel();
	}
}