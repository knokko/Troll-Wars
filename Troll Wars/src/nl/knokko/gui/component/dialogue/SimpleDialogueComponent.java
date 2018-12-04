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
package nl.knokko.gui.component.dialogue;

import nl.knokko.gui.component.AbstractGuiComponent;
import nl.knokko.gui.dialogue.GuiDialogue;
import nl.knokko.gui.keycode.KeyCode;
import nl.knokko.gui.render.GuiRenderer;
import nl.knokko.gui.texture.GuiTexture;
import nl.knokko.story.dialogue.SimpleDialoguePart;
import nl.knokko.util.resources.Resources;

import static nl.knokko.shaders.SpecialGuiShader.SPECIAL_GUI_SHADER;

import java.awt.image.BufferedImage;

import static nl.knokko.gui.shader.GuiShader.GUI_SHADER;

public class SimpleDialogueComponent extends AbstractGuiComponent {
	
	private final SimpleDialoguePart part;
	private final GuiDialogue gui;
	private GuiTexture texture;

	public SimpleDialogueComponent(SimpleDialoguePart part, GuiDialogue gui) {
		this.part = part;
		this.gui = gui;
	}

	@Override
	public void init() {
		texture = state.getWindow().getTextureLoader().loadTexture(Resources.createDialogueImage(part, null, false));
	}

	@Override
	public void update() {}

	@Override
	public void render(GuiRenderer renderer) {
		SPECIAL_GUI_SHADER.start();
		SPECIAL_GUI_SHADER.updateColors();
		// This is really funny stuff: 
		// Even though the GuiRenderer assumes that the normal gui shader is being used, it won't make a
		// difference because the special gui shader uses the same data and the lwjgl interface is static
		renderer.renderTexture(texture, 0, 0, 1, 1);
		SPECIAL_GUI_SHADER.stop();
		GUI_SHADER.start();
	}

	@Override
	public void click(float x, float y, int button) {
		next();
	}

	@Override
	public void clickOut(int button) {}

	@Override
	public boolean scroll(float amount) {
		return false;
	}

	@Override
	public void keyPressed(int keyCode) {
		if (keyCode == KeyCode.KEY_ENTER || keyCode == KeyCode.KEY_SPACE || keyCode == KeyCode.KEY_RIGHT) {
			next();
		}
	}

	@Override
	public void keyPressed(char character) {}

	@Override
	public void keyReleased(int keyCode) {}
	
	private void next() {
		part.next();
		if (gui.getDialogue().current() != part) {
			gui.changePart();
		}
	}
}